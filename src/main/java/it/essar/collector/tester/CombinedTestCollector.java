package it.essar.collector.tester;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Simple test collector for registering a collector over HTTP and injecting data on the UDP layer.
 * @author Steve Roberts
 */
public class CombinedTestCollector extends SimpleTestCollector implements DataApiFunctions, HttpApiFunctions
{
	
	private static final Logger _LOG = Logger.getLogger(CombinedTestCollector.class.getName());
	
	private int httpPort;
	
	private String[] groups;
	
	/**
	 * Creates a new CombinedTestCollector with the specified collectorID, remote host and port.
	 * @param hostName the remote hostname or IP address.
	 * @param httpPort the remote HTTP port.
	 * @param udpPort the remote UDP port.
	 */
	CombinedTestCollector(String hostName, int httpPort, int udpPort) {
		
		super(null, hostName, udpPort);
		this.httpPort = httpPort;
		groups = new String[] { "testing" };
		
	}
	
	/**
	 * Formats the list of groups into a JSON string.
	 * @param groups an array of Strings.
	 * @return the groups as a JSON string.
	 */
	@SuppressWarnings("unchecked")
	private static String formatData(String[] groups) {
		
		JSONObject jObj = new JSONObject();
		jObj.put("name", CombinedTestCollector.class.getSimpleName());
		jObj.put("groups", Arrays.asList(groups));
		
		return jObj.toJSONString();
		
	}
	
	/**
	 * Parses the collector ID from a JSON object.
	 * @param in the input Reader.
	 * @return the collector ID.
	 * @throws IOException if the reader cannot be read.
	 * @throws ParseException if the JSON data cannot be parsed.
	 */
	private static String parseCollectorID(Reader in) throws IOException, ParseException {
		
		JSONParser p = new JSONParser();
		
		return (String) p.parse(in);
		
	}
	
	/**
	 * Parses an array of collector IDs from a JSON object.
	 * @param in the input Reader.
	 * @return the collector IDs.
	 * @throws IOException if the reader cannot be read.
	 * @throws ParseException if the JSON data cannot be parsed.
	 */
	private static String[] parseCollectorIDs(Reader in) throws IOException, ParseException {
		
		JSONParser p = new JSONParser();
		
		JSONObject jObj = (JSONObject) p.parse(in);
		JSONArray jArr = (JSONArray) jObj.get("collectorIDs");
		
		ArrayList<String> strs = new ArrayList<>();
		for(Object obj : jArr) {
			
			strs.add((String) obj);
			
		}
		
		return strs.toArray(new String[strs.size()]);
		
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.HttpApiFunctions#getCollectors()
	 */
	@Override
	public String[] getCollectors() {
		
		String path = "/collectors";

		try {
			
			URL u = new URL("http", getRemoteHostname(), getRemoteHttpPort(), path);
			
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			
			if(con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				
				throw new IOException(String.format("Unexpected response code %d: %s", con.getResponseCode(), con.getResponseMessage()));
				
			}
			
			return parseCollectorIDs(new InputStreamReader(con.getInputStream()));
			
		} catch(IOException ioe) {
			
			_LOG.log(Level.SEVERE, "Unable to get collector list", ioe);
			
		} catch(ParseException pe) {
			
			_LOG.log(Level.SEVERE, "Unable to parse collector list", pe);
			
		}
		
		return new String[0];
		
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.HttpApiFunctions#getGroups()
	 */
	@Override
	public String[] getGroups() {
	
		return groups;
		
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.HttpApiFunctions#getRemoteHttpPort()
	 */
	@Override
	public int getRemoteHttpPort() {
		
		return httpPort;
		
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.TestCollector#register()
	 */
	@Override
	public void register() throws IOException {
		
		String path = "/register";
		
		String data = formatData(groups);
		_LOG.log(Level.FINE, "POST data: {0}", data);
		
		URL u = new URL("http", getRemoteHostname(), httpPort, path);
		_LOG.log(Level.FINE, "Remote URL: {0}", u);
		
		HttpURLConnection con = (HttpURLConnection) u.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Content-Length", String.valueOf(data.length()));
		con.getOutputStream().write(data.getBytes());
		
		con.connect();
		
		if(con.getResponseCode() != HttpURLConnection.HTTP_OK) {
			
			throw new IOException(String.format("Unexpected response code %d: %s", con.getResponseCode(), con.getResponseMessage()));
			
		}
		
		try {
			
			String collectorID = parseCollectorID(new InputStreamReader(con.getInputStream()));
			setCollectorID(collectorID);
			
		} catch(ParseException pe) {
			
			_LOG.log(Level.WARNING, "Unable to parse collector ID", pe);
			
		}
	}
	
	/* (non-Javadoc)
	 * @see it.essar.collector.tester.TestCollector#unregister()
	 */
	@Override
	public void unregister() throws IOException {
		
		throw new UnsupportedOperationException("Unregister not yet supported in " + getClass().getCanonicalName());
		
	}
	
	/**
	 * Entry point for CombinedTestCollector
	 * @param args hostName httpPort udpPort cycles waitMillis collectorCount
	 */
	public static void main(String[] args) {
		
		// Specify default parameters
		String hostname = "localhost";
		int httpPort = 8081;
		int udpPort = 9999;
		int cycles = 5;
		long waitMillis = 1000L;
		int cCount = 3;
		
		if(args.length > 0) {
			
			hostname = args[0];
			
		}
		if(args.length > 1) {
			
			httpPort = Integer.parseInt(args[1]);
			
		}
		if(args.length > 2) {
			
			udpPort = Integer.parseInt(args[2]);
			
		}
		if(args.length > 4) {
			
			cycles = Integer.parseInt(args[3]);
			waitMillis = Long.parseLong(args[4]);
			
		}
		if(args.length > 5) {
			
			cCount = Integer.parseInt(args[5]);
			
		}
		
		CombinedTestCollector t = new CombinedTestCollector(hostname, httpPort, udpPort);
		// Create and register some collectors
		for(int i = 0; i < cCount; i ++) {
			
			try {
				
				t.register();
				
			} catch(IOException ioe) {
				
				_LOG.log(Level.SEVERE, "Unable to register collector", ioe);
				
			}
		}
		
		for(String cID : t.getCollectors()) {
		
			SimpleTestCollector st = new SimpleTestCollector(cID, hostname, udpPort);
			
			Thread thd = new Thread(new UDPSendExecutor(st, cycles, waitMillis));
			thd.setDaemon(false);
			thd.setName(String.format("TestExecutorThread-%d", st.getLocalUdpPort()));
			
			thd.start();

		}
	}
}
