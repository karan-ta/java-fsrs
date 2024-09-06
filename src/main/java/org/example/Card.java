package org.example;
import java.time.ZonedDateTime;

public class Card {
    private ZonedDateTime due;
    private double stability;
    private double difficulty;
    private int elapsedDays;
    private int scheduledDays;
    private int reps;
    private int lapses;
    private State state;
    private ZonedDateTime lastReview;

    public Card(
            ZonedDateTime due,
            double stability,
            double difficulty,
            int elapsedDays,
            int scheduledDays,
            int reps,
            int lapses,
            State state,
            ZonedDateTime lastReview) {
        if(due == null){
            this.due =  ZonedDateTime.now();
        }
        else{
            this.due = due;
        }
        if(lastReview != null){
            this.lastReview = lastReview;
        }
        this.stability = stability;
        this.difficulty = difficulty;
        this.elapsedDays = elapsedDays;
        this.scheduledDays = scheduledDays;
        this.reps = reps;
        this.lapses = lapses;
        this.state = state;

    }
    public Card(){
        this(
                null,
                0,
                0,
                0,
                0,
                0,
                0,
                State.New,
                null
        );
    }

}
