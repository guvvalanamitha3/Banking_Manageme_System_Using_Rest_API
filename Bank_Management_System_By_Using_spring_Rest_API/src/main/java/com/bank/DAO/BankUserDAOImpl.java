package com.bank.DAO;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.BankUserDetails;
import com.bank.repository.BankUserRepository;
@Component
public class BankUserDAOImpl implements BankUserDAO{
	@Autowired
	BankUserRepository bankUserRepository;
	@Override
	public BankUserDetails userRegistration(BankUserDetails bankUserDetails) {
		return bankUserRepository.save(bankUserDetails);
	}
	@Override
	public List<BankUserDetails> getAllUserDetails() {
		return bankUserRepository.findAll();
	}
	@Override
	public BankUserDetails updateAccountNumberAndPinByUsingId(int id) {
		return bankUserRepository.findById(id);
	}
	@Override
	public BankUserDetails updateUserDetails(BankUserDetails bankUserDetails) {
		return bankUserRepository.save(bankUserDetails);
		
	}
	@Override
	public int deleteUserDetailsByUsingEmailid(String emailid) {
		
		return bankUserRepository.deleteByEmailid(emailid);
	}
	@Override
	public Boolean userlogin(String emailid, int pin) {
		return bankUserRepository.existsByEmailidAndPin(emailid, pin);
		
	}
	/*@Override
	public BankUserDetails updateAmountByUsingAccountNumber(long accountnumber) {
		return bankUserRepository.findByAccountnumber(accountnumber);
	}
	@Override
	public BankUserDetails debitAmountByUsingAccountNumber(long accountnumber) {
		return bankUserRepository.findByAccountnumber(accountnumber);
	}*/
	@Override
	public BankUserDetails getUserDetailsByUsingAccountNumber(long accountNumber) {
		// TODO Auto-generated method stub
		return bankUserRepository.findByAccountnumber(accountNumber);
	}
	@Override
	public BankUserDetails updateAcceptedDetailsById(int id) {
		// TODO Auto-generated method stub
		return bankUserRepository.findById(id);
	}
	@Override
	public BankUserDetails getDetailsByPin(int pin) {
		return bankUserRepository.findByPin(pin);
	}
	@Override
	public BankUserDetails getUserDetailsById(int id) {
		// TODO Auto-generated method stub
		return bankUserRepository.findById(id);
	}
	@Override
	public BankUserDetails getUserDetailsByMobileNumber(long mobilenumber) {
		// TODO Auto-generated method stub
		return bankUserRepository.findByMobilenumber(mobilenumber);
	}
	@Override
	public BankUserDetails updatePinByUsingAccountNumber(long accountnumber) {
		// TODO Auto-generated method stub
		return bankUserRepository.findByAccountnumber(accountnumber);
	}
	
}
