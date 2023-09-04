package com.easybytes.controller;

import com.easybytes.model.Customer;
import com.easybytes.model.Loans;
import com.easybytes.repository.CustomerRepository;
import com.easybytes.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;

    private final CustomerRepository customerRepository;

    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        return optionalCustomer.map(customer -> loanRepository
                .findByCustomerIdOrderByStartDtDesc(customer.getId())).orElse(null);
    }
}
