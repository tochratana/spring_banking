package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Modifying
    @Query(value = """
    UPDATE Customer c SET c.isDeleted = true WHERE c.phoneNumber = :phoneNumber
""")
    void disableByPhoneNumber(String phoneNumber);

    // JPQL
    @Query(value = """
    SELECT EXISTS(SELECT c
            FROM Customer c
                WHERE c.phoneNumber = ?1)
""", nativeQuery = false)
    boolean isExistsByPhoneNumber(String phoneNumber);

    // Deride
    // SELECT * FROM customer WHERE phone_number = ?1
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    List<Customer> findAllByIsDeletedFalse();
    Optional<Customer> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    Customer findByNationalCardId(String nationalCardId);
}