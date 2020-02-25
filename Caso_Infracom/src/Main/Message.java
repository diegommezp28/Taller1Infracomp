package Main;

public class Message {

	/**
	 * Attribute that contains the message of the request or the answer 
	 */
	private int message;
	
	/**
	 * Attribute to know if the client is already waiting for the answer
	 */
	private boolean isClientWaiting=false;
	
	/**
	 * Object that will use to control the thread of the client 
	 */
	private Object client;
	
	/**
	 * Method constructor of the Class Message
	 * @param message integer whit the message that will by use in the request
	 */
	public Message(int message) {
		this.message=message;
		
	}
	
	/**
	 * Method to change the information of the message
	 * @param message integer whit the new information of the message
	 */
	private void setMessage(int message) {
		this.message=message;
	}
	
	/**
	 * Method that returns the information of the message
	 * @return Integer whit the information of the message
	 */
	public int getMessage() {
		return message;
	}
	
	/**
	 * Method that inform the thread of the client that wait for the answer
	 * @throws InterruptedException
	 */
	public void setClientWaiting() throws InterruptedException {
		client=new Object();
		synchronized (client) {
			isClientWaiting=true;
			client.wait();
		}
		
	}
	
	/**
	 * Method to know if the client is ready to get the answer
	 * @return a boolean true if the client is ready to get the message or false if not
	 */
	public boolean isWaiting() {
		return isClientWaiting;
	}
	
	/**
	 * Method that change the information of the message, notify the client
	 * @param message integer whit the answer to be set
	 */
	public void answerClient(int message) {
		setMessage(message);
		synchronized (client) {
			client.notify();
		}
	}
	
}
