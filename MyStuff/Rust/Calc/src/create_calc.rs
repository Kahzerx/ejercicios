/// https://ascii.co.uk/art/calculator
pub fn draw(mut content: &str) {
    if content == "" {
        content = "0";
    }
    else if get_str_size(content) >= 15 {
        content = "OUT OF RANGE";
    }
    let calc: &str = "
 _____________________
|  _________________  |
| |                 | |
| |_________________| |
|  ___ ___ ___   ___  |
| | 7 | 8 | 9 | | + | |
| |___|___|___| |___| |
| | 4 | 5 | 6 | | - | |
| |___|___|___| |___| |
| | 1 | 2 | 3 | | x | |
| |___|___|___| |___| |
| | . | 0 | = | | / | |
| |___|___|___| |___| |
|_____________________|";
    // println!("{:?}", calc.split("\n"));
    // println!("{}", content);
    for (i, x) in calc.split("\n").enumerate() {
        if i == 3 {
            // let screen = &x[4..19];
            let new_content = build_screen_content(content);
            println!("{}", new_content);
        }
        else {
            println!("{}", x);
        }
    }
}

fn build_screen_content(new: &str) -> String {
    let mut updated = String::from("| | ");
    for _i in 0..15 - get_str_size(new) {
        updated.push_str(" ");
    }
    updated.push_str(new);
    updated.push_str(" | |");
    updated
}

fn get_str_size(s: &str) -> usize {
    s.chars().count()
}