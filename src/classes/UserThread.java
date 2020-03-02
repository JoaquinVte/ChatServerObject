package classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread {
	
	private Socket socket;
	private ChatServer server;
	private ObjectOutputStream writer;
	
	
	public UserThread(Socket socket,ChatServer server) {
		
		this.socket=socket;
		this.server=server;
		
	}
	
	@Override
	public void run() {
		
		try(ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
				
			OutputStream output = socket.getOutputStream();
				
			)
		{
			writer = new ObjectOutputStream(output);
			
			// Obtengo nombre usuario recien conectado
			String userName = ((Mensaje)reader.readObject()).getTexto();
			server.addUser(userName);
			
			writer.writeObject(new Mensaje(server.getUserNames().toString()));
			
			
			// Envio a todos los usuarios el nombre del recien llegado
			String men = "Nuevo usario conectado: " + userName;
			server.sendMessageBroadCast(men,this);
			
			String clientMessage="";
			
			do {
				
				clientMessage=((Mensaje)reader.readObject()).getTexto();
				if(clientMessage==null)
					clientMessage="";
				men = "["+userName+"]:"+clientMessage;
				
				server.sendMessageBroadCast(men, this);
				
			}while(!clientMessage.equals("bye"));
			
			server.remove(this);
			
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	public void print(String m) {
		try {
			writer.writeObject(new Mensaje(m));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
