package codewiththugboy.customer.data.model;

import jakarta.persistence.Embeddable;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@NotEmpty
public class Address {
    private String houseNumber;
    private String street;
    private String city;
    private String zipCode;
    private String state;





}
