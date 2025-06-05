package com.kathford.wms.repository;

import com.kathford.wms.custom.AdminRepositoryCustom;
import com.kathford.wms.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Admin, Long>, AdminRepositoryCustom {

//    @Query("select count(id) > 0 from tbl_admin where username = :username")
//     boolean isUsernameAlreadyAvailable(String username);

}
