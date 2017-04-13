<?php

$response = array();

if (isset($_POST['id']) && isset($_POST['user'])) {
 
    $id = $_POST['id'];
    $user = $_POST['user'];
 
    require_once __DIR__ . '/DbConnect.php';
    $db = new DB_CONNECT();
    // mysql inserting a new row
    $n_user = $user."_id";
    $query = sprintf("SELECT * FROM appointment_request WHERE %s = %d order by request_date",mysql_real_escape_string($n_user), mysql_real_escape_string($id));
    $result = mysql_query($query);
    $row = mysql_fetch_array($result);
    if($row){
        $count = 0;
        $response["requests"] = array();
 
  do{
        // temp user array
        $request = array();
        $request["request_id"] = $row["request_id"];
        $request["faculty_id"]=$row["faculty_id"];
        $request["student_id"] = $row["student_id"];
        $request["request_date"] = $row["request_date"];
        $request["reason"] = $row["reason"];
        $request["urgent"] = $row["urgent"];
        $request["status"] = $row["status"];
        $request["details"] = $row["details"];
        $request["modify_request"] = $row["modify_request"];
        $request["isClose"] = $row["isClose"];
        $count = $count+1;
        // push single product into final response array
        array_push($response["requests"], $request);
    }   while ($row = mysql_fetch_array($result));
    // success
    $response["success"] = 1;
    $response["count"] = $count;
 
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