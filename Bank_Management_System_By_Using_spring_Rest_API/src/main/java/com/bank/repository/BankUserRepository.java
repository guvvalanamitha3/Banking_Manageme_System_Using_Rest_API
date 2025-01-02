package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bank.entity.BankUserDetails;
import com.zaxxer.hikari.util.FastList;

public interface BankUserRepository extends JpaRepository<BankUserDetails, Integer>{
	  BankUserDetails findById(int id);
	  //BankUserDetails deleteById(int id);
	  int deleteByEmailid(String emailid);
	  Boolean existsByEmailidAndPin(String emailid,int pin);
	 //BankUserDetails serachById(int id);
	 BankUserDetails findByAccountnumber(long accountnumber);
	 BankUserDetails findByPin(int pin);
	 BankUserDetails findByMobilenumber(long mobilenumber);
}
