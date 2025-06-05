package com.kathford.wms.controller;

import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Category;
import com.kathford.wms.domain.User;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.CategoryService;
import com.kathford.wms.service.WasteService;
import com.kathford.wms.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "waste")
public class WasteController {

    private final WasteService wasteService;
    private final Helper helper;
    private final CategoryService categoryService;
    private final AdminService adminService;

    @GetMapping(value = "")
    public String viewListingWaste(HttpSession session, Model model, RedirectAttributes redirectAttributes,
                                   HttpServletRequest httpServletRequest) {
        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Helper.setTitle(model, "All Wastes");
        model.addAttribute("baseUrl", httpServletRequest.getContextPath());
        Long loggedInUserId = Helper.getLoggedInUserId(session);
        model.addAttribute("wastes", wasteService.findAllWasteByUserId(loggedInUserId));

        return "waste/index";
    }

    @PostMapping(value = "filter-all")
    public ResponseEntity<Response> filterAllWaste(
            HttpSession session,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate
    ) {

        //validate user logged in status
//        if (!Helper.checkUserLogin(session) || !Helper.checkAdminLogin(session))
//            return Helper.getResponseEntity(null, "Please login first", false);

        var success = false;
        String message = null;
        List<Waste> wasteList = null;
        try {
            wasteList = wasteService.filterWasteAsRequested(status, fromDate, toDate, Helper.getLoggedInUserId(session));
            if (!Helper.isNullOrEmpty(wasteList)) {
                // Create a custom comparator to sort by name
//                Comparator<Waste> nameComparator = Comparator.comparing(Waste::getCreatedDate);
                Comparator<Waste> nameComparator = Comparator.comparing(Waste::getCreatedDate).reversed();
                wasteList.sort(nameComparator);
            }

            if (!Helper.isNullOrEmpty(wasteList))
                success = true;

        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(wasteList, message, success);
    }

    @GetMapping(value = "add")
    public String addWaste(HttpSession session, RedirectAttributes redirectAttributes, Model model,
                           HttpServletRequest request) {
        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Helper.setTitle(model, "Add Waste");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("categories", categoryService.findAllCategory());

        return "waste/add";
    }

    @PostMapping(value = "save")
    public String saveWaste(HttpSession session, RedirectAttributes redirectAttributes,
                            @ModelAttribute Waste waste,
                            @RequestParam(required = false) MultipartFile attachment,
                            @RequestParam Long categoryId) throws IOException {
        //validate user login status
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        String name = waste.getName();
        String longitude = waste.getLongitude();

        if (Helper.isNullOrEmpty(name) || Helper.isNullOrEmpty(longitude)) {
            Helper.setFlashMessage(MessageType.MESSAGE, "Please input name and longitude and then try again.", redirectAttributes);
            return "redirect:/waste";
        }

        User user = helper.getLoggedInUser(session);
        if (user != null)
            waste.setCreatedBy(user);

        waste.setCreatedDate(Helper.getCurrentDateTime());
        waste.setCategory(new Category(categoryId));
        waste.setStatus("Pending");

        if (attachment != null && attachment.getSize() > 0) {
            String postImage = System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + FilenameUtils.getExtension(attachment.getOriginalFilename());
            attachment.transferTo(new File(Strings.FILE_UPLOAD_BASE_PATH + "waste/" + postImage));
            waste.setImage(postImage);
        }

        if (wasteService.saveOrUpdate(waste))
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.SAVED_SUCCESSFULLY, redirectAttributes);
        else
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_SAVE, redirectAttributes);

        return "redirect:/waste";
    }

    @GetMapping(value = "{id}")
    public String viewWaste(HttpSession session, RedirectAttributes redirectAttributes,
                            @PathVariable Long id, Model model) {
        //validate user login status
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Waste waste = wasteService.findById(id);
        Helper.setTitle(model, "View Waste");
        model.addAttribute("waste", waste);

        return "waste/view";
    }

    @GetMapping(value = "{id}/edit")
    public String editWaste(HttpSession session, RedirectAttributes redirectAttributes,
                            @PathVariable Long id, Model model) {
        //validate user login status
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Waste waste = wasteService.findById(id);
        Helper.setTitle(model, "Edit Waste");
        if (waste != null) {
            model.addAttribute("waste", waste);
            model.addAttribute("categories", categoryService.findAllCategory());
        } else return "redirect:/waste";

        return "waste/edit";
    }

    @PostMapping(value = "{id}/update")
    public String updateWaste(HttpSession session, RedirectAttributes redirectAttributes,
                              @ModelAttribute Waste waste,
                              @PathVariable Long id,
                              @RequestParam(required = false) MultipartFile attachment,
                              @RequestParam Long categoryId) throws IOException {

        User user = helper.getLoggedInUser(session);
        Waste updateWaste = wasteService.findById(id);

        updateWaste.setModifiedBy(user);

        updateWaste.setModifiedDate(Helper.getCurrentDateTime());
        updateWaste.setCategory(new Category(categoryId));
        updateWaste.setName(waste.getName());
        updateWaste.setAddress(waste.getAddress());
        updateWaste.setWeight(waste.getWeight());
//        waste.setStatus("Pending");
        updateWaste.setPrice(waste.getPrice());
        updateWaste.setLongitude(waste.getLongitude());
//        updateWaste.setLocation(waste.getLocation());
        updateWaste.setDescription(waste.getDescription());

        if (attachment != null && attachment.getSize() > 0) {
            String postImage = System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + FilenameUtils.getExtension(attachment.getOriginalFilename());
            attachment.transferTo(new File(Strings.FILE_UPLOAD_BASE_PATH + "waste/" + postImage));
            updateWaste.setImage(postImage);
        } else
            updateWaste.setImage(updateWaste.getImage());

        if (wasteService.saveOrUpdate(updateWaste))
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.UPDATED_SUCCESSFULLY, redirectAttributes);
        else
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_UPDATE, redirectAttributes);


        return "redirect:/waste";
    }

    @GetMapping(value = "{id}/delete")
    public String deleteWaste(
            HttpSession session, RedirectAttributes redirectAttributes,
            @PathVariable Long id
    ) {
        //validate user login status
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Waste waste = wasteService.findById(id);
        if (waste != null) {
            if (wasteService.deleteWaste(waste)) {
                Helper.setFlashMessage(MessageType.MESSAGE, Strings.DELETED_SUCCESSFULLY, redirectAttributes);
            } else
                Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_DELETE, redirectAttributes);
        }

        return "redirect:/waste";
    }

    @GetMapping(value = "{id}/approved")
    public String wasteApproved(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) throws Exception {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToCollectorLogin(redirectAttributes);

        Waste waste = wasteService.findById(id);
        Admin admin = adminService.findById(Helper.getLoggedInAdminId(session));
        waste.setApprovedBy(admin);
        waste.setApprovedDate(Helper.getCurrentDateTime());
        waste.setStatus("Approved");

        var receiverEmail = waste.getCreatedBy().getEmail();
        var sendarEmail = admin.getEmail();
        EmailUtil.sendMail(
                "Your wastage is approve by " + admin.getName() + ".", "Wastage Approval",
                sendarEmail, receiverEmail
        );
        if (wasteService.saveOrUpdate(waste))
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.APPROVED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_APPROVE, redirectAttributes);

        return "redirect:/collector-waste";

    }

    @GetMapping(value = "{id}/rejected")
    public String rejectWastes(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToCollectorLogin(redirectAttributes);

        Waste waste = wasteService.findById(id);
        Admin admin = adminService.findById(Helper.getLoggedInAdminId(session));
        waste.setApprovedBy(admin);
        waste.setApprovedDate(Helper.getCurrentDateTime());
        waste.setStatus("Rejected");

        var receiverEmail = waste.getCreatedBy().getEmail();
        var sendarEmail = admin.getEmail();
        EmailUtil.sendMail(
                "Your wastage is rejected by " + admin.getName() + ".", "Wastage Rejected",
                sendarEmail, receiverEmail
        );

        if (wasteService.saveOrUpdate(waste))
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.REJECTED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_REJECT, redirectAttributes);

        return "redirect:/collector-waste";
    }
}