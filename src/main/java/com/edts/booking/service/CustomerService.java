package com.edts.booking.service;

import com.edts.booking.model.dto.request.AddNewCustomer;
import com.edts.booking.model.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    void add(AddNewCustomer request);
    CustomerEntity getCustomer(Long customerId);
    List<CustomerEntity> getCustomer(String name);
}
