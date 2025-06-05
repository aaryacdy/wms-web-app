package com.kathford.wms.custom;

import com.kathford.wms.domain.Payment;

import java.util.List;

public interface PaymentCustom {

    List<Payment> findLoggedInCollectorPayment(Long loggedInAdminId);

    Integer countAllPayment(Long userId);

    boolean setApproveOrRejectPayment(Long id, String status);
}
