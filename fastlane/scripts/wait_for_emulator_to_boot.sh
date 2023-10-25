#!/usr/bin/env bash
sleep 10
echo "list of available devices : $($ANDROID_HOME/platform-tools/adb devices)"
for n in $($ANDROID_HOME/platform-tools/adb devices | egrep -o 'emulator-(\d+)')
do
  echo "Waiting for $n to boot up"
  $ANDROID_HOME/platform-tools/adb -s $n wait-for-device shell 'while [[ -z $(getprop sys.boot_completed | tr -d '\r') ]]; do sleep 1; done;'
  echo "$n started"
done