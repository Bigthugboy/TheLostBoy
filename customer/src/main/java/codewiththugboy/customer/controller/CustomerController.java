package codewiththugboy.customer.controller;

import codewiththugboy.customer.client.CustomerFeignClient;
import codewiththugboy.customer.data.model.Customer;
import codewiththugboy.customer.dto.request.CustomerDto;
import codewiththugboy.customer.dto.request.RegisterRequest;
import codewiththugboy.customer.dto.response.ApiResponse;
import codewiththugboy.customer.dto.response.RegisterResponse;
import codewiththugboy.customer.xcepstion.CustomerException;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerFeignClient services;


    @PostMapping("/signup")
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody @Valid RegisterRequest accountCreationRequest) throws UnirestException {
        String host = request.getRequestURL().toString();
        int index = host.indexOf("/", host.indexOf("/", host.indexOf("/")) + 2);
        host = host.substring(0, index + 1);
        log.info("Host --> {}", host);
        RegisterResponse response = services.registerCustomer(accountCreationRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message("customer created successfully")
                .data(response)
                .build();
        log.info("Returning response");
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @RequestMapping("/verify/{token}")
    public ResponseEntity<?>verify(@PathVariable("token") String token) throws CustomerException {
        services.verifyCustomer(token);
        return new ResponseEntity<>(true,HttpStatus.OK) ;
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") @NotNull @NotBlank String userId) {
        try {
            if (("null").equals(userId) || ("").equals(userId.trim())) {
                throw new CustomerException("String id cannot be null", 400);
            }
            CustomerDto customerDto = services.findCustomerById(userId);
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("success")
                    .message("customer found")
                    .data(String.format("%s %s %s", customerDto.getFirstName(), customerDto.getLastName(),customerDto.getEmail()))
                    .result(1)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } catch (CustomerException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fail")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getMessage()));
        }
    }

    @PutMapping("updateCustomer/")
    public ResponseEntity<?> updateCustomerProfile(@Valid @NotBlank @NotNull @RequestParam String id, @RequestBody @NotNull CustomerDto request) throws CustomerException {
        try {
            if (("null").equals(id) || ("").equals(id.trim())) {
                throw new CustomerException("String id cannot be null", 400);
            }
            CustomerDto customerDto = services.updateCustomerProfile(id, request);
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("success")
                    .message("customer found")
                    .data(String.format("%s %s %s", customerDto.getFirstName(), customerDto.getLastName(),customerDto.getEmail()))
                    .result(1)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

        } catch (CustomerException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fail")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getMessage()));
        }
    }

    @GetMapping(value = "/getAllCustomers", produces = {"application/hal+json"})
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<Customer> customers = services.findAll();
        ApiResponse apiResponse = ApiResponse.builder()
                .status("success")
                .message(customers.size() != 0 ? "customers found" : "no user exists in database")
                .data(customers)
                .result(customers.size())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable("email") @NotNull @NotBlank String email) {
        try {
            if (("null").equals(email) || ("").equals(email.trim())) {
                throw new CustomerException("email cannot be null", 400);
            }
            Customer customer = services.findCustomerByEmail(email);
            ApiResponse response = ApiResponse.builder()
                    .data(String.format("%s %s %s", customer.getFirstName(), customer.getLastName(),customer.getEmail()))
                    .message("customer found")
                    .status("success")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomerException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fail")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getMessage()));
        }
    }

    @DeleteMapping("/deleteCustomer/")
    public ResponseEntity<?> deleteCustomerByEmail(@RequestParam String email) {
        try {
            if (("null").equals(email) || ("").equals(email.trim())) {
                throw new CustomerException("email cannot be null", 400);
            }
            Customer customer = services.deleteCustomerByEmail(email);
            ApiResponse response = ApiResponse.builder()
                    .data(customer)
                    .message("customer deleted")
                    .status("success")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomerException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fail")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getMessage()));
        }

    }

    @DeleteMapping("/deleteCustomerId/")
    public ResponseEntity<?> deleteCustomerByCustomerId(@RequestParam String customerId) {
        try {
            if (("null").equals(customerId) || ("").equals(customerId.trim())) {
                throw new CustomerException("email cannot be null", 400);
            }
            Customer customer = services.deleteCustomerById(customerId);
            ApiResponse response = ApiResponse.builder()
                    .data(customer)
                    .message("customer deleted")
                    .status("success")
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomerException e) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .status("fail")
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(e.getMessage()));
        }

    }
}