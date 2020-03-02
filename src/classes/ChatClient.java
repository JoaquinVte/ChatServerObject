package classes;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {
	
	private String hostname;
	private int port;
	private String userName;

	public ChatClient(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("IP:");
		String hostname = sc.nextLine();
		System.out.println("Port:");
		int port = sc.nextInt();
		
		ChatClient chat = new ChatClient(hostname,port);
		chat.execute();

	}

	private void execute() {
		
		try{
			Socket socket = new Socket(hostname,port);
			System.out.println("Conectado....");
			
			new WriterThread(socket,this).start();
			new ReaderThread(socket,this).start();				
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
