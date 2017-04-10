package it.essar.collector;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CollectorData implements Serializable
{
	
	private static final long serialVersionUID = 3230033208092857912L;
	
	private final int capacity;
	private final LinkedList<DataPoint> collectedData = new LinkedList<>();
	
	private DataPoint lastReading = null;
	
	public CollectorData() {
		
		// Default constructor
		this(1000);
		
	}
	
	public CollectorData(int capacity) {
		
		this.capacity = capacity;
		
	}
	
	public void addObservation(DataPoint data) {
		
		// Trim data list if necessary
		while(collectedData.size() > capacity - 1) {
			
			collectedData.removeFirst();
			
		}
		
		collectedData.add(data);
		lastReading = data;
		
	}
	
	public List<DataPoint> getCollectedData() {
		
		return new LinkedList<>(collectedData);
		
	}
	
	public DataPoint getLastObservation() {
		
		return lastReading;
		
	}
}
