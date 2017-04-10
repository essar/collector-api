package it.essar.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean object that provides methods to manipulate <tt>Collector</tt> objects.
 * Uses an internal static map to persist collector objects.
 * @author Steve Roberts
 */
public class CollectorBean
{

	private static final Map<String, Collector> collectors = new HashMap<>();
	
	/**
	 * Instantiates a new CollectorBean.
	 */
	public CollectorBean() {
		
		// Default constructor;
		
	}
	
	/**
	 * Generates a new collector ID
	 * @return a String containing a unique collector ID.
	 */
	private static String createCollectorID() {
		
		return String.format("%X", System.currentTimeMillis());
		
	}
	
	/**
	 * Adds an observation to a collector.
	 * @param dataPoint the DataPoint to add.
	 * @throws NoSuchCollectorException if the source for this DataPoint is not known to this application.
	 */
	public void addObservation(DataPoint dataPoint) throws NoSuchCollectorException {
		
		Collector collector = getCollector(dataPoint.getSource());
		collector.addObservation(dataPoint);
		
	}
	
	/**
	 * Gets a collector.
	 * @param collectorID the ID of the collector to retrieve.
	 * @return the Collector.
	 * @throws NoSuchCollectorException if the collector ID is not known to this application.
	 */
	public Collector getCollector(String collectorID) throws NoSuchCollectorException {
		
		synchronized(collectors) {
			
			if(!collectors.containsKey(collectorID)) {
				
				throw new NoSuchCollectorException("Unknown collector: " + collectorID);
				
			}
		
			return collectors.get(collectorID);
			
		}
	}
	
	/**
	 * Gets collector data.
	 * @param collectorID the ID of the collector to retrieve.
	 * @return the CollectorData.
	 * @throws NoSuchCollectorException if the collector ID is not known to this application.
	 */
	public CollectorData getCollectorData(String collectorID) throws NoSuchCollectorException {
		
		Collector c = getCollector(collectorID);
		return c == null ? null : c.getData();
		
	}
	
	/**
	 * Gets a list of known collector IDs.
	 * @return a List of collector IDSs.
	 */
	public List<String> getCollectorIDs() {
		
		return new ArrayList<>(collectors.keySet());
		
	}
	
	/**
	 * Registers a collector with this application.
	 * @param collector the Collector to register.
	 */
	public void registerCollector(Collector collector) {
		
		String collectorID = collector.getCollectorID();
		
		if(collectorID == null) {
			
			collectorID = createCollectorID();
			collector.setCollectorID(collectorID);
			
		}
		
		synchronized(collectors) {
			
			collectors.put(collectorID, collector);
			
		}
	}
	
	/**
	 * Unregisters a collector with this application.
	 * @param collector the Collector to unregister.
	 */
	public void unregisterCollector(Collector collector) {
		
		String collectorID = collector.getCollectorID();
		
		synchronized(collectors) {

			if(collectors.containsKey(collectorID)) {
				
				collectors.remove(collectorID);
				
			}
		}
	}
}
