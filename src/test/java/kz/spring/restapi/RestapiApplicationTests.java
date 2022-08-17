package kz.spring.restapi;

import kz.spring.restapi.exception.EmailExists;
import kz.spring.restapi.exception.EmployeeNotFoundException;
import kz.spring.restapi.model.AddressEmployee;
import kz.spring.restapi.model.Employee;
import kz.spring.restapi.model.EmployeeDto;
import kz.spring.restapi.repo.EmployeeRepo;
import kz.spring.restapi.service.impl.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class RestapiApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepo employeeRepo;

    @Test
    public void getEmployees(){
        AddressEmployee addressEmployee = new AddressEmployee(1,"Street", "House");
        when(employeeRepo.findAll()).thenReturn(Stream.of(
                new Employee(UUID.randomUUID(), "User","User","user@gmail.com",addressEmployee),
                new Employee(UUID.randomUUID(), "User1","User1","user1@gmail.com",addressEmployee)).collect(Collectors.toList()));
        assertEquals(2, employeeService.getAllEmployes().size());
    }

    @Test
    public void getEmployeeByEmail() throws EmployeeNotFoundException {
        String email = "user@gmail.com";
        AddressEmployee addressEmployee = new AddressEmployee(1,"Street", "House");
        Optional<Employee> employee = employeeRepo.findByEmail(email);
        if(employee.isPresent()){
            when(employeeRepo.findByEmail(email)).thenReturn(employee);
            assertEquals(1, employeeService.getEmployee(email));
        }
    }

    @Test
    public void saveEmployee() throws EmailExists {
        EmployeeDto employeeDto = new EmployeeDto("User", "User", "user@gmail.com", "Street 1", "5");
        Locale locale = Locale.US;
        Employee emp = new Employee();
        emp.setFirstName(employeeDto.getFirstName());
        emp.setLastName(employeeDto.getLastName());
        emp.setEmail(employeeDto.getEmail());
        AddressEmployee address = new AddressEmployee();
        address.setHouse(employeeDto.getHouse());
        address.setStreet(employeeDto.getStreet());
        emp.setAddress(address);
        when(employeeRepo.save(emp)).thenReturn(emp);
        assertEquals(emp, employeeService.addEmployee(employeeDto,locale));
    }

    @Test
    public void deleteEmployee(){
        String email = "user@gmail.com";
        Optional<Employee> employee = employeeRepo.findByEmail(email);
        if(employee.isPresent()){
            employeeService.delete(email);
            Employee employee1 = employee.get();
            verify(employeeRepo, times(1)).delete(employee1);
        }

    }

}
