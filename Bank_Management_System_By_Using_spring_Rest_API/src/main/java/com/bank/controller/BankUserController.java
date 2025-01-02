package com.bank.controller;

import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.DAO.BankUserDAO;
import com.bank.entity.BankStatement;
import com.bank.entity.BankUserDetails;
import com.bank.entity.GlobalValues;
import com.bank.service.BankUserService;
import com.bank.service.ResponseStructure;
import com.bank.service.StatementService;

@Controller
@CrossOrigin("*")
public class BankUserController {
	@Autowired
	BankUserDAO bankuserDAO;
	@Autowired
	BankUserService bankUserService;
	@Autowired
	StatementService statementService;
	@Autowired
	GlobalValues values;
	/* It is used to specify the insertion operation
	 * It is used to create the end point or url pattern or API
	 * It is used to map the end point with the method
	 * 
	 * localhost:1919/userregistration*/
	@PostMapping("/userregistration")
	@ResponseBody
	public ResponseEntity<ResponseStructure<BankUserDetails>> userRegistration(@RequestBody BankUserDetails bankUserDetails)
	{
		//System.out.println(bankUserDetails);
		//return bankuserDAO.userRegistration(bankUserDetails);
		return bankUserService.userinfromation(bankUserDetails);
	}
	@GetMapping("/userdetails")
	@ResponseBody
	public List<BankUserDetails> getuserdetails()
	{
		return bankuserDAO.getAllUserDetails();
	}
	@PutMapping("/acceptpending/{id}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> acceptPinAnAccountNumber(@PathVariable("id") int id)
	{
		return bankUserService.insertAccountNumberAndPin(id);
	}
	@GetMapping("/allaccepteddetails")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllAcceptedDetails()
	{
		return bankUserService.allAcceptedDetails();
	}
	@GetMapping("/allpendingdetails")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllPendingDetails()
	{
		return bankUserService.allPendingDetails();
	}
	@GetMapping("/allclosingdetails")
	@ResponseBody
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> getAllClosedDetails()
	{
		return bankUserService.allClosingDetails();
	}
	@DeleteMapping("/deleteuserdetails/{emailid}")
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> deleteDetails(@PathVariable("emailid") String emailid)
	{
		return bankUserService.deleteUserDetailsByUsingEmailid(emailid);
	}
	@GetMapping("/userlogin/{emailid}/{pin}")
	//@ResponseBody
	public ResponseEntity<ResponseStructure<String>> userloginPage(@PathVariable("emailid") String emailid,@PathVariable("pin") int pin)
	{
		//System.out.println("Login Successfully");
		values.setPin(pin);
		return bankUserService.userLogin(emailid, pin);
	}
//	@PostMapping("/creditamount/{accountnumber}/{amount}")
//	public ResponseEntity<ResponseStructure<String>> updateAmountByUsingAccountNumber(@PathVariable("accountnumber") long accountnumber,@PathVariable("amount") double amount,@RequestParam boolean credit)
//	{
//		return bankUserService.updateAmountByUsingAccountNumber(accountnumber, amount, credit);
//	}
//	@PostMapping("/debitamount/{accountnumber}/{amount}")
//	public ResponseEntity<ResponseStructure<String>> debitAmount(@PathVariable("accountnumber") long useraccountnumber,@PathVariable("amount") double amountdebit, @RequestParam boolean debit) {
//	    return bankUserService.debitAmountByUsingAccountNumber(useraccountnumber, amountdebit, debit);
//	}
//	@GetMapping("/checkbalance/{accountnumber}")
//	public ResponseEntity<ResponseStructure<String>> checkBalance(@PathVariable("accountnumber") long accountnumber)
//	{
//		return bankUserService.checkBalance(accountnumber);
//	}
	@PutMapping("/modifyuser")
	public ResponseEntity<ResponseStructure<String>> modifyUser(@RequestBody BankUserDetails bankUserDetails)
	{
		return bankUserService.modifyUserDetails(bankUserDetails);
	}
	@PostMapping("/creditamount/{accountnumber}/{amount}")
	public ResponseEntity<ResponseStructure<String>> creditAmount(@PathVariable("accountnumber") long accountnumber,@PathVariable("amount") double amount)
	{
		int pin=values.getPin();
		BankUserDetails details=bankuserDAO.getDetailsByPin(pin);
		if(details.getAccountnumber()!=accountnumber)
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Invalid Account Number");
			responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
		}
		return statementService.creditAmount(accountnumber, amount);
	}
	@PostMapping("/debitamount/{accountnumber}/{amount}")
	public ResponseEntity<ResponseStructure<String>> debitAmount(@PathVariable("accountnumber") long accountnumber,@PathVariable("amount") double amount) {
		int pin=values.getPin();
		BankUserDetails details=bankuserDAO.getDetailsByPin(pin);
		if(details.getAccountnumber()!=accountnumber)
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Invalid Account Number");
			responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
		}
	    return statementService.debitAmount(accountnumber, amount);
	}
	@GetMapping("/checkbalance/{accountnumber}")
	public ResponseEntity<ResponseStructure<Double>> checkBalance(@PathVariable("accountnumber") long accountnumber)
	{
		int pin=values.getPin();
		BankUserDetails details=bankuserDAO.getDetailsByPin(pin);
		if(details.getAccountnumber()!=accountnumber)
		{
			ResponseStructure<Double> responseStructure=new ResponseStructure<Double>();
			responseStructure.setResponsemsg("Invalid Account Number");
			responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ResponseStructure<Double>>(responseStructure,HttpStatus.BAD_REQUEST);
		}
		return statementService.balanceAmount(accountnumber);
	}
	@GetMapping("/bankstatement")
	public ResponseEntity<ResponseStructure<List<BankStatement>>> balanceHistory()
	{
		int pin=values.getPin();
		return statementService.getStatementDetails(pin);
	}
	@PostMapping("/mobiletomobile/{sender}/{receiver}/{amount}")
	public ResponseEntity<ResponseStructure<String>> mobileToMobile(@PathVariable("sender") long sender, @PathVariable("receiver") long receiver,@PathVariable("amount") double amount)
	{
		return statementService.mobileToMobileTransaction(sender, receiver, amount);
	}
	@PostMapping("/changepin/{accountnumber}/{newpin}")
	 public ResponseEntity<ResponseStructure<String>> changePin(@PathVariable("accountnumber")long accountnumber,@PathVariable("newpin")int pin) {
		return bankUserService.updatePinByUsingAccountNumber(accountnumber, pin);
	 }
	@PutMapping("closeaccount/{accountnumber}")
	 public ResponseEntity<ResponseStructure<String>> closingAccount(@PathVariable("accountnumber") long accountnumber) {
		 int pin = values.getPin();
			BankUserDetails detailsByPin = bankuserDAO.getDetailsByPin(pin);
			if(detailsByPin.getAccountnumber()!=accountnumber) {
				ResponseStructure<String> structure=new ResponseStructure<String>();
				structure.setResponsemsg("Invalid AccountNumber");
				structure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
				return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);			
				
			}
		 return bankUserService.closeAccountByUsingAccountNumber(accountnumber); 
	 }
}