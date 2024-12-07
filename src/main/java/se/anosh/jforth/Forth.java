package se.anosh.jforth;

import java.util.*;
import java.util.stream.Collectors;

public final class Forth {

    private static final char START_WORD_DEFINITION = ':';
    private static final char END_WORD_DEFINITION = ';';

    private final Map<String, Runnable> dictionary;
    private final Deque<Integer> stack = new ArrayDeque<>();

    private final Runnable NOOP = () -> {
    };

    public Forth() {
        dictionary = new HashMap<>();
        dictionary.putAll(Map.of(
                "dup", this::dup,
                ".", this::dot,
                ".s", this::dotS,
                "swap", this::swap,
                "over", this::over,
                "rot", this::rot,
                "drop", this::drop,
                "+", this::plus,
                "-", this::minus,
                "assert", this::assertEquals
        ));
        dictionary.putAll(Map.of(
                "*", this::multiply,
                "/", this::division,
                "mod", this::modulo
        ));
    }

    public void interpret(String code) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < code.length(); i++) {
            char ch = code.charAt(i);
            if (ch == START_WORD_DEFINITION) {
                StringBuilder func = new StringBuilder();
                do {
                    ch = code.charAt(i);
                    func.append(ch);
                    i++;
                } while (ch != END_WORD_DEFINITION);
                addWord(func.toString());
                continue; // resume for-loop
            }
            if (ch == ' ') {
                if (!sb.isEmpty()) {
                    interpretWord(sb.toString());
                    sb = new StringBuilder();
                }
                continue; // skip to next char
            }
            sb.append(code.charAt(i));
        }
        if (!sb.isEmpty()) {
            interpretWord(sb.toString()); // execute last word
        }
    }

    private void addWord(String word) { // TODO refactor
        int endNamePos = word.indexOf(" ", 2);
        String name = word.substring(2, endNamePos);
        //System.out.println("name:" + name);
        String func = word.substring(endNamePos+1, word.lastIndexOf(" "));
        //System.out.println(func);
        dictionary.put(name, () -> interpret(func));
    }

    private void interpretWord(String word) {
        final String lowercaseWord = word.toLowerCase();
        if (dictionary.containsKey(lowercaseWord)) {
            dictionary.get(lowercaseWord).run();
        } else {
            stack.push(Integer.parseInt(word));
        }
    }

    // + ( n1, n2 -- sum )
    private void plus() {
        int a = stack.pop();
        int b = stack.pop();
        int sum = a + b;
        stack.push(sum);
    }

    // - ( n1, n2 -- diff )
    private void minus() {
        int a = stack.pop();
        int b = stack.pop();
        int diff = b - a;
        stack.push(diff);
    }

    // * ( n1, n2 -- product )
    private void multiply() {
        int a = stack.pop();
        int b = stack.pop();
        int product = a * b;
        stack.push(product);
    }

    // ( dividend, divisor -- quotient )
    private void division() {
        int divisor = stack.pop();
        int dividend = stack.pop();
        int quotient = dividend / divisor;
        stack.push(quotient);
    }

    // ( dividend, divisor -- remainder )
    private void modulo() {
        int divisor = stack.pop();
        int dividend = stack.pop();
        int remainder = dividend % divisor;
        stack.push(remainder);
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

    @Override
    public String toString() {
        return dictionary.keySet().toString();
    }


}
