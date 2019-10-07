import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadAdmin extends Thread {
    private Socket admin;
    private DataOutputStream dos;
    private DataInputStream dis;

    public ThreadAdmin(Socket cliente) throws IOException{
        this.admin = cliente;
        this.dos = new DataOutputStream(cliente.getOutputStream());
        this.dis = new DataInputStream(cliente.getInputStream());
    }

    public DataOutputStream getDos() {
        return this.dos;
    }

    public void run() {
        while (true) {
            try {
                String mensagem = this.dis.readUTF();
                String[] message = mensagem.split(" ");
                String action = message[0];

                switch (action) {
                    case "topics":
                        dos.writeUTF(Repository.topicos.toString());
                        break;
                    case "create":
                        Repository.topicos.put(message[1], new Topico(message[1]));
                        dos.writeUTF("Tópico " + message[1] + " criado");
                        break;
                    case "write":
                        if (message.length < 3) {
                            dos.writeUTF("Chamada inválida.");
                        } else {
                            Topico t = Repository.topicos.get(message[1]);
                            t.addMessage(message[2]);
                            ArrayList<ThreadCliente> subscribers = t.getSubscribers();
                            for (ThreadCliente subscriber : subscribers) {
                                subscriber.getDos().writeUTF(message[1] + " - " + message[2]);
                            }
                        }
                        break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
