package Main;

public class Client extends Thread{

	private int id;

	private int numberOfRequest;

	private Message msg;

	private Buffer buff;

	public Client(int numbreOfRequest,Buffer buff,int id) {
		this.buff=buff;
		this.numberOfRequest=numbreOfRequest;
		this.id=id;
	}

	public void sendMessage() throws InterruptedException {
		boolean res=buff.addRequest(msg);
		while(!res) {
			yield();
			res=buff.addRequest(msg);
		}
		msg.setClientWaiting();
	}

	public boolean getAnswer(int msgRequest) throws Exception {
		int res=msg.getMessage();
		msgRequest++;
		if(res!=msgRequest) {
			throw new Exception("La respuesta obtenida no es la esperada");
		}
		return true;
	}
	
	public void run() {
		int i=0;
		while(numberOfRequest>i) {
			msg=new Message(i);
			try {
				sendMessage();
				getAnswer(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		buff.clientFinish();
		System.out.println("Cliente "+id+" termino, realizo " + numberOfRequest+" consultas");
	}
}
