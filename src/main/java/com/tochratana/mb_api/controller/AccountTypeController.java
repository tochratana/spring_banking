package com.tochratana.mb_api.controller;

import com.tochratana.mb_api.dto.AccountTypeResponse;
import com.tochratana.mb_api.dto.CreateAccountTypeRequest;
import com.tochratana.mb_api.service.AccountTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountTypeResponse createAccountType(@Valid @RequestBody CreateAccountTypeRequest request) {
        return accountTypeService.createAccountType(request);
    }

    @GetMapping
    public List<AccountTypeResponse> getAllAccountTypes() {
        return accountTypeService.getAllAccountTypes();
    }

    @GetMapping("/{id}")
    public AccountTypeResponse getAccountTypeById(@PathVariable Integer id) {
        return accountTypeService.getAccountTypeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountType(@PathVariable Integer id) {
        accountTypeService.deleteAccountType(id);
    }
}