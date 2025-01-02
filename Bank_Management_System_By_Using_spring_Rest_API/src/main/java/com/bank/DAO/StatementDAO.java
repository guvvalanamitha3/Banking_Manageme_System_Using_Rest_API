package com.bank.DAO;

import java.util.List;

import com.bank.entity.BankStatement;
import com.bank.entity.BankUserDetails;

public interface StatementDAO {
	BankStatement updateStatement(BankStatement statement);
	public List<BankStatement> getStatementDetails();
	BankStatement creditByUsingAccountNumber(long accountnumber);
}
