package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.config.security.UserDetailsImpl;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.data.repository.BannedUserRepository;
import com.danikvitek.MCPluginMarketplace.data.repository.UserRepository;
import com.danikvitek.MCPluginMarketplace.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public final class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final BannedUserRepository bannedUserRepository;

    @Override
    public @NotNull UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return new UserDetailsImpl(user, bannedUserRepository);
    }
}
