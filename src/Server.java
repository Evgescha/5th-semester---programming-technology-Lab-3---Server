import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server implements Runnable {

	static String input, output;
	static BufferedReader in = null;
	static PrintWriter out = null;
	static Server serv;
	static ArrayList<String> list = new ArrayList<String>();
	
	ServerSocket servers = null;
	Socket fromclient = null;
	Scanner sc = new Scanner(System.in);

	public Server(Socket socket) {
        this.fromclient = socket;
    }
	
	public class ClWrie implements  Runnable {
		public void run() {
			System.out.println("Ожидание сообщения от клиента");
			try {
				while ((input = in.readLine()) != null) {
					if (input.equalsIgnoreCase("exit"))
						break;

					System.out.println(input);

					if (input.contains("getMy_")) {
						input = input.substring(input.indexOf("_") + 1, input.length());
						out.println("SMS: " + list.get(Integer.parseInt(input)-1));
						
					} 
					if (input.contains("deleteMy_")) {
						input = input.substring(input.indexOf("_") + 1, input.length());
						list.remove(Integer.parseInt(input)-1);
						out.println("SMS №" + input+" has been deleted");
					} else
						list.add(input);
				}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				out.close();
				in.close();
				fromclient.close();
				servers.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
	}
	

	@Override
	public void run() {
		
			new Thread(new ClWrie()).start();
		
		try {
			in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
			out = new PrintWriter(fromclient.getOutputStream(), true);
			//System.out.println("Клент подключился");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

		
		
		BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
		String a;
		// System.out.println("ПОТООК ПОШОООООООЛ");
		try {
			while ((output = inu.readLine()) != null) {

				out.println("Сервер: " + output);
				list.add(output);
				// System.out.println(output);
				// System.out.println("ПОТООК ИДЕЕЕЕЕЕЕЕЕЕЕЕЕЕт");
			}
		} catch (IOException e) {
			System.out.println("Общение скончалось(((");
			// e.printStackTrace();
		}
	}

}