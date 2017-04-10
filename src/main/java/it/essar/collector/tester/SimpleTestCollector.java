package it.essar.collector.tester;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.essar.collector.DataPoint;

/**
 * Simple test collector for injecting data to the UDP layer.
 * @author Steve Roberts
 */
public class SimpleTestCollector implements DataApiFunctions
{

	static final Logger _LOG = Logger.getLogger(SimpleTestCollector.class.getName());
	private static int BASE_LOCAL_PORT = 9900;
	
	private DatagramSocket skt;
	private DataPoint lastPoint; 
	
	private String collectorID;
	private int localUdpPort, remoteUdpPort;
	private String hostName;
	
	
	/**
	 * Creates a new SimpleTestCollector using an incrementing local UDP port.
	 */
	SimpleTestCollector() {
		
		this.localUdpPort = BASE_LOCAL_PORT ++;
		
	}
	
	/**
	 * Creates a new SimpleTestCollector with the specified collectorID, remote host and port.
	 * @param collectorID the ID of this collector.
	 * @param hostName the remote hostname or IP address.
	 * @param udpPort the remote UDP port.
	 */
	SimpleTestCollector(String collectorID, String hostName, int udpPort) {
		
		this();
		
		this.collectorID = collectorID;
		this.hostName = hostName;
		
		this.remoteUdpPort = udpPort;
		
	}
	
	/**
	 * Calculates a random offset using a cosine curve.
	 * @param lastValue the previous value.
	 * @return the new value.
	 */
	private static double randomOffset(double lastValue) {
		
		return lastValue + Math.cos(Math.random() * Math.PI);
		
	}
	
	/**
	 * Creates a DataPoint for sending to the remote host.
	 * @return the generated DataPoint.
	 */
	private DataPoint createDataPoint() {
		
		TestDataPoint dp = new TestDataPoint(collectorID);
		
		dp.setTimestamp(System.currentTimeMillis());
		dp.setTemperature(lastPoint == null ? 15.0 : randomOffset(lastPoint.getTemperature()));
		dp.setHumidity(lastPoint == null ? 80.0 : randomOffset(lastPoint.getHumidity()));
		dp.setPressure(lastPoint == null ? 1024.0 : randomOffset(lastPoint.getPressure()));
		
		lastPoint = dp;
		
		_LOG.log(Level.INFO, "DP: {0}", dp);
		return dp;
		
	}
	
	/**
	 * Sets the collector ID.
	 * @param collectorID the ID of this collector.
	 */
	protected void setCollectorID(String collectorID) {
		
		this.collectorID = collectorID;
		
	}
	
	/**
	 * Closes any open resources. 
	 */
	void close() {
		
		if(skt != null && !skt.isClosed()) {

			skt.close();
				
		}
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.TestCollector#getCollectorID()
	 */
	@Override
	public String getCollectorID() {

		return collectorID;
		
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.TestCollector#getLocalUdpPort()
	 */
	@Override
	public int getLocalUdpPort() {

		return localUdpPort;
	
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.TestCollector#getRemoteHostname()
	 */
	@Override
	public String getRemoteHostname() {

		return hostName;
	
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.TestCollector#getRemoteUdpPort()
	 */
	@Override
	public int getRemoteUdpPort() {

		return remoteUdpPort;
	
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.TestCollector#sendPacket()
	 */
	@Override
	public void sendPacket() throws IOException {
		
		byte[] bytes = createDataPoint().toBytes();
		_LOG.log(Level.FINE, "Bytes={0}", Arrays.toString(bytes));
		
		DatagramPacket pkt = new DatagramPacket(bytes, bytes.length);
		pkt.setSocketAddress(new InetSocketAddress(hostName, remoteUdpPort));
		
		// Create a socket if one is not already active
		if(skt == null || skt.isClosed()) {
			
			skt = new DatagramSocket(localUdpPort);
			
		}
		
		skt.send(pkt);
		
	}
	
	/**
	 * Entry point for SimpleTestCollector
	 * @param args collectorID hostName udpPort cycles waitMillis
	 */
	public static void main(String[] args) {
		
		// Specify default parameters
		String collectorID = "";
		String hostname = "localhost";
		int udpPort = 9999;
		int cycles = 5;
		long waitMillis = 1000L;
		
		if(args.length > 0) {
			
			collectorID = args[0];
			
		}
		
		if(args.length > 1) {
			
			hostname = args[1];
			
		}
		if(args.length > 2) {
			
			udpPort = Integer.parseInt(args[2]);
			
		}
		if(args.length > 4) {
			
			cycles = Integer.parseInt(args[3]);
			waitMillis = Long.parseLong(args[4]);
			
		}
		
		
		SimpleTestCollector c = null;
		
		try {
			
			c = new SimpleTestCollector(collectorID, hostname, udpPort);			
			
			Thread t = new Thread(new UDPSendExecutor(c, cycles, waitMillis));
			t.setDaemon(false);
			t.setName(String.format("TestExecutorThread-%d", c.getLocalUdpPort()));
			
			t.start();
			
			// Wait for thread to end
			try {
				
				t.join();

			} catch(InterruptedException ie) {

				// Do nothing, just log
				_LOG.log(Level.WARNING, "Test interrupted", ie);
				
			}
			
		} finally {
			
			if(c != null) {
				
				c.close();
				
			}
		}
	}
}
