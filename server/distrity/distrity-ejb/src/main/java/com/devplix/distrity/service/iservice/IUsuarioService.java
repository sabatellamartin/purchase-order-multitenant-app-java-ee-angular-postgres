package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.auth.Operador;
import com.app.distrity.model.auth.Usuario;

public interface IUsuarioService {
	
	public Boolean login(String username, String password);
	
	public Boolean add(Usuario usuario);

	public Boolean update(Usuario usuario);

	public Boolean removeById(Long id);

	public Boolean remove(Usuario usuario);

	public Usuario getById(Long id);

	public Usuario getByEmail(String email);
	
	public Boolean existByEmail(String email);
	
	public List<Usuario> getAll();

	public List<Usuario> getAllOrderedByUsername();

	public List<Usuario> search(String term);

	public String getRolByEmail(String email);

	public List<Usuario> searchByRol(String term, String rol);
	
	public Boolean toggleLockById(Long id);

	/* PROPIETARIO */
	
	public List<Operador> searchOperadoresByRol(String term, String rol, String tenant);

	public Boolean addOperador(Operador usuario, String tenant);

	public Boolean updateOperador(Operador usuario, String tenant);
	
	public Boolean removeOperador(Long id, String tenant);

	public Boolean toggleLockOperadorById(Long id, String tenant);

	public Operador getOperadorById(Long id, String tenant);

	public Operador getOperadorByEmail(String email, String tenant);

	public Boolean toggleBajaOperadorById(Long id, String tenant);

	public String restorePasswordOperador(Long id, String tenant);

	public Boolean changePasswordOperador(Long id, String tenant, String clearOld, String clearNew);
	
}
