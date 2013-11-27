package com.supercarritodroid.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jocasta.Model;
import com.jocasta.annotations.Ignore;

public class Product extends Model implements Serializable {
    @Ignore
	private static final long serialVersionUID = 1L;

    @Ignore
	public static String BASE_URL = "http://androidservicios.somee.com/service1.svc/";

    @Ignore
	public static HashMap<String, String> URLS = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;

		{
			put("search", "/GetProductosFiltro1");
		}
	};

	private String codigo;
	private String nombre;
	private String categoria;
	private String fecVencimiento;
	private String marca;
	private Double precio;
	private String rutaImagen;
	
	public static Product fromJSON(JSONObject jsonItem) {
		Product item = new Product();
		
		item.setCodigo(jsonItem.optString("Codigo"));
		item.setNombre(jsonItem.optString("Nombre"));
		item.setCategoria(jsonItem.optString("Categoria"));
		item.setFecVencimiento(jsonItem.optString("Fecvencimiento"));
		item.setMarca(jsonItem.optString("Marca"));
		item.setPrecio(jsonItem.optDouble("Precio"));
		item.setRutaImagen(jsonItem.optString("Rutaimg"));
		
		return item;
	}
	
	public static ArrayList<Product> fromJSON(JSONArray jsonCollection) {
		ArrayList<Product> collection = new ArrayList<Product>();
	    
	    int count = jsonCollection.length(), i = 0;
	    
	    for (i = 0; i < count; i++) {
	      if (jsonCollection.optJSONObject(i) != null) {
	        Product item = Product.fromJSON(jsonCollection.optJSONObject(i));
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
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getFecVencimiento() {
		return fecVencimiento;
	}
	public void setFecVencimiento(String fecVencimiento) {
		this.fecVencimiento = fecVencimiento;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getRutaImagen() {
		return rutaImagen;
	}
	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
}
