fn main() {
    let age = 25;

    if age > 21 {
        println!("over 21");
    }
    else if age < 21 {
        println!("under 21");
    }
    else {
        println!("exactly 21");
    }

    let old_enough = if age > 21 { true } else { false };  // no ternary :(

    println!("{}", old_enough);

    match age {  // like a switch but it can also detect ranges.
        21 => println!("age is 21"),
        22 => println!("age is 22"),
        23 | 24 => println!("age is 23 or 24"),
        25..=28 => println!("age is between 25 and 28"),
        n if n < 5 => println!("age is less than 5"),  // n is just a placeholder for the actual age.
        n if n > 50 => println!("age is greater than 50"),
        _ => println!("age is smth else {}", age)
    }

    let mut i = 0;
    loop {  // infinite loop
        if i == 10 {
            break;
        }
        i += 1;
    }

    let mut j = 0;
    let x = loop {  // You can capture the value of the loop when it stopped.
        if j == 10 {
            break j;
        }
        j += 1;
    };

    println!("The value of j on break was {}", x);

    let mut k = 0;
    while k < 10 {
        k += 1;
    }

    for i in (0..10).step_by(1) {  // instead of for (int i = 0; i < 10; i++) | 0..10 is a range.
        println!("{}", i);
    }

    for i in 0..10 {  // step by 1 is default and we can remove it.
        println!("{}", i);
    }

    let nums = vec![1, 2, 3, 4, 5];
    for num in nums {
        println!("{}", num);
    }
}
