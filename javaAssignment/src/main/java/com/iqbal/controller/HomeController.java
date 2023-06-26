/**
 * 
 */
package com.iqbal.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.iqbal.entity.Customer;
import com.iqbal.service.CustomerService;

/**
 * 
 */
@RestController
public class HomeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CustomerService customerService;
	
	
	@GetMapping(path = {"/","home"})
	public ModelAndView index(Model model) {
	
		return new ModelAndView("index");
	}
	
	@GetMapping(path = {"/cusm"})
	public ModelAndView cusm(Customer customer, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
	
		return new ModelAndView("customer/cusm.html");
	}
	
	/*
	 * @PostMapping(path = {"/cusm"}) public ModelAndView saveCusm(@Valid Customer
	 * customer, BindingResult bindingResult, Model model, RedirectAttributes
	 * redirectAttributes) { String post = customer.getPostalCode();
	 * logger.info(".......save customer...."+customer.getPostalCode());
	 * 
	 * if (bindingResult.hasErrors()) { model.addAttribute("customer", customer);
	 * redirectAttributes.addFlashAttribute("message", "Registration Failed");
	 * redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
	 * model.addAttribute("Customer", new Customer()); return new
	 * ModelAndView("customer/cusm.html"); } if (post == "") {
	 * redirectAttributes.addFlashAttribute("message", "Registration Failed");
	 * redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
	 * model.addAttribute("Customer", new Customer()); return new
	 * ModelAndView("customer/cusm.html"); } else { //
	 * logger.info(".............user..."+creditRiskMaster+"...."+creditRiskMaster.
	 * getFunded()); customerService.saveCustomer(customer);
	 * //modelAndView.addObject("successMessage",
	 * "User has been registered successfully"); // modelAndView.addObject("user",
	 * new User()); // modelAndView.setViewName("/crmmaster_list");
	 * redirectAttributes.addFlashAttribute("message",
	 * "Customer create successfully");
	 * 
	 * } return new ModelAndView("customer/show"); }
	 */
	@PostMapping(path = {"/cusm"})
	public ModelAndView saveCusm(@Valid Customer customer, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		String post = customer.getPostalCode();
		logger.info(".......save customer...."+customer.getPostalCode());

		if (bindingResult.hasErrors()) {
			logger.info(".......save customer error..1..");

			model.addAttribute("customer", customer);
			redirectAttributes.addFlashAttribute("message", "Registration Failed");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			model.addAttribute("Customer", new Customer());
			return new ModelAndView("customer/cusm.html");
		}
		if (post == "") {
			logger.info(".......save customer error..2..");
			model.addAttribute("customer", customer);
			redirectAttributes.addFlashAttribute("message", "Registration Failed");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			//model.addAttribute("Customer", new Customer());
			return new ModelAndView("customer/cusm");
		}
		else {
			 logger.info(".............save customer...");
			customerService.saveCustomer(customer);
			redirectAttributes.addFlashAttribute("message", "Customer create successfully");
			
		}
		return new ModelAndView("customer/cusm");
	}
	
	@GetMapping(value = "/cusm/list")
	public ModelAndView getAllList(Model model) {

		try {
			List<Customer> cusmList = customerService.getAll();

			System.out.println("cusmList"+cusmList.size());

			model.addAttribute("cusmList", cusmList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("customer/show");
	}
	
	@GetMapping("/cusm/{id}/edit")
	public ModelAndView getAddressByID(@PathVariable("id") long id, Model model) {
		Customer customer = customerService.getById(id);
		System.out.println(customer);
		System.out.println(customer.getCustomerID());


		model.addAttribute("customer", customer);
		return new ModelAndView("address/cusm");
	}

}
