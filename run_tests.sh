#!/bin/sh
if wget donne | grep -q '401';
  then
    echo "Tests passed!"
    exit 0
  else
    echo "Tests failed!"
    exit 1
fi
