package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class SchemaElement {
    @Field(id = 1, required = false)
    private Type type;

    @Field(id = 2, required = false)
    private int typeLength;

    @Field(id = 3, required = false)
    private FieldRepetitionType repetitionType;

    @Field(id = 4, required = true)
    private String name;

    @Field(id = 5, required = false)
    private int numChildren;
}
