package com.easybytes.controller;

import com.easybytes.model.AccountTransactions;
import com.easybytes.model.Customer;
import com.easybytes.repository.AccountTransactionsRepository;
import com.easybytes.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final AccountTransactionsRepository accountTransactionsRepository;

    private final CustomerRepository customerRepository;

    @GetMapping("/myBalance")
    @PostAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        return optionalCustomer.map(customer -> accountTransactionsRepository
                .findByCustomerIdOrderByTransactionDtDesc(customer.getId())).orElse(null);
    }
}
