package com.shade.thrift.data;

import java.util.Map;

public record ThriftMap(Map<? extends ThriftValue, ? extends ThriftValue> values) implements ThriftValue {
    @Override
    public String toString() {
        return values.toString();
    }
}
