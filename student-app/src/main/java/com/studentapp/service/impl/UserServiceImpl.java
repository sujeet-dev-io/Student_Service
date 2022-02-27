package com.studentapp.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.studentapp.constant.AdminStatus;
import com.studentapp.constant.AdminType;
import com.studentapp.dto.AddUserRequest;
//import com.pct.auth.dto.PermissionDto;
//import com.pct.auth.entity.PermissionEntity;
import com.studentapp.entity.UserEntity;
import com.studentapp.jwt.JwtUser;
import com.studentapp.repository.UserRepository;
import com.studentapp.validation.ValidateRequest;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
//@AllArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
	
	@Value("${spring.mail.from}")
	private String fromEmail;

	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private ValidateRequest validate;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public Boolean addUser(AddUserRequest request) {
//		UserEntity entity = modelMapper.map(dto, UserEntity.class);
		
		validate.validateRequest(request);
		
		UserEntity user = new UserEntity();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUsername(request.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
		user.setEmailId(request.getEmailId());
		user.setMobileNumber(request.getMobileNumber());
		user.setTypeId(AdminType.SUPER_ADMIN.getLookupId());
		user.setStatusId(AdminStatus.Active.getLookupId());
		user.setCreatedAt(LocalDateTime.now());
		user.setCreatedBy("System");
		user.setUpdatedAt(LocalDateTime.now());
		user.setUpdatedBy("System");
		userDao.save(user);
		sendEmail(user.getUsername());
		return true;
	}
	
	void sendEmail(String username) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(fromEmail);
			messageHelper.setTo("kumar8851pravee@gmail.com");
			messageHelper.setSubject("Welcome "+username);
			String content ="Contratulations !! "
					+ "You have successfully register in Student App";
			messageHelper.setText(content, true);
		};
		try {
			javaMailSender.send(messagePreparator);
			System.out.println("User Register Welcome Message Sent Successfully");
		} catch (MailException e) {
			e.printStackTrace();
		}
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
