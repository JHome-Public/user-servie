package com.jhome.user.dto;

import com.jhome.user.controller.PasswordPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EditRequest {

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @PasswordPattern(message = "Password must contain at least one letter, one number, and one special character")
    private String password;

    @Email(message = "Invalid email format")
    private String email;

}
