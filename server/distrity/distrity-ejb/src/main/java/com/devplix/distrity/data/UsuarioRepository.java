package com.app.distrity.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.bson.Document;

import com.app.distrity.data.idata.IUsuarioRepository;
import com.app.distrity.model.auth.Administrador;
import com.app.distrity.model.auth.Operador;
import com.app.distrity.model.auth.Usuario;
import com.app.distrity.util.Constants;
import com.app.distrity.util.MongoThread;
import com.mongodb.client.MongoCollection;

@Stateless
public class UsuarioRepository implements IUsuarioRepository {

	@PersistenceContext(unitName = Constants.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
    
	@Inject
	private MongoThread mongoThread;
	
	public UsuarioRepository() {}

	@Override
	public void saveLogin(String username, Boolean result) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		long unixTime = System.currentTimeMillis() / 1000L;
		Date date = new Date();
		MongoCollection<Document> collection = mongoThread.getDB().getCollection("logins");
		Document doc = new Document("username", username)
				.append("resultado", result)
				.append("timestamp", unixTime)
				.append("fecha", dateFormat.format(date));
		collection.insertOne(doc);
	}
	
	@Override
	public Boolean add(Usuario usuario) {
		Boolean result = false;
		try {
			if (usuario instanceof Administrador) {
				em.persist((Administrador)usuario); 
			} else if (usuario instanceof Operador) {
				em.persist((Operador)usuario);	
			}
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean update(Usuario usuario) {
		Boolean result = false;
		try {
			em.merge(usuario);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean remove(Usuario usuario) {
		Boolean result = false;
		try {
			usuario = em.find(Usuario.class, usuario.getId());
			em.remove(usuario);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Boolean removeById(Long id) {
		Boolean result = false;
		try {
			Usuario usuario = getById(id);
			result = remove(usuario);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public Usuario getById(Long id) {
		return em.find(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getAll() {
		return em.createQuery("FROM " + Usuario.class.getName()).getResultList();
	}

	@Override
	public List<Usuario> getAllOrderedByUsuarioname() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
		Root<Usuario> Usuario = criteria.from(Usuario.class);
		criteria.select(Usuario).orderBy(cb.desc(Usuario.get("username")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public Usuario getByEmail(String email) {
		Usuario usuario = null;
		String sql = "FROM " + Usuario.class.getName() + " u WHERE u.email =:email";
		List<?> result =  em.createQuery(sql).setParameter("email", email).getResultList();
		if (!result.isEmpty()){
			for(Object obj : result) {
				usuario = (Usuario) obj;
			}
		}
		return usuario;
	}
	
	@Override
	public List<Usuario> search(String term) {
		List<Usuario> list = new ArrayList<Usuario>();
		String sql = "FROM " + Usuario.class.getName() + " u " 
				+ "WHERE u.email LIKE :term " 
				+ "OR u.username LIKE :term";
		List<?> result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Usuario) obj);
			}
		}
		return list;
	}

	@Override
	public List<Usuario> searchByRol(String term, String rol) {
		List<Usuario> list = new ArrayList<Usuario>();
		String sql;
		List<?> result;
		if(rol.compareToIgnoreCase(Constants.ADMINISTRADOR)==0) {
			rol = Administrador.class.getName();
		} else if(rol.compareToIgnoreCase(Constants.OPERADOR)==0) {
			rol = Operador.class.getName();
		}
		sql = "FROM " + rol + " u ";
		if (term == null || term.compareToIgnoreCase("void") == 0) {
			result = em.createQuery(sql).getResultList();
		} else {
			sql += "WHERE u.email LIKE :term " 
				+ "OR u.username LIKE :term";
			result = em.createQuery(sql).setParameter("term", '%' + term + '%').getResultList();
		}
		if (!result.isEmpty()) {
			for (Object obj : result) {
				list.add((Usuario) obj);
			}
		}
		return list;
	}

	@Override
	public List<Operador> searchOperadoresByRol(String term, String rol, String tenant) {
		Integer limit = 100;
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Operador> criteria = cb.createQuery(Operador.class);
		Root<Operador> o = criteria.from(Operador.class);
		criteria.select(o);
		List<Predicate> predicate = new ArrayList<Predicate>();
	    Expression<String> tenantName = o.get("tenant").get("nombre");
	    predicate.add(cb.equal(tenantName, tenant));//EQUAL:tenant
	    if(rol!= null && rol.compareToIgnoreCase("")!=0){
	    	Expression<String> rolName = o.get("rol");
	    	predicate.add(cb.equal(rolName, rol));//EQUAL:rol
	    }
	    if(term.compareToIgnoreCase("void")!=0) {
		    Expression<String> username = o.get("username");
		    Predicate likeUsername = cb.like(cb.upper(username), '%' + term.toUpperCase() + '%');
		    Expression<String> email = o.get("email");
		    Predicate likeEmail = cb.like(cb.upper(email), '%' + term.toUpperCase() + '%');
		    predicate.add(cb.or(likeUsername, likeEmail)); //OR:LIKES
	    }
	    if(!predicate.isEmpty()){
	        Predicate[] predicateList = new Predicate[predicate.size()];
	        predicate.toArray(predicateList);
	        criteria.where(predicateList);//WHERE  
	    }
		criteria.orderBy(cb.desc(o.get("username")));//ORDER
		if (limit < 5) {
			limit = 5;
		}
		return em.createQuery(criteria).setMaxResults(limit).getResultList();//LIMIT
	}
	
}
