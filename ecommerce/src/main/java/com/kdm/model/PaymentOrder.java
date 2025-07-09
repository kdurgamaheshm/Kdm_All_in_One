package com.kdm.model;


import com.kdm.domain.PaymentMethod;
import com.kdm.domain.PaymentOrderStatus;
import com.kdm.domain.PaymentStatus;
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
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long amount;
    private PaymentOrderStatus status= PaymentOrderStatus.PENDING;
    private PaymentMethod paymentMethod;
    private String paymentLinkId;
    @ManyToOne
    private User user;
    @OneToMany
    private Set<Order> orders=new HashSet<>();
}
