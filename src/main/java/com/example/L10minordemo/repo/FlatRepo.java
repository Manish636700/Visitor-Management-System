package com.example.L10minordemo.repo;

import com.example.L10minordemo.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FlatRepo extends JpaRepository<Flat,Long> {
    Flat findByNumber(String number);

}
