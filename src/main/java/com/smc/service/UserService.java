package com.smc.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import com.smc.model.User;

public interface UserService extends UserDetailsService{

	User findOne(String userName);

}
