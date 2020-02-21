package Main;

import java.util.ArrayList;

public class Buffer {
	
	private ArrayList<Mensaje> mensajes;
	
	private int capacidad;
	
	private Boolean lleno;
	
	private Boolean vacio;
	
	private static int numeroClientes;
	
	public Buffer(int pCapacidad, int pNumeroClientes) {
		mensajes = new ArrayList<Mensaje>(pCapacidad);
		lleno = false;
		vacio = true;
		this.capacidad = pCapacidad;
		Buffer.numeroClientes = pNumeroClientes;
	}
	
	
	
	public synchronized Boolean meterMensaje(Mensaje mensaje){
		if(!lleno) {
			System.out.println("se mete con lleno en: "+ lleno);
			mensajes.add(mensaje);
			if(mensajes.size()== capacidad) {
				this.lleno = true;
			}
			this.vacio = false;
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public synchronized Mensaje sacarMensaje() {
		if(!vacio) {
			System.out.println("se saca con vacio en: "+ vacio);
			Mensaje sacado = mensajes.remove(0);
			if(mensajes.isEmpty()) {
				vacio = true;
			}
			lleno = false;
			return sacado;}
		else {
			return null;
		}
	}
	
	public synchronized Boolean getLleno() {
		return this.lleno;
	}

	public synchronized Boolean getVacio() {
		return this.vacio;
	}
	
	public static synchronized int getNumeroClientesEsperando() {
		return Buffer.numeroClientes;
	}
	
	public static  synchronized void retirarCliente() {
		Buffer.numeroClientes--;
	}
}
