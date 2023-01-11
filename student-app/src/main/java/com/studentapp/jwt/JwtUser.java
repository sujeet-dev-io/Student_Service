package com.studentapp.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.studentapp.enums.AdminType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class JwtUser implements UserDetails {

//	private String roleId;
	
//	private String roleName;
	
	private String username;
	
	private String password;

	// To set the authorities
	private AdminType adminType;

	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(new String[]{adminType.name()})
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
