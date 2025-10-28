package dev.sdm.profiler.events;

public record StartZoneEvent(String name, long threadId) implements NetworkEvent{
    @Override
    public byte id() {
        return 1;
    }

    @Override
    public Object getPrams() {
        return new Object[] {name, threadId};
    }
}
