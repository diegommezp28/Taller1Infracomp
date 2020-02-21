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
			Mensaje men = mensajes.remove(0);
			int viejo = men.getMensaje();


			//Se sincroniza el acceso a este mensaje para que el servidor que lo reciba, solo mande
			//respuesta después que este thread haya hecho wait (si la mandase antes del wait se podría 
			//quedar bloqueado)
			synchronized(men) {
				//meter mensaje 
				Boolean resultado = this.buffer.meterMensaje(men);
				if(resultado) {
					mensajesEnviados++;

					try {
						//Esperar respuesta
						men.wait();
						//recibí respuesta, leo el mesaje, verifico que esté correcto, lo imprimo y descarto
						int respuesta = men.getMensaje();
						System.out.println("viejo: "+viejo+" - nuevo: "+respuesta + (respuesta-1 == viejo? " - Correcto": " - Incorrecto"));

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					//Se mete de nuevo en la lista el mensaje que no se pudo enviar
					mensajes.add(men);
					//si no hay espacio para meter mensaje hago yield, y cuando sea mi turno en 
					//procesador de nuevo, vuelvo a verificas la condición del while y repito el proceso
					Thread.yield();
				}
			}
		}
		//Se informa al buffer que se retira un cliente
		retirarCliente();

	}

	public void retirarCliente() {
		Buffer.retirarCliente();
	}

}
