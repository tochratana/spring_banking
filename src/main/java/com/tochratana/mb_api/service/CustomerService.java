package com.tochratana.mb_api.service;

import com.tochratana.mb_api.dto.CreateCustomerRequest;
import com.tochratana.mb_api.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);
    List<CustomerResponse> findAllCustomer();
}
