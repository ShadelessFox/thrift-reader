package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

import java.util.List;

public class RowGroup {
    @Field(id = 1, required = true)
    public List<ColumnChunk> columns;

    @Field(id = 2, required = true)
    public long totalByteSize;

    @Field(id = 3, required = true)
    public long numRows;

    @Field(id = 4, required = false)
    public List<SortingColumn> sortingColumns;

    @Field(id = 5, required = false)
    public long fileOffset;

    @Field(id = 6, required = false)
    public long compressedSize;

    @Field(id = 7, required = false)
    public short ordinal;
}
