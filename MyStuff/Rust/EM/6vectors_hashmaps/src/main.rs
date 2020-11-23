use std::collections::HashMap;

fn main() {
    // Vectors in rust are like fixed arrays, but you dont need to define size - items at compile time
    // and has way more functionalities (can grow adding or removing items).

    // Hash maps will let you map keys to values, you need to define the types.
    
    // let nums: Vec<i32> = Vec::new();
    let mut nums = vec![1, 2, 3];

    nums.push(4);
    nums.push(5);

    println!("Actual vec: {:?}", nums);

    println!("Get specific value: {}", nums[2]);
    println!("Get slices: {:?}", &nums[2..]);

    println!("`get` values: {:?}", nums.get(15));

    nums.remove(2);

    println!("Remove value {:?}", nums);

    for num in &nums {
        println!("{}", num);
    }

    println!("Increment 1");

    for num in &mut nums {
        *num += 1;
        println!("{}", num);
    }

    println!("Actual vec: {:?}", nums);

    // You can store different types in vectors.
    enum Value {
        Int(i32),
        Float(f32)
    };

    let _random = vec![Value::Int(3), Value::Float(3.3)];

    let mut numbers: HashMap<&str, i32> = HashMap::new();
    numbers.insert("one", 1);
    numbers.insert("two", 2);
    numbers.insert("three", 3);

    println!("Actual hashmap: {:?}", numbers);
    println!("`get` values: {}", numbers.get("two").unwrap());  // only use unwrap if you can garantee that "two" exists, you cant unwrap None and causes panic.
    // solutions

    if numbers.contains_key("two") {
        println!("`get` values: {}", numbers.get("two").unwrap());
    }

    match numbers.get("two") {
        Some(val) => println!("{}", val),
        None => println!("Key does not exist")
    };

    numbers.remove("three");

    println!("Remove value {:?}", numbers);

    for (key, value) in &numbers {
        println!("{} => {}", key, value);  // stuff in the hashmap are stored randomly.
    }
}
