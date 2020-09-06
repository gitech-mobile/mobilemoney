#!/bin/sh
if wget http://backoffice:80  && grep 'paiement' index.html ;
  then
    echo "Tests passed!"
    exit 0
  else
    echo "Tests failed!"
    exit 1
fi
