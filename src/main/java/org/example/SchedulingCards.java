package org.example;

public class SchedulingCards {
    private Card again;
    private Card hard;
    private Card good;
    private Card easy;
    public SchedulingCards(Card card) {
        this.again = new Card(card); // Assuming Card has a copy constructor
        this.hard = new Card(card);
        this.good = new Card(card);
        this.easy = new Card(card);

    }

    public void updateState(State state) {
        if (state == State.New) {
            this.again.setState(State.Learning);
            this.hard.setState(State.Learning);
            this.good.setState(State.Learning);
            this.easy.setState(State.Review);
        } else if (state == State.Learning || state == State.Relearning) {
            this.again.setState(state);
            this.hard.setState(state);
            this.good.setState(State.Review);
            this.easy.setState(State.Review);
        } else if (state == State.Review) {
            this.again.setState(State.Relearning);
            this.hard.setState(State.Review);
            this.good.setState(State.Review);
            this.easy.setState(State.Review);
            this.again.lapses += 1; // Assuming there's a method to increment lapses
        }
    }
}
