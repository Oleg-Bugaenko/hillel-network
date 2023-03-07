package ua.hillel.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class UdpEchoServer implements AutoCloseable{
    private DatagramSocket datagramSocket;

    public UdpEchoServer(int portConnection) throws IOException {
        datagramSocket = new DatagramSocket(portConnection);                  //відкриваємо сокет UDP

        boolean isRunning = true;
        while (isRunning) {
            //отримання пакету та даних відправника
            byte[] bytes = new byte[128];                               //створюємо буфер для зберігання даних
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);    //
            datagramSocket.receive(datagramPacket);                     //запис даних які увійшли в порт в об'єкт datagramPacket
            InetAddress address = datagramPacket.getAddress();          //отримання IP адреси відправника пакету
            int port = datagramPacket.getPort();                        //отримання порту відправника
            byte[] data = datagramPacket.getData();                     //отримання даних

            String request = new String(data, 0, datagramPacket.getLength());
            System.out.print(request);


            //відправлення пакету
            String responseText = "Echo response: " + LocalDateTime.now() + "\n";
            byte[] responseBytes = responseText.getBytes();

            DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
            datagramSocket.send(responsePacket);

            if (request.equalsIgnoreCase("-end")) isRunning = false;        //зупинка сервера
        }
    }


    @Override
    public void close() throws Exception {
        if (!datagramSocket.isClosed()) datagramSocket.close();
    }
}
