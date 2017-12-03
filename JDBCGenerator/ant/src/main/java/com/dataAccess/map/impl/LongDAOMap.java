package com.dataAccess.map.impl;

import com.dataAccess.map.DAOMap;
import com.dataAccess.map.DAOMapAbs;
import com.generate.marker.ObjectMap;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

@ObjectMap
public class LongDAOMap extends DAOMapAbs<Long> implements DAOMap<Long> {
  public Long mapRow(Map<String, Object> results, String sqlVarName) {
    try{return Long.parseLong(sqlVarName);
    }catch(NumberFormatException e){/*TODO: log*/};
    return Long.MIN_VALUE;
  }
}
