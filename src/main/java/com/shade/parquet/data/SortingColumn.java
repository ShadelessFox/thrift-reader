package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class SortingColumn {
    @Field(id = 1, required = true)
    private int index;

    @Field(id = 2, required = true)
    private boolean descending;

    @Field(id = 3, required = true)
    private boolean nullsFirst;
}
