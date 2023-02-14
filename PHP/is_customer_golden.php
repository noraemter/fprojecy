<?php
require_once '_database.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $sql = "SELECT isGoldenCustomer FROM customer WHERE user_id= :user_id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':user_id', $_POST['user_id']);
    $stmt->execute();
    $isGolden = $stmt->fetch();
    $response = array();
    if ($isGolden) {
        $response["golden_customer"]["isGolden"] = $isGolden['isGoldenCustomer'];
        echo json_encode($response);
    }

}
$pdo = null;