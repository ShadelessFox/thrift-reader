package com.shade.thrift.protocol;

import com.shade.thrift.data.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CompactThriftReader implements ThriftReader {
    private static final int FT_BOOL_TRUE = 1;
    private static final int FT_BOOL_FALSE = 2;
    private static final int FT_I8 = 3;
    private static final int FT_I16 = 4;
    private static final int FT_I32 = 5;
    private static final int FT_I64 = 6;
    private static final int FT_DOUBLE = 7;
    private static final int FT_BINARY = 8;
    private static final int FT_LIST = 9;
    private static final int FT_SET = 10;
    private static final int FT_MAP = 11;
    private static final int FT_STRUCT = 12;
    private static final int FT_UUID = 13;

    @Override
    public ThriftStruct readStruct(ByteBuffer src) {
        List<ThriftStruct.Field> fields = new ArrayList<>();
        int currentFieldId = 0;

        while (true) {
            int header = src.get() & 0xff;
            if (header == 0) {
                // Encountered a stop field
                break;
            }

            int deltaId = header >>> 4;
            int type = header & 0xf;
            int id;

            if (deltaId != 0) {
                id = currentFieldId + deltaId;
            } else {
                id = readInt16(src) & 0xffff;
            }

            ThriftValue value = readValue(src, type);
            fields.add(new ThriftStruct.Field(id, value));

            currentFieldId = id;
        }

        return new ThriftStruct(fields);
    }

    @Override
    public ThriftList readList(ByteBuffer src) {
        int header = src.get() & 0xff;
        int size = header >>> 4;
        int type = header & 0xf;

        if (size == 15) {
            size = readInt32(src);
        }

        List<ThriftValue> elements = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            elements.add(readValue(src, type));
        }

        return new ThriftList(elements);
    }

    private ThriftMap readMap(ByteBuffer src) {
        throw new IllegalStateException();
    }

    private ThriftBinary readBinary(ByteBuffer src) {
        byte[] data = new byte[readInt32(src)];
        src.get(data);
        return new ThriftBinary(data);
    }

    private ThriftValue readValue(ByteBuffer src, int type) {
        return switch (type) {
            case FT_BOOL_TRUE -> ThriftBoolean.TRUE;
            case FT_BOOL_FALSE -> ThriftBoolean.FALSE;
            case FT_I8 -> new ThriftNumber(fromZigZag(readInt8(src)));
            case FT_I16 -> new ThriftNumber(fromZigZag(readInt16(src)));
            case FT_I32 -> new ThriftNumber(fromZigZag(readInt32(src)));
            case FT_I64 -> new ThriftNumber(fromZigZag(readInt64(src)));
            case FT_DOUBLE -> new ThriftNumber(Double.longBitsToDouble(readInt64(src)));
            case FT_BINARY -> readBinary(src);
            case FT_LIST, FT_SET -> readList(src);
            case FT_MAP -> readMap(src);
            case FT_STRUCT -> readStruct(src);
            case FT_UUID -> new ThriftUUID(new UUID(src.getLong(), src.getLong()));
            default -> throw new IllegalArgumentException("Unsupported value type: " + type);
        };
    }

    private static byte readInt8(ByteBuffer src) {
        return src.get();
    }

    private static short readInt16(ByteBuffer src) {
        long value = readInt64(src);
        if ((short) value != value) {
            throw new ArithmeticException("short overflow");
        }
        return (short) value;
    }

    private static int readInt32(ByteBuffer src) {
        return Math.toIntExact(readInt64(src));
    }

    private static long readInt64(ByteBuffer src) {
        int value = 0;
        int size = 0;

        while (true) {
            if (size > 9) {
                throw new IllegalStateException("VarInt is too big!");
            }

            byte b = src.get();
            value |= (b & 0x7f) << (7 * size);
            size += 1;

            if ((b & 0x80) == 0) {
                break;
            }
        }

        return value;
    }

    private static short fromZigZag(short n) {
        return (short) ((n >>> 1) ^ -(n & 1));
    }

    private static int fromZigZag(int n) {
        return (n >>> 1) ^ -(n & 1);
    }

    private static long fromZigZag(long n) {
        return (n >>> 1) ^ -(n & 1);
    }
}
