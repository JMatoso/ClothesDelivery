package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

}
