use rand::seq::SliceRandom;
use std::io::{stdin, stdout, Write};

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

    fn randomize(&mut self) {
        self.word = self.word_list.choose(&mut rand::thread_rng()).unwrap().to_string();
        let mut result = vec![];
        for _chr in self.word.chars() {
            result.push("_".to_string());
        }
        self.already_done = result;
    }
}

pub fn initialize(w: &mut Word, list: Vec<&str>) {
    create_word_list(w, list);
    get_random_word(w);
}

pub fn reset(w: &mut Word) {
    get_random_word(w);
    w.tries = 6;
}

pub fn play(w: &mut Word) {
    let mut s = String::new();
    let has_finished = false;
    let array = w.word.split("") as Vec<String>;
    while !has_finished {
        hyphen_writer(w);
        print!("\nIntroduce una letra: ");
        read(&mut s);
        s = s.to_uppercase().trim().parse().unwrap();
        if s.chars().count() == 1 {
            if array.contains(&s) {
                if !w.already_done.contains(&s) {
                    w.tries -= 1;
                }
                else {
                    println!("Ya has escrito esa letra!")
                }
            }
            else {
                println!("Letra incorrecta.");
            }
        }
        else {
            println!("Error");
        }
    }
}

fn hyphen_writer(w: &Word) {
    println!("{}", w.already_done.join(" "));
}

pub fn validate(s1: &str, opt: Vec<&str>) -> bool {
    let mut s = String::new();
    let mut is_valid = false;
    while !is_valid {
        s = "".to_string();
        print!("\n{} ({}): ", s1, opt.join("/"));
        read(&mut s);
        s = s.to_uppercase().trim().parse().unwrap();
        if s.chars().count() == 1 && opt.contains(&&*s) {
            is_valid = true;
        }
        else {
            println!("Introduce una opción correcta.");
        }
    }
    return s.to_ascii_uppercase().trim() == opt[0];
}

fn create_word_list(mut w: &mut Word, list: Vec<&str>) {
    let mut final_list = Vec::new();
    for w in list {
        final_list.push(w.to_string());
    }
    w.word_list = final_list;
}

fn get_random_word(w: &mut Word) {
    w.randomize();
}

fn read(input: &mut String) {  // Read from console.
    stdout().flush().expect("Failed to flush");
    stdin().read_line(input).expect("Failed to read");
}
