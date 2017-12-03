package com.characterBuilder.aerase;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.Test;


public class TestGen
{
	BasicDataSource dataSource;
	
	@Before
	public void setup() {
		dataSource = new BasicDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres2112");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/PubHub");
		dataSource.setMaxIdle(10);
		dataSource.setMaxIdle(5);
		dataSource.setInitialSize(5);
		dataSource.setValidationQuery("SELECT 1");
	}
	@Test
	public void testSpringAndJdbc() throws SQLException {
		
//		ResultSet rs = dataSource.getConnection().prepareStatement("Select isbn_13, title, author from books").executeQuery();

//		boolean b = rs.next();
//		
//		String author = rs.getString("author");
		
//		System.out.println(new JDBCBookDAO(dataSource).getAllBooks());
		
//		assert(jsc != null);
//		System.out.println(jsc.getResults("SELECT * FROM BOOKS"));
//		
//		JDBCBookDAO bdaoi = new JDBCBookDAO();
//		System.out.println(new JDBCProviderServiceLocationDAO(null));
	}
}
