package codewiththugboy.customer.service;

import codewiththugboy.customer.data.model.Customer;
import codewiththugboy.customer.data.model.RoleType;
import codewiththugboy.customer.data.repo.CustomerRepo;
import codewiththugboy.customer.dto.request.AuthenticationRequest;
import codewiththugboy.customer.dto.request.CreateCustomerDto;
import codewiththugboy.customer.dto.request.RegisterRequest;
import codewiththugboy.customer.dto.response.AuthenticationResponse;
import codewiththugboy.customer.dto.response.RegisterResponse;
import codewiththugboy.customer.xcepstion.DefaultExceptionsHandler;
import codewiththugboy.customer.xcepstion.DuplicateEmailException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CustomerServicesImpl implements CustomerServices{
    private final CustomerRepo repo;

    @Override
    public RegisterResponse registerCustomer(RegisterRequest request) throws DuplicateEmailException {
        if (findByEmail(request.getEmail())) {
            throw new DuplicateEmailException("ACCOUNT ALREADY REQUEST");
        }
        ModelMapper modelMapper = new ModelMapper();
        Customer customer = modelMapper.map(request, Customer.class);
        customer.setRoles(RoleType.ROLE_USER);
        repo.save(customer);

        return RegisterResponse.builder()
                .email(customer.getEmail())
                .message("welcome ")
                .build();
    }

    private boolean findByEmail(String email) {
        Optional<Customer> user = repo.findByEmail(email);
        return user.isPresent();

    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        return null;
    }

    @Override
    public Customer registerCustomer(CreateCustomerDto dto) throws DefaultExceptionsHandler {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPassword(dto.getPassword());
        return customer;
    }
}
