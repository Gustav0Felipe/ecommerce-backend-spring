package com.app.ecommerce.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.ecommerce.model.Usuario;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private List<GrantedAuthority> roles;
	
	public UserDetailsImpl() {
	}

	public UserDetailsImpl(Usuario usuario) {
		this.username = usuario.getEmail();
		this.password = usuario.getSenha();
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		usuario.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));

		this.roles = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
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
