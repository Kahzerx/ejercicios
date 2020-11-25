use std::process::Command;
use crate::utils;

pub fn run() {
    let mut word = utils::Word::new();

    loop {
        clear_console();
        utils::initialize(&mut word, vec!["CETYS", "TEST", "HOLA", "ADIOS", "BABYYODA"]);

        utils::play(&mut word);

        if !utils::validate("Quieres jugar de nuevo?", vec!["S", "N"]) { break; }
        utils::reset(&mut word);
    }
}

fn clear_console() {
    let output = Command::new("cls").output().unwrap_or_else(|_|Command::new("clear").output().unwrap());
    println!("{}", String::from_utf8_lossy(&output.stdout));
}