package com.example.test.Modeles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Entity
@Data
public class Materiels {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String designation;
	private String etat ;
	private String disponible;
	@Lob
	private byte[] materielimage;
	@ManyToOne
	private Categorie categorie;
    @ManyToMany(mappedBy = "materiels")
    @JsonIgnore
	private List<Inventaire> inventaires ;
    ;
	
	
	public Materiels( String designation , String etat, String disponible,
			byte[] materielimage,  Categorie categorie) {
		super();
		this.designation = designation;
		this.etat = etat;
		this.disponible = disponible;
		
		this.materielimage= materielimage;
		
		this.categorie=categorie;
	
	}
	public Materiels(Integer id, String designation , String etat, String disponible, 
			byte[] materielimage) {
		super();
		this.designation = designation;
		this.etat = etat;
		this.disponible = disponible;
		this.materielimage= materielimage;
	
		
	
	
	
	}

	public Materiels() {
		super();
	}


}
