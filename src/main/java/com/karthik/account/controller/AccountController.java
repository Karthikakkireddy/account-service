package com.karthik.account.controller;


import com.karthik.account.constants.AccountsConstants;
import com.karthik.account.dto.CustomerDto;
import com.karthik.account.dto.ResponseDto;
import com.karthik.account.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AccountController
{

    private final IAccountsService accountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccounts(@RequestBody CustomerDto customerDto)
    {

        accountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }


    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber)
    {
       CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);

       return ResponseEntity
               .status(HttpStatus.OK)
               .body(customerDto);
    }
}
