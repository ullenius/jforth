package se.anosh.jforth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ForthTest {

    private final Forth forth = new Forth();

    @Test
    void assertWorks() {
        assertThrows(AssertionError.class, () -> forth.interpret("2 3 assert"));
    }

    @Test
    void dupWorks() {
        forth.interpret("2 3 5 dup");
        forth.interpret("5 assert");
    }

    @Test
    void dotWorks() {
        forth.interpret("2 3 5 .");
        forth.interpret("3 assert");
    }

    @Test
    void swap() {
        forth.interpret("2 3 swap");
        forth.interpret("2 assert 3 assert");
    }

    @Test
    void over() {
        forth.interpret("2 3 over");
        forth.interpret("2 assert 3 assert 2 assert");
    }

    @Test
    void rot() {
        forth.interpret("2 3 5 rot");
        forth.interpret("2 assert 5 assert 3 assert");
    }

    @Test
    void drop() {
        forth.interpret("42 255 drop");
        forth.interpret("42 assert");
    }

    @Test
    void dotSWorks() {
        forth.interpret("2 3 5 7 .s");
        forth.interpret("7 assert 5 assert 3 assert 2 assert"); // stack is not modified
    }

    @Test
    void plusWorks() {
        forth.interpret("10 32 +");
        forth.interpret("42 assert");
    }

    @Test
    void minusWorks() {
        forth.interpret("10 15 -");
        forth.interpret("-5 assert");
    }

    @Test
    void multiplication() {
        forth.interpret("5 7 *");
        forth.interpret("35 assert");
    }

    @Test
    void createWord() {
        forth.interpret(": foo 10 32 + ;");
        forth.interpret("foo 42 assert");
    }

    @Test
    void wordInsideWord() {
        forth.interpret(": foo 5 5 bar ;");
        forth.interpret(": bar * ;");
        forth.interpret("foo dup . 25 assert");
    }

    @Test
    void division() {
        forth.interpret("25 2 /");
        forth.interpret("12 assert");
    }

    @Test
    void modulo() {
        forth.interpret("25 2 mod");
        forth.interpret("1 assert");
    }

    @Test
    void caseInsensitiveParsing() {
        forth.interpret(": foo 42 10 * ;");
        forth.interpret("FOO dup . 420 assert");
    }

    @Test
    void negate() {
        forth.interpret("42 negate");
        forth.interpret("-42 assert");

        forth.interpret("-128 negate");
        forth.interpret("128 assert");
    }

    @Test
    void abs() {
        forth.interpret("-42 abs");
        forth.interpret("42 assert");

        forth.interpret("255 abs");
        forth.interpret("255 assert");
    }

    @Test
    void doubleSwap() { // 2swap
        forth.interpret("2 3 5 7 2swap");
        forth.interpret("3 assert 2 assert 7 assert 5 assert"); // 5 7 2 3
    }

    @Test
    void doubleDrop() {// 2drop
        forth.interpret("2 3 5 7 2drop");
        forth.interpret("3 assert 2 assert");
    }

    @Test
    void equals() { // =
        forth.interpret("2 2 =");
        forth.interpret("true assert");

        forth.interpret("8 16 =");
        forth.interpret("false assert");
    }

    @Test
    void notEquals() { // <>
        forth.interpret("64 128 <>");
        forth.interpret("true assert");

        forth.interpret("32 32 <>");
        forth.interpret("false assert");
    }


    @Test
    void trueAliasWorks() {
        forth.interpret("-1 true assert");
    }

    @Test
    void falseAliasWorks() {
        forth.interpret("0 false assert");
    }

    @Test
    void invertWorks() {
        forth.interpret("true invert");
        forth.interpret("false assert");

        forth.interpret("false invert");
        forth.interpret("true assert");
    }

    @Test
    void isZero() {
        forth.interpret("0 0=");
        forth.interpret("true assert");
    }

    @Test
    void greaterThanZero() {
        forth.interpret("42 0>");
        forth.interpret("true assert");

        forth.interpret("-127 0>");
        forth.interpret("false assert");
    }

    @Test
    void lessThanZero() {
        forth.interpret("-127 0<");
        forth.interpret("true assert");

        forth.interpret("255 0<");
        forth.interpret("false assert");
    }

    @Test
    void lessThan() {
        forth.interpret("-1 2 <");
        forth.interpret("true assert");

        forth.interpret("32 16 <");
        forth.interpret("false assert");
    }

    @Test
    void greaterThan() {
        forth.interpret("16 8 >");
        forth.interpret("true assert");

        forth.interpret("64 128 >");
        forth.interpret("false assert");
    }

    // bitwise
    @Test
    void and() { // bitwise AND &
        forth.interpret("true true AND");
        forth.interpret("true assert");

        forth.interpret("false false AND");
        forth.interpret("false assert");

        forth.interpret("true false AND");
        forth.interpret("false assert");

        forth.interpret("false true AND");
        forth.interpret("false assert");
    }

    @Test
    void or() {
        forth.interpret("true true OR");
        forth.interpret("true assert");

        forth.interpret("false false OR");
        forth.interpret("false assert");

        forth.interpret("true false OR");
        forth.interpret("true assert");

        forth.interpret("false true OR");
        forth.interpret("true assert");
    }

    @Test
    void xor() {
        forth.interpret("true true XOR");
        forth.interpret("false assert");

        forth.interpret("false false XOR");
        forth.interpret("false assert");

        forth.interpret("true false XOR");
        forth.interpret("true assert");

        forth.interpret("false true XOR");
        forth.interpret("true assert");
    }

    @Test
    void min() {
        forth.interpret("2 5 MIN");
        forth.interpret("2 assert");

        forth.interpret("-5 -42 MIN");
        forth.interpret("-42 assert");
    }

    @Test
    void max() {
        forth.interpret("2 5 MAX");
        forth.interpret("5 assert");

        forth.interpret("-5 -42 MAX");
        forth.interpret("-5 assert");
    }

    @Test
    void nonZeroDup() { // ?dup
        forth.interpret("2 3 ?dup");
        forth.interpret("3 assert 3 assert 2 assert");

        forth.interpret("2 4 8 16 0 ?dup");
        forth.interpret("0 assert 16 assert 8 assert 4 assert 2 assert");
    }

    @Test
    void parenthesisCommentsAreIgnored() {
        forth.interpret(": cubed ( n -- n ) dup dup * * ;");
        forth.interpret("5 cubed 125 assert");
    }


    // TODO
    /*
        # bitwise operators:
        lshift
        rshift

        # double
        2dup
        2over
     */





}
