package com.example.L10minordemo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false,unique = true)
    private String phone;

    @Column(nullable = false,unique = true)
    private String idNumber;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    private Address address;

    @OneToMany(mappedBy = "visitor")
    private Set<Visit>visitSet;

    @CreationTimestamp
    private Date createdDate;

    @UpdateTimestamp
    private Date updatedDate;
}
