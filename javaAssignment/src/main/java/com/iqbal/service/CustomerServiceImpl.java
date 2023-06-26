/**
 * 
 */
package com.iqbal.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqbal.entity.Customer;
import com.iqbal.repository.CustomerRepository;

import jakarta.transaction.Transactional;

/**
 * 
 */

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public void saveCustomer(@Valid Customer customer) {
		Customer newCustomer = new Customer();
		newCustomer.setId(customer.getId());
		newCustomer.setCustomerID(customer.getCustomerID());
		newCustomer.setShortName(customer.getShortName());
		newCustomer.setFullName(customer.getFullName());
		newCustomer.setAddress1(customer.getAddress1());
		newCustomer.setAddress2(customer.getAddress2());
		newCustomer.setAddress3(customer.getAddress3());
		newCustomer.setCity(customer.getCity());
		newCustomer.setPostalCode(customer.getPostalCode());
		customerRepository.save(newCustomer);
		
	}

	@Override
	@Transactional
	public List<Customer> getAll() {
		// TODO Auto-generated method stub
		System.out.println("get all.."+customerRepository.findAll());
		return customerRepository.findAll();
	}

	@Override
	public Customer getById(long id) {
		// TODO Auto-generated method stub
		return customerRepository.getById(id);
	}

}
