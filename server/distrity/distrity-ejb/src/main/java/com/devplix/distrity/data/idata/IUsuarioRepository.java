package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.auth.Operador;
import com.app.distrity.model.auth.Usuario;

public interface IUsuarioRepository {

	public Boolean add(Usuario usuario);

	public Boolean update(Usuario usuario);

	public Boolean removeById(Long id);

	public Boolean remove(Usuario usuario);

	public Usuario getById(Long id);

	public Usuario getByEmail(String email);
	
	public List<Usuario> getAll();
	
	public List<Usuario> getAllOrderedByUsuarioname();

	public List<Usuario> searchByRol(String term, String rol); 

	public List<Usuario> search(String term);

	public void saveLogin(String username, Boolean result);

	public List<Operador> searchOperadoresByRol(String term, String rol, String tenant);
		
}
