package mjerez.jmicrobench.reports;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import mjerez.jmicrobench.BenchmarkRunner.Phase;
import mjerez.jmicrobench.PhasesProfiler;
import mjerez.jmicrobench.TimeElement;
import mjerez.jmicrobench.TimeProfiler;
import mjerez.jmicrobench.jtpl.Template;


/** Generates the code info area.
 * A HTml block is generated from one {@link TimeProfiler} containing
 *  info about the executed code.
 * @author mjerez
 */
public class CodeArea {
	
	
	/* default height of the title section */
	private static final int code_title_Height = 45;
	
	/* chartArea & chartBlock & variables in the template. */	
	private static final String CODEAREA = "codeArea";
	private static final String CODEBLOCK = "codeBlock";
	
	private static final String VAR_AREASIZE = "small";
	private static final String VAR_CODESIZE = "codeSize";	
	private static final String VAR_LOAD = "load";
	private static final String VAR_WARMUP = "warmup";
	private static final String VAR_PROFILE = "profile";
	private static final String VAR_CODETITLE = "codeTitle";
	private static final String VAR_CODEID = "codeId";
	private static final String VAR_CODE = "code";
	
	
	/** The {@link TimeProfiler} to render. */
	private final PhasesProfiler phasesProfiler;
	/** The render options */
	private final ReportOptions options;
	

	/** Creates a object responsible for generate the code Area.
	 * @param phasesProfiler object from which extract the code data.
	 * @param options the render options. */
	public CodeArea(PhasesProfiler phasesProfiler, ReportOptions options){
		this.phasesProfiler = phasesProfiler;
		this.options = options;
	}
	
	/** Generates the codeArea.*/
	public void generatesCodeArea(){
		if (!options.drawCode) return;
		
		TimeProfiler tpLoad = phasesProfiler.getProfiler(Phase.LOADING);
		TimeProfiler tpWarmup = phasesProfiler.getProfiler(Phase.WARMUP);
		TimeProfiler tpProfile = phasesProfiler.getProfiler(Phase.PROFILING);		
	
		/* areaSize */
		if(options.smallSize)
			options.HtmlTemplate.assign(VAR_AREASIZE, VAR_AREASIZE);
		else 
			options.HtmlTemplate.assign(VAR_AREASIZE, "");
		
		/* phases */
		options.HtmlTemplate.assign(VAR_LOAD, tpLoad.getLoopsInfo());
		options.HtmlTemplate.assign(VAR_WARMUP, tpWarmup.getLoopsInfo());
		options.HtmlTemplate.assign(VAR_PROFILE, tpProfile.getLoopsInfo());
		
		/* #codeSize# */
		int heightL = (options.drawLoad)? tpLoad.getChart().getHeight(): 0;
		int heightP = (options.drawProfiling)? tpProfile.getChart().getHeight(): 0;		
		options.HtmlTemplate.assign(VAR_CODESIZE, heightL+heightP-code_title_Height+"px");	
		
		/* #code#  */
		TimeProfiler tp = (options.drawLoad)? tpLoad:tpProfile;
		Collection<TimeElement> elems = tp.getElements();
		for (TimeElement tElement : elems) {			
			generateCodeBlock(tElement);
		}
		
		options.HtmlTemplate.parse(CODEAREA);
	}
	
	
	/** Generates each codeBlock.*/
	private void generateCodeBlock(TimeElement elem){
		
		Template tpl = options.HtmlTemplate;
		int start = elem.getStartLine()+1, end = elem.getEndLine()-1;
		String line, codeTitle;		
		
		/* #codeTitle# */
		if(end < start)
			line = "No code.";
		else if(end == start)
			line = "Line "+ start+".";
		else
			line = "Lines "+start+" to "+end+".";
		codeTitle = "<strong style='color:"+elem.getColor()+"'>"+
					elem.getTitle()+"</strong><br />Class: "+
					elem.getRunClass()+" <br />"+ line;
		tpl.assign(VAR_CODETITLE, codeTitle);		
		
		/* #codeID# */		
		tpl.assign(VAR_CODEID, elem.getCodeId());
		
		/* #code# */
		if(elem.getEndLine()>=elem.getStartLine())
			tpl.assign(VAR_CODE, getCode(elem));
		else 
			tpl.assign(VAR_CODE,"");
		
		tpl.parse(CODEAREA+"."+CODEBLOCK);
	}

	
	/** Get the executed code from the source file.
	 * @return The executed code.
	 */
	private String getCode(TimeElement elem){		
		StringBuilder sb =  new StringBuilder(200);		
		File f = null;
		
		try {
			String classPath = elem.getRunClass().replace(".","/")+".java";
			Class<?> runclass = Class.forName(elem.getRunClass()); 
			
			if(new URI(options.sourcesPath).isAbsolute())
				f = new File(options.sourcesPath+ classPath);
			else 
				f = new File(runclass.getResource("/").getPath()+options.sourcesPath+ classPath);			
			
			
			
			
			FileReader fr = new FileReader(f);
			LineNumberReader lnr = new LineNumberReader(fr);

			for (int i = 0; i < elem.getStartLine(); i++) {
				lnr.readLine();
			}
			for (int i = elem.getStartLine(); i < elem.getEndLine()-1; i++) {
				sb.append(lnr.readLine().trim()+"\n");
			}
			fr.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			String error = "<span style='color:red'>ERROR: <br/>"+e.getMessage()+"</span>";
			sb.append(error);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			String error = "<span style='color:red'>ERROR: <br/>"+e.getMessage()+"</span>";
			sb.append(error);
			e.printStackTrace();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			String error = "<span style='color:red'>ERROR: <br/>"+e.getMessage()+"</span>";
			sb.append(error);
			e.printStackTrace();
		} 
		
		return sb.toString();
	}
}
