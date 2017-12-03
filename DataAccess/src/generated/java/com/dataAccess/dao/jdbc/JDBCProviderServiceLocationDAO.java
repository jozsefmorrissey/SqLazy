package com.dataAccess.dao.jdbc;

import com.dataAccess.bean.Field;
import com.dataAccess.bean.Parameter;
import com.dataAccess.bean.ProviderServiceLocation;
import com.dataAccess.dao.GenerateDaoImplAbs;
import com.dataAccess.dao.ProviderServiceLocationDAO;
import com.dataAccess.map.impl.ProviderServiceLocationDAOMap;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"unchecked", "rawtypes"})
public class JDBCProviderServiceLocationDAO extends GenerateDaoImplAbs implements ProviderServiceLocationDAO {
  @Autowired
  public JDBCProviderServiceLocationDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<ProviderServiceLocation> getNonSense(String partyPooper, String billVermin) {
    Parameter[] parameters = new Parameter[2];
    parameters[0] = new Parameter("PARTY_POOPER", "partyPooper", "java.lang.String", "string");
    parameters[1] = new Parameter("BILL_VERMIN", "billVermin", "java.lang.String", "string");
    List<Map<String, Object>> results = (List)exicuteQuery("getNonSense", parameters);
    return new ProviderServiceLocationDAOMap().mapResults(results, "TODO: add field");
  }

  public List<ProviderServiceLocation> getEverything(String breadButter) {
    Parameter[] parameters = new Parameter[1];
    parameters[0] = new Parameter("BREAD_BUTTER", "breadButter", "java.lang.String", "string");
    List<Map<String, Object>> results = (List)exicuteQuery("getEverything", parameters);
    return new ProviderServiceLocationDAOMap().mapResults(results, "TODO: add field");
  }

  private void build() {
    Field field;
    Parameter param;
    this.addQuery("getNonSense", "Hello world!\n"
            + "Select HELP_WANTED, PARTY_POOPER, BILL_VERMIN \n"
            + "from diningRoom \n"
            + "where $$BILL_VERMIN$$ = BILL_VERMIN \n"
            + "AND $$PARTY_POOPER$$ = PARTY_POOPER");
    param = new Parameter("PARTY_POOPER", "partyPooper", "java.lang.String", "string");
    this.addParameter("getNonSense", param);
    param = new Parameter("BILL_VERMIN", "billVermin", "java.lang.String", "string");
    this.addParameter("getNonSense", param);
    field = new Field("PARTY_POOPER", "partyPooper", "java.lang.String", null);
    this.addField("getNonSense", field);
    field = new Field("HELP_WANTED", "hello", "java.lang.Double", "0.0");
    this.addField("getNonSense", field);
    field = new Field("BILL_VERMIN", "billVermin", "java.lang.String", null);
    this.addField("getNonSense", field);
    this.addQuery("getEverything", "Select HELP_WANTED, PARTY_POOPER, BILL_VERMIN \n"
            + "from diningRoom \n"
            + "where $$BREAD_BUTTER$$ = BREAD_BUTTER");
    param = new Parameter("BREAD_BUTTER", "breadButter", "java.lang.String", "string");
    this.addParameter("getEverything", param);
    field = new Field("HELP_WANTED", "helpWanted", "java.lang.String", null);
    this.addField("getEverything", field);
    field = new Field("PARTY_POOPER", "partyPooper", "java.lang.String", null);
    this.addField("getEverything", field);
    field = new Field("BILL_VERMIN", "billVermin", "java.lang.String", null);
    this.addField("getEverything", field);
  }
}
