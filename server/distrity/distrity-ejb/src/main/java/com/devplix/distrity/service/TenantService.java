package com.app.distrity.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.ITenantRepository;
import com.app.distrity.model.tenant.Tenant;
import com.app.distrity.service.iservice.ITenantService;

@Stateless
public class TenantService implements ITenantService {

	@Inject
	private ITenantRepository tenantRepository;

	@Override
	public Tenant getById(Long id) {
		return tenantRepository.getById(id);
	}

	@Override
	public Tenant getByNombre(String nombre) {
		return tenantRepository.getByNombre(nombre);
	}

	private List<Tenant> getAll() {
		return tenantRepository.getAll();
	}
	
	@Override
	public List<Tenant> search(String term) {
		List<Tenant> tenants = new ArrayList<Tenant>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			tenants = this.getAll();
		} else {
			tenants = tenantRepository.search(term);
		} 
		return tenants;
	}

	@Override
	public Boolean add(Tenant tenant) {
		return tenantRepository.add(tenant);
	}

	@Override
	public Boolean update(Tenant tenant) {
		return tenantRepository.update(tenant);
	}

	@Override
	public Boolean removeById(Long id) {
		return tenantRepository.removeById(id);
	}

}
