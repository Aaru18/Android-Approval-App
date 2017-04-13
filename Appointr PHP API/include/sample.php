<?php

$response = array();

if (isset($_GET['email']) && isset($_GET['pass'])) {
 
    $email = $_GET['email'];
    $pass = $_GET['pass'];
    $ss = "student";
    require_once __DIR__ . '/DbConnect.php';
	$db = new DB_CONNECT();
    // mysql inserting a new row
    $table = "accounts_".$ss;
    $query = sprintf("SELECT * FROM %s WHERE email_id = '%s'", mysql_real_escape_string($table),mysql_real_escape_string($email));
    $result = mysql_query($query);
 	 $row = mysql_fetch_array($result);
     $response["success"] =$row["password"];
    echo json_encode($response);
   
    
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>