import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientWrite  implements Runnable  {

	static String input, output;
	static BufferedReader in = null;
	static PrintWriter out = null;
	static Serverss serv;
	static ArrayList<String> list = new ArrayList<String>();
	
	ServerSocket servers = null;
	Socket fromclient = null;
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
			out = new PrintWriter(fromclient.getOutputStream(), true);
			//System.out.println("Êëåíò ïîäêëş÷èëñÿ");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

		
		
		BufferedReader inu = new BufferedReader(new InputStreamReader(System.in));
		String a;
		// System.out.println("ÏÎÒÎÎÊ ÏÎØÎÎÎÎÎÎÎË");
		try {
			while ((output = inu.readLine()) != null) {

				out.println("Ñåğâåğ: " + output);
				list.add(output);
				// System.out.println(output);
				// System.out.println("ÏÎÒÎÎÊ ÈÄÅÅÅÅÅÅÅÅÅÅÅÅÅÅò");
			}
		} catch (IOException e) {
			System.out.println("Îáùåíèå ñêîí÷àëîñü(((");
			// e.printStackTrace();
		}
		
	}

}
