package com.young.udp;

import com.young.util.Util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

/**
 * @author young
 * create_date 2018/9/11 10:42
 * @version 1.0
 */
public class Server {
    private List<User> userList = new CopyOnWriteArrayList<>();

    public Server() {
        receive();
        init();
    }

    private void init() {
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

    private void receive() {
        Executors.newCachedThreadPool().execute(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(7368);
                while (true) {
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(packet);
                    User user = (User) Util.getObject(packet.getData());
                    if (user.getType() == 1) {
                        System.out.println(user + "加入");
                        userList.add(user);
                    } else {
                        System.out.println(user + "退出");
                        userList.remove(user);
                    }
                    send(socket, packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void send(DatagramSocket socket, DatagramPacket packet) throws IOException {
        for (User user : userList) {
            InetAddress ip = InetAddress.getByName(user.getHost());
            packet = new DatagramPacket(packet.getData(), packet.getLength(), ip, user.getPort());
            socket.send(packet);
        }
    }
}
