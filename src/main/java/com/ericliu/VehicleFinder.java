package com.ericliu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by L078865 on 28/06/2016.
 */

public enum VehicleFinder {
    INSTANCE;

    public static final double VEHICLE_WHEEL_BASE_LENGTH = 2.5;
    public static final double SPEED = 60; // km/h
    public static final double AVG_VEHICLE_PASSING_TIME_INTERVAL = VEHICLE_WHEEL_BASE_LENGTH / (SPEED * 1000 / (3600 * 1000));


    public Map<String, List<List<Interval>>> createIntervalMap() {

        Map<String, List<List<Long>>> longMap = DataParser.getCountByDay();
        Map<String, List<List<Interval>>> intervalMap = new HashMap<>();

        Interval interval = null;
        long midValue = 0L;
        long duration = 0L;
        List<Interval> intervalList;
        List<List<Interval>> listsByDay;

        for (String key : longMap.keySet()) {
            List<List<Long>> listList = longMap.get(key);
            listsByDay = new ArrayList<>();

            for (List<Long> longList : listList) {
                intervalList = new ArrayList<>();
                for (int i = 0; (i + 1) < longList.size(); i++) {
                    midValue = (longList.get(i) + longList.get(i + 1)) / 2;
                    duration = longList.get(i + 1) - longList.get(i);
                    if (isVehiclePass(duration)) {
                        interval = new Interval(midValue, duration);
                        intervalList.add(interval);
                    }
                }
                listsByDay.add(intervalList);
            }

            intervalMap.put(key, listsByDay);
        }
        return intervalMap;
    }

    


    public boolean isVehiclePass(long duration) {
        // ignore vehicles going under 15km/h (60km/h * (1/4))) and going over 180km/h (60km/h * 3)
        if (duration > 4 * AVG_VEHICLE_PASSING_TIME_INTERVAL) {
            return false;
        } else if (duration < (AVG_VEHICLE_PASSING_TIME_INTERVAL / 3)) {
            return false;
        } else {
            return true;
        }

    }

}
