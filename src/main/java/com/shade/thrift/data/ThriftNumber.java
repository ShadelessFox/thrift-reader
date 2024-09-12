package com.shade.thrift.data;

public record ThriftNumber(Number value) implements ThriftValue {
    @Override
    public String toString() {
        return value.toString();
    }
}
