<?php
require_once '_database.php';
//http_response_code(500);
if($_SERVER['REQUEST_METHOD']=='POST'){
    $sql = "SELECT id, login_type FROM login_table WHERE uname= :user_name AND passwd= sha1(:pass_word)";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue('user_name', isset($_POST['user_name']) ? $_POST['user_name'] : '');
    $stmt->bindValue('pass_word', isset($_POST['pass_word']) ? $_POST['pass_word'] : '');
    $stmt->execute();
    $user = $stmt->fetch();
    $response= array();
    if($user){
        $response["user"]=$user;
        header('Content-Type: application/json; charset=utf-8');
        echo json_encode($response);
    }
}

$pdo = null;