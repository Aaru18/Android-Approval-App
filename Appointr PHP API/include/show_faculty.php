<?php

$response = array();


    require_once __DIR__ . '/DbConnect.php';
    $db = new DB_CONNECT();
    // mysql inserting a new row

    $query = sprintf("SELECT * FROM accounts_faculty");
    $result = mysql_query($query);
    $row = mysql_fetch_array($result);
    if($row){

        $response["faculties"] = array();
        $count = 0;
    do {
        // temp user array
        $faculty = array();
        $faculty["user_id"] = $row["user_id"];
        $faculty["departments"]=$row["departments"];
        $faculty["faculty_name"] = $row["faculty_name"];
        $count = $count + 1;
        array_push($response["faculties"], $faculty);
    }while ($row = mysql_fetch_array($result));
    // success
    $response["success"] = 1;
    $response["message"] = "SuccessFull";
    $response["count"] = $count;
    // echoing JSON response
    echo json_encode($response);

    } else {
        $response["success"] = 0;
        $response["message"] = "No Request Found";

        echo json_encode($response);
    }
   
?>