<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateYourPillsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('your_pills', function (Blueprint $table) {
            $table->id();
            $table->string('Nombre');
            $table->integer('Cantidad');
            $table->integer('Dias');
            $table->integer('Franja_Horas');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('your_pills');
    }
}
