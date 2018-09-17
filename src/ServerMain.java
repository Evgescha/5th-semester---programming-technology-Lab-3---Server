import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerMain {

	private static final int PORT = 8080;

    public static void main(String[] args) throws UnknownHostException {

        ServerSocket servers = null;
        Socket socket = null;
        
        Scanner sc = new Scanner(System.in);

		System.out.println("Добро пожаловать на сервер.");
		System.out.println("");

		InetAddress IP = InetAddress.getLocalHost();
		System.out.println("IP адрес системы - " + IP.getHostAddress());

		System.out.println("Введите порт для работы с клиентами (8080 по умолчанию)");
		int portServ = sc.nextInt();

		try {
			servers = new ServerSocket(portServ);
		} catch (IOException e) {
			System.out.println("Ошибка подключения к порту " + portServ + ". Будет произведен выход из приложения.");
			System.exit(-1);
		}
		// ждем подключения еще кого-нибудь
        while (true) {
            try {
            	System.out.print("Ожидание подключения клиентов...");   
                socket = servers.accept();
            } catch (IOException e) {
            	System.out.println("Провал. Будет произведен выход из приложения");
    			System.exit(-1);
            }
            new Thread(new Server(socket)).start();
            System.out.println("Клиент подключился!");
        }
    }
}
