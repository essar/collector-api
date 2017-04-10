package it.essar.collector.api.components;

import java.util.ArrayList;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import it.essar.collector.Collector;

/**
 * Anypoint callable class that registers a collector.
 * @author Steve Roberts
 */
public class RegisterCollector extends BaseCollectorComponent implements Callable
{

	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
	
		// Get the request object from the payload
		Request req = eventContext.getMessage().getPayload(Request.class);
		
		// Create a new Collector object and set properties from request
		Collector c = new Collector();
		c.setCollectorName(req == null ? null : req.getName());
		
		// Register the collector with the bean
		cb.registerCollector(c);
		
		// Return the collector ID
		return c.getCollectorID();
		
	}
	
	/**
	 * Request bean class for RegisterCollector operation.
	 * @author Steve Roberts
	 */
	public static class Request
	{
		
		private ArrayList<String> groups = new ArrayList<>();
		private String name;
		
		/**
		 * Gets the list of group names.
		 * @return a List of Strings containing group names.
		 */
		public ArrayList<String> getGroups() {
			
			return groups;
			
		}
		
		/**
		 * Gets the collector name.
		 * @return a String containing the name.
		 */
		public String getName() {
			
			return name;
			
		}
		
		/**
		 * Sets the collector name.
		 * @param name a String containing the name.
		 */
		public void setName(String name) {
			
			this.name = name;
			
		}
	}
}
