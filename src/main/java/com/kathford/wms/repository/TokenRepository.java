package com.kathford.wms.repository;

import com.kathford.wms.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findById(Long id);

    Token findByUserId(@Param("userId") Long userId);

    Token findByAdminId(@Param("adminId") Long adminId);

}
