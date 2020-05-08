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

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.types.Row;

public class RangeSourceFunction implements SourceFunction<Row> {

    private static final long serialVersionUID = 1L;

    private boolean stopped = false;
    private Row reuseRow = new Row(1);

    private final int start;
    private final int end;
    private final int step;

    public RangeSourceFunction(int start, int end, int step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public void run(SourceContext<Row> sourceContext) {
        for (int id = start; id <= end && !stopped; id += step) {
            reuseRow.setField(0, id);
            sourceContext.collect(reuseRow);
        }
    }

    @Override
    public void cancel() {
        stopped = true;
    }
}
