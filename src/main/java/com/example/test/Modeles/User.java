package com.example.test.Modeles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;
@Data
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id ;
	@Column(name="name")
	private String userName ;
	@Column(name="lastname")
	private String lastname ;
	@Column(name="email")
	private String email ;
	@Column(name="departement")
	private String departement ;
	@Column(name ="telephone")
	private Integer telephone ;
	@Column(name="password")
	private String password ;
	@ManyToMany(fetch = FetchType.LAZY)
	  @JoinTable(name = "user_role", 
	      joinColumns = @JoinColumn(name="USER_ID", referencedColumnName="ID"),
	      inverseJoinColumns = @JoinColumn(name="ROLE_ID", referencedColumnName="ID"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany (mappedBy = "user")
	@JsonIgnore
	private List<Inventaire> inventaires;
	

}
