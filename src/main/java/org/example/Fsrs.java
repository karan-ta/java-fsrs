package org.example;

import java.util.Map;

public class Fsrs {
    static Map<String, Integer> Grade = Map.ofEntries(
            Map.entry("AGAIN", 1),
            Map.entry("HARD", 2),
            Map.entry("GOOD", 3),
            Map.entry("EASY", 4)
    );
    static final float DECAY = -0.5f;
    static final float REQUESTED_RETENTION_RATE = 0.9f;
    static final float WEIGHTS[] = new float[] {0.4f, 0.6f, 2.4f, 5.8f, 4.93f, 0.94f, 0.86f, 0.01f, 1.49f, 0.14f, 0.94f, 2.18f, 0.05f,
            0.34f, 1.26f, 0.29f, 2.61f};

    static final double FACTOR = 19.0 / 81.0;


}
