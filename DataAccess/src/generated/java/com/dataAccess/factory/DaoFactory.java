package com.dataAccess.factory;

import com.dataAccess.dao.AccreditationAproximationDAO;
import com.dataAccess.dao.ProviderServiceLocationDAO;
import com.dataAccess.dao.jdbc.JDBCAccreditationAproximationDAO;
import com.dataAccess.dao.jdbc.JDBCProviderServiceLocationDAO;

public class DaoFactory {
  private DataSourceFactory dataSourceFactory = new DataSourceFactory();

  private AccreditationAproximationDAO AccreditationAproximationDAO = new JDBCAccreditationAproximationDAO(dataSourceFactory.getJdbcSuperClass());

  private ProviderServiceLocationDAO ProviderServiceLocationDAO = new JDBCProviderServiceLocationDAO(dataSourceFactory.getJdbcSuperClass());

  public AccreditationAproximationDAO getAccreditationAproximationDAO() {
    return AccreditationAproximationDAO;
  }

  public void setAccreditationAproximationDAO(AccreditationAproximationDAO AccreditationAproximationDAO) {
    this.AccreditationAproximationDAO = AccreditationAproximationDAO;
  }

  public ProviderServiceLocationDAO getProviderServiceLocationDAO() {
    return ProviderServiceLocationDAO;
  }

  public void setProviderServiceLocationDAO(ProviderServiceLocationDAO ProviderServiceLocationDAO) {
    this.ProviderServiceLocationDAO = ProviderServiceLocationDAO;
  }
}
