package org.avegarlabs.userservice.models;

import lombok.*;
import org.avegarlabs.userservice.models.enums.UserRoles;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private List<UserRoles> roleList;
    private String moniker;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
