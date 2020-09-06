#!/bin/sh
if wget http://localhost:9001  | grep -q '401';
  then
    echo "Tests passed!"
    exit 0
  else
    echo "Tests failed!"
    exit 1
fi
