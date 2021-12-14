package com.danikvitek.MCPluginMarketplace.data.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    user((byte) 0),
    moderator((byte) 1),
    admin((byte) 2);

    private final byte id;
}
