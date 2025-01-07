package com.jhome.user.domain;

import com.jhome.user.dto.EditRequest;
import com.jhome.user.dto.JoinRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "JHOME_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Enumerated(EnumType.STRING)
    private UserType type;

    private String name;
    private String email;
    private String phone;
    private String picture;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = UserStatus.ACTIVE;
        }
        if (this.role == null) {
            this.role = UserRole.ROLE_USER;
        }
        if (this.type == null) {
            this.type = UserType.JHOME_USER;
        }
    }

    public void encodePassword() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(this.password);
    }

    public void edit(EditRequest request) {
        if (request.getPassword() != null) {
            this.password = request.getPassword();
            this.encodePassword();
        }
        if (request.getEmail() != null) {
            this.email = request.getEmail();
        }
    }

    public void deactivate() {
        this.status = UserStatus.INACTIVE;
    }

    public static UserEntity createUser(JoinRequest request) {
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .email(request.getEmail())
                .build();
        user.encodePassword();
        return user;
    }

    public static UserEntity createJHomeUser(JoinRequest request) {
        UserEntity user = createUser(request);
        user.setType(UserType.JHOME_USER);
        return user;
    }

}
