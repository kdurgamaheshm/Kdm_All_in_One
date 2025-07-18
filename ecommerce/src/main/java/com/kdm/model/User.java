package com.kdm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kdm.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;
    private String fullName;
    private String Mobile;
    private USER_ROLE role= USER_ROLE.ROLE_CUSTOMER;
    @OneToMany
    private Set<Address> addresses= new HashSet<>();
    @ManyToMany
    @JsonIgnore
    private Set<Coupon> usedCoupons=new HashSet<>();



}
