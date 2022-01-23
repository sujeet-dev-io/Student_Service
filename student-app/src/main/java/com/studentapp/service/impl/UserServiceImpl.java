package com.studentapp.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studentapp.dto.AddUserRequestDto;
//import com.pct.auth.dto.PermissionDto;
//import com.pct.auth.entity.PermissionEntity;
import com.studentapp.entity.UserEntity;
import com.studentapp.jwt.JwtUser;
import com.studentapp.repository.UserRepository;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userDao;
	
	public Boolean addUser(AddUserRequestDto dto) {
//		UserEntity entity = modelMapper.map(dto, UserEntity.class);
		UserEntity entity = new UserEntity();
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());
		entity.setEmailId(dto.getEmailId());
		entity.setMobileNumber(dto.getMobileNumber());
		entity.setCreatedAt(LocalDateTime.now());
		entity.setCreatedBy(dto.getEmailId());
		userDao.save(entity);
		return true;
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
