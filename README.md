# ☕ Tracy Profiler Java Client (TracySocketClient)

**TracySocketClient** — This is a Java SDK for sending profiler events.  
(zones, frames, frames, and other markers) in the native **Tracy Profiler**  
via an intermediate TCP server [`Tracy Socket Server`](https://github.com/DeusSixik/TracySocketServer).

This project implements a convenient layer for profiling Java applications.,  
Minecraft mods (Forge / NeoForge/Fabric) and any real-time JVM programs without the need to write a JNI/JNA implementation

## 🚀 Main features

- 📡 Sending the `StartZoneEvent` / `EndZoneEvent` zones
- 🧩 Support for frames (`MarkFrameEvent`)
- 🧵 Support for multiple threads (`Thread.currentThread().getId()`)
- ⚙️ Flexible serialization system (`SerializerRegister`, `ProfilerSerializer`)
- 💬 Extensible events (`NetworkEvent` base class)
- 📦 Without third - party dependencies — only Java SE 17+

## 📂 Project structure
```
dev.sdm.profiler/
├── buffer/
│ └── SharedBuffer.java ← binary buffer with auto-indexing
├── events/
│ ├── NetworkEvent.java ← basic interface
│ ├── StartZoneEvent.java ← the beginning of the zone
│ ├── EndZoneEvent.java ← end of the zone
│ └── MarkFrameEvent.java ← frame mark
├── network/
│ ├── TcpClient.java ← TCP client, message queue
│ ├── SerializerRegister.java ← registry of serializers
│ ├── Serializers.java ← serializer interface
│ └── ProfilerSerializer.java ← a common event serializer
└── TracyProfiler.java ← high-level API for profiling
```

## ⚙️ Installation and launch
### 1️⃣ Launch the Tracy Socket Server
On the C++ side (see [`Tracy Socket Server`](https://github.com/DeusSixik/TracySocketServer )):
```bash
TracySocketServer.exe
[C++] Server listening on port 9001...
```

### 2️⃣ Connect the Java client

```java
TracyProfiler.startConnection();

[Java] Connected to TracySocketServer server
```

### 3️⃣ Open the Tracy Viewer

1. Launch Tracy.exe
2. Click Connect → 127.0.0.1

## 🧱 Usage example

```java
public class Example {
    public static void main(String[] args) throws Exception {
        try {
            TracyProfiler.startConnection();
            TracyProfiler.startZone("LoadWorld");
            Thread.sleep(100);
            TracyProfiler.startZone("GenerateTerrain");
            Thread.sleep(50);
            TracyProfiler.endZone();
            TracyProfiler.endZone();

            TracyProfiler.markFrame();
            TracyProfiler.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
```

## 🧠 How it work ?
The Java client serializes the data into a binary buffer and sends it over TCP.
On the C++ side, the `TracySocketServer' receives and translates them into real `ScopedZone` Tracy objects.

## 👥 Authors
`TracySocketClient` — developed as part of the BehindTheScenery / SDM Team project for profiling JVM parts of the BTS Engine
