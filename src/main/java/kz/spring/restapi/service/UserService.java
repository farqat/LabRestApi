package kz.spring.restapi.service;

import kz.spring.restapi.model.Role;
import kz.spring.restapi.model.Users;

import java.util.List;

public interface UserService {
    Users saveUser(Users users);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    Users getUser(String email);
    List<Users> getUsers();
}
