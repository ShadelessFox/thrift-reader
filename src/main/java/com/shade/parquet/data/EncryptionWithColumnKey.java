package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

import java.util.List;

public class EncryptionWithColumnKey {
    @Field(id = 1, required = true)
    private List<String> pathInSchema;

    @Field(id = 2, required = false)
    private byte[] keyMetadata;
}
