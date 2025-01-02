package com.bank.service;

import com.bank.entity.Admin;
import com.bank.exception.AdminException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStructure<T> {
	private String responsemsg;
	private int httpstatuscode;
	private T data;
}
