package com.app.distrity.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.distrity.data.idata.IArticuloRepository;
import com.app.distrity.model.Articulo;
import com.app.distrity.model.Unidad;
import com.app.distrity.service.iservice.IArticuloService;
import com.app.distrity.service.iservice.IUnidadService;
import com.app.distrity.util.filter.ArticuloFilter;
import com.app.distrity.util.paginator.PaginatorResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

@Stateless
public class ArticuloService implements IArticuloService {

	@Inject
	private IArticuloRepository articuloRepository;
	
	@Inject
	private IUnidadService unidadService;

	@Override
	public Articulo getById(Long id) {
		return articuloRepository.getById(id);
	}

	@Override
	public Articulo getByCodigo(String codigo) {
		return articuloRepository.getByCodigo(codigo);
	}

	@Override
	public List<Articulo> getAll() {
		return articuloRepository.getAll();
	}
	
	@Override
	public List<Articulo> search(String term) {
		List<Articulo> articulos = new ArrayList<Articulo>();
		if (term == null || term.compareToIgnoreCase("void")==0) {
			articulos = this.getAll();
		} else {
			articulos = articuloRepository.search(term);
		} 
		return articulos;
	}

	@Override
	public Boolean add(Articulo articulo) {
		articulo.setFechaAlta(new Date());
		if (articulo.getUnidad()==null) {
			List<Unidad> unidades = this.unidadService.getAll();
			if (!unidades.isEmpty()) {
				articulo.setUnidad(unidades.get(0));
			}			
		}
		return articuloRepository.add(articulo);
	}

	@Override
	public Boolean update(Articulo articulo) {
		if (articulo.getUnidad()==null) {
			List<Unidad> unidades = this.unidadService.getAll();
			if (!unidades.isEmpty()) {
				articulo.setUnidad(unidades.get(0));
			}			
		}
		return articuloRepository.update(articulo);
	}

	@Override
	public Boolean removeById(Long id) {
		return articuloRepository.removeById(id);
	}

	@Override
	public Boolean remove(Articulo articulo) {
		return articuloRepository.remove(articulo);
	}
	
	@Override
	public List<Articulo> loadFullData(InputStream incomingData) {
		List<Articulo> articulos = this.readFileData(incomingData);
		List<Articulo> fallidos = new ArrayList<Articulo>();
		if (!articulos.isEmpty()) {
			for (Articulo a : articulos) {
				if (a.getCodigo()!=null) {
					Articulo articulo = this.getByCodigo(a.getCodigo());
					if (articulo!=null) {
						a.setId(articulo.getId());
						this.update(a);
					} else {
						this.add(a);
					}
				} else {
					fallidos.add(a);
				}
			}
		}
		return fallidos;
	}

	private List<Articulo> readFileData(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				if (!line.contains("----")) {
					crunchifyBuilder.append(line);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		String jsonString = crunchifyBuilder.toString().split("application/octet-stream")[1];
		System.out.println("Data Received: " + jsonString);
		JsonReader reader = new JsonReader(new StringReader(jsonString));
		reader.setLenient(true);
		Gson gson = new Gson();
		return gson.fromJson(reader, new TypeToken<List<Articulo>>(){}.getType());
	}

	@Override
	public Boolean existByCodigo(String codigo) {
		Boolean result = false;
		Articulo a = this.getByCodigo(codigo);
		if (a != null) {
			result = true;
		}
		return result;
	}

	@Override
	public PaginatorResponse<Articulo> searchFilter(ArticuloFilter articuloFilter) {
		return articuloRepository.searchFilter(articuloFilter);
	}

}
