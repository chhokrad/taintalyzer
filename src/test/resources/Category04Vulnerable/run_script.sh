#!/bin/bash
# note -> uses netcat
# sends a password to the server
# usage:
# ./run_script.sh <password>
password=$1
pid=$2
echo $password | nc localhost 8000
echo "about to kill java process"
sleep 2
kill -9 $pid
