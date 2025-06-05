package com.kathford.wms.service;

import com.kathford.wms.domain.Token;
import com.kathford.wms.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public boolean saveOrUpdate(Token token) {
        try {
            tokenRepository.save(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Token findByUserId(Long userId) {
        return tokenRepository.findByUserId(userId);
    }

    public Token findByAdminId(Long adminId) {
        return tokenRepository.findByAdminId(adminId);
    }
}
