package com.dataAccess.dao.jdbc;

import com.dataAccess.bean.AccreditationAproximation;
import com.dataAccess.bean.Field;
import com.dataAccess.bean.Parameter;
import com.dataAccess.dao.AccreditationAproximationDAO;
import com.dataAccess.dao.GenerateDaoImplAbs;
import com.dataAccess.map.impl.AccreditationAproximationDAOMap;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"unchecked", "rawtypes"})
public class JDBCAccreditationAproximationDAO extends GenerateDaoImplAbs implements AccreditationAproximationDAO {
  @Autowired
  public JDBCAccreditationAproximationDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<AccreditationAproximation> getGarbTastic(String partyPooper, String billVermin) {
    Parameter[] parameters = new Parameter[2];
    parameters[0] = new Parameter("PARTY_POOPER", "partyPooper", "java.lang.String", "string");
    parameters[1] = new Parameter("BILL_VERMIN", "billVermin", "java.lang.String", "string");
    List<Map<String, Object>> results = (List)exicuteQuery("getGarbTastic", parameters);
    return new AccreditationAproximationDAOMap().mapResults(results, "TODO: add field");
  }

  public List<AccreditationAproximation> getStuffedBunnies(String downHawkBlack, String skittles) {
    Parameter[] parameters = new Parameter[2];
    parameters[0] = new Parameter("DOWN_HAWK_BLACK", "downHawkBlack", "java.lang.String", "string");
    parameters[1] = new Parameter("SKITTLES", "skittles", "java.lang.String", "string");
    List<Map<String, Object>> results = (List)exicuteQuery("getStuffedBunnies", parameters);
    return new AccreditationAproximationDAOMap().mapResults(results, "TODO: add field");
  }

  private void build() {
    Field field;
    Parameter param;
    this.addQuery("getGarbTastic", "Select HELICOPTER, DOWN_HAWK_BLACK, BRUCE_WILLIS, SPAGGETIOS \n"
            + "from darkDungion \n"
            + "where $$BILL_VERMIN$$ = BILL_VERMIN \n"
            + "AND $$PARTY_POOPER$$ = PARTY_POOPER");
    param = new Parameter("PARTY_POOPER", "partyPooper", "java.lang.String", "string");
    this.addParameter("getGarbTastic", param);
    param = new Parameter("BILL_VERMIN", "billVermin", "java.lang.String", "string");
    this.addParameter("getGarbTastic", param);
    field = new Field("SPAGGETIOS", "spaggetios", "java.lang.String", null);
    this.addField("getGarbTastic", field);
    field = new Field("BRUCE_WILLIS", "bruceWillis", "java.lang.String", null);
    this.addField("getGarbTastic", field);
    field = new Field("DOWN_HAWK_BLACK", "downHawkBlack", "java.lang.String", null);
    this.addField("getGarbTastic", field);
    field = new Field("HELICOPTER", "helicopter", "java.lang.String", null);
    this.addField("getGarbTastic", field);
    this.addQuery("getStuffedBunnies", "Select HELICOPTER, DOWN_HAWK_BLACK, BRUCE_WILLIS, SPAGGETIOS \n"
            + "from darkDungion \n"
            + "where $$DOWN_HAWK_BLACK$$ = DOWN_HAWK_BLACK \n"
            + "AND $$SKITTLES$$ = SKITTLES");
    param = new Parameter("DOWN_HAWK_BLACK", "downHawkBlack", "java.lang.String", "string");
    this.addParameter("getStuffedBunnies", param);
    param = new Parameter("SKITTLES", "skittles", "java.lang.String", "string");
    this.addParameter("getStuffedBunnies", param);
    field = new Field("HELICOPTER", "helicopter", "java.lang.String", null);
    this.addField("getStuffedBunnies", field);
    field = new Field("BRUCE_WILLIS", "bruceWillis", "java.lang.String", "\"hello world!\"");
    this.addField("getStuffedBunnies", field);
    field = new Field("DOWN_HAWK_BLACK", "downHawkBlack", "java.lang.Long", null);
    this.addField("getStuffedBunnies", field);
    field = new Field("SPAGGETIOS", "spaggetios", "java.lang.String", null);
    this.addField("getStuffedBunnies", field);
  }
}
