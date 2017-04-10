package it.essar.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean object that provides methods to manipulate <tt>Group</tt> objects.
 * Uses an internal static map to persist group objects.
 * @author Steve Roberts
 */
public class GroupBean
{
	private static final Map<String, List<Collector>> groups = new HashMap<>();
	
	/**
	 * Instantiates a new GroupBean.
	 */
	public GroupBean() {
		
		// Default constructor
		
	}
	
	/**
	 * Creates a new group.
	 * @param groupName the group name.
	 */
	void createGroup(String groupName) {
		
		synchronized(groups) {
			
			groups.put(groupName, new ArrayList<Collector>());
			
		}
		
	}
	
	/**
	 * Adds a collector to a specified group, creating the group if necessary.
	 * @param groupName the group name to add the collector to.
	 * @param collector the collector to add.
	 */
	public void addToGroup(String groupName, Collector collector) {
		
		synchronized(groups) {
			
			if(!groups.containsKey(groupName)) {
				
				createGroup(groupName);
				
			}
			
			groups.get(groupName).add(collector);
			
		}
	}
	
	/**
	 * Gets a list of collectors associated with a group.
	 * @param groupName the group name.
	 * @return a List of Collectors.
	 * @throws NoSuchGroupException if the group name is not known to the application.
	 */
	public List<Collector> getCollectors(String groupName) throws NoSuchGroupException {
		
		synchronized(groups) {
			
			if(!groups.containsKey(groupName)) {
				
				throw new NoSuchGroupException("Unknown group: " + groupName);
				
			}
				
			return groups.get(groupName);
			
		}
	}
	
	/**
	 * Gets a list of group names known to the application.
	 * @return a List of Strings.
	 */
	public List<String> getGroupNames() {
		
		synchronized(groups) {
			
			return new ArrayList<>(groups.keySet());
			
		}
	}
	
	/**
	 * Removes a group. Does not unregister the collections.
	 * @param groupName the group name.
	 */
	public void removeGroup(String groupName) {
		
		synchronized(groups) {
			
			if(groups.containsKey(groupName)) {
				
				groups.remove(groupName);
				
			}
		}
	}
}
