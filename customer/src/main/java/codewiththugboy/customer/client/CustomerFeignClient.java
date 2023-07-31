package codewiththugboy.customer.client;
import codewiththugboy.customer.data.model.Customer;
import codewiththugboy.customer.dto.request.AuthenticationRequest;
import codewiththugboy.customer.dto.request.CustomerDto;
import codewiththugboy.customer.dto.request.RegisterRequest;
import codewiththugboy.customer.dto.response.AuthenticationResponse;
import codewiththugboy.customer.dto.response.RegisterResponse;
import codewiththugboy.customer.xcepstion.DuplicateEmailException;
import com.mashape.unirest.http.exceptions.UnirestException;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


import java.util.List;
public interface CustomerFeignClient {
    @RequestLine("POST /register")
    @Headers("Content-Type: application/json")
    RegisterResponse registerCustomer(RegisterRequest request) throws DuplicateEmailException, UnirestException;

    @RequestLine("POST /login")
    @Headers("Content-Type: application/json")
    AuthenticationResponse login(AuthenticationRequest request);

    @RequestLine("GET /customers")
    List<Customer> findAll();

    @RequestLine("GET /customers?email={email}")
    Customer findCustomerByEmail(@Param("email") String email);

    @RequestLine("PUT /customers/{customerId}")
    @Headers("Content-Type: application/json")
    CustomerDto updateCustomerProfile(@Param("customerId") String customerId, CustomerDto request);

    @RequestLine("GET /customers/{customerId}")
    CustomerDto findCustomerById(@Param("customerId") String customerId);

    @RequestLine("DELETE /customers/{customerId}")
    Customer deleteCustomerById(@Param("customerId") String customerId);

    @RequestLine("DELETE /customers?email={email}")
    Customer deleteCustomerByEmail(@Param("email") String email);

    @RequestLine("PUT /customers/{token}")
    boolean verifyCustomer(@Param("token") String token);


}
