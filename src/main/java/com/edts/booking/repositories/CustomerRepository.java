package com.edts.booking.repositories;

import com.edts.booking.model.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByCustomerMobileNo(String customerMobileNo);

    @Query("""
    SELECT c FROM CustomerEntity c WHERE :name IS NULL OR LOWER(c.customerName) LIKE :name
""")
    List<CustomerEntity> findByCustomerName(@Param("name") String customerName);
}
