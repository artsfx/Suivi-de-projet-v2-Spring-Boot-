package com.artsfx.springboot.scrumapp.scrumapp.services;

import com.artsfx.springboot.scrumapp.scrumapp.dto.UserDto;
import com.artsfx.springboot.scrumapp.scrumapp.entity.User;
import com.artsfx.springboot.scrumapp.scrumapp.exceptions.EmailExistsException;

public interface IUserService {
	public User registerNewUserAccount(UserDto userDto)
		throws EmailExistsException;
}
