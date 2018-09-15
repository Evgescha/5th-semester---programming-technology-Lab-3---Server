import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		
		BufferedReader in = null;
		PrintWriter out = null;

		ServerSocket servers = null;
		Socket fromclient = null;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("����� ���������� �� ������.");
		System.out.println("");
		
		InetAddress IP=InetAddress.getLocalHost();
		System.out.println("IP ����� ������� - "+IP.getHostAddress());
		
		System.out.println("������� ���� ��� ������ � ��������� (8080 �� ���������)");
		int portServ = sc.nextInt();
		

		// create server socket
		try {
			servers = new ServerSocket(portServ);
		} catch (IOException e) {
			System.out.println("������ ����������� � ����� " + portServ + ". ����� ���������� ����� �� ����������.");
			System.exit(-1);
		}

		try {
			System.out.print("�������� ����������� ��������...");
			fromclient = servers.accept();
			System.out.println("������ �����������!");
		} catch (IOException e) {
			System.out.println("������. ����� ���������� ����� �� ����������");
			System.exit(-1);
		}

		in = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
		out = new PrintWriter(fromclient.getOutputStream(), true);
		String input, output;

		System.out.println("�������� ��������� �� �������");
		while ((input = in.readLine()) != null) {
			if (input.equalsIgnoreCase("exit"))
				break;
			out.println("������: " + input);
			System.out.println(input);
		}
		out.close();
		in.close();
		fromclient.close();
		servers.close();
	}
}