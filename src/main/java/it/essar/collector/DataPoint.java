package it.essar.collector;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Represents an observed set of environmental data.
 * @author Steve Roberts
 */
public class DataPoint implements Serializable
{

	private static final long serialVersionUID = 439243692606991895L;
	
	private double h, pa, t;
	private long ts;
	private String srcCollectorID;
	
	/**
	 * Initialises a new DataPoint object.
	 */
	public DataPoint() {
		
		// Empty constructor
		srcCollectorID = "EMPTY";
		
	}
	
	/**
	 * Initialises a new DataPoint object, reading an array of bytes.
	 * @param bytes an array of bytes containing the data.
	 */
	public DataPoint(byte[] bytes) {
		
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		
		byte[] src = new byte[16];
		buf.get(src);
		
		setSource(new String(src).trim());
		setTimestamp(buf.getLong());
		setTemperature(buf.getDouble());
		setHumidity(buf.getDouble());
		setPressure(buf.getDouble());
		
	}
	
	/**
	 * Gets the ID of the source collector.
	 * @return a String containing the ID of the source collector.
	 */
	String getSource() {
		
		return srcCollectorID;
		
	}
	
	/**
	 * Sets the source collector.
	 * @param collectorID a String containing the ID of the source collector.
	 */
	protected void setSource(String collectorID) {
		
		this.srcCollectorID = collectorID;
		
	}
	
	/**
	 * Gets the observed humidity.
	 * @return the relative humidity.
	 */
	public double getHumidity() {
		
		return h;
		
	}
	
	/**
	 * Gets the observed pressure.
	 * @return the pressure in millibars.
	 */
	public double getPressure() {
		
		return pa;
		
	}
	
	/**
	 * Gets the observed temperature.
	 * @return The temperature in degrees celsius.
	 */
	public double getTemperature() {
		
		return t;
		
	}
	
	/**
	 * Gets the observation timestamp.
	 * @return Epoc timestamp in milliseconds.
	 */
	public long getTimestamp() {
		
		return ts;
		
	}
	
	/**
	 * Sets the observed humidity.
	 * @param h the relative humidity.
	 */
	public void setHumidity(double h) {
		
		this.h = h;
		
	}
	
	/**
	 * Sets the observed pressure.
	 * @param pa the pressure in millibars.
	 */
	public void setPressure(double pa) {
		
		this.pa = pa;
		
	}
	
	/**
	 * Sets the observed temperature.
	 * @param t the temperature in degrees celsius.
	 */
	public void setTemperature(double t) {
		
		this.t = t;
		
	}
	
	/**
	 * Sets the observation timestamp.
	 * @param ts Epoc timestamp in milliseconds.
	 */
	public void setTimestamp(long ts) {
		
		this.ts = ts;
		
	}
	
	/**
	 * Gets the data as a byte-array.
	 * @return an array of bytes containing the observation data.
	 */
	public byte[] toBytes() {
		
		ByteBuffer buf = ByteBuffer.allocate(80);
		
		byte[] src = new byte[16];
		if(getSource() != null) {

			System.arraycopy(getSource().getBytes(), 0, src, 0, Math.min(getSource().getBytes().length, 16));
			
		}
		buf.put(src);
		
		buf.putLong(getTimestamp());
		buf.putDouble(getTemperature());
		buf.putDouble(getHumidity());
		buf.putDouble(getPressure());
		
		return buf.array();
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return String.format("id=%s|ts=%d|t=%.5f|h=%.5f|pa=%.5f\n", srcCollectorID, ts, t, h, pa);
		
	}
}
