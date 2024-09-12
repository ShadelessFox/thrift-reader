package com.shade.thrift.data;

import java.util.List;

public record ThriftList(List<? extends ThriftValue> values) implements ThriftValue {
    @Override
    public String toString() {
        return values.toString();
    }
}
