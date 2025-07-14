package com.tochratana.mb_api.service;

import com.tochratana.mb_api.dto.AccountTypeResponse;
import com.tochratana.mb_api.dto.CreateAccountTypeRequest;

import java.util.List;

public interface AccountTypeService {
    AccountTypeResponse createAccountType(CreateAccountTypeRequest request);
    List<AccountTypeResponse> getAllAccountTypes();
    AccountTypeResponse getAccountTypeById(Integer id);
    void deleteAccountType(Integer id);
}