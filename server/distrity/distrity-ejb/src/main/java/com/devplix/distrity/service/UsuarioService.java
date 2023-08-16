package com.app.distrity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IUsuarioRepository;
import com.app.distrity.model.auth.Administrador;
import com.app.distrity.model.auth.Operador;
import com.app.distrity.model.auth.Usuario;
import com.app.distrity.model.tenant.Tenant;
import com.app.distrity.service.iservice.ITenantService;
import com.app.distrity.service.iservice.IUsuarioService;
import com.app.distrity.util.Constants;
import com.app.distrity.util.PasswordTool;

@Stateless
public class UsuarioService implements IUsuarioService {

	@Inject
	private IUsuarioRepository usuarioRepository;
	
	@Inject
	private PasswordTool password;
	
	@Inject
	private ITenantService tenantService;
	
	@Override
	public Boolean login(String username, String password) {
		Boolean result = false;
		if (password != null && username != null) {
			Usuario u = this.getByEmail(username);
			if (u.getEmail() != null && !(u.getEmail().isEmpty())) {
				if (u.getBloqueado()==null) {
					if (u.getPassword().compareToIgnoreCase(password) == 0) {
						result = true;
					}
				}
			}
		}
		usuarioRepository.saveLogin(username, result);
		return result;
	}
	
	@Override
	public Boolean add(Usuario usuario) {
		Boolean result = false;
		// Generate clear random password
		String clear = this.password.randomPassword();
		usuario.setAlta(new Date());
		// Set Clear encrypt in SHA512
		usuario.setPassword(this.password.hashPassword(clear));
		//usuario.setSetting(new Setting());
		// Validate & save
		if (this.isValid(usuario)) {
			result = usuarioRepository.add(usuario);
		}
		return result;
	}
	
	@Override
	public Boolean update(Usuario usuario) {
		Boolean result = false;
		Usuario old = this.getById(usuario.getId());
		if (old!=null) {
			usuario.setPassword(old.getPassword());
			result = usuarioRepository.update(usuario);
		}
		return result;
	}

	@Override
	public Boolean removeById(Long id) {
		return usuarioRepository.removeById(id);
	}

	@Override
	public Boolean remove(Usuario usuario) {
		return usuarioRepository.remove(usuario);
	}
	
	@Override
	public Usuario getById(Long id) {
		return usuarioRepository.getById(id);
	}

	@Override
	public Usuario getByEmail(String email) {
		return usuarioRepository.getByEmail(email);
	}
	
	@Override
	public List<Usuario> getAll() {
		return usuarioRepository.getAll();
	}

	@Override
	public List<Usuario> getAllOrderedByUsername() {
		return usuarioRepository.getAllOrderedByUsuarioname();
	}

	@Override
	public List<Usuario> search(String term) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if (term == null || term.compareToIgnoreCase("void") == 0) {
			usuarios = this.getAll();
		} else {
			usuarios = usuarioRepository.search(term);
		}
		return usuarios;
	}
	
	@Override
	public Boolean existByEmail(String email) {
		Boolean result = false;
		Usuario u = this.getByEmail(email);
		if (u != null) {
			result = true;
		}
		return result;
	}
	
	@Override
	public List<Usuario> searchByRol(String term, String rol) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = usuarioRepository.searchByRol(term,rol);
		return usuarios;
	}

	public Boolean isValid(Usuario usuario) {
		Boolean result = false;
		if (!this.existByEmail(usuario.getEmail())) {
			result = true;
		}
		return result;
	}

	@Override
	public Boolean toggleLockById(Long id) {
		Boolean result = false;
		Usuario usuario = this.getById(id);
		if (usuario!=null) {
			Date date = usuario.getBloqueado()==null ? new Date() : null;
			usuario.setBloqueado(date);			
			result = usuarioRepository.update(usuario);
		}
		return result;
	}
	
	@Override
	public String getRolByEmail(String email) {
		String rol = "";
		Usuario usuario = this.getByEmail(email);
		if (usuario != null) {
			if (usuario instanceof Administrador) {
				rol = Constants.ADMINISTRADOR;
			} else if (usuario instanceof Operador) {
				rol = ((Operador) usuario).getRol();
			}
		}
		return rol;
	}

	/* PROPIETARIO */
	
	@Override
	public List<Operador> searchOperadoresByRol(String term, String rol, String tenant) {
		List<Operador> operadores = new ArrayList<Operador>();
		if (term == null || term.compareToIgnoreCase("void") == 0) {
			term = "void";
		}
		operadores = usuarioRepository.searchOperadoresByRol(term, rol, tenant);
		return operadores;
	}

	@Override
	public Boolean addOperador(Operador usuario, String tenant) {
		Boolean response = false;
		Tenant t = this.tenantService.getByNombre(tenant);
		if(t!=null) {
			usuario.setTenant(t);
			response = this.add(usuario);	
		}
		return response;
	}

	@Override
	public Boolean updateOperador(Operador usuario, String tenant) {
		Boolean response = false;
		Tenant t = this.tenantService.getByNombre(tenant);
		if(t!=null) {
			usuario.setTenant(t);
			response = this.update(usuario);	
		}
		return response;
	}

	@Override
	public Boolean removeOperador(Long id, String tenant) {
		Boolean response = false;
		Operador operador = this.getOperadorById(id, tenant);
		if (operador!=null) {
			response = this.removeById(operador.getId());
		}
		return response;
	}

	@Override
	public Boolean toggleLockOperadorById(Long id, String tenant) {
		Boolean result = false;
		Operador usuario = this.getOperadorById(id, tenant);
		if (usuario!=null) {
			Date date = usuario.getBloqueado()==null ? new Date() : null;
			usuario.setBloqueado(date);			
			result = usuarioRepository.update(usuario);
		}
		return result;
	}

	@Override
	public Operador getOperadorById(Long id, String tenant) {
		Operador operador = (Operador)this.getById(id);
		return operador.getTenant().getNombre().compareToIgnoreCase(tenant)==0 ? operador: null;
	}

	@Override
	public Operador getOperadorByEmail(String email, String tenant) {
		Operador operador = (Operador)this.getByEmail(email);
		return operador.getTenant().getNombre().compareToIgnoreCase(tenant)==0 ? operador: null;
	}

	@Override
	public Boolean toggleBajaOperadorById(Long id, String tenant) {
		Boolean result = false;
		Operador usuario = this.getOperadorById(id, tenant);
		if (usuario!=null) {
			Date date = usuario.getBaja()==null ? new Date() : null;
			usuario.setBaja(date);			
			result = usuarioRepository.update(usuario);
		}
		return result;
	}

	@Override
	public String restorePasswordOperador(Long id, String tenant) {
		Boolean result = false;
		String clear = this.password.randomPassword();
		Operador usuario = this.getOperadorById(id, tenant);
		if (usuario!=null) {
			usuario.setPassword(this.password.hashPassword(clear));		
			result = usuarioRepository.update(usuario);
		}
		return result ? clear : null;
	}

	@Override
	public Boolean changePasswordOperador(Long id, String tenant, String clearOld, String clearNew) {
		Boolean result = false;
		Operador usuario = this.getOperadorById(id, tenant);
		if (usuario!=null) {
			String hashOld = this.password.hashPassword(clearOld);
			if (hashOld.compareToIgnoreCase(usuario.getPassword())==0) {
				String hashNew = this.password.hashPassword(clearNew);
				usuario.setPassword(hashNew);		
				result = usuarioRepository.update(usuario);
			}
		}
		return result;
	}
	
}
