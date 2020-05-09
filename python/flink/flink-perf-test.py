################################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
################################################################################
import logging
import sys
import time

from pyflink.datastream import StreamExecutionEnvironment
from pyflink.table import StreamTableEnvironment, EnvironmentSettings
from pyflink.table.types import DataTypes
from pyflink.table.udf import udf

from python.connectors.sink import PrintTableSink
from python.connectors.source import RangeTableSource


def main():
    env = StreamExecutionEnvironment.get_execution_environment()
    env.set_parallelism(1)
    environment_settings = EnvironmentSettings.new_instance().use_blink_planner().build()
    t_env = StreamTableEnvironment.create(env, environment_settings=environment_settings)

    t_env.get_config().get_configuration().set_integer("python.fn-execution.bundle.size", 300000)
    t_env.get_config().get_configuration().set_integer("python.fn-execution.bundle.time", 1000)
    t_env.get_config().get_configuration().set_boolean("pipeline.object-reuse", True)

    t_env.register_table_sink(
        "sink",
        PrintTableSink(
            ["id"],
            [DataTypes.INT(False)]))

    @udf(input_types=[DataTypes.INT(False)], result_type=DataTypes.INT(False))
    def inc(x):
        return x + 1

    t_env.register_function("inc", inc)
    t_env.register_java_function("java_inc", "com.alibaba.flink.function.JavaInc")

    num_rows = 100000000
    t_env.from_table_source(RangeTableSource(1, num_rows, 1)).alias("id") \
        .select("inc(id)") \
        .insert_into("sink")

    beg_time = time.time()
    t_env.execute("Python UDF")
    print("PyFlink Python UDF inc() consume time: " + str(time.time() - beg_time))


if __name__ == '__main__':
    logging.basicConfig(stream=sys.stdout, level=logging.INFO, format="%(message)s")

    main()
