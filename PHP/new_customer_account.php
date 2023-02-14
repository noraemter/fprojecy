<?php
require_once '_database.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $response = array();
    $response["insert"]['error'] = true;
    $response["insert"]['message'] = "Error for new account";
    $sql = 'INSERT into login_table (uname, passwd, login_type) VALUES(:user_name, :pass_word, 1)';
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':user_name', $_POST['user_name']);
    $stmt->bindValue(':pass_word', sha1($_POST['pass_word']));
    $stmt->execute();
    $last_id = $pdo->lastInsertId();
    $sql = "INSERT INTO customer (customer_name, user_id) VALUES(:customer_name, :user_id)";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':customer_name', $_POST['customer_name']);
    $stmt->bindValue(':user_id', $last_id);
    $stmt->execute();
    $response["insert"]['error'] = false;
    $response["insert"]['message'] = "New account was created successfully";
    echo json_encode($response);
    $pdo = null;
}

