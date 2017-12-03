package com.dataAccess.factory;

import com.dataAccess.dataSource.JdbcSuperClass;

public class DataSourceFactory {
  private JdbcSuperClass JdbcSuperClass = new JdbcSuperClass();

  public JdbcSuperClass getJdbcSuperClass() {
    return JdbcSuperClass;
  }

  public void setJdbcSuperClass(JdbcSuperClass JdbcSuperClass) {
    this.JdbcSuperClass = JdbcSuperClass;
  }
}
