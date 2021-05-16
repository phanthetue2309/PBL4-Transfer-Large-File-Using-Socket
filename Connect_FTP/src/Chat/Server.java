package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private int port;
	public static ArrayList<Socket> listSK;

	public Server(int port) {
		this.port = port;
	}

	private void execute() throws IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("Server is listening...");
		while (true) {
			Socket socket = server.accept();
			System.out.println("Đã kết nối với  " + socket);
			Server.listSK.add(socket);
			ReadServer read = new ReadServer(socket);
			read.start();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server.listSK = new ArrayList<>();
		Server server = new Server(0105);
		server.execute();
	}
	
}

class ReadServer extends Thread {
	private Socket socket;

	public ReadServer(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			while (true) {
				String sms = dis.readUTF();
				if(sms.contains("exit")) {
					Server.listSK.remove(socket);
					System.out.println("Đã ngắt kết nối với " + socket);
					dis.close();
					socket.close();
					continue; // Ngắt kết nối 
				}
				for (Socket item : Server.listSK) {
					if(item.getPort() != socket.getPort()) {
						DataOutputStream dos = new DataOutputStream(item.getOutputStream());
						dos.writeUTF(sms);
					}
				}
				System.out.println(sms);
			}
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException ex) {
				System.out.println("Ngắt kết nối Server");
			}
		}
	}
}

