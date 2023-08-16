package com.app.distrity.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.distrity.data.idata.ITenantRepository;
import com.app.distrity.model.tenant.Tenant;
import com.app.distrity.util.Constants;

@Stateless
public class TenantRepository implements ITenantRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	public TenantRepository() {}

	@Override
	public Tenant getById(Long id) {
		return em.find(Tenant.class, id);
	}

	@Override
	public Tenant getByNombre(String nombre) {
		Tenant tenant = null;
		String sql = "FROM " + Tenant.class.getName() + " t WHERE t.nombre =:nombre";
		List<?> result =  em.createQuery(sql).setParameter("nombre", nombre).getResultList();
		if (!result.isEmpty()){
			for(Object obj : result) {
				tenant = (Tenant) obj;
			}
		}
		return tenant;
	}
	
	@Override
	public List<Tenant> search(String term) {
		List<Tenant> list = new ArrayList<Tenant>();
		String sql = "FROM " + Tenant.class.getName() + " t "
				+ "WHERE t.nombre LIKE :term "
				+ "ORDER BY t.nombre";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Tenant) obj);
			}
		}
		return list;
	}

	@Override
	public Boolean add(Tenant tenant) {
		Boolean result = false;
		try {
			em.persist(tenant);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Tenant tenant) {
		Boolean result = false;
		try {
			em.merge(tenant);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean removeById(Long id) {
		Boolean result = false;
		try {
			Tenant tenant = this.getById(id);
			result = this.remove(tenant);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}
	
	private Boolean remove(Tenant tenant) {
		Boolean result = false;
		try {
			tenant = em.find(Tenant.class, tenant.getId());
			em.remove(tenant);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tenant> getAll() {
		return em.createQuery("FROM " + Tenant.class.getName()).getResultList();
	}
	
}
