package com.studentapp.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.studentapp.dto.AuthenticationRequest;
import com.studentapp.dto.Status;
import com.studentapp.jwt.JwtUser;
import com.studentapp.jwt.TokenProvider;
import com.studentapp.response.GenericResponse;
import com.studentapp.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Authentication Controller Provider")
@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Resource(name = "userService")
	private UserServiceImpl userService;

	@CrossOrigin("*")
	@ApiOperation(value = "It is to generate new authorization token")
	@PostMapping
	public ResponseEntity<GenericResponse> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							authenticationRequest.getUsername(),
							authenticationRequest.getPassword()
							)
					);

			JwtUser jwtUser = userService.loadUserByUsername(authenticationRequest.getUsername());
			final String token = jwtTokenUtil.generateToken(jwtUser);
			GenericResponse response = new GenericResponse();
			response.setMessage("New authorization token has been generated.");
			response.setStatus(Status.SUCCESS);
			response.setToken(token);
			return ResponseEntity.ok(response);

		} catch (BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password is invalid");
		} catch (DisabledException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user is blocked");
		}

	}
	
}
