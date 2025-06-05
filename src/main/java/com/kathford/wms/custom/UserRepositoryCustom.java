package com.kathford.wms.custom;

import com.kathford.wms.domain.User;

public interface UserRepositoryCustom {

    User findByUsername(String username);

    User findByEmail(String email);

    boolean setUserBlockedOrUnblocked(Long id, boolean isBlocked);

    boolean isUsernameAlreadyAvailable(String username);

    boolean isEmailAlreadyAvailable(String email);

}
