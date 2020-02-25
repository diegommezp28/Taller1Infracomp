package Main;

import java.util.LinkedList;

public class Buffer {

	private LinkedList<Message> buff;

	private int buffCapacity;

	private int numberOfClients;

	private int actualRequest;

	public Buffer(int buffCapacity,int numberOfClients) {
		this.buffCapacity=buffCapacity;
		this.numberOfClients=numberOfClients;
		this.buff=new LinkedList<Message>();
		this.actualRequest=0;
	}

	public synchronized boolean addRequest(Message msg) {
		actualRequest++;
		if(actualRequest>buffCapacity) {
			actualRequest--;
			return false;
		}
		buff.add(msg);
		return true;
	}

	public synchronized Message getRequest() {		

		if(actualRequest>0) {
			actualRequest--;
			return buff.pop();
		}

		return null;
	}

	public void clientFinish() {
		numberOfClients--;
	}

	public int getClientNumber() {
		return numberOfClients;
	}
}
