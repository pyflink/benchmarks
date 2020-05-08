#!/usr/bin/env bash
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
CURRENT_DIR="$(cd "$( dirname "$0" )" && pwd)"

# compile java code
if [[ ! -f ${CURRENT_DIR}/java/target/flink-perf-tests-0.1.jar ]]; then
    pushd ${CURRENT_DIR}/java
    mvn clean install
    popd
fi

LIB_DIR=`python -c "import pyflink;import os;print(os.path.dirname(os.path.abspath(pyflink.__file__))+'/lib')"`
if [[ ! -f ${LIB_DIR}/flink-perf-tests-0.1.jar ]]; then
    # copy the jar file to the path site-packages/pyflink/lib of the used Python interpreter.
    cp ${CURRENT_DIR}/java/target/flink-perf-tests-0.1.jar ${LIB_DIR}
    ls -al ${LIB_DIR}
fi

export PYTHONPATH=${PYTHONPATH}:${CURRENT_DIR}
# run PyFlink pandas job
python ${CURRENT_DIR}/python/flink/flink-perf-pandas-test.py
