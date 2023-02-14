-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 13, 2023 at 10:38 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `c578Resturant`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `bill_id` int(11) NOT NULL,
  `orderAt` datetime NOT NULL,
  `customer_id` int(11) NOT NULL,
  `value_of_bill` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`bill_id`, `orderAt`, `customer_id`, `value_of_bill`, `status`) VALUES
(21, '2022-06-11 23:36:17', 8, 30, 'approve'),
(22, '2022-06-11 23:47:51', 8, 30, ''),
(23, '2022-06-11 23:55:39', 8, 51, ''),
(24, '2022-06-12 01:19:59', 8, 49, 'sent'),
(25, '2022-06-12 19:36:00', 8, 56, 'sent'),
(26, '2022-06-12 23:35:07', 9, 69, 'approve'),
(27, '2022-06-15 12:22:14', 8, 89, 'approve');

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

CREATE TABLE `bill_details` (
  `bill_id` int(11) NOT NULL,
  `meal_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill_details`
--

INSERT INTO `bill_details` (`bill_id`, `meal_id`, `quantity`) VALUES
(21, 5, 1),
(22, 5, 1),
(23, 5, 2),
(24, 2, 1),
(24, 5, 1),
(25, 5, 1),
(25, 6, 2),
(26, 1, 2),
(26, 6, 1),
(27, 1, 1),
(27, 5, 3);

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(150) NOT NULL,
  `category_image_path` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `category_image_path`) VALUES
(3, 'Shawarma', '/MobileProject/category_images/shawarma.png'),
(4, 'Pizza', '/MobileProject/category_images/pizza.png'),
(5, 'Burger', '/MobileProject/category_images/burger.png');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(200) NOT NULL,
  `user_id` int(11) NOT NULL,
  `isGoldenCustomer` int(11) DEFAULT NULL,
  `minTotoalToBeGolden` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `customer_name`, `user_id`, `isGoldenCustomer`, `minTotoalToBeGolden`) VALUES
(8, 'Mohammad', 1, 1, 234),
(9, 'Ameer', 17, NULL, 69);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL,
  `employee_name` varchar(200) NOT NULL,
  `employee_salary` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `employee_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `employee_name`, `employee_salary`, `user_id`, `employee_type`) VALUES
(8, 'Chef', 1200, 10, 3),
(9, 'TestEmployee', 2500, 18, 3);

-- --------------------------------------------------------

--
-- Table structure for table `login_table`
--

CREATE TABLE `login_table` (
  `id` int(11) NOT NULL,
  `uname` varchar(150) NOT NULL,
  `passwd` varchar(500) NOT NULL,
  `login_type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_table`
--

INSERT INTO `login_table` (`id`, `uname`, `passwd`, `login_type`) VALUES
(1, 'amer', 'ae47715dd93f82a6e2ad8dcf0de22bd0850d4dfe', 1),
(9, 'admin', 'f865b53623b121fd34ee5426c792e5c33af8c227', 2),
(10, 'chef', '30a864bee491a994b372a4949e87e4c1711c8a79', 3),
(16, 'employee2', '9adcb29710e807607b683f62e555c22dc5659713', 4),
(17, 'temp', 'd7683e52af93b105a44fcef5bd668a77fafd49f9', 1),
(18, 'emp', 'd253a477e7040f74c18f4722804d84582d242035', 3);

-- --------------------------------------------------------

--
-- Table structure for table `meal`
--

CREATE TABLE `meal` (
  `meal_id` int(11) NOT NULL,
  `meal_name` varchar(100) NOT NULL,
  `meal_category_id` int(11) NOT NULL,
  `meal_description` varchar(1000) NOT NULL,
  `meal_selling_price` int(11) NOT NULL,
  `meal_cost_price` int(11) NOT NULL,
  `meal_img_path` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `meal`
--

INSERT INTO `meal` (`meal_id`, `meal_name`, `meal_category_id`, `meal_description`, `meal_selling_price`, `meal_cost_price`, `meal_img_path`) VALUES
(1, 'Turkey Burgers', 5, 'It contains two pieces of meat and comes with the meal, french fries.', 22, 18, '/MobileProject/meals_images/turkey_burger.jpg'),
(2, 'Beef Burgers', 5, 'It contains one pieces of meat and comes with the meal, french fries.', 18, 15, '/MobileProject/meals_images/beef_burger.jpg'),
(3, 'Pepperoni Pizza', 4, 'Contains chicken and cheese with pepperoni sauce', 25, 20, '/MobileProject/meals_images/pepperoni_pizza.png'),
(4, 'Margherita Pizza', 4, 'Contains chicken margarita cheese with special sauce', 35, 26, '/MobileProject/meals_images/margherita_pizza.jpg'),
(5, 'Arabic Shawarma', 3, 'It comes cut up and comes with French fries and a variety of salads', 20, 15, '/MobileProject/meals_images/arabic_shawarma.jpg'),
(6, 'Shawarma', 3, 'Contains chicken and salad inside', 14, 11, '/MobileProject/meals_images/shawarma.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`bill_id`),
  ADD KEY `customer _id` (`customer_id`);

--
-- Indexes for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD PRIMARY KEY (`bill_id`,`meal_id`),
  ADD KEY `meal_id` (`meal_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customer_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employee_id`),
  ADD UNIQUE KEY `user_id` (`user_id`);

--
-- Indexes for table `login_table`
--
ALTER TABLE `login_table`
  ADD PRIMARY KEY (`id`) USING BTREE;

--
-- Indexes for table `meal`
--
ALTER TABLE `meal`
  ADD PRIMARY KEY (`meal_id`),
  ADD KEY `meal_category_id` (`meal_category_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `login_table`
--
ALTER TABLE `login_table`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `meal`
--
ALTER TABLE `meal`
  MODIFY `meal_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`);

--
-- Constraints for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD CONSTRAINT `bill_details_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`),
  ADD CONSTRAINT `bill_details_ibfk_2` FOREIGN KEY (`meal_id`) REFERENCES `meal` (`meal_id`);

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `login_table` (`id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `login_table` (`id`);

--
-- Constraints for table `meal`
--
ALTER TABLE `meal`
  ADD CONSTRAINT `meal_ibfk_1` FOREIGN KEY (`meal_category_id`) REFERENCES `category` (`category_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
