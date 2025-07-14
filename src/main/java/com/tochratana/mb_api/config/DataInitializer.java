package com.tochratana.mb_api.config;

import com.tochratana.mb_api.domain.AccountType;
import com.tochratana.mb_api.repository.AccountTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AccountTypeRepository accountTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default account types if they don't exist
        if (accountTypeRepository.count() == 0) {
            AccountType savings = new AccountType();
            savings.setTypeName("SAVINGS");
            savings.setIsDeleted(false);
            accountTypeRepository.save(savings);

            AccountType current = new AccountType();
            current.setTypeName("CURRENT");
            current.setIsDeleted(false);
            accountTypeRepository.save(current);

            AccountType business = new AccountType();
            business.setTypeName("BUSINESS");
            business.setIsDeleted(false);
            accountTypeRepository.save(business);

            System.out.println("Default account types initialized!");
        }
    }
}