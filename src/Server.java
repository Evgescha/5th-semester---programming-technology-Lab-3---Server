import java.io.*;
import java.net.*;
import java.util.LinkedList;

/**
 * ������ ��������� ���������� ��������������������� ���.
 * ���� � ��������� ������� ������� - � ������ Server.
 * @author izotopraspadov, the tech
 * @version 2.0
 */

class ServerSomthing extends Thread {
    
    private Socket socket; // �����, ����� ������� ������ �������� � ��������,
    // ����� ���� - ������ � ������ ����� �� �������
    private BufferedReader in; // ����� ������ �� ������
    private BufferedWriter out; // ����� ������� � �����
    
    /**
     * ��� ������� � �������� ��������� ����� (�������� ������)
     * @param socket
     * @throws IOException
     */
    
    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;
        // ���� ������ �����/������ �������� � ������������� ����������, ��� ������������ ������
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Server.story.printStory(out); // ����� ������ ��������� ��� �������� ������� ��������� 10
        // ���������� ������ ����������
        start(); // �������� run()
    }
    @Override
    public void run() {
        String word;
        try {
            // ������ ��������� ������������ ���� - ��� �������
            word = in.readLine();
            try {
                out.write(word + "\n");
                out.flush(); // flush() ����� ��� ������������ ���������� ������
                // ���� ����� ����, � ������� ������ ��� �������� ����
            } catch (IOException ignored) {}
            try {
                while (true) {
                    word = in.readLine();
                    if(word.equals("stop")) {
                        this.downService(); // ��������
                        break; // ���� ������ ������ ������ - ������� �� ����� ���������
                    }
                    System.out.println("Echoing: " + word);
                    Server.story.addStoryEl(word);
                    for (ServerSomthing vr : Server.serverList) {
                        vr.send(word); // �������� �������� ��������� � ������������ ������� ���� ��������� ������ ���
                    }
                }
            } catch (NullPointerException ignored) {}

            
        } catch (IOException e) {
            this.downService();
        }
    }
    
    /**
     * ������� ������ ��������� ������� �� ���������� ������
     * @param msg
     */
    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
        
    }
    
    /**
     * �������� �������
     * ���������� ���� ��� ���� � �������� �� ������ �����
     */
    private void downService() {
            try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerSomthing vr : Server.serverList) {
                    if(vr.equals(this)) vr.interrupt();
                    Server.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {}
    }
}

/**
 * ����� �������� � ��������� ���������
 * ������ ���������� � ��������� 10 (��� ������) ����������
 */

class Story {
    
    private LinkedList<String> story = new LinkedList<>();
    
    /**
     * �������� ����� ������� � ������
     * @param el
     */
    
    public void addStoryEl(String el) {
        // ���� ��������� ������ 10, ������� ������ � ��������� �����
        // ����� ������ ��������
        if (story.size() >= 10) {
            story.removeFirst();
            story.add(el);
        } else {
            story.add(el);
        }
    }
    
    /**
     * �������� ��������������� ������ ��������� �� ������
     * � ����� ������ ������� ������� (������ �����������)
     * @param writer
     */
    
    public void printStory(BufferedWriter writer) {
        if(story.size() > 0) {
            try {
                writer.write("History messages" + "\n");
                for (String vr : story) {
                    writer.write(vr + "\n");
                }
                writer.write("/...." + "\n");
                writer.flush();
            } catch (IOException ignored) {}
            
        }
        
    }
}

public class Server {

    public static final int PORT = 8080;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>(); // ������ ���� ����� - �����������
    // �������, ��������� ������ ������ �������
    public static Story story; // ������� ���������
    
    /**
     * @param args
     * @throws IOException
     */
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        story = new Story();
        System.out.println("Server Started");
        try {
            while (true) {
                // ����������� �� ������������� ������ ����������:
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerSomthing(socket)); // �������� ����� ����������� � ������
                } catch (IOException e) {
                    // ���� ���������� ��������, ����������� �����,
                    // � ��������� ������, ���� ������� ���:
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}