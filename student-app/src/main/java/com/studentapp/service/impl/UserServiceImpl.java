package com.studentapp.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.studentapp.constant.Message;
import com.studentapp.dto.AddUserRequest;
//import com.pct.auth.dto.PermissionDto;
//import com.pct.auth.entity.PermissionEntity;
import com.studentapp.entity.UserEntity;
import com.studentapp.jwt.JwtUser;
import com.studentapp.repository.UserRepository;





@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userDao;
	
	String regex = "(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)";
	String regSpecialChars = "([a-zA-Z\\s+']{1,80}\\s*)+";
	String regSpecialOnNums = "(^[0-9]{10}$)";
	Pattern pattern = Pattern.compile(regex);
	Pattern patternSpecialChars = Pattern.compile(regSpecialChars);
	Pattern patternSpacialNums = Pattern.compile(regSpecialOnNums);
	
	public Boolean addUser(AddUserRequest request) {
//		UserEntity entity = modelMapper.map(dto, UserEntity.class);
		
		validateRequest(request);
		
		UserEntity entity = new UserEntity();
		entity.setFirstName(request.getFirstName());
		entity.setLastName(request.getLastName());
		entity.setUsername(request.getUsername());
		entity.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
		entity.setEmailId(request.getEmailId());
		entity.setMobileNumber(request.getMobileNumber());
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy(request.getEmailId());
		userDao.save(entity);
		return true;
	}
	
	private void validateRequest(AddUserRequest request) {
		
		Matcher matcher = pattern.matcher(request.getEmailId());
		Matcher matcherFirstName = patternSpecialChars.matcher(request.getFirstName());
		Matcher matcherLastName = patternSpecialChars.matcher(request.getLastName());

		Optional<UserEntity> user = userDao.findByEmailId(request.getEmailId());
		
		if (!matcherFirstName.matches()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.FIRST_NAME_NOT_VALID);

		if (!matcherLastName.matches()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.LAST_NAME_NOT_VALID);
		
		if (request.getFirstName() == null || request.getFirstName() == "") 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.FIRSTNAME_CAN_NOT_BE_BLANK);

		if (request.getLastName() == null || request.getLastName() == "") 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.LASTNAME_CAN_NOT_BE_BLANK);
		
		if (user.isPresent()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.EMAIL_ALREADY_EXISTS);

		if (request.getEmailId() == null || request.getEmailId() == "") 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.EMAIL_MUST_BE_PROVIDED);
		
		if (!matcher.matches()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.SHOULD_BE_VALID_EMAIL);
		
		if (request.getPassword() == null || request.getPassword() == "") 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.PASSWORD_IS_REQUARD);

		if (request.getPassword().length() < 6) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.PASSWORD_LENGTH_NOT_CORRECT);
		
		if (!isValidPhoneNumber(request.getMobileNumber())) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					Message.PHONE_NO_NOT_VALID);

	}
	
	private Boolean isValidPhoneNumber(String phoneNumber) {
		String strPattern = "^[0-9]{10}$";
		return phoneNumber.matches(strPattern);
	}


	public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		
		JwtUser jwtUser = new JwtUser();
		jwtUser.setUsername(username);
		jwtUser.setPassword(user.getPassword());
//		jwtUser.setRoleId(user.getRole().getRoleId().toString());
//		jwtUser.setRoleName(user.getRole().getName());
		return jwtUser;
		//return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new HashSet<>());
	}

//	public List<PermissionDto> getAllPermissionsByUsername(String username) {
//		UserEntity user = userDao.findByUsername(username);
//		List<PermissionDto> permissionDtoList = new ArrayList<PermissionDto>();
//		for(PermissionEntity permission : user.getRole().getPermissions()) {
//			PermissionDto permissionDto = modelMapper.map(permission, PermissionDto.class);
//			permissionDtoList.add(permissionDto);
//		}
//		return permissionDtoList;
//	}
}
