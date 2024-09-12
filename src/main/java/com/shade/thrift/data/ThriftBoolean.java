package com.shade.thrift.data;

public record ThriftBoolean(boolean value) implements ThriftValue {
    public static final ThriftBoolean TRUE = new ThriftBoolean(true);
    public static final ThriftBoolean FALSE = new ThriftBoolean(false);
}
