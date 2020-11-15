fn main() {
    let _a: i32 = 5;  // Cant leave non used variables, in order to make it work put an "_" as variable prefix.

    let _b: [i32; 3] = [1, 2, 3];

    // let c: () = ["Hello", "World"];  // Let the program fail if you dont know what to put there :P.

    let _c: [&str; 2] = ["Hello", "World"];  // Let the program fail if you dont know what to put there :P.



    let _num: i32 = 3;  // let to declare the "num" variable, then : followed by type (32bit integer).
    // You can change variable type.
    let _num: &str = "three";
    let _num: f32 = 3.0;

    let _num = 4;  // Is smart enough to add type depending on the value you input.

    // let a = Vec::new();  // In this situation I've declared a variable of vectors but is empty, rust can't add type, (integer?, string?).
    let _a: Vec<i32> = Vec::new();  // you now need to specify the type

    // Variables are not mutable, if you want to change it you need to add "mut".

    let mut _num2 = 56;  // I intend to change "num2" value.
    _num2 = 66;



    let _name: &'static str = "Hello world";  // not mutable, literal stored in read only data.

    let mut name2: String = String::new();  // Create mutable string on heap.
    name2 += "Hello";
    name2.push_str(" world");
    println!("{}", name2);

    let name3: &str = &name2[..5];  // same as str[:2] in python.
    let name4 = &name2[6..];
    println!("{} - {}", name3, name4);

    for i in name2.chars() {
        print!("{}", i);
    }
    println!("");


    let name = String::from("Hello world");

    let size = get_length(&name);

    println!("The string length is {}", size);

    take_ownership(&name);  // If you don't use & as prefix, you will send the value to the function, which when completed, will deallocate it
    // So you won't be able to use it again :(, using & means you are sending a copy.

    println!("{}", name);

    println!("{}", add(1, 3));
    println!("{}", add(5, 8));

    let mut n = 1;
    incrementer(&mut n);
}

fn get_length(s: &str) -> usize {  // fn is function, s is variable name, &str is variable type, -> usize indicates return value if there is one
    s.chars().count()  // Rust will automatically detect that the return value is the last line that doesnt have a semicolon. WTF
}

fn take_ownership(s: &String) {
    println!("{}", s);
}

fn add(n1: i32, n2: i32) -> i32 {
    n1 + n2
}

fn incrementer(n1: &mut i32) {
    *n1 = *n1 + 1;  // Dereferencing the same way as C, to manipulate data contained in memory located by a pointer.
    println!("{}", n1);
}
