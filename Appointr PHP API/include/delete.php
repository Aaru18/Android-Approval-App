<?php

$response = array();

if (isset($_POST['id']) ) {
 
    $id = $_POST['id'];
 
    require_once __DIR__ . '/DbConnect.php';
    $db = new DB_CONNECT();
    // mysql inserting a new row
    $n_user = $user."_id";
    $query = sprintf("UPDATE appointment_request SET isClose = %d WHERE request_id = %d",mysql_real_escape_string($id),mysql_real_escape_string($id));
    $result = mysql_query($query);
    if($result){
 
    $response["success"] = 1;
   $response["message"] = "Appointment Deleted";
 
    // echoing JSON response
    echo json_encode($response);

    } else {
        $response["success"] = 0;
        $response["message"] = "No Request Found";

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