package com.jhome.user.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode {

    SUCCESS(0,"Success"),
    FAIL(-1,"Unknown Failure"),

    // xxx: Common Exceptions
    REQUEST_ARGS_INVALID(-100, "Request Arguments Invalid"),
    REQUEST_SORT_FIELD_INVALID(-101, "Request Sort Field Invalid"),
    LOGIN_FAILURE(-200, "Login Failure"),
    INVALID_TOKEN(-201, "Invalid Token"),
    DATA_ACCESS_ERROR(-300, "Data Access Failure"),

    // 1xxx: User Exceptions
    USER_ALREADY_EXIST(-1001, "User Already Exist"),
    USER_NOT_FOUND(-1002, "User Not Found"),
    ;

    private final int code;
    private final String message;
}
