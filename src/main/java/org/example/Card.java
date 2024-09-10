package org.example;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Card {
    private LocalDate due;
    private double stability;
    private double difficulty;
    private int elapsedDays;
    private int scheduledDays;
    private int reps;
    private int lapses;
    private State state;
    private LocalDate lastReview;

    public Card(
            LocalDate due,
            double stability,
            double difficulty,
            int elapsedDays,
            int scheduledDays,
            int reps,
            int lapses,
            State state,
            LocalDate lastReview) {
        if(due == null){
            this.due =  LocalDate.now();
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

    public Optional<Double> getRetrievability(LocalDate now) {
        final float DECAY = -0.5f;
        final double FACTOR = Math.pow(0.9, 1 / DECAY) - 1;
        if (state == State.Review) {
            long elapsedDays = Math.max(0, ChronoUnit.DAYS.between(lastReview, now));
            double retrievability = Math.pow(1 + FACTOR * elapsedDays / stability, DECAY);
            return Optional.of(retrievability);
        } else {
            return Optional.empty();
        }
    }

    public Map<String, Object> toDict() {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("due", due.toString()); // ISO format
        returnMap.put("stability", stability);
        returnMap.put("difficulty", difficulty);
        returnMap.put("elapsed_days", elapsedDays);
        returnMap.put("scheduled_days", scheduledDays);
        returnMap.put("reps", reps);
        returnMap.put("lapses", lapses);
        returnMap.put("state", state.getValue()); // Assuming state has a getValue() method

        if (lastReview != null) {
            returnMap.put("last_review", lastReview.toString()); // ISO format
        }

        return returnMap;
    }

    public static Card fromDict(Map<String, Object> sourceDict) {
        LocalDate due = LocalDate.parse((String) sourceDict.get("due"), DateTimeFormatter.ISO_DATE);
        double stability = Double.parseDouble(sourceDict.get("stability").toString());
        double difficulty = Double.parseDouble(sourceDict.get("difficulty").toString());
        int elapsedDays = Integer.parseInt(sourceDict.get("elapsed_days").toString());
        int scheduledDays = Integer.parseInt(sourceDict.get("scheduled_days").toString());
        int reps = Integer.parseInt(sourceDict.get("reps").toString());
        int lapses = Integer.parseInt(sourceDict.get("lapses").toString());
        State state = State.values()[Integer.parseInt(sourceDict.get("state").toString())];

        LocalDate lastReview = null;
        if (sourceDict.containsKey("last_review")) {
            lastReview = LocalDate.parse((String) sourceDict.get("last_review"), DateTimeFormatter.ISO_DATE);
        }

        return new Card(due, stability, difficulty, elapsedDays, scheduledDays, reps, lapses, state, lastReview);
    }


}
