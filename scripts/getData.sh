#!/bin/bash 

source_url="ftp://ftp.ncdc.noaa.gov/pub/data/noaa/"; 
download_to="/opt/data/ncdc_data"; 

if [ ! -d "$download_to" ]; then 
    mkdir "$download_to"; 
fi 

wget -r -c --progress=bar --no-parent -P "$download_to" "$source_url";