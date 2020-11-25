use rand::seq::SliceRandom;  // Elegir una palabra random.
use std::io::{stdin, stdout, Write};
use std::str::Chars;

pub struct Word {
    pub(crate) word: String,
    pub(crate) already_done: Vec<String>,
    word_list: Vec<String>,
    pub(crate) tries: i8
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

pub fn build_word(ltr: Chars, letter: char, mut actual_array: Vec<String>) -> Vec<String> {
    for (i, c) in ltr.enumerate() {

        if c == letter {
            actual_array[i] = c.to_string();
        }
    }
    actual_array
}

pub fn hyphen_writer(w: Vec<String>) {
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

pub fn create_word_list(list: Vec<&str>) -> Vec<String> {
    let mut final_list = Vec::new();

    for w in list {
        final_list.push(w.to_string());
    }

    final_list
}

pub fn log(what: Vec<&str>) {
    println!("{}", what.join(" "));
}

pub(crate) fn read(input: &mut String) {  // Read from console.
    stdout().flush().expect("Failed to flush");
    stdin().read_line(input).expect("Failed to read");
}
