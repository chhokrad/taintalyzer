`mvn package` in **phosphor/Phosphor**
cd phosphor/Phosphor/target
java -jar Phosphor-0.0.4-SNAPSHOT.jar -controlTrack -multiTaint $JAVA_HOME/jre $JAVA_HOME/jre/ jre-inst-ctrl-multi
chmod +x jre-inst-ctrl-multi/bin/*
mvn package
