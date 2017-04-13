<?php

$response = array();

if (isset($_POST['id']) && isset($_POST['pass']) && isset($_POST['new_pass']) && isset($_POST['user'])) {
 
    $id = $_POST['id'];
    $pass = $_POST['pass'];
    $new_pass = $_POST['new_pass'];
    $user = $_POST['user'];
 
    require_once __DIR__ . '/DbConnect.php';
	$db = new DB_CONNECT();
    // mysql inserting a new row
    $table= "accounts_".$user;
    $query = sprintf("SELECT * FROM %s WHERE user_id = '%s'",mysql_real_escape_string($table),mysql_real_escape_string($id));
    $result = mysql_query($query);
 	$row = mysql_fetch_array($result);	
 	if($row){

 		$password = $row["password"];

 		if($password == $pass){
            $query2 = sprintf("UPDATE %s SET password = '%s' WHERE user_id = '%s'", mysql_real_escape_string($table), mysql_real_escape_string($new_pass), mysql_real_escape_string($id));
            $res = mysql_query($query2);
            if($res){
                $response["success"] = 1;
                $response["message"] = "Successfully Password Changed";

                echo json_encode($response);
            } else {
                $response["success"] = 0;
                $response["message"] = "Server Error. Try Again Later";

                echo json_encode($response);
            }
            
 		} else {
 			$response["success"] = 0;
    		$response["message"] = " Old Password is incorrect";

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