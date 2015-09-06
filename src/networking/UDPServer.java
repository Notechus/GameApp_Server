package networking;

import java.io.*;
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author notechus
 */
public class UDPServer implements Runnable {

    private static final Logger log = Logger.getLogger(UDPServer.class.getName());
    private DatagramSocket sock;
    private DatagramPacket incoming;
    private static final int port = 7777;
    private InetSocketAddress host;
    private Input input;

    private Thread t;

    public UDPServer() {
        try {
            sock = new DatagramSocket(null);
            host = new InetSocketAddress(InetAddress.getByName("192.168.1.10"), port);
            input = new Input();
            init();
        } catch (IOException ex) {
            echo(ex.getMessage());
        }
    }

    @Override
    public void run() {
        //communication loop
        int count = 0;
        try {
            while (true) {
                sock.receive(incoming);
                byte[] data = incoming.getData();
                byte[] reply = new byte[65536];
                //initialising recieved object
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);
                try {
                    Packet p = (Packet) is.readObject();
                    input = p.getInput();
                    echo((count++) + " Packet recieved: " + p.toString() + " from: " + incoming.getAddress());
                    reply = ("Recieved packet " + p.toString()).getBytes();
                } catch (ClassNotFoundException ex) {
                    echo(ex.getMessage());
                }
                //setting up reply
                DatagramPacket replyPacket = new DatagramPacket(reply, reply.length, incoming.getAddress(), incoming.getPort());
                sock.send(replyPacket);
            }
        } catch (IOException ex) {
            echo(ex.getMessage());
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "UDPServer Thread");
            t.start();
        }
    }

    public Input getInput() {
        return input;
    }

    public void stop() {
        sock.close();
    }

    public void init() throws IOException {

        //setting up logger
        FileHandler fh = new FileHandler("ServerLog.log", true);
        log.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        log.setUseParentHandlers(false);
        echo("Started application");

        // creating a server socket, parameter is local port number
        sock.bind(host);

        //buffer to receive incoming data
        byte[] buffer = new byte[65536];
        incoming = new DatagramPacket(buffer, buffer.length);

        // Wait for an incoming data
        echo("Server socket created. Waiting for incoming data...");
    }

    //simple function to echo data to terminal and to log file
    public void echo(String msg) {
        log.info(msg);
        System.out.println(msg);
    }
}
