# Convert NYC taxi pickup/dropoff locations to trajectory 
This is a sub-project done as preperation part to my research in supporting trajectory data. This project can support any dataset that has <id,time,startlocation,endlocation>. The full trajectory data is prepared based on Dijkstras shortest path algorithm. 

## Download data to process. 
1. Download road network from [TAREEG](http://www.tareeg.org/).
2. Download nyc taxi data from [nyc Gov](http://www.nyc.gov/html/tlc/html/about/trip_record_data.shtml). You can use the provided python script **download.py**

## Preprocess nyc taxi. 
1. use the python script to extract trajectory information **extractNYC.py**. 
2. Once the script finish your extracted pickup/dropoff locatoins will within the same directory of the data. 

## Generate road network graph from TAREEG dataset. 
1. Use class ReadMap that takes two file *node.txt* and *edge.txt*. The output is three files that are going to be used latter for computing the shortest path, namely *newnode.txt, newedge.txt, and graph.txt*.

## Compute the shortestpath for every trajectory data. 
1. Use the **MainNYC.java** class that takes the four args namely, *new_nodefile graphfile rowdataset outputtrajectoryfile*. The *new_nodefile* and *graphfile* are generated from the previous step. Meanwhile, the rowdataset is the one extracted from nyc original dataset. The output of this phase is the full trajectory path based on the Dijkstra's algorithm. 
