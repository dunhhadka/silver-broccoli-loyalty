package org.example.loyalty.loyalty.interfaces.rest;

import org.example.loyalty.loyalty.application.model.customer.CustomerFilterRequest;
import org.example.loyalty.loyalty.application.model.customer.CustomerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @GetMapping
    public List<CustomerResponse> filter(CustomerFilterRequest request) {
        return List.of();
    }
}
