package co.edu.unbosque.ciclo3back.model;

import javax.persistence.*;

@Entity
public class productos {
	@Id
	private long codigo_producto;
	private String ivacompra;
	private String nitproveedor;
	private String nombre_producto;
	private String precio_compra;
	private String precio_venta;
	
	public long getCodigo_producto() {
		return codigo_producto;
	}
	public void setCodigo_producto(long codigo_producto) {
		this.codigo_producto = codigo_producto;
	}
	public String getIvacompra() {
		return ivacompra;
	}
	public void setIvacompra(String ivacompra) {
		this.ivacompra = ivacompra;
	}
	public String getNitproveedor() {
		return nitproveedor;
	}
	public void setNitproveedor(String nitproveedor) {
		this.nitproveedor = nitproveedor;
	}
	public String getNombre_producto() {
		return nombre_producto;
	}
	public void setNombre_producto(String nombre_producto) {
		this.nombre_producto = nombre_producto;
	}
	public String getPrecio_compra() {
		return precio_compra;
	}
	public void setPrecio_compra(String precio_compra) {
		this.precio_compra = precio_compra;
	}
	public String getPrecio_venta() {
		return precio_venta;
	}
	public void setPrecio_venta(String precio_venta) {
		this.precio_venta = precio_venta;
	}
	
	
	
}