package com.bank.service;

	import java.sql.Date;
	import java.sql.Time;
	import java.time.LocalDate;
	import java.time.LocalTime;
	import java.util.List;
	import java.util.stream.Collectors;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.stereotype.Component;

	import com.bank.DAO.BankUserDAO;
	import com.bank.DAO.StatementDAO;
import com.bank.entity.BankStatement;
import com.bank.entity.BankUserDetails;
import com.bank.exception.BankUserException;
	@Component
	public class StatementServiceImpl implements StatementService{
		
		 @Autowired
		   StatementDAO statementDao;
		 @Autowired
		 BankUserDAO bankUserDao;
		 

		 @Override
		 public ResponseEntity<ResponseStructure<String>> creditAmount(long accountnumber, double amount) {
			 BankUserDetails userDetails=bankUserDao.getUserDetailsByUsingAccountNumber(accountnumber);
			 if(amount>=0)
			 {
				 if(userDetails.getAccountnumber()!=0)
				 {
					 double balanceamount=userDetails.getAmount()+amount;
					 userDetails.setAmount(balanceamount);
					 BankUserDetails userdetails=bankUserDao.updateUserDetails(userDetails);
					 BankStatement bankStatement=new BankStatement();
					 bankStatement.setAccountnumber(accountnumber);
					 bankStatement.setBalanceamount(balanceamount);
					 bankStatement.setTransactionamount(amount);
					 bankStatement.setTransactiondate(Date.valueOf(LocalDate.now()));
					 bankStatement.setTransactiontime(Time.valueOf(LocalTime.now()));
					 bankStatement.setTransactiontype("CREDIT");
					 statementDao.updateStatement(bankStatement);
					 ResponseStructure<String> responseStructure=new ResponseStructure<String>();
					 responseStructure.setResponsemsg("Credited Successfully and Balance Amount:"+balanceamount);
					 responseStructure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
					 return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.ACCEPTED);
				 }
				 else
				 {
					 ResponseStructure<String> responseStructure=new ResponseStructure<String>();
					 responseStructure.setResponsemsg("Not Credited");
					 responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
					 return new ResponseEntity<ResponseStructure<String>>(HttpStatus.BAD_REQUEST);
				 }
			 }
			 else
			 {
				 throw new BankUserException("Failed to fetch amount");
			 }
		 } 
		 @Override
		 public ResponseEntity<ResponseStructure<String>> debitAmount(long accountnumber, double amount) {
			 BankUserDetails bankUserDetails=bankUserDao.getUserDetailsByUsingAccountNumber(accountnumber);
			 if(bankUserDetails.getAccountnumber()!=0)
			 {
				 double userbalance=bankUserDetails.getAmount();
				 if(amount<=userbalance)
				 {
					 userbalance=userbalance-amount;
					 bankUserDetails.setAmount(userbalance);
					 BankUserDetails userdetails=bankUserDao.updateUserDetails(bankUserDetails);
					 BankStatement bankStatement=new BankStatement();
					 bankStatement.setAccountnumber(accountnumber);
					 bankStatement.setBalanceamount(userbalance);
					 bankStatement.setTransactionamount(amount);
					 bankStatement.setTransactiondate(Date.valueOf(LocalDate.now()));
					 bankStatement.setTransactiontime(Time.valueOf(LocalTime.now()));
					 bankStatement.setTransactiontype("DEBIT");
					 statementDao.updateStatement(bankStatement);
					 ResponseStructure<String> responseStructure=new ResponseStructure<String>();
					 responseStructure.setResponsemsg("Debited successfully and Balance Amount:"+userbalance);
					 responseStructure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
					 return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.ACCEPTED);
				 }
				 else
				 {
					 ResponseStructure<String> responseStructure=new ResponseStructure<String>();
					 responseStructure.setResponsemsg("Not Debited");
					 responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
					 return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
				 }
			 }
			 else
			 {
				 throw new BankUserException("Failed to fetch amount");
			 }
			}
		@Override
			public ResponseEntity<ResponseStructure<Double>> balanceAmount(long accountNumber) {
			    BankUserDetails userDetails = bankUserDao.getUserDetailsByUsingAccountNumber(accountNumber);
			    if (userDetails!=null) {
			    double currentBalance = userDetails.getAmount();
			    ResponseStructure<Double> responseStructure = new ResponseStructure<Double>();
			    responseStructure.setResponsemsg("Balance fetched successfully."+currentBalance);
			    responseStructure.setHttpstatuscode(HttpStatus.OK.value());
			    responseStructure.setData(currentBalance);
			    return new ResponseEntity<ResponseStructure<Double>>(responseStructure, HttpStatus.OK);
			    }
			    
	
			    else
			    {
			    	ResponseStructure<Double> responseStructure=new ResponseStructure<Double>();
					 responseStructure.setResponsemsg("Invalid Account Number");
					 responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
					 return new ResponseEntity<ResponseStructure<Double>>(responseStructure,HttpStatus.BAD_REQUEST);
			    }
			}



		@Override
		public ResponseEntity<ResponseStructure<List<BankStatement>>> getStatementDetails(int pin) {
			BankUserDetails detailsByPin = bankUserDao.getDetailsByPin(pin);
			long accountNumber=detailsByPin.getAccountnumber();
			List<BankStatement> statement=statementDao.getStatementDetails();
			statement=statement.stream().filter(s->s.getAccountnumber()==accountNumber).collect(Collectors.toList());
			if(statement!=null) {
				ResponseStructure<List<BankStatement>> responseStructure= new  
						ResponseStructure<List<BankStatement>>();
				responseStructure.setData(statement);
				responseStructure.setResponsemsg("fetched statement");
				responseStructure.setHttpstatuscode(HttpStatus.FOUND.value());
				return new ResponseEntity<ResponseStructure<List<BankStatement>>>(responseStructure,HttpStatus.FOUND);
			}
			else {
				ResponseStructure<List<BankStatement>> responseStructure=new ResponseStructure<List<BankStatement>>();
				responseStructure.setData(statement);
				responseStructure.setResponsemsg("No Details found");
				responseStructure.setHttpstatuscode(HttpStatus.NOT_FOUND.value());
				return new ResponseEntity<ResponseStructure<List<BankStatement>>>(responseStructure,HttpStatus.NOT_FOUND);
			}
		}
		@Override
		public ResponseEntity<ResponseStructure<String>> mobileToMobileTransaction(long sender, long receiver, double amount) {
		    BankUserDetails senderMobile=bankUserDao.getUserDetailsByMobileNumber(sender);
		    BankUserDetails receiverMobile=bankUserDao.getUserDetailsByMobileNumber(receiver);
		    if(senderMobile==null){
		        throw new BankUserException("Sender mobile number not found");
		    }
		    if(receiverMobile==null) {
		        throw new BankUserException("Receiver mobile number not found");
		    }
		    if(senderMobile.getAmount()<amount){
		        throw new BankUserException("Insufficient balance");
		    }
		    senderMobile.setAmount(senderMobile.getAmount() - amount);
		    receiverMobile.setAmount(receiverMobile.getAmount() + amount);
		    bankUserDao.updateUserDetails(senderMobile);
		    bankUserDao.updateUserDetails(receiverMobile);
		    BankStatement debitStatement = new BankStatement();
		    debitStatement.setAccountnumber(senderMobile.getAccountnumber());
		    debitStatement.setTransactiontype("Debit");
		    debitStatement.setTransactionamount(amount);
		    debitStatement.setBalanceamount(senderMobile.getAmount());
		    debitStatement.setTransactiondate(Date.valueOf(LocalDate.now()));
		    debitStatement.setTransactiontime(Time.valueOf(LocalTime.now()));
		    statementDao.updateStatement(debitStatement);

		    BankStatement creditStatement = new BankStatement();
		    creditStatement.setAccountnumber(receiverMobile.getAccountnumber());
		    creditStatement.setTransactiontype("Credit");
		    creditStatement.setTransactionamount(amount);
		    creditStatement.setBalanceamount(receiverMobile.getAmount());
		    creditStatement.setTransactiondate(Date.valueOf(LocalDate.now()));
		    creditStatement.setTransactiontime(Time.valueOf(LocalTime.now()));
		    statementDao.updateStatement(creditStatement);

		    ResponseStructure<String> responseStructure = new ResponseStructure<>();
		    responseStructure.setResponsemsg("Amount transferred successfully through mobile number");
		    responseStructure.setHttpstatuscode(HttpStatus.OK.value());
		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		}
	}
