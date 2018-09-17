import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.xml.stream.events.StartDocument;

class Server1 {
	public static LinkedList<Serverss> serverList = new LinkedList<>(); // список всех нитей - экземпляров
	// сервера, слушающих каждый своего клиента

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.println("Добро пожаловать на сервер.");
		System.out.println("");

		InetAddress IP = InetAddress.getLocalHost();
		System.out.println("IP адрес системы - " + IP.getHostAddress());

		System.out.println("Введите порт для работы с клиентами (8080 по умолчанию)");
		int portServ = sc.nextInt();

		ServerSocket server = new ServerSocket(portServ);
		System.out.println("Server Started");
		try {
			while (true) {
				// Блокируется до возникновения нового соединения:
				Socket socket = server.accept();
				try {
					serverList.add(new Serverss(socket)); // добавить новое соединенние в список
				} catch (IOException e) {
					// Если завершится неудачей, закрывается сокет,
					// в противном случае, нить закроет его:
					socket.close();
				}
			}
		} finally {
			server.close();
		}
	}
}

class Serverss extends Thread {

	String input, output;// строки что приняли и что отправляем
	BufferedReader in = null;
	PrintWriter out = null;
	ArrayList<String> list = new ArrayList<String>();
	Socket fromclient = null;

	public Serverss(Socket fromclient) throws IOException {
		this.fromclient = fromclient;
		in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
		out = new PrintWriter(fromclient.getOutputStream(), true);
		// BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
		start();
	}

	void sendMessage(String msg) {
		for (Serverss vr : Server1.serverList) {
			vr.out.println(msg);
		}
	}
	@Override
	public void run() {

		try {
			while (true) {
				input = in.readLine();
				
				System.out.println("В чате пишут: " + input);
				if (input.contains("getMy_")) {
					input = input.substring(input.indexOf("_") + 1, input.length());
					sendMessage("SMS: " + list.get(Integer.parseInt(input)));
					
				} 
				if (input.contains("deleteMy_")) {
					input = input.substring(input.indexOf("_") + 1, input.length());
					list.remove(Integer.parseInt(input));
					sendMessage("SMS " + input+" has been deleted");
				} else {
					list.add(input);

				sendMessage(input);
				}
			}
		} catch (IOException e) {
			System.out.println("Общение скончалось(((");
			// e.printStackTrace();
		}

	}

}
