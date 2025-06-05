package com.kathford.wms.repository;

import com.kathford.wms.custom.FeedbackCustom;
import com.kathford.wms.domain.Complaint;
import com.kathford.wms.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>, FeedbackCustom {
}
