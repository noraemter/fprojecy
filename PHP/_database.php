<?php
$host = '192.168.1.14';
$database_name = 'c578Resturant';
$user = 'root';
$passwd = '';

$connString = "mysql:host=$host;dbname=$database_name";

try{
    $pdo = new PDO($connString, $user, $passwd);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
}catch (\PDOException $exception){
    throw new PDOException($exception->getMessage(), (int)$exception->getCode());
}
