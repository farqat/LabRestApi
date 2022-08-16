package kz.spring.restapi.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EmployeeDto {

    @NotBlank(message = "{firstname.notempty}")
    @Size(max = 30, min = 1, message = "{firstname.lengh}")
    private String firstName;

    @NotBlank(message = "{lastname.notempty}")
    @Size(max = 30, min = 1, message = "{lastname.lengh}")
    private String lastName;

    @NotBlank
    @Email(message = "{email.notvalid}")
    private String email;

    @NotBlank(message = "{street.notempty}")
    @Size(max = 30, min = 1, message = "{street.lengh}")
    private String street;

    @NotBlank(message = "{house.noempty}")
    @Size(max = 30, min = 1, message = "{house.lengh}")
    private String house;
}
