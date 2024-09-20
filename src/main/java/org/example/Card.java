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
    int lapses;
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

    public Card(Card card) {
        this.due = card.due;
        this.stability = card.stability;
        this.difficulty = card.difficulty;
        this.elapsedDays = card.elapsedDays;
        this.scheduledDays = card.scheduledDays;
        this.reps = card.reps;
        this.lapses = card.lapses;
        this.state = card.state;
        this.lastReview = card.lastReview;

    }

    public void setState(State state){
        this.state = state;
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


//
//   import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.HashMap;
//import java.util.Map;
//
//class SchedulingCards {
//    private Card again;
//    private Card hard;
//    private Card good;
//    private Card easy;
//
//    public SchedulingCards(Card card) {
//        this.again = new Card(card); // Assuming Card has a copy constructor
//        this.hard = new Card(card);
//        this.good = new Card(card);
//        this.easy = new Card(card);
//    }
//
//    public void updateState(State state) {
//        if (state == State.New) {
//            this.again.setState(State.Learning);
//            this.hard.setState(State.Learning);
//            this.good.setState(State.Learning);
//            this.easy.setState(State.Review);
//        } else if (state == State.Learning || state == State.Relearning) {
//            this.again.setState(state);
//            this.hard.setState(state);
//            this.good.setState(State.Review);
//            this.easy.setState(State.Review);
//        } else if (state == State.Review) {
//            this.again.setState(State.Relearning);
//            this.hard.setState(State.Review);
//            this.good.setState(State.Review);
//            this.easy.setState(State.Review);
//            this.again.incrementLapses(); // Assuming there's a method to increment lapses
//        }
//    }
//
//    public void schedule(LocalDateTime now, int hardInterval, int goodInterval, int easyInterval) {
//        this.again.setScheduledDays(0);
//        this.hard.setScheduledDays(hardInterval);
//        this.good.setScheduledDays(goodInterval);
//        this.easy.setScheduledDays(easyInterval);
//        this.again.setDue(now.plusMinutes(5));
//
//        if (hardInterval > 0) {
//            this.hard.setDue(now.plusDays(hardInterval));
//        } else {
//            this.hard.setDue(now.plusMinutes(10));
//        }
//
//        this.good.setDue(now.plusDays(goodInterval));
//        this.easy.setDue(now.plusDays(easyInterval));
//    }
//
//    public Map<Integer, SchedulingInfo> recordLog(Card card, LocalDateTime now) {
//        Map<Integer, SchedulingInfo> log = new HashMap<>();
//
//        log.put(Rating.Again, new SchedulingInfo(
//                this.again,
//                new ReviewLog(Rating.Again, this.again.getScheduledDays(), card.getElapsedDays(), now, card.getState())
//        ));
//
//        log.put(Rating.Hard, new SchedulingInfo(
//                this.hard,
//                new ReviewLog(Rating.Hard, this.hard.getScheduledDays(), card.getElapsedDays(), now, card.getState())
//        ));
//
//        log.put(Rating.Good, new SchedulingInfo(
//                this.good,
//                new ReviewLog(Rating.Good, this.good.getScheduledDays(), card.getElapsedDays(), now, card.getState())
//        ));
//
//        log.put(Rating.Easy, new SchedulingInfo(
//                this.easy,
//                new ReviewLog(Rating.Easy, this.easy.getScheduledDays(), card.getElapsedDays(), now, card.getState())
//        ));
//
//        return log;
//    }
//}
//
