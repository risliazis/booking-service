package com.edts.booking.service.impl;

import com.edts.booking.exception.BadRequestException;
import com.edts.booking.exception.NotFoundException;
import com.edts.booking.model.dto.request.AddNewCustomer;
import com.edts.booking.model.dto.response.CustomerResponse;
import com.edts.booking.model.entity.CustomerEntity;
import com.edts.booking.repositories.CustomerRepository;
import com.edts.booking.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void add(AddNewCustomer request) {

        if (customerRepository.existsByCustomerMobileNo(request.mobileNo().trim())){
            throw new BadRequestException("mobile number already registered");
        }

        try {
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setCustomerEmail(request.email().trim());
            customerEntity.setCustomerName(request.name().trim());
            customerEntity.setCustomerMobileNo(request.mobileNo().trim());
            customerEntity.setCreatedBy("SYSTEM");
            customerRepository.save(customerEntity);
        } catch (Exception ex) {
            throw new BadRequestException("failed creating new customer");
        }
    }

    @Override
    public CustomerEntity getCustomer(Long customerId) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(customerId);
        return customerEntity.orElseThrow(() -> new NotFoundException("customer not found"));
    }

    @Override
    public List<CustomerResponse> getCustomer(String name) {
        String searchName = (name == null) ? null : "%" + name.toLowerCase() + "%";
        List<CustomerEntity> result = customerRepository.findByCustomerName(searchName);
        return result.stream().map(customerEntity ->
            new CustomerResponse(
                    customerEntity.getCustomerId(),
                    customerEntity.getCustomerEmail(),
                    customerEntity.getCustomerName(),
                    customerEntity.getCustomerMobileNo())
        ).toList();
    }

}
