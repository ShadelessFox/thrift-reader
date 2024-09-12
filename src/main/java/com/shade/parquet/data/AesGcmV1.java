package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class AesGcmV1 {
    @Field(id = 1, required = false)
    private byte[] aadPrefix;

    @Field(id = 2, required = false)
    private byte[] aadFileUnique;

    @Field(id = 3, required = false)
    private boolean supplyAadPrefix;
}
