package com.studentapp.controller;

import com.studentapp.constant.Message;
import com.studentapp.dto.AuthenticationRequest;
import com.studentapp.exception.BadRequestException;
import com.studentapp.jwt.JwtUser;
import com.studentapp.jwt.TokenProvider;
import com.studentapp.response.BaseResponse;
import com.studentapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = Message.AUTHENTICATE_CONTROLLER)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticateController {

	private final AuthenticationManager authenticationManager;
	private final TokenProvider jwtTokenUtil;
	
	@Resource(name = Message.USERSERVICE)
	private IUserService userService;

	@CrossOrigin("*")
	@ApiOperation(value = Message.GENERATE_NEW_TOKEN)
	@PostMapping
	public ResponseEntity<BaseResponse> createAuthToken(
			@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);

			JwtUser jwtUser = userService.loadUserByUsername(authenticationRequest.getUsername());
			final String token = jwtTokenUtil.generateToken(jwtUser);

			BaseResponse<Object> response = BaseResponse.builder()
					.successMsg(Message.TOKEN_GENERATED)
					.token(token)
					.build();
			return ResponseEntity.ok(response);

		} catch (BadCredentialsException e) {
			throw new BadRequestException(Message.INVALID_USERNAME_PASSWORD);
		} catch (DisabledException e) {
			throw new BadRequestException(Message.USER_BLOCKED);
		}

	}
	
}
