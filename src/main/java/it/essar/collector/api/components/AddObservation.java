package it.essar.collector.api.components;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import it.essar.collector.DataPoint;

/**
 * Anypoint callable class that adds an observation to a collector.
 * @author Steve Roberts
 */
public class AddObservation extends BaseCollectorComponent implements Callable
{
	
	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		// Obtain the DataPoint object from the message payload
		DataPoint dp = eventContext.getMessage().getPayload(DataPoint.class);
		
		// Add the DataPoint to the collector
		cb.addObservation(dp);
		
		return null;
		
	}
}
