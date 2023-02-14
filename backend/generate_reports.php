<?php
require_once '_database.php';


$sql = 'SELECT SUM(b.quantity) as "sum_of_quantity" , m.meal_name as "meal_name" ,
       (( m.meal_selling_price - m.meal_cost_price)*SUM( b.quantity))AS "profit"
        From meal m ,bill_details b where b.meal_id = m.meal_id GROUP by (b.meal_id)';
$stmt = $pdo->prepare($sql);
$stmt->execute();
$response = array();
while ($meal = $stmt->fetch()) {
    $response[] = $meal;
}

echo json_encode($response);

$pdo = null;



