package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.data.model.entity.BannedUser;
import com.danikvitek.MCPluginMarketplace.data.repository.BannedUserRepository;
import com.danikvitek.MCPluginMarketplace.data.repository.UserRepository;
import com.danikvitek.MCPluginMarketplace.util.exception.UserIsNotBannedException;
import com.danikvitek.MCPluginMarketplace.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public final class BannedUserService {
    private final BannedUserRepository bannedUserRepository;
    private final UserRepository userRepository;

    public BannedUser fetchById(long userId) throws UserNotFoundException, UserIsNotBannedException {
        userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return bannedUserRepository
                .findById(userId)
                .orElseThrow(UserIsNotBannedException::new);
    }
    
    public @NotNull BannedUser create(long userId, String reason) throws UserNotFoundException {
        userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        BannedUser bannedUser = BannedUser.builder()
                .userId(userId)
                .reason(reason)
                .build();
        return bannedUserRepository.save(bannedUser);
    }
    
    public void update(long userId, String reason) throws UserIsNotBannedException {
        BannedUser bannedUser = bannedUserRepository
                .findById(userId)
                .orElseThrow(UserIsNotBannedException::new);
        bannedUser.setReason(reason);
        bannedUserRepository.save(bannedUser);
    }
    
    public void delete(long userId) {
        bannedUserRepository.deleteById(userId);
    }
}
