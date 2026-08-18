package com.kathford.wms.controller;

import com.kathford.wms.domain.User;
import com.kathford.wms.service.AboutService;
import com.kathford.wms.service.UserService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.MessageType;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AboutService aboutService;

    @GetMapping(value = "signup")
    public String viewUserSignup(Model model, HttpServletRequest request) {

        Helper.setTitle(model, "Signup");
        model.addAttribute("baseUrl", request.getContextPath());

        return "user/signup";
    }

    @PostMapping(value = "signup")
    public String saveSignup(RedirectAttributes redirectAttributes, @ModelAttribute User user) {

        try {
            String password = user.getPassword();
            String username = user.getUsername();
            String email = user.getEmail();

            if (Helper.isNullOrEmpty(username)) {
                redirectAttributes.addFlashAttribute("message", "Please enter username.");
                return "redirect:/signup";
            }

            if (Helper.isNullOrEmpty(password)) {
                redirectAttributes.addFlashAttribute("message", "Please enter password.");
                return "redirect:/signup";
            }

            if (userService.isUsernameAlreadyAvailable(username)) {
                Helper.setFlashMessage(MessageType.MESSAGE, "Username is already available. Please use another username.", redirectAttributes);
                return "redirect:/signup";
            }

            if (userService.isEmailAlreadyAvailable(email)) {
                Helper.setFlashMessage(MessageType.MESSAGE, "Email is already available. Please use another email.", redirectAttributes);
                return "redirect:/signup";
            }

            String salt = UUID.randomUUID().toString().substring(0, 8);
            String concatedPwd = salt.concat(password);
            String finalRawPwd = DigestUtils.md5DigestAsHex(concatedPwd.getBytes());
            user.setPassword(finalRawPwd);
            user.setSalt(salt);
            user.setCreatedDate(new Date());

            if (userService.saveOrUpdate(user)) {
                redirectAttributes.addFlashAttribute(MessageType.MESSAGE, Strings.SAVED_SUCCESSFULLY);
            } else redirectAttributes.addFlashAttribute(MessageType.MESSAGE, Strings.FAILED_TO_SAVE);
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute(MessageType.MESSAGE, Strings.SOMETHING_WENT_WRONG);
        }

        return "redirect:/login";

    }

    @GetMapping(value = {""})
    public String dashboard(Model model, HttpServletRequest request) {

        Helper.setTitle(model, "Home page");
        model.addAttribute("baseUrl", request.getContextPath());
//        model.addAttribute("aboutUs", aboutService.findById(32l));
        model.addAttribute("aboutUs", aboutService.findById(10L));

        return "new-design/dashboard";
    }

    @GetMapping(value = "login")
    public String userLogin(Model model, HttpServletRequest request) {

        log.info(" Login Page ");
        Helper.setTitle(model, "Login");
        model.addAttribute("baseUrl", request.getContextPath());

        return "user/login";
    }

    @PostMapping(value = "validate-login")
    public String viewDashboardIfUserLoggedIn(
            RedirectAttributes redirectAttributes,
            @RequestParam String username, HttpSession session,
            @RequestParam String password
    ) {
        if (Helper.isNullOrEmpty(username)) {
            redirectAttributes.addAttribute("message", "Please enter username.");
            return "redirect:/login";
        }

        if (Helper.isNullOrEmpty(password)) {
            redirectAttributes.addAttribute("message", "Please enter password.");
            return "redirect:/login";
        }

        String redirectUrl = "redirect:/login";
        User user = userService.findByUsername(username);

        if (user != null) {

            if (user.isBlocked()) {
                Helper.setFlashMessage(MessageType.MESSAGE, "You have been blocked by admin. Please contact admin from contact us page.", redirectAttributes);
                return redirectUrl;
            }

            String salt = user.getSalt();
            String encryptedPwd = user.getPassword();

            String rawPwd = salt.concat(password);
            String finalPwd = DigestUtils.md5DigestAsHex(rawPwd.getBytes());

            if (finalPwd.equalsIgnoreCase(encryptedPwd)) {
                Helper.setFlashMessage(MessageType.MESSAGE, "Login successfully.", redirectAttributes);
                redirectUrl = "redirect:/dashboard";
                session.setAttribute("username", username);
                session.setAttribute("email", user.getEmail());
                session.setAttribute("fullname", user.getName());
                session.setAttribute("user_id", user.getId());
            } else Helper.setFlashMessage(MessageType.MESSAGE, "Password does not match.", redirectAttributes);
        } else Helper.setFlashMessage(MessageType.MESSAGE, "Username and password does not match.", redirectAttributes);

        return redirectUrl;

    }

    @GetMapping(value = "logout")
    public String userLogout(HttpSession session) {
        //destroy the session here
        session.removeAttribute("username");
        session.removeAttribute("email");
        session.removeAttribute("fullname");
        session.removeAttribute("user_id");

        return "redirect:/login";
    }

    @GetMapping(value = "users")
    public String viewAllUsers(HttpSession session, RedirectAttributes redirectAttributes, Model model,
                               HttpServletRequest request) {
        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "All Users");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("users", userService.findAllUser());
        return "user/index";
    }

    @GetMapping(value = "user/{id}/unblock")
    public String blockUser(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        boolean success = userService.blockOrUnblockedUser(id, false);
        if (success)
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.USER_UNBLOCKED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_UNBLOCKED_USER, redirectAttributes);

        return "redirect:/users";

    }

    @GetMapping(value = "user/{id}/block")
    public String unblockUser(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        boolean success = userService.blockOrUnblockedUser(id, true);
        if (success) Helper.setFlashMessage(MessageType.MESSAGE, Strings.USER_BLOCKED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_BLOCKED_USER, redirectAttributes);

        return "redirect:/users";
    }

    @GetMapping(value = "user/{id}/delete")
    public String deleteUser(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        User user = userService.findById(id);
        if (user == null) {
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.LINK_BROKEN, redirectAttributes);
            return "redirect:/users";
        }

        if (userService.deleteUser(user))
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.DELETED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_DELETE, redirectAttributes);

        return "redirect:/users";
    }

    @GetMapping(value = "user/reset-password")
    public String viewResetPassword(
            HttpSession session, Model model, RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        //validate user login status
        if (!Helper.checkUserLogin(session)) return Helper.redirectToUserLogin(redirectAttributes);

        Helper.setTitle(model, "Reset Password");
        model.addAttribute("baseUrl", request.getContextPath());
        return "user/reset-password";
    }

    @PostMapping(value = "user/reset-password")
    public String resetPassword(HttpSession session, RedirectAttributes redirectAttributes,
                                @RequestParam String oldPassword,
                                @RequestParam String newPassword, @RequestParam String confirmPassword) {
        //validate user logged in status
        if (!Helper.checkUserLogin(session)) return Helper.redirectToUserLogin(redirectAttributes);

        if (!Helper.isNullOrEmpty(oldPassword) && !Helper.isNullOrEmpty(newPassword) && !Helper.isNullOrEmpty(confirmPassword)) {
            User user = userService.findById(Helper.getLoggedInUserId(session));
            String salt = user.getSalt();
            String encryptedPwd = user.getPassword();
            String rawPwd = salt.concat(oldPassword);
            String finalPwd = DigestUtils.md5DigestAsHex(rawPwd.getBytes());
            if (finalPwd.equalsIgnoreCase(encryptedPwd)) {
                if (newPassword.equalsIgnoreCase(confirmPassword)) {
                    String newSalt = UUID.randomUUID().toString().substring(0, 8);
                    String newRawPwd = newSalt.concat(newPassword);
                    String newFinalPwd = DigestUtils.md5DigestAsHex(newRawPwd.getBytes());
                    user.setSalt(newSalt);
                    user.setPassword(newFinalPwd);
                    user.setModifiedDate(Helper.getCurrentDateTime());
                    if (userService.saveOrUpdate(user)) {
                        Helper.setFlashMessage(MessageType.MESSAGE, Strings.RESET_SUCCESSFULLY, redirectAttributes);
                    } else
                        Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_SAVE_CHANGES, redirectAttributes);
                } else
                    Helper.setFlashMessage(MessageType.MESSAGE, "New password and confirm password does not match. Please match both and try again.", redirectAttributes);
            } else
                Helper.setFlashMessage(MessageType.MESSAGE, "Old password does not match. Please tray again.", redirectAttributes);
        } else
            Helper.setFlashMessage(MessageType.MESSAGE, "Please enter all the field and then try again.", redirectAttributes);

        return "redirect:/user/reset-password";
    }
}