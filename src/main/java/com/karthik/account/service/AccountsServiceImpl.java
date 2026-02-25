package com.karthik.account.service;

import com.karthik.account.constants.AccountsConstants;
import com.karthik.account.domain.Accounts;
import com.karthik.account.domain.Customer;
import com.karthik.account.dto.AccountsDto;
import com.karthik.account.dto.CustomerDto;
import com.karthik.account.exception.CustomerAlreadyExistsException;
import com.karthik.account.exception.ResourceNotFoundException;
import com.karthik.account.mapper.AccountsMapper;
import com.karthik.account.mapper.CustomerMapper;
import com.karthik.account.repository.AccountsRepo;
import com.karthik.account.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService
{

    private final AccountsRepo accountsRepo;
    private final CustomerRepo customerRepo;


    @Override
    public void createAccount(CustomerDto customerDto)
    {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepo.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent())
        {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
        }

        customer.setCreatedAt(LocalTime.now());
        customer.setCreatedBy("Anonymous");

        Customer savedCustomer = customerRepo.save(customer);
        accountsRepo.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        newAccount.setCreatedAt(LocalTime.now());
        newAccount.setCreatedBy("Anonymous");


        return newAccount;
    }


    @Override
    public CustomerDto fetchAccount(String mobileNumber)
    {
            Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
            );


            Accounts account = accountsRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
            );

            CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
            AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());
            customerDto.setAccountsDto(accountsDto);

            return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepo.deleteByCustomerId(customer.getCustomerId());
        customerRepo.deleteById(customer.getCustomerId());
        return true;
    }


}
