package com.tochratana.mb_api.service;

import com.tochratana.mb_api.dto.AccountResponse;
import com.tochratana.mb_api.dto.CreateAccountRequest;

import java.util.List;

public interface AccountService {
    AccountResponse createNewAccount(CreateAccountRequest createAccountRequest);
    AccountResponse getAccountById(Integer id);
    List<AccountResponse> getAccountsByCustomerId(Integer customerId);
    AccountResponse updateAccount(Integer id, CreateAccountRequest updateRequest);
    void deleteAccount(Integer id);
}