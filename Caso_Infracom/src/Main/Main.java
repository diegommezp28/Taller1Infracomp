package Main;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {


	public static void main(String[] args) {
		try {
			BufferedReader reader=new BufferedReader(new FileReader("data.txt"));
			String line=reader.readLine();
			String[] content;

			content=line.split(":");
			int tamBuff=Integer.parseInt(content[1]);
			line=reader.readLine();

			content=line.split(":");
			int numClients=Integer.parseInt(content[1]);
			line=reader.readLine();

			content=line.split(":");
			int numServer=Integer.parseInt(content[1]);
			line=reader.readLine();
			
			Buffer buff = new Buffer(tamBuff,numClients);
			
			Client[] clients=new Client[numClients];
			Server[] servers=new Server[numServer];
			
			int i=0;
			while(line!=null && i<numClients) {
				content=line.split(":");
				clients[i]=new Client(Integer.parseInt(content[1]), buff,i);
				clients[i].start();
				if(numServer>i) {
					servers[i]=new Server(buff);
					servers[i].start();
				}
				i++;
				line=reader.readLine();
			}
			
			for(int j=i;j<numServer;j++) {
				servers[j]=new Server(buff);
				servers[j].start();
			}
			
			reader.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
