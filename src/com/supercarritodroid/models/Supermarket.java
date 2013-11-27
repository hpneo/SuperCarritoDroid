package com.supercarritodroid.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jocasta.Model;
import com.jocasta.annotations.Ignore;

public class Supermarket extends Model implements Serializable {
    @Ignore
	private static final long serialVersionUID = 1L;

    @Ignore
    public static String resourceURL = "";
    @Ignore
    public static String BASE_URL = "http://androidservicios.somee.com/service1.svc/";
	
    @Ignore
	public static HashMap<String, String> URLS = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;

		{
			put("index", "/getsupermercados");
		}
	};
	
	private String codigo;
	private String nombre;
	private String paginaWeb;
	private String ruc;
	private String rutaImagen;
	private String telefono;
	
	public Supermarket() {
	}
	
	public static Supermarket fromJSON(JSONObject jsonItem) {
		Supermarket item = new Supermarket();
		
		item.setCodigo(jsonItem.optString("Codigo"));
		item.setNombre(jsonItem.optString("Nombre"));
		item.setPaginaWeb(jsonItem.optString("Paginaweb"));
		item.setRuc(jsonItem.optString("Ruc"));
		item.setRutaImagen(jsonItem.optString("Rutaimg"));
		item.setTelefono(jsonItem.optString("Telefono"));
		
		return item;
	}
	
	public static ArrayList<Supermarket> fromJSON(JSONArray jsonCollection) {
		ArrayList<Supermarket> collection = new ArrayList<Supermarket>();
		
		int count = jsonCollection.length(), i = 0;
		
		for (i = 0; i < count; i++) {
			if (jsonCollection.optJSONObject(i) != null) {
				Supermarket item = Supermarket.fromJSON(jsonCollection.optJSONObject(i));
				collection.add(item);
			}
		}
		
		return collection;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPaginaWeb() {
		return paginaWeb;
	}
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRutaImagen() {
		return rutaImagen;
	}
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public static String getResourceUrl(String action) {
	    return BASE_URL + resourceURL + URLS.get(action);
	}
}
