package com.studentapp.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
import com.studentapp.dto.EmailDto;
//import com.pct.auth.dto.PermissionDto;
//import com.pct.auth.entity.PermissionEntity;
import com.studentapp.entity.UserEntity;
import com.studentapp.jwt.JwtUser;
import com.studentapp.repository.UserRepository;
import com.studentapp.validation.ValidateRequest;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
//@AllArgsConstructor
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
	
	@Value("${spring.mail.from}")
	private String fromEmail;
	
    @Value("${spring.mail.default-to}")
    private String defaultToEmailAddress;

	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private ValidateRequest validate;
	
	@Autowired
    private Configuration configuration;
	
	@Autowired
	private EmailService emailService;
	
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
		sendEmail(user);
		return true;
	}
	
	private void sendEmail(UserEntity user) {
		Map<String, Object> emailAttributes = new HashMap<>();
		String userName = user.getUsername();
		String userId = String.valueOf(user.getAdminId());
		String email = user.getEmailId();
		emailAttributes.put("userName", Objects.nonNull(userName) ? userName : "User");
        emailAttributes.put("userId", Objects.nonNull(userId) ? userId : "");
        emailAttributes.put("email", Objects.nonNull(email) ? email : "");
        System.out.println("After type cast, claim:: {}"+ emailAttributes);
        String subject = "User Registered on Student App";
        EmailDto emailDto = null;
		try {
			emailDto = emailBuilder(subject, email, "User.ftl", emailAttributes);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
        emailService.sendEmail(emailDto.getTo(), emailDto.getFrom(), emailDto.getSubject(),
        		emailDto.getBody(), emailDto.getCc());
	}
	
	private String getEmailBody(String template, Map<String, Object> model)
			throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();
        configuration.getTemplate(template).process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

    private EmailDto emailBuilder(
    		String subject,
    		String toEmailAddress,
    		String template,
    		Map<String, Object> model) throws IOException, TemplateException {
        return EmailDto.builder()
                .subject(subject)
                .body(getEmailBody(template, model))
                .to(Objects.nonNull(toEmailAddress) ? toEmailAddress : defaultToEmailAddress)
                .from(fromEmail).build();
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
