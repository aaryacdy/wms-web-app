package com.kathford.wms.controller;

import com.kathford.wms.config.constant.Constant;
import com.kathford.wms.domain.Admin;
import com.kathford.wms.service.AdminService;
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
import java.util.Date;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "collector")
public class CollectorController {

    private final AdminService adminService;

    @GetMapping(value = "signup")
    public String viewSignUpPage(Model model, HttpServletRequest httpServletRequest) {

        Helper.setTitle(model, "Signup");
        model.addAttribute("baseUrl", httpServletRequest.getContextPath());
        return "collector/add";
    }

    @PostMapping(value = "signup/save")
    public String saveRegistration(RedirectAttributes redirectAttributes,
                                   @ModelAttribute Admin admin) {
        try {
            String password = admin.getPassword();
            String username = admin.getUsername();
            String email = admin.getEmail();

            if (Helper.isNullOrEmpty(username)) {
                redirectAttributes.addFlashAttribute("message", "Please enter username.");
                return "redirect:/collector/signup";
            }

            if (Helper.isNullOrEmpty(password)) {
                redirectAttributes.addFlashAttribute("message", "Please enter password.");
                return "redirect:/collector/signup";
            }

            if (adminService.isUsernameAlreadyAvailable(username)) {
                Helper.setFlashMessage(MessageType.MESSAGE, "Username is already available. Please use another username.", redirectAttributes);
                return "redirect:/collector/signup";
            }

            if (adminService.isEmailAlreadyAvailable(email)) {
                Helper.setFlashMessage(MessageType.MESSAGE, "Email is already available. Please use another email.", redirectAttributes);
                return "redirect:/collector/signup";
            }

            admin.setRole(Constant.COLLECTOR);
            String salt = UUID.randomUUID().toString().substring(0, 8);
            String concatedPwd = salt.concat(password);
            String finalRawPwd = DigestUtils.md5DigestAsHex(concatedPwd.getBytes());
            admin.setPassword(finalRawPwd);
            admin.setSalt(salt);
            admin.setPhoneNo(admin.getPhoneNo() != null ? admin.getPhoneNo() : "");
            admin.setCreatedDate(new Date());

            if (adminService.saveOrUpdate(admin)) {
                redirectAttributes.addFlashAttribute("message", Strings.SAVED_SUCCESSFULLY);
            } else redirectAttributes.addFlashAttribute("message", Strings.FAILED_TO_SAVE);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", Strings.SOMETHING_WENT_WRONG);
        }

        return "redirect:/collector/login";
    }

    @GetMapping(value = "login")
    public String viewLoginPage(Model model, HttpServletRequest httpServletRequest) {
        //validate admin logged in status

        log.info("Collector Login");
        Helper.setTitle(model, "Login");
        model.addAttribute("baseUrl", httpServletRequest.getContextPath());
        return "collector/login";
    }

    @PostMapping(value = "login/validate-login")
    public String validateLogin(RedirectAttributes redirectAttributes,
                                HttpSession session,
                                @RequestParam String username,
                                @RequestParam String password) {

        if (Helper.isNullOrEmpty(username)) {
            redirectAttributes.addAttribute("message", "Please enter username");
            return "redirect:/collector/signup";
        }

        if (Helper.isNullOrEmpty(password)) {
            redirectAttributes.addAttribute("message", "Please enter password");
            return "redirect:/collector/signup";
        }

        Admin admin = adminService.findByUsername(username);
        String redirectUrl = "redirect:/collector/login";
        if (admin != null) {
            String salt = admin.getSalt();
            String encryptedPwd = admin.getPassword();

            String rawPwd = salt.concat(password);
            String finalPwd = DigestUtils.md5DigestAsHex(rawPwd.getBytes());

            if (finalPwd.equalsIgnoreCase(encryptedPwd)) {
                Helper.setFlashMessage(MessageType.MESSAGE, "Login successfully.", redirectAttributes);
                redirectUrl = "redirect:/collector/dashboard";
//                System.out.println(admin.getId() + " admiId ");
                session.setAttribute("admin_username", username);
                session.setAttribute("admin_id", admin.getId());
            } else {
                Helper.setFlashMessage(MessageType.MESSAGE, "Password does not match.", redirectAttributes);
            }
        } else Helper.setFlashMessage(MessageType.MESSAGE, "Username and password does not match.", redirectAttributes);

        return redirectUrl;
    }

    @GetMapping(value = "logout")
    public String userLogout(HttpSession session) {
        //destroy the session here
        session.removeAttribute("username");
        session.removeAttribute("admin_id");

        return "redirect:/admin/login";
    }

    @GetMapping(value = "reset-password")
    public String resetPassword(HttpSession session, Model model, RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {
        //validate user login status
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);

        Helper.setTitle(model, "Reset Password");
        model.addAttribute("baseUrl", request.getContextPath());
        return "collector/reset-password";
    }

    @PostMapping(value = "reset-password")
    public String resetPassword(HttpSession session,
                                RedirectAttributes redirectAttributes,
                                @RequestParam String oldPassword,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword) {
        //validate user logged in status
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);

        if (!Helper.isNullOrEmpty(oldPassword) && !Helper.isNullOrEmpty(newPassword) && !Helper.isNullOrEmpty(confirmPassword)) {
            Admin admin = adminService.findById(Helper.getLoggedInAdminId(session));
            if (admin != null) {
                String salt = admin.getSalt();
                String encryptedPwd = admin.getPassword();
                String rawPwd = salt.concat(oldPassword);
                String finalPwd = DigestUtils.md5DigestAsHex(rawPwd.getBytes());
                if (finalPwd.equalsIgnoreCase(encryptedPwd)) {
                    if (newPassword.equalsIgnoreCase(confirmPassword)) {
                        String newSalt = UUID.randomUUID().toString().substring(0, 8);
                        String newRawPwd = newSalt.concat(newPassword);
                        String newFinalPwd = DigestUtils.md5DigestAsHex(newRawPwd.getBytes());
                        admin.setSalt(newSalt);
                        admin.setPassword(newFinalPwd);
                        admin.setModifiedDate(Helper.getCurrentDateTime());
                        if (adminService.saveOrUpdate(admin)) {
                            Helper.setFlashMessage(MessageType.MESSAGE, Strings.RESET_SUCCESSFULLY, redirectAttributes);
                        } else
                            Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_SAVE_CHANGES, redirectAttributes);
                    } else
                        Helper.setFlashMessage(MessageType.MESSAGE, "New password and confirm password does not match. Please match both and try again.", redirectAttributes);
                } else
                    Helper.setFlashMessage(MessageType.MESSAGE, "Old password does not match. Please tray again.", redirectAttributes);
            } else Helper.setFlashMessage(MessageType.MESSAGE, "Failed to reset password", redirectAttributes);
        } else
            Helper.setFlashMessage(MessageType.MESSAGE, "Please enter all the field and then try again.", redirectAttributes);

        return "redirect:/collector/reset-password";
    }

    @GetMapping(value = "")
    public String viewAllCollectors(HttpSession session, RedirectAttributes redirectAttributes, Model model,
                                    HttpServletRequest request) {
        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "All Collectors");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("collectors", adminService.findAllByRole(Constant.COLLECTOR));

        return "admin/all-collector";
    }

    @GetMapping(value = "{id}/unblock")
    public String unBlockCollector(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        boolean success = adminService.blockOrUnblockedAdmin(id, false);
        if (success)
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.COLLECTOR_UNBLOCKED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_UNBLOCKED_COLLECTOR, redirectAttributes);

        return "redirect:/collector";

    }

    @GetMapping(value = "{id}/block")
    public String blockCollector(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        boolean success = adminService.blockOrUnblockedAdmin(id, true);
        if (success)
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.COLLECTOR_BLOCKED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_BLOCKED_COLLECTOR, redirectAttributes);

        return "redirect:/collector";
    }

    @GetMapping(value = "{id}/delete")
    public String deleteCollector(HttpSession session, RedirectAttributes redirectAttributes, @PathVariable Long id) {

        //validate admin logged in status
        if (!Helper.checkAdminLogin(session)) return Helper.redirectToAdminLogin(redirectAttributes);

        Admin admin = adminService.findById(id);
        if (admin == null) {
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.LINK_BROKEN, redirectAttributes);
            return "redirect:/collector";
        }

        if (adminService.deleteUser(admin))
            Helper.setFlashMessage(MessageType.MESSAGE, Strings.DELETED_SUCCESSFULLY, redirectAttributes);
        else Helper.setFlashMessage(MessageType.MESSAGE, Strings.FAILED_TO_DELETE, redirectAttributes);

        return "redirect:/collector";
    }
}
