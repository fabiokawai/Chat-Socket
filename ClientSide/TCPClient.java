package ClientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient extends Thread {
	private static ObjectInputStream input;

	public static void main(String[] args) throws Exception {
		String text;
		Scanner reader = new Scanner(System.in);
		
		Socket client = new Socket("localhost", 2020);

		input = new ObjectInputStream(client.getInputStream());
		ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());

		TCPClient tcpclient = new TCPClient();
		tcpclient.start();

		output.flush();

		System.out.println("Digite seu nickname: ");
		text = reader.next();
			
		output.writeObject(text);	
		
		while (true) {
			text = reader.next();
			output.writeObject(text);
		}
		
	}

	public void run() {
		while (true) {
			try {
				System.out.println(input.readObject().toString());
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
}
