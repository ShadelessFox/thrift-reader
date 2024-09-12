package com.shade.parquet.data;

import com.shade.thrift.serde.Field;

public class PageEncodingStats {
    @Field(id = 1, required = true)
    private PageType pageType;

    @Field(id = 2, required = true)
    private Encoding encoding;

    @Field(id = 3, required = true)
    private int count;
}
