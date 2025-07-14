package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {
    Optional<AccountType> findByTypeName(String typeName);
    Optional<AccountType> findByTypeNameAndIsDeletedFalse(String typeName);
}