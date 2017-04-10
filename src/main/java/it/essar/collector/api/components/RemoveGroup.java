package it.essar.collector.api.components;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

/**
 * Anypoint callable class that removes a group.
 * @author Steve Roberts
 */
public class RemoveGroup extends BaseCollectorComponent implements Callable
{

	/* (non-Javadoc)
	 * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		
		// Read Group name from the message
		String groupName = getGroupName(eventContext);

		// Remove the group
		gb.removeGroup(groupName);
		
		return true;
		
	}
}
