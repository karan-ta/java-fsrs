package org.example;
import static org.example.Fsrs.*;

public class Main {
    public static void main(String[] args) {
        final Card myNewCard = new Card(Grade.get("GOOD"));
        final int daysAfterReview = 2;
        final Card myNextCard = myNewCard.gradeCard(daysAfterReview,Grade.get("EASY"));
        final double nextReviewResult = myNextCard.cardNextInterval;
        System.out.println(myNextCard.cardDifficulty);
        System.out.println(myNextCard.cardStability);
        System.out.println("Card will be due for review in "+String.valueOf(nextReviewResult));

    }
}