package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByCustomerIdAndIsDeletedFalse(Integer customerId);
    List<Account> findByIsDeletedFalse();
}