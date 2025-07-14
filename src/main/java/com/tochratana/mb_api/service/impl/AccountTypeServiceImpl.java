package com.tochratana.mb_api.service.impl;

import com.tochratana.mb_api.domain.AccountType;
import com.tochratana.mb_api.dto.AccountTypeResponse;
import com.tochratana.mb_api.dto.CreateAccountTypeRequest;
import com.tochratana.mb_api.mapper.AccountTypeMapper;
import com.tochratana.mb_api.repository.AccountTypeRepository;
import com.tochratana.mb_api.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountTypeServiceImpl implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapper accountTypeMapper;

    @Override
    public AccountTypeResponse createAccountType(CreateAccountTypeRequest request) {
        // Check if account type already exists
        if (accountTypeRepository.findByTypeName(request.typeName()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Account type with name already exists: " + request.typeName()
            );
        }

        AccountType accountType = accountTypeMapper.toAccountType(request);
        accountType.setIsDeleted(false);

        accountType = accountTypeRepository.save(accountType);
        return accountTypeMapper.fromAccountType(accountType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTypeResponse> getAllAccountTypes() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypes.stream()
                .filter(at -> !at.getIsDeleted())
                .map(accountTypeMapper::fromAccountType)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountTypeResponse getAccountTypeById(Integer id) {
        AccountType accountType = accountTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account type not found with id: " + id
                ));

        if (accountType.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account type not found with id: " + id
            );
        }

        return accountTypeMapper.fromAccountType(accountType);
    }

    @Override
    public void deleteAccountType(Integer id) {
        AccountType accountType = accountTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account type not found with id: " + id
                ));

        if (accountType.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Account type not found with id: " + id
            );
        }

        accountType.setIsDeleted(true);
        accountTypeRepository.save(accountType);
    }
}