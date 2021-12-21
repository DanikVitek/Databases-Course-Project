package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.config.security.UserPrincipal;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.data.repository.UserRepository;
import com.danikvitek.MCPluginMarketplace.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return new UserPrincipal(user);
    }
}
