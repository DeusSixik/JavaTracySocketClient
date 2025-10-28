package dev.sdm.profiler.network;

import dev.sdm.profiler.buffer.SharedBuffer;
import dev.sdm.profiler.events.NetworkEvent;

public record ProfilerSerializer(SerializerRegister register) {

    public SharedBuffer serializeEvent(NetworkEvent event) {
        SharedBuffer buf = new SharedBuffer(2048);
        buf.write(event.id());

        final Object params = event.getPrams();

        if (params == null) return buf;

        if (params.getClass().isArray()) {
            Object[] arr = (Object[]) params;
            for (Object obj : arr) {
                writeDynamic(buf, obj);
            }
        } else {
            writeDynamic(buf, params);
        }

        return buf;
    }

    private void writeDynamic(SharedBuffer buf, Object value) {
        if (value == null) return;
        final Class<?> type = value.getClass();

        final Serializers<?> serializer = register.get(type);
        if (serializer == null) {
            throw new IllegalStateException("No serializer for type: " + type);
        }

        @SuppressWarnings("unchecked") final Serializers<Object> s = (Serializers<Object>) serializer;
        s.write(buf, value);
    }
}
