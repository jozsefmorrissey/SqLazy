package com.dataAccess.map.impl;

import com.dataAccess.bean.user_info;
import com.dataAccess.factory.MapFactory;
import com.dataAccess.map.DAOMap;
import com.dataAccess.map.DAOMapAbs;
import com.generate.marker.ObjectMap;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

@ObjectMap
public class user_infoDAOMap extends DAOMapAbs<user_info> implements DAOMap<user_info> {
  MapFactory mapFactory = new MapFactory();

  public user_info mapRow(Map<String, Object> row, String sqlVarName) {
    user_info bean = new user_info();
    bean.setName(mapFactory.getStringDAOMap().mapRow(row, "NAME"));
    bean.setPassword(mapFactory.getStringDAOMap().mapRow(row, "PASSWORD"));
    bean.setEmail(mapFactory.getStringDAOMap().mapRow(row, "EMAIL"));
    return bean;
  }
}
