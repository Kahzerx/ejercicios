fn main() {
    enum Color {  // Just custom types with predefined names and variants.
        _Red,
        Green,
        Blue,
        _Orange,
        Custom(String),  // tuple struct style
        _Coord{x: i32, y: i32}  // classic struct style
    }

    // explicit values.
    enum Number {
        One = 1,
        Five = 5,
        Ten = 10
    }

    println!("{}", Number::One as i32);
    println!("{}", Number::Five as i32);
    println!("{}", Number::Ten as i32);

    let mut favourite: Color = Color::Green;
    let custom: Color = Color::Custom("Pink".to_string());

    //check with if let
    if let Color::Green = favourite {  // let favourite = Color::Green is not the same, that will assign the enum to the variable and won't work properly.
        println!("favourite color is green");
    }

    favourite = Color::Blue;

    // matching is just a reaally fancy switch statement.
    match favourite {
        Color::Green => println!("favourite color is green"),
        Color::Blue => println!("favourite color is blue"),
        _ => {}  // match requires to handle every posibility, compiler complains as it is not handling custom.
    }

    match custom {
        Color::Custom(color) => println!("custom color: {}", color),
        _ => {}
    }

    // Rust doesnt have null values.
    // Built-in Option<T> enum.
    let mut age: Option<i32> = None;  // warn because the compiler thinks i should add the value 18 now.
    // Do processing...
    age = Some(18);

    match age {
        Some(age) => {
            if age >= 18 {
                println!("Age over 17 {}", age);
            }
            else {
                println!("Age under 18 {}", age);
            }
        },
        None => println!("unknown age")
    }
}
