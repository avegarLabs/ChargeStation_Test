package org.avegarlabs.chargestationservice.services.client;

import org.avegarlabs.chargestationservice.config.AuthInterceptor;
import org.avegarlabs.chargestationservice.dto.UserListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class UserServiceClient {
    @Autowired
    private RestTemplate template;

    @Autowired
    AuthInterceptor interceptor;

    public UserListItem fecthUserData(String userId, String authorizationToken){
        interceptor.setToken(authorizationToken);
        template.setInterceptors(Collections.singletonList(interceptor));
        return template.getForObject("http://user-service/api/user/check?id="+userId, UserListItem.class);
    }
}
