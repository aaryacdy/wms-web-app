package com.kathford.wms.custom;

import com.kathford.wms.domain.Admin;
import com.kathford.wms.domain.Waste;

import java.util.List;

public interface AdminRepositoryCustom {

    Admin findAdminByUsername(String username);

    Admin findAdminByUserRole(String role);

    boolean isUsernameAlreadyAvailable(String username);

    boolean isEmailAlreadyAvailable(String email);

    boolean setAdminBlockedOrUnblocked(Long id, boolean isBlocked);

    List<Admin> findAllByRole(String role);

    Admin findByUsernameAndRole(String username, String role);

    Admin findByEmailAndRole(String email, String role);

    boolean isUsernameAlreadyAvailable(String username, String role);

    boolean isEmailAlreadyAvailable(String email, String role);

}
