package codewiththugboy.customer.service;

import codewiththugboy.customer.dto.request.AuthenticationRequest;
import codewiththugboy.customer.dto.response.AuthenticationResponse;
import codewiththugboy.customer.dto.response.RegisterResponse;
import codewiththugboy.customer.dto.request.RegisterRequest;
import codewiththugboy.customer.xcepstion.DuplicateEmailException;

public interface CustomerServices {
    RegisterResponse registerCustomer (RegisterRequest request) throws DuplicateEmailException;
    AuthenticationResponse login(AuthenticationRequest request);
}
