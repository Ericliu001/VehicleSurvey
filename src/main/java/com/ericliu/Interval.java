package com.ericliu;

/**
 * Created by ericliu on 28/06/2016.
 */

/**
 * The Interval between two sensor registers, with the mid time being the middle time between the first register and the second register
 * duration is the period between the first register and the second register
 */
public class Interval {
    public final long midTime;
    public final long duration;
    
    public Interval(long midTime, long duration){

        this.midTime = midTime;
        this.duration = duration;
    }
}
