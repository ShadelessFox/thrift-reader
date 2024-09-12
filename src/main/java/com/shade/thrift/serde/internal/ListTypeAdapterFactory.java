package com.shade.thrift.serde.internal;

import com.shade.thrift.data.ThriftValue;
import com.shade.thrift.serde.ThriftDeserializer;
import com.shade.thrift.serde.TypeAdapter;
import com.shade.thrift.serde.TypeAdapterFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Type type) {
        Class<?> rawType;
        if (type instanceof Class<?> cls) {
            rawType = cls;
        } else if (type instanceof ParameterizedType p) {
            rawType = (Class<?>) p.getRawType();
        } else {
            return null;
        }

        if (!List.class.isAssignableFrom(rawType)) {
            return null;
        }

        return null;
    }

    private static class Adapter<E> implements TypeAdapter<List<E>> {
        @Override
        public List<E> deserialize(ThriftDeserializer deserializer, ThriftValue value) {
            return List.of();
        }
    }
}
