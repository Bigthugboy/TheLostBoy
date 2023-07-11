package codewiththugboy.customer.controller;


import codewiththugboy.customer.data.model.Customer;
import codewiththugboy.customer.dto.request.CreateCustomerDto;
import codewiththugboy.customer.service.CustomerServicesImpl;
import codewiththugboy.customer.xcepstion.DefaultExceptionsHandler;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor

@RequestMapping("/customers")
public class CustomerController {
    CustomerServicesImpl customerServiceImpl;

    @PostMapping("")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerDto dto)  {

        try {
            Customer customer = customerServiceImpl.registerCustomer(dto);
            return ResponseEntity.ok(customer);
        }catch (DefaultExceptionsHandler exception ){
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
