package com.tochratana.mb_api.controller;

import com.tochratana.mb_api.dto.AccountResponse;
import com.tochratana.mb_api.dto.CreateAccountRequest;
import com.tochratana.mb_api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createNewAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createNewAccount(createAccountRequest);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Integer id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> getAccountsByCustomerId(@PathVariable Integer customerId) {
        return accountService.getAccountsByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public AccountResponse updateAccount(@PathVariable Integer id,
                                         @Valid @RequestBody CreateAccountRequest updateRequest) {
        return accountService.updateAccount(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Integer id) {
        accountService.deleteAccount(id);
    }
}
