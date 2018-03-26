package ServerSide;

import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

	public static void main(String[] args) throws Exception {
		int x = 1;
		ConnectionManager cm = new ConnectionManager();
		cm.start();
		ServerSocket server = new ServerSocket(2020);
		while (true) {
			Socket socket = server.accept();
			Client cli = new Client(socket, x);
			cli.start();
			System.out.println("Client " + x + " conectou");
			cm.addClient(cli);
			x++;
		}
		//input.close();
		//client.close(); server.close();
	}
}
