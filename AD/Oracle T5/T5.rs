struct TcicloFormativo {
    _cod_ciclo: String,
    nombre: String
}

struct Tempresa {
    _cif: String,
    nombre: String
}

struct Talumno {
    _dni: String,
    nombre: String,
    ciclo_formativo: TcicloFormativo,
    e_practicas: Tempresa,
    horas_diarias: i16
}

impl Talumno {
    fn dias_practicas(&self) -> i16 {
        return 370 / self.horas_diarias;
    }
}

fn main() {
    let ciclo1 = TcicloFormativo { _cod_ciclo: "123A".to_string(), nombre: "ciclo1".to_string() };
    let ciclo2 = TcicloFormativo { _cod_ciclo: "123B".to_string(), nombre: "ciclo2".to_string() };
    let ciclo3 = TcicloFormativo { _cod_ciclo: "123C".to_string(), nombre: "ciclo3".to_string() };

    let empresa1 = Tempresa { _cif: "234A".to_string(), nombre: "empresa1".to_string() };
    let empresa2 = Tempresa { _cif: "234B".to_string(), nombre: "empresa2".to_string() };
    let empresa3 = Tempresa { _cif: "234C".to_string(), nombre: "empresa3".to_string() };

    let alumno1 = Talumno { _dni: "345A".to_string(), nombre: "alumno1".to_string(), ciclo_formativo: ciclo1, e_practicas: empresa1, horas_diarias: 5 };
    let alumno2 = Talumno { _dni: "345B".to_string(), nombre: "alumno2".to_string(), ciclo_formativo: ciclo2, e_practicas: empresa2, horas_diarias: 4 };
    let alumno3 = Talumno { _dni: "345C".to_string(), nombre: "alumno3".to_string(), ciclo_formativo: ciclo3, e_practicas: empresa3, horas_diarias: 6 };

    println!("El alumno {} del ciclo {} va a la empresa {} para hacer prácticas durante {} días.",
    alumno1.nombre, alumno1.ciclo_formativo.nombre, alumno1.e_practicas.nombre, alumno1.dias_practicas());
    println!("El alumno {} del ciclo {} va a la empresa {} para hacer prácticas durante {} días.",
    alumno2.nombre, alumno2.ciclo_formativo.nombre, alumno2.e_practicas.nombre, alumno2.dias_practicas());
    println!("El alumno {} del ciclo {} va a la empresa {} para hacer prácticas durante {} días.",
    alumno3.nombre, alumno3.ciclo_formativo.nombre, alumno3.e_practicas.nombre, alumno3.dias_practicas());
}