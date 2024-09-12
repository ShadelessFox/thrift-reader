package com.shade.thrift.data;

import java.util.UUID;

public record ThriftUUID(UUID value) implements ThriftValue {
    @Override
    public String toString() {
        return value.toString();
    }
}
