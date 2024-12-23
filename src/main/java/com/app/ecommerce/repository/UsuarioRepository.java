package com.app.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.app.ecommerce.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public List<Usuario> findAll();
	
	public Usuario findByUsuario();

	public Boolean existsByUsuario(@Param("email")String email);

	public Usuario findByVerificationCode(String code);
	
}
