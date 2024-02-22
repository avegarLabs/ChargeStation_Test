package org.avegarlabs.userservice.dto;

import lombok.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserModel {
    private String name;
    private String username;
    private String email;
    private String password;
    private List<String> roleList;
}
