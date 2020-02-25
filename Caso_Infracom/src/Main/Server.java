package Main;

public class Server extends Thread {

	/**
	 * Attribute that represents the buffer that will be use to comunicate
	 */
	private Buffer buff;

	/**
	 * 
	 */
	private Message msg;

	/**
	 * 
	 */
	public Server (Buffer buff) {
		this.buff=buff;
		this.msg=null;
	}

	public void run() {

		while(buff.getClientNumber()>0) {
			msg=buff.getRequest();
			if(msg!=null) {
				while(!msg.isWaiting()) {
					yield();
				}
				int message=msg.getMessage()+1;
				msg.answerClient(message);
			}
		}
	}

}
