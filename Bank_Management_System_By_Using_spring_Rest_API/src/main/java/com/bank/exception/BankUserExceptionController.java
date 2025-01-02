package com.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.service.ResponseStructure;
/* @ControllerAdvice is used to specify the class is used to handle the exception which are getting
   from the entire application.
   It is going to act as a global exception handle*/
@ControllerAdvice
public class BankUserExceptionController {
	/*To specify which class exception should be handled by this method we make use of
	 the annotation @Exceptionhandler */
	@ExceptionHandler(BankUserException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> bankUserExceptionHandler(BankUserException bankUserException)
	{
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg(bankUserException.getExceptionmsg());
		responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
	}
}
