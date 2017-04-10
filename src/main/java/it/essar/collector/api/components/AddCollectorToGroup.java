package it.essar.collector.api.components;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import it.essar.collector.Collector;

/**
 * Anypoint callable class that adds a collector to a group.
 * @author Steve Roberts
 */
public class AddCollectorToGroup extends BaseCollectorComponent implements Callable
{
	
	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		// Obtain the group name from the message payload
		String groupName = eventContext.getMessage().getPayloadAsString();
		
		// Read Collector ID from the message
		String collectorID = getCollectorID(eventContext);
		
		// Look up the collector
		Collector collector = cb.getCollector(collectorID);
				
		// Add the Collector to the Group
		gb.addToGroup(groupName, collector);
		
		return groupName;
		
	}
}
