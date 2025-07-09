package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
}
