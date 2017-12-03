package com.dataAccess.dataSource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.generate.marker.DataSourceObject;

@DataSourceObject
public class JdbcSuperClass implements DataSource
{
	public JdbcSuperClass() {
		
	}
	
	public List<Map<String, Object>> getResults(String query) {
		Connection c;
		try
		{
			c = getConnection();
			PreparedStatement stmt = c.prepareStatement(query);
			
			ResultSet rs = stmt.executeQuery();
			// parsing the column each time is a linear search
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount(); //number of column
			String columnName[] = new String[count];
			
			List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			while (rs.next()) {
				for (int i = 1; i <= count; i++)
				{
					System.out.println(i);
				    Object obj = rs.getObject(i);
				    map.put(columnName[i - 1], obj);
				}
				results.add(map);
			}
			return results;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException
	{
		// TODO Auto-generated method stub
		return DAOUtilities.getConnection();
	}

	@Override
	public Connection getConnection(String arg0, String arg1) throws SQLException
	{
		// TODO Auto-generated method stub
		return DAOUtilities.getConnection();
	}
}
