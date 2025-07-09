package com.tochratana.mb_api;

import com.tochratana.mb_api.domain.Customer;
import com.tochratana.mb_api.domain.KYC;
import com.tochratana.mb_api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BankingMobileApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BankingMobileApplication.class, args);
    }

    private final CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
//        Customer customer = new Customer();
//        KYC kyc = new KYC();
//        kyc.setNationalCardId("123456789");
//        kyc.setIsVerified(false);
//        kyc.setIsDeleted(false);
//        kyc.setCustomer(customer);
//
//        customer.setFullName("Ratana");
//        customer.setGender("M");
//        customer.setEmail("ratana@gmail.com");
//        customer.setPhoneNumber("0886465190");
//        customer.setKyc(kyc);
//        customer.setRemark("Student");
//        customer.setIsDeleted(false);
//        customerRepository.save(customer);
    }
}
