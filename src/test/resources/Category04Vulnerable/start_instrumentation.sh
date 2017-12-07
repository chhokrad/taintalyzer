#!/bin/bash
rm -rf src Archive.zip
fileName=$(find *.java)
mkdir -p src/e1e4
cp $fileName src/e1e4
zip -r Archive.zip src
APP_SOURCE=$(pwd)/Archive.zip
IFS='.'
read -r var1 var2 <<< "$fileName"
APP_ENTRY="e1e4.${var1}"
APP_NAME="${var1}"

java -jar "$TAINT_HOME/target/taintalyzer-0.0.1-SNAPSHOT-jar-with-dependencies.jar" \
 -s "$APP_SOURCE" -e "$APP_ENTRY" -n "$APP_NAME"
rm -rf src
rm Archive.zip
cd generated-code/src-instrumented
mvn clean package
cd target

$TAINT_HOME/phosphor/Phosphor/target/jre-inst-ctrl-multi/bin/java \
-Xbootclasspath/a:$TAINT_HOME/phosphor/Phosphor/target/Phosphor-0.0.4-SNAPSHOT.jar \
-javaagent:$TAINT_HOME/phosphor/Phosphor/target/Phosphor-0.0.4-SNAPSHOT.jar \
-Xint \
-jar "${var1}-0.0.1-jar-with-dependencies.jar" & java_pid=($!)
echo "java_pid is : $java_pid"
cd ../../../
sleep 15
exec ./run_script.sh "mypassword" $java_pid
