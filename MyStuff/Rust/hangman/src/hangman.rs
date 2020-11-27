use std::process::Command;
use crate::utils;
use crate::utils::{Word, hyphen_writer, log, build_word};

pub fn run() {
    let mut word = utils::Word::new();
    utils::initialize(&mut word, vec!["CETYS", "TEST", "HOLA", "ADIOS", "BABYYODA"]);

    loop {
        clear_console();

        play(&mut word);

        if !utils::validate("Quieres jugar de nuevo?", vec!["S", "N"]) { break; }
        utils::reset(&mut word);
    }
}

fn play(w: &mut Word) {
    let mut s = String::new();
    let mut has_finished = false;
    let array: Vec<char> = w.word.chars().collect();

    while !has_finished {
        hyphen_writer(w.already_done.to_owned());  // Escribe las "_" de la palabra reemplazando las ya completadas.
        print!("\nIntroduce una letra: ");
        utils::read(&mut s);
        s = s.to_uppercase().trim().parse().unwrap();  // Leo el input y lo parseo para quitar tabuladores e intros innecesarios.

        if s.chars().count() == 1 {  // Compruebo que solo se ha introducido un char.

            if array.contains(&s.chars().next().unwrap()) {  // Compruebo si el char existe en la palabra.

                if !w.already_done.contains(&s) {  // Compruebo si ya he escrito la letra introducida.
                    w.already_done = build_word(w.word.chars(), s.chars().next().unwrap(), w.already_done.to_owned());  // Actualizo mi array principal.

                    if !w.already_done.contains(&"_".to_string()) {  // Compruebo si he completado todas las letras.
                        log(vec!["GG"]);
                        has_finished = true;
                    }

                    else {
                        log(vec!["Intentos restantes", &w.tries.to_string()]);
                    }
                }

                else {
                    log(vec!["Ya has escrito esa letra!"]);
                    log(vec!["Intentos restantes", &w.tries.to_string()]);
                }
            }

            else {
                log(vec!["Letra incorrecta"]);
                w.tries -= 1;

                if w.tries > 0 {  // Compruebo que no he agotado mi máximo de intentos.
                    log(vec!["Intentos restantes", &w.tries.to_string()]);
                }

                else {
                    log(vec!["RIP :("]);
                    has_finished = true;
                }
            }
        }

        else {
            log(vec!["Error, introduce una sola letra."]);
            log(vec!["Intentos restantes", &w.tries.to_string()]);
        }
        s = "".parse().unwrap();  // Reseteo la variable, si no lo hago al proximo read() se hace un append.
    }
}

fn clear_console() {
    let output = Command::new("cls").output().unwrap_or_else(|_|Command::new("clear").output().unwrap());
    println!("{}", String::from_utf8_lossy(&output.stdout));
}