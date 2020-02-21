package Main;

public class Servidor extends Thread{

	private Buffer buffer;

	public Servidor(Buffer buffer) {
		super();
		this.buffer = buffer;
	}

	public void run(){

		while(Buffer.getNumeroClientesEsperando()>0) {
			//sacar un mensaje del buffer
			Mensaje mensaje = this.buffer.sacarMensaje();
			if(mensaje!=null) {
				int numero = mensaje.getMensaje();
				//actualizar mensaje
				mensaje.setMensaje(++numero);
				synchronized(mensaje) {
					//notifica que se tiene la respuesta a su mensaje
					mensaje.notify();
					//Al ser el objeto mensaje un apuntador, el cliente puede leer la respuesta solo viendo la información 
					//actualizada del objeto
				}

			}
			else {
				Thread.yield();
			}
		}
	}


}
