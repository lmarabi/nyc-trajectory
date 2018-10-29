#!/bin/python  
# Author: Louai Alarabi
import sys 
import os
import re

source = ['yellow_tripdata','green_tripdata']
years = [2009,2010,2011,2012,2013,2014,2015,2016]

# This script extract some information from the nyc-taxi dataset
# this script should be placed at the parent directory that contains two folder named
# yellow and green each contains its own dataset. 
# the output will be written in single file call output.txt inside each folder
# seperatly. 

# The output format extracted as follow:
# uniqueTripID , TripTime, pickup longitude, pickup lattitude, dropoff longitude, dropoff latitude

# This script should be placed in the parent directory which contains two foldoer yellow and green as following 
# --.
# --extractNYC.py
# --yellow
#         --yellow_tripdata_2009-01.csv
#         --yellow...
# --green
#         --green_tripdata.csv

# Regular expersion for the extracted data
dateFormat = "\d{4}[-]\d{2}[-]\d{2}\s\d{2}[:]\d{2}[:]\d{2}"
floatFormat = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"
decimalFormat = "[-+]?\d+"
patDate = re.compile(dateFormat)
patFloat = re.compile(floatFormat)
patDecimal = re.compile(decimalFormat)

#main program 
for s in source:
    print "processing "+ s
    directory = os.getcwd()+ '/' +s.split('_')[0]
    outfile = open(directory+"/output-pickup.txt",'w')
    for x in range(1,13):
        uniqueID = 1
        for y in years:
            if x < 10:
                filename = directory+'/'+s+'_'+str(y)+'-0'+str(x)+'.csv'
            else:
                filename = directory+'/'+s+'_'+str(y)+'-'+str(x)+'.csv'
            if os.path.isfile(filename):
                inputfile = open(filename,'r')
                for line in inputfile:
		    print "line: "+ line
                    out = line.split(',')
                    if (len(out) < 13):
                        continue
                    if filename.__contains__('yellow'):
			print "processing " + filename
                        if  ( (patDate.match(out[1])) and ( patFloat.match(out[5]) or patDecimal.match(out[5])) and ( patFloat.match(out[6]) or patDecimal.match(out[6])) ):
                            #csv = id, pickupdate/time + longitude + latitude + drop off lon + drop off lat
                            csv = 'yellowID_' + out[0] +'_'+ str(uniqueID) + ',' + out[1] + ',' + out[5] + ',' + out[6] + ',' + out[9] + ',' + out[10] + "\n"
                            uniqueID+=1
                            outfile.write(csv)
                        else:
                            print line + "does not match."
                    else:
			print "processing" + filename
                        if  ( (patDate.match(out[1])) and ( patFloat.match(out[5]) or patDecimal.match(out[5])) and ( patFloat.match(out[6]) or patDecimal.match(out[6])) ):
                            #csv = genereatedID +  pickupdate/time + pickup lon + pickup lat + dropoff lon+ dropoff lat 
                            csv = 'greebID_' + out[0] +'_'+ str(uniqueID) + ',' + out[1] + ',' + out[5] + ',' + out[6]+ ',' + out[7]+ ',' + out[8] +"\n"
                            uniqueID+=1
                            outfile.write(csv)
                        else:
                            print line + " doesn't match - "
                inputfile.close()
    outfile.close()
    print "program end"                                                          
