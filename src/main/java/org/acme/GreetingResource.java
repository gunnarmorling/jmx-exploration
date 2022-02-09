package org.acme;

import java.lang.management.ManagementFactory;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
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
		
		ObjectName name = new ObjectName("debezium:00=dbserver1,name=ConnectorMetrics");
		ConnectorMetrics bean = new ConnectorMetrics();
		mbs.registerMBean(bean, name);
		names.add(name);
		
		name = new ObjectName("debezium:00=dbserver1,01=task-1,name=TaskMetrics");
		TaskMetrics bean2 = new TaskMetrics();
		mbs.registerMBean(bean2, name);
		names.add(name);
		
		name = new ObjectName("debezium:00=dbserver1,01=task-1,02=schema-history,name=Partition 1");
		PartitionHistoryMetrics bean3 = new PartitionHistoryMetrics();
		mbs.registerMBean(bean3, name);
		names.add(name);
		
		name = new ObjectName("debezium:00=dbserver1,01=task-1,02=schema-history,name=Partition 2");
		PartitionHistoryMetrics bean4 = new PartitionHistoryMetrics();
		mbs.registerMBean(bean4, name);
		names.add(name);

		name = new ObjectName("debezium:00=dbserver1,01=task-1,02=snapshotting,name=Partition 1");
		PartitionSnapshotMetrics bean5 = new PartitionSnapshotMetrics();
		mbs.registerMBean(bean5, name);
		names.add(name);
		
		name = new ObjectName("debezium:00=dbserver1,01=task-1,02=snapshotting,name=Partition 2");
		PartitionSnapshotMetrics bean6 = new PartitionSnapshotMetrics();
		mbs.registerMBean(bean6, name);
		names.add(name);

		name = new ObjectName("debezium:00=dbserver1,01=task-1,02=streaming,name=Partition 1");
		PartitionStreamingMetrics bean7 = new PartitionStreamingMetrics();
		mbs.registerMBean(bean7, name);
		names.add(name);
		
		name = new ObjectName("debezium:00=dbserver1,01=task-1,02=streaming,name=Partition 2");
		PartitionStreamingMetrics bean8 = new PartitionStreamingMetrics();
		mbs.registerMBean(bean8, name);
		names.add(name);
	}
	
	public void unregisterMBeans(@Observes ShutdownEvent se) throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		
		for (ObjectName objectName : names) {
			mbs.unregisterMBean(objectName);
		}
	}
}
