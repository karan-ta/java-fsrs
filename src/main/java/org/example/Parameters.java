package org.example;

import java.util.Arrays;

public class Parameters {
    float requestRetention;
    int maximumInterval;
    float[] w;

    @Override
    public String toString() {
        return requestRetention + "\n" + maximumInterval + "\n" + Arrays.toString(w) ;
    }

    public Parameters(float[] w , Float requestRetention, Integer maximumInterval){
        if(w != null){
            this.w = w ;
        }
        else{
            this.w = new float[]{
                    0.4872f,
                    1.4003f,
                    3.7145f,
                    13.8206f,
                    5.1618f,
                    1.2298f,
                    0.8975f,
                    0.031f,
                    1.6474f,
                    0.1367f,
                    1.0461f,
                    2.1072f,
                    0.0793f,
                    0.3246f,
                    1.587f,
                    0.2272f,
                    2.8755f
            };
            if(requestRetention != null){
                this.requestRetention = requestRetention;
            }
            else{
                this.requestRetention = 0.9f;
            }

            if(maximumInterval != null){
                this.maximumInterval = maximumInterval;
            }
            else{
                this.maximumInterval = 36500;
            }


        }

    }

    public Parameters(){
        this(null,null,null);
    }
}
