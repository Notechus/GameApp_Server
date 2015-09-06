package networking;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author notechus
 */
public class UDPClient implements Runnable {

    static final Logger log = Logger.getLogger(UDPClient.class.getName());
    private DatagramSocket sock = null;
    private final int port = 7777;
    private InetAddress host = null;
    ByteArrayOutputStream outputStream;
    private Thread thread;
    private long time;

    public UDPClient() {
        try {
            host = InetAddress.getByName("notechus.ddns.net");
            init();
            echo("Started client");
        } catch (UnknownHostException ex) {
            echo(ex.getMessage());
        } catch (IOException ex) {
            echo(ex.getMessage());
        }
    }

    public void init() throws IOException {
        FileHandler fh = new FileHandler("ClientLog.log", true);
        log.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        log.setUseParentHandlers(false);
        time = System.nanoTime();
    }

    @Override
    public void run() {

    }

    public void send(Packet p) {
        try {
            sock = new DatagramSocket();

            //take input and send the packet
            outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);
            os.writeObject(p);
            byte[] b = outputStream.toByteArray();
            DatagramPacket db = new DatagramPacket(b, b.length, host, port);
            sock.send(db);
            time = System.nanoTime();
        } catch (IOException ex) {
            echo(ex.getMessage());
        }
    }

    public String reply() {
        String s = "";
        try {
            byte[] buffer = new byte[65536];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            sock.receive(reply);
            byte[] data = reply.getData();
            s = new String(data, 0, reply.getLength());
        } catch (IOException ex) {
            echo(ex.getMessage());
        }
        return s;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, "UDPClient Thread");
            thread.start();
        }
    }

    public void stop() {
        sock.close();
        echo("socket closed");
    }

    public long getLastSendTime() {
        return time;
    }

//simple function to echo data to terminal
    public static void echo(String msg) {
        log.info(msg);
        System.out.println(msg);
    }
}
