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
    t_alumno (Dni, Nombre Ciclo CicloFormativo (es una referencia a CicloFormativo),
    EPractica (es una referencia a Empresa), Horas_diarias,
    Dias_practicas() return number (método que devuelve los días que debe permanecer
    el alumno en la empresa en función de las horas que trabaja al día y sabiendo que las prácticas siempre son de 370 horas))
*/
CREATE OR REPLACE TYPE t_ciclo_formativo AS OBJECT (
    cod_ciclo VARCHAR2(15),
	nombre VARCHAR2(20));

CREATE OR REPLACE TYPE t_empresa AS OBJECT (
	cif VARCHAR2(15),
	nombre VARCHAR2(20));

CREATE OR REPLACE TYPE t_alumno AS OBJECT (
	dni VARCHAR2(25),
	nombre VARCHAR2(30),
	ciclo_formativo REF t_ciclo_formativo,
	e_practica REF t_empresa,
	horas_diarias NUMBER,
	MEMBER FUNCTION dias_practicas RETURN NUMBER);
/*
3.- Desarrolla el método Dias_practicas().
*/
CREATE OR REPLACE TYPE BODY t_alumno AS
	MEMBER FUNCTION dias_practicas RETURN NUMBER IS
		days NUMBER;
	BEGIN
		days:=370 / horas_diarias;
		RETURN days;
	END;
END;

/*4.- Crea tres tablas (CicloFormativo, Empresa y Alumno) de los objetos creados anteriormente e inserta 3 registros por tabla.
 * Hay que tener en cuenta que en la tabla t_alumno, el campo Ciclo coge los valores de la tabla CicloFormativo
 * y que el campo EPractica lo coge de la tabla Empresa.*/

CREATE TABLE ciclo_formativo OF t_ciclo_formativo;
CREATE TABLE empresa OF t_empresa;
CREATE TABLE alumno OF t_alumno;


INSERT INTO ciclo_formativo VALUES(
    '121212A',
    'NombreCiclo1');
INSERT INTO ciclo_formativo VALUES(
    '131313B',
    'NombreCiclo2');
INSERT INTO ciclo_formativo VALUES(
    '141414C',
    'NombreCiclo3');

INSERT INTO empresa VALUES(
    '212121A',
    'NombreEmpresa1');
INSERT INTO empresa VALUES(
    '232323B',
    'NombreEmpresa2');
INSERT INTO empresa VALUES(
    '242424C',
    'NombreEmpresa3');

INSERT INTO alumno VALUES(
    '313131A',
    'NombreAlumno1',
    (SELECT REF(cf) FROM ciclo_formativo cf WHERE cf.Cod_ciclo='121212A'),
    (SELECT REF(e) FROM empresa e WHERE e.CIF='212121A'),
    5);
INSERT INTO alumno VALUES(
    '323232B',
    'NombreAlumno2',
    (SELECT REF(cf) FROM ciclo_formativo cf WHERE cf.Cod_ciclo='131313B'),
    (SELECT REF(e) FROM empresa e WHERE e.CIF='232323B'),
    6);
INSERT INTO alumno VALUES(
    '343434C',
    'NombreAlumno3',
    (SELECT REF(cf) FROM ciclo_formativo cf WHERE cf.Cod_ciclo='141414C'),
    (SELECT REF(e) FROM empresa e WHERE e.CIF='242424C'),
    4);

/*SELECT a.dni, a.nombre, DEREF(a.ciclo_formativo), DEREF(a.e_practica), a.horas_diarias FROM alumno a;*/


/*5.- Genera una consulta donde se muestre el nombre del alumno, el nombre de la empresa
 * donde va a realizar las prácticas y los días que tiene que estar en ella.*/

SELECT a.nombre nombre_alumno, a.e_practica.nombre nombre_empresa, a.dias_practicas() dias_practicas FROM alumno a;
