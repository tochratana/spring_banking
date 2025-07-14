package com.tochratana.mb_api.mapper;

import com.tochratana.mb_api.domain.Customer;
import com.tochratana.mb_api.dto.CreateCustomerRequest;
import com.tochratana.mb_api.dto.CustomerResponse;
import com.tochratana.mb_api.dto.UpdateCustomerRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    // DTO -> Model
    // Model -> DTO
    // Return to convert data
    // Parameter is source data


    // Partially update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(UpdateCustomerRequest updateCustomerRequest,
                             @MappingTarget Customer customer);

    // Map from customer to customer response
    CustomerResponse fromCustomer(Customer customer);

    // Map from customer creation request to customer
    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    List<CustomerResponse> fromCustomers(List<Customer> customers);


}
