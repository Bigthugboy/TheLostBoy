package codewiththugboy.customer.service;

import codewiththugboy.customer.client.CustomerFeignClient;
import codewiththugboy.customer.data.model.ConfirmationToken;
import codewiththugboy.customer.data.model.Customer;
import codewiththugboy.customer.data.model.RoleType;
import codewiththugboy.customer.data.repo.CustomerRepo;
import codewiththugboy.customer.dto.request.AuthenticationRequest;
import codewiththugboy.customer.dto.request.CustomerDto;
import codewiththugboy.customer.dto.request.RegisterRequest;
import codewiththugboy.customer.dto.request.VerificationMessageRequest;
import codewiththugboy.customer.dto.response.AuthenticationResponse;
import codewiththugboy.customer.dto.response.RegisterResponse;
import codewiththugboy.customer.event.SendMessageEvent;
import codewiththugboy.customer.token.ConfirmationTokenImpl;
import codewiththugboy.customer.xcepstion.CustomerException;
import codewiththugboy.customer.xcepstion.DuplicateEmailException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServicesImpl implements CustomerFeignClient {
    private final CustomerRepo repo;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PasswordEncoder passwordConfig;
    private ModelMapper modelMapper ;
    private final ConfirmationTokenImpl confirmationToken;
    private ConfirmationToken confirmToken;



    @Override
    public RegisterResponse registerCustomer(RegisterRequest request) throws DuplicateEmailException {
        System.out.println("Entering registerCustomer method.");

        if (findByEmail(request.getEmail())) {
            System.out.println("Duplicate email found. Throwing DuplicateEmailException.");
            throw new DuplicateEmailException("ACCOUNT ALREADY EXISTS");
        }

        System.out.println("Creating a new Customer entity.");
        Customer customer;


        customer = modelMapper.map(request, Customer.class);
        String encodedPassword = passwordConfig.encode(request.getPassword());
        customer.setPassword(encodedPassword);
        customer.setRoles(RoleType.ROLE_USER);
        customer.setDate(LocalDate.now());
        customer.setCustomerId(String.valueOf(UUID.randomUUID()).substring(1,10));

        System.out.println("Generating verification token.");

        String token = confirmationToken.generateAndSaveToken(customer);
        log.info("token -> {}",token);
        System.out.println("Sending verification email.");
        String host = "";
        VerificationMessageRequest message ;
        assert false;
        message = VerificationMessageRequest.builder()
                .phoneNumber(request.getPhoneNumber())
                .verificationToken(token)
                .subject("VERIFY EMAIL")
                .sender("rapilomme@gmail.com")
                .receiver(customer.getEmail())
                .domainUrl(host)
                .usersFullName(String.format("%s %s", customer.getFirstName(), customer.getLastName()))
                .build();
        SendMessageEvent event = new SendMessageEvent(message);
        applicationEventPublisher.publishEvent(event);


        System.out.println("Saving the customer entity to the repository.");
        repo.save(customer);

        System.out.println("Registering process completed successfully.");
        return RegisterResponse.builder()
                .email(customer.getEmail())
                .message("Welcome " + customer.getFirstName() + "!")
                .token(token)
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
    public List<Customer> findAll() {
         return repo.findAll().stream()
                .map(user -> modelMapper.map(user, Customer.class)).toList();
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new CustomerException("user not found", 400));
    }

    @Override
    public CustomerDto updateCustomerProfile(String customerId, CustomerDto request)throws CustomerException {
      Optional <Customer> customer = Optional.ofNullable(repo.findByCustomerId(customerId).orElseThrow(() -> new CustomerException("Customer with " + customerId + "not found", 404)));
      if (customer.isPresent()){
          modelMapper.map(request,Customer.class);
          repo.save(customer.get());
      }
        throw new CustomerException("Customer not found",404);
    }

    @Override
    public CustomerDto findCustomerById(String customerId)throws CustomerException {
        Customer customer = repo.findByCustomerId(customerId).orElseThrow( () -> new CustomerException("Customer with " + customerId + "not found",404));
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public Customer deleteCustomerById(String customerId)throws CustomerException {
        Customer customer = repo.deleteByCustomerId(customerId).orElseThrow( () -> new CustomerException("Customer with " + customerId + "not found",404));
        return modelMapper.map(customer, Customer.class);

    }

    @Override
    public Customer deleteCustomerByEmail(String email) throws CustomerException{
        Customer customer = repo.deleteByEmail(email).orElseThrow( () -> new CustomerException("Customer with " + email + "not found",404));
        return modelMapper.map(customer, Customer.class);
    }

    @Override
    public boolean verifyCustomer(String token) {
        return confirmationToken.isEnabled(token);
    }


}
