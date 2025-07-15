package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.KYC;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface KYCRepository extends CrudRepository<KYC, Integer> {
    Optional<KYC> findByNationalCardId(String nationalCardId);
    List<KYC> findByIsVerifiedFalse(); // Fixed method name
}
