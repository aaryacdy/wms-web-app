package com.kathford.wms.repository;

import com.kathford.wms.custom.PaymentCustom;
import com.kathford.wms.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentCustom {
}
