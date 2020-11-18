use crate::create_calc;
use crate::process;

pub fn run() {
    let mut calc = process::Calculator::new();  // Creo una calculadora.

    create_calc::draw("");

    create_calc::draw(process::num1(&mut calc));  // Proceso y dibujo el num1.

    process::operator(&mut calc);  // Proceso el operador.

    create_calc::draw(process::num2(&mut calc));  // Proceso y dibujo el num2.

    if process::confirmation(&calc) {
        create_calc::draw(&process::calculate(&calc));  // Calcular.
        process::print_operation(&calc);
    }

    process::clear(&mut calc);  // Resetear los valores.
}