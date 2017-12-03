package com.dataAccess.map;

import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DAOMapAbs<T> implements DAOMap<T> {
  public List<T> mapResults(List<Map<String, Object>> results, String sqlVarName) {
    List<T> list = new ArrayList<T>();
    for(Map<String, Object> row : results)
        list.add(mapRow(row, sqlVarName));
    return list;
  }
}
