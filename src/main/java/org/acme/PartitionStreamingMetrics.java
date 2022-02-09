package org.acme;

public class PartitionStreamingMetrics implements PartitionStreamingMXBean {

	@Override
	public long getMilliSecondsBehindSource() {
		return 60;
	}
}
