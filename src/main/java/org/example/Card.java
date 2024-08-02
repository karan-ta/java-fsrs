package org.example;

import static org.example.Fsrs.WEIGHTS;

public class Card{

    float initialStability(int gradeNumber) {
        return WEIGHTS[gradeNumber - 1];
    }

    float initialDifficulty(int gradeNumber){
        final float calculatedValue = WEIGHTS[4] + (gradeNumber - 3) * WEIGHTS[5];
        return Math.max(1,Math.min(10, calculatedValue));
    }

}