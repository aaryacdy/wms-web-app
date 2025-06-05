package com.kathford.wms.api;

import com.kathford.wms.domain.BaseModel;
import com.kathford.wms.domain.Feedback;
import com.kathford.wms.domain.User;
import com.kathford.wms.service.FeedbackService;
import com.kathford.wms.util.Helper;
import com.kathford.wms.util.Response;
import com.kathford.wms.util.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/feedback")
@RequiredArgsConstructor
public class FeedbackApiController extends BaseModel {

    private final FeedbackService feedbackService;

    @PostMapping(value = "save")
    public ResponseEntity<Response> saveFeedback(
            @RequestParam(required = false) Long userId,
            @ModelAttribute Feedback feedbackNew
    ) {

        var success = false;
        String message = null;
        try {

            feedbackNew.setCreatedBy(new User(userId));
            feedbackNew.setCreatedDate(new Date());

            if (feedbackService.saveOrUpdate(feedbackNew)) {
                success = true;
                message = Strings.SAVED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_SAVE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Response> deleteFeedback(
            @RequestParam Long id
    ) {
        var success = false;
        String message = null;
        try {

            var complaint = feedbackService.findById(id);
            if (feedbackService.deleteFeedback(complaint)) {
                success = true;
                message = Strings.DELETED_SUCCESSFULLY;
            } else message = Strings.FAILED_TO_DELETE;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(null, message, success);
    }

    @GetMapping(value = "view")
    public ResponseEntity<Response> viewFeedback(
            @RequestParam Long id
    ) {

        var success = false;
        String message = null;
        Feedback feedback = null;
        try {
            feedback = feedbackService.findById(id);
            if (feedback != null)
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(feedback, message, success);
    }

    @GetMapping(value = "filter-user-feedback")
    public ResponseEntity<Response> viewLoggedInUserFeedback(
            @RequestParam Long userId
    ) {
        var success = false;
        String message = null;
        List<Feedback> feedbacks = null;
        try {

            feedbacks = feedbackService.findAllFeedbackByUserId(userId);
            if (!Helper.isNullOrEmpty(feedbacks))
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(feedbacks, message, success);
    }

    /*used for admin and collector side*/
    @GetMapping(value = "findall")
    public ResponseEntity<Response> viewAllFeedback(
    ) {
        var success = false;
        String message = null;
        List<Feedback> feedbacks = null;
        try {

            feedbacks = feedbackService.findAll();
            if (!Helper.isNullOrEmpty(feedbacks))
                success = true;
        } catch (Exception e) {
            e.printStackTrace();
            message = Strings.SOMETHING_WENT_WRONG;
        }

        return Helper.getResponseEntity(feedbacks, message, success);
    }
}