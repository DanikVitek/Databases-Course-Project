package com.danikvitek.MCPluginMarketplace.service;

import com.danikvitek.MCPluginMarketplace.api.dto.FullUserDto;
import com.danikvitek.MCPluginMarketplace.api.dto.SimpleUserDto;
import com.danikvitek.MCPluginMarketplace.data.model.entity.Plugin;
import com.danikvitek.MCPluginMarketplace.data.model.entity.User;
import com.danikvitek.MCPluginMarketplace.data.repository.PluginRepository;
import com.danikvitek.MCPluginMarketplace.data.repository.UserRepository;
import com.danikvitek.MCPluginMarketplace.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public final class UserService {
    private final UserRepository userRepository;
    private final PluginRepository pluginRepository;

    public @NotNull User fetchById(long id) throws IllegalArgumentException {
        if (id >= 1)
            return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        else throw new IllegalArgumentException("ID must be >= 1");
    }

    public @NotNull Page<User> fetchPage(int page, int size) throws IllegalArgumentException {
        if (page < 0) throw new IllegalArgumentException("Page index must be >= 0");
        else if (size < 1) throw new IllegalArgumentException("Page size must be >= 1");
        else return userRepository.findAll(Pageable.ofSize(size).withPage(page));
    }

    public User fetchByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
    
    public Set<Plugin> fetchAuthoredPluginsById(long userId) throws IllegalArgumentException {
        if (userId >= 1) return pluginRepository.findAuthoredPlugins(userId);
        else throw new IllegalArgumentException("User id must be >= 1");
    }


    public SimpleUserDto userToSimpleDto(@NotNull User user) {
        return SimpleUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .build();
    }

    public FullUserDto userToFullDto(@NotNull User user) {
        return FullUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .password(user.getPassword())
                .registrationTime(user.getRegistrationTime())
                .build();
    }

    public SimpleUserDto fullUserDtoToSimpleUserDto(@NotNull FullUserDto fullDto) {
        return SimpleUserDto.builder()
                .id(fullDto.getId())
                .username(fullDto.getUsername())
                .firstName(fullDto.getFirstName())
                .lastName(fullDto.getLastName())
                .email(fullDto.getEmail())
                .role(fullDto.getRole())
                .build();
    }
}
