package com.example.test.message;

import lombok.Data;

@Data
public class ServerResponse {
	private String status;
	private String message;
	private String authToken;
	private String userType;
	

}
