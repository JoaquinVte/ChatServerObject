package classes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class WriterThread extends Thread {

	private Socket socket;
	private ChatClient client;
	private ObjectOutputStream writer;

	public WriterThread(Socket socket, ChatClient client) {

		this.socket = socket;
		this.client = client;

		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {

		// Pedir al usuario su nombre
		Scanner sc = new Scanner(System.in);
		System.out.println("Dame tu nombre:");
		String userName = sc.nextLine();

		// Enviar el nombre por la red a UserThread
		try {
			writer.writeObject(new Mensaje(userName));

			// Bucle pidiendo texto y enviar
			String texto;
			do {
				texto = sc.nextLine();

				writer.writeObject(new Mensaje(texto));

			} while (!texto.equalsIgnoreCase("bye"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Cerrar flujos
		try {
			socket.close();
			writer.close();
			sc.close();
		} catch (IOException e) {
		}

	}
}
