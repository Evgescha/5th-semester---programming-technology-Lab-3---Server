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

		System.out.println("����� ���������� �� ������.");
		System.out.println("");

		InetAddress IP = InetAddress.getLocalHost();
		System.out.println("IP ����� ������� - " + IP.getHostAddress());

		System.out.println("������� ���� ��� ������ � ��������� (8080 �� ���������)");
		int portServ = sc.nextInt();

		try {
			servers = new ServerSocket(portServ);
		} catch (IOException e) {
			System.out.println("������ ����������� � ����� " + portServ + ". ����� ���������� ����� �� ����������.");
			System.exit(-1);
		}
		// ���� ����������� ��� ����-������
        while (true) {
            try {
            	System.out.print("�������� ����������� ��������...");   
                socket = servers.accept();
            } catch (IOException e) {
            	System.out.println("������. ����� ���������� ����� �� ����������");
    			System.exit(-1);
            }
            new Thread(new Server(socket)).start();
            System.out.println("������ �����������!");
        }
    }
}
