package com.ericliu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ericliu on 28/06/2016.
 */

public enum VehicleFinder {
    INSTANCE;

    public static final double VEHICLE_WHEEL_BASE_LENGTH = 2.5;
    public static final double SPEED = 60; // km/h
    public static final double AVG_VEHICLE_PASSING_TIME_INTERVAL = VEHICLE_WHEEL_BASE_LENGTH / (SPEED * 1000 / (3600 * 1000));

    public Map<String, List<List<Interval>>> mVehicleRecordsMap;


    public List<Interval> getVehicleRecords(String sensorName, int day, long startTime, long endTime) {
        Map<String, List<List<Interval>>> map = VehicleFinder.INSTANCE.createIntervalMap();
        List<List<Interval>> listList = map.get(sensorName);

        if (day < 0 || day >= listList.size()) {
            return null;
        }
        List<Interval> intervalList = listList.get(day);
        List<Interval> subList = findSubList(intervalList, startTime, endTime);

        return new ArrayList<>(subList); // clone the list
    }

    private List<Interval> findSubList(List<Interval> intervalList, long startTime, long endTime) {
        int startIndex = findUpperBound(intervalList, startTime, 0, intervalList.size() - 1);
        int endIndex = findLowerBound(intervalList, endTime, 0, intervalList.size() - 1);
        return intervalList.subList(startIndex, endIndex);
    }




    public int findUpperBound(List<Interval> intervalList, long target, int low, int high) {
        int mid;

        while (high - low > 1) {
            mid = (low + high) / 2;
            long midTime = intervalList.get(mid).midTime;
            if (midTime > target) {
                high = mid;
            } else if (midTime < target) {
                low = mid;
            } else {
                return mid;
            }
        }

        return  high;
    }

    public int findLowerBound(List<Interval> intervalList, long target, int low, int high) {
        int mid;

        while (high - low > 1) {
            mid = (low + high) / 2;
            long midTime = intervalList.get(mid).midTime;
            if (midTime > target) {
                high = mid;
            } else if (midTime < target) {
                low = mid;
            } else {
                return mid;
            }
        }

        return  low;
    }


    public Map<String, List<List<Interval>>> createIntervalMap() {
        if (mVehicleRecordsMap != null) {
            return mVehicleRecordsMap;
        }

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

        mVehicleRecordsMap = intervalMap;
        return mVehicleRecordsMap;
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
