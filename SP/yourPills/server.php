<?php

/**
 * Laravel - A PHP Framework For Web Artisans
 *
 * @package  Laravel
 * @author   Taylor Otwell <taylor@laravel.com>
 */

// La base de datos

// CREATE TABLE `yourpills`.`medicamento`(
//     `ID` INT NOT NULL AUTO_INCREMENT,
//     `Nombre` VARCHAR(45) NOT NULL,
//     `Cantidad` INT NOT NULL,
//     `Dias` INT NOT NULL,
//     `Franja_Horas` INT NOT NULL,
// PRIMARY KEY (`ID`));


// Para crear la tabla escribir php artisan make:migration medicamento y luego php artisan migrate


$uri = urldecode(
    parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH)
);

// This file allows us to emulate Apache's "mod_rewrite" functionality from the
// built-in PHP web server. This provides a convenient way to test a Laravel
// application without having installed a "real" web server software here.
if ($uri !== '/' && file_exists(__DIR__.'/public'.$uri)) {
    return false;
}

require_once __DIR__.'/public/index.php';

