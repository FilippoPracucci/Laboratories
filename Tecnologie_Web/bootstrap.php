<?php
require_once("db/database.php");
$dbh = new DatabaseHelper("localhost", "route", "", "blogtw", 3306);
define("UPLOAD_DIR", "./upload/");
?>