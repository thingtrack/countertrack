#!/bin/bash -e

if [ ! -d /sys/class/gpio/gpio30 ]; then echo 30 > /sys/class/gpio/export; fi #P9_11
if [ ! -d /sys/class/gpio/gpio60 ]; then echo 60 > /sys/class/gpio/export; fi #P9_12
if [ ! -d /sys/class/gpio/gpio31 ]; then echo 31 > /sys/class/gpio/export; fi #P9_13
if [ ! -d /sys/class/gpio/gpio50 ]; then echo 50 > /sys/class/gpio/export; fi #P9_14
if [ ! -d /sys/class/gpio/gpio48 ]; then echo 48 > /sys/class/gpio/export; fi #P9_15
if [ ! -d /sys/class/gpio/gpio51 ]; then echo 51 > /sys/class/gpio/export; fi #P9_16
if [ ! -d /sys/class/gpio/gpio3 ]; then echo 3 > /sys/class/gpio/export; fi #P9_21
if [ ! -d /sys/class/gpio/gpio2 ]; then echo 2 > /sys/class/gpio/export; fi #P9_22
if [ ! -d /sys/class/gpio/gpio49 ]; then echo 49 > /sys/class/gpio/export; fi #P9_23
if [ ! -d /sys/class/gpio/gpio15 ]; then echo 15 > /sys/class/gpio/export; fi #P9_24

echo in > /sys/class/gpio/gpio30/direction
echo in > /sys/class/gpio/gpio60/direction
echo in > /sys/class/gpio/gpio31/direction
echo in > /sys/class/gpio/gpio50/direction
echo in > /sys/class/gpio/gpio48/direction
echo in > /sys/class/gpio/gpio51/direction
echo in > /sys/class/gpio/gpio3/direction
echo in > /sys/class/gpio/gpio2/direction
echo in > /sys/class/gpio/gpio49/direction
echo in > /sys/class/gpio/gpio15/direction




