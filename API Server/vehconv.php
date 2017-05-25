<?php
try{
  
$db = new PDO('mysql:host=localhost;dbname=database;charset=utf8mb4',
 'user', 'password');
 
} catch  (Exception $e){
    //Only for this script, this can reveal pwds in some cases.
    echo "Problem";
	print_r($e);
	die();
}  
 

 $affected_rows = $db->exec("DROP TABLE IF EXISTS `YMME`;");

$create_table = <<<EOT
    CREATE TABLE `YMME` (
 `uid` int(11) NOT NULL AUTO_INCREMENT,
 `year` int(4) NOT NULL,
 `make` varchar(25) NOT NULL,
 `model` varchar(45) NOT NULL,
 `engine` varchar(55) NOT NULL,
 `active` tinyint(1) NOT NULL,
 PRIMARY KEY (`uid`),
 KEY `year` (`year`,`make`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1
EOT;
echo "Table Created...... <br/>";

$affected_rows = $db->exec($create_table);
    
foreach (glob("vehdata/*.text") as $filename) {
    
  echo "Accessing: ".$filename;
  echo "<br/>";
    $count  = 0;
$myfile = fopen( $filename , "r") or die("Unable to open file!");
	//echo fread($myfile,filesize("data/upchash.dat"));
	$la=[];
	 
	while( ($line  = fgets($myfile)) !== false){
	  
	  $la = explode(",", $line );
	  $la = array_map("strip", $la);
	 
	 try{
	   	$qx = "INSERT INTO YMME(`year`,`make`,`model`,`engine`)VALUES($la[0],'$la[1]','$la[2]','$la[3]');";
	  $affected_rows = $db->exec($qx);
         $count++;
	 
	} catch (Exception $e) {
			print_r($e);
		die;
	}

 }
    echo $count." rows inserted from ".$filename."<br/>";
fclose($myfile); 
} 


function strip($a){
    return trim($a);
}
