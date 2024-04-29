package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketManager {
	private static SocketManager instance;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static final int port = 9090;
    private static String host = "localhost";

    SocketManager() throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void enviar(String mensaje) {
        out.println(mensaje);
    }

    public String recibir() throws IOException {
        return in.readLine();
    }

    public Socket getSocket() {
        return socket;
    }

    public void close() throws IOException {
    	out.close();
        in.close();
        socket.close();
    }
}