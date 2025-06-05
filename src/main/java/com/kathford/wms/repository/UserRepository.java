package com.kathford.wms.repository;

import com.kathford.wms.custom.UserRepositoryCustom;
import com.kathford.wms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
