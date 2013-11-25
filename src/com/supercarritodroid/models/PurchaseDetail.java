package com.supercarritodroid.models;

import java.io.Serializable;

import com.jocasta.Model;

public class PurchaseDetail extends Model implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int codigoCompra;
    private int codigoCliente;
    private int codigoProducto;
    private int codigoSupermercado;
    private int cantidad;
    private double subtotal;
    
    public PurchaseDetail() {
    }
    
    public int getCodigoCompra() {
        return codigoCompra;
    }
    public void setCodigoCompra(int codigoCompra) {
        this.codigoCompra = codigoCompra;
    }
    public int getCodigoCliente() {
        return codigoCliente;
    }
    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
    public int getCodigoProducto() {
        return codigoProducto;
    }
    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    public int getCodigoSupermercado() {
        return codigoSupermercado;
    }
    public void setCodigoSupermercado(int codigoSupermercado) {
        this.codigoSupermercado = codigoSupermercado;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
