package com.karthik.account.repository;

import com.karthik.account.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
