package mjerez.jmicrobench.reports;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import mjerez.jmicrobench.PhasesProfiler;
import mjerez.jmicrobench.Registry;

/** Base class to generates the HTml reports. The generated HTml doesn't include 
 * all required js and css files.
 * Each report consist of two areas the Chart area and the Code area,  
 * @author mjerez
 *
 */
public class HtmlReport {
	
	
	
	
	
	/* chartArea & chartBlock & variables in the template. */	
	public static final String CODEAREA = "codeArea";
	public static final String CODEBLOCK = "codeBlock";
	
	public static final String VAR_CODESIZE = "codeSize";	
	public static final String VAR_CODETITLE = "codeTitle";
	public static final String VAR_CODEID = "codeId";
	public static final String VAR_CODE = "code";
		
	
	/** Render options of the chart. */
	private final ReportOptions options;	

	
	/** Creates a object responsible to generate an HTml section 
	 * with default {@link ReportOptions}. */
	public HtmlReport() {
		if(Registry.getAllPhasesProfilers().size()==0)
			throw new RuntimeException("No benchmark or TimeProfiler has been created");
		this.options = new ReportOptions();
	}
	

	/** Creates a Web HTml section with the indicated {@link ReportOptions}.
	 * @param options
	 */
	public HtmlReport(ReportOptions options){
		if(Registry.getAllPhasesProfilers().size()==0)
			throw new RuntimeException("No benchmark or TimeProfiler has been created");
		this.options = options;
	}
	
	
	/* ------------------------ METHODS -------------------------- */
	

	/** Writes the report to the indicated filename.
	 * @param filename i.e: "c:/reports/report1.HTml". */
	public void writeToFile(String filename){
		try {
			// Create file
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);			
			write(out);
			out.close();			
			System.out.println("Created report on: "+filename);
		} catch (Exception e) {
			e.printStackTrace();
		}							
	}
	
	/** Writes the report to the indicated out.
	 * @param out the output {@link Writer}*/
	public void write(Writer out) throws IOException{		
		out.write(getHtml());
	}
	
	
	/** Gets the generated HTml.
	 * @return A string in HTml format*/
	protected String getHtml(){
		StringBuffer sb = new StringBuffer(300);
		Collection<PhasesProfiler> d = Registry.getAllPhasesProfilers();
		
		for (PhasesProfiler prof : d) {			
			ChartArea chartArea = new ChartArea(prof, options);
			chartArea.genarateChartArea();
			CodeArea codeArea = new CodeArea(prof, options);
			codeArea.generatesCodeArea();	
			sb.append(options.HtmlTemplate.out());
			options.resetTemplate();
		}			
		return sb.toString();
	}
}
