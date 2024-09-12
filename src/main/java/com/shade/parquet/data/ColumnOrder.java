package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class ColumnOrder {
    @Field(id = 1, required = true)
    TypeDefinedOrder typeDefinedOrder;
}
