package com.kathford.wms.service;

import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.User;
import com.kathford.wms.domain.Waste;
import com.kathford.wms.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public boolean saveOrUpdate(Admin admin) {
        try {
            adminRepository.save(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public Admin findByUsername(String username) {
        return adminRepository.findAdminByUsername(username);
    }

    public Admin findByUserRole(String role) {
        return adminRepository.findAdminByUserRole(role);
    }

    public boolean isUsernameAlreadyAvailable(String username) {
        return adminRepository.isUsernameAlreadyAvailable(username);
    }

    public boolean isEmailAlreadyAvailable(String email) {
        return adminRepository.isEmailAlreadyAvailable(email);
    }

    public List<Admin> findAll(){
        return adminRepository.findAll();
    }

    public boolean deleteUser(Admin admin) {
        try {
            adminRepository.delete(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean blockOrUnblockedAdmin(Long id, boolean isBlocked) {
        return adminRepository.setAdminBlockedOrUnblocked(id, isBlocked);
    }

    public List<Admin> findAllByRole(String role) {
        return adminRepository.findAllByRole(role);
    }

    public Admin findByUsernameAndRole(String username, String role) {
        return adminRepository.findByUsernameAndRole(username, role);
    }

    public Admin findByEmailAndRole(String email, String role) {
        return adminRepository.findByEmailAndRole(email, role);
    }

    public boolean isUsernameAlreadyAvailable(String username, String role) {
        return adminRepository.isUsernameAlreadyAvailable(username, role);
    }

    public boolean isEmailAlreadyAvailable(String email, String role) {
        return adminRepository.isEmailAlreadyAvailable(email, role);
    }

}
