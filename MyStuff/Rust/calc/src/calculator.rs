use crate::create_calc;
use crate::process;
use std::process::Command;

pub fn run() {
    let mut calc = process::Calculator::new();  // Creo una calculadora.
    loop {
        clear_console();

        create_calc::draw(process::num1(&mut calc));  // Proceso y dibujo el num1.

        process::operator(&mut calc);  // Proceso el operador.

        create_calc::draw(process::num2(&mut calc));  // Proceso y dibujo el num2.

        if process::confirmation(&calc) {
            create_calc::draw(&process::calculate(&calc));  // Calcular.
            process::print_operation(&calc);
        }

        if !process::validate("Quieres realizar otra operación?", vec!["S", "N"]) { break; }

        process::clear(&mut calc);  // Resetear los valores.
    }
}

fn clear_console() {
    let output = Command::new("cls").output().unwrap_or_else(|_|Command::new("clear").output().unwrap());
    println!("{}", String::from_utf8_lossy(&output.stdout));
}
