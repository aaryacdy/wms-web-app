package com.kathford.wms.api;

import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Category;
import com.kathford.wms.domain.User;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.WasteService;
import com.kathford.wms.util.EmailUtil;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "api/waste")
@RequiredArgsConstructor
public class WasteApiController {

    private final AdminService adminService;
    private final WasteService wasteService;

    @PostMapping(value = "save-update")
    public ResponseEntity<Response> saveWaste(
            @ModelAttribute Waste waste,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long categoryId
    ) {

        var success = false;
        String message = null;
        try {

            waste.setCreatedDate(new Date());
            waste.setCreatedBy(new User(userId));
            waste.setCategory(new Category(categoryId));
            if (wasteService.saveOrUpdate(waste)) {
                success = true;
                message = Strings.SAVED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_SAVE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Response> deleteWaste(
            @RequestParam Long id
    ) {

        var success = false;
        String message = null;
        try {

            var waste = wasteService.findById(id);
            if (wasteService.deleteWaste(waste)) {
                success = true;
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "view")
    public ResponseEntity<Response> viewWaste(
            @RequestParam Long id
    ) {

        var success = false;
        String message = null;
        Waste waste = null;
        try {

            waste = wasteService.findById(id);
            if (waste != null)
                success = true;
            else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(waste, message, success);
    }

    @GetMapping(value = "findall")
    public ResponseEntity<Response> viewAllWaste(
    ) {
        var success = false;
        String message = null;
        List<Waste> wastes = null;
        try {

            wastes = wasteService.findAll();
            if (!Helper.isNullOrEmpty(wastes))
                success = true;
            else message = "No data available.";
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(wastes, message, success);
    }

    @GetMapping(value = "filter-user-waste")
    public ResponseEntity<Response> filterUserWastes(
            @RequestParam Long userId
    ) {
        var success = false;
        String message = null;
        List<Waste> wastes = null;
        try {

            wastes = wasteService.findAllWasteByUserId(userId);
            if (!Helper.isNullOrEmpty(wastes))
                success = true;
            else message = "No data available.";
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(wastes, message, success);
    }

    @GetMapping(value = "approve")
    public ResponseEntity<Response> wasteApproved(
            @RequestParam Long id,
            @RequestParam Long collectorId
    ) {

        var success = false;
        String message = null;

        Waste waste = wasteService.findById(id);
        if (waste == null)
            return Helper.getResponseEntity(null, "Waste is null or you provide wrong waste id.", success);

        Admin admin = adminService.findById(collectorId);
        if (admin == null)
            return Helper.getResponseEntity(null, "No such collector is present. Please check the correct collector id and try again.", success);

        if (waste.getStatus() != null && waste.getStatus().equalsIgnoreCase("Approved"))
            return Helper.getResponseEntity(null, "Wastes is already approved.", success);

        try {

            waste.setApprovedBy(admin);
            waste.setApprovedDate(Helper.getCurrentDateTime());
            waste.setStatus("Approved");

            var receiverEmail = waste.getCreatedBy().getEmail();
            var sendarEmail = admin.getEmail();
            EmailUtil.sendMail(
                    "Your wastage is approve by " + admin.getName() + ".", "Wastage Approval",
                    sendarEmail, receiverEmail
            );

            if (wasteService.saveOrUpdate(waste)) {
                message = Strings.APPROVED_SUCCESSFULLY;
                success = true;
            } else message = Strings.FAILED_TO_APPROVE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "reject")
    public ResponseEntity<Response> rejectWastes(
            @RequestParam Long id,
            @RequestParam Long collectorId
    ) {

        var success = false;
        String message = null;

        Waste waste = wasteService.findById(id);
        if (waste == null)
            return Helper.getResponseEntity(null, "Waste is null or you provide wrong waste id.", success);

        Admin admin = adminService.findById(collectorId);
        if (admin == null)
            return Helper.getResponseEntity(null, "No such collector is present. Please check the correct collector id and try again.", success);

        if (waste.getStatus() != null && waste.getStatus().equalsIgnoreCase("Rejected"))
            return Helper.getResponseEntity(null, "Wastes is already rejected.", success);

        try {

            waste.setApprovedBy(admin);
            waste.setApprovedDate(Helper.getCurrentDateTime());
            waste.setStatus("Rejected");

            var receiverEmail = waste.getCreatedBy().getEmail();
            var sendarEmail = admin.getEmail();
            EmailUtil.sendMail(
                    "Your wastage is rejected by " + admin.getName() + ".", "Wastage Rejected",
                    sendarEmail, receiverEmail
            );

            if (wasteService.saveOrUpdate(waste)) {
                success = true;
                message = Strings.REJECTED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_REJECT;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "filterbystatus")
    public ResponseEntity<Response> viewAllApprovedWaste(
            @RequestParam(defaultValue = "Approved") String status
    ) {
        var success = false;
        String message = null;
        List<Waste> wastes = null;
        try {

            wastes = wasteService.filterWasteAsRequested(status, null, null, false);
            if (!Helper.isNullOrEmpty(wastes)) {
                // Create a custom comparator to sort by name
                Comparator<Waste> nameComparator = Comparator.comparing(Waste::getCreatedDate).reversed();
                wastes.sort(nameComparator);
            }
            if (!Helper.isNullOrEmpty(wastes))
                success = true;
            else message = "No data available.";
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(wastes, message, success);
    }
}
