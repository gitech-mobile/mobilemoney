#!/bin/sh
if wget http://backoffice:9080  | grep -q 'paiement';
  then
    echo "Tests passed!"
    exit 0
  else
    echo "Tests failed!"
    exit 1
fi
