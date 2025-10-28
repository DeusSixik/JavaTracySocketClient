package dev.sdm.profiler.network;

import dev.sdm.profiler.buffer.SharedBuffer;

import java.util.HashMap;
import java.util.Map;

public class SerializerRegister {

    protected final Map<Class<?>, Serializers<?>> serializers = new HashMap<>();

    public <T> void register(Class<T> clazz, Serializers<T> serializer) {
        serializers.put(clazz, serializer);
    }

    @SuppressWarnings("unchecked")
    public <T> Serializers<T> get(Class<T> clazz) {
        return (Serializers<T>) serializers.get(clazz);
    }

    public boolean has(Class<?> clazz) {
        return serializers.containsKey(clazz);
    }

    public static SerializerRegister createDefault() {
        SerializerRegister reg = new SerializerRegister();

        reg.register(String.class, SharedBuffer::write);
        reg.register(Integer.class, SharedBuffer::write);
        reg.register(int.class, SharedBuffer::write);
        reg.register(Float.class, SharedBuffer::write);
        reg.register(float.class, SharedBuffer::write);
        reg.register(Double.class, SharedBuffer::write);
        reg.register(double.class, SharedBuffer::write);
        reg.register(Long.class, SharedBuffer::write);
        reg.register(long.class, SharedBuffer::write);
        reg.register(Character.class, SharedBuffer::write);
        reg.register(char.class, SharedBuffer::write);

        return reg;
    }
}
