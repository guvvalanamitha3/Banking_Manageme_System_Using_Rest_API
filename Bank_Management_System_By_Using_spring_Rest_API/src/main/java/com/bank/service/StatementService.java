package com.bank.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.entity.BankStatement;

public interface StatementService {

	ResponseEntity<ResponseStructure<String>> creditAmount(long accountnumber, double amount);

	ResponseEntity<ResponseStructure<String>> debitAmount(long accountnumber, double amount);

	ResponseEntity<ResponseStructure<Double>> balanceAmount(long accountNumber);

	ResponseEntity<ResponseStructure<List<BankStatement>>> getStatementDetails(int pin);

	ResponseEntity<ResponseStructure<String>> mobileToMobileTransaction(long sender, long receiver, double amount);
	
}
