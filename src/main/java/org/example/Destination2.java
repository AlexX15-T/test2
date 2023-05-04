package org.example;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Destination2 {
    public static final int RECEIVE_PORT = 3456;

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(RECEIVE_PORT);
        byte[] buffer = new byte[4];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        for (int i = 0; i < Sender.NUM_PACKETS; i++) {
            socket.receive(packet);
            int packetData = ByteBuffer.wrap(packet.getData()).getInt();
            System.out.println("Received packet #" + (i + 1) + " with data " + packetData + " from " + packet.getAddress().getHostName() + ":" + packet.getPort());
            packetData++;
            buffer = ByteBuffer.allocate(4).putInt(packetData).array();
            DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("localhost"), Destination3.RECEIVE_PORT);
            socket.send(sendPacket);
            System.out.println("Sent packet #" + (i + 1) + " with data " + packetData + " to " + sendPacket.getAddress().getHostName() + ":" + sendPacket.getPort());
        }
        socket.close();
    }
}