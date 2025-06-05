package com.kathford.wms.service;

import com.kathford.wms.domain.Feedback;
import com.kathford.wms.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record FeedbackService(FeedbackRepository feedbackRepository) {
    public boolean saveOrUpdate(Feedback feedback) {
        try {
            feedbackRepository.save(feedback);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public boolean deleteFeedback(Feedback feedback) {
        try {
            feedbackRepository.delete(feedback);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> findAllFeedbackByUserId(Long loggedInUser) {
        return feedbackRepository.findLoggedInUserFeedback(loggedInUser);
    }

    public Integer countAllFeedback(Long userId) {
        return feedbackRepository.countAllFeedback(userId);
    }
}
