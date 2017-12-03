package com.dataAccess.dataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Class used to retrieve DAO Implementations. Serves as a factory. Also manages a single instance of the database connection.
 */
public class DAOUtilities {
	private static final String PUBHUB_DB_NAME = "PubHub";
	private static final String PUBHUB_TEST_DB_NAME = "TestPubHub";
	
	private static final String POSTGRES_CONNECTION_USERNAME = "postgres";
	private static final String POSTGRES_CONNECTION_PASSWORD = "postgres2112";
	private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/";
	private static final String PUBHUB_URL = POSTGRES_URL + PUBHUB_DB_NAME;
	private static final String PUBHUB_TEST_URL = POSTGRES_URL + PUBHUB_TEST_DB_NAME;
	//Allows for test data base to be accessed.
	private static String testConnection = PUBHUB_URL;

	private static Connection connection;
	private static SessionFactory sessionFactory = null;
	
	public static synchronized void terminate(){
		closeResource(connection, "DataBase Connection");
		closeResource(sessionFactory, "Session Factory");
		System.out.println(sessionFactory.isClosed());
		
	}
	
	public static synchronized Session getSession(){
		
		if(sessionFactory == null || sessionFactory.isClosed()){
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

			try{
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			}
			catch (Exception e){
				System.out.println("Could not created connection!");
				e.printStackTrace();
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}

		return sessionFactory.openSession();
	}
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}

			return connection = DriverManager.getConnection(testConnection, 
									POSTGRES_CONNECTION_USERNAME, POSTGRES_CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			connection = DriverManager.getConnection(testConnection, 
							POSTGRES_CONNECTION_USERNAME, POSTGRES_CONNECTION_PASSWORD);
		}

		return connection;
	}
	
	// Closing all resources is important, to prevent memory leaks. 
	// Ideally, you really want to close them in the reverse-order you open them
	public static void closeResource(AutoCloseable c, String connectType) {
		try {
			if (c != null)
				c.close();
		} catch (Exception e) {
			System.out.println(connectType + " could not be closed.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		PostgresConnect();
		// Create PubHub dataBase.
		createDataBase(PUBHUB_DB_NAME);
		// Create TestPubHub dataBase.
		createDataBase(PUBHUB_TEST_DB_NAME);
		
		//Create columns and data.
		PubHubConnect();
		exicuteFileQuery("pubhub-mysetup.txt");
		

		PubHubTestConnect();
		exicuteFileQuery("pubhub-mysetup.txt");
		
			
	}

	/**
	 *  Creates a data base with a muti-case dbName.
	 */
	private static void createDataBase(String dbName) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DAOUtilities.getConnection();
			String sql = "CREATE DATABASE \"" + dbName + "\";"; // Were using a lot of ?'s here...
			stmt = connection.prepareStatement(sql);
			
			stmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeResource(connection, "Connection");
			closeResource(stmt, "PreparedStatement");
		}
	}
	
	/**
	 *  Exicutes sql code contained in fileName.
	 */
	@SuppressWarnings("resource")
	private static void exicuteFileQuery(String fileName){
		URL setupFile = ClassLoader.getSystemResource(fileName);
        
		String sql = null;
		try {
			sql = new Scanner(new File(setupFile.getFile())).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			connection = DAOUtilities.getConnection();
			stmt = connection.prepareStatement(sql);
			
			stmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			closeResource(connection, "Connection");
			closeResource(stmt, "PreparedStatement");
		}
	}
	
	public static void PubHubConnect(){
		testConnection = PUBHUB_URL;
	}
	
	public static void PubHubTestConnect(){
		testConnection = PUBHUB_TEST_URL;
	}
	
	public static void PostgresConnect(){
		testConnection = POSTGRES_URL;
	}
}
