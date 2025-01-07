package com.jhome.user.controller;

import com.jhome.user.common.exception.CustomException;
import com.jhome.user.common.response.ApiResponseCode;
import com.jhome.user.dto.JoinRequest;
import com.jhome.user.common.response.ApiResponse;
import com.jhome.user.dto.EditRequest;
import com.jhome.user.dto.SearchRequest;
import com.jhome.user.dto.UserResponse;
import com.jhome.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> join(
            @Valid @RequestBody JoinRequest request
    ) {
        UserResponse userResponse = userService.addUser(request);

        return ResponseEntity
                .created(URI.create("/api/users/"+userResponse.getUsername()))
                .body(ApiResponse.success(userResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> list(
            SearchRequest searchRequest
    ) {
        Pageable pageable = searchRequest.createPageable();

        Page<UserResponse> userResPage = userService.getUserResponsePage(searchRequest.getSearchKeyword(), pageable);

        return ResponseEntity
                .ok(ApiResponse.success(userResPage));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<?>> detail(
            @PathVariable("username") String username
    ) {
        UserResponse userResponse = userService.getUserResponse(username);

        return ResponseEntity
                .ok(ApiResponse.success(userResponse));
    }

    @PutMapping("/{username}")
    public ResponseEntity<ApiResponse<?>> edit(
            @PathVariable("username") String username,
            @Valid @RequestBody EditRequest request
    ) {
        UserResponse userResponse = userService.editUser(username, request);

        return ResponseEntity
                .created(URI.create("/api/users/"+userResponse.getUsername()))
                .body(ApiResponse.success(userResponse));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ApiResponse<?>> leave(
            @PathVariable("username") String username
    ) {
        userService.deactivateUser(username);

        return ResponseEntity
                .ok(ApiResponse.success());
    }

}
