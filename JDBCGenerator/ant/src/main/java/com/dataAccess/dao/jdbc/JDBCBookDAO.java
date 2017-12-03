package com.dataAccess.dao.jdbc;

import com.dataAccess.bean.Book;
import com.dataAccess.dao.BookDAO;
import com.dataAccess.dao.GenerateDaoImplAbs;
import com.dataAccess.map.impl.BookDAOMap;
import com.generate.marker.JDBC;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Parameter;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

@JDBC
@SuppressWarnings({"unchecked", "rawtypes"})
public class JDBCBookDAO extends GenerateDaoImplAbs implements BookDAO {
  @Autowired
  public JDBCBookDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<Book> getAllBooks() {
    Parameter[] parameters = new Parameter[0];
    List<Map<String, Object>> results = (List)exicuteQuery("getAllBooks", parameters);
    return new BookDAOMap().mapResults(results, "TODO: add field");
  }

  public List<Book> getBooksByRussell(String author) {
    Parameter[] parameters = new Parameter[1];
    parameters[0] = new Parameter("author", null, null, null);
    List<Map<String, Object>> results = (List)exicuteQuery("getBooksByRussell", parameters);
    return new BookDAOMap().mapResults(results, "TODO: add field");
  }

  private void build() {
    Field field;
    Parameter param;
    this.addQuery("getAllBooks", "Select isbn_13, title, author \n"
            + "from books");
    field = new Field("TITLE", null, null, null);
    this.addField("getAllBooks", field);
    field = new Field("AUTHOR", null, null, null);
    this.addField("getAllBooks", field);
    field = new Field("ISBN_13", null, null, null);
    this.addField("getAllBooks", field);
    this.addQuery("getBooksByRussell", "Select publish_date, price, isbn_13 \n"
            + "from books \n"
            + "where $$author$$ = author");
    param = new Parameter("author", null, null, null);
    this.addParameter("getBooksByRussell", param);
    field = new Field("ISBN_13", null, null, null);
    this.addField("getBooksByRussell", field);
    field = new Field("PUBLISH_DATE", null, null, null);
    this.addField("getBooksByRussell", field);
    field = new Field("PRICE", null, null, null);
    this.addField("getBooksByRussell", field);
  }
}
