package com.danikvitek.MCPluginMarketplace.services;

import com.danikvitek.MCPluginMarketplace.model.User;
import com.danikvitek.MCPluginMarketplace.repositories.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Getter
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createAccount(User user) {
        return userRepository.save(user); // todo: implement
    }
}
