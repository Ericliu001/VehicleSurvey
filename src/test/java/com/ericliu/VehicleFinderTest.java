package com.ericliu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ericliu on 28/06/2016.
 */
public class VehicleFinderTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSpeed(){
        double speedA = VehicleFinder.INSTANCE.getAverageSpeed(DataParser.SENSOR_A, 0, 0, Integer.MAX_VALUE);
        assertTrue(speedA > 0);


        double speedB = VehicleFinder.INSTANCE.getAverageSpeed(DataParser.SENSOR_B, 0, 0, Integer.MAX_VALUE);
        assertTrue(speedB > 0);

    }

    @Test
    public void getVehicleRecords() throws Exception {
        List<Interval> vehicleRecordsA = VehicleFinder.INSTANCE.getVehicleRecords(DataParser.SENSOR_A, 0, 0, Integer.MAX_VALUE);
        List<Interval> vehicleRecordsB = VehicleFinder.INSTANCE.getVehicleRecords(DataParser.SENSOR_B, 0, 0, Integer.MAX_VALUE);

        assertNotNull(vehicleRecordsA);
        assertTrue(vehicleRecordsB.size() > 0);
    }

    @Test
    public void binarySearch() throws Exception {
        List<Interval> intervalList = new ArrayList<>();
        intervalList.add(new Interval(9, 23));
        intervalList.add(new Interval(11, 23));
        intervalList.add(new Interval(20, 23));
        intervalList.add(new Interval(33, 23));
        intervalList.add(new Interval(54, 23));


        int index1 = VehicleFinder.INSTANCE.findUpperBound(intervalList, 10, 0, intervalList.size() - 1);
        assertEquals(1, index1);


        int index2 = VehicleFinder.INSTANCE.findUpperBound(intervalList, 19, 0, intervalList.size() - 1);
        assertEquals(2, index2);

        int index3 = VehicleFinder.INSTANCE.findUpperBound(intervalList, 33, 0, intervalList.size() - 1);
        assertEquals(3, index3);

        int index4 = VehicleFinder.INSTANCE.findUpperBound(intervalList, 34, 0, intervalList.size() - 1);
        assertEquals(4, index4);


        int index5 = VehicleFinder.INSTANCE.findUpperBound(intervalList, 55, 0, intervalList.size() - 1);
        assertEquals(4, index5);


        int index6 = VehicleFinder.INSTANCE.findLowerBound(intervalList, 10, 0, intervalList.size() - 1);
        assertEquals(0, index6);
    }

    @Test
    public void createIntervalMap() throws Exception {

    }

    @Test
    public void isVehiclePass() throws Exception {

    }

}