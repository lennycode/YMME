<?php
 
  
$db = new PDO('mysql:host=localhost;dbname=grocery2_ymmbase;charset=utf8mb4',
 'grocery2', 'abc123ABC!@#');

 
$mode= htmlspecialchars($_GET["mode"]);
$mode = trim($mode);
api_controller($mode, $db);
    
function api_controller($action, $db){
   
    switch($action){
        case "years":
            years($db);
        break;
            
        case "makes":
            makes_by_year($db,trim($_GET["year"]));
        break;
        
        case "models":
            make_model_by_year($db,trim($_GET["year"]), trim($_GET["make"]) );
        break;
        
        case "engines":
            make_model_engine_by_year($db,trim($_GET["year"]), trim($_GET["make"]), trim($_GET["model"]));
        break;
            
    }
    
}    

    
function years($db){
    try{
        $stmt = $db->query("SELECT DISTINCT YEAR FROM YMME"); 
        $result = $stmt->setFetchMode(PDO::FETCH_ASSOC); 
        $years = [];
        foreach(($stmt->fetchAll()) as $k=>$v) { 
            array_push ($years, $v["YEAR"]); 
        }
        echo json_encode(["years" => $years, "count" => count($years)]);
    } catch  (Exception $e){
        print_r($e);
	     die();
    }  
}


function makes_by_year($db, $year){
     try{
          $startTime = microtime(true);
        $stmt = $db->prepare("SELECT DISTINCT `make` FROM YMME WHERE `year`= ?"); 
        $stmt->execute([$year]);
        $result = $stmt->setFetchMode(PDO::FETCH_ASSOC); 
        $makes = [];
        foreach(($stmt->fetchAll()) as $k=>$v) { 
            array_push($makes, $v["make"]);
        }
    echo json_encode(["makes" => $makes,  "year"=>$year,  "count" => count($makes), "time"=>number_format(( microtime(true) - $startTime), 4)]);
    } catch  (Exception $e){
        print_r($e);
        die();
    }  
}


function make_model_by_year($db, $year, $make){
    try{
        $startTime = microtime(true);
        $stmt = $db->prepare("SELECT DISTINCT `make`, `model` FROM YMME WHERE `year` =? AND `make` = ?"); 
        $stmt->execute([$year, $make]);
        $result = $stmt->setFetchMode(PDO::FETCH_ASSOC); 
        $models = [];
        foreach(($stmt->fetchAll()) as $k=>$v) { 
            array_push($models, $v["model"]);
        }
        echo json_encode(["models" => $models,   "make"=>$make ,  "year"=>$year,  "count" => count($models), "time"=>number_format(( microtime(true) - $startTime), 4)]);
    }

         catch  (Exception $e){
        print_r($e);
        die();
}  
}

function make_model_engine_by_year($db, $year, $make, $model){
    try{
        $startTime = microtime(true);
        $stmt = $db->prepare("SELECT DISTINCT `engine` FROM YMME WHERE `year`=? AND `make`=? AND `model` = ?"); 
        $stmt->execute([$year, $make, $model]);
        $result = $stmt->setFetchMode(PDO::FETCH_ASSOC); 
        $engines = [];
        foreach(($stmt->fetchAll()) as $k=>$v) { 
            array_push($engines, $v["engine"]);
        }
        echo json_encode(["engines" => $engines,  "make"=>$make , "model" => $model,"year"=>$year,   "count" => count($engines), "time"=>number_format(( microtime(true) - $startTime), 4)]);
    }
    catch  (Exception $e){
	   print_r($e);
	   die();
    }  
}
