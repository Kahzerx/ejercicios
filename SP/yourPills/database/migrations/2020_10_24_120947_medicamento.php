<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class Medicamento extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('medicamento', function(Blueprint $table) {
            $table -> increments('id');
            $table -> string('Nombre');
            $table -> integer('Cantidad');
            $table -> integer('Dias');
            $table -> integer('Franja_Horas');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('medicamento');
    }
}
