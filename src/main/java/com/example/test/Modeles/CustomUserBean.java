package com.example.test.Modeles;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomUserBean implements UserDetails {
	
	private static final long serialVersionUID = -4709084843450077569L;  
	private Integer id ;
	private String userName;
	private String lastname;
	private String email;
	private String departement;
	private Integer telephone;
	@JsonIgnore
	private String password ;
	
	private Collection<? extends GrantedAuthority> authorities;
	  CustomUserBean(Integer id, String userName, String email, String lastname,String departement, 
	      String password,Integer telephone, Collection<? extends GrantedAuthority> authorities){
	    this.id = id;
	    this.userName = userName;
	    this.email = email;
	    this.lastname = lastname;
	    this.departement =departement;
	    this.telephone =telephone;
	    this.password = password;
	    
	    this.authorities = authorities;
	  }
	  public static CustomUserBean createInstance(User user) {
		  List<GrantedAuthority> authorities = user.getRoles()
                  .stream()
                      .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                   .collect(Collectors.toList());
 return new CustomUserBean(user.getId(), user.getUserName(),user.getEmail(),user.getLastname(),user.getDepartement(),
     user.getPassword(),user.getTelephone(), authorities);
		  }
	  @Override
	  public boolean equals(Object rhs) {
	    if (rhs instanceof CustomUserBean) {
	      return userName.equals(((CustomUserBean) rhs).userName);
	    }
	    return false;
	  }

	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
	    return authorities;
	  }

	  @Override
	  public String getPassword() {
	    return password;
	  }

	  @Override
	  public String getUsername() {
	    return userName;
	  }
	  public String getLastname() {
		  return lastname;
	  }
	  public String getDepartement() {
		  return departement;
	  }

	  public Integer getId() {
	    return id;
	  }

	  public String getEmail() {
	    return email;
	  }
	  public int getTelephone() {
		  return telephone;
	  }
	  
	  @Override
	  public boolean isAccountNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isAccountNonLocked() {
	    return true;
	  }

	  @Override
	  public boolean isCredentialsNonExpired() {
	    return true;
	  }

	  @Override
	  public boolean isEnabled() {
	    return true;
	  }
	 

	  @Override
	  public int hashCode() {
	    return userName.hashCode();
	  }
}
