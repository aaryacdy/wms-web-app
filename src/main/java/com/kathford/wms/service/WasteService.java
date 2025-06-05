package com.kathford.wms.service;

import com.kathford.wms.domain.Waste;
import com.kathford.wms.repository.WasteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record WasteService(WasteRepository wasteRepository) {

    public boolean saveOrUpdate(Waste waste) {
        try {
            wasteRepository.save(waste);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Waste findById(Long id) {
        return wasteRepository.findById(id).orElse(null);
    }

    public boolean deleteWaste(Waste waste) {
        try {
            wasteRepository.delete(waste);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Waste> findAll() {
        return wasteRepository.findAll();
    }

    public List<Waste> findAllWasteByUserId(Long loggedInUser) {
        return wasteRepository.findLoggedInUserWaste(loggedInUser);
    }

    public List<Waste> filterWasteAsRequested(String status, String fromDate, String toDate, Long loggedInUser) {
        return wasteRepository.filterWasteAsRequested(status, fromDate, toDate, loggedInUser);
    }

    public Integer countAllWaste(Long userId) {
        return wasteRepository.countAllWaste(userId);
    }

    public List<Waste> filterWasteAsRequested(String status, String address, Long loggedInUser, boolean showPaidWastes) {
        return wasteRepository.filterWasteByStatusAndAddress(status, address, loggedInUser, showPaidWastes);
    }

}
