package com.tochratana.mb_api.service.impl;

import com.tochratana.mb_api.domain.Customer;
import com.tochratana.mb_api.domain.KYC;
import com.tochratana.mb_api.domain.Segment;
import com.tochratana.mb_api.dto.CreateCustomerRequest;
import com.tochratana.mb_api.dto.CustomerResponse;
import com.tochratana.mb_api.dto.UpdateCustomerRequest;
import com.tochratana.mb_api.mapper.CustomerMapper;
import com.tochratana.mb_api.repository.CustomerRepository;
import com.tochratana.mb_api.repository.KYCRepository;
import com.tochratana.mb_api.repository.SegmentRepository;
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
    private final KYCRepository kycRepository;
    private final SegmentRepository segmentRepository;


    @Override
    public void verifyKyc(String nationalCardId) {
        // Find customer by national card ID
        Customer customer = customerRepository.findByNationalCardId(nationalCardId);

        if (customer == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Customer not found with national card ID: " + nationalCardId
            );
        }

        KYC kyc = customer.getKyc();
        if (kyc == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "KYC not found for customer"
            );
        }

        // Update KYC status
        kyc.setIsVerified(true);

        // Save KYC
        kycRepository.save(kyc);
    }

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
        // Validate email
        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer with email already exists"
            );
        }

        // Validate phoneNumber
        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer with phone number already exists"
            );
        }

        // Validate national card ID
        if (customerRepository.findByNationalCardId(createCustomerRequest.nationalCardId()) != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Customer with national card ID already exists"
            );
        }

        // Validate segment exists
        Segment segment = segmentRepository.findById(createCustomerRequest.segmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Segment not found with id: " + createCustomerRequest.segmentId()
                ));

        // Create customer
        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer.setIsDeleted(false);
        customer.setAccounts(new ArrayList<>());
        customer.setSegment(segment);

        // Auto-create KYC
        KYC kyc = new KYC();
        kyc.setIsDeleted(false);
        kyc.setIsVerified(false);
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        kyc.setCustomer(customer);

        // Set KYC to customer (bidirectional relationship)
        customer.setKyc(kyc);

        // Save customer (cascade will save KYC)
        customer = customerRepository.save(customer);

        // Don't auto-verify KYC - let it be verified manually via endpoint
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
