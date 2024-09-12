package com.shade.thrift.data;

import java.util.List;

public record ThriftStruct(List<Field> fields) implements ThriftValue {
    public record Field(int id, ThriftValue value) {
    }
}
