package com.dataAccess.dao.jdbc;

import com.dataAccess.bean.user_info;
import com.dataAccess.dao.GenerateDaoImplAbs;
import com.dataAccess.dao.user_infoDAO;
import com.dataAccess.map.impl.user_infoDAOMap;
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
public class JDBCuser_infoDAO extends GenerateDaoImplAbs implements user_infoDAO {
  @Autowired
  public JDBCuser_infoDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<user_info> getUserByEmail(String email) {
    Parameter[] parameters = new Parameter[1];
    parameters[0] = new Parameter("email", null, null, null);
    List<Map<String, Object>> results = (List)exicuteQuery("getUserByEmail", parameters);
    return new user_infoDAOMap().mapResults(results, "TODO: add field");
  }

  private void build() {
    Field field;
    Parameter param;
    this.addQuery("getUserByEmail", "Select email, password, name \n"
            + "from user_info \n"
            + "where $$email$$ = email \n"
            + "\n"
            + "name\n"
            + "password\n"
            + "email");
    param = new Parameter("email", null, null, null);
    this.addParameter("getUserByEmail", param);
    field = new Field("NAME", null, null, null);
    this.addField("getUserByEmail", field);
    field = new Field("PASSWORD", null, null, null);
    this.addField("getUserByEmail", field);
    field = new Field("EMAIL", null, null, null);
    this.addField("getUserByEmail", field);
  }
}
