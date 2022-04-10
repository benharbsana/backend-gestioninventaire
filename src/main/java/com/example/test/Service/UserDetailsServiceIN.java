package com.example.test.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;


import com.example.test.Repository.UserRepository;
import com.example.test.Modeles.CustomUserBean;
import com.example.test.Modeles.User;

@Service

public class UserDetailsServiceIN implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
		
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User with "
                + "user name "+ username + " not found"));
		return CustomUserBean.createInstance(user);
	}
	
}
