<?php
require_once '_database.php';


$meal_query = "SELECT meal_id, meal_name, meal_description, 
       meal_selling_price, meal_img_path FROM meal WHERE meal_category_id= :category_id";
$stmt = $pdo->prepare($meal_query);
$stmt->bindValue(':category_id', isset($_GET['category_id']) ? $_GET['category_id'] : '');
$stmt->execute();
$arrayMeals = array();
while ($meal = $stmt->fetch()) {
    $arrayMeals[] = $meal;
}

echo json_encode($arrayMeals);


$pdo = null;
