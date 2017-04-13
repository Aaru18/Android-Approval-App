<?php

$response = array();

if (isset($_POST['email']) && isset($_POST['pass'])) {
 
    $email = $_POST['email'];
    $pass = $_POST['pass'];
 
    require_once __DIR__ . '/DbConnect.php';
	$db = new DB_CONNECT();
    // mysql inserting a new row
    $query = sprintf("SELECT * FROM accounts_faculty WHERE email_id = '%s'",mysql_real_escape_string($email));
    $result = mysql_query($query);
    	$row = mysql_fetch_array($result);	
 	if($row){
 

 		$password = $row["password"];

 		if($password == $pass){

 			$response["success"] = 1;
    		$response["message"] = "Successfully User Verified";
            $response["id"] = $row["user_id"];

    		echo json_encode($response);

 		} else {
 			$response["success"] = 0;
    		$response["message"] = "Password is incorrect";
    		echo json_encode($response);
 		}

 	} else {
 		$response["success"] = 0;
    	$response["message"] = "No such user exists";

    	echo json_encode($response);
 	}
   
    
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>