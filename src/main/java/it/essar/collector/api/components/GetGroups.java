package it.essar.collector.api.components;

import java.util.ArrayList;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

/**
 * Anypoint callable class that gets a list of groups.
 * @author Steve Roberts
 */
public class GetGroups extends BaseCollectorComponent implements Callable
{

	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {

		Response rsp = new Response();
		rsp.getGroups().addAll(gb.getGroupNames());
		return rsp;
		
	}
	
	/**
	 * Response bean class for GetGroups operation.
	 * @author Steve Roberts
	 */
	public static class Response
	{
		
		private ArrayList<String> groups = new ArrayList<>();
		
		/**
		 * Gets the list of group IDs.
		 * @return a List of Strings containing the group names.
		 */
		public ArrayList<String> getGroups() {
			
			return groups;
			
		}
	}
}
