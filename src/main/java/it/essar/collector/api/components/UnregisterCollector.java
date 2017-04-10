package it.essar.collector.api.components;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import it.essar.collector.Collector;

/**
 * Anypoint callable class that unregisters a collector.
 * @author Steve Roberts
 */
public class UnregisterCollector extends BaseCollectorComponent implements Callable
{
	
	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		// Read Collector ID from the message
		String collectorID = getCollectorID(eventContext);
		
		// Get the collector
		Collector collector = cb.getCollector(collectorID);
		
		// Unregister the Collector
		cb.unregisterCollector(collector);
		
		return true;
		
	}
}
