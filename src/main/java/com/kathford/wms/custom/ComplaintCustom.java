package com.kathford.wms.custom;

import com.kathford.wms.domain.Complaint;

import java.util.List;

public interface ComplaintCustom {

    List<Complaint> findLoggedInUserComplaint(Long loggedInUserId);

    Integer countAllComplaint(Long userId);

}
