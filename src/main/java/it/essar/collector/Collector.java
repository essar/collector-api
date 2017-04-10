package it.essar.collector;

import java.io.Serializable;
import java.util.List;

/**
 * Defines an object that collects environmental data.
 * @author Steve Roberts
 */
public class Collector implements Serializable
{

	private static final long serialVersionUID = -6133601358828444712L;

	private CollectorData data;
	private String collectorID, collectorName;
	
	/**
	 * Instantiates a new Collector.
	 */
	public Collector() {
		
		// Default constructor
		data = new CollectorData();
	}
	
	/**
	 * Adds a piece of observation data to this collector.
	 * @param dataPoint a piece of observed data.
	 */
	void addObservation(DataPoint dataPoint) {
		
		data.addObservation(dataPoint);
		
	}
	
	/**
	 * Gets a list of observed data.
	 * @return a List of observed data points.
	 */
	List<DataPoint> getCollectedData() {
		
		return data.getCollectedData();
		
	}
	
	/**
	 * Gets the raw CollectorData object for this collector.
	 * @return the underlying CollectorData object.
	 */
	CollectorData getData() {
		
		return data;
		
	}
	
	/**
	 * Gets this collector's the identifier.
	 * @return a String containing the unique collector ID.
	 */
	public String getCollectorID() {
		
		return collectorID;
		
	}
	
	/**
	 * Gets this collector's name.
	 * @return a String containing the name of this collector.
	 */
	public String getCollectorName() {
		
		return collectorName;
				
	}
	
	/**
	 * Gets the average humidity observed by this collector.
	 * @return the average relative humidity.
	 */
	public double getHumidityAvg() {
		
		return ListFunctions.avg(getCollectedData(), (dp) -> dp.getHumidity());
		
	}
	
	/**
	 * Gets the highest humidity observed by this collector.
	 * @return the maximum relative humidity.
	 */
	public double getHumidityMax() {
		
		return ListFunctions.max(getCollectedData(), (dp) -> dp.getHumidity());
		
	}
	
	/**
	 * Gets the lowest humidity observed by this collector.
	 * @return the minimum relative humidity.
	 */
	public double getHumidityMin() {
		
		return ListFunctions.min(getCollectedData(), (dp) -> dp.getHumidity());
		
	}
	
	/**
	 * Gets the last observed data by this collector.
	 * @return the last observed data point.
	 */
	public DataPoint getLastObservation() {
		
		return data.getLastObservation();
		
	}
	
	/**
	 * Gets the average pressure observed by this collector.
	 * @return the average pressure in millibars.
	 */
	public double getPressureAvg() {
		
		return ListFunctions.avg(getCollectedData(), (dp) -> dp.getPressure());
		
	}
	
	/**
	 * Gets the highest pressure observed by this collector.
	 * @return the maximum pressure in millibars.
	 */
	public double getPressureMax() {
		
		return ListFunctions.max(getCollectedData(), (dp) -> dp.getPressure());
		
	}
	
	/**
	 * Gets the lowest pressure observed by this collector.
	 * @return the minimum pressure in millibars.
	 */
	public double getPressureMin() {
		
		return ListFunctions.min(getCollectedData(), (dp) -> dp.getPressure());
		
	}
	
	/**
	 * Gets the average temperature observed by this collector.
	 * @return the average temperature in degrees celsius.
	 */
	public double getTempAvg() {
		
		return ListFunctions.avg(getCollectedData(), (dp) -> dp.getTemperature());
		
	}
	
	/**
	 * Gets the highest temperature observed by this collector.
	 * @return the maximum temperature in degrees celsius.
	 */
	public double getTempMax() {
		
		return ListFunctions.max(getCollectedData(), (dp) -> dp.getTemperature());
		
	}
	
	/**
	 * Gets the lowest temperature observed by this collector.
	 * @return the minimum temperature in degrees celsius.
	 */
	public double getTempMin() {
		
		return ListFunctions.min(getCollectedData(), (dp) -> dp.getTemperature());
		
	}
	
	/**
	 * Sets this collector's identifier.
	 * @param collectorID a String containing the unique collector ID.
	 */
	public void setCollectorID(String collectorID) {
		
		this.collectorID = collectorID;
		
	}
	
	/**
	 * Sets this collector's name.
	 * @param collectorName a String containing the collector name
	 */
	public void setCollectorName(String collectorName) {
		
		this.collectorName = collectorName;
		
	}
}
