package com.example.test.Modeles;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
	private User user ;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	  @JoinTable(name = "inventairemateriel", 
	      joinColumns = @JoinColumn(name="inventaire_id"),
	      inverseJoinColumns = @JoinColumn(name="materiel_id"))
	private List<Materiels> materiels = new ArrayList<>();
	
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
