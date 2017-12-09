# Taintalyzer [![Build Status](https://travis-ci.org/chhokrad/taintalyzer.svg?branch=master)](https://travis-ci.org/chhokrad/taintalyzer)

This project aims at finding data flow dependencies between user defined taint sources and loop variables in java byte code using Phosphor.

# Installation

- `git clone https://github.com/chhokrad/taintalyzer`
- `git submodule update --init --recursive`
- Install `gpg` and generate keys
  * `gpg --gen-key`
- Run `mvn verify`

