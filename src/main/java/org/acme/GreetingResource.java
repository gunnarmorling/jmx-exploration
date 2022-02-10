package org.acme;

import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@Path("/hello")
public class GreetingResource {

	private Set<ObjectName> names = new HashSet<>();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello RESTEasy";
	}

	public void registerMBeans(@Observes StartupEvent se) throws Exception {
		System.out.println("Registering MBeans");

		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		
		ObjectName name = new ObjectName("debezium:m1=dbserver1");
		ConnectorMetrics bean = new ConnectorMetrics();
		mbs.registerMBean(bean, name);
		names.add(name);
		
		name = new ObjectName("debezium:m1=dbserver1,m2=task-1");
		TaskMetrics bean2 = new TaskMetrics();
		mbs.registerMBean(bean2, name);
		names.add(name);
		
		name = new ObjectName("debezium:m1=dbserver1,m2=task-1,m3=schema-history,m4=Partition 1");
		PartitionHistoryMetrics bean3 = new PartitionHistoryMetrics();
		mbs.registerMBean(bean3, name);
		names.add(name);
		
		name = new ObjectName("debezium:m1=dbserver1,m2=task-1,m3=snapshotting,m4=Partition 1");
		PartitionSnapshotMetrics bean5 = new PartitionSnapshotMetrics();
		mbs.registerMBean(bean5, name);
		names.add(name);
		
		name = new ObjectName("debezium:m1=dbserver1,m2=task-1,m3=snapshotting,m4=Partition 2");
		PartitionSnapshotMetrics bean6 = new PartitionSnapshotMetrics();
		mbs.registerMBean(bean6, name);
		names.add(name);

		name = new ObjectName("debezium:m1=dbserver1,m2=task-1,m3=streaming,m4=Partition 1");
		PartitionStreamingMetrics bean7 = new PartitionStreamingMetrics();
		mbs.registerMBean(bean7, name);
		names.add(name);
		
		name = new ObjectName("debezium:m1=dbserver1,m2=task-1,m3=streaming,m4=Partition 3");
		PartitionStreamingMetrics bean8 = new PartitionStreamingMetrics();
		mbs.registerMBean(bean8, name);
		names.add(name);

		name = new ObjectName("debezium:m1=dbserver1,m2=task-2");
		TaskMetrics bean9 = new TaskMetrics();
		mbs.registerMBean(bean9, name);
		names.add(name);
	}
	
	public void unregisterMBeans(@Observes ShutdownEvent se) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		
		for (ObjectName objectName : names) {
			mbs.unregisterMBean(objectName);
		}
	}
}
