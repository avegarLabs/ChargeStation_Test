package org.avegarlabs.userservice.services;

import org.avegarlabs.userservice.dto.UserListItem;
import org.avegarlabs.userservice.dto.UserModel;
import org.avegarlabs.userservice.models.User;
import org.avegarlabs.userservice.models.enums.UserRoles;
import org.avegarlabs.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<UserListItem> allUsers() {
        return repository.findAll().stream().map(this::mapUserToUserListItem).toList();
    }

    public UserListItem persistUser(UserModel model) {
        if (repository.existsByUsername(model.getUsername()) || repository.existsByEmail(model.getEmail()))
            throw new RuntimeException("Username or email already in use");
        User user = mapUserModelToUser(model);
        repository.save(user);
        return mapUserToUserListItem(user);
    }

    public void deleteUser(String id){
        repository.deleteById(id);
    }


    private UserListItem mapUserToUserListItem(User user) {
        List<String> roles = user.getRoleList().stream().map(Enum::name).toList();
        return UserListItem.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleList(roles)
                .build();
    }

    private User mapUserModelToUser(UserModel model) {
        List<UserRoles> userRolesList = model.getRoleList().stream().map(UserRoles::valueOf).toList();
        return User.builder()
                .name(model.getName())
                .username(model.getUsername())
                .email(model.getEmail())
                .password(passwordEncoder.encode(model.getPassword()))
                .roleList(userRolesList)
                .build();
    }
}
