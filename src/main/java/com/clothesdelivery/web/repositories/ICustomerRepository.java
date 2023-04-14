package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
    Customer findByEmailAndPassword(String email, String password);
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

}
