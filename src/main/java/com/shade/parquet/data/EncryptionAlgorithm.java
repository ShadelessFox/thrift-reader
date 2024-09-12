package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class EncryptionAlgorithm {
    @Field(id = 1, required = false)
    private AesGcmV1 aesGcmV1;

    @Field(id = 2, required = false)
    private AesGcmCtrV1 aesGcmCtrV1;
}
