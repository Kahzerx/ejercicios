use rand::seq::SliceRandom;  // Elegir una palabra random.
use std::io::{stdin, stdout, Write};
use std::str::Chars;

pub struct Word {
    word: String,
    already_done: Vec<String>,
    word_list: Vec<String>,
    tries: i8
}

impl Word {
    pub(crate) fn new() -> Self {
        Word {
            word: "".to_string(),
            already_done: vec![],
            word_list: vec![],
            tries: 6
        }
    }

    fn randomize(&mut self) -> Vec<String> {
        self.word = self.word_list.choose(&mut rand::thread_rng()).unwrap().to_string();
        let mut result = vec![];
        for _chr in self.word.chars() {
            result.push("_".to_string());
        }

        result
    }
}

pub fn initialize(w: &mut Word, list: Vec<&str>) {
    w.word_list = create_word_list(list);
    w.already_done = w.randomize();
}

pub fn reset(w: &mut Word) {
    w.already_done = w.randomize();
    w.tries = 6;
}

pub fn play(w: &mut Word) {
    let mut s = String::new();
    let mut has_finished = false;
    let array: Vec<char> = w.word.chars().collect();

    while !has_finished {
        hyphen_writer(w.already_done.to_owned());  // Escribe las "_" de la palabra reemplazando las ya completadas.
        print!("\nIntroduce una letra: ");
        read(&mut s);
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

fn build_word(ltr: Chars, letter: char, mut actual_array: Vec<String>) -> Vec<String> {
    for (i, c) in ltr.enumerate() {

        if c == letter {
            actual_array[i] = c.to_string();
        }
    }
    actual_array
}

fn hyphen_writer(w: Vec<String>) {
    println!("{}", w.join(" "));
}

pub fn validate(s1: &str, opt: Vec<&str>) -> bool {
    let mut s = String::new();
    let mut is_valid = false;

    while !is_valid {
        s = "".to_string();
        print!("\n{} ({}): ", s1, opt.join("/"));
        read(&mut s);
        s = s.to_uppercase().trim().parse().unwrap();

        if s == "" {
            return true;
        }

        if s.chars().count() == 1 && opt.contains(&&*s) {
            is_valid = true;
        }

        else {
            log(vec!["Introduce una opción correcta."]);
        }
    }

    s == opt[0].to_uppercase()
}

fn create_word_list(list: Vec<&str>) -> Vec<String> {
    let mut final_list = Vec::new();

    for w in list {
        final_list.push(w.to_string());
    }

    final_list
}

fn log(what: Vec<&str>) {
    println!("{}", what.join(" "));
}

fn read(input: &mut String) {  // Read from console.
    stdout().flush().expect("Failed to flush");
    stdin().read_line(input).expect("Failed to read");
}
