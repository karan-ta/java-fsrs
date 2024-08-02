package org.example;

import java.util.Map;

public class Fsrs {
    static Map<String, Integer> Grade = Map.ofEntries(
            Map.entry("AGAIN", 1),
            Map.entry("HARD", 2),
            Map.entry("GOOD", 3),
            Map.entry("EASY", 4)
    );
    static final float DECAY = -0.5;

}
