package Main;

import java.util.ArrayList;
import java.util.Random;

public class Cliente extends Thread {

	private Integer numeroMensajes;

	private Buffer buffer;

	public Cliente(Integer numeroMensajes, Buffer buffer) {
		super();
		this.numeroMensajes = numeroMensajes;
		this.buffer = buffer;
	}


	public int generarNumeroAleatorio() {
		Random random = new Random();
		int mensaje = (random.nextInt()%200)+1;
		return mensaje;
	}


	public void run() {

		ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();

		for(int i = 0; i < numeroMensajes; i++) {
			Mensaje mensaje = new Mensaje(generarNumeroAleatorio());
			mensajes.add(mensaje);
		}

		int mensajesEnviados = 0;

		while(mensajesEnviados<numeroMensajes) {
			//Syncronizarme con los demás clientes y servidores que intentan acceder al buffer

			//verificar si hay espacio para meter mensaje
			Boolean verify = this.buffer.getLleno();
			if(!verify){
				//Si hay espacio, sacar uno de los mensajes de la lista de mensajes por enviar
				Mensaje men = mensajes.remove(0);
				int viejo = men.getMensaje();

				//Se sincroniza el acceso a este mensaje para que el servidor que lo reciba, solo mande
				//respuesta después que este thread haya hecho wait (si la mandase antes del wait se podría 
				//quedaría bloqueado)
				synchronized(men) {
					//meter mensaje 
					this.buffer.meterMensaje(men);
					mensajesEnviados++;

					try {
						//Esperar respuesta
						men.wait();
						System.out.println("viejo: "+viejo+" - nuevo: "+men.getMensaje());
						//recibí respuesta, leo el mesaje, lo imprimo y descarto

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			else{
				//si no hay espacio para meter mensaje hago yield, y cuando sea mi turno en 
				//procesador de nuevo, vuelvo a verificas la condición del while y repito el proceso
				Thread.yield();
			}

		}
		//Se informa al buffer que se retira un cliente
		retirarCliente();

	}

	public void retirarCliente() {
		Buffer.retirarCliente();
	}

}
