package com.jhome.user.service;

import com.jhome.user.common.property.DatetimeProperty;
import com.jhome.user.common.response.ApiResponseCode;
import com.jhome.user.domain.UserEntity;
import com.jhome.user.dto.JoinRequest;
import com.jhome.user.dto.EditRequest;
import com.jhome.user.dto.UserResponse;
import com.jhome.user.common.exception.CustomException;
import com.jhome.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DatetimeProperty datetimeProperty;


    @Transactional
    public UserResponse addUser(JoinRequest request) {
        if(isUserExist(request.getUsername())) {
            throw new CustomException(ApiResponseCode.USER_ALREADY_EXIST);
        }
        UserEntity newUser = userRepository.save(
                UserEntity.createJHomeUser(request));
        return UserResponse.build(newUser, datetimeProperty);
    }

    public Boolean isUserExist(String username) {
        return userRepository.existsByUsername(username);
    }

    public Page<UserResponse> getUserResponsePage(String searchKeyword, Pageable pageable) {
        Page<UserEntity> userPage;
        if(searchKeyword == null || searchKeyword.isBlank()){
            userPage = userRepository.findAll(pageable);
        } else {
            userPage = userRepository.findByKeyword(searchKeyword, pageable);
        }
        return userPage.map(user -> UserResponse.build(user, datetimeProperty));
    }

    public UserResponse getUserResponse(String username) {
        UserEntity user = getUserOrThrow(username);
        return UserResponse.build(user, datetimeProperty);
    }

    @Transactional
    public UserResponse editUser(String username, EditRequest request) {
        UserEntity user = getUserOrThrow(username);
        user.edit(request);
        UserEntity editedUser = userRepository.save(user);
        return UserResponse.build(editedUser, datetimeProperty);
    }

    @Transactional
    public void deactivateUser(String username) {
        UserEntity user = getUserOrThrow(username);
        user.deactivate();
        userRepository.save(user);
    }

    public UserEntity getUserOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ApiResponseCode.USER_NOT_FOUND));
    }

}