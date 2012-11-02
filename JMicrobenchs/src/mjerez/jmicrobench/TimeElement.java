package mjerez.jmicrobench;





/** This Class corresponds to each portion of code measured.
 * @author mjerez
 */
public class TimeElement {
	
	/** factor to translate nanoseconds to milliseconds */
	public static final int nanosToMillis = 1000000;
	/** factor to translate nanoseconds to microseconds */
	public static final int nanosToMicros = 1000;
	/** set the precision of the system in decimal points of millisecond */
	public static final float precisionMillis = 100000;
	/** set the precision of the system in decimal points of microseconds */
	public static final float precisionMicros = 1000;
	
	
	/** Deep level in the {@link Thread#getStackTrace()} of the measured code.
	 * When object calls the function {@link TimeProfiler#startCount(String)} or 
	 * {@link TimeProfiler#stopCount(String)} */
	private static final int stakIndex = 3;
	
	/** The source code measured by this {@link TimeElement}. */
	protected String code;
	/** Start line of the code */
	private int startLine;
	/** End line of the code */
	protected int endLine;	
	/** Total time consumed by the measured code, by all loops */
	protected float elapsedTime;
	/** Time consumed by each interval startCount() to stopCount()*/
	protected long interval;
	/** The title of this object, will be displayed in the chart */
	private final String title;
	/** The name of the class that execute the measured code */
	private final String runClass;
	/** the name of the java source file of the class, i.e: foo.java */
	protected final String sourcefile;	
	/** A color associated to this object, will be the color of the bar in the chart. */
	private final String color;	
	/** Flag that indicates if this element is measuring or is stopped. */
	protected boolean measuring;
	
	
	/** @param title The title of this element, will be the name of this
	 * {@link TimeElement} in the chart*/
	TimeElement(String title){
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
		this.title = title;
		this.runClass = ste[stakIndex].getClassName();
		this.sourcefile = ste[stakIndex].getFileName();
		this.elapsedTime = 0;
		this.setStartLine(-1);
		this.endLine = -1;
		this.color = HighChart.getColor(title);
	}
	
	/** Extracts the start line of the executed code. */
	void setStartLine(){
		if(startLine >= 0)	return;
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
		startLine = ste[stakIndex].getLineNumber();
	}
	
	/** Extracts the End line of the executed code. */
	void setEndLine(){
		if(endLine >= 0)	return;
		StackTraceElement ste[] = Thread.currentThread().getStackTrace();
		endLine = ste[stakIndex].getLineNumber();
	}
	
	/** @return An id of code block*/
	public String getCodeId(){
		return "code_"+this.hashCode();
	}
	
	/** @return the endline of the code */
	public int getEndLine(){
		return endLine;
	}
	
	/** Calculates the time per cycle in milliseconds depending on the number of loops.
	 * @param loops the number of loops executed.
	 * @return (elapsedTime/loops) with the predefined precision.
	 */
	public float calculeTimePerCicleMillis(int loops){		
		float x = elapsedTime /(loops*nanosToMillis);		
		return Math.round(x*precisionMillis)/precisionMillis;
	}
	
	/** Calculates the time per cycle in microseconds depending on the number of loops.
	 * @param loops the number of loops executed.
	 * @return (elapsedTime/loops) with the predefined precision.
	 */
	public float calculeTimePerCicleMicros(int loops){		
		float x = elapsedTime /(loops*nanosToMicros);		
		return Math.round(x*precisionMicros)/precisionMicros;
	}

	/**
	 * @return the runClass
	 */
	public String getRunClass() {
		return runClass;
	}

	/**
	 * @return the startLine
	 */
	public int getStartLine() {
		return startLine;
	}

	/**
	 * @param startLine the startLine to set
	 */
	public void setStartLine(int startLine) {
		this.startLine = startLine;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	
	
	
	
	
	
	
}
