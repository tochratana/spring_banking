package com.tochratana.mb_api.service.impl;

import com.tochratana.mb_api.domain.Customer;
import com.tochratana.mb_api.dto.CreateCustomerRequest;
import com.tochratana.mb_api.dto.CustomerResponse;
import com.tochratana.mb_api.repository.CustomerRepository;
import com.tochratana.mb_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        // validate email
        if (customerRepository.existsByEmail(createCustomerRequest.email())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, // 409 Conflict
                    "Customer with email already exists"
            );
        }

        // validate phoneNumber
        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, // 409 Conflict
                    "Customer with email already exists"
            );
        }

        Customer customer = new Customer();
        customer.setFullName(createCustomerRequest.fullName());
        customer.setGender(createCustomerRequest.gender());
        customer.setRemark(createCustomerRequest.remark());
        customer.setEmail(createCustomerRequest.email());
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());

        customer = customerRepository.save(customer);
        return new CustomerResponse(
                customer.getFullName(),
                customer.getGender(),
                customer.getEmail()
        );
    }

    @Override
    public List<CustomerResponse> findAllCustomer() {

        // Contains the raw data from the database (all Customer entities)
        List<Customer> customers = customerRepository.findAll();


        // Contains the data that we want to return to the frontend or client (DTO format)
        List<CustomerResponse> responses = new ArrayList<>();


        for (Customer customer : customers) {

            // Skip deleted customers if needed
            if (!Boolean.TRUE.equals(customer.getIsDeleted())) {
                responses.add(new CustomerResponse(
                        customer.getFullName(),
                        customer.getGender(),
                        customer.getEmail()
                ));
            }
        }

        return responses;
    }

}
