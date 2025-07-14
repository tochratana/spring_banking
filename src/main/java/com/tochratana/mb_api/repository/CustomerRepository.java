//package com.tochratana.mb_api.repository;
//
//import com.tochratana.mb_api.domain.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//
//    // SELECT * FROM customer WHERE phone_number = ?1
//    Optional<Customer> findByPhoneNumber(String phoneNumber);
//
//    boolean existsByEmail(String email);
//
//    boolean existsByPhoneNumber(String phoneNumber);
//}


package com.tochratana.mb_api.repository;

import com.tochratana.mb_api.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}