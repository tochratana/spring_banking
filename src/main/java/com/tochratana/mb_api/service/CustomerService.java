package com.tochratana.mb_api.service;

import com.tochratana.mb_api.dto.CreateCustomerRequest;
import com.tochratana.mb_api.dto.CustomerResponse;
import com.tochratana.mb_api.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
    void disableByPhoneNumber (String phoneNumber);
    void deleteByPhoneNumber (String phoneNumber);
    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);
    CustomerResponse findByPhoneNumber(String phoneNumber);
    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);
    List<CustomerResponse> findAllCustomer();
    void verifyKyc(String nationalCardId);
}
