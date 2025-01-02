package com.bank.DAO;

import java.util.List;
import java.util.Optional;

import com.bank.entity.BankUserDetails;

public interface BankUserDAO {
	BankUserDetails userRegistration(BankUserDetails bankUserDetails);
	public List<BankUserDetails> getAllUserDetails();
	BankUserDetails updateAccountNumberAndPinByUsingId(int id);
	BankUserDetails updateUserDetails(BankUserDetails bankUserDetails);
	int deleteUserDetailsByUsingEmailid(String emailid);
	Boolean userlogin(String emailid,int pin);
//	BankUserDetails updateAmountByUsingAccountNumber(long accountnumber);
//	BankUserDetails debitAmountByUsingAccountNumber(long accountnumber);
	BankUserDetails getUserDetailsByUsingAccountNumber(long accountNumber);
	BankUserDetails updateAcceptedDetailsById(int id);
	BankUserDetails  getDetailsByPin(int pin);
	BankUserDetails getUserDetailsById(int id);
	BankUserDetails getUserDetailsByMobileNumber(long sender);
	BankUserDetails updatePinByUsingAccountNumber(long accountnumber);
}
