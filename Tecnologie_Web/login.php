<?php

require_once("bootstrap.php");

if(isset($_POST["username"]) && isset($_POST["password"])) {
    $login_result = $dbh->checkLogin($_POST["username"], $_POST["password"]);
    if(count($login_result) == 0) {
        //login failed
        $templateParams["errorelogin"] = "Errore! Verificare username e/o password";
    } else {
        registerLoggedUser($login_result[0]);
    }
}

if(isUserLoggedIn()) {
    $templateParams["titolo"] = "Blog TW - Admin";
    $templateParams["nome"] = "login-home.php";
    $templateParams["articoli"] = $dbh->getPostByAuthorID($_SESSION["idautore"]);
    if(isset($_GET["formmsg"])) {
        $templateParams["formmsg"] = $_GET["formmsg"];
    }
}
else {
    $templateParams["titolo"] = "Blog TW - Login";
    $templateParams["nome"] = "login-form.php";
}

$templateParams["categorie"] = $dbh->getCategories();
$templateParams["articolicasuali"] = $dbh->getRandomPosts(2);

require("template/base.php");

?>