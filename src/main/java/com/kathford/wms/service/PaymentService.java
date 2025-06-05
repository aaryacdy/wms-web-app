package com.kathford.wms.service;

import com.kathford.wms.domain.Feedback;
import com.kathford.wms.domain.Payment;
import com.kathford.wms.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public boolean saveOrUpdate(Payment payment) {
        try {
            paymentRepository.save(payment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    public boolean delete(Payment payment) {
        try {
            paymentRepository.delete(payment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> findAllPayment() {
        return paymentRepository.findAll();
    }

    public List<Payment> findAllPaymentByCollectorId(Long loggedInAdminId) {
        return paymentRepository.findLoggedInCollectorPayment(loggedInAdminId);
    }

    public Integer countAllPayment(Long userId) {
        return paymentRepository.countAllPayment(userId);
    }

    public boolean approveOrRejectPayment(Long id, String status) {
        return paymentRepository.setApproveOrRejectPayment(id, status);
    }

}
