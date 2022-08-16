package kz.spring.restapi.repo;

import kz.spring.restapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<Users, Long> {

    @Query("FROM Users WHERE email=:email")
    Users findByEmail(@Param("email") String email);
}
