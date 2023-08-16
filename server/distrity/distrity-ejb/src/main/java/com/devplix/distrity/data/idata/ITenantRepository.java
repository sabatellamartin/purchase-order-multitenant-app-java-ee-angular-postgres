package com.app.distrity.data.idata;

import java.util.List;

import com.app.distrity.model.tenant.Tenant;

public interface ITenantRepository {

	public Tenant getById(Long id);

	public Tenant getByNombre(String nombre);
	
	public List<Tenant> search(String term);

	public Boolean add(Tenant tenant);
	
	public Boolean update(Tenant tenant);

	public Boolean removeById(Long id);

	public List<Tenant> getAll();

}
