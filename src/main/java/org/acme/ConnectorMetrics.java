package org.acme;

public class ConnectorMetrics implements ConnectorMXBean {

	@Override
	public String getType() {
		return "SQL Server";
	}
	
}
