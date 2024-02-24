package org.avegarlabs.chargestationservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserListItem {
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private List<String> roleList;
    private String moniker;
}
