@extends('layouts.main')

@section('content')
<form action="/submitUpdate" method="POST" class="authbox" id="form">
    @csrf
    @foreach ($row as $element)
    <input type="hidden" name="id" value="{{ Crypt::encrypt($element -> id) }}">
    <div class="title">Usuario</div>
    <div class="subTitle">Actualiza los datos del medicamento</div>
    <h5 class="inputType">Nombre</h5>
    <input class="input" type="text" name="nombre" maxlength="30" value="{{ $element -> Nombre }}" required>
    <h5 class="inputType">Cantidad de unidades</h5>
    <input class="input" type="text" name="cantidad" oninput="this.value = onlyInt(this.value)" maxlength="9" value="{{ $element -> Cantidad }}" required>
    <h5 class="inputType">Dias de consumo</h5>
    <div class="checkboxDate">
        <ul data-days="{{ $element -> Dias }}" class="list">
            <li class="list-item">
                <h5>Lunes</h5>
                <input type="checkbox" name="lunes" value="1">
            </li>
            <li class="list-item">
                <h5>Martes</h5>
                <input type="checkbox" name="martes" value="2">
            </li>
            <li class="list-item">
                <h5>Miércoles</h5>
                <input type="checkbox" name="miercoles" value="3">
            </li>
            <li class="list-item">
                <h5>Jueves</h5>
                <input type="checkbox" name="jueves" value="4">
            </li>
            <li class="list-item">
                <h5>Viernes</h5>
                <input type="checkbox" name="viernes" value="5">
            </li>
            <li class="list-item">
                <h5>Sábado</h5>
                <input type="checkbox" name="sabado" value="6">
            </li>
            <li class="list-item">
                <h5>Domingo</h5>
                <input type="checkbox" name="domingo" value="7">
            </li>
        </ul>
    </div>
    <h5 class="inputType">Franja horaria (Cada cuántas horas)</h5>
    <input class="input" type="text" name="horas" value="{{ $element -> Franja_Horas }}" oninput="this.value = onlyIntAnd24(this.value)" required>
    <br><br><br>
    <div class="buttonContents">
        <input type="submit" value="Actualizar" class="submitButton">
    </div>
    @endforeach
    <div class="redirect" id="caja_salida"><a style="text-decoration: none; color: #888;" href="{{ url('list')}}">Click aquí volver a la lista</a></div>
</form>

<script type="text/javascript">
    $(document).ready(function() {
        let numArray = $('.list').attr('data-days').split('').map(Number);
        let form = document.getElementById('form');

        for (let i = 0; i < form.elements.length; i++) {  // Restaurar el estado de todas las checkBox.
            if (form.elements[i].type == 'checkbox' && numArray.includes(i - 3)) {
                form.elements[i].checked = true;
            }
        }

        $('.submitButton').click(function() {
            checked = $("input[type=checkbox]:checked").length; /* Check de que al menos 1 checkbox está marcada. */

            if (!checked) {
                alert("Debes seleccionar al menos 1 checkbox.");
                return false;
            }

        });
    });
</script>
@endsection