package com.ericliu;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ericliu on 27/06/2016.
 */

public class DataParser {

    public static final String SENSOR_A = "A";
    public static final String SENSOR_B = "B";
    public static Map<String, List<String>> mDataMap;

    public static final String FILENAME = "Vehicle Survey Coding Challenge sample data.txt";
    public static final String REGEX_A = SENSOR_A + "{1}\\d*";
    public static final String REGEX_B = SENSOR_B + "{1}\\d*";

    private DataParser() {
    }


    public static Map<String, List<List<Long>>> getCountByDay() {
        Map<String, List<List<Long>>> countByDay = new HashMap<>();
        Map<String, List<String>> rawMap = getRawDataListMap(false);


        for (String key : rawMap.keySet()) {
            List<String> rawList = rawMap.get(key);
            List<List<String>> listsByDayString = createListsByDay(rawList);
            List<List<Long>> listsByDayLong = new ArrayList<>();
            for (List<String> stringList : listsByDayString) {
                List<Long> longArrayList = new ArrayList<>();
                for (String s : stringList) {
                    longArrayList.add(Long.valueOf(s));
                }
                listsByDayLong.add(longArrayList);
            }
            countByDay.put(key, listsByDayLong);
        }

        return countByDay;

    }

    private static List<List<String>> createListsByDay(List<String> rawList) {
        List<List<String>> listsByDayString = new ArrayList<>();
        List<String> temp;
        int start = 0;
        long currentValue = 0;
        long nextValue = 0;
        for (int i = 0; (i + 1) < rawList.size(); i++) {
            currentValue = Long.valueOf(rawList.get(i));
            nextValue = Long.valueOf(rawList.get(i + 1));

            if (currentValue > nextValue) {
                temp = rawList.subList(start, i);
                start = i + 1;
                listsByDayString.add(temp);
            }

            if ((i + 1) == rawList.size() - 1) {
                temp = rawList.subList(start, i + 1);
                listsByDayString.add(temp);
            }
        }

        return listsByDayString;
    }

    public static Map<String, List<String>> getRawDataListMap(boolean forceUpdate) {
        if (mDataMap != null && !forceUpdate) {
            // if the cached data is not null, we just return it to save the cost of reading and parsing file.
            return mDataMap;
        }

        Map<String, List<String>> dataMap = new HashMap<>();
        try {
            String raw = null;
            raw = readFile(DataParser.FILENAME);

            String[] splitStr = raw.split("\\n");

            List<String> alist = new ArrayList<>();
            List<String> blist = new ArrayList<>();


            for (String s : splitStr) {
                if (s.matches(REGEX_A)) {
                    s = s.replace("A", "");
                    alist.add(s);
                } else if (s.matches(REGEX_B)) {
                    s = s.replace("B", "");
                    blist.add(s);
                }
            }

            dataMap.put(SENSOR_A, alist);
            dataMap.put(SENSOR_B, blist);

            // store the data if any of the list has a size that's bigger than 0.
            if (alist.size() > 0 || blist.size() > 0) {
                mDataMap = dataMap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }


    public static String readFile(String filename) throws IOException {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return content;
    }
}
