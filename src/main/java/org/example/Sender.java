package org.example;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Sender {
    public static final int NUM_PACKETS = 100;
    private static final int MIN_PACKET_DATA = 1;
    private static final int MAX_PACKET_DATA = 100;
    public static final int SEND_PORT = 1234;

    public static void main(String[] args) throws IOException {
        InetAddress[] destinations = {
                InetAddress.getByName("localhost"),
                InetAddress.getByName("localhost"),
                InetAddress.getByName("localhost")
        };

        int[] ports = {2345, 3456, 4567};

        DatagramSocket socket = new DatagramSocket();
        int packetData = MIN_PACKET_DATA;
        for (int i = 0; i < NUM_PACKETS; i++) {
            int destinationIndex = (int) (Math.random() * destinations.length);
            byte[] buffer = ByteBuffer.allocate(4).putInt(packetData).array();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, destinations[destinationIndex], ports[destinationIndex]);
            socket.send(packet);
            System.out.println("Sent packet #" + (i + 1) + " with data " + packetData + " to " + destinations[destinationIndex].getHostName() + ":" + ports[destinationIndex]);
            packetData++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}