package com.shade.thrift.serde;

import java.lang.reflect.Type;

public interface TypeAdapterFactory {
    <T> TypeAdapter<T> create(Type type);
}
