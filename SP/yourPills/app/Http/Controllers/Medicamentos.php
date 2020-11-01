<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Imputs;
use Illuminate\Support\Facades\Crypt;

class Medicamentos extends Controller {
    function save(Request $req){
        $imput=new Imputs;
        $imput -> Nombre = $req -> nombre;
        $imput -> Cantidad = $req -> cantidad;

        $day = $req -> lunes;
        $day .= $req -> martes;
        $day .= $req -> miercoles;
        $day .= $req -> jueves;
        $day .= $req -> viernes;
        $day .= $req -> sabado;
        $day .= $req -> domingo;  // Detecto todas las checkbox seleccionadas y las uno.

        $imput -> Dias = $day;
        $imput -> Franja_Horas = $req -> horas;
        $imput -> save();  // Guardo todos los elementos en una row de la Base de Datos.
    }

    public function index() {
        $rows = Imputs::all();  // Sacamos todas las rows de la base de datos definida en el Model `Imputs`.
        return view('list', compact('rows'));  // Se lo pasamos a la view `list.blade.php`.
    }

    public function removerow(Request $req) {
        $row = Imputs::find(Crypt::decrypt($req -> id));
        if (!$row == null) {
            $row -> delete();
        }
    }

    public function editrow($id) {
        // Intento acceder al editor de esa row especifica si existe, si no devuelve a list.
        $row = Imputs::where('id', Crypt::decrypt($id)) -> get();
        if (!$row -> isEmpty()){
            return view('edit', compact('row'));
        }
        else {
            return redirect('list');
        }
    }

    function update(Request $req){
        $id = Crypt::decrypt($req -> id);

        $Nombre = $req -> nombre;

        $Cantidad = $req -> cantidad;

        $day = $req -> lunes;
        $day .= $req -> martes;
        $day .= $req -> miercoles;
        $day .= $req -> jueves;
        $day .= $req -> viernes;
        $day .= $req -> sabado;
        $day .= $req -> domingo;
        $Dias = $day;

        $Franja_Horas = $req -> horas;

        // Introducimos los datos actualizados actualizando la row anterior.
        Imputs::where('id', $id) -> update(['Nombre' => $Nombre, 'Cantidad' => $Cantidad, 'Dias' => $Dias, 'Franja_Horas' => $Franja_Horas]);

        return redirect('list');
    }
}
