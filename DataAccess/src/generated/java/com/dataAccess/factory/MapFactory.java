package com.dataAccess.factory;

import com.dataAccess.map.impl.AccreditationAproximationDAOMap;
import com.dataAccess.map.impl.DoubleDAOMap;
import com.dataAccess.map.impl.LongDAOMap;
import com.dataAccess.map.impl.ProviderServiceLocationDAOMap;
import com.dataAccess.map.impl.StringDAOMap;

public class MapFactory {
  private StringDAOMap StringDAOMap = new StringDAOMap();

  private LongDAOMap LongDAOMap = new LongDAOMap();

  private ProviderServiceLocationDAOMap ProviderServiceLocationDAOMap = new ProviderServiceLocationDAOMap();

  private AccreditationAproximationDAOMap AccreditationAproximationDAOMap = new AccreditationAproximationDAOMap();

  private DoubleDAOMap DoubleDAOMap = new DoubleDAOMap();

  public StringDAOMap getStringDAOMap() {
    return StringDAOMap;
  }

  public void setStringDAOMap(StringDAOMap StringDAOMap) {
    this.StringDAOMap = StringDAOMap;
  }

  public LongDAOMap getLongDAOMap() {
    return LongDAOMap;
  }

  public void setLongDAOMap(LongDAOMap LongDAOMap) {
    this.LongDAOMap = LongDAOMap;
  }

  public ProviderServiceLocationDAOMap getProviderServiceLocationDAOMap() {
    return ProviderServiceLocationDAOMap;
  }

  public void setProviderServiceLocationDAOMap(ProviderServiceLocationDAOMap ProviderServiceLocationDAOMap) {
    this.ProviderServiceLocationDAOMap = ProviderServiceLocationDAOMap;
  }

  public AccreditationAproximationDAOMap getAccreditationAproximationDAOMap() {
    return AccreditationAproximationDAOMap;
  }

  public void setAccreditationAproximationDAOMap(AccreditationAproximationDAOMap AccreditationAproximationDAOMap) {
    this.AccreditationAproximationDAOMap = AccreditationAproximationDAOMap;
  }

  public DoubleDAOMap getDoubleDAOMap() {
    return DoubleDAOMap;
  }

  public void setDoubleDAOMap(DoubleDAOMap DoubleDAOMap) {
    this.DoubleDAOMap = DoubleDAOMap;
  }
}
