package kz.spring.restapi.service.impl;

import kz.spring.restapi.exception.EmailExists;
import kz.spring.restapi.exception.EmployeeNotFoundException;
import kz.spring.restapi.model.AddressEmployee;
import kz.spring.restapi.model.Employee;
import kz.spring.restapi.model.EmployeeDto;
import kz.spring.restapi.repo.EmployeeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"employeeCache"})
public class EmployeeService {
    private final EmployeeRepo employeeRepo;
    private final MessageSource messageSource;

    @Cacheable("employeeCache")
    public List<Employee> getAllEmployes() {
        return employeeRepo.findAll();

    }
    @Cacheable("employeeCache")
    public Employee getEmployee(String email) throws EmployeeNotFoundException {
        Optional<Employee> getEmployee = employeeRepo.findByEmail(email);
        Locale locale = Locale.US;
        if (getEmployee.isPresent()) {
            return getEmployee.get();
        } else {
            throw new EmployeeNotFoundException(messageSource.getMessage("employee.emailnotfound", null, locale) + " " + email);
        }

    }

    @CacheEvict(allEntries = true)
    public String delete(String email) {
        Optional<Employee> getEmployee = employeeRepo.findByEmail(email);
        if (getEmployee.isPresent()) {
            employeeRepo.delete(getEmployee.get());
            return "Employee with email: " + email + "deleted";
        } else {
            return "Employee not found";
        }
    }

    @CachePut(key = "#employeeDto")
    public Employee addEmployee(EmployeeDto employeeDto, Locale locale) throws EmailExists {
        Optional<Employee> empl = employeeRepo.findByEmail(employeeDto.getEmail());
        if (empl.isEmpty()) {
            Employee emp = new Employee();
            emp.setFirstName(employeeDto.getFirstName());
            emp.setLastName(employeeDto.getLastName());
            emp.setEmail(employeeDto.getEmail());
            AddressEmployee address = new AddressEmployee();
            address.setHouse(employeeDto.getHouse());
            address.setStreet(employeeDto.getStreet());
            emp.setAddress(address);
            return employeeRepo.save(emp);
        } else {
            throw new EmailExists(employeeDto.getEmail() + " " + messageSource.getMessage("email.exists", null, locale));
        }
    }


    public List<Employee> addManyEmployee(Set<EmployeeDto> employeeDtos, Locale locale) throws EmailExists {
        List<Employee> emplist = new ArrayList<>();

        for (EmployeeDto e : employeeDtos
        ) {
            Optional<Employee> empl = employeeRepo.findByEmail(e.getEmail());
            if (empl.isPresent()) {
                throw new EmailExists(empl.get().getEmail() + " " + messageSource.getMessage("email.exists", null, locale));
            } else {
                Employee emp = new Employee();
                AddressEmployee adr = new AddressEmployee();
                emp.setFirstName(e.getFirstName());
                emp.setLastName(e.getLastName());
                emp.setEmail(e.getEmail());
                adr.setHouse(e.getHouse());
                adr.setStreet(e.getStreet());
                emp.setAddress(adr);
                emplist.add(emp);
            }
        }
        return employeeRepo.saveAllAndFlush(emplist);
    }

    @CachePut(key = "#employeeDto")
    public Employee updateEmployee(EmployeeDto employeeDto, Locale locale) throws EmployeeNotFoundException {
        Optional<Employee> emp = employeeRepo.findByEmail(employeeDto.getEmail());
        if (emp.isPresent()) {
            Employee employee = emp.get();
            employee.setFirstName(employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName());
            AddressEmployee addressEmployee = emp.get().getAddress();
            addressEmployee.setStreet(employeeDto.getStreet());
            addressEmployee.setHouse(employeeDto.getHouse());
            employee.setAddress(addressEmployee);
            return employeeRepo.save(employee);
        } else {
            throw new EmployeeNotFoundException(messageSource.getMessage("employee.emailnotfound", null, locale) + " " + employeeDto);
        }
    }

    @CacheEvict(allEntries = true)
    public void refreshCache(){
    }

    @CacheEvict(allEntries = true)
    public String deleteAllEmpls(){
        employeeRepo.deleteAll();
        return "Delete all success";
    }


}
