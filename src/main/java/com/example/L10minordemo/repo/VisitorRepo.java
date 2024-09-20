package com.example.L10minordemo.repo;

import com.example.L10minordemo.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepo extends JpaRepository<Visitor,Long> {

    Visitor findByidNumber(String idNumber);
}
