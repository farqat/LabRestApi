package kz.spring.restapi.repo;

import kz.spring.restapi.model.AddressEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressEmployeeRepository extends JpaRepository<AddressEmployee, Long> {
}