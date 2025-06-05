package com.kathford.wms.controller;

import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Payment;
import com.kathford.wms.domain.User;
import com.kathford.wms.service.PaymentService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;

@Controller
@RequestMapping(value = "payment/khalti")
@RequiredArgsConstructor
public class KhaltiIntegrationController {

    private final PaymentService paymentService;

    @PostMapping("callback")
    public ResponseEntity<Response> handleSuccess(
            HttpSession session,
            @RequestBody String payload
    ) {

        boolean success = false;
        String message = null;
        String finalResponse = null;


        var obj = new JSONObject(payload);
        var transactionId = obj.getString("idx");
//        String refId = obj.getString("product_identity");
        var token = obj.getString("token");
        var amount = obj.getDouble("amount");

        HttpResponse<JsonNode> resp = null;
        try {
            resp = Unirest.post("https://khalti.com/api/v2/payment/verify/")
                    .header("Authorization", "Key " + "test_secret_key_b19c5f583dd148d2bd8e125cb11b39d9")
//                    .header("Authorization", "Key " + "live_secret_key_71d84957ce944e76845a6e5b40b81e61")
                    .field("amount", amount)
                    .field("token", token)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        if (resp != null) {

            var payment = new Payment();
            payment.setPaymentMethod("Khalti");
            payment.setAmount(BigDecimal.valueOf(amount));
            payment.setCreatedDate(new Date());
//            Long userId = Helper.getLoggedInUserId(session);
//            if(userId == 0) userId = 23l;
//            payment.setCreatedBy(new User(52l));
//            payment.setCreatedBy(new Admin(52l));
            payment.setCreatedBy(new Admin(Helper.getLoggedInAdminId(session)));
            paymentService.saveOrUpdate(payment);

            JSONObject jsonObject = new JSONObject(resp.getBody().toString());
//            System.out.println(jsonObject);
            success = jsonObject.has("state") && jsonObject.getJSONObject("state").getString("name").equals("Completed");
            if (success)
                message = "Transaction completed successfully.";
            else
                message = "Sorry but failed to complete the transaction. Please contact admin with transaction Id: <b>" + transactionId + "</b>.";
        }

        return ResponseEntity.ok(new Response(finalResponse, message, success));
    }

}
