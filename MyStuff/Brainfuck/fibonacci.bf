+>+>>++++++++[-[->+<]<<<[->>+>+<<<]>>>[-<<<+>>>]<<[->+>+<<]>>[-<<+>>]>]<


Explicación


+>+>> a 1 1
++++++++ Numero de iteraciones de fib alejado de operaciones
[-[->+<] hago iteraciones menos 1 y lo muevo 1 a la derecha
<<< regreso a la posicion donde están los numeros a sumar
[->>+>+<<<] muevo el valor en pos 1 a pos3 y pos4 dejando 1 en 0
>>> me voy a la pos4
[-<<<+>>>] recupero los valores en pos1 y dejo pos4 en 0
<< me voy a la pos2
[->+>+<<] muevo el valor en pos2 a pos3 y pos4 dejando pos2 en 0
>> me voy a la pos4
[-<<+>>] recupero los valores en pos2 y dejo pos4 en 0
>]< avanzo 1 a la derecha para repetir el proceso con los siguientes 2 valores

Para probar copiar y pegar en "https://repl.it/languages/Brainfuck"

Con logger de verdad with EDD
+++++++[->+++++++<]>-.<++++[->----<]>.[-]<+++++++++++++++>>+<<[->>[->>>>>>>>>>+<<<<<<<<<<]>>>>>>>>>>>>++++++++++<<[->+>-[>+>>]>[+[-<+>]>+>>]<<<<<<]>>[-]>>>++++++++++<[->-[>+>>]>[+[-<+>]>+>>]<<<<<]>[-]>>[>++++++[-<++++++++>]<.<<+>+>[-]]<[<[->-<]++++++[->++++++++<]>.[-]]<<++++++[-<++++++++>]<.[-]<<[-<+>]<<<<<<<<<<<[-]++++++++++++++++++++++++++++++++.[-]>>>>>>>>>>[-<<<<<<<<<<+>>>>>>>>>>]<<<<<<<<<<[->+>+<<]<[->+<]>>>[-<<<+>>>]<[-<+>]<<<]