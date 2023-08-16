package com.app.distrity.util.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	public static ThreadLocal<String> tenantIdentifier = new ThreadLocal<>();

	@Override
	public String resolveCurrentTenantIdentifier() {
		String currentTenantIdentifier = tenantIdentifier.get();
		if (currentTenantIdentifier == null)
			currentTenantIdentifier = "organizations";
		return currentTenantIdentifier;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}

}
