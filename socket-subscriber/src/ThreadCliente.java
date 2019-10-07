import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadCliente extends Thread {
    private Socket cliente;
    private DataOutputStream dos;
    private DataInputStream dis;

    public ThreadCliente(Socket cliente) throws IOException{
        this.cliente = cliente;
        this.dos = new DataOutputStream(cliente.getOutputStream());
        this.dis = new DataInputStream(cliente.getInputStream());
    }

    public DataOutputStream getDos() {
        return this.dos;
    }

    public void run() {
        try {
            String mensagem = this.dis.readUTF();
            String[] message = mensagem.split(" ");
            String action = message[0];
            Boolean envio = false;
            while (true) {
                switch (action) {
                    case "subscribe":
                        Topico topico = Repository.topicos.get(message[1]);
                        if (topico != null && envio == false) {
                            envio = true;
                            topico.subscribe(this);
                            dos.writeUTF("Inscrito em: " + message[1]);
                        } else if (envio == false){
                            dos.writeUTF("Tópico inexistente.");
                        }
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
