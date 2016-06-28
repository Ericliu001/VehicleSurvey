package com.ericliu;

import java.util.List;
import java.util.Map;

/**
 * Created by L078865 on 28/06/2016.
 */

public enum VehicleFinder {
    INSTANCE;
    
    
    public void createIntervalLists(){

        Map<String, List<List<Long>>>  map = DataParser.getCountByDay();
        
        
    }
    
}
