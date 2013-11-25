package com.supercarritodroid.models;

import java.io.Serializable;
import java.util.Date;

import com.jocasta.Model;

public class Purchase extends Model implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String BASE_URL = "http://androidservicios.somee.com/service1.svc/";
	
	private int codigoCliente;
    private Date fechaCompra;
    private double total;
    
    public Purchase() {
        super();
    }
    
    public int getCodigoCliente() {
        return codigoCliente;
    }
    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
    public Date getFechaCompra() {
        return fechaCompra;
    }
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}
