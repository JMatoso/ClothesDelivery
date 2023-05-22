package com.clothesdelivery.web.repositories;

import com.clothesdelivery.web.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepository extends JpaRepository<Address, Long> {
    Address findByCity(String city);
    Address findByCountry(String country);
    Address findByAddress(String address);
}
