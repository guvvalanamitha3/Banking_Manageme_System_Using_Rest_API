package com.bank.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bank.service.ResponseStructure;
@ControllerAdvice
public class AdminExceptionController {
	@ExceptionHandler(AdminException.class)
	@ResponseBody
	public ResponseEntity<ResponseStructure<String>> adminExceptionHandler(AdminException adminException)
	{
		ResponseStructure<String> responseStructure=new ResponseStructure<String>();
		responseStructure.setResponsemsg(adminException.getAdminexceptionmsg());
		responseStructure.setHttpstatuscode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.BAD_REQUEST);
	}
}
