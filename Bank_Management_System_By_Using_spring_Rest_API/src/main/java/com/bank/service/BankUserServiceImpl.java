package com.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.BankUserDAO;
import com.bank.entity.BankUserDetails;
import com.bank.exception.BankUserException;
import com.bank.repository.BankUserRepository;

import jakarta.transaction.Transactional;
import lombok.Data;
@Component
public class BankUserServiceImpl implements BankUserService{
	@Autowired
	BankUserDAO bankuserDAO;
	@Autowired
	BankUserRepository bankUserRepository;
	Random random=new Random();
	@Override
	public ResponseEntity<ResponseStructure<BankUserDetails>> userinfromation(BankUserDetails bankuserdetails)
	{
		List<BankUserDetails> alluserdetails=bankuserDAO.getAllUserDetails();
		boolean emailidcheck=alluserdetails.stream().anyMatch((user->user.getEmailid().equalsIgnoreCase(bankuserdetails.getEmailid())));
		if(emailidcheck)
		{
			throw new BankUserException("Emailid Already Existed");
		}
		boolean mobilenumcheck=alluserdetails.stream().anyMatch((user->user.getMobilenumber()==bankuserdetails.getMobilenumber()));
		if(mobilenumcheck)
		{
			throw new BankUserException("Mobile Number Already Existed");
		}
		boolean adharcheck=alluserdetails.stream().anyMatch((useradhar->useradhar.getAadharnumber()==bankuserdetails.getAadharnumber()));
		if(adharcheck)
		{
			throw new BankUserException("Aadhar Number Already Existed");
		}
		boolean pannumcheck=alluserdetails.stream().anyMatch((user->user.getPannumber().equals(bankuserdetails.getPannumber())));
		if(pannumcheck)
		{
			throw new BankUserException("Pan Number Already Exist");
		}
		bankuserdetails.setStatus("Pending");
		  BankUserDetails userdetails=bankuserDAO.userRegistration(bankuserdetails);
		  if(userdetails.getId()>0)
		  {
			  //System.out.println("Registration Successfully");
			 // return "Registration Successfully";
			  ResponseStructure<BankUserDetails> responseStructure=new ResponseStructure<BankUserDetails>();
			  responseStructure.setResponsemsg("Registration Successfull");
			  responseStructure.setHttpstatuscode(HttpStatus.CREATED.value());
			  responseStructure.setData(userdetails);
			  return new ResponseEntity<ResponseStructure<BankUserDetails>>(responseStructure,HttpStatus.CREATED);
			  
		  }
		  else
		  {
			  //System.out.println("Server Error");
			   throw new BankUserException("server error");

		  }
		
		 
		
	}
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> insertAccountNumberAndPin(int id) {
		boolean astatus =true;
		List<BankUserDetails> bankUserDetails=bankuserDAO.getAllUserDetails();
		BankUserDetails bankuserdetails=bankuserDAO.updateAccountNumberAndPinByUsingId(id);
		List<BankUserDetails> allaccepteddetails=bankUserDetails.stream().filter((user->user.getStatus().equalsIgnoreCase("Accepted"))).collect(Collectors.toList());
		while(astatus) {
			int taccountnumbers=random.nextInt(10000000);
			
			if(taccountnumbers<=999999)
			{
				taccountnumbers+=1000000;
			}
			int accountnumber=taccountnumbers;
			boolean accountmatch=allaccepteddetails.stream().anyMatch((user->user.getAccountnumber()==accountnumber));
			if(accountmatch)
			{
				
			}
			else
			{
				bankuserdetails.setAccountnumber(accountnumber);
				astatus=false;
			}
		}
		boolean pstatus=true;
		while(pstatus)
		{
			int tpin=random.nextInt(10000);
			if(tpin<=999)
			{
				tpin+=1000;
			}
			int pin=tpin;
			boolean pinmatch=allaccepteddetails.stream().anyMatch((user->user.getPin()==pin));
			if(pinmatch)
			{
				
			}
			else
			{
				bankuserdetails.setPin(pin);
				pstatus=false;
			}
		}
		bankuserdetails.setStatus("Accepted");
		BankUserDetails bankDetails=bankuserDAO.updateUserDetails(bankuserdetails);
		if(bankDetails!=null)
		{
			System.out.println("Accepted");
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("Created");
			responseStructure.setHttpstatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.OK);
		}
		else
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("Not Created");
			responseStructure.setHttpstatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.OK);
		}
	}
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> allAcceptedDetails() {
		List<BankUserDetails> allUserDetails=bankuserDAO.getAllUserDetails();
		List<BankUserDetails> allaccepteddetails=allUserDetails.stream().filter((user->user.getStatus().equalsIgnoreCase("Accepted"))).collect(Collectors.toList());
		if(allaccepteddetails.size()!=0)
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("All accepted details");
			responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.FOUND);
		}
		else
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("No accepted details found");
			responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.NOT_FOUND);
		}
		
	}
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> allPendingDetails() {
		List<BankUserDetails> allUserDetails=bankuserDAO.getAllUserDetails();
		List<BankUserDetails> allaccepteddetails=allUserDetails.stream().filter((user->user.getStatus().equalsIgnoreCase("Pending"))).collect(Collectors.toList());
		if(allaccepteddetails.size()!=0)
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("All Pending details");
			responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.FOUND);
		}
		else
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("No Pending details found");
			responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.NOT_FOUND);
		}
	}
	@Override
	public ResponseEntity<ResponseStructure<List<BankUserDetails>>> allClosingDetails() {
		List<BankUserDetails> allUserDetails=bankuserDAO.getAllUserDetails();
		List<BankUserDetails> allaccepteddetails=allUserDetails.stream().filter((user->user.getStatus().equalsIgnoreCase("Closed"))).collect(Collectors.toList());
		if(allaccepteddetails.size()!=0)
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("All Closed Details");
			responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.FOUND);
		}
		else
		{
			ResponseStructure<List<BankUserDetails>> responseStructure=new ResponseStructure<List<BankUserDetails>>();
			responseStructure.setData(allaccepteddetails);
			responseStructure.setResponsemsg("No Closed details found");
			responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<List<BankUserDetails>>>(responseStructure,HttpStatus.NOT_FOUND);
		}
	}
	@Transactional
	@Override
	public ResponseEntity<com.bank.service.ResponseStructure<String>> deleteUserDetailsByUsingEmailid(String emailid) {
		if(bankuserDAO.deleteUserDetailsByUsingEmailid(emailid)>0)
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Deleted Successfully");
			responseStructure.setData(emailid);
			responseStructure.setHttpstatuscode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
		}
		else
		{
			throw new BankUserException("Status does not closed");
		}
}
	@Override
	public ResponseEntity<ResponseStructure<String>> userLogin(String emailid, int pin) {
		BankUserDetails bankUserDetails=new BankUserDetails();
		List<BankUserDetails> alldetails=bankuserDAO.getAllUserDetails();
		if(bankuserDAO.userlogin(emailid, pin) && pin!=0)
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Login successfully");
			responseStructure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure ,HttpStatus.ACCEPTED);
		}
		else
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Invalid Emailid or Pin Number");
			responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure ,HttpStatus.NOT_FOUND);
		}
		
	}
	@Override
	public ResponseEntity<ResponseStructure<String>> modifyUserDetails(BankUserDetails bankUserDetails) {
		BankUserDetails alldetails=bankuserDAO.getUserDetailsById(bankUserDetails.getId());
		 if (bankUserDetails.getId() == null) {
		        throw new IllegalArgumentException("User ID cannot be null");
		    }
		if(bankUserDetails.getName().length()<=2)
		{
			System.out.println("Invalid length");
		}
		List<BankUserDetails> details=bankuserDAO.getAllUserDetails();
		details=details.stream().filter(user->user.getId()!=bankUserDetails.getId()).collect(Collectors.toList());
		if(details.stream().anyMatch(user->user.getEmailid().equalsIgnoreCase(bankUserDetails.getEmailid())))
		{
			throw new BankUserException("Already Emailid used");
		}
		if(bankUserDetails.getMobilenumber()<6000000000l || bankUserDetails.getMobilenumber()>9999999999l)
		{
			throw new BankUserException("Invalid mobile number");
		}
		if(details.stream().anyMatch(user->user.getMobilenumber()==bankUserDetails.getMobilenumber()))
		{
			throw new BankUserException("Already Mobile Number used");
		}
		alldetails.setName(bankUserDetails.getName());
		alldetails.setEmailid(bankUserDetails.getEmailid());
		alldetails.setMobilenumber(bankUserDetails.getMobilenumber());
		bankUserRepository.save(alldetails);
		BankUserDetails bankDetails=bankuserDAO.userRegistration(alldetails);
		if(bankDetails!=null)
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Updated Successfully");
			responseStructure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.ACCEPTED);
		}
		else
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Not Updated");
			responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
		}
	}
	@Override
	public ResponseEntity<ResponseStructure<String>> updatePinByUsingAccountNumber(long accountnumber,int newPin) {
		BankUserDetails userdetails=bankuserDAO.updatePinByUsingAccountNumber(accountnumber);
		List<BankUserDetails> details=bankuserDAO.getAllUserDetails();
		List<BankUserDetails> allaccepteddetails=details.stream()
				.filter((user->user.getStatus().equalsIgnoreCase("Accepted")))
				.collect(Collectors.toList());
	      boolean oldPin=allaccepteddetails.stream().anyMatch((user->user.getPin()==newPin));
		if(oldPin) {
			throw new BankUserException("Pin is Already in Use");
		}
		userdetails.setPin(newPin);
		bankuserDAO.updateUserDetails(userdetails);
		 ResponseStructure<String> responseStructure = new ResponseStructure<>();
		    responseStructure.setResponsemsg("PIN updated successfully");
		    responseStructure.setHttpstatuscode(HttpStatus.OK.value());
		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		}
	@Override
	public ResponseEntity<ResponseStructure<String>> closeAccountByUsingAccountNumber(long accountnumber) {
	BankUserDetails userDetails=bankuserDAO.getUserDetailsByUsingAccountNumber(accountnumber);
		if(userDetails==null) {
			 ResponseStructure<String> responseStructure = new ResponseStructure<>();
	            responseStructure.setResponsemsg("Account not found");
	            responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
	            return new ResponseEntity<>(responseStructure, HttpStatus.NOT_FOUND);
		}
		    userDetails.setStatus("Closed");
		    userDetails.setAccountnumber(0);
		    userDetails.setPin(0);
	        BankUserDetails details= bankuserDAO.updateUserDetails(userDetails);
	        ResponseStructure<String> responseStructure = new ResponseStructure<>();
	        responseStructure.setResponsemsg("successfull");
	        responseStructure.setHttpstatuscode(HttpStatus.OK.value());
	        responseStructure.setData("Status Changed To Closed of account "+userDetails.getAccountnumber());
	        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}
}