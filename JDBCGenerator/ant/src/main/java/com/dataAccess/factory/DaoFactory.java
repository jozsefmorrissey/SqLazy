package com.dataAccess.factory;

import com.dataAccess.dao.BookDAO;
import com.dataAccess.dao.jdbc.JDBCBookDAO;
import com.dataAccess.dao.jdbc.JDBCuser_infoDAO;
import com.dataAccess.dao.user_infoDAO;

public class DaoFactory {
  private DataSourceFactory dataSourceFactory = new DataSourceFactory();

  private BookDAO BookDAO = new JDBCBookDAO(dataSourceFactory.getJdbcSuperClass());

  private user_infoDAO user_infoDAO = new JDBCuser_infoDAO(dataSourceFactory.getJdbcSuperClass());

  public BookDAO getBookDAO() {
    return BookDAO;
  }

  public void setBookDAO(BookDAO BookDAO) {
    this.BookDAO = BookDAO;
  }

  public user_infoDAO getUser_infoDAO() {
    return user_infoDAO;
  }

  public void setUser_infoDAO(user_infoDAO user_infoDAO) {
    this.user_infoDAO = user_infoDAO;
  }
}
