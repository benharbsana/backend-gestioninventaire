package com.example.test.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.Modeles.Role;
import com.example.test.Modeles.Roles;



@RestController
@CrossOrigin(origins="*") 
@RequestMapping("/auth")

public class AuthController {
	@Autowired
	  com.example.test.Repository.UserRepository userRepository;
	  @Autowired
	  com.example.test.Repository.RoleRepository roleRepository;
	  @Autowired
	  PasswordEncoder encoder;
	  @Autowired
	  AuthenticationManager authenticationManager;
	  @Autowired
	  com.example.test.Security.JwtTokenUtil jwtTokenUtil;
	  
	  @PostMapping("/login")
	  public ResponseEntity<?> userLogin(@Validated @RequestBody com.example.test.Modeles.User user) {
	    Authentication authentication = authenticationManager.authenticate(
	          new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String token = jwtTokenUtil.generateJwtToken(authentication);
	    com.example.test.Modeles.CustomUserBean userBean = (com.example.test.Modeles.CustomUserBean) authentication.getPrincipal();    
	    List<String> roles = userBean.getAuthorities().stream()
	                   .map(auth -> auth.getAuthority())
	                   .collect(Collectors.toList());
	    com.example.test.Modeles.AuthResponse authResponse = new com.example.test.Modeles.AuthResponse();
	    authResponse.setToken(token);
	    authResponse.setRoles(roles);
	    return ResponseEntity.ok(authResponse);
	  }
	  @PostMapping("/signup")
	  public ResponseEntity<?> userSignup(@Validated @RequestBody com.example.test.Modeles.SignupRequest signupRequest) {
	  System.out.println(signupRequest);
		  if(userRepository.existsByUserName(signupRequest.getUserName())){
	      return ResponseEntity.badRequest().body("Username is already taken");
	    }
	    if(userRepository.existsByEmail(signupRequest.getEmail())){
	      return ResponseEntity.badRequest().body("Email is already taken");
	    }
	    com.example.test.Modeles.User user = new com.example.test.Modeles.User();
	    Set<Role> roles = new HashSet<>();
	    user.setUserName(signupRequest.getUserName());
	    user.setLastname(signupRequest.getLastname());
	    user.setEmail(signupRequest.getEmail());
	    user.setDepartement(signupRequest.getDepartement());
	    user.setPassword(encoder.encode(signupRequest.getPassword()));
	    user.setTelephone(signupRequest.getTelephone());
	    //System.out.println("Encoded password--- " + user.getPassword());
	    String[] roleArr = signupRequest.getRoles();
	    
	    if(roleArr == null) {
	      roles.add( roleRepository.findByRoleName(Roles.ROLE_EMPLOYER).get());
	    }
	    for(String role: roleArr) {
	      switch(role) {
	        case "Admin":
	          roles.add( roleRepository.findByRoleName(com.example.test.Modeles.Roles.ROLE_ADMIN).get());
	          break;
	        case "Employer":
	          roles.add( roleRepository.findByRoleName(com.example.test.Modeles.Roles.ROLE_EMPLOYER).get());
	          break;  
	        default:
	          return ResponseEntity.badRequest().body("Specified role not found");
	      }
	    }
	    user.setRoles(roles);
	    userRepository.save(user);
	    return ResponseEntity.ok("User signed up successfully");
	  }
	  

}
