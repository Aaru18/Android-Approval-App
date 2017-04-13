<?php

$response = array();

if (isset($_POST['faculty_id']) && isset($_POST['student_id']) && isset($_POST['date']) && isset($_POST['reason']) && isset($_POST['urgent'])) {
 
    $faculty_id = $_POST['faculty_id'];
    $student_id = $_POST['student_id'];
    $date = $_POST['date'];
    $reason = $_POST['reason'];
    $urgent = $_POST['urgent'];
 
    require_once __DIR__ . '/DbConnect.php';
    $db = new DB_CONNECT();
    // mysql inserting a new row
    $query = sprintf("INSERT INTO appointment_request(faculty_id, student_id, request_date, reason, urgent) VALUES(%d, %d, %d, '%s', %d)",mysql_real_escape_string($faculty_id), mysql_real_escape_string($student_id), mysql_real_escape_string($date),  mysql_real_escape_string($reason), mysql_real_escape_string($urgent));
    $result = mysql_query($query);
    if($result){
        $response["success"] = 1;
        $response["message"] = "Request successfully created.";
        echo json_encode($response);

    } else {
        $response["success"] = 0;
        $response["message"] = "Duplicate Request";

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