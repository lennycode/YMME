# YMME
A database of year, make, model, engine for cars sold in US, along with an Android Viewer.

This project has a few parts:

An API Server folder: 

  -perl script to extract year, make, mode, and engine info for a given year. There are delays in the script and 
  it shouldn't be used continously. I may evemtually commit the actual CSV files.
  
      Example usage: `perl ymme_extraction_script.pl 1998`
      
  -php conversion utility to take the output from the above script and insert it into mysql with an appropriate index
  
      vehconv.php
  
  -an API endpoint to serve JSON
  
      http://??.com/vehendpoint.php?mode=makes&year=2013   - Fetches all makes for 2013 (See the API for more)
  
   
 Also, there is an Android app to view this data. The app uses Retrofit, EventBus, RecyclerView and Fragments. 
 It is a good starting point for apps using those componenets.
