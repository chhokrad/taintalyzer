# Taintalyzer

This project aims at finding data flow dependencies between user defined taint sources and loop variables in java byte code using Phosphor.

# Installation

- `git clone https://github.com/chhokrad/taintalyzer`
- `git submodule update --init --recursive`
- Install `gpg` and generate keys
  * `gpg --gen-key`
- Run `mvn install` in **phosphor/Phosphor**


- Set $JAVA_HOME
   On Mac:
   - `export JAVA_HOME=$(/usr/libexec/java_home)`
