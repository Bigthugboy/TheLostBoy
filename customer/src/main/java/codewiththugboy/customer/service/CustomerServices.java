package codewiththugboy.customer.service;

import codewiththugboy.customer.data.model.Customer;
import codewiththugboy.customer.dto.request.AuthenticationRequest;
import codewiththugboy.customer.dto.request.CreateCustomerDto;
import codewiththugboy.customer.dto.response.AuthenticationResponse;
import codewiththugboy.customer.dto.response.RegisterResponse;
import codewiththugboy.customer.dto.request.RegisterRequest;
import codewiththugboy.customer.xcepstion.DefaultExceptionsHandler;
import codewiththugboy.customer.xcepstion.DuplicateEmailException;

public interface CustomerServices {
    RegisterResponse registerCustomer (RegisterRequest request) throws DuplicateEmailException;
    AuthenticationResponse login(AuthenticationRequest request);

    Customer registerCustomer(CreateCustomerDto dto) throws DefaultExceptionsHandler;
}
