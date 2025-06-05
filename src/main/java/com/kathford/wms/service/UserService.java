package com.kathford.wms.service;

import com.kathford.wms.domain.User;
import com.kathford.wms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean saveOrUpdate(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteUser(User user) {
        try {
            userRepository.delete(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isUsernameAlreadyAvailable(String username) {
        return userRepository.isUsernameAlreadyAvailable(username);
    }

    public boolean isEmailAlreadyAvailable(String email) {
        return userRepository.isEmailAlreadyAvailable(email);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public boolean blockOrUnblockedUser(Long id, boolean isBlocked) {
        return userRepository.setUserBlockedOrUnblocked(id, isBlocked);
    }
}