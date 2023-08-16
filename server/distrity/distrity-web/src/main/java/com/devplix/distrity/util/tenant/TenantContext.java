package com.app.distrity.util.tenant;

import com.app.distrity.util.Constants;

public class TenantContext {

	private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

	public static String getCurrentTenant() {
		return currentTenant.get();
	}

	public static void setCurrentTenant(String tenant) {
		currentTenant.set(tenant);
	}

	public static void clear() {
		currentTenant.set(null);
	}

	public static void connectTenant(String tenant) {
	    if (tenant == null || tenant.isEmpty()) {
	    	tenant = Constants.MASTER_TENANT;
		}
		TenantContext.setCurrentTenant(tenant);
		CurrentTenantIdentifierResolverImpl.tenantIdentifier.set(DbUtil.buildSchemaString(TenantContext.getCurrentTenant()));
	}

}