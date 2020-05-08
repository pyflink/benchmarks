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

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.types.Row;

public class PrintSinkFunction implements SinkFunction<Tuple2<Boolean, Row>> {

    private static final long serialVersionUID = 1L;

    private int i = 0;

    @Override
    public void invoke(Tuple2<Boolean, Row> value, Context context) {
        i++;
        if (i == 10000000) {
            System.out.println(value);
            i = 0;
        }
    }
}
