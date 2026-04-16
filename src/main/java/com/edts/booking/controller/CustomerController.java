package com.edts.booking.controller;

import com.edts.booking.model.dto.request.AddNewCustomer;
import com.edts.booking.model.dto.response.CustomerResponse;
import com.edts.booking.model.entity.CustomerEntity;
import com.edts.booking.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody AddNewCustomer request){
        log.info("add new customer with request: {}", request);
        customerService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<?> getCustomer(@RequestParam(name = "name", required = false) String name){
        List<CustomerResponse> result = customerService.getCustomer(name);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
