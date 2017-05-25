# YMME
A database of year, make, model, engine configuration for cars sold in US, along with an Android Viewer.

This project has a few parts:

An API Server folder: 

  -A gz archive of this information from 1980-2016. This expands into csv files representing a single model year. 
  2009 to the present contain everything that moves(including heavy trucks). Prior years are mostly passenger cars and trucks.
  an exclusion table could be easily created so you can filter on the makes you need.

  -perl script to extract year, make, mode, and engine info for a given year. This can be used to update the archive. There are delays in the script and it shouldn't be used continously. It is possible in the future that this script may not work due to changes in the datasource, but it was stable for many years.  
  
      Example usage: `perl ymme_extraction_script.pl 1998`
      
  -php conversion utility to take the output from the above script (or files) and insert it into mysql with an appropriate index
  
      vehconv.php
  
  -A light API endpoint to serve JSON
  
      http://??.com/vehendpoint.php?mode=makes&year=2013   - Fetches all makes for 2013 (See the API for more)
  
   
 Also, there is an Android app to view this data. The app uses Retrofit, EventBus, RecyclerView and Fragments. 
 It is a good starting point for apps using those componenets.
