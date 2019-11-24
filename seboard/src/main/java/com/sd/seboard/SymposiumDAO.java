package com.sd.seboard;

import java.sql.*;
import java.util.*;

import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;

public class SymposiumDAO {

	final static String DB_URL = "jdbc:oracle:thin:@kallolatpdme_medium?TNS_ADMIN=D:\\SD\\misc\\Symposium\\Amritsar\\wallet_SYMPOSIUMATP";
	final static String DB_USER = "admin";
	final static String DB_PASSWORD = "Welcome123456";
	final static String CONN_FACTORY_CLASS_NAME = "oracle.jdbc.pool.OracleDataSource";

	private Connection conn;

	public static void main(String args[]) throws Exception {

		SymposiumDAO dao = new SymposiumDAO();
		try {
			Connection conn = dao.getConnection();

			ArrayList<String> persons = dao.getEmployees(conn);
			ArrayList<Picture> pictures = dao.getPictures(conn);

			for (int i = 0; i < persons.size(); i++) {
				System.out.print(persons.get(i) + " , ");
			}
			System.out.println("\n*************\n\n");

			for (int i = 0; i < pictures.size(); i++) {
				System.out.print(pictures.get(i).getFileName() + " : " + pictures.get(i).getPersonsInPic());
			}
			System.out.println("\n*************\n\n");

			conn.close();
		} catch (SQLException e) {
			System.out.println("UCPSample - " + "SQLException occurred : " + e.getMessage());
		}
		System.out.println("Completed");
//		System.out.println("Available connections after checkin: " + pds.getAvailableConnectionsCount());
//		System.out.println("Borrowed connections after checkin: " + pds.getBorrowedConnectionsCount());
	}

	public Connection getConnection() throws SQLException {
		System.out.println("DB_URL[" + DB_URL + "]");
		PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();

		// Set the connection factory first before all other properties
		pds.setConnectionFactoryClassName(CONN_FACTORY_CLASS_NAME);
		pds.setURL(DB_URL);
		pds.setUser(DB_USER);
		pds.setPassword(DB_PASSWORD);
		pds.setConnectionPoolName("JDBC_UCP_POOL");

		// Default is 0. Set the initial number of connections to be created
		// when UCP is started.
		pds.setInitialPoolSize(5);

		// Default is 0. Set the minimum number of connections
		// that is maintained by UCP at runtime.
		pds.setMinPoolSize(5);

		// Default is Integer.MAX_VALUE (2147483647). Set the maximum number of
		// connections allowed on the connection pool.
		pds.setMaxPoolSize(20);

		// Default is 30secs. Set the frequency in seconds to enforce the timeout
		// properties. Applies to inactiveConnectionTimeout(int secs),
		// AbandonedConnectionTimeout(secs)& TimeToLiveConnectionTimeout(int secs).
		// Range of valid values is 0 to Integer.MAX_VALUE. .
		pds.setTimeoutCheckInterval(5);

		// Default is 0. Set the maximum time, in seconds, that a
		// connection remains available in the connection pool.
		pds.setInactiveConnectionTimeout(10);

		Connection conn = pds.getConnection();

		return conn;
	}

	public ArrayList<String> getEmployees(Connection connection) throws SQLException {
		ArrayList<String> persons = new ArrayList<String>();

		try (Statement statement = connection.createStatement()) {

			String query = "select user_id from employee_master";

			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				persons.add(resultSet.getString(1));
			}
		}
		return persons;
	}

	public ArrayList<Picture> getPictures(Connection connection) throws SQLException {
		ArrayList<Picture> pictures = new ArrayList<Picture>();

		try (Statement statement = connection.createStatement()) {

			String query = "select pic_file_name,pic_people_identified from symposium_pictures";

			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				pictures.add(new Picture(LeaderBoardProcess.DELIM + resultSet.getString(2) + LeaderBoardProcess.DELIM,
						resultSet.getString(1)));
			}
		}
		return pictures;
	}

}