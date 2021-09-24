package co.edu.unbosque.ciclo3back.model;

import javax.persistence.*;

@Entity
public class ventas {
	@Id
	private long codigo_venta;
	private String cedula_cliente;
	private String cedula_usuario;
	private String ivaventa;
	private String total_venta;
	private String valor_venta;
	
	public long getCodigo_venta() {
		return codigo_venta;
	}
	public void setCodigo_venta(long codigo_venta) {
		this.codigo_venta = codigo_venta;
	}
	public String getCedula_cliente() {
		return cedula_cliente;
	}
	public void setCedula_cliente(String cedula_cliente) {
		this.cedula_cliente = cedula_cliente;
	}
	public String getCedula_usuario() {
		return cedula_usuario;
	}
	public void setCedula_usuario(String cedula_usuario) {
		this.cedula_usuario = cedula_usuario;
	}
	public String getIvaventa() {
		return ivaventa;
	}
	public void setIvaventa(String ivaventa) {
		this.ivaventa = ivaventa;
	}
	public String getTotal_venta() {
		return total_venta;
	}
	public void setTotal_venta(String total_venta) {
		this.total_venta = total_venta;
	}
	public String getValor_venta() {
		return valor_venta;
	}
	public void setValor_venta(String valor_venta) {
		this.valor_venta = valor_venta;
	}

	
	
}