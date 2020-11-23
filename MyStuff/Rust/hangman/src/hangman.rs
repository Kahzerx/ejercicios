use crate::utils;

pub fn run() {
    let mut word = utils::Word::new();
    utils::initialize(&mut word, vec!["CETYS", "TEST", "HOLA", "ADIOS", "BABYYODA"]);

    utils::play(&mut word);





    utils::validate("Quieres jugar de nuevo?", vec!["S", "N"]);
    utils::reset(&mut word);
}