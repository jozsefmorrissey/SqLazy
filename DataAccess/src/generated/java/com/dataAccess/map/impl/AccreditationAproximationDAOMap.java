package com.dataAccess.map.impl;

import com.dataAccess.bean.AccreditationAproximation;
import com.dataAccess.factory.MapFactory;
import com.dataAccess.map.DAOMap;
import com.dataAccess.map.DAOMapAbs;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

public class AccreditationAproximationDAOMap extends DAOMapAbs<AccreditationAproximation> implements DAOMap<AccreditationAproximation> {
  MapFactory mapFactory = new MapFactory();

  public AccreditationAproximation mapRow(Map<String, Object> row, String sqlVarName) {
    AccreditationAproximation bean = new AccreditationAproximation();
    bean.setHelicopter(mapFactory.getStringDAOMap().mapRow(row, "HELICOPTER"));
    bean.setBruceWillis(mapFactory.getStringDAOMap().mapRow(row, "BRUCE_WILLIS"));
    bean.setDownHawkBlack(mapFactory.getLongDAOMap().mapRow(row, "DOWN_HAWK_BLACK"));
    bean.setSpaggetios(mapFactory.getStringDAOMap().mapRow(row, "SPAGGETIOS"));
    return bean;
  }
}
