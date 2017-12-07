if [ -z "$JAVA_HOME" ]
  then
  echo "Set JAVA_HOME"
  exit
fi

export TAINT_HOME=$(pwd)
target=$TAINT_HOME/phosphor/Phosphor/target

create_phosphor_jar() {
  echo "Creating Phosphor for $1"
  cd phosphor/Phosphor
  echo "echo \"We skip phosphor's own tests\"" > instrumentJRE.sh
  mvn clean install -DskipTests -DskipITs
  cd ../..
  git submodule status > $target/phosphor_hash.txt
}

if [ ! -f $target/Phosphor-0.0.4-SNAPSHOT.jar ]
  then
  create_phosphor_jar "No Phosphor jar"
fi

if [ ! -f $target/phosphor_hash.txt ]
  then
  create_phosphor_jar "No Phosphor Hash"
fi

hash=$(<$target/phosphor_hash.txt)
current_hash=`git submodule status`
if [ "$hash" != "$current_hash" ]
  then
  create_phosphor_jar "Phosphor hash does not match"
fi

if [ ! -d $target/jre-inst-ctrl-multi ]
  then
  echo "Creating jre-inst-ctrl-multi"
  java -jar $target/Phosphor-0.0.4-SNAPSHOT.jar -controlTrack -multiTaint $JAVA_HOME/jre $target/jre-inst-ctrl-multi
  chmod +x $target/jre-inst-ctrl-multi/bin/*
fi
