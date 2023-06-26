/**
 * 
 */
package com.iqbal.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "Customer_Tbl")
public class Customer {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long Id;

	private String customerID;
	private String shortName;

	private String fullName;
	@Valid
	@Length(max = 80, message = "*Your Address 1 maximum 80 characters")
	private String address1;
	@Valid
	@Length(max = 80, message = "*Your Address 2 maximum 80 characters")
	private String address2;
	@Valid
	@Length(max = 80, message = "*Your Address 3 maximum 80 characters")
	private String address3;
	private String city;

	@Valid
	@NotBlank(message = "*Please input Postal Code")
	@Length(min = 1, max = 80, message = "*Your Postal Code minimum 1 characters")
	private String postalCode;

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "Customer [Id=" + Id + ", customerID=" + customerID + ", shortName=" + shortName + ", fullName="
				+ fullName + ", address1=" + address1 + ", address2=" + address2 + ", address3=" + address3 + ", city="
				+ city + ", postalCode=" + postalCode + "]";
	}

}
