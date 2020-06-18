<?php

require_once('connect.php');
require_once __DIR__ . '/db_connect.php';
$currentDateTime = date('Y-m-d H:i:s');
$username=$_GET['userid'];
$email=$_GET['produitid'];
$password=$_GET['quantite'];
$prenom=$_GET['prix'];
$nom=$_GET['date_p'];
$sql = "INSERT INTO panier (user_id,produit_id,quantite,prix,date_p)
VALUES ('$username','$email','$password','$prenom','$currentDateTime')";

if (mysqli_query($conn, $sql)) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($conn);
?>