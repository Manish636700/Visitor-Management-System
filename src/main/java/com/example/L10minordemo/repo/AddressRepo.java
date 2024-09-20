package com.example.L10minordemo.repo;

import com.example.L10minordemo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Long> {
    Address findById(long id);
}
