package se.anosh.jforth;

import java.util.*;
import java.util.stream.Collectors;

public class Forth {

    private final Map<String, Runnable> dictionary;
    private final Deque<Integer> stack = new ArrayDeque<>();

    private final Runnable NOOP = () -> {};


    public Forth() {
        dictionary = Map.of(
                "dup", this::dup,
                ".", this::dot,
                ".s", this::dotS,
                "swap", this::swap,
                "over", this::over,
                "rot", this::rot,
                "drop", this::drop,
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
    private void drop() {
        stack.pop();
    }

    // ( n1, n2 -- n1, n2, n1 )
    private void over() {
        int a = stack.pop();
        dup();
        stack.push(a);
        swap();
    }

    // (  n1, n2, n3 -- n2, n3, n1 )
    private void rot() {
        int a = stack.pop();
        swap();
        stack.push(a);
        swap();
    }

    // . ( n -- )
    private void dot() {
            int n = stack.pop();
            System.out.printf("%d ", n);
    }

    // .s ( -- )
    private void dotS() {
        var all = new ArrayList<>(stack);
        Collections.reverse(all);
        System.out.println(all);
    }

    // ( n1, n2 -- n2, n1 )
    private void swap() {
        int n2 = stack.pop();
        int n1 = stack.pop();
        stack.push(n2);
        stack.push(n1);
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
