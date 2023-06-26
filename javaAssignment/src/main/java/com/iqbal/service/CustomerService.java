/**
 * 
 */
package com.iqbal.service;

import java.util.List;

import javax.validation.Valid;

import com.iqbal.entity.Customer;

/**
 * 
 */
public interface CustomerService {

	void saveCustomer(@Valid Customer customer);

	List<Customer> getAll();

	Customer getById(long id);

}
