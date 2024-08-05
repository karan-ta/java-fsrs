package org.example;

import static org.example.Fsrs.*;

public class Card{
    float cardDifficulty;

    double cardStability;

    double cardNextInterval;

    float initialStability(int gradeNumber) {
        return WEIGHTS[gradeNumber - 1];
    }

    public Card(int grade){
        this.cardDifficulty = initialDifficulty(grade);
        this.cardStability = initialStability(grade);
        this.cardNextInterval = nextInterval(REQUESTED_RETENTION_RATE,this.cardStability);
    }
    public Card(float currentDifficulty, double currentStability, double currentNextInterval) {
        this.cardDifficulty = currentDifficulty;
        this.cardStability = currentStability;
        this.cardNextInterval = currentNextInterval;

    }


    float initialDifficulty(int gradeNumber){
        final float calculatedValue = WEIGHTS[4] + (gradeNumber - 3) * WEIGHTS[5];
        return Math.max(1,Math.min(10, calculatedValue));
    }

    double retrievability(int daysAfterPrevReview, double stability){
        return Math.pow(1 + FACTOR * (daysAfterPrevReview / stability),DECAY);
    }

     double nextInterval(float requestedRetentionRate,double stability){
        System.out.println("--- inside next interval --- ");
         System.out.println("--- inside next interval --- "+requestedRetentionRate);
         System.out.println("--- inside next interval --- "+stability);
         System.out.println("--- inside next interval --- "+stability / FACTOR);
         System.out.println("--- inside next interval --- "+Math.pow(requestedRetentionRate,1/DECAY));
         System.out.println("--- inside next interval --- "+(stability / FACTOR) * (Math.pow(requestedRetentionRate,1/DECAY) - 1));


        return ((stability / FACTOR) * (Math.pow(requestedRetentionRate,1/DECAY) - 1)) ;

    }

    float nextDifficulty(float previousDifficulty,int currentGrade){
        final float calculatedValue = WEIGHTS[7] * initialDifficulty(3) + (1 - WEIGHTS[7]) * (previousDifficulty - WEIGHTS[6] * (currentGrade - 3));
        return Math.max(1,Math.min(10,calculatedValue));
    }

    double nextStabilityAfterRecall(
            float difficulty,
            double stability,
            double retreivability,
            int grade){
        final float hardPenalty = Grade.get("HARD") == grade ? WEIGHTS[15] : 1;
        final float easyBound = Grade.get("EASY") == grade ? WEIGHTS[16] : 1;
        return (
                stability *
                        (1 +
                            Math.exp(WEIGHTS[8]) *
                                    (11 - difficulty) *
                                     Math.pow(stability,-WEIGHTS[9]) *
                                    (Math.exp((1 - retreivability) * WEIGHTS[10]) - 1) *
                                    hardPenalty *
                                    easyBound)
                                );

    }

    double nextStabilityAfterForget(float difficulty, double stability, double retrievability){
        return (
                WEIGHTS[11] *
                        Math.pow(difficulty,-WEIGHTS[12]) *
                        (Math.pow(stability + 1,WEIGHTS[13]) - 1) *
                        Math.exp((1 - retrievability) * WEIGHTS[14])
                );
    }

     Card gradeCard(int daysAfterReview,int grade){
        final double currentRetreivability = retrievability(daysAfterReview,this.cardStability);
        final float currentDifficulty = nextDifficulty(this.cardDifficulty,grade);
        double currentStability ;
        if(grade == Grade.get("AGAIN")){
            currentStability = nextStabilityAfterForget(currentDifficulty, this.cardStability, currentRetreivability);
        }
        else{
            currentStability = nextStabilityAfterRecall(currentDifficulty, this.cardStability, currentRetreivability,grade);
        }
        double currentNextInterval = nextInterval(REQUESTED_RETENTION_RATE,currentStability);
        return new Card(currentDifficulty,currentStability,currentNextInterval);
    }
}