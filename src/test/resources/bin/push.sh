#!/bin/sh
# example after party shell program
echo "$1 payload..."
while read -r; do
  printf "%s\n" "$REPLY"
done

exit 1;
