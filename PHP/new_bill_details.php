<?php
$response = array();
require_once '_database.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $response["bill_details"]["error"] = true;
    $sql = "INSERT into bill_details (bill_id, meal_id, quantity) VALUES( :b_id, :m_id, :quantity)";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':b_id', $_POST['b_id']);
    $stmt->bindValue(':m_id', $_POST['m_id']);
    $stmt->bindValue(':quantity', $_POST['quantity']);
    $stmt->execute();
    $response["bill_details"]["error"] = false;
    echo json_encode($response);
    $pdo = null;
}