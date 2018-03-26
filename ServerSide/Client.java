package ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Thread {

	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private int id;
	private String message;
	private String nickname;
	private boolean newMessage;

	public Client(Socket socket, int id) {
		try {
			this.id = id;
			this.socket = socket;
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());
			try {
				nickname = input.readObject().toString();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run() {
		while (true) {
			try {
				message = (nickname + " disse: " + input.readObject().toString());
				// System.out.println(message);
				if (message != null) {
					newMessage = true;
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void send(String msg) {
		try {
			output.writeObject(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getMessage() {
		return message;
	}
	public void setNewMessage(boolean newMessage) {
		this.newMessage = newMessage;
	}
	public boolean isNewMessage() {
		return newMessage;
	}
}
