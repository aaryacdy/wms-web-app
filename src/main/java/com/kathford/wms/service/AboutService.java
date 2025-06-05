package com.kathford.wms.service;

import com.kathford.wms.domain.AboutUs;
import com.kathford.wms.repository.AboutUsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AboutService {

    private final AboutUsRepository aboutUsRepository;

    public boolean saveOrUpdate(AboutUs aboutUs) {
        try {
            aboutUsRepository.save(aboutUs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public AboutUs findById(Long id) {
        return aboutUsRepository.findById(id).orElse(null);
    }
}
