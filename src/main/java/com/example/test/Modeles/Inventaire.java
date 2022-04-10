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
public class Inventaire {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codeInv;
	private String quantite;
	private String dateinv ;
	@Lob
	private byte[] qrcode;
	
	@ManyToOne()
	private User user ;
	
	@ManyToMany ( mappedBy  ="inventaires")
	private List<Materiels> materiels ;
	
	
	public Inventaire( String quantite,
			byte[] qrcode, String dateinv,User user) {
		super();
		
	this.quantite=quantite;
	this.dateinv=dateinv;
	this.qrcode=qrcode ;
	this.user=user;
	
	}
	public Inventaire(  Integer codeInv, String quantite,
			byte[] qrcode, String dateinv) {
		super();
	this.codeInv=codeInv;	
	this.quantite=quantite;
	this.dateinv=dateinv;
	this.qrcode=qrcode ;
	
	
	
	
	}
	


	public Inventaire() {
		super();
	}
	

}
