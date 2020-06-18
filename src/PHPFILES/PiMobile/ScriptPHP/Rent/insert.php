<?php

require_once('connect.php');
require_once __DIR__ . '/db_connect.php';
$currentDateTime = date('Y-m-d H:i:s');
$debuit=$_GET['debuit'];
$fin=$_GET['fin'];
$num=$_GET['num'];
date('%Y-%m-%d',$debuit);
strftime('%Y-%m-%d',$fin);

$sql = "INSERT INTO contract (date_debut_location,date_fin_location,date,phonenumber)
VALUES ('$debuit','$fin','$currentDateTime','$num')";

if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>