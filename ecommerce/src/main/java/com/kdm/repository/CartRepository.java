package com.kdm.repository;

import com.kdm.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

//to assign a new user cart
public interface CartRepository  extends JpaRepository<Cart, Long> {

}
