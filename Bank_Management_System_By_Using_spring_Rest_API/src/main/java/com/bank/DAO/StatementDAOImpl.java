package com.bank.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank.entity.BankStatement;
import com.bank.entity.BankUserDetails;
import com.bank.repository.StatementRepository;
@Component
public class StatementDAOImpl implements StatementDAO{

	@Autowired 
	StatementRepository repository;
	@Override
	public BankStatement updateStatement(BankStatement statement) {
		return repository.save(statement);
	}

	@Override
	public List<BankStatement> getStatementDetails() {
		return repository.findAll();
	}

	@Override
	public BankStatement creditByUsingAccountNumber(long accountnumber) {
		return repository.findByAccountnumber(accountnumber);
		
	}

}
