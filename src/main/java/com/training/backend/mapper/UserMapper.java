package com.training.backend.mapper;

import com.training.backend.entity.User;
import com.training.backend.model.MRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MRegisterResponse toRegisterResponse(User user);

}
