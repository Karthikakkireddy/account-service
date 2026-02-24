package com.karthik.account.repository;

import com.karthik.account.domain.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepo extends JpaRepository<Accounts, Long> {
}
