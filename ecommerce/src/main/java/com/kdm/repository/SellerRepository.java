package com.kdm.repository;

import com.kdm.domain.AccountStatus;
import com.kdm.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByEmail(String email);

    List<Seller> findAllByAccountStatus(AccountStatus status);
}
