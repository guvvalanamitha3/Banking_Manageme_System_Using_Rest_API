package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.BankStatement;

public interface StatementRepository extends JpaRepository<BankStatement, Integer> {
	BankStatement findByAccountnumber(long accountnumber);

}
