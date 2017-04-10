package it.essar.collector.api.components;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

/**
 * Anypoint callable class that adds an observation to a collector.
 * @author Steve Roberts
 */
public class GetGroupCollectors extends BaseCollectorComponent implements Callable
{
	
	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		// Read Group name from the message
		String groupName = getGroupName(eventContext);
		
		// Get and return the list of collectors
		return gb.getCollectors(groupName);
		
	}
}
