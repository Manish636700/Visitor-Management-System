package com.example.L10minordemo.repo;

import com.example.L10minordemo.entity.Flat;
import com.example.L10minordemo.entity.Visit;
import com.example.L10minordemo.enums.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {

    List<Visit> findByStatusAndFlat(VisitStatus visitStatus, Flat flat);

    List<Visit> findByStatusAndCreatedDateLessThanEqual(VisitStatus visitStatus, Date date);
}