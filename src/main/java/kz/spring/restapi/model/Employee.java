package kz.spring.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "employee")
public class Employee extends RepresentationModel<Employee> {

    @Id
    private UUID id = UUID.randomUUID();
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @Column(name = "email",updatable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_ID")
    private AddressEmployee address;
}
