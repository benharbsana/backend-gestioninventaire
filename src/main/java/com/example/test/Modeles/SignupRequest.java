package com.example.test.Modeles;


import lombok.Data;

@Data
public class SignupRequest {
	private String userName; 
	private String lastname;
	private String email;
	private String departement;
	private Integer telephone;
	private String password;
	private String[] roles;
}
