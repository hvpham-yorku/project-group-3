package com.yupathbuilder.backend.authentication.entity_mapper;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.model.User;

public class UserMapper {

    private UserMapper() {}

    public static User toModel(UserEntity entity) {

        if (entity == null) return null;

        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getProgramId()
        );
    }

    public static UserEntity toEntity(User model) {

        if (model == null) return null;

        UserEntity entity = new UserEntity(
                model.getEmail(),
                model.getPasswordHash(),
                model.getProgramId()
        );

        return entity;
    }
}