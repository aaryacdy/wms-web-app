package com.kathford.wms.custom;

import com.kathford.wms.domain.Waste;

import java.util.List;

public interface WasteRepositoryCustom {
    List<Waste> findLoggedInUserWaste(Long loggedInUserId);

    List<Waste> filterWasteAsRequested(String status, String fromDate, String toDate, Long loggedInUser);

    Integer countAllWaste(Long userId);

    List<Waste> filterWasteByStatusAndAddress(String status, String address, Long loggedInUser, boolean showPaidWaste);
}
