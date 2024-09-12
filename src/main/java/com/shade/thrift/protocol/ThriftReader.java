package com.shade.thrift.protocol;

import com.shade.thrift.data.ThriftList;
import com.shade.thrift.data.ThriftStruct;

import java.nio.ByteBuffer;

public interface ThriftReader {
    ThriftStruct readStruct(ByteBuffer src);

    ThriftList readList(ByteBuffer src);
}
