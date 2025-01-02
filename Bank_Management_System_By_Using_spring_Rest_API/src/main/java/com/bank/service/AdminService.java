package com.bank.service;

import org.springframework.http.ResponseEntity;

import com.bank.entity.Admin;

public interface AdminService {
	ResponseEntity<ResponseStructure<String>> adminLogin(String emailid,String pin);
	ResponseEntity<ResponseStructure<Admin>> adminInformation(Admin admin);
}
