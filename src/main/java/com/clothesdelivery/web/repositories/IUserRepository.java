package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.User;
import com.clothesdelivery.web.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findAllByRole(Role role);
    User findByEmailAndPassword(String email, String password);
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

}
