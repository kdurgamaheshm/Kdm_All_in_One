package com.kdm.model;

import com.kdm.domain.AccountStatus;
import com.kdm.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String sellerName;



    @Column(unique = true,nullable = false)
    private String email;

    private String password;
    private String mobile;

    @Embedded
    private BusinessDetails businessDetails=new BusinessDetails();

    @Embedded
    private BankDetails bankDetails=new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    private Address pickUpAddress=new Address();



    private String GSTIN;

    private USER_ROLE role=USER_ROLE.ROLE_SELLER;

    private boolean isEmailVerified=false;

    private AccountStatus accountStatus= AccountStatus.PENDING_VERIFICATION;

}
