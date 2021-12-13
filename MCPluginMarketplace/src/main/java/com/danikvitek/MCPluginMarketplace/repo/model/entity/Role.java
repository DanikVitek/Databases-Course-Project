package com.danikvitek.MCPluginMarketplace.repo.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public enum Role {
    user((byte) 0),
    moderator((byte) 1),
    admin((byte) 2);

    private final byte id;
}
