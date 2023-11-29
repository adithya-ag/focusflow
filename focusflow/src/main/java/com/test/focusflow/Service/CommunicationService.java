package com.test.focusflow.Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
public class CommunicationService {

    private static final int DISCOVERY_PORT = 12345;
    private List<String> receivedMessages = new ArrayList<>();

    public InetAddress getBroadcastAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue; // Skip loopback and inactive interfaces
            }

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();

                if (broadcast != null) {
                    return broadcast;
                }
            }
        }

        throw new RuntimeException("No suitable broadcast address found. Try specifying the broadcast address explicitly.");
    }

    public void sendMessage(String message, InetAddress broadcastAddress) {
        try {
            DatagramSocket sendSocket = new DatagramSocket();

            byte[] sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcastAddress, DISCOVERY_PORT);
            sendSocket.send(sendPacket);

            sendSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }

    public void addReceivedMessage(String message) {
        receivedMessages.add(message);
    }
}
