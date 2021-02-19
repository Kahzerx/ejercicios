/*
Un señor de internet dice que para mi versión lo mejor es crear otro user que tendrá derecho a sus apartados y así crear una nueva base de datos o "schema"
CREATE USER FCT IDENTIFIED BY root;
GRANT ALL PRIVILEGE TO FCT;
*/
ALTER SESSION SET current_schema = FCT;
/*
2.- Crea los tipos:
    t_cicloFormativo (Cod_ciclo, Nombre)
    t_empresa (CIF, Nombre)
    t_alumno (Dni, Nombre Ciclo CicloFormativo (es una referencia a CicloFormativo), EPractica (es una referencia a Empresa), Horas_diarias, int Dias_practicas() return number (método que devuelve los días que debe permanecer el alumno en la empresa en función de las horas que trabaja al día y sabiendo que las prácticas siempre son de 370 horas))
*/
CREATE OR REPLACE TYPE t_ciclo_formativo AS OBJECT (
							Cod_ciclo VARCHAR2(15),
							Num_telefono VARCHAR2(20));

CREATE OR REPLACE TYPE t_empresa AS OBJECT (
							CIF VARCHAR2(15),
							Nombre VARCHAR2(20));

CREATE OR REPLACE TYPE t_alumno AS OBJECT (
							Dni VARCHAR2(25),
							Nombre VARCHAR2(30),
							Ciclo_formativo REF t_ciclo_formativo,
							E_practica REF t_empresa,
							Horas_diarias NUMBER,
							MEMBER FUNCTION dias_practicas RETURN NUMBER);
/*
3.- Desarrolla el método Dias_practicas().
*/
/*CREATE OR REPLACE TYPE BODY t_alumno AS MEMBER FUNCTION dias_practicas RETURN NUMBER IS*/


