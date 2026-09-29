#!/usr/bin/env bash
# usage: ./run.sh NN-problem-name  [MainClass]
set -euo pipefail
DIR="${1:?usage: ./run.sh NN-problem-name [MainClass]}"
MAIN="${2:-Main}"
DIR="${DIR%/}"
OUT="out/$DIR"
rm -rf "$OUT"; mkdir -p "$OUT"
mapfile -t SRC < <(find "$DIR" -name '*.java')
[ "${#SRC[@]}" -gt 0 ] || { echo "no .java files in $DIR"; exit 1; }
javac -Xlint:all -d "$OUT" "${SRC[@]}"
echo "--- running $MAIN ---"
java -cp "$OUT" "$MAIN"
