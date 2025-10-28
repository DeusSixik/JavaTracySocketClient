package dev.sdm.profiler.events;

public class MarkFrameEvent implements NetworkEvent{
    @Override
    public byte id() {
        return 3;
    }

    @Override
    public Object getPrams() {
        return 0;
    }
}
