package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class ColumnChunk {
    @Field(id = 1, required = false)
    private String filePath;

    @Field(id = 2, required = true)
    private long fileOffset;

    @Field(id = 3, required = false)
    private ColumnMetaData metaData;

    @Field(id = 4, required = false)
    private long offsetIndexOffset;

    @Field(id = 5, required = false)
    private int offsetIndexLength;

    @Field(id = 6, required = false)
    private long columnIndexOffset;

    @Field(id = 7, required = false)
    private int columnIndexLength;

    @Field(id = 8, required = false)
    private ColumnCryptoMetaData cryptoMetadata;

    @Field(id = 9, required = false)
    private byte[] encryptedColumnMetadata;
}
