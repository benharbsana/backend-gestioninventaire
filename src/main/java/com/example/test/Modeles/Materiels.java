package com.example.test.Modeles;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
	private String origine;
	@Lob
	private byte[] materielimage;
	private String date ;
	@ManyToOne
	private Categorie categorie;
	
       @ManyToMany()
	
	private List<Inventaire> inventaires;
	
	
	public Materiels( String designation , String etat, String disponible,String origine, 
			byte[] materielimage, String date, Categorie categorie) {
		super();
		this.designation = designation;
		this.etat = etat;
		this.disponible = disponible;
		this.origine = origine;
		this.materielimage= materielimage;
		this.date = date;
		this.categorie=categorie;
	
	}
	public Materiels(Integer id, String designation , String etat, String disponible,String origine, 
			byte[] materielimage, String date) {
		super();
		this.designation = designation;
		this.etat = etat;
		this.disponible = disponible;
		this.origine = origine;
		this.materielimage= materielimage;
		this.date = date;
		
	
	
	
	}

	public Materiels() {
		super();
	}


}
