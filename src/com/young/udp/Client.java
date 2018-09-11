package com.young.udp;

import com.young.User;
import com.young.util.Util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * @author young
 * create_date 2018/9/11 10:41
 * @version 1.0
 */
public class Client {
    public static void main(String[] args) throws Exception {
        InetAddress ip = InetAddress.getByName("localhost");
        DatagramSocket socket = new DatagramSocket();
        byte[] message = getMessage();
        DatagramPacket packet = new DatagramPacket(message, message.length, ip, 9999);
        socket.send(packet);
        socket.close();
    }

    private static byte[] getMessage() throws Exception {
        Scanner in = new Scanner(System.in);
        User user = new User();
        System.out.print("姓名:");
        user.setName(in.nextLine());
        System.out.print("年龄:");
        user.setAge(in.nextInt());
        user.setBirthday(new Date());
        ArrayList<User> users = new ArrayList<>(3);
        users.add(user);
        users.add(user);
        users.add(user);
        return Util.getByteArray(users);
    }
}
