<?php
require_once '_database.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $response = array();
    $response["insert"]['error'] = true;
    $sql = 'INSERT into login_table (uname, passwd, login_type) VALUES(:user_name, sha1(:pass_word), :login_type)';
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':user_name', $_POST['user_name']);
    $stmt->bindValue(':pass_word', $_POST['pass_word']);
    $stmt->bindValue(':login_type', $_POST['employee_type']);

    $stmt->execute();
    $last_id = $pdo->lastInsertId();

    $sql = "INSERT INTO employee (employee_name, employee_salary,user_id,employee_type) VALUES( :employee_name, :employee_salary, :user_id , :employee_type)";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':employee_name', $_POST['employee_name']);
    $stmt->bindValue(':employee_salary', $_POST['employee_salary']);
    $stmt->bindValue(':employee_type', $_POST['employee_type']);
    $stmt->bindValue(':user_id', $last_id);

    $stmt->execute();
    $response["insert"]['error'] = false;
    echo json_encode($response);
    $pdo = null;

}