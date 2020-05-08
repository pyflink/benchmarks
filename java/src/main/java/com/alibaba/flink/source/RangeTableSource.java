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

package com.alibaba.flink.source;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.TableSchema;
import org.apache.flink.table.sources.StreamTableSource;
import org.apache.flink.table.types.DataType;
import org.apache.flink.types.Row;

public class RangeTableSource implements StreamTableSource<Row> {

    private final int start;
    private final int end;
    private final int step;

    public RangeTableSource(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public DataStream<Row> getDataStream(StreamExecutionEnvironment execEnv) {
        return execEnv.addSource(
                new RangeSourceFunction(start, end, step),
                new RowTypeInfo(new TypeInformation[]{BasicTypeInfo.INT_TYPE_INFO}, new String[]{"id"}));
    }

    @Override
    public TableSchema getTableSchema() {
        return new TableSchema.Builder().field("id", DataTypes.INT().notNull()).build();
    }

    @Override
    public DataType getProducedDataType() {
        return DataTypes.ROW(DataTypes.FIELD("id", DataTypes.INT().notNull()));
    }
}
