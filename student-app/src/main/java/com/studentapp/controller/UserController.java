package com.studentapp.controller;

import com.studentapp.dto.AddUserRequest;
import com.studentapp.response.BaseResponse;
import com.studentapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "User Controller")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

	private final IUserService userService;

	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	@CrossOrigin("*")
	@ApiOperation(value = "CREATE USER API")
	@PostMapping("/addUser")
	public ResponseEntity<BaseResponse<Object>> addUser(
			@Valid @RequestBody AddUserRequest dto) {
		BaseResponse<Object> response = BaseResponse.builder()
				.successMsg("User Added")
				.data(userService.addUser(dto))
				.build();
		return ResponseEntity.ok(response);
	}
}
