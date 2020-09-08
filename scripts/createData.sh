#!/usr/bin/env bash

# Un-gzip each station file and concat into one file
year=1900
for ((i=1901;i<=1999;i+=1))
do
	year=$[$year + 1]
	for file in /opt/data/$year/*
	do
 		gunzip -c $file >> $year.txt
	done
done
