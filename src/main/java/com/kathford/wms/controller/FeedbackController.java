package com.kathford.wms.controller;

import com.kathford.wms.domain.BaseModel;
import com.kathford.wms.domain.Complaint;
import com.kathford.wms.domain.Feedback;
import com.kathford.wms.service.FeedbackService;
import com.kathford.wms.service.UserService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.MessageType;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "feedback")
@RequiredArgsConstructor
public class FeedbackController extends BaseModel {

    private final Helper helper;
    private final FeedbackService feedbackService;

    @GetMapping(value = "")
    public String viewFeedback(HttpSession session, Model model,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate admin login
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToAdminLogin(redirectAttributes);

        Helper.setTitle(model, "Feedback");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("feedbacks", feedbackService.findAll());

        return "feedback/all-feedback";
    }

    @GetMapping(value = "collector")
    public String viewFeedbackToCollector(HttpSession session, Model model,
                                           HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate admin login
        if (!Helper.checkAdminLogin(session))
            return Helper.redirectToCollectorLogin(redirectAttributes);

        Helper.setTitle(model, "Feedback");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("feedbacks", feedbackService.findAll());

        return "feedback/collector-feedback";
    }

    @GetMapping(value = "add")
    public String addFeedback(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes,
                               Model model) {

        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Helper.setTitle(model, "Add Feedback");
        model.addAttribute("baseUrl", request.getContextPath());

        return "feedback/add";
    }

    @PostMapping(value = "save-update")
    public String saveFeedback(HttpSession session, RedirectAttributes redirectAttributes,
                                @ModelAttribute Feedback feedback) {

        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        boolean hasFeedback = feedback.getId() != null;

        try {

            feedback.setCreatedDate(Helper.getCurrentDateTime());
            feedback.setCreatedBy(helper.getLoggedInUser(session));
            if (feedbackService.saveOrUpdate(feedback))
                Helper.setFlashMessage(MessageType.MESSAGE, hasFeedback ? Strings.UPDATED_SUCCESSFULLY : Strings.SAVED_SUCCESSFULLY, redirectAttributes);
            else
                Helper.setFlashMessage(MessageType.MESSAGE, hasFeedback ? Strings.FAILED_TO_UPDATE : Strings.FAILED_TO_SAVE, redirectAttributes);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/feedback/user";
    }

    @GetMapping(value = "user")
    public String viewUserFeedback(HttpSession session, Model model,
                                   HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //validate user login
        if (!Helper.checkUserLogin(session))
            return Helper.redirectToUserLogin(redirectAttributes);

        Helper.setTitle(model, "Feedback");
        model.addAttribute("baseUrl", request.getContextPath());
        model.addAttribute("feedbacks", feedbackService.findAllFeedbackByUserId(Helper.getLoggedInUserId(session)));

        return "feedback/feedback";
    }
}