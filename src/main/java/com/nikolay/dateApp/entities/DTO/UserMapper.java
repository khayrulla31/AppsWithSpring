package com.nikolay.dateApp.entities.DTO;

import com.nikolay.dateApp.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserResponse fromUserToUserResponse(User user);
}
