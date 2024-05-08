package ru.samgtu.tasker.mapper;

import org.mapstruct.Mapper;
import ru.samgtu.tasker.api.dto.request.RegistrationRequestDTO;
import ru.samgtu.tasker.entity.UserEntity;

@Mapper
public interface UserMapper {
    UserEntity toEntity(RegistrationRequestDTO request);
}
