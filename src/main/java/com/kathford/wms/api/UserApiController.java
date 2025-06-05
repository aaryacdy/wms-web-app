package com.kathford.wms.api;

import com.kathford.wms.config.constant.Constant;
import com.kathford.wms.domain.Feedback;
import com.kathford.wms.domain.User;
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

import javax.mail.Multipart;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping(value = "findall")
    public ResponseEntity<Response> getAllUsers() {

        var success = false;
        String messsage = null;
        List<User> users = null;

        try {
            users = userService.findAllUser();
            if (!Helper.isNullOrEmpty(users))
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            messsage = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(users, messsage, success);
    }

    @PostMapping(value = "signup")
    public ResponseEntity<Response> saveSignup(
            @ModelAttribute User user,
            @RequestParam(required = false) MultipartFile attachment
    ) {
        String message;
        var success = false;

        try {
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
            user.setRole(!Helper.isNullOrEmpty(user.getRole()) ? user.getRole() : Constant.USER);
            user.setUsername(username.toLowerCase());
            user.setCreatedDate(new Date());

            if (userService.saveOrUpdate(user)) {
                message = Strings.SAVED_SUCCESSFULLY;
                success = true;
            } else message = Strings.FAILED_TO_SAVE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "login")
    public ResponseEntity<Response> viewDashboardIfUserLoggedIn(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam String password
    ) {
        String message;
        var success = false;

        log.info("Username: {}, Email : {}", username, email);
        if (Helper.isNullOrEmpty(username) && Helper.isNullOrEmpty(email)) {
            message = "Please enter username or email.";
            return Helper.getResponseEntity(null, message, success);
        }

        if (Helper.isNullOrEmpty(password)) {
            message = "Please enter password.";
            return Helper.getResponseEntity(null, message, success);
        }

        User user = null;

        if (!Helper.isNullOrEmpty(username))
            user = userService.findByUsername(username);
        else
            user = userService.findByEmail(email);

        try {
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
                    message = "Login successfully.";
                    success = true;
                } else message = "Password does not match.";
            } else message = "Username and password does not match.";
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @DeleteMapping(value = "{id}/delete")
    public ResponseEntity<Response> deleteUser(
            @PathVariable Long id
    ) {
        var success = false;
        String message;

        User user = userService.findById(id);
        if (user == null) {
            message = Strings.LINK_BROKEN;
            return Helper.getResponseEntity(null, message, success);
        }

        try {
            if (userService.deleteUser(user)) {
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "unblock")
    public ResponseEntity<Response> unBlockUser(@RequestParam Long id) {

        String message;
        var success = false;

        try {
            success = userService.blockOrUnblockedUser(id, false);
            if (success)
                message = Strings.USER_UNBLOCKED_SUCCESSFULLY;
            else message = Strings.FAILED_TO_UNBLOCKED_USER;

        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "block")
    public ResponseEntity<Response> unblockUser(@RequestParam Long id) {

        var success = false;
        String message;

        try {
            success = userService.blockOrUnblockedUser(id, true);
            if (success)
                message = Strings.USER_BLOCKED_SUCCESSFULLY;
            else message = Strings.FAILED_TO_BLOCKED_USER;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<Response> resetPassword(
            @RequestParam Long userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {
        var success = false;
        String message;
        try {
            if (!Helper.isNullOrEmpty(oldPassword) && !Helper.isNullOrEmpty(newPassword) && !Helper.isNullOrEmpty(confirmPassword)) {
                User user = userService.findById(userId);
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
            } else message = "Please enter all the field and then try again.";
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Response> deleteUserV2(
            @RequestParam Long id
    ) {
        var success = false;
        String message;

        User user = userService.findById(id);
        if (user == null) {
            message = Strings.LINK_BROKEN;
            return Helper.getResponseEntity(null, message, success);
        }

        try {
            if (userService.deleteUser(user)) {
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @PostMapping(value = "unblock")
    public ResponseEntity<Response> unBlockUserV2(@RequestParam Long id) {

        String message;
        var success = false;

        try {
            success = userService.blockOrUnblockedUser(id, false);
            if (success)
                message = Strings.USER_UNBLOCKED_SUCCESSFULLY;
            else message = Strings.FAILED_TO_UNBLOCKED_USER;

        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @PostMapping(value = "block")
    public ResponseEntity<Response> unblockUserV2(@RequestParam Long id) {

        var success = false;
        String message;

        try {
            success = userService.blockOrUnblockedUser(id, true);
            if (success)
                message = Strings.USER_BLOCKED_SUCCESSFULLY;
            else message = Strings.FAILED_TO_BLOCKED_USER;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }
}