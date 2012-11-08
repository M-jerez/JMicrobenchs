package mjerez.jmicrobench;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import mjerez.jmicrobench.BenchmarkRunner.Phase;
import mjerez.jmicrobench.HighChart.data;
import mjerez.jmicrobench.HighChart.series;
import mjerez.jmicrobench.HighChart.xAxis;


/** Base class used to do time profiling.</br>
 * To do time profiling just create one object of this class and call the methods {@link #startCount(String)}
 * and {@link #stopCount(String)}.</br>
 * Each object of this class Manages a group of {@link TimeElement}, one chart is generated by each object of this class.
 * @author mjerez
 */
public class TimeProfiler {
	
	
	/** The title and id of this object. */
	private final String title;
	/** If false any measure will done */
	private final boolean enabled;
	/** Number of loops corresponding to the {@link Phase#PROFILING} */
	private final int profilingLoops;
	/** The phase which this {@link TimeProfiler} is measuring*/
	private final Phase phase;
	/** Dictionary within all {@link TimeElement} of this object. */
	private HashMap<String,TimeElement> elements;	
	/** The HighChart to display in the HTml report.*/
	private HighChart hChart;

	/** Lower time of all the {@link TimeElement} in the {{@link #elements} dictionary. */
	private float low= Float.MAX_VALUE;
	/** Higher time of all the {@link TimeElement} in the {{@link #elements} dictionary. */
	private float high=0;
	
	
	/** Default constructor of {@link TimeProfiler}.
	 * @param title the title and id of this object.
	 * @param loops the number of loops during the profiling phase.
	 * @param enable flag to enable the measure of time.
	 * @param phase the phase which this profile will measure.
	 */
	TimeProfiler(String title,int loops, boolean enable, Phase phase) {		
		this.title = title;
		this.enabled = enable;
		this.profilingLoops = loops;
		this.phase = phase;
		if(enable==false) return;
		elements = new LinkedHashMap<String, TimeElement>();
	}
	
	
	
	
	/** Creates or access a {@link TimeElement} with the corresponding
	 * title and starts to count the time.
	 * @param name
	 */
	public void startCount(String name){		
		if(!enabled) return;
		String key=name;
		if(!elements.containsKey(key)){			
			TimeElement elm = new TimeElement(key);			
			elm.setStartLine();
			elements.put(key, elm);
		}
		TimeElement elem = elements.get(key);
		elem.interval = System.nanoTime();		
	}
	
	/** Stops the {@link TimeElement} with the corresponding title.
	 * @param name of the {@link TimeElement} to stop.
	 * @throws IllegalArgumentException if the {@link TimeElement} doens't exist.
	 */
	public void stopCount(String name){
		if(!enabled ) return;
		long stop = System.nanoTime();		
		String key = name;
		if(!elements.containsKey(key)) 
			throw new IllegalArgumentException("The counter \""+key+"\" has not been initialized.");	
		TimeElement elm = elements.get(key);
		elm.elapsedTime += (float)(stop-elm.interval) ;	
		elm.setEndLine();
	}
	
	
	
	/**  Gets All {@link TimeElement} of this object.
	 * @return A collection of {@link TimeElement} */
	public Collection<TimeElement> getElements(){
		return elements.values();
	}
	
	/** Gets the ID of this object generated from object.HashCode().
	 * @return A ID String. */
	public String getID(){
		return "chartID"+this.hashCode();
	}
	
	/** Gets An id required to render the chart in the HTml document.
	 * @return  A ID String.*/
	public String getCointainerId(){
		return "container_"+getID()+"_"+phase.toString();
	}
	
	/** Gets the {@link HighChart} of this object.
	 * @return A {@link HighChart} object. */
	public HighChart getChart() {
		if(hChart!=null) return hChart;
		HighChart chart = new HighChart();
		chart.getChart().renderTo = getCointainerId();
		chart.getTitle().text = getTitle();
		chart.getSubtitle().text = phase.toString()+" phase. "+profilingLoops+" times executed.";		
		
		chart.setSeries(getSeriesInMilliseconds());
		if(high*1000<100.0f){
			chart.toMicros();
			chart.setSeries(getSeriesInMicroseconds());
		}
			
		chart.setxAxis(new xAxis(getCategories()));
		
		chart.update(low, high);		
		hChart = chart;
		return hChart;
	}
	
	/** Gets the {@link series} object required to draw the hightChart in milliseconds precision.
	 * @return A {@link series} object.*/
	private series[] getSeriesInMilliseconds(){		
		low= Float.MAX_VALUE;
		high=0;
		Collection<TimeElement> elms = elements.values();
		series[] series = new series[1];
		series[0] = new series("Average Time");
		data[] data = new data[elms.size()];
		int i = 0;
		for (TimeElement tElem : elms) {
			data[i] = new data(null,tElem.calculeTimePerCicleMillis(profilingLoops));
			data[i].setColor(tElem.getColor());
			if(data[i].getY()<low)low = data[i].getY();
			if(data[i].getY()>high) high=data[i].getY();
			i++;
		}
		series[0].data = data;
		return series;
	}
	
	/** Gets the {@link series} object required to draw the hightChart in microseconds precision.
	 * @return A {@link series} object.*/
	private series[] getSeriesInMicroseconds(){	
		low= Float.MAX_VALUE;
		high=0;
		Collection<TimeElement> elms = elements.values();
		series[] series = new series[1];
		series[0] = new series("Average Time");
		data[] data = new data[elms.size()];
		int i = 0;
		for (TimeElement tElem : elms) {
			data[i] = new data(null,tElem.calculeTimePerCicleMicros(profilingLoops));
			data[i].setColor(tElem.getColor());
			if(data[i].getY()<low)low = data[i].getY();
			if(data[i].getY()>high) high=data[i].getY();
			i++;
		}
		series[0].data = data;
		return series;
	}
	
	
	/** Gets all the categories of the hightChart.
	 * @return An array of strings*/
	private String[] getCategories(){
		Set<String> keys = elements.keySet();
		String[] categories = new String[keys.size()];	
		int i = 0;
		for (String key : keys){	
			categories[i] = key;
			i++;
		}
		return categories;
	}
	


	/** Gets this object in cvs format containing only info about time performance.
	 * @return A String in CSV format */
	public String getCSV(){
		if(!enabled ) return null;
		String s = "Category; time (millis) \n";
		String[] cat = getCategories();
		Object[] data = getSeriesInMilliseconds();
		for (int i = 0; i < data.length; i++) {
			s += cat[i]+";"+data[i]+"\n";
		}
		return s;
	}

	/** Gets the title of this object.
	 * @return the title. */
	public String getTitle() {
		return title;
	}
	
	
	
	

}