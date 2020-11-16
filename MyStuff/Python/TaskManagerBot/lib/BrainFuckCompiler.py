if __name__ == "__main__":
    program = '+>+>>++++++++[-[->+<]<<<[->>+>+<<<]>>>[-<<<+>>>]<<[->+>+<<]>>[-<<+>>]>]'
    maxIters = 10000
    buffer = [0]
    pos = 0
    output = ''
    i = 0


    while i < len(program):

        j = program[i]

        if i > maxIters:
            print('max iters')
            break

        if j == '>':
            pos += 1
            if pos >= len(buffer):
                buffer.append(0)

        elif j == '<':
            pos -= 1
            if pos < 0:
                print('out of bounds exception')
                break

        elif j == '+':
            buffer[pos] += 1

        elif j == '-':
            buffer[pos] -= 1

        elif j == '.':
            output += chr(buffer[pos])

        elif j == '[':
            num = 0
            if buffer[pos] == 0:
                for k in range(i, len(program)):
                    if program[k] == '[':
                        num += 1
                    elif program[k] == ']':
                        num -= 1
                        if num == 0:
                            i = k

        elif j == ']':
            if buffer[pos] != 0:
                ind = i
                num = 0
                while ind >= 0:
                    if program[ind] == ']':
                        num += 1
                    elif program[ind] == '[':
                        num -= 1
                        if num == 0:
                            i = ind - 1
                            continue
                    ind -= 1

        i += 1

    bufferString = ''
    for i, l in enumerate(buffer):
        if i == pos:
            l = f'[{l}]'
        bufferString += f'{l} '

    output = output if output != '' else 'There is no output'

    print(f'{bufferString}\n==========\n{output}')