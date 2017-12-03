package com.dataAccess.factory;

import com.dataAccess.map.impl.BookDAOMap;
import com.dataAccess.map.impl.DoubleDAOMap;
import com.dataAccess.map.impl.LongDAOMap;
import com.dataAccess.map.impl.StringDAOMap;
import com.dataAccess.map.impl.user_infoDAOMap;

public class MapFactory {
  private StringDAOMap StringDAOMap = new StringDAOMap();

  private DoubleDAOMap DoubleDAOMap = new DoubleDAOMap();

  private BookDAOMap BookDAOMap = new BookDAOMap();

  private LongDAOMap LongDAOMap = new LongDAOMap();

  private user_infoDAOMap user_infoDAOMap = new user_infoDAOMap();

  public StringDAOMap getStringDAOMap() {
    return StringDAOMap;
  }

  public void setStringDAOMap(StringDAOMap StringDAOMap) {
    this.StringDAOMap = StringDAOMap;
  }

  public DoubleDAOMap getDoubleDAOMap() {
    return DoubleDAOMap;
  }

  public void setDoubleDAOMap(DoubleDAOMap DoubleDAOMap) {
    this.DoubleDAOMap = DoubleDAOMap;
  }

  public BookDAOMap getBookDAOMap() {
    return BookDAOMap;
  }

  public void setBookDAOMap(BookDAOMap BookDAOMap) {
    this.BookDAOMap = BookDAOMap;
  }

  public LongDAOMap getLongDAOMap() {
    return LongDAOMap;
  }

  public void setLongDAOMap(LongDAOMap LongDAOMap) {
    this.LongDAOMap = LongDAOMap;
  }

  public user_infoDAOMap getUser_infoDAOMap() {
    return user_infoDAOMap;
  }

  public void setUser_infoDAOMap(user_infoDAOMap user_infoDAOMap) {
    this.user_infoDAOMap = user_infoDAOMap;
  }
}
