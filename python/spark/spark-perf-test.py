#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import time

from pyspark.shell import spark
from pyspark.sql.functions import col, udf

numRows = 1000000000
batchSize = 10000

spark.conf.set('spark.sql.execution.arrow.maxRecordsPerBatch', str(batchSize))
df = spark.range(1, numRows + 1, numPartitions=1).select(col('id').alias('a'))


@udf("int")
def inc(x):
    return x + 1


beg_time = time.time()
df = df.select(inc('a').alias('a'))
result = df.select('a').filter(df.a < 3).head()
print("PySpark Python UDF inc() consume time: " + str(time.time() - beg_time))
