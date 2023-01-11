package com.studentapp.controller;

import com.studentapp.dto.AddUserRequest;
import com.studentapp.enums.Status;
import com.studentapp.response.BaseResponse;
import com.studentapp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "User Controller Provider")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private IUserService userService;

	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	//@Secured("SUPER_ADMIN")
	@CrossOrigin("*")
	@ApiOperation(value = "API to add new user")
	@PostMapping("/addUser")
	public ResponseEntity<BaseResponse<String, Long>> addUser(
			@Valid @RequestBody AddUserRequest dto) {
		BaseResponse<String, Long> response = new BaseResponse<>();
		userService.addUser(dto);
		response.setSuccessMsg("User added successfully.");
		response.setStatus(Status.SUCCESS);
		return ResponseEntity.ok(response);
	}
}
