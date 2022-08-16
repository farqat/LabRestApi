package kz.spring.restapi.repo;

import kz.spring.restapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepo extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmail(String email);
}
