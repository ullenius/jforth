package se.anosh.jforth;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Forth {

    private final Map<String, Runnable> dictionary;
    private final Deque<Integer> stack = new ArrayDeque<>();


    public Forth() {
        dictionary = Map.of(
                "dup", this::dup,
                ".", this::dot,
                "assert", this::assertEquals

        );
    }

    public void interpret(String code) {
        String[] arr = code.split(" ");
        for (String cmd : arr) {
            if (dictionary.containsKey(cmd)) {
                dictionary.get(cmd).run();
            }
            else {
                stack.push(Integer.parseInt(cmd));
            }
        }
    }

    // ( n -- )
    private void dot() {
            int n = stack.pop();
            System.out.printf("%d ", n);
    }

    // ( n -- n, n )
    private void dup() {
        int n = stack.peek();
        stack.push(n);
    }

    // ( actual, expected -- )
    private void assertEquals() {
        int expected = stack.pop();
        int actual = stack.pop();
        isTrue(expected == actual, "Expected: %d actual: %d".formatted(expected, actual));
    }

    private void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new AssertionError(message);
        }
    }


}
