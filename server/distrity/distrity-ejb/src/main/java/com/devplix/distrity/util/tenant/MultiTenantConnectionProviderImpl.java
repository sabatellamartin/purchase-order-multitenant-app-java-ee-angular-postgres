package com.app.distrity.util.tenant;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;

public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
	private static final long serialVersionUID = -3873310037428575725L;
	
	private final ConnectionProvider connectionProvider = new ConnectionProviderImpl();

	public boolean isUnwrappableAs(@SuppressWarnings("rawtypes") Class unwrapType) {
		return false;
	}

	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}

	public Connection getAnyConnection() throws SQLException {
		final Connection connection = connectionProvider.getConnection();
		return connection;
	}

	public void releaseAnyConnection(Connection connection) throws SQLException {
		connectionProvider.closeConnection(connection);
	}

	public Connection getConnection(String tenantIdentifier) throws SQLException {
		final Connection connection = getAnyConnection();
		try {
			connection.createStatement().execute("SET SEARCH_PATH TO " + tenantIdentifier + ";");
		} catch (SQLException e) {
			throw new HibernateException(
					"Could not alter JDBC connection to specified schema [" + tenantIdentifier + "]", e);
		}
		return connection;
	}

	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		releaseAnyConnection(connection);
	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

}
