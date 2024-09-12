package com.shade.thrift.serde;

import com.shade.thrift.data.ThriftValue;

public interface TypeAdapter<T> {
    T deserialize(ThriftDeserializer deserializer, ThriftValue value);
}
