package com.kathford.wms.controller;


import com.kathford.wms.config.constant.Constant;
import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.WasteService;
import com.kathford.wms.util.EmailUtil;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "email")
public class EmailSendController {

    private final AdminService adminService;

    private final WasteService wasteService;

    @RequestMapping(value = "")
    public String viewEmailSend(
            HttpSession session,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        //validate collector login
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);

        model.addAttribute("baseUrl", request.getContextPath());
        Helper.setTitle(model, "Bulk Email Send");
        var wasteList = wasteService.filterWasteAsRequested(Constant.APPROVED, "", null, true);
        if (!Helper.isNullOrEmpty(wasteList)) {
            // Create a custom comparator to sort by name
//            Comparator<Waste> nameComparator = Comparator.comparing(Waste::getCreatedDate).reversed();
            Comparator<Waste> nameComparator = Comparator.comparing(Waste::getCreatedDate);
            wasteList.sort(nameComparator);
        }

        model.addAttribute("wastes", wasteList);

        return "email/index";
    }


    @PostMapping(value = "sent")
    public ResponseEntity<Response> sendEmail(
            HttpSession session,
            @RequestParam String address,
            @RequestParam String type,
            @RequestParam(required = false) String fromTime,
            @RequestParam(required = false) String toTime

    ) {
        //validate collector login
        if (!Helper.checkAdminLogin(session))
            return Helper.getResponseEntity(null, "Session Expired!! Please login first.", false);

        var success = false;
        String message = null;

        try {
            var wastage = wasteService.filterWasteAsRequested(Constant.APPROVED, address, null, true);
            if (!Helper.isNullOrEmpty(wastage)) {
                Admin admin = adminService.findById(Helper.getLoggedInAdminId(session));
                var sendarEmail = admin.getEmail();
                String textMsg;

                if (type.equalsIgnoreCase(Constant.COLLECT)) {
                    textMsg = "Waste can be colleted from " + fromTime + " to " + toTime + ". So please be ready on time.";
                } else {
                    textMsg = "Sorry but it is informed you that due to certain problem, today your wastage is not going to collect. I will inform in next email about the time. Thank you.";
                }

                for (var waste : wastage) {
                    var isApproved = waste.getApprovedBy() != null;
                    if (isApproved) {
                        success = true;
                        var receiverEmail = waste.getCreatedBy().getEmail();
                        EmailUtil.sendMail(textMsg, "Alert !!Alert", sendarEmail, receiverEmail);
                    }
                }

                if (success) message = "Email sent successfully";
                else message = "Failed to send email.";
            } else message = "Wastage is not approved at. So please approved the waste and try again.";
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }
}