<?php
require_once '_database.php';
$sql = 'SELECT * FROM category';
$stmt = $pdo->prepare($sql);
$stmt->execute();
$arrayCategories = array();
while ($cat = $stmt->fetch()) {
    $arrayCategories[] = $cat;
}
echo json_encode($arrayCategories);


$pdo = null;