package com.wolfnine.backend.entity.dto.user;

import com.wolfnine.backend.entity.UserRole;
import com.wolfnine.backend.entity.entityEnum.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserInfoDto {
    private long id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private UserStatus status;
    private List<UserRole> userRoles;
}
