package codewiththugboy.customer.service;

import codewiththugboy.customer.data.model.Customer;
import codewiththugboy.customer.dto.request.AuthenticationRequest;
import codewiththugboy.customer.dto.request.CustomerDto;
import codewiththugboy.customer.dto.response.AuthenticationResponse;
import codewiththugboy.customer.dto.response.RegisterResponse;
import codewiththugboy.customer.dto.request.RegisterRequest;
import codewiththugboy.customer.xcepstion.CustomerException;
import codewiththugboy.customer.xcepstion.DuplicateEmailException;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.List;

public interface CustomerServices {
    RegisterResponse registerCustomer (RegisterRequest request) throws DuplicateEmailException, UnirestException;
    AuthenticationResponse login(AuthenticationRequest request)throws CustomerException;
    List<Customer> findAll()throws CustomerException;
    Customer findCustomerByEmail(String email)throws CustomerException;
    CustomerDto updateCustomerProfile(String customerId,CustomerDto request)throws CustomerException;
    CustomerDto findCustomerById(String customerId)throws CustomerException;
    Customer deleteCustomerByEmail(String email)throws CustomerException;
    Customer deleteCustomerById(String customerId)throws CustomerException;



}
