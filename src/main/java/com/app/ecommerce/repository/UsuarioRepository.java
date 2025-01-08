package com.app.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.app.ecommerce.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public List<Usuario> findAll();
	
	public Optional<Usuario> findByEmail(@Param("Email") String email);

	public Boolean existsByEmail(@Param("email")String email);

	public Optional<Usuario> findByVerificationCode(String code);
	
}
