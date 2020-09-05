package Dto;

import lombok.Synchronized;

import java.io.IOException;
import java.net.*;

public class Goip {
    private final DatagramSocket socket;
    private final int port;
    private final InetAddress  addresse;
    private final String password;

    public Goip(String addresse, int port,  String password) throws UnknownHostException, SocketException {
        this.port = port;
        this.addresse = InetAddress.getByName(addresse);
        this.password = password;
        this.socket   = new DatagramSocket();
        this.socket.setSoTimeout(30000);
    }

    public void close(){
        this.socket.close();
    }

    public void send(String msg) throws IOException {
        System.out.println("send " + msg);
        msg += "/n";
        byte[] buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, this.addresse, this.port);
        this.socket.send(packet);
        System.out.println("send finish");
    }

    private String listenToReply( String expected, String unExpected) throws Exception {
        System.out.println("listen");
        String received ;
        byte[] buf = new byte[2048];
        DatagramPacket packet = new DatagramPacket(buf,2048,this.addresse,this.port);

        for (int i = 0; i <30 ; i++) {
            System.out.println("in the for" +i);
            this.socket.receive(packet);
            System.out.println("after packet");
            received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("RECV:" + received);

            if (received.contains(expected) )
                return received;

            if (received.contains(unExpected))
                throw new Exception(received);

        }

        System.out.println("fin listen");

        throw new Exception("Timeout listen to Reply");
    }
    @Synchronized
    public String sendUssd(String ussd, Long id) throws Exception {
        send("USSD " + id + " " + this.password + " " + ussd );
        String rec =  listenToReply("USSD " +id, "USSDERROR " +id );
        this.close();
        return rec;
    }

}
