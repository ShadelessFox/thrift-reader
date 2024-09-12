package com.shade.parquet.data;

public enum Encoding {
    PLAIN,
    GROUP_VAR_INT,
    PLAIN_DICTIONARY,
    RLE,
    BIT_PACKED,
    DELTA_BINARY_PACKED,
    DELTA_LENGTH_BYTE_ARRAY,
    DELTA_BYTE_ARRAY,
    RLE_DICTIONARY,
    BYTE_STREAM_SPLIT
}
