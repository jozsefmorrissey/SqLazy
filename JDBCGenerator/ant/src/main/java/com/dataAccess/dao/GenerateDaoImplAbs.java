package com.dataAccess.dao;

import com.generate.Util.StringUtil;
import com.generate.enums.SQL_TYPE;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Parameter;
import com.generate.parce.bean.Query;
import java.lang.Object;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class GenerateDaoImplAbs {
  private Log log = LogFactory.getLog(GenerateDaoImplAbs.class);

  @Autowired
  private AbstractMessageSource messageSource;

  private Map<String, Query> queries;

  private JdbcTemplate jdbcTemplate;

  @Autowired
  protected GenerateDaoImplAbs(DataSource dseDataSource) {
    jdbcTemplate = new JdbcTemplate(dseDataSource);
    queries = new HashMap<String, Query>();
  }

  public Log getLog() {
    return log;
  }

  public void setLog(Log log) {
    this.log = log;
  }

  public AbstractMessageSource getMessageSource() {
    return messageSource;
  }

  public void setMessageSource(AbstractMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  protected void addQuery(String name, String queryFormat) {
    Query q = new Query(queryFormat);
    ((Map<String, Query>)queries).put(name, q);
  }

  protected void addParameter(String queryName, Parameter param) {
    if(((Map<String, Query>)queries).containsKey(queryName))
        ((Map<String, Query>)queries).get(queryName).getParameters().add(param);
  }

  protected void addField(String queryName, Field fieldName) {
    if(((Map<String, Query>)queries).containsKey(queryName))
        ((Map<String, Query>)queries).get(queryName).getFields().add(fieldName);
  }

  protected void setParameterValue(String queryName, Parameter paramName, Object value) {
    if(((Map<String, Query>)queries).containsKey(queryName))
        for(Parameter s : ((Map<String, Query>)queries).get(queryName).getParameters())
        if(s.getSqlVarName().equals(paramName))
        s.setValue(value);
  }

  protected String extractResult(Map<String, Object> results, Field field) {
    Object resultObj = results.get(field);
    if(resultObj != null)
        return resultObj.toString();
    return null;
  }

  protected List<Map<String, Object>> exicuteQuery(String queryName, Parameter[] parameters) {
    long startJavaLogTime = Calendar.getInstance().getTimeInMillis();
    String query = buildQuery(queryName, parameters);
    List<Map<String, Object>> rows = (List) jdbcTemplate.queryForList(query);
    logRequestTimer(startJavaLogTime, query);
    return rows;
  }

  private List<Map<String, Object>> mockData(String name) {
    Query q = (Query) queries.get(name);
    List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
    StringBuilder[] words = new StringBuilder[]{new StringBuilder("First"), new StringBuilder("Second"), new StringBuilder("Third")};
    for(int i = 0; i < 3; i++)
        mockDataRowBuilder(results, q, words[i]);
    return results;
  }

  protected List<Map<String, Object>> exicuteMockQuery(String queryName, Parameter[] parameters) {
    return mockData(queryName);
  }

  private String makeParameter(String queryStr, Parameter param) {
    String varStr = StringUtil.getVarString(param.getSqlType());
    String valueStr = SQL_TYPE.prepValue(param.getSqlType(), param.getValue());
    queryStr = queryStr.replace(varStr, valueStr);
    return queryStr;
  }

  protected String buildQuery(String queryName, Parameter[] parameters) {
    Query q = (Query) queries.get(queryName);
    String queryStr = q.getFormat();
    for(Parameter param : parameters)
        queryStr = makeParameter(queryStr, param);
    System.out.println("Query: " + queryStr);
    return queryStr;
  }

  protected int getRowCount(String queryName, Parameter[] parameters) {
    return exicuteQuery(queryName, parameters).size();
  }

  protected void logRequestTimer(long startJavaLogTime, String query) {
  }

  private Map mockDataRowBuilder(List results, Query q, StringBuilder word) {
    Map row = new HashMap();
    for(Field field : q.getFields())
        mockDataAddWord(row, word, field.getSqlVarName());
    results.add(row);
    return row;
  }

  protected void mockDataAddWord(Map row, StringBuilder word, String field) {
    row.put(field, word);
    word.append(word);
  }
}
