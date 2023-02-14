<?php
require_once '_database.php';
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $response["bill"]["error"] = true;
    $response["update"]["error"] = true;
    $response["golden"]["error"] = true;
    $sql = "SELECT customer_id FROM customer WHERE user_id= :user_id";
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':user_id', $_POST['user_id']);
    $stmt->execute();
    $customer = $stmt->fetch();
    if ($customer) {
        // add new bill
        $response = array();
        $sql = 'INSERT into bill (orderAt, customer_id, value_of_bill, status) VALUES(now(), :custom_id, :val_of_bill, \'sent\' )';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':custom_id', $customer['customer_id']);
        $stmt->bindValue(':val_of_bill', $_POST['val_of_bill']);
        $stmt->execute();
        $bill_id = $pdo->lastInsertId();
        $response["bill"]["bill_id"] = $bill_id;
        $response["bill"]["error"] = false;

        //update total summation of customer purchases
        $sql = 'UPDATE customer SET minTotoalToBeGolden= minTotoalToBeGolden+ :val_of_bill WHERE customer_id= :custom_id';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':val_of_bill', $_POST['val_of_bill']);
        $stmt->bindValue(':custom_id', $customer['customer_id']);
        $stmt->execute();
        $response["update"]["error"] = false;

        //update customer to be golden
        $sql = 'UPDATE customer SET isGoldenCustomer= 1 WHERE customer_id= :custom_id AND minTotoalToBeGolden>200';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':custom_id', $customer['customer_id']);
        $stmt->execute();
        $response["golden"]["error"] = false;
        echo json_encode($response);
    }

}
$pdo = null;
