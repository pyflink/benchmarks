import time

from pyspark.shell import spark
from pyspark.sql.functions import col
from pyspark.sql.functions import pandas_udf, PandasUDFType

numRows = 1000000000
batchSize = 10000

spark.conf.set('spark.sql.execution.arrow.maxRecordsPerBatch', str(batchSize))
df = spark.range(1, numRows + 1, numPartitions=1).select(col('id').alias('a'))


@pandas_udf("int", PandasUDFType.SCALAR)
def pandas_inc(x):
    return x + 1


beg_time = time.time()
df = df.select(pandas_inc('a').alias('a'))
result = df.select('a').filter(df.a < 3).head()
print("PySpark Pandas UDF pandas_inc() consume time: " + str(time.time() - beg_time))
