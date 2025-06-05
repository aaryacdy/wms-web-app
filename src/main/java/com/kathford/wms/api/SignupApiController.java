package com.kathford.wms.api;

import com.kathford.wms.config.constant.Constant;
import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Token;
import com.kathford.wms.domain.User;
import com.kathford.wms.payload.response.TokenResponse;
import com.kathford.wms.service.AdminService;
import com.kathford.wms.service.TokenService;
import com.kathford.wms.service.UserService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api")
public class SignupApiController {

    private final UserService userService;
    private final AdminService adminService;
    private final TokenService tokenService;

    @GetMapping(value = "findall")
    public ResponseEntity<Response> getAllAdmin(
            @RequestParam(defaultValue = Constant.SUPER_ADMIN) String role
    ) {

        System.out.println(" HELLO ARYA ");
        var success = false;
        String messsage = null;
        List<Admin> admins = null;
        List<User> users = null;

        var isUser = role.equalsIgnoreCase(Constant.USER);
        System.out.println(isUser + " isUser ");
        try {
            if (isUser) {
                users = userService.findAllUser();
                if (!Helper.isNullOrEmpty(users))
                    success = true;
            } else {
                admins = adminService.findAllByRole(role);
                if (!Helper.isNullOrEmpty(admins))
                    success = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            messsage = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(isUser ? users : admins, messsage, success);
    }

    @PostMapping(value = "signup")
    public ResponseEntity<Response> saveRegistration(
            @ModelAttribute Admin admin,
            @RequestParam(defaultValue = Constant.USER) String role,
            @RequestParam(required = false) MultipartFile attachment,
            @ModelAttribute User user
    ) {

        String message;
        var success = false;
        try {

            var isUser = role.equalsIgnoreCase(Constant.USER);
            if (isUser) {
                String password = user.getPassword();
                String username = user.getUsername();
                String email = user.getEmail();

                if (Helper.isNullOrEmpty(username)) {
                    message = "Please enter username.";
                    return Helper.getResponseEntity(null, message, success);
                }

                if (Helper.isNullOrEmpty(password)) {
                    message = "Please enter password.";
                    return Helper.getResponseEntity(null, message, success);
                }

                if (userService.isUsernameAlreadyAvailable(username)) {
                    message = "Username is already available. Please use another username.";
                    return Helper.getResponseEntity(null, message, success);
                }

                if (userService.isEmailAlreadyAvailable(email)) {
                    message = "Email is already available. Please use another email.";
                    return Helper.getResponseEntity(null, message, success);
                }

                if (attachment != null && attachment.getSize() > 0) {
                    String postImage = System.currentTimeMillis() + "_" + UUID.randomUUID() + "." + FilenameUtils.getExtension(attachment.getOriginalFilename());
                    attachment.transferTo(new File(Strings.FILE_UPLOAD_BASE_PATH + "user/" + postImage));
                    user.setProfilePicture(postImage);
                }

                String salt = UUID.randomUUID().toString().substring(0, 8);
                String concatedPwd = salt.concat(password);
                String finalRawPwd = DigestUtils.md5DigestAsHex(concatedPwd.getBytes());
                user.setPassword(finalRawPwd);
                user.setSalt(salt);
                user.setRole(!Helper.isNullOrEmpty(user.getRole()) ? user.getRole() : "User");
                user.setUsername(username.toLowerCase());
                user.setCreatedDate(new Date());

                if (userService.saveOrUpdate(user)) {
                    message = Strings.SAVED_SUCCESSFULLY;
                    success = true;
                } else message = Strings.FAILED_TO_SAVE;
            } else {
                String password = admin.getPassword();
                String username = admin.getUsername();
                String email = admin.getEmail();

                log.info("Admin Username : {}, email : {}, Role : {}", username, email, role);

                if (Helper.isNullOrEmpty(username)) {
                    message = "Please enter username.";
                    return Helper.getResponseEntity(null, message, success);
                }

                if (Helper.isNullOrEmpty(password)) {
                    message = "Please enter password.";
                    return Helper.getResponseEntity(null, message, success);
                }

                if (adminService.isUsernameAlreadyAvailable(username, role)) {
                    message = "Username is already available. Please use another username.";
                    return Helper.getResponseEntity(null, message, success);
                }

                if (adminService.isEmailAlreadyAvailable(email, role)) {
                    message = "Email is already available. Please use another email.";
                    return Helper.getResponseEntity(null, message, success);
                }

                String salt = UUID.randomUUID().toString().substring(0, 8);
                String concatedPwd = salt.concat(password);
                String finalRawPwd = DigestUtils.md5DigestAsHex(concatedPwd.getBytes());
                admin.setPassword(finalRawPwd);
                admin.setSalt(salt);
                admin.setPhoneNo(admin.getPhoneNo() != null ? admin.getPhoneNo() : "");
                admin.setCreatedDate(new Date());

                if (adminService.saveOrUpdate(admin)) {
                    message = Strings.SAVED_SUCCESSFULLY;
                    success = true;
                } else message = Strings.FAILED_TO_SAVE;

            }

        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(admin != null ? admin.getId() : null, message, success);
    }

    @GetMapping(value = "login")
    public ResponseEntity<Response> validateLogin(
            @RequestParam String password,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String username,
            @RequestParam(defaultValue = Constant.SUPER_ADMIN) String role
    ) {

        var success = false;
        String message = null;

        /*if (Helper.isNullOrEmpty(username)) {
            message = "Please enter username";
            return Helper.getResponseEntity(null, message, success);
        }*/

        if (Helper.isNullOrEmpty(email)) {
            message = "Please enter email.";
            return Helper.getResponseEntity(null, message, success);
        }

        if (Helper.isNullOrEmpty(password)) {
            message = "Please enter password.";
            return Helper.getResponseEntity(null, message, success);
        }

        TokenResponse tokenResponse = null;
        var isUser = role.equalsIgnoreCase(Constant.USER);
        if (isUser) {

           /* User user = null;
            if (!Helper.isNullOrEmpty(username))
                user = userService.findByUsername(username); */

            User user = userService.findByEmail(email);
            if (user != null) {
                if (user.isBlocked()) {
                    message = "You have been blocked by admin. Please contact admin from contact us page.";
                    return Helper.getResponseEntity(null, message, success);
                }

                var salt = user.getSalt();
                var encryptedPwd = user.getPassword();
                var rawPwd = salt.concat(password);
                var finalPwd = DigestUtils.md5DigestAsHex(rawPwd.getBytes());

                if (finalPwd.equalsIgnoreCase(encryptedPwd)) {

                    //generate token
                    Token token = tokenService.findByUserId(user.getId());
                    if (token == null || token.getToken() == null) {
                        var tokenStr = UUID.randomUUID().toString();
                        var userToken = Token.builder()
                                .user(user)
                                .token(tokenStr)
                                .build();

                        tokenService.saveOrUpdate(userToken);
                        tokenResponse = TokenResponse.builder()
                                .id(user.getId())
                                .role(role)
                                .token(tokenStr)
                                .build();

                    } else {
                        tokenResponse = TokenResponse.builder()
                                .id(user.getId())
                                .role(role)
                                .token(token.getToken())
                                .build();
                    }

                    message = "Login successfully.";
                    success = true;
                } else message = "Password does not match.";
            } else message = "Username and password does not match.";

        } else {
            //Admin admin = adminService.findByUsernameAndRole(username, role);
            Admin admin = adminService.findByEmailAndRole(email, role);
            if (admin != null) {
                String salt = admin.getSalt();
                String encryptedPwd = admin.getPassword();

                String rawPwd = salt.concat(password);
                String finalPwd = DigestUtils.md5DigestAsHex(rawPwd.getBytes());

                if (finalPwd.equalsIgnoreCase(encryptedPwd)) {

                    //generate token
                    var token = tokenService.findByAdminId(admin.getId());
                    if (token == null || token.getToken() == null) {
                        var tokenStr = UUID.randomUUID().toString();
                        var tokenObj = Token.builder()
                                .admin(admin)
                                .token(tokenStr)
                                .build();

                        tokenService.saveOrUpdate(tokenObj);
                        tokenResponse = TokenResponse.builder()
                                .id(admin.getId())
                                .role(role)
                                .token(tokenStr)
                                .build();

                    } else {
                        tokenResponse = TokenResponse.builder()
                                .id(admin.getId())
                                .role(role)
                                .token(token.getToken())
                                .build();
                    }

                    message = "Login successfully.";
                    success = true;
                } else {
                    message = "Password does not match.";
                }
            } else message = "Username and password does not match.";
        }

        return Helper.getResponseEntity(tokenResponse, message, success);
    }

    @GetMapping(value = "reset-password")
    public ResponseEntity<Response> resetPassword(
            @RequestParam Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            @RequestParam(defaultValue = Constant.USER) String role
    ) {

        var success = false;
        String message = null;

        try {
            if (!Helper.isNullOrEmpty(oldPassword) && !Helper.isNullOrEmpty(newPassword) && !Helper.isNullOrEmpty(confirmPassword)) {
                var isUser = role.equalsIgnoreCase(Constant.USER);
                if (isUser) {
                    User user = userService.findById(id);
                    if (user != null) {
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
                                    message = Strings.RESET_SUCCESSFULLY;
                                    success = true;
                                } else
                                    message = Strings.FAILED_TO_SAVE_CHANGES;
                            } else
                                message = "New password and confirm password does not match. Please match both and try again.";
                        } else
                            message = "Old password does not match. Please tray again.";
                    } else message = "Failed to reset password.";
                } else {
                    Admin admin = adminService.findById(id);
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
                                    message = Strings.RESET_SUCCESSFULLY;
                                    success = true;
                                } else
                                    message = Strings.FAILED_TO_SAVE_CHANGES;
                            } else
                                message = "New password and confirm password does not match. Please match both and try again.";
                        } else
                            message = "Old password does not match. Please tray again.";
                    } else message = "Failed to reset password.";
                }
            } else
                message = "Please enter all the field and then try again.";
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }
}
