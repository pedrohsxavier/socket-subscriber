import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) throws IOException {
        System.out.println("====== Servidor ======");
        ServerSocket serverSocket = new ServerSocket(7001);

        Socket admin = serverSocket.accept();
        ThreadAdmin tratadorAdmin = new ThreadAdmin(admin);
        tratadorAdmin.start();

        while (true) {
            Socket cliente = serverSocket.accept();
            ThreadCliente tratadorCliente = new ThreadCliente(cliente);
            tratadorCliente.start();
        }
    }
}