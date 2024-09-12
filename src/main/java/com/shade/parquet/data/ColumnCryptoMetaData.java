package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class ColumnCryptoMetaData {
    @Field(id = 1, required = false)
    private EncryptionWithFooterKey encryptionWithFooterKey;

    @Field(id = 2, required = false)
    private EncryptionWithColumnKey encryptionWithColumnKey;
}
