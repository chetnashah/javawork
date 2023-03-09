
## Stack machine

http://fpgacpu.ca/stack/Second-Generation_Stack_Computer_Architecture.pdf
https://www.youtube.com/watch?v=eYk0EennUrA
https://www.youtube.com/watch?v=7hrLD4z8eUA

a stack machine is a computer processor or a virtual machine in which the primary interaction is moving short-lived temporary values to and from a push down stack.

 stack-based bytecode interpreter simulates a hardware processor with no general-purpose registers. That means that bytecode instructions must use an operand stack to hold temporary values. Temporary values include arithmetic operands, parameters, and return values

Stack machines extend push-down automata with additional load/store operations.

 all stack machine instructions assume that operands will be from the stack, and results placed in the stack

it's easier to generate code for a stack machine.

You don't have to deal with register allocation strategies. You have, essentially, an unlimited number of registers to work with.

operators, operands and results are put on stack

implicit operands must be on stack

1. JVM uses stackk machine
2. WASM uses stack machine

### History

1. Reverse polish notation

t he introduced a parenthesis-free notation for arithmetic and logic which
eventually became known as Polish Notation or Prefix Notation.

. Its main feature is that it
makes the order of operations explicit, contrary to the usual algebraic notation (correspondingly
called Infix Notation) which depends on a knowledge of operator precedence and the use of
parentheses to override it where necessary

For example, the expression (5 + 5)/2 requires the use of parentheses to specify that the
result should be 5 and not 7.5 due to the higher precedence of the division operator

 The equivalent Prefix expression / + 5 5 2 is unambiguous and can be evaluated left-to-right by leaving
the application of an operator pending until enough operands are available

RPE - s ’reverse Polish’ notation kept the operators in the same order as in the
original infix notation and removed the need for delaying the application of an operator since it
would arrive only after its operands.  `5 5 2 / +`

, only a single stack is required since the
operators are never waiting for their operands

It is now possible to visualize the general shape a machine designed to use such
code might take. It would have a ’running accumulator’ and ’nesting register’ as
described, and a number-store arranged on something like the pattern indicated,



## n-register stack machine

top n registers in stack machine.

1 register stack machine - register is called accumulator