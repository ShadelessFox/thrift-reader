package com.shade.thrift.data;

import java.util.HexFormat;

public record ThriftBinary(byte[] data) implements ThriftValue {
    @Override
    public String toString() {
        return HexFormat.of().formatHex(data);
    }
}
