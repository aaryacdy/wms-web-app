package com.kathford.wms.controller;

import com.kathford.wms.domain.BaseModel;
import com.kathford.wms.domain.Category;
import com.kathford.wms.domain.Complaint;
import com.kathford.wms.service.ComplaintService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.MessageType;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "complaint")
@RequiredArgsConstructor
public class ComplaintController extends BaseModel {

    private final Helper helper;
    private final ComplaintService complaintService;

    @GetMapping(value = "")
    public String viewComplaint(HttpSession session, Model model,
                                HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate admin login
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "Complaint");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("complaints", complaintService.findAll());

        return "complaint/all-complaint";
    }

    @GetMapping(value = "collector")
    public String viewComplaintToCollector(HttpSession session, Model model,
                                HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate admin login
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);

        Helper.setTitle(model, "Complaint");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("complaints", complaintService.findAll());

        return "complaint/collector-complaint";
    }

    @GetMapping(value = "add")
    public String addComplaint(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes,
                               Model model) {

        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Helper.setTitle(model, "Add Complaint");
        model.addAttribute("baseUrl", request.getContextPath());

        return "complaint/add";
    }

    @PostMapping(value = "save-update")
    public String saveComplaint(HttpSession session, RedirectAttributes redirectAttributes,
                                @ModelAttribute Complaint complaint) {

        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        boolean hasComplaint = complaint.getId() != null;

        try {

            complaint.setCreatedDate(Helper.getCurrentDateTime());
            complaint.setCreatedBy(helper.getLoggedInUser(session));
            if (complaintService.saveOrUpdate(complaint))
                Helper.setFlashMessage(MessageType.MESSAGE, hasComplaint ? Strings.UPDATED_SUCCESSFULLY : Strings.SAVED_SUCCESSFULLY, redirectAttributes);
            else
                Helper.setFlashMessage(MessageType.MESSAGE, hasComplaint ? Strings.FAILED_TO_UPDATE : Strings.FAILED_TO_SAVE, redirectAttributes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/complaint/user";
    }

    @GetMapping(value = "user")
    public String viewUserComplaint(HttpSession session, Model model,
                                    HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "Complaint");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("complaints", complaintService.findAllComplaintByUserId(Helper.getLoggedInUserId(session)));

        return "complaint/complaint";
    }
}
