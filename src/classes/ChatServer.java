package classes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {

	private int port;
	private Set<String> userNames;
	private Set<UserThread> userThreads;

	public ChatServer(int port) {

		this.port = port;

		userNames = new HashSet<String>();
		userThreads = new HashSet<UserThread>();
	}

	public void execute() {

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Servidor iniciado.");

			while (true) {

				Socket socket = serverSocket.accept();

				UserThread newUser = new UserThread(socket, this);
				userThreads.add(newUser);
				newUser.start();

			}

		} catch (IOException e) {
			System.out.println("No se ha podido iniciar el servidor en el puerto" + port);
			e.printStackTrace();
		}

	}

	public void addUser(String userName) {
		userNames.add(userName);
	}

	public void remove(UserThread u) {
		userThreads.remove(u);
	}

	public void sendMessageBroadCast(String message, UserThread newUser) {
		System.out.println(newUser.getName() + " -> " + message);
		for (UserThread user : userThreads) {
			if (user != newUser)
				user.print(message);
		}
	}

	public Set<String> getUserNames() {
		return userNames;
	}

	public static void main(String[] args) {

		ChatServer chat = new ChatServer(6000);
		chat.execute();

	}

}
