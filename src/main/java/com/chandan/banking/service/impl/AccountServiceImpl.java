package com.chandan.banking.service.impl;

import com.chandan.banking.dto.AccountDto;
import com.chandan.banking.entity.Account;
import com.chandan.banking.mapper.AccountMapper;
import com.chandan.banking.repository.AccountRepository;
import com.chandan.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {

        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposite(Long id, double amount) {

        Account accounts = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        double total = accounts.getBalance() + amount;
        accounts.setBalance(total);
        Account savedAccount = accountRepository.save(accounts);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account accounts = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        if (accounts.getBalance() < amount) {
            throw new RuntimeException("Insufficient amount");
        }

        double total = accounts.getBalance() - amount;

        accounts.setBalance(total);
        Account save = accountRepository.save(accounts);

        return AccountMapper.mapToAccountDto(save);
    }

    @Override
    public List<AccountDto> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        List<AccountDto> collect = accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteAccount(Long id) {

        Account accounts = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        accountRepository.deleteById(id);

    }
}
