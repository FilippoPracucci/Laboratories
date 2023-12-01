<?php

function getIDFromName($name) {
    return preg_replace("/[^a-z]/", '', strtolower($name));
}

?>