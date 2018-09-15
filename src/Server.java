import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server implements Runnable {

	static String input, output;
	static BufferedReader in = null;
	static PrintWriter out = null;
	static Server serv;

	private void Go() throws IOException {
		ServerSocket servers = null;
		Socket fromclient = null;

		Scanner sc = new Scanner(System.in);

		System.out.println("Добро пожаловать на сервер.");
		System.out.println("");

		InetAddress IP = InetAddress.getLocalHost();
		System.out.println("IP адрес системы - " + IP.getHostAddress());

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

		new Thread(serv).start();
		System.out.println("Ожидание сообщения от клиента");
		while ((input = in.readLine()) != null) {
			if (input.equalsIgnoreCase("exit"))
				break;

			System.out.println(input);
		}
		out.close();
		in.close();
		fromclient.close();
		servers.close();
	}

	public static void main(String[] args) throws IOException {

		serv = new Server();
		serv.Go();

	}

	@Override
	public void run() {
		BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
		String a;
		System.out.println("ПОТООК ПОШОООООООЛ");
		try {
			while ((output = inu.readLine()) != null) {

				out.println("Сервер: " + output);
				System.out.println(output);
				System.out.println("ПОТООК ИДЕЕЕЕЕЕЕЕЕЕЕЕЕЕт");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}