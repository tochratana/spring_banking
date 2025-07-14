package com.tochratana.mb_api.mapper;

import com.tochratana.mb_api.domain.Account;
import com.tochratana.mb_api.dto.AccountResponse;
import com.tochratana.mb_api.dto.CreateAccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "accountType", ignore = true)
    Account toAccount(CreateAccountRequest createAccountRequest);

    @Mapping(target = "customer.id", source = "customer.id")
    @Mapping(target = "customer.name", source = "customer.fullName")
    @Mapping(target = "customer.email", source = "customer.email")
    @Mapping(target = "accountType.id", source = "accountType.id")
    @Mapping(target = "accountType.name", source = "accountType.typeName")
    @Mapping(target = "accountType.description", source = "accountType.typeName")
    AccountResponse fromAccount(Account account);

    List<AccountResponse> fromCustomers(List<Account> accounts);
}