package seonjiwon.ticketsync.domain.auth.converter;

import seonjiwon.ticketsync.domain.auth.dto.SignUpResponse;
import seonjiwon.ticketsync.domain.auth.dto.UserDto;
import seonjiwon.ticketsync.domain.user.entity.User;

public class UserConverter {
    public static SignUpResponse toSignUpResponse(User user) {
        return SignUpResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
