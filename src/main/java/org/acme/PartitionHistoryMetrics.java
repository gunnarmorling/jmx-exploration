package org.acme;

public class PartitionHistoryMetrics implements PartitionHistoryMXBean {

	@Override
	public String getChunk() {
		return "chunk 1";
	}
	
}
