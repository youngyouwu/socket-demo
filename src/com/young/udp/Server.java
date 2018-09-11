package com.young.udp;

import com.young.util.Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author young
 * create_date 2018/9/11 10:42
 * @version 1.0
 */
public class Server {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9999);
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        socket.receive(packet);
        System.out.println(Util.getObject(packet.getData()));
        socket.close();
    }
}
