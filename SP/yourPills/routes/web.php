<?php

use App\Http\Controllers\Medicamentos;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('index');
});

Route::get('list', 'Medicamentos@index');  // Sacar la información de la base de datos y ponerla en forma de lista.

Route::get('list/removerow', 'Medicamentos@removerow') -> name('list.removerow');  // Eliminar una row específica.

/* Route::get('edit/{num}', 'Medicamentos@editrow', function($type) {
    return view('edit', ['row' => $type]);
}); */

Route::get('edit/{id}', 'Medicamentos@editrow');

Route::get('edit', function() {
    return redirect('list');
});

Route::post('submit','Medicamentos@save');  // Guardar la información del form en la base de datos.

Route::post('/submitUpdate','Medicamentos@update') ;  // Guardar la información del form en la base de datos.
