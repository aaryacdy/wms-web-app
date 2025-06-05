package com.kathford.wms.api;

import com.kathford.wms.config.constant.Constant;
import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Payment;
import com.kathford.wms.domain.User;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.PaymentService;
import com.kathford.wms.service.UserService;
import com.kathford.wms.service.WasteService;
import com.kathford.wms.util.EmailUtil;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/payment")
public class PaymentApiController {

    private final Helper helper;
    private final AdminService adminService;
    private final WasteService wasteService;
    private final UserService userService;
    private final PaymentService paymentService;

    /*used for admin only to show all the payment*/
    @GetMapping(value = "all")
    public ResponseEntity<Response> viewAllCollectorPayment(
    ) {

        var success = false;
        String message = null;

        List<Payment> payments = null;
        try {

            payments = paymentService.findAllPayment();
            if (!Helper.isNullOrEmpty(payments))
                success = true;

        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "collector-payment")
    public ResponseEntity<Response> viewCollectorPayment(
            @RequestParam Long collectorId
    ) {

        var success = false;
        String message = null;

        List<Payment> payments = null;
        try {

            payments = paymentService.findAllPaymentByCollectorId(collectorId);
            if (!Helper.isNullOrEmpty(payments))
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @PostMapping(value = "save-update")
    public ResponseEntity<Response> savePayment(
            HttpSession session,
            @RequestParam(required = false) Long wasteId,
            @ModelAttribute Payment payment,
            @RequestParam Long collectorId,
            RedirectAttributes redirectAttributes
    ) {

        var success = false;
        String message = null;

        try {
            Waste waste = wasteService.findById(wasteId);
            if (waste != null)
                payment.setWaste(waste);
            payment.setCreatedDate(Helper.getCurrentDateTime());
            payment.setStatus("Pending");
            payment.setCreatedBy(new Admin(collectorId));
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

                message = Strings.SAVED_SUCCESSFULLY;
                success = true;
            } else message = Strings.SOMETHING_WENT_WRONG;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Helper.getResponseEntity(null, message, success);
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
    public ResponseEntity<Response> approvePayment(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {

        var success = false;
        String message = null;

        try {
            User user = userService.findById(userId);
            Payment payment = paymentService.findById(id);
            payment.setApprovedOrRejectedBy(user);
            payment.setApproveOrRejectedDate(Helper.getCurrentDateTime());
            payment.setStatus(Constant.APPROVED);
            if (paymentService.saveOrUpdate(payment)) {
                message = Strings.APPROVED_SUCCESSFULLY;
                var senderEmail = user.getEmail();
                var receiverEmail = payment.getCreatedBy().getEmail();
                EmailUtil.sendMail("Your payment of wastage is successfully received.", "Payment Successfull", senderEmail, receiverEmail);
                success = true;
            } else message = Strings.FAILED_TO_APPROVE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);

    }

    @GetMapping(value = "{id}/reject")
    public ResponseEntity<Response> rejectPayment(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        var success = false;
        String message = null;

        try {
            User user = userService.findById(userId);
            Payment payment = paymentService.findById(id);
            payment.setApprovedOrRejectedBy(user);
            payment.setApproveOrRejectedDate(Helper.getCurrentDateTime());
            payment.setStatus(Constant.REJECTED);
            if (paymentService.saveOrUpdate(payment)) {
                var senderEmail = user.getEmail();
                var receiverEmail = payment.getCreatedBy().getEmail();
                EmailUtil.sendMail("Your payment of wastage is rejected.", "Payment Rejected", senderEmail, receiverEmail);
                message = Strings.REJECTED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_REJECT;


        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
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
