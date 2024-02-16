package com.rewu.relics;

import java.sql.Time;

public class TimeUtils {
    
    public static long GetCurrentEpoch() {
        return System.currentTimeMillis() / 1000;
    }

    public static long GetElapsedSeconds(long epoch) {
        return GetCurrentEpoch() - epoch;
    }

}
