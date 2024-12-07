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


}
