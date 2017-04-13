    <?php

$response = array();


    require_once __DIR__ . '/DbConnect.php';
    $db = new DB_CONNECT();
    // mysql inserting a new row

    $query = sprintf("SELECT * FROM accounts_student");
    $result = mysql_query($query);
     $row = mysql_fetch_array($result);
    if($row){

        $response["students"] = array();
   $count = 0;
   do{
        // temp user array
        $student = array();
        $student["user_id"] = $row["user_id"];
        $student["student_name"]=$row["student_name"];
        $student["roll_no"] = $row["roll_no"];
        $student["email_id"] = $row["email_id"];
        $count = $count + 1;
        
        array_push($response["students"], $student);
    } while ($row = mysql_fetch_array($result)) ;
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