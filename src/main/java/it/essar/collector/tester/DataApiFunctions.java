package it.essar.collector.tester;

import java.io.IOException;

public interface DataApiFunctions
{
	
	/**
	 * Gets the collector ID.
	 * @return a String containing the collector ID.
	 */
	public String getCollectorID();
	
	/**
	 * Gets the local UDP port.
	 * @return the local port used for UDP connections.
	 */
	public int getLocalUdpPort();
	
	/**
	 * Gets the remote hostname.
	 * @return a String containing the remote host name or IP address this collector connects to.
	 */
	public String getRemoteHostname();
	
	/**
	 * Gets the remote UDP port.
	 * @return the remote port used for UDP connections.
	 */
	public int getRemoteUdpPort();
	
	/**
	 * Send an observed packet.
	 * @throws IOException if the package cannot be sent.
	 */
	public void sendPacket() throws IOException;
	
}
