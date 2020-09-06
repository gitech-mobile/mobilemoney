#!/bin/sh
if wget http://discovery:9003/eureka  | grep -q 'eureka';
  then
    echo "Tests passed!"
    exit 0
  else
    echo "Tests failed!"
    exit 1
fi
