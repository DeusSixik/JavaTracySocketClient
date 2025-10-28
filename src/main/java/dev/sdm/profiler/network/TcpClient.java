package dev.sdm.profiler.network;

import dev.sdm.profiler.buffer.SharedBuffer;
import dev.sdm.profiler.events.NetworkEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class TcpClient {

    public static Consumer<String> MessageConsumer = System.out::println;

    public static String IPConnection = "127.0.0.1";
    public static final int PortConnection = 9001;

    private static Socket socket;

    protected static ConcurrentLinkedQueue<NetworkEvent> MessagesQueue = new ConcurrentLinkedQueue<>();

    public static void tryConnect() throws Exception {
        SerializerRegister register = SerializerRegister.createDefault();
        ProfilerSerializer serializer = new ProfilerSerializer(register);

        try (Socket socket = new Socket(IPConnection, PortConnection)) {
            TcpClient.socket = socket;
            socket.setKeepAlive(true);
            MessageConsumer.accept("[Java] Connected to TracySocketServer server");
            OutputStream out = socket.getOutputStream();

            while (!Thread.interrupted()) {
                if(socket.isClosed()) break;

                NetworkEvent event = MessagesQueue.poll();
                if (event == null) {
                    Thread.sleep(1);
                    continue;
                }

                SharedBuffer buffer = serializer.serializeEvent(event);
                int length = buffer.position();
                byte[] data = buffer.getBuffer().array();

                out.write((length >> 24) & 0xFF);
                out.write((length >> 16) & 0xFF);
                out.write((length >> 8) & 0xFF);
                out.write(length & 0xFF);

                out.write(data, 0, length);
                out.flush();
            }

            MessageConsumer.accept("[Java] Disconnect from TracySocketServer");
        }
    }

    public static void sendMessage(NetworkEvent message) {
        MessagesQueue.add(message);
    }

    public static boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public static void disconnect() throws IOException {
        if(isConnected()) socket.close();
    }

    public static Socket getSocket() {
        return socket;
    }
}
