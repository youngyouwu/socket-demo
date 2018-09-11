package com.young.udp;

import com.young.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;

/**
 * @author young
 * create_date 2018/9/11 16:07
 * @version 1.0
 */
public class UserGUI extends JFrame {
    private JButton sendBtn; // 消息发送按钮
    private JButton resetBtn; // 消息重置按钮
    private JTextArea listText; // 消息显示列表
    private JTextArea sendText; // 消息输入框

    private int clientPort;
    private String username;

    private UserGUI(int clientPort, String username) {
        this.clientPort = clientPort;
        this.username = username;
        createListPanel();
        createInputPanel();
        createBtnPanel();
        even();
        init();
        receive();
    }

    private void init() {
        this.setTitle("聊天室"); // 设置标题
        this.setSize(400, 600); // 设置窗体大小
        this.setResizable(false); // 窗体大小不可变
        this.setLocationRelativeTo(null); // 窗体屏幕居中
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 点击关闭按钮结束程序
        this.setVisible(true); // 显示窗体
    }

    private void createListPanel() {
        JScrollPane listPanel = new JScrollPane();
        listPanel.setPreferredSize(new Dimension(400, 410));
        listPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listText = new JTextArea();
        listText.setLineWrap(true);
        listText.setEditable(false);
        listText.setFont(new Font("行楷", Font.PLAIN, 18));
        listText.setBackground(Color.CYAN);
        listPanel.setViewportView(listText);
        this.add(listPanel, BorderLayout.NORTH);
    }

    private void createInputPanel() {
        JScrollPane inputPanel = new JScrollPane();
        inputPanel.setPreferredSize(new Dimension(400, 150));
        inputPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sendText = new JTextArea();
        sendText.setLineWrap(true);
        sendText.setFont(new Font("行楷", Font.PLAIN, 18));
        inputPanel.setViewportView(sendText);
        this.add(inputPanel, BorderLayout.CENTER);
    }

    private void createBtnPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(400, 40));
        btnPanel.setBackground(Color.LIGHT_GRAY);
        sendBtn = new JButton("发送");
        resetBtn = new JButton("重置");
        btnPanel.add(sendBtn);
        btnPanel.add(resetBtn);
        this.add(btnPanel, BorderLayout.SOUTH);
    }

    private void send(Message msg) {
        try {
            InetAddress serveIp = InetAddress.getByName("localhost"); // 服务器IP
            int serverPort = 7369; // 服务器端口
            DatagramSocket socket = new DatagramSocket();
            byte[] message = Util.getByteArray(msg);
            DatagramPacket packet = new DatagramPacket(message, message.length, serveIp, serverPort);
            socket.send(packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void even() {
        // 点击按钮发送数据
        sendBtn.addActionListener(e -> {
            String text = sendText.getText();
            sendText.setText("");
            send(new Message(this.username, text));
        });
        // 绑定事件，按回车键发送数据
        sendText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (KeyEvent.VK_ENTER == keyChar) {
                    String text = sendText.getText();
                    sendText.setText("");
                    send(new Message(username, text.substring(0, text.length() - 1)));
                }
            }
        });
        // 清空发送框
        resetBtn.addActionListener(e -> sendText.setText(""));
    }

    private void receive() {
        Executors.newCachedThreadPool().execute(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(clientPort);
                while (true) {
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(packet);
                    Message message = (Message) Util.getObject(packet.getData());
                    listText.append(message.getFrom());
                    listText.append(" : ");
                    listText.append(LocalDateTime.now().toString());
                    listText.append("\n");
                    listText.append(message.getContent());
                    listText.append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        new UserGUI(8888, "jerry");
        new UserGUI(8887, "tom");
        new UserGUI(8886, "ada");
        new UserGUI(8885, "addy");
    }
}
