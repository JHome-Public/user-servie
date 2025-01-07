package com.jhome.user.dto;

import com.jhome.user.common.property.DatetimeProperty;
import com.jhome.user.domain.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@ToString
public class UserResponse {

    private final String username;
    private final String role;
    private final String type;
    private final String name;
    private final String email;
    private final String phone;
    private final String picture;
    private final String createAt;
    private final String updateAt;

    public static UserResponse build(UserEntity user, DatetimeProperty datetimeProperty){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datetimeProperty.getFormat());
        return UserResponse.builder()
                .username(user.getUsername())
                .role(user.getRole().name())
                .type(user.getType().name())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .picture(user.getPicture())
                .createAt(user.getCreatedAt().format(formatter))
                .updateAt(user.getUpdatedAt().format(formatter))
                .build();
    }

}
