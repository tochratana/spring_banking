package com.tochratana.mb_api.controller;

import com.tochratana.mb_api.dto.CreateCustomerRequest;
import com.tochratana.mb_api.dto.CustomerResponse;
import com.tochratana.mb_api.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createNew(@Valid  @RequestBody CreateCustomerRequest createCustomerRequest){
        return customerService.createNew(createCustomerRequest);
    }

    @GetMapping
    public List<CustomerResponse> findAllCustomer(){
        return customerService.findAllCustomer();
    }
}
