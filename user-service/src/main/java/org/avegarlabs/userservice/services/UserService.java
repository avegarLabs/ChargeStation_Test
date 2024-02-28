package org.avegarlabs.userservice.services;

import org.avegarlabs.userservice.dto.ChargeStationUseResponse;
import org.avegarlabs.userservice.dto.UserListItem;
import org.avegarlabs.userservice.dto.UserModel;
import org.avegarlabs.userservice.models.User;
import org.avegarlabs.userservice.models.enums.UserRoles;
import org.avegarlabs.userservice.repositories.UserRepository;
import org.avegarlabs.userservice.util.CreateMoniker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
   ChargeStationClientService clientService;


    @Autowired
    CreateMoniker moniker;

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

    public UserListItem getById(String id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty())
            throw new RuntimeException(" User with id: " + id + " not found");
        return mapUserToUserListItem(user.get());
    }

    public UserListItem getByMoniker(String moniker){
        Optional<User> user = repository.findByMoniker(moniker);
        if (user.isEmpty())
            throw new RuntimeException(" User with moniker: " + moniker + " not found");

        return mapUserToUserListItem(user.get());
    }

    @Cacheable(value = "activeUser", unless = "#result == null")
    public UserListItem getByName(String username){
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty())
            throw new RuntimeException(" User with name: " + username + " not found");

        return mapUserToUserListItem(user.get());
    }

    public void deleteUser(String id){
        repository.deleteById(id);
    }


    public List<ChargeStationUseResponse> getChargesByUser(String id){
        return clientService.fecthUserActivity(id);
    }


    private UserListItem mapUserToUserListItem(User user) {
        List<String> roles = user.getRoleList().stream().map(Enum::name).toList();
        return UserListItem.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleList(roles)
                .moniker(user.getMoniker())
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
                .moniker(moniker.createMoniker(model.getName()))
                .build();
    }
}
