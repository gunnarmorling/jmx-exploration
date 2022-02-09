package org.acme;

public class TaskMetrics implements TaskMXBean {

	@Override
	public int getQueueSize() {
		return 100;
	}
}
