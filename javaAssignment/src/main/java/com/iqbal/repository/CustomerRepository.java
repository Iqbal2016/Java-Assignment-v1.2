/**
 * 
 */
package com.iqbal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iqbal.entity.Customer;

/**
 * 
 */

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
