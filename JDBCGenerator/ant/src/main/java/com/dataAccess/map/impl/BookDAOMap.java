package com.dataAccess.map.impl;

import com.dataAccess.bean.Book;
import com.dataAccess.factory.MapFactory;
import com.dataAccess.map.DAOMap;
import com.dataAccess.map.DAOMapAbs;
import com.generate.marker.ObjectMap;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

@ObjectMap
public class BookDAOMap extends DAOMapAbs<Book> implements DAOMap<Book> {
  MapFactory mapFactory = new MapFactory();

  public Book mapRow(Map<String, Object> row, String sqlVarName) {
    Book bean = new Book();
    bean.setIsbn13(mapFactory.getStringDAOMap().mapRow(row, "ISBN_13"));
    bean.setPublishDate(mapFactory.getStringDAOMap().mapRow(row, "PUBLISH_DATE"));
    bean.setPrice(mapFactory.getStringDAOMap().mapRow(row, "PRICE"));
    bean.setTitle(mapFactory.getStringDAOMap().mapRow(row, "TITLE"));
    bean.setAuthor(mapFactory.getStringDAOMap().mapRow(row, "AUTHOR"));
    return bean;
  }
}
