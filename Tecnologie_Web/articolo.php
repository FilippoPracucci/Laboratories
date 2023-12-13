<?php

require_once("bootstrap.php");

$templateParams["titolo"] = "Blog TW - Articolo";
$templateParams["nome"] = "singolo-articolo.php";
$templateParams["articolicasuali"] = $dbh->getRandomPosts(2);
$templateParams["categorie"] = $dbh->getCategories();

$idarticolo = -1;
if (isset($_GET["id"])) {
    $idarticolo = $_GET["id"];
}
$templateParams["articolo"] = $dbh->getPostByID($idarticolo);

require("template/base.php");

?>