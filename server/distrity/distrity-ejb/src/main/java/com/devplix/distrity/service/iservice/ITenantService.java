package com.app.distrity.service.iservice;

import java.util.List;

import com.app.distrity.model.tenant.Tenant;

public interface ITenantService {
	
	public Tenant getById(Long id);

	public Tenant getByNombre(String nombre);
	
	public List<Tenant> search(String term);

	public Boolean add(Tenant tenant);
	
	public Boolean update(Tenant tenant);

	public Boolean removeById(Long id);

}
