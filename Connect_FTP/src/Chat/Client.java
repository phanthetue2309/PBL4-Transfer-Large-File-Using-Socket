package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private InetAddress host;
	private int port;

	public Client(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}

	private void execute() throws IOException {

		Scanner sc = new Scanner(System.in);
		System.out.print("Nhập vào tên của bạn : ");
		String name = sc.nextLine();

		Socket client = new Socket(host, port);
		ReadClient read = new ReadClient(client);
		read.start();
		WriteClient write = new WriteClient(client, name);
		write.start();
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client(InetAddress.getLocalHost(), 0105);
		client.execute();
	}
}

class ReadClient extends Thread {
	private Socket client;

	public ReadClient(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(client.getInputStream());
			while (true) {
				String sms = dis.readUTF();
				System.out.println(sms);
			}
		} catch (Exception e) {
			try {
				dis.close();
				client.close();
			} catch (IOException ex) {
				System.out.println("Ngắt kết nối Server");
			}
		}
	}
}

class WriteClient extends Thread {
	private Socket client;
	private String name;
	private String msg = "ahihi";

	public WriteClient(Socket client, String name) {
		this.client = client;
		this.name = name;
	}

	@Override
	public void run() {
		DataOutputStream dos = null;
	
		try {
			dos = new DataOutputStream(client.getOutputStream());
			String sms = msg;
			// sc = new Scanner(System.in);
			while (true) {

				if (sms != "") {
					dos.writeUTF(name + " : " + sms);
					sms = "";// sc.nextLine();

				}
			}
		} catch (Exception e) {
			try {
				dos.close();
				client.close();
			} catch (IOException ex) {
				System.out.println("Ngắt kết nối Server");
			}
		}
	}

}
