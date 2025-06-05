package com.kathford.wms.custom;


import com.kathford.wms.domain.Feedback;

import java.util.List;

public interface FeedbackCustom {

    List<Feedback> findLoggedInUserFeedback(Long loggedInUserId);

    Integer countAllFeedback(Long userId);

}
