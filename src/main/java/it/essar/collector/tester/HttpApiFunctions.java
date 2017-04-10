package it.essar.collector.tester;

import java.io.IOException;

public interface HttpApiFunctions
{
	/**
	 * Gets the collector IDs.
	 * @return an array of Strings listing all the current collectors.
	 */
	public String[] getCollectors();
	
	/**
	 * Gets the collector ID.
	 * @return a String containing the collector ID.
	 */
	public String getCollectorID();
	
	/**
	 * Gets the collector groups.
	 * @return an array of Strings listing the groups this collector is a member of.
	 */
	public String[] getGroups();
	
	/**
	 * Gets the remote hostname.
	 * @return a String containing the remote host name or IP address this collector connects to.
	 */
	public String getRemoteHostname();
	
	/**
	 * Gets the remote HTTP port.
	 * @return the remote port used for HTTP connections.
	 */
	public int getRemoteHttpPort();
	
	/**
	 * Register this connector with the API.
	 * @throws IOException if the connector cannot be registered.
	 */
	public void register() throws IOException;
	
	/**
	 * Unregister this connector with the API.
	 * @throws IOException if the connector cannot be unregistered.
	 */
	public void unregister() throws IOException;
	
}
