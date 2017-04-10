package it.essar.collector.tester;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for executing behaviour against a TestCollector.
 * @author Steve Roberts
 */
class UDPSendExecutor implements Runnable
{
	
	private static final Logger _LOG = Logger.getLogger(UDPSendExecutor.class.getName());
	
	private final DataApiFunctions tester;
	private int cycles;
	private long waitMillis;
	
	/**
	 * Instantiates a new TestCollectorExecutor for a given TestCollector.
	 * @param tester the test collector to use.
	 * @param waitMillis the time in milliseconds to wait between executions.
	 * @param cycles the number of cycles to execute.
	 */
	UDPSendExecutor(DataApiFunctions tester, int cycles, long waitMillis) {
		
		this.tester = tester;
		this.cycles = cycles;
		this.waitMillis = waitMillis;
		
	}
	
	/**
	 * Gets the number of cycles.
	 * @return the number of cycles to execute.
	 */
	public int getCycles() {
		
		return cycles;
		
	}
	
	/**
	 * Gets the wait time.
	 * @return the time in milliseconds to wait between executions.
	 */
	public long getWaitMillis() {
		
		return waitMillis;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {

		int pktCount = 0;
		int errCount = 0;
		
		try {
		
			if(tester != null) {
				
				// Random wait on startup
				Thread.sleep(Math.round(Math.random() * waitMillis));
				
				_LOG.log(Level.INFO, "Starting packet transmision...");
				
				_LOG.log(Level.CONFIG, "localPort={0,number,0000}", tester.getLocalUdpPort());
				_LOG.log(Level.CONFIG, "remoteHostname={0}", tester.getRemoteHostname());
				_LOG.log(Level.CONFIG, "remotePort={0,number,0000}", tester.getRemoteUdpPort());
				
				for(int i = 0 ; i < cycles; i ++) {
					
					try {
						
						tester.sendPacket();
						pktCount ++;
						
					} catch(IOException ioe) {
						
						_LOG.log(Level.WARNING, "Unable to send test packet", ioe);
						errCount ++;
						
					}
					
					Thread.sleep(waitMillis);
					
				}
			}
			
		} catch(InterruptedException ie) {
			
			// Log and end
			_LOG.log(Level.WARNING, "Test runner interrupted", ie);
			
		}
		
		_LOG.log(Level.INFO, "Executor complete: sent {0} packet(s), {1} error(s).", new Object[] { pktCount, errCount });
		
	}
	
	/**
	 * Sets the number of cycles.
	 * @param cycles the number of cycles to execute.
	 */
	public void setCycles(int cycles) {
		
		this.cycles = cycles;
		
	}
	
	/**
	 * Sets the wait time.
	 * @param waitMillis the time in milliseconds to wait between executions.
	 */
	public void setWaitMillis(long waitMillis) {
		
		this.waitMillis = waitMillis;
		
	}
}