package com.example.test.message;

import java.util.List;

import com.example.test.Modeles.Inventaire;

import lombok.Data;

@Data
public class InventaireResponse {
	
	private String status;
	private String message;
	private String AUTH_TOKEN;
	private List<Inventaire> oblist;

}
