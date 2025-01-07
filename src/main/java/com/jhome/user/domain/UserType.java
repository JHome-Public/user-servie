package com.jhome.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    JHOME_USER,
    OAUTH2_USER;

}
