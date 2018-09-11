package com.young.udp;

import com.young.util.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author young
 * create_date 2018/9/11 10:42
 * @version 1.0
 */
public class Server {
    private static List<User> userList = new ArrayList<User>(4);

    static {
        userList.add(new User("localhost", 8888));
        userList.add(new User("localhost", 8887));
        userList.add(new User("localhost", 8886));
        userList.add(new User("localhost", 8885));
    }

    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(7369);
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                System.out.println(Util.getObject(packet.getData()).toString());
                send(socket, packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void send(DatagramSocket socket, DatagramPacket packet) throws IOException {
        for (User user : userList) {
            InetAddress ip = InetAddress.getByName(user.getHost());
            packet = new DatagramPacket(packet.getData(), packet.getLength(), ip, user.getPort());
            socket.send(packet);
        }
    }
}
