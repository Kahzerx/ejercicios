// `curl https://sh.rustup.rs -sSf | sh`  to install

mod hello;

fn main() {  // By running `cargo init` in the terminal will generate the proyect structure including src with a main.rs with some metadata/dependencies in a cargo.toml.
    println!("Hello, world!");  // Always executes main(), will always be the entry point for the application
    hello::say_hello();  // run func in other class.
}
// To execute cargo use `cargo run`.
// This will download the dependencies & compiles everything in src and runs it.
