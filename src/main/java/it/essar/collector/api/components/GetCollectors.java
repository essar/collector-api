package it.essar.collector.api.components;

import java.util.ArrayList;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

/**
 * Anypoint callable class that gets a list of collectors.
 * @author Steve Roberts
 */
public class GetCollectors extends BaseCollectorComponent implements Callable
{

	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		Response rsp = new Response();
		rsp.getCollectorIDs().addAll(cb.getCollectorIDs());
		return rsp;
		
	}
	
	/**
	 * Response bean class for GetCollectors operation.
	 * @author Steve Roberts
	 */
	public static class Response
	{
		
		private ArrayList<String> collectorIDs = new ArrayList<>();
		
		public ArrayList<String> getCollectorIDs() {
			
			return collectorIDs;
			
		}
	}
}
