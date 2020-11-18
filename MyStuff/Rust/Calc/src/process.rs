use std::io::{stdin, stdout, Write};

pub struct Calculator {
    num1: String,
    num2: String,
    operator: String
}

impl Calculator {
    pub(crate) fn new() -> Self {
        Calculator {
            num1: "".to_string(),
            num2: "".to_string(),
            operator: "".to_string()
        }
    }

    fn add(&self) -> f64 {
        self.num1.parse::<f64>().unwrap() + self.num2.parse::<f64>().unwrap()
    }

    fn sub(&self) -> f64 {
        self.num1.parse::<f64>().unwrap() - self.num2.parse::<f64>().unwrap()
    }

    fn mul(&self) -> f64 {
        self.num1.parse::<f64>().unwrap() * self.num2.parse::<f64>().unwrap()
    }

    fn div(&self) -> f64 {
        let res = (self.num1.parse::<f64>().unwrap() / self.num2.parse::<f64>().unwrap()).to_string();
        res[..14].parse::<f64>().unwrap()
    }
}

pub fn num1(calc: &mut Calculator) -> &str{
    calc.num1 = process_num(1);
    calc.num1.as_str()
}

pub fn num2(calc: &mut Calculator) -> &str {
    calc.num2 = process_num(2);
    calc.num2.as_str()
}

pub fn operator(calc: &mut Calculator) {
    calc.operator = process_operator();
    println!("\n{} {} ...", calc.num1, calc.operator);
}

pub fn calculate(calc: &Calculator) -> String {
    let operator: char = calc.operator.trim().chars().next().unwrap();
    let result = match operator {
        '+' => calc.add(),
        '-' => calc.sub(),
        '*' => calc.mul(),
        '/' => calc.div() as f64,
        _ => panic!("Error in operator")
    };

    result.to_string()
}

pub fn print_operation(calc: &Calculator) {
    println!("\n{} {} {} = {}\n", calc.num1, calc.operator, calc.num2, calculate(calc));
}

pub fn confirmation(calc: &Calculator) -> bool {
    let mut ipt = String::new();
    let mut is_valid = false;
    while !is_valid {
        print!("\n{} {} {}\n\nCalcular? (s/n): ", calc.num1, calc.operator, calc.num2);
        read(&mut ipt);
        if is_sn(&ipt) {
            is_valid = true;
        }
        else {
            println!("\nIntroduce una respuesta correcta (s/n).");
        }
    }
    return if "s" == ipt.trim().to_ascii_lowercase() { true } else { false }
}

fn process_num(i: i8) -> String {
    let mut s = String::new();
    let mut is_num = false;
    while !is_num {
        s = "".parse().unwrap();
        print!("\nCuál es el operador {}: ", i);
        read(&mut s);
        if is_numeric_string(&s.trim()) && is_valid_length(&s.trim()) {
            is_num = true;
        }
        else {
            println!("\nIntroduce un número correcto (max length 15).");
        }
    }
    s.trim().to_string()
}

fn process_operator() -> String {
    let operators = "+-*/";
    let mut s = String::new();
    let mut is_char = false;
    while !is_char {
        s = "".parse().unwrap();
        print!("\nCuál es el operador: ");
        read(&mut s);
        if s.trim().chars().count() == 1 && operators.contains(s.trim().chars().next().unwrap()){
            is_char = true;
        }
        else {
            println!("\nIntroduce un operador correcto ({})", operators);
        }
    }
    s.trim().to_string()
}

fn read(input: &mut String) {
    stdout().flush().expect("Failed to flush");
    stdin().read_line(input).expect("Failed to read");
}

pub fn clear(mut calc: &mut Calculator) {
    calc.num1 = "".parse().unwrap();
    calc.num2 = "".parse().unwrap();
    calc.operator = "".parse().unwrap();
}

fn is_numeric_string(s: &str) -> bool {
    let mut dec: i8 = 0;
    for c in s.chars() {
        if c == '.' {
            dec += 1;
        }
        if (dec > 1 || c != '.') && !c.is_numeric() {
            return false;
        }
    }
    return true;
}

fn is_valid_length(s: &str) -> bool {
    s.chars().count() <= 15
}

fn is_sn(s: &str) -> bool {
    s.trim().chars().count() == 1 && "sn".contains(s.chars().next().unwrap().to_ascii_lowercase())
}
