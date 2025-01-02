package com.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bank.DAO.AdminDAO;
import com.bank.entity.Admin;
import com.bank.exception.AdminException;
import com.bank.repository.AdminRepository;
@Component
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminRepository adminRepository;
	@Autowired
	AdminDAO adminDAO;
	@Override
	public ResponseEntity<ResponseStructure<String>> adminLogin(String emailid,String pin) {
		List<Admin> admindetails=adminDAO.getalladmindetails();
		Admin admin=new Admin();
		//boolean emailidcheck=alluserdetails.stream().anyMatch((user->user.getEmailid().equalsIgnoreCase(bankuserdetails.getEmailid())));
		boolean emailidcheck=admindetails.stream().anyMatch((user->user.getEmailid().equals(admin.getEmailid())));
		if(emailidcheck)
		{
			throw new AdminException("Emailid already Existed");
		}
		boolean pincheck=admindetails.stream().anyMatch((user->user.getPin().equals(admin.getPin())));
		if(pincheck)
		{
			throw new AdminException("Pin already Existed");
		}
		if(adminDAO.validateAdminDetailsByUsingEmailidAndPassword(emailid, pin))
		{
			ResponseStructure<String> responseStructure=new ResponseStructure<String>();
			responseStructure.setResponsemsg("Login Successfully");
			responseStructure.setHttpstatuscode(HttpStatus.ACCEPTED.value());
			//responseStructure.getData();
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.ACCEPTED);
		}
		else
		{
			throw new AdminException("Invalid Credentials");
		}
	}
	@Override
	public ResponseEntity<ResponseStructure<Admin>> adminInformation(Admin admin) {
		List<Admin> admindetails=adminDAO.getalladmindetails();
		boolean emailidcheck=admindetails.stream().anyMatch((user->user.getEmailid().equals(admin.getEmailid())));
		if(emailidcheck)
		{
			throw new AdminException("Emailid already Existed");
		}
		boolean pincheck=admindetails.stream().anyMatch((email->email.getPin().equals(admin.getPin())));
		if(pincheck)
		{
			throw new AdminException("Pin already Existed");
		}
		Admin admin1=adminDAO.adminRegistration(admin);
		if(admin1.getId()>0)
		{
			ResponseStructure<Admin> responseStructure=new ResponseStructure<Admin>();
			responseStructure.setResponsemsg("Registration Successfully");
			responseStructure.setHttpstatuscode(HttpStatus.CREATED.value());
			responseStructure.setData(admin1);
			return new ResponseEntity<ResponseStructure<Admin>>(responseStructure,HttpStatus.CREATED);
		}
		else
		{
			throw new AdminException("server error");
		}
	}

}
