package com.kathford.wms.controller;

import com.kathford.wms.config.constant.Constant;
import com.kathford.wms.domain.Payment;
import com.kathford.wms.domain.User;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.PaymentService;
import com.kathford.wms.service.UserService;
import com.kathford.wms.service.WasteService;
import com.kathford.wms.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Comparator;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "payment")
public class PaymentController {

    private final Helper helper;
    private final AdminService adminService;
    private final WasteService wasteService;
    private final UserService userService;
    private final PaymentService paymentService;

    @GetMapping(value = "")
    public String viewPayment(HttpSession session, Model model,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate admin login
//        if (!Helper.checkAdminLogin(session))
//            return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "Payment");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("payments", paymentService.findAllPaymentByCollectorId(Helper.getLoggedInAdminId(session)));

        return "payment/index";
    }

    @GetMapping(value = "to-user")
    public String viewUserPayment(HttpSession session, Model model,
                                  HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate admin login
//        if (!Helper.checkUserLogin(session))
//            return Helper.redirectToUserLogin(redirectAttributes);

        Helper.setTitle(model, "Payment");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("payments", paymentService.findAllPaymentByCollectorId(null));

        return "payment/user-payment-list";
    }

    @GetMapping(value = "all")
    public String viewUserAllPayment(HttpSession session, Model model,
                                     HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate user login
        /*if (!Helper.checkAdminLogin(session))
            return Helper.redirectToAdminLogin(redirectAttributes);*/

        Helper.setTitle(model, "All Collector Payment");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("payments", paymentService.findAllPaymentByCollectorId(null));

        return "payment/all-payment";
    }

    @GetMapping(value = "add")
    public String addPayment(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes,
                             Model model) {

        //validate admin login
        /*if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);*/

        Helper.setTitle(model, "Add Payment");
        model.addAttribute("baseUrl", request.getContextPath());
//        var wasteList = wasteService.filterWasteAsRequested(Constant.APPROVED, "", Helper.getLoggedInUserId(session));
        var wasteList = wasteService.filterWasteAsRequested(Constant.APPROVED, "", null, false);
        if (!Helper.isNullOrEmpty(wasteList)) {
            // Create a custom comparator to sort by name
//            Comparator<Waste> nameComparator = Comparator.comparing(Waste::getCreatedDate).reversed();
            Comparator<Waste> nameComparator = Comparator.comparing(Waste::getCreatedDate);
            wasteList.sort(nameComparator);

            for (var w : wasteList) {
                System.out.println(w.getName() + " Approved waste name  ");
            }
        }

        model.addAttribute("wastes", wasteList);

        return "payment/add-v2";
    }

    @PostMapping(value = "save-update")
    public String savePayment(
            HttpSession session,
            @RequestParam(required = false) Long wasteId,
            @ModelAttribute Payment payment,
            RedirectAttributes redirectAttributes
    ) {

        //validate admin logged
        /*if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);*/

        try {
            Waste waste = wasteService.findById(wasteId);
            if (waste != null)
                payment.setWaste(waste);
            payment.setCreatedDate(Helper.getCurrentDateTime());
            payment.setStatus("Pending");
//            payment.setCreatedBy(helper.getLoggedInUser(session));
            payment.setCreatedBy(helper.getLoggedInAdmin(session));
            if (paymentService.saveOrUpdate(payment)) {

                //send email
                if (waste != null) {
                    var sendarEmail = waste.getCreatedBy().getEmail();
                    var approval = waste.getApprovedBy();
                    if (approval != null) {
                        var receiverEmail = approval.getEmail();
                        EmailUtil.sendMail(waste.getCreatedBy().getName() + " has successfully pay the money.", "Paid money",
                                sendarEmail, receiverEmail);
                    }
                }

                Helper.setFlashMessage(MessageType.SUCCESS, Strings.SAVED_SUCCESSFULLY, redirectAttributes);
            } else Helper.setFlashMessage(MessageType.ERROR, Strings.SOMETHING_WENT_WRONG, redirectAttributes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/payment";
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Response> deletePayment(
            @RequestParam Long id
    ) {
        var success = false;
        String message = null;
        try {

            var payment = paymentService.findById(id);
            if (paymentService.delete(payment)) {
                success = true;
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "{id}/approve")
    public String approvePayment(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate user logged in status
//        if (!Helper.checkUserLogin(session))
//            return Helper.redirectToUserLogin(redirectAttributes);

//        Admin admin = adminService.findById(Helper.getLoggedInAdminId(session));
        User user = userService.findById(Helper.getLoggedInUserId(session));
        Payment payment = paymentService.findById(id);
        payment.setApprovedOrRejectedBy(user);
        payment.setApproveOrRejectedDate(Helper.getCurrentDateTime());
        payment.setStatus(Constant.APPROVED);
//        boolean success = paymentService.approveOrRejectPayment(id, "Approved");
        if (paymentService.saveOrUpdate(payment)) {
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.APPROVED_SUCCESSFULLY, redirectAttributes);
            var senderEmail = user.getEmail();
            var receiverEmail = payment.getCreatedBy().getEmail();
            EmailUtil.sendMail("Your payment of wastage is successfully received.", "Payment Successfull", senderEmail, receiverEmail);
        } else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_APPROVE, redirectAttributes);

        return "redirect:/payment/to-user";

    }

    @GetMapping(value = "{id}/reject")
    public String rejectPayment(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate user logged in status
//        if (!Helper.checkUserLogin(session)) return Helper.redirectToUserLogin(redirectAttributes);

//        boolean success = paymentService.approveOrRejectPayment(id, "Rejected");
//        if (success)
//        Admin admin = adminService.findById(Helper.getLoggedInAdminId(session));
        User user = userService.findById(Helper.getLoggedInUserId(session));
        Payment payment = paymentService.findById(id);
        payment.setApprovedOrRejectedBy(user);
        payment.setApproveOrRejectedDate(Helper.getCurrentDateTime());
        payment.setStatus(Constant.REJECTED);
        if (paymentService.saveOrUpdate(payment)) {
            var senderEmail = user.getEmail();
            var receiverEmail = payment.getCreatedBy().getEmail();
            EmailUtil.sendMail("Your payment of wastage is rejected.", "Payment Rejected", senderEmail, receiverEmail);
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.REJECTED_SUCCESSFULLY, redirectAttributes);
        } else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_REJECT, redirectAttributes);

        return "redirect:/payment/to-user";
    }


    @PostMapping(value = "view")
    public ResponseEntity<Response> viewPayment(
            @RequestParam Long id
    ) {

        var success = false;
        String message = null;
        Payment payment = null;
        try {
            payment = paymentService.findById(id);
            if (payment != null)
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(payment, message, success);
    }
}
