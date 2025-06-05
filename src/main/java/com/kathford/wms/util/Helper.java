package com.kathford.wms.util;

import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.User;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class Helper {

    private final
    AdminService adminService;

    private final UserService userService;

    private static final String AES_ALGORITHM = "AES";
    private static final String SECRET_KEY = "ThisIsASecretKey";

    public static void setTitle(Model model, String title) {
        model.addAttribute("title", title);
    }

    public static boolean checkAdminLogin(HttpSession session) {
        return (session.getAttribute("admin_id") != null);
    }

    public static boolean checkUserLogin(HttpSession session) {
        return (session.getAttribute("username") != null && session.getAttribute("username") != "");
    }

    public static Long getLoggedInUserId(HttpSession session) {
        return session.getAttribute("user_id") != null ? Long.parseLong(session.getAttribute("user_id").toString()) : 0l;
    }

    public User getLoggedInUser(HttpSession session) {
        return userService.findById(Helper.getLoggedInUserId(session));
    }

    public static Long getLoggedInAdminId(HttpSession session) {
        return session.getAttribute("admin_id") != null ? Long.parseLong(session.getAttribute("admin_id").toString()) : 0l;
    }

    public Admin getLoggedInAdmin(HttpSession session) {
        return adminService.findById(Helper.getLoggedInAdminId(session));
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(List<?> value) {
        return value == null || value.isEmpty();
    }

    public static void setFlashMessage(String messageType, String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(messageType, message);
    }

    public static String redirectToUserLogin(RedirectAttributes redirectAttributes) {
        //validate user login status
        redirectAttributes.addFlashAttribute(MessageType.MESSAGE, "Please login.");
        return "redirect:/login";
    }

    public static String redirectToAdminLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MessageType.MESSAGE, "Please login.");
        return "redirect:/admin/login";
    }

    public static String redirectToCollectorLogin(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(MessageType.MESSAGE, "Please login.");
        return "redirect:/collector/login";
    }

    public static Date getCurrentDateTime() {
        return new Date();
    }

    public static boolean isNotNUllAndGreaterThanZero(Long value) {
        return value != null && value > 0;
    }

    public static boolean isNotNUllAndGreaterThanZero(Integer value) {
        return value != null && value > 0;
    }

    public static ResponseEntity<Response> getResponseEntity(Object object, String message, boolean succcess) {
        return new ResponseEntity<>(new Response(object, message, succcess), HttpStatus.OK);
    }

    /*public static String encryptPassword(String password) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }*/

    public static String decryptPassword(String encryptedPassword) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

}
