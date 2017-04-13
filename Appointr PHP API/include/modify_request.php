<?php

$response = array();

if (isset($_POST['id']) && isset($_POST['status']) && isset($_POST['modify'])) {
 
    $id = $_POST['id'];
    $status = $_POST['status'];
    $msg;
    if($status == "-1"){
        $msg = "Request Rejected";
    }elseif($status == "0"){
        $msg = "No Change";
    }elseif($status == "1"){
          $msg = "Request Accepted";
    }
    $modify = $_POST['modify'];
    if ($modify){
        $date = $_POST['date'];
    }     

    require_once __DIR__ . '/DbConnect.php';
    $db = new DB_CONNECT();
    $query;
    if($modify){
        $query = sprintf("UPDATE appointment_request SET request_date = %d, status = %d, modify_request = 1 WHERE request_id = %d", mysql_real_escape_string($date),mysql_real_escape_string($status),  mysql_real_escape_string($id));
    } else {
        $query = sprintf("UPDATE appointment_request SET status = %d WHERE request_id = %d", mysql_real_escape_string($status),mysql_real_escape_string($id));
    }
    $result = mysql_query($query);
    if($result){
    // success
    $response["success"] = 1;
    $response["message"] = $msg;
    // echoing JSON response
    echo json_encode($response);

    } else {
        $response["success"] = 0;
        $response["message"] = "Server Error";

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