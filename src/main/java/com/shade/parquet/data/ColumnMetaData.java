package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

import java.util.List;

public class ColumnMetaData {
    @Field(id = 1, required = true)
    private Type type;

    @Field(id = 2, required = true)
    private List<Encoding> encodings;

    @Field(id = 3, required = true)
    private List<String> pathInSchema;

    @Field(id = 4, required = true)
    private CompressionCodec codec;

    @Field(id = 5, required = true)
    private long numValues;

    @Field(id = 6, required = true)
    private long totalUncompressedSize;

    @Field(id = 7, required = true)
    private long totalCompressedSize;

    @Field(id = 8, required = false)
    private List<KeyValue> keyValueMetadata;

    @Field(id = 9, required = true)
    private long dataPageOffset;

    @Field(id = 10, required = false)
    private long indexPageOffset;

    @Field(id = 11, required = false)
    private long dictionaryPageOffset;

    @Field(id = 12, required = false)
    private Statistics statistics;

    @Field(id = 13, required = false)
    private List<PageEncodingStats> encodingStats;

    @Field(id = 14, required = false)
    private long bloomFilterOffset;

    @Field(id = 15, required = false)
    private int bloomFilterLength;

    @Field(id = 16, required = false)
    private SizeStatistics sizeStatistics;
}
