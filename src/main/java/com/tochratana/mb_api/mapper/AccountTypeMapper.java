package com.tochratana.mb_api.mapper;

import com.tochratana.mb_api.domain.AccountType;
import com.tochratana.mb_api.dto.AccountTypeResponse;
import com.tochratana.mb_api.dto.CreateAccountTypeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {


    // Map from user request to an account type to save in db
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "accounts", ignore = true)
    AccountType toAccountType(CreateAccountTypeRequest request);

    AccountTypeResponse fromAccountType(AccountType accountType);
}