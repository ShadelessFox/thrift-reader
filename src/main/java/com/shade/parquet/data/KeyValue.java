package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class KeyValue {
    @Field(id = 1, required = true)
    private String key;

    @Field(id = 2, required = false)
    private String value;
}
