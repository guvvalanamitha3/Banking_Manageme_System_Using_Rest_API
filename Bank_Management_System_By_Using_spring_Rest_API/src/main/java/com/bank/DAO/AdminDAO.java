package com.bank.DAO;

import java.util.List;

import com.bank.entity.Admin;

public interface AdminDAO {
	
	Boolean validateAdminDetailsByUsingEmailidAndPassword(String emailid, String pin);
	List<Admin> getalladmindetails();
	Admin adminRegistration(Admin admin);
}
