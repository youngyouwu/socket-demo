package com.young.udp.client;

import com.young.udp.SocketDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
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
    private String name;
    private final String host = "localhost";

    public UserGUI(int clientPort, String name) {
        this.clientPort = clientPort;
        this.name = name;
        createListPanel();
        createInputPanel();
        createBtnPanel();
        even();
        init();
        sendPlus(SocketDto.register(this.name, this.host, clientPort));
        receive();
    }

    private void init() {
        this.setTitle("聊天室" + "-" + this.name); // 设置标题
        this.setSize(400, 600); // 设置窗体大小
        this.setResizable(false); // 窗体大小不可变
        this.setLocationRelativeTo(null); // 窗体屏幕居中
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 点击关闭按钮结束程序
        this.setVisible(true); // 显示窗体
    }

    private void createListPanel() {
        JScrollPane listPanel = new JScrollPane();
        listPanel.setPreferredSize(new Dimension(400, 410));
        listPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listText = new JTextArea();
        listText.setLineWrap(true);
        listText.setEditable(false);
        listText.setFont(new Font("行楷", Font.PLAIN, 16));
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
        sendText.setFont(new Font("行楷", Font.PLAIN, 16));
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

    private void even() {
        // 点击按钮发送数据
        sendBtn.addActionListener(e -> {
            String text = sendText.getText();
            sendText.setText("");
            sendPlus(SocketDto.message(this.name, text));
        });
        // 绑定事件，按回车键发送数据
        sendText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (KeyEvent.VK_ENTER == keyChar) {
                    String text = sendText.getText();
                    sendText.setText("");
                    sendPlus(SocketDto.message(UserGUI.this.name, text.substring(0, text.length() - 1)));
                }
            }
        });
        // 清空发送框
        resetBtn.addActionListener(e -> sendText.setText(""));
        // 关闭窗口事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sendPlus(SocketDto.logout(UserGUI.this.name, UserGUI.this.host, UserGUI.this.clientPort));
                super.windowClosed(e);
            }
        });
    }

    private void sendPlus(SocketDto socketDto) {
        try {
            int serverPort = 7369; // 服务器端口
            InetAddress serveIp = InetAddress.getByName("localhost"); // 服务器IP
            DatagramSocket socket = new DatagramSocket();
            byte[] message = SocketDto.getByteArray(socketDto);
            DatagramPacket packet = new DatagramPacket(message, message.length, serveIp, serverPort);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receive() {
        Executors.newCachedThreadPool().execute(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(UserGUI.this.clientPort);
                while (true) {
                    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(packet);
                    UserGUI.this.print(SocketDto.getObject(packet.getData()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void print(SocketDto socketDto) {
        if (socketDto.getType().equals(SocketDto.MESSAGE)) {
            listText.append(socketDto.getName());
            listText.append(" : ");
            listText.append(LocalDate.now().toString());
            listText.append("\n");
            listText.append(socketDto.getContent());
            listText.append("\n");
        } else {
            listText.append(socketDto.getName());
            if (socketDto.getType().equals(SocketDto.REGISTER)) {
                listText.append(" 加入");
            }
            if (socketDto.getType().equals(SocketDto.LOGOUT)) {
                listText.append(" 退出");
            }
            listText.append("\n");
        }
    }
}