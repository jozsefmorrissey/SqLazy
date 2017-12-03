package com.dataAccess.map.impl;

import com.dataAccess.map.DAOMap;
import com.dataAccess.map.DAOMapAbs;
import com.generate.marker.ObjectMap;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

@ObjectMap
public class StringDAOMap extends DAOMapAbs<String> implements DAOMap<String> {
  public String mapRow(Map<String, Object> results, String sqlVarName) {
    Object resultObj = results.get(sqlVarName);
    if(resultObj != null)
        return resultObj.toString();
    return null;
  }
}
