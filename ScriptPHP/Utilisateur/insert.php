<?php

require_once('connect.php');
require_once __DIR__ . '/db_connect.php';

$username=$_GET['username'];
$email=$_GET['email'];
$password=$_GET['password'];
$prenom=$_GET['prenom'];
$nom=$_GET['nom'];
$roles=$_GET['roles'];
$image=$_GET['image'];

$sql = "INSERT INTO fos_user (username_canonical,username,email,email_canonical,enabled,password,prenom,nom,roles,image)
VALUES ('$username','$username','$email','$email','1','$password','$prenom','$nom','a:0:{}','$image')";

if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>