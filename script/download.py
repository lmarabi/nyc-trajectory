#!/bin/python  
# Author: Louai Alarabi. 
# This script download NYC taxi dataset. 
import sys 
import os

source = ['yellow_tripdata','green_tripdata']
years = [2009,2010,2011,2012,2013,2014,2015,2016,2017,2018]

os.system('find . -empty -delete')

for x in range(1,10):
    for s in source:
        for y in years:
            cmd =  'wget -N https://s3.amazonaws.com/nyc-tlc/trip+data/'+s+'_'+str(y)+'-0'+str(x)+'.csv'
            print cmd 
            os.system(cmd)


for x in range(10,13):
    for s in source:
        for y in years:
            cmd =  'wget -N https://s3.amazonaws.com/nyc-tlc/trip+data/'+s+'_'+str(y)+'-'+str(x)+'.csv'
            print cmd 
            os.system(cmd)
