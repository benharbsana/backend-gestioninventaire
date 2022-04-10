package com.example.test.message;

import java.util.List;

import com.example.test.Modeles.Materiels;

import lombok.Data;

@Data

public class MaterielResponse {
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private List<Materiels> oblist;


}
