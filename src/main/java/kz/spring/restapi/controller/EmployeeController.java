package kz.spring.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.spring.restapi.exception.EmailExists;
import kz.spring.restapi.exception.EmployeeNotFoundException;
import kz.spring.restapi.model.Employee;
import kz.spring.restapi.model.EmployeeDto;
import kz.spring.restapi.service.impl.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(description = "Employee rest controller", name = "Employee Resource")
@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Get Employees",
            description = "Provides all available employees list"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "${api.response-codes.ok.desc}"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "${api.response-codes.badRequest.desc}"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "${api.response-codes.notFound.desc}"
                    )
            }
    )
    @GetMapping
    public List<Employee> getAllEmployee() throws EmployeeNotFoundException {
        List<Employee> getAll = employeeService.getAllEmployes();
        List<Employee> response = new ArrayList<>();
        for (Employee e : getAll) {
            e.add(linkTo(methodOn(EmployeeController.class).getEmployee(e.getEmail())).withSelfRel());
            response.add(e);
        }
        return response;
    }

    @GetMapping("/refresh")
    public void refresh(){
        employeeService.refreshCache();
    }

    @GetMapping("/delete")
    public String deleteAll(){
        return employeeService.deleteAllEmpls();
    }

    @GetMapping("{email}")
    public Employee getEmployee(@PathVariable String email) throws EmployeeNotFoundException {

        Employee employee = employeeService.getEmployee(email);
        employee.add(linkTo(methodOn(EmployeeController.class).getAllEmployee()).withSelfRel());
        return employee;
    }

    @DeleteMapping("{email}")
    public String deleteEmployee(@PathVariable String email) {
        return employeeService.delete(email);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody @Valid EmployeeDto employeeDto, Locale locale) throws EmailExists, EmployeeNotFoundException {

        Employee employee = employeeService.addEmployee(employeeDto, locale);
        employee.add(linkTo(methodOn(EmployeeController.class).getAllEmployee()).withSelfRel());
        employee.add(linkTo(methodOn(EmployeeController.class).getEmployee(employee.getEmail())).withSelfRel());
        return employee;
    }

    @PostMapping("/list")
    public List<Employee> addEmployees(@RequestBody @Valid Set<EmployeeDto> employeeDto, Locale locale) throws EmailExists {
        return employeeService.addManyEmployee(employeeDto, locale);
    }


    @PutMapping
    public Employee updateEmployee(@RequestBody @Valid EmployeeDto employeeDto, Locale locale) throws EmployeeNotFoundException {

        Employee employee = employeeService.updateEmployee(employeeDto, locale);
        employee.add(linkTo(methodOn(EmployeeController.class).getAllEmployee()).withSelfRel());
        employee.add(linkTo(methodOn(EmployeeController.class).getEmployee(employee.getEmail())).withSelfRel());
        return employee;
    }
}
