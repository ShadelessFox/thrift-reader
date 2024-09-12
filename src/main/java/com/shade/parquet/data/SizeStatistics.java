package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

import java.util.List;

public class SizeStatistics {
    @Field(id = 1, required = false)
    private long unencodedByteArrayDataBytes;

    @Field(id = 2, required = false)
    private List<Long> repetitionLevelHistogram;

    @Field(id = 3, required = false)
    private List<Long> definitionLevelHistogram;
}
