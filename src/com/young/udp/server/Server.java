package com.young.udp.server;

import com.young.udp.SocketDto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器，消息中转站
 * @author young
 * create_date 2018/9/11 10:42
 * @version 1.0
 */
public class Server implements Runnable {
    private Map<User, String> userStringMap = new ConcurrentHashMap<>();
    private DatagramSocket socket;

    public Server() {
        try {
            this.socket = new DatagramSocket(7369);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                socket.receive(packet);
                SocketDto socketDto = SocketDto.getObject(packet.getData());
                send(socket, packet);
                User user = new User(socketDto.getName(), socketDto.getHost(), socketDto.getPort());
                if (socketDto.getType().equals(SocketDto.REGISTER)) {
                    userStringMap.put(user, user.getName());
                }
                if (socketDto.getType().equals(SocketDto.LOGOUT)) {
                    userStringMap.remove(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(DatagramSocket socket, DatagramPacket packet) throws IOException {
        for (User user : userStringMap.keySet()) {
            InetAddress ip = InetAddress.getByName(user.getHost());
            packet = new DatagramPacket(packet.getData(), packet.getLength(), ip, user.getPort());
            socket.send(packet);
        }
    }
}
