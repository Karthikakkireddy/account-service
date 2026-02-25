package com.karthik.account.repository;

import com.karthik.account.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long>
{
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
