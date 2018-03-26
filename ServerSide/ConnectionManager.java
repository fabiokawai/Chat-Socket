package ServerSide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ConnectionManager extends Thread {
	boolean writing;
	boolean reading;
	String message;

	private List<Client> clientList = new ArrayList<Client>();
	private Semaphore mutex = new Semaphore(1);

	public void run() {
		while (true) {
			Client clientAtivo = null;
			message = "";
			try {
				mutex.acquire();
				for (Client client : clientList) {
					if (client.isNewMessage()) {
						System.out.println("Cliente recebeu mensagem ");
						clientAtivo = client;
						message = client.getMessage();
						System.out.println(message);
						client.setNewMessage(false);
						System.out.println("Enviando aos outros");
						break;
					}
				}
				if (clientAtivo != null) {
					for (Client client : clientList) {
						if (client != clientAtivo) {
							System.out.println("Enviando");
							client.send(message);
						}
					}
				}
				mutex.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void addClient(Client cli) {
		try {
			mutex.acquire();
			clientList.add(cli);
			mutex.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
