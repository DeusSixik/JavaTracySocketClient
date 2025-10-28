package dev.sdm.profiler.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicInteger;

public final class SharedBuffer {
    private final int buffer_size;
    private final ByteOrder order;
    private final AtomicInteger write_index = new AtomicInteger(0);
    private final AtomicInteger read_index = new AtomicInteger(0);

    private final ByteBuffer buffer;

    public SharedBuffer(int buffer_size, ByteOrder order) {
        this.buffer_size = buffer_size;
        this.order = order;
        this.buffer = ByteBuffer.allocate(buffer_size).order(order);
    }

    public SharedBuffer(int buffer_size) {
        this(buffer_size, ByteOrder.LITTLE_ENDIAN);
    }

    public int bufferSize() {
        return buffer_size;
    }

    public ByteOrder order() {
        return order;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public int position() {
        return write_index.get();
    }

    public void write(byte _byte) {
        buffer.put(write_index.getAndAdd(1), _byte);
    }

    public void write(short _short) {
        final int pos = write_index.getAndAdd(2);
        buffer.putShort(pos, _short);
    }

    public void write(int _int) {
        final int pos = write_index.getAndAdd(4);
        buffer.putInt(pos, _int);
    }

    public void write(long _long) {
        final int pos = write_index.getAndAdd(8);
        buffer.putLong(pos, _long);
    }

    public void write(float _float) {
        final int pos = write_index.getAndAdd(4);
        buffer.putFloat(pos, _float);
    }

    public void write(double _double) {
        final int pos = write_index.getAndAdd(8);
        buffer.putDouble(pos, _double);
    }

    public void write(char _char) {
        final int pos = write_index.getAndAdd(2);
        buffer.putChar(pos, _char);
    }

    public void write(String str) {
        final int len = str.length();
        write(len);
        final int pos = write_index.getAndAdd(len * 2);
        for (int i = 0; i < len; i++) {
            buffer.putChar(pos + i * 2, str.charAt(i));
        }
    }

    public byte readByte() {
        final int pos = read_index.getAndAdd(1);
        return buffer.get(pos);
    }

    public short readShort() {
        final int pos = read_index.getAndAdd(2);
        return buffer.getShort(pos);
    }

    public int readInt() {
        final int pos = read_index.getAndAdd(4);
        return buffer.getInt(pos);
    }

    public long readLong() {
        final int pos = read_index.getAndAdd(8);
        return buffer.getLong(pos);
    }

    public float readFloat() {
        final int pos = read_index.getAndAdd(4);
        return buffer.getFloat(pos);
    }

    public double readDouble() {
        final int pos = read_index.getAndAdd(8);
        return buffer.getDouble(pos);
    }

    public char readChar() {
        final int pos = read_index.getAndAdd(2);
        return buffer.getChar(pos);
    }

    public String readString() {
        final int len = readInt();
        final int pos = read_index.getAndAdd(len * 2);
        final StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(buffer.getChar(pos + i * 2));
        }
        return sb.toString();
    }


}
