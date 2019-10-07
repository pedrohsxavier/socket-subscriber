import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.net.Socket;

public class Topico {
    private String name;
    private ArrayList<ThreadCliente> subscribers;
    private ArrayList<String> messages;

    public Topico(String name) {
        this.name = name;
        this.subscribers = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public Boolean subscribe(ThreadCliente socketCliente) {
        if (this.subscribers.indexOf(socketCliente) == -1) {
            this.subscribers.add(socketCliente);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ThreadCliente> getSubscribers() {
        return this.subscribers;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public String toString() {
        return name + '\n';
    }

}
