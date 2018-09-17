import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


class Server1 {
	 public static LinkedList<Serverss> serverList = new LinkedList<>(); // список всех нитей - экземпляров
	    // сервера, слушающих каждый своего клиента

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
               serverList.add(new Serverss(socket)); // добавить новое соединенние в список
           } catch (IOException e) {
           	try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
           	System.out.println("Провал. Будет произведен выход из приложения");
   			System.exit(-1);
           }
           //new Thread(new Server(socket)).start();
           //System.out.println("Клиент подключился!");
       }
   }
}


public class Serverss implements Runnable {

	String input, output;//строки что приняли и что отправляем
	BufferedReader in = null;
	PrintWriter out = null;
	Serverss serv;
	ArrayList<String> list = new ArrayList<String>();
	ServerSocket servers = null;
	Socket fromclient = null;
	Scanner sc = new Scanner(System.in);

	public Serverss(Socket fromclient) throws IOException {
		this.fromclient = fromclient;
		in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
		out = new PrintWriter(fromclient.getOutputStream(), true);
		//BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
	}

	public class ClWrie implements Runnable {
		public void run() {
			try {
				
				while ((input = in.readLine()) != null) {
					
					System.out.println(input);

					if (input.contains("getMy_")) {
						input = input.substring(input.indexOf("_") + 1, input.length());
						out.println("SMS: " + list.get(Integer.parseInt(input) - 1));

					}
					if (input.contains("deleteMy_")) {
						input = input.substring(input.indexOf("_") + 1, input.length());
						list.remove(Integer.parseInt(input) - 1);
						out.println("SMS №" + input + " has been deleted");
					} else
						list.add(input);
				}
				out.close();
				in.close();
				fromclient.close();
				servers.close();
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void run() {
		try {
			new Thread(new ClWrie()).start();
			
			String serverMessage;
			// System.out.println("ПОТООК ПОШОООООООЛ");
			try {
				while (true) {
					input = in.readLine();
					System.out.println("В чате пишут: " + input);
					//out.println("Сервер: " + input);
					// list.add(output);
					
					// System.out.println("ПОТООК ИДЕЕЕЕЕЕЕЕЕЕЕЕЕЕт");
					
					for (Serverss vr : Server1.serverList) {
						out.println(input);
                    }
					
					
				}
			} catch (IOException e) {
				System.out.println("Общение скончалось(((");
				// e.printStackTrace();
			}
		} catch (Exception e) {

		}
	}

}


