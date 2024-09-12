package com.shade.thrift.data;

public sealed interface ThriftValue
    permits ThriftBinary, ThriftBoolean, ThriftList, ThriftMap, ThriftNumber, ThriftStruct, ThriftUUID {
}
