package com.shade.parquet;

import com.shade.parquet.data.FileMetaData;
import com.shade.thrift.protocol.CompactThriftReader;
import com.shade.thrift.serde.ThriftDeserializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParquetReader {
    private static final int PARQUET_MAGIC = 'P' | 'A' << 8 | 'R' << 16 | '1' << 24;

    public static void main(String[] args) throws IOException {
        Path path = Path.of("C:\\Users\\ShadelessFox\\Downloads\\sample1.parquet");
        ByteBuffer buffer = ByteBuffer.wrap(Files.readAllBytes(path)).order(ByteOrder.LITTLE_ENDIAN);

        if (buffer.getInt(0) != PARQUET_MAGIC) {
            throw new IllegalArgumentException("Not a parquet file: invalid header magic");
        }
        if (buffer.getInt(buffer.limit() - 4) != PARQUET_MAGIC) {
            throw new IllegalArgumentException("Not a parquet file: invalid footer magic");
        }

        var metadataSize = buffer.getInt(buffer.limit() - 8);
        var metadataBuffer = buffer
            .slice(buffer.limit() - 8 - metadataSize, metadataSize)
            .order(ByteOrder.LITTLE_ENDIAN);

        var metadata = new ThriftDeserializer().deserialize(FileMetaData.class, new CompactThriftReader().readStruct(metadataBuffer));
    }
}
