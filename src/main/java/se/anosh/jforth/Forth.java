package se.anosh.jforth;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Forth {

    private final Map<String, Runnable> dictionary;
    private final Deque<Integer> stack = new ArrayDeque<>();


    public Forth() {
        dictionary = new HashMap<>();

    }


}
