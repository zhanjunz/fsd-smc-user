package com.smc.controller;

import static com.smc.config.Constants.TOKEN_PREFIX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.smc.config.JwtTokenUtil;
import com.smc.model.ApiResponse;
import com.smc.model.AuthToken;
import com.smc.model.LoginUser;
import com.smc.model.User;
import com.smc.service.UserService;

@RestController
public class JwtAuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/authenticate")
	public ApiResponse<AuthToken> authenticate(@RequestBody LoginUser loginUser) throws AuthenticationException {
		
		final String username = loginUser.getUsername(); 
        authenticate(username, loginUser.getPassword());
        final User user = userService.findOne(username);
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",new AuthToken(username, user.getUserType(), TOKEN_PREFIX + token));
    }

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
	}
}
