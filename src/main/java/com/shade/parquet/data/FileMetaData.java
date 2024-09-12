package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

import java.util.List;

public class FileMetaData {
    @Field(id = 1, required = true)
    private int version;

    @Field(id = 2, required = true)
    private List<SchemaElement> schema;

    @Field(id = 3, required = true)
    private long numRows;

    @Field(id = 4, required = true)
    private List<RowGroup> rowGroups;

    @Field(id = 5, required = false)
    private List<KeyValue> keyValueMetadata;

    @Field(id = 6, required = false)
    private String createdBy;

    @Field(id = 7, required = false)
    private List<ColumnOrder> columnOrders;

    @Field(id = 8, required = false)
    private EncryptionAlgorithm encryptionAlgorithm;

    @Field(id = 9, required = false)
    private byte[] footerSigningKeyMetadata;
}
