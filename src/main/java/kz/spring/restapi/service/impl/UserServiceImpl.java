package kz.spring.restapi.service.impl;

import kz.spring.restapi.model.Role;
import kz.spring.restapi.model.Users;
import kz.spring.restapi.repo.RoleRepo;
import kz.spring.restapi.repo.UserRepo;
import kz.spring.restapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepo.findByEmail(email);
        if(users == null){
            log.info("Email not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }else{
            log.info("Email found in database " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        users.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(users.getEmail(), users.getPassword(), authorities);
    }

    @Override
    public Users saveUser(Users users) {
        log.info("Saving new user {} to database", users.getName());
        users.setPassword(passwordEncoder.encode(users.getPassword()));

        return userRepo.saveAndFlush(users);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to database", role.getName());
        return roleRepo.saveAndFlush(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding new role {} to user {}", roleName, email);
        Users users = userRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        users.getRole().add(role);

    }

    @Override
    public Users getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public List<Users> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }


}
