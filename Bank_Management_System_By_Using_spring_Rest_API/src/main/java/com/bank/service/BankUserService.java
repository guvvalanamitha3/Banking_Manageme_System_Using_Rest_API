package com.bank.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.entity.BankUserDetails;

public interface BankUserService {
	ResponseEntity<ResponseStructure<BankUserDetails>> userinfromation(BankUserDetails bankuserdetails);
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> insertAccountNumberAndPin(int id);
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> allAcceptedDetails();
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> allPendingDetails();
	ResponseEntity<ResponseStructure<List<BankUserDetails>>> allClosingDetails();
	ResponseEntity<ResponseStructure<String>> deleteUserDetailsByUsingEmailid(String emailid);
	ResponseEntity<ResponseStructure<String>> userLogin(String emailid,int pin);
	ResponseEntity<ResponseStructure<String>> modifyUserDetails(BankUserDetails bankUserDetails);
//	ResponseEntity<ResponseStructure<String>> updateAmountByUsingAccountNumber(long accountnumber,double amount,boolean creditamount);
//	ResponseEntity<ResponseStructure<String>> debitAmountByUsingAccountNumber(long accountnumber,double amount,boolean debitamount);
//	ResponseEntity<ResponseStructure<String>> checkBalance(long accountnumber);
	ResponseEntity<ResponseStructure<String>> updatePinByUsingAccountNumber(long accountnumber, int newPin);
	ResponseEntity<ResponseStructure<String>> closeAccountByUsingAccountNumber(long accountnumber);
}
