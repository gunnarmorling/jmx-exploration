package org.acme;

public class PartitionSnapshotMetrics implements PartitionSnapshotMXBean {

	@Override
	public int getNumberOfRecoveredRecords() {
		return 42;
	}
}
