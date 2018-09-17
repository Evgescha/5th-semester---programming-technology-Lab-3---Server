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

	public Server(Socket fromclient) {
		this.fromclient = fromclient;
	}

	public class ClWrie implements Runnable {
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
				while ((input = in.readLine()) != null) {
					
					System.out.println(input);

					if (input.contains("getMy_")) {
						input = input.substring(input.indexOf("_") + 1, input.length());
						out.println("SMS: " + list.get(Integer.parseInt(input) - 1));

					}
					if (input.contains("deleteMy_")) {
						input = input.substring(input.indexOf("_") + 1, input.length());
						list.remove(Integer.parseInt(input) - 1);
						out.println("SMS ¹" + input + " has been deleted");
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
			out = new PrintWriter(fromclient.getOutputStream(), true);
			BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
			String serverMessage;
			// System.out.println("ÏÎÒÎÎÊ ÏÎØÎÎÎÎÎÎÎË");
			try {
				while ((serverMessage = inu.readLine()) != null) {

					out.println("Ñåğâåğ: " + serverMessage);
					// list.add(output);
					// System.out.println(output);
					// System.out.println("ÏÎÒÎÎÊ ÈÄÅÅÅÅÅÅÅÅÅÅÅÅÅÅò");
				}
			} catch (IOException e) {
				System.out.println("Îáùåíèå ñêîí÷àëîñü(((");
				// e.printStackTrace();
			}
		} catch (Exception e) {

		}
	}

}