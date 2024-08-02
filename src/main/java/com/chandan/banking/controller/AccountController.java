package com.chandan.banking.controller;

import com.chandan.banking.dto.AccountDto;
import com.chandan.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    // add account rest api


    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    // get account REST api

    @GetMapping("{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountById = accountService.getAccountById(id);
        return ResponseEntity.ok(accountById);
    }

    //Deposite RestApi

    @PutMapping("/{id}/deposite")
    public ResponseEntity<AccountDto> deposite(@PathVariable Long id,@RequestBody Map<String, Double> request) {
        AccountDto accountDto = accountService.deposite(id, request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    //withdraw Rest api

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,@RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto withdraw = accountService.withdraw(id, amount);
        return ResponseEntity.ok(withdraw);
    }


    //get all account Rest api
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccount(){
        List<AccountDto> allAccounts = accountService.getAllAccounts();
        return ResponseEntity.ok(allAccounts);
    }

    //delete account rest api

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable  Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully!");
    }
}
