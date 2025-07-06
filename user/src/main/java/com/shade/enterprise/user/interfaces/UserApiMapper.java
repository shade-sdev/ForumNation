package com.shade.enterprise.user.interfaces;

import com.shade.enterprise.user.application.model.UserCreation;
import com.shade.enterprise.user.interfaces.model.UserCreationApiBean;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserApiMapper {

    UserCreation mapToDto(UserCreationApiBean userCreationApiBean);

}
