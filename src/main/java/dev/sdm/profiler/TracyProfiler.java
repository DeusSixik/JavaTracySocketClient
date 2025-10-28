package dev.sdm.profiler;

import dev.sdm.profiler.events.EndZoneEvent;
import dev.sdm.profiler.events.MarkFrameEvent;
import dev.sdm.profiler.events.NetworkEvent;
import dev.sdm.profiler.events.StartZoneEvent;
import dev.sdm.profiler.network.TcpClient;

import java.io.IOException;
import java.util.function.Consumer;

public class TracyProfiler {

    public static void startConnection() throws Exception {
        TcpClient.tryConnect();
    }

    public static void disconnect() throws Exception {
        TcpClient.disconnect();
    }

    public static void markFrame() {
        sendEvent(new MarkFrameEvent());
    }

    public static void startZone(String name) {
        sendEvent(new StartZoneEvent(name, Thread.currentThread().getId()));
    }

    public static void endZone(String name) {
        sendEvent(new EndZoneEvent(name, Thread.currentThread().getId()));
    }

    public static void sendEvent(NetworkEvent event) {
        TcpClient.sendMessage(event);
    }

    public static void setLogger(Consumer<String> message) {
        TcpClient.MessageConsumer = message;
    }
}
