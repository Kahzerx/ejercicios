## Your Pills

### Instalación

Debes tener una base de datos creada en mysql llamada `yourpills`, posteriormente debes ejecutar el comando `php artisan migrate` para la creación de la tabla.

La base de datos tiene este formato: 
```SQL
CREATE TABLE `yourpills`.`medicamento`(
    `ID` INT NOT NULL AUTO_INCREMENT,
    `Nombre` VARCHAR(255) NOT NULL,
    `Cantidad` INT NOT NULL,
    `Dias` INT NOT NULL,
    `Franja_Horas` INT NOT NULL,
PRIMARY KEY (`ID`));
```

Puedes modificar la información relacionada con tu base de datos en el archivo `.env`.

Posteriormente ejecutar el comando `php artisan serve` y acceder a la ruta que aparece en la consola (por defecto 127.0.0.1:8000), para acceder al servidor.