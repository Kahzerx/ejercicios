use crate::create_calc;
use crate::process;

pub fn run() {
    let mut calc = process::Calculator::new();

    create_calc::draw("");

    create_calc::draw(process::num1(&mut calc));

    process::operator(&mut calc);

    create_calc::draw(process::num2(&mut calc));

    if process::confirmation(&calc) {
        create_calc::draw(&process::calculate(&calc));
        process::print_operation(&calc);
    }

    process::clear(&mut calc);
}