package com.ericliu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by L078865 on 27/06/2016.
 */
public class DataParserTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void readFile() throws Exception {
        Map<String, List<String>> dataListMap = DataParser.getRawDataListMap(false); 
        
        assertNotNull("file parsing failed.", dataListMap.values());
        assertTrue(dataListMap.keySet().size() > 0);
    }
    
    @Test
    public void getListsByDay(){
        
        DataParser.getCountByDay();
    }
    
    @Test
    public void getIntervalMap(){
        Map map = VehicleFinder.INSTANCE.createIntervalMap();
        
        assertNotNull(map);
    }

}