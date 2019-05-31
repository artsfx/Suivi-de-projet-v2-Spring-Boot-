package com.artsfx.springboot.scrumapp.scrumapp.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artsfx.springboot.scrumapp.scrumapp.dto.UserDto;
import com.artsfx.springboot.scrumapp.scrumapp.entity.Role;
import com.artsfx.springboot.scrumapp.scrumapp.entity.User;
import com.artsfx.springboot.scrumapp.scrumapp.exceptions.EmailExistsException;
import com.artsfx.springboot.scrumapp.scrumapp.repository.RolesRepository;
import com.artsfx.springboot.scrumapp.scrumapp.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	private boolean emailExists = false;

	
	@Override
	@Transactional
	public User registerNewUserAccount(UserDto userDto) throws EmailExistsException {
		
		
		if (emailExists(userDto.getEmail())) {
			System.out.println("Email exists already");
			
			throw new EmailExistsException("There is an account with that email address: " + userDto.getEmail());
			
		}
		
        User user = new User();    
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserName(userDto.getEmail());
        user.setName("");
        
        
        // Set user role to ROLE_USER
        rolesRepository.findByName("ROLE_USER").ifPresent(role -> {
        	System.out.println("Role name is" + role.getName());
          user.setRoles(Arrays.asList(role));
        });
       
        return userRepository.saveAndFlush(user);
	}
	
	private boolean emailExists(String email) {
		
		Optional<User> userOptional = userRepository.findByUserName(email);
		
		
		userOptional.ifPresent(user -> {
			this.emailExists = true;
		});
		
//		System.out.println("user repo " + user);
//		if (user != null) {
//			return true;
//		}
		return emailExists;
	}
	
}
