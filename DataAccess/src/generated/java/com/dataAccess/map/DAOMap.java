package com.dataAccess.map;

import java.lang.Object;
import java.lang.String;
import java.util.List;
import java.util.Map;

public interface DAOMap<T> {
  T mapRow(Map<String, Object> row, String sqlVarName);

  List<T> mapResults(List<Map<String, Object>> results, String sqlVarName);
}
