import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server implements Runnable{

	public static void main(String[] args) throws IOException {
		
		BufferedReader in = null;
		PrintWriter out = null;

		ServerSocket servers = null;
		Socket fromclient = null;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Добро пожаловать на сервер.");
		System.out.println("");
		
		InetAddress IP=InetAddress.getLocalHost();
		System.out.println("IP адрес системы - "+IP.getHostAddress());
		
		System.out.println("Введите порт для работы с клиентами (8080 по умолчанию)");
		int portServ = sc.nextInt();
		

		// create server socket
		try {
			servers = new ServerSocket(portServ);
		} catch (IOException e) {
			System.out.println("Ошибка подключения к порту " + portServ + ". Будет произведен выход из приложения.");
			System.exit(-1);
		}

		try {
			System.out.print("Ожидание подключения клиентов...");
			fromclient = servers.accept();
			System.out.println("Клиент подключился!");
		} catch (IOException e) {
			System.out.println("Провал. Будет произведен выход из приложения");
			System.exit(-1);
		}

		in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
		out = new PrintWriter(fromclient.getOutputStream(), true);
		String input, output;
		BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Ожидание сообщения от клиента");
		while ((input = in.readLine()) != null) {
			if (input.equalsIgnoreCase("exit"))
				break;
			String a = inu.readLine();
			if (a != null) out.println("Сервер: " + a);
			System.out.println(input);
		}
		out.close();
		in.close();
		fromclient.close();
		servers.close();
		
		
	}
/*
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while ((input = in.readLine()) != null) {
			if (input.equalsIgnoreCase("exit"))
				break;
			String a = inu.readLine();
			if (a != null) out.println("Сервер: " + a);
			System.out.println(input);
		}
	}
	*/
	
}