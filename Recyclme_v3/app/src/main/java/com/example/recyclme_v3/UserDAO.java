package com.example.recyclme_v3;

public class UserDAO {
    private int timesRecycled;
    private float weightRecycled;

    public UserDAO(int timesRecycled, float weightRecycled) {
        this.timesRecycled = timesRecycled;
        this.weightRecycled = weightRecycled;
    }

    public int getTimesRecycled() {
        return timesRecycled;
    }

    public void setTimesRecycled(int timesRecycled) {
        this.timesRecycled = timesRecycled;
    }

    public float getWeightRecycled() {
        return weightRecycled;
    }

    public void setWeightRecycled(float weightRecycled) {
        this.weightRecycled = weightRecycled;
    }
}