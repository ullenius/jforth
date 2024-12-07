package se.anosh.jforth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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

}
