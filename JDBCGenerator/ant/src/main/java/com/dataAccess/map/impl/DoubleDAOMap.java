package com.dataAccess.map.impl;

import com.dataAccess.map.DAOMap;
import com.dataAccess.map.DAOMapAbs;
import com.generate.marker.ObjectMap;
import java.lang.Double;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

@ObjectMap
public class DoubleDAOMap extends DAOMapAbs<Double> implements DAOMap<Double> {
  public Double mapRow(Map<String, Object> results, String sqlVarName) {
    try{return Double.parseDouble(sqlVarName);
    }catch(NumberFormatException e){/*TODO: log*/};
    return Double.MIN_VALUE;
  }
}
