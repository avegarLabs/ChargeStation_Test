package org.avegarlabs.userservice.services;

import org.avegarlabs.userservice.models.User;
import org.avegarlabs.userservice.models.models_implements.UserInfoUserDetails;
import org.avegarlabs.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByUsername(username);
        if (userInfo.isEmpty())
            throw new UsernameNotFoundException(" User not found " + username);
        return UserInfoUserDetails.build(userInfo.get());
    }
}
