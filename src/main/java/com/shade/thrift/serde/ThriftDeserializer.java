package com.shade.thrift.serde;

import com.shade.thrift.data.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ThriftDeserializer {
    public Object deserialize(Type type, ThriftValue value) {
        if (type instanceof ParameterizedType generic && generic.getRawType() instanceof Class<?> cls) {
            if (cls.isAssignableFrom(List.class)) {
                return deserializeList(generic.getActualTypeArguments()[0], value);
            }
        } else if (type instanceof Class<?> cls) {
            if (cls.isEnum()) {
                return deserializeEnum(cls, value);
            } else if (cls.isPrimitive() || isBoxedPrimitive(cls)) {
                return deserializePrimitive(cls, value);
            } else if (cls.isAssignableFrom(String.class)) {
                return deserializeString(cls, value);
            } else if (cls.isAssignableFrom(byte[].class)) {
                return deserializeBinary(cls, value);
            } else {
                return deserializeClass(cls, value);
            }
        }
        throw error(type, value, "unsupported type");
    }

    private Object deserializePrimitive(Class<?> cls, ThriftValue value) {
        if (cls == boolean.class || cls == Boolean.class) {
            if (value instanceof ThriftBoolean bool) {
                return bool.value();
            }
            throw error(cls, value, "value is not a boolean");
        }
        if (!(value instanceof ThriftNumber number)) {
            throw error(cls, value, "value is not a number");
        }
        if (cls == byte.class || cls == Byte.class) {
            return number.value().byteValue();
        } else if (cls == short.class || cls == Short.class) {
            return number.value().shortValue();
        } else if (cls == int.class || cls == Integer.class) {
            return number.value().intValue();
        } else if (cls == long.class || cls == Long.class) {
            return number.value().longValue();
        } else if (cls == double.class || cls == Double.class) {
            return number.value().doubleValue();
        }
        throw error(cls, value, "unsupported primitive type");
    }

    private byte[] deserializeBinary(Type type, ThriftValue value) {
        if (!(value instanceof ThriftBinary binary)) {
            throw error(type, value, "value is not a binary");
        }
        return binary.data();
    }

    private String deserializeString(Type type, ThriftValue value) {
        if (!(value instanceof ThriftBinary binary)) {
            throw error(type, value, "value is not a binary");
        }
        return new String(binary.data(), StandardCharsets.UTF_8);
    }

    private List<Object> deserializeList(Type type, ThriftValue value) {
        if (!(value instanceof ThriftList list)) {
            throw error(type, value, "value is not a list");
        }
        List<Object> elements = new ArrayList<>(list.values().size());
        for (ThriftValue element : list.values()) {
            elements.add(deserialize(type, element));
        }
        return elements;
    }

    private <T> T deserializeEnum(Class<T> cls, ThriftValue value) {
        if (!(value instanceof ThriftNumber number) || !(number.value() instanceof Integer ordinal)) {
            throw error(cls, value, "value is not a number");
        }
        return cls.getEnumConstants()[ordinal];
    }

    private <T> T deserializeClass(Class<T> cls, ThriftValue value) {
        if (!(value instanceof ThriftStruct struct)) {
            throw error(cls, value, "value is not a struct");
        }

        Map<Integer, ThriftValue> values = struct.fields().stream()
            .collect(Collectors.toMap(
                ThriftStruct.Field::id,
                ThriftStruct.Field::value
            ));

        T instance;

        try {
            instance = cls.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw error(cls, value, "can't instantiate class", e);
        }

        for (var field : cls.getDeclaredFields()) {
            Field fieldInfo = field.getDeclaredAnnotation(Field.class);
            if (fieldInfo == null) {
                continue;
            }

            ThriftValue fieldValue = values.get(fieldInfo.id());
            if (fieldValue == null) {
                if (fieldInfo.required()) {
                    throw error(
                        cls,
                        value,
                        "field with id %d '%s' is marked as required but the value is missing".formatted(fieldInfo.id(), field.getName())
                    );
                }
                continue;
            }

            if (!field.canAccess(instance)) {
                field.setAccessible(true);
            }

            try {
                field.set(instance, deserialize(field.getGenericType(), fieldValue));
            } catch (IllegalAccessException e) {
                throw error(cls, value, "can't assign value to field with id %d '%s'".formatted(fieldInfo.id(), field.getName()), e);
            }
        }

        return instance;
    }

    private static boolean isBoxedPrimitive(Class<?> cls) {
        return cls == Byte.class
            || cls == Short.class
            || cls == Integer.class
            || cls == Long.class
            || cls == Double.class
            || cls == Boolean.class;
    }

    private static IllegalStateException error(Type type, ThriftValue value, String reason) {
        return error(type, value, reason, null);
    }

    private static IllegalStateException error(Type type, ThriftValue value, String reason, Exception cause) {
        return new IllegalStateException("Could not deserialize " + value.getClass() + " into " + type + ": " + reason, cause);
    }
}
