package dev.sdm.profiler.network;

import dev.sdm.profiler.buffer.SharedBuffer;

@FunctionalInterface
public interface Serializers<T> {

    void write(SharedBuffer buffer, T object);
}
