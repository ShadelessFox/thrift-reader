package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class Statistics {
    @Field(id = 1, required = false)
    private byte[] max;

    @Field(id = 2, required = false)
    private byte[] min;

    @Field(id = 3, required = false)
    private long nullCount;

    @Field(id = 4, required = false)
    private long distinctCount;

    @Field(id = 5, required = false)
    private byte[] maxValue;

    @Field(id = 6, required = false)
    private byte[] minValue;

    @Field(id = 7, required = false)
    private boolean isMaxValueExact;

    @Field(id = 8, required = false)
    private boolean isMinValueExact;
}
