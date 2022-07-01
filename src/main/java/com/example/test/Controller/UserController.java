package com.example.test.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.test.Repository.UserRepository;
import com.example.test.Modeles.User;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@GetMapping("/user")
	public List<User> getAlluser() {
		return userRepository.findAll();
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getuserById(@PathVariable(value = "id") Integer userId)
			throws com.example.test.exception.ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("User not found for this id :: " + userId));
		return ResponseEntity.ok().body(user);
	}
	@PostMapping("/user")
	public User createuser(@Validated @RequestBody User user) {
		return userRepository.save(user);
	}
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateuser(@PathVariable(value = "id") Integer userid,
			@Validated @RequestBody User userDetails) throws com.example.test.exception.ResourceNotFoundException {
		User  user = userRepository.findById(userid)
				.orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("user not found for this id :: " + userid));
  user.setUserName(userDetails.getUserName());
  user.setLastname(userDetails.getLastname());
  user.setEmail(userDetails.getEmail());
  user.setDepartement(userDetails.getDepartement());
  user.setPassword(userDetails.getPassword());
  user.setTelephone(userDetails.getTelephone());
  user.setRoles(userDetails.getRoles());
  
  final User updateduser = userRepository.save(user);
	return ResponseEntity.ok(updateduser);
}
	@DeleteMapping("/user/{id}")
	public Map<String, Boolean> deleteuser(@PathVariable(value = "id")Integer userid)
			throws com.example.test.exception.ResourceNotFoundException {
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new com.example.test.exception.ResourceNotFoundException("user not found for this id :: " + userid));

		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	
	@GetMapping(path = "/user/usersSearch/{username}")
	public List<User> getSearch(@PathVariable String username)
	{
		System.out.print("\n test\n");
		System.out.print("\n"+username+" \n");
		System.out.print(userRepository.findAllByUserName(username));
		 return  userRepository.findAllByUserName(username);
		
	}
	
	
	
	
	
	
	
	
	

}
