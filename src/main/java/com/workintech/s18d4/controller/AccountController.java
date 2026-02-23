package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping
public class AccountController {
    private  AccountService accountService;
    private CustomerService customerService;
    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }
    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable("customerId") long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        if(customer != null) {
            customer.getAccounts().add(account);
            account.setCustomer(customer);
            accountService.save(account);
        }
        else{
            throw new RuntimeException("Customer not found");
        }
    return new AccountResponse(account.getId(),account.getAccountName(),account.getMoneyAmount(),
    new CustomerResponse(customer.getId(),customer.getEmail(),customer.getSalary()));
}
@PutMapping("/{customerId}")
    public AccountResponse update(@RequestBody Account account,@PathVariable long customerId) {
        Customer customer = customerService.find(customerId);
        Account tobeUpdatedAccount = null;
        for(Account account1: customer.getAccounts()){
            if(account.getId()==account1.getId()){
                tobeUpdatedAccount = account1;
            }

        }
        if(tobeUpdatedAccount == null){
            throw new RuntimeException("Account not found" + account.getId());
        }
       int indexOfBeUpdated = customer.getAccounts().indexOf(tobeUpdatedAccount);
        customer.getAccounts().set(indexOfBeUpdated,account);
        account.setCustomer(customer);
        accountService.save(account);
        return new AccountResponse(account.getId(), account.getAccountName(),
                account.getMoneyAmount(), new CustomerResponse(customerId,
                customer.getEmail(), customer.getSalary()));

}
@DeleteMapping("/{id}")
    public AccountResponse remove(@PathVariable long id) {
        Account account = accountService.find(id);
        if(account == null){
            throw new RuntimeException("Account not found" + id);
        }
        accountService.delete(id);
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(),
                new CustomerResponse(account.getCustomer().getId(),account.getCustomer().getEmail(),account.getCustomer().getSalary()));

}
}
