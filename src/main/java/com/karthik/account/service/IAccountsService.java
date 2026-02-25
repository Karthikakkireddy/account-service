package com.karthik.account.service;

import com.karthik.account.dto.CustomerDto;

public interface IAccountsService
{

//    @param customerDto - CustomerDto Object
    void createAccount(CustomerDto customerDto);
}
