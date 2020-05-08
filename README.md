# benchmarks
This project is used to do PyFlink related benchmark test

## Quick Start

### Setup
1. python3
2. java 1.8
3. maven version >= 3.3.0

#### Install python3
Python version (3.5, 3.6 or 3.7) is required for PyFlink. Please run the following command to make sure that it meets the requirements:

```shell
$ python --version
# the version printed here must be 3.5, 3.6 or 3.7
```

#### Install java 8

[java download page](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

#### Install maven

maven version >=3.3.0

[download maven page](http://maven.apache.org/download.cgi)

```shell
$ tar -xvf apache-maven-3.6.1-bin.tar.gz
$ mv -rf apache-maven-3.6.1 /usr/local/
```
configuration environment variables
```shell
MAVEN_HOME=/usr/local/apache-maven-3.6.1
export MAVEN_HOME
export PATH=${PATH}:${MAVEN_HOME}/bin
```

### Install PyFlink

If you want to install PyFlink version 1.10, you can execute the following command to install the PyFlink:

```shell
$ python -m pip install apache-flink==1.10
```

If you want to install PyFlink version 1.11 which has not released, you need to build from the source code.
You can refer to [Build PyFlink](https://ci.apache.org/projects/flink/flink-docs-master/flinkDev/building.html#build-pyflink).

### Install PySpark

you need to download PySpark 3.0.0-preview2 from [download page](https://spark.apache.org/downloads.html).
Then you can execute the following command to install PySpark 3.0:
```shell
$ tar zxvf spark-3.0.0-preview2-bin-hadoop2.7.tgz
$ cd spark-3.0.0-preview2-bin-hadoop2.7/python
$ python setup.py sdist
$ pip install dist/pyspark-3.0.0.dev2.tar.gz
```

### Run Test

```shell
# Run PyFlink Python UDF Test
$ ./run_flink_test.sh

# Run PyFlink Pandas UDF Test
$ ./run_flink_pandas_test.sh

# Run PySpark Python UDF Test
$ ./run_spark_test.sh

# Run PySpark Pandas UDF Test
$ ./run_spark_pandas_test.sh

```
