<?php

require_once '_database.php';
if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    $sql = "SELECT b.bill_id , m.meal_name , bd.quantity
    FROM meal m , bill_details bd , bill b 
    where 
    b.bill_id = bd.bill_id
    and bd.meal_id = m.meal_id
    and b.status = 'sent' ";

    $stmt = $pdo->prepare($sql);
    $stmt->execute();
    $response = array();

    while ($order = $stmt->fetch())
        $response[$order['bill_id']][] = $order;

    echo json_encode($response);
    $pdo = null;

}


