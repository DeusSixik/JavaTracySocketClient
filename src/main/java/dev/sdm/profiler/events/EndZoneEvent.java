package dev.sdm.profiler.events;

public record EndZoneEvent(String name, long threadId) implements NetworkEvent {

    @Override
    public byte id() {
        return 0;
    }

    @Override
    public Object getPrams() {
        return new Object[] { name, threadId };
    }
}
