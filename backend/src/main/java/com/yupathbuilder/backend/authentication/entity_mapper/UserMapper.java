package com.yupathbuilder.backend.authentication.entity_mapper;

import com.yupathbuilder.backend.authentication.entity.UserEntity;
import com.yupathbuilder.backend.authentication.model.User;

/**
 * Converts between persistence entities and the legacy authentication domain
 * model used by parts of the codebase.
 */
public class UserMapper {

    private UserMapper() {}

    /**
     * Maps a persisted user entity to the in-memory authentication model.
     */
    public static User toModel(UserEntity entity) {

        if (entity == null) return null;

        return new User(
                entity.getId(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getProgramId()
        );
    }

    /**
     * Maps the in-memory authentication model back to the persistence entity
     * shape expected by the repository layer.
     */
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
