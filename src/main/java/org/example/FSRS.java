package org.example;

public class FSRS {
    Parameters p;
    final float DECAY;
    final float FACTOR;

    @Override
    public String toString() {
        return ""+p.toString()+"\n" +DECAY+"\n"+FACTOR;
    }

    public FSRS(float [] w, Float requestRetention, Integer maximumInterval) {
        this.DECAY = -0.5f;
        this.FACTOR = (float)Math.pow(0.9, 1.0 / DECAY) - 1;
        this.p = new Parameters(w,requestRetention,maximumInterval);
        System.out.println(this);

    }
    public FSRS(){
        this(null,null,null);
    }
}
