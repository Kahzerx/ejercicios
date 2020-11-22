# fibb:  +>+>>++++++++++++++++++[-[->+<]<<<[->>+>+<<<]>>>[-<<<+>>>]<<[->+>+<<]>>[-<<+>>]>]
# Hola Mundo!: +++[->+++[->+++[->+++<]<]<]>+++[-<--->]>>[-<<<+>>>]<<<.>+++[->+++[->+++<]<]>>[-<<<+>>>]+++[-<+++>]<+++[-<<+>>]<<.---.>+++[-<--->]<--.>+++[->+++[->+++<]<]>>[-<+>]++[-<++>]<+.[-]<+++[->+++[->++<]<]>>++[-<<<->>>]<<<.+[-->+[->+++<]<]>>.<+++[-<-->]>-[-<<+>>]<<.->+++[-<--->]<.>+++[-<+++>]<++.[-]+++[->+++[->+++<]<]>++[-<+++>]>[-<<+>>]<<.[-]
# Fraxito: +++[->+++[->+++[->++<]<]<]>+++[->+++[->++<]<]>>--.<+++[-<+++[-<+++>]>]+++[->+++<]>[-<<<+>>>]<++[-<++[-<++>]>]<<.>+++[-<--->]+++[-<--->]<+.>+++[->+++[->++<]<]>++[->++<]>[-<<<+>>>]<<<+.>+++>>+++[-<+++[-<-->]>]<<[+<->]<.+>++>>+++[-<++[-<-->]>]<<[+<+>]<.>++[-<-->]<-.[-]+++[->+++[->+++<]<]>++[-<+++>]>[-<<+>>]<<.[-]
# fibb + printer: +++++++[->+++++++<]>-.<++++[->----<]>.[-]<+++++++++++++++>>+<<[->>[->>>>>>>>>>+<<<<<<<<<<]>>>>>>>>>>>>++++++++++<<[->+>-[>+>>]>[+[-<+>]>+>>]<<<<<<]>>[-]>>>++++++++++<[->-[>+>>]>[+[-<+>]>+>>]<<<<<]>[-]>>[>++++++[-<++++++++>]<.<<+>+>[-]]<[<[->-<]++++++[->++++++++<]>.[-]]<<++++++[-<++++++++>]<.[-]<<[-<+>]<<<<<<<<<<<[-]++++++++++++++++++++++++++++++++.[-]>>>>>>>>>>[-<<<<<<<<<<+>>>>>>>>>>]<<<<<<<<<<[->+>+<<]<[->+<]>>>[-<<<+>>>]<[-<+>]<<<]
def processCode(program):
    maxIters = 1000000
    buffer = [0]
    counter = 0
    pos = 0
    output = ''
    i = 0
    num = 0

    while i < len(program):

        j = program[i]

        if counter > maxIters:
            return 'Max iters'

        if j == '>':
            pos += 1
            if pos >= len(buffer):
                buffer.append(0)

        elif j == '<':
            pos -= 1
            if pos < 0:
                return 'Pointer out of bounds'

        elif j == '+':
            buffer[pos] += 1

        elif j == '-':
            buffer[pos] -= 1

        elif j == '.':
            output += chr(buffer[pos])

        elif j == '[':
            if buffer[pos] == 0:
                i += 1
                if i >= len(program):
                    return 'Instruction out of bounds'
                while num > 0 or program[i] != ']':
                    if program[i] == '[':
                        num += 1
                    elif program[i] == ']':
                        num -= 1
                    i += 1
                    if i >= len(program):
                        return 'Instruction out of bounds'

        elif j == ']':
            if buffer[pos] != 0:
                i -= 1
                if i < 0:
                    return 'Instruction out of bounds'
                while num > 0 or program[i] != '[':
                    if program[i] == ']':
                        num += 1
                    elif program[i] == '[':
                        num -= 1
                    i -= 1
                    if i < 0:
                        return 'Instruction out of bounds'
                i -= 1

        i += 1
        counter += 1

    bufferString = ''
    for i, l in enumerate(buffer):
        if i == pos:
            l = f'[{l}]'
        bufferString += f'{l} '

    output = output if output != '' else 'There is no output'

    result = f'{bufferString}\n==========\n{output}\n==========\nDone in {counter} iterations'

    return result
