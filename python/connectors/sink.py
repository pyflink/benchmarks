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
from pyflink.java_gateway import get_gateway
from pyflink.table import TableSink
from pyflink.table.types import _to_java_type
from pyflink.util import utils


class PrintTableSink(TableSink):
    """
    A simple :class:`TableSink` to emit data as standard output.
    """

    def __init__(self, field_names, field_types):
        gateway = get_gateway()
        j_field_names = utils.to_jarray(gateway.jvm.String, field_names)
        j_field_types = utils.to_jarray(gateway.jvm.TypeInformation,
                                        [_to_java_type(field_type) for field_type in field_types])
        j_table_sink = gateway.jvm.com.alibaba.flink.sink.PrintTableSink(
            j_field_names, j_field_types)
        super(PrintTableSink, self).__init__(j_table_sink)
