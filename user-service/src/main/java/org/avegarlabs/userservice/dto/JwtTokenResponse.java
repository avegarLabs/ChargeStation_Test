package org.avegarlabs.userservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtTokenResponse {
    private String token;
}
