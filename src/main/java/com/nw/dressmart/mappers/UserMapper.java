package com.nw.dressmart.mappers;

import com.nw.dressmart.dto.RegisterRequestDto;
import com.nw.dressmart.dto.UserDto;
import com.nw.dressmart.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User registerRequestDtoToUser(RegisterRequestDto registerRequestDto);

    UserDto UserToUserDto(User user);
}
