package com.app.distrity.util.tenant;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import com.app.distrity.util.Constants;

public class ConnectionProviderImpl implements ConnectionProvider {
	private static final long serialVersionUID = 6148712117945382783L;
	
	private static final String jndiName = Constants.JTA_DATA_SOURCE;

	@Override
	public Connection getConnection() throws SQLException {
		DataSource ds = null;
		try {
			InitialContext ic = new InitialContext();
			ds = (DataSource) ic.lookup(jndiName);
			return ds.getConnection();
		} catch (NamingException e) {
			return null;
		}
	}

	@Override
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public boolean isUnwrappableAs(@SuppressWarnings("rawtypes") Class type) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> type) {
		return null;
	}

}
