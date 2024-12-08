# jforth
Simple Forth interpreter written in Java.

Usage:

```java
Forth forth = new Forth();

forth.interpret(": squared dup * ; ");
forth.interpret("2 squared");
forth.interpret("dup . 4 assert"); // 4
```


## Supported words
* .
* .s
* cr
* `: ;` \ word creation

### Stack manoeuvres
* dup
* swap
* over
* rot
* drop

### Maths
* `+`
* `-`
* /
* mod
* negate
* abs

### Custom
* assert `( actual expected -- ) \ assertEquals`

## Build
`mvn clean package`

## Requirements
* Java 17


## TODO
* Add `do loop`
* Add conditionals `if else then`
* Add string support
* Add return stack support - `>R R>`

## Licence
GPL-3.0-only
