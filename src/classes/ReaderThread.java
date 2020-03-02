package classes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReaderThread extends Thread {

	private Socket socket;
	private ChatClient client;
	private ObjectInputStream reader;

	public ReaderThread(Socket socket, ChatClient client) {
		this.socket = socket;
		this.client = client;

		try {
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			System.out.println("Error obteniendo el inputStream.");
			ioe.printStackTrace();
		}
	}

	public void run() {
		Mensaje m;
		String respuesta;

		while (true) {
			try {
				
				m = (Mensaje)reader.readObject();
				respuesta = m.getTexto();
				
				System.out.println(respuesta);
				
				if (client.getUserName() != null)
					System.out.print("[" + client.getUserName() + "]: ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
