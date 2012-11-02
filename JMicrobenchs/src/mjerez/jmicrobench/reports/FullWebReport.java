package mjerez.jmicrobench.reports;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.Scanner;

import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.jtpl.Template;




/** Makes full HTml report of all executed benchmarks, including all required js and css files.
 * This object generate a web page within all JavaScript libraries and CSS included in the HTml-head and puts
 * the partial report generate by {@link HtmlReport} in the HTml-body section.
 * @author mjerez
 *
 */
public class FullWebReport{
	
	/* Required files */
	private static final String CSSFILE = "files/jbench-style.css";
	private static final String JQUERYFILE = "files/jquery-1.8.2.min.js";
	private static final String HIGHCHARTSFILE = "files/highcharts.js";
	private static final String JBENCHFILE = "files/jbench.js";
	
	/* Template file and variables to replace in the template */
	private static final String FULLWEBFILE = "files/fullweb.html";
	private static final String VAR_STYLE = "style";
	private static final String VAR_CONTENT = "content";
	private static final String VAR_SCRIPTS = "scripts";


	private final ReportOptions options;	
	

	/** Creates a Web Page with default {@link ReportOptions}. */
	public FullWebReport() {
		this.options = new ReportOptions();
	}
	

	/** Creates a Web Page with the indicated {@link ReportOptions}.
	 * @param options
	 */
	public FullWebReport(ReportOptions options){
		if(Registry.getAllPhasesProfilers().size()==0)
			throw new RuntimeException("No benchmark or TimeProfiler has been created");
		this.options = options;
	}
	
	/* ------------------------ METHODS -------------------------- */
	
	
	/** Writes the report to the indicated filename.
	 * @param filename i.e: "c:/reports/report1.HTml". */
	public void writeFullWebToFile(String filename){		
		try {
			// Create file
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);			
			writeFullWeb(out);
			out.close();			
			System.out.println("Created report on: "+filename);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}	
	


	/** Writes the report to the indicated out.
	 * @param out the output {@link Writer}*/
	public void writeFullWeb(Writer out) {			
			try {
				InputStream is = this.getClass().getResourceAsStream(FULLWEBFILE);
				InputStreamReader isr = new InputStreamReader(is);
				Template tpl = new Template(isr);
				
				/* #styles# */
				tpl.assign(VAR_STYLE, 
						new Scanner(this.getClass().getResourceAsStream(CSSFILE)).useDelimiter("\\Z").next()+"\n"
						);			
				
				/* #scripts# */
				tpl.assign(VAR_SCRIPTS, 
						new Scanner(this.getClass().getResourceAsStream(JQUERYFILE)).useDelimiter("\\Z").next()+"\n"+
						new Scanner(this.getClass().getResourceAsStream(HIGHCHARTSFILE)).useDelimiter("\\Z").next()+"\n"+
						new Scanner(this.getClass().getResourceAsStream(JBENCHFILE)).useDelimiter("\\Z").next()+"\n"
						);
				/* #content# */				
				HtmlReport report = new HtmlReport(options);
				tpl.assign(VAR_CONTENT, report.getHtml());
				
				out.write(tpl.out());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
						
	}
	
	
	
	

	
}
