package com.karthik.account.repository;

import com.karthik.account.domain.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepo extends JpaRepository<Accounts, Long>
{
   Optional<Accounts> findByCustomerId(Long customerId);
}
