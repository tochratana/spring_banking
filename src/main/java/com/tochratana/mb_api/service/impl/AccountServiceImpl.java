package com.tochratana.mb_api.service.impl;

import com.tochratana.mb_api.domain.Account;
import com.tochratana.mb_api.domain.AccountType;
import com.tochratana.mb_api.domain.Customer;
import com.tochratana.mb_api.dto.AccountResponse;
import com.tochratana.mb_api.dto.CreateAccountRequest;
import com.tochratana.mb_api.mapper.AccountMapper;
import com.tochratana.mb_api.repository.AccountRepository;
import com.tochratana.mb_api.repository.AccountTypeRepository;
import com.tochratana.mb_api.repository.CustomerRepository;
import com.tochratana.mb_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createNewAccount(CreateAccountRequest createAccountRequest) {
        // Validate customer exists
        Customer customer = customerRepository.findById(createAccountRequest.customerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + createAccountRequest.customerId()
                ));

        // Validate account type exists
        AccountType accountType = accountTypeRepository.findById(createAccountRequest.accountTypeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account type not found with id: " + createAccountRequest.accountTypeId()
                ));

        // Create account
        Account account = accountMapper.toAccount(createAccountRequest);
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setIsDeleted(false);

        account = accountRepository.save(account);
        return accountMapper.fromAccount(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found with id: " + id
                ));

        if (account.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found with id: " + id
            );
        }

        return accountMapper.fromAccount(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByCustomerId(Integer customerId) {
        // Validate customer exists
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Customer not found with id: " + customerId
                ));

        List<Account> accounts = accountRepository.findByCustomerIdAndIsDeletedFalse(customerId);
        return accounts.stream()
                .map(accountMapper::fromAccount)
                .toList();
    }

    @Override
    public AccountResponse updateAccount(Integer id, CreateAccountRequest updateRequest) {
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found with id: " + id
                ));

        if (existingAccount.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found with id: " + id
            );
        }

        // Update fields
        existingAccount.setActType(updateRequest.actType());
        existingAccount.setActCurrency(updateRequest.actCurrency());
        existingAccount.setBalance(updateRequest.balance());

        // Update customer if changed
        if (!existingAccount.getCustomer().getId().equals(updateRequest.customerId())) {
            Customer customer = customerRepository.findById(updateRequest.customerId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Customer not found with id: " + updateRequest.customerId()
                    ));
            existingAccount.setCustomer(customer);
        }

        // Update account type if changed
        if (!existingAccount.getAccountType().getId().equals(updateRequest.accountTypeId())) {
            AccountType accountType = accountTypeRepository.findById(updateRequest.accountTypeId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Account type not found with id: " + updateRequest.accountTypeId()
                    ));
            existingAccount.setAccountType(accountType);
        }

        Account updatedAccount = accountRepository.save(existingAccount);
        return accountMapper.fromAccount(updatedAccount);
    }

    @Override
    public void deleteAccount(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found with id: " + id
                ));

        if (account.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account not found with id: " + id
            );
        }

        account.setIsDeleted(true);
        accountRepository.save(account);
    }
}