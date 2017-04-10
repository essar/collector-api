package it.essar.collector.api.components;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

/**
 * Anypoint callable class that gets collector information.
 * @author Steve Roberts
 */
public class GetCollector extends BaseCollectorComponent implements Callable
{
	
	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		// Read Collector ID from the message
		String collectorID = getCollectorID(eventContext);
		
		// Get and return the Collector
		return cb.getCollector(collectorID);
		
	}
}
