<?php

require_once '_database.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $response = array();
    $response["update"]['error'] = true;

    $sql = "UPDATE bill Set status = 'approve' WHERE bill.bill_id= :bill_id";

    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':bill_id', $_POST['bill_id']);

    $stmt->execute();
    $response["update"]['error'] = false;

    echo json_encode($response);
    $pdo = null;

}