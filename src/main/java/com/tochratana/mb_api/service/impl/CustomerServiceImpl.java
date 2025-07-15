package com.tochratana.mb_api.service.impl;

import com.tochratana.mb_api.domain.Customer;
import com.tochratana.mb_api.dto.CreateCustomerRequest;
import com.tochratana.mb_api.dto.CustomerResponse;
import com.tochratana.mb_api.dto.UpdateCustomerRequest;
import com.tochratana.mb_api.mapper.CustomerMapper;
import com.tochratana.mb_api.repository.CustomerRepository;
import com.tochratana.mb_api.service.CustomerService;
import jakarta.transaction.Transactional;
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
    private final CustomerMapper customerMapper;

    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if(!customerRepository.isExistsByPhoneNumber(phoneNumber)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't have phoneNumber");
        }

        customerRepository.disableByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find phone Number"));
        customerRepository.delete(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {

        return customerRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .map(customerMapper::fromCustomer)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find"));
    }

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

        // Use mapper to convert request to entity
        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());

        // save to db
        customer = customerRepository.save(customer);

        // Use mapper to convert entity to response
        return customerMapper.fromCustomer(customer);
    }

    @Override
    public List<CustomerResponse> findAllCustomer() {
        List<Customer> customers = customerRepository.findAllByIsDeletedFalse();

        // Filter out deleted customers
        List<Customer> nonDeleted = customers.stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .toList();

        // 👉 Use mapper to convert list
        return customerMapper.fromCustomers(nonDeleted);
    }

    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {

        Customer customer =
                customerRepository
                        .findByPhoneNumber(phoneNumber)
                        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Can't find phone number"));

//        if(updateCustomerRequest.fullName() != null){
//            customer.setFullName(updateCustomerRequest.fullName());
//        }

        customerMapper.toCustomerPartially(updateCustomerRequest,customer);

        customer = customerRepository.save(customer);
        return customerMapper.fromCustomer(customer);
    }
}
