package com.example.test.Modeles;
import java.util.Date;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Entity
@Data

public class Inventaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codeInv;
	private Date dateinv ;
	
	
	@ManyToOne()
	@JsonIgnore
	private User user ;
	
	@ManyToMany ( mappedBy  ="inventaires")
	private List<Materiels> materiels ;
	
	
	public Inventaire(Integer codeInv, Date dateinv, User user, List<Materiels> materiels) {
		super();
		this.codeInv = codeInv;
		this.dateinv = dateinv;
		this.user = user;
		this.materiels = materiels;
	}
	
	
	
	
	
	
	
	


	public Inventaire() {
		super();
	}












	

}
