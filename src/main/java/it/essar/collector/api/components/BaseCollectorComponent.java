package it.essar.collector.api.components;

import org.mule.api.MuleEventContext;
import org.mule.api.transport.PropertyScope;

import it.essar.collector.CollectorBean;
import it.essar.collector.GroupBean;

/**
 * Abstract class defining base functions for callable classes.
 * @author Steve Roberts
 */
abstract class BaseCollectorComponent
{
	
	protected static final String COLLECTOR_ID = "collectorID";
	protected static final String GROUP_NAME = "groupName";
	
	protected final CollectorBean cb;
	protected final GroupBean gb;
	
	/**
	 * Instantiates a new BaseCollectorComponent.
	 */
	protected BaseCollectorComponent() {
		
		this.cb = new CollectorBean();
		this.gb = new GroupBean();
		
	}
	
	/**
	 * Gets the collector ID from the session scope.
	 * @param eventContext current context.
	 * @return a String containing the collector ID.
	 */
	protected static String getCollectorID(MuleEventContext eventContext) {
		
		return eventContext.getMessage().getProperty(COLLECTOR_ID, PropertyScope.SESSION, "");
		
	}
	
	/**
	 * Gets the group name from the session scope.
	 * @param eventContext current context.
	 * @return a String containing the group name.
	 */
	protected static String getGroupName(MuleEventContext eventContext) {
		
		return eventContext.getMessage().getProperty(GROUP_NAME, PropertyScope.SESSION, "");
		
	}
}
