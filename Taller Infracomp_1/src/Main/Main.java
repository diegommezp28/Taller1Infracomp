package Main;

public class Main {

	public static void main(String[] args) {
		int numeroClientes = 20;
		int mensajesPermitidosPorCliente = 10;
		int numeroServidores = 15;
		int capacidadBuffer = 10;
		
		Buffer buffer = new Buffer(capacidadBuffer, numeroClientes);
		
		for(int i = 0; i<numeroClientes; i++) {
			Cliente cliente = new Cliente(mensajesPermitidosPorCliente, buffer);
			cliente.start();
		}
		
		for(int i = 0; i < numeroServidores; i++) {
			Servidor servidor = new Servidor(buffer);
			servidor.start();
		}
		
		
	}


}
