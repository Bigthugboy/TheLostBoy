package codewiththugboy.customer.data.repo;

import codewiththugboy.customer.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer>findByCustomerId(String customerId);
    Optional<Customer>deleteByCustomerId(String customerId);
    Optional<Customer>deleteByEmail(String customerEmail);

}
