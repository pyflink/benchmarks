/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.flink.sink;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.table.sinks.RetractStreamTableSink;
import org.apache.flink.table.sinks.TableSink;
import org.apache.flink.types.Row;

public class PrintTableSink implements RetractStreamTableSink<Row> {

    private String[] fieldNames;
    private TypeInformation<?>[] fieldTypes;

    public PrintTableSink(String[] fieldNames, TypeInformation<?>[] fieldTypes) {
        this.fieldNames = fieldNames;
        this.fieldTypes = fieldTypes;
    }

    @Override
    public TableSink<Tuple2<Boolean, Row>> configure(String[] fieldNames, TypeInformation<?>[] fieldTypes) {
        return new PrintTableSink(fieldNames, fieldTypes);
    }

    @Override
    public String[] getFieldNames() {
        return fieldNames;
    }

    @Override
    public TypeInformation<?>[] getFieldTypes() {
        return fieldTypes;
    }

    @Override
    public DataStreamSink<?> consumeDataStream(DataStream<Tuple2<Boolean, Row>> dataStream) {
        return dataStream.addSink(new PrintSinkFunction())
                .setParallelism(dataStream.getParallelism());
    }

    @Override
    public TypeInformation<Row> getRecordType() {
        return new RowTypeInfo(fieldTypes);
    }
}

