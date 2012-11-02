package mjerez.jmicrobench.reports;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mjerez.jmicrobench.BenchmarkRunner.Phase;
import mjerez.jmicrobench.jtpl.Template;

/** Contains all the parameters required to configure the generated reports.
 * As this object is shared between all the classes that renders the charts and code this object 
 * also includes the {@link Template} in which render it.
 * @author mjerez
 */
public class ReportOptions {
	
	
	/* Template filename */
	private static final String reportfile = "files/JbenchReport.html";
	
	/** If true the generated report will contain info about {@link Phase#LOADING}.</br>
	 * False by default */
	public final boolean drawLoad;
	/** If true the generated report will contain info about {@link Phase#PROFILING} info.</br> 
	 * True by default*/
	public final boolean drawProfiling;
	/** If true the generated report will contain info about executed code.</br>
	 * True by default. */
	public final boolean drawCode;
	/** True to draw bar charts, false to draw columns charts.</br> 
	 * True by default. */
	public final boolean bars;
	/** If true, chartsArea and codeArea will be displayed in the same row at the 50% of the space.</br>
	 * True by default. */
	public final boolean smallSize;
	/** relative path from the binaries to the source. This value is required to render the codeArea.</br>
	 * The system makes use of the function {@link Class#getResourceAsStream(String)} combined with this path
	 * to find the source code. */
	public final String sourcesPath;	
	/** HTml template to prints the results */
	public Template HtmlTemplate;
	

	/** Creates the object with all the indicated values.
	 * @param drawLoad
	 * @param drawProfiling
	 * @param drawCode
	 * @param bars
	 * @param smallSize
	 * @param sourcesPath
	 */
	public ReportOptions(boolean drawLoad, boolean drawProfiling, boolean drawCode, 
			boolean bars, boolean smallSize, String sourcesPath){
		this.drawLoad = drawLoad;
		this.drawProfiling = drawProfiling;
		this.bars = bars;
		this.drawCode = drawCode;
		this.smallSize = smallSize;
		this.sourcesPath = sourcesPath;
		HtmlTemplate = initTemplate();
	}
	
	/** Creates the object with default values and the indicated sourcesPath.
	 * @param sourcesPath
	 */
	public ReportOptions(String sourcesPath){
		drawLoad = false;
		drawProfiling = true;
		bars = true;
		drawCode = true;
		smallSize =true;
		this.sourcesPath = sourcesPath;
		HtmlTemplate = initTemplate();
	}
	
	/** Creates the object with all default values */
	public ReportOptions(){
		drawLoad = false;
		drawProfiling = true;
		bars = true;
		drawCode = true;
		smallSize =true;
		this.sourcesPath = "";
		HtmlTemplate = initTemplate();
	}

	/** @return  The template to draw the chart and code */
	private Template initTemplate(){
		try {
			InputStream is = this.getClass().getResourceAsStream(reportfile);
			InputStreamReader isr = new InputStreamReader(is);
			return new Template(isr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/** Resets the HtmlTemplate. */
	public void resetTemplate(){
		HtmlTemplate = initTemplate();
	}

}
