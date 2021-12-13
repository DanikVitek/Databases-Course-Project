package com.danikvitek.MCPluginMarketplace.repo.model.converter;

import com.danikvitek.MCPluginMarketplace.repo.model.entity.User;

import javax.persistence.Converter;
import javax.persistence.AttributeConverter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<User.Role, String> {

    @Override
    public String convertToDatabaseColumn(User.Role role) {
        return role.toString();
    }

    @Override
    public User.Role convertToEntityAttribute(String string) {
        return User.Role.valueOf(string);
    }
}
