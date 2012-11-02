package mjerez.jmicrobench.reports;


import mjerez.jmicrobench.BenchmarkRunner.Phase;
import mjerez.jmicrobench.HighChart;
import mjerez.jmicrobench.PhasesProfiler;
import mjerez.jmicrobench.TimeProfiler;
import mjerez.jmicrobench.jtpl.Template;




/** Generates one HTml block from one {@link TimeProfiler}.
 * It's possible to obtain two different HTml-blocks from one {@link TimeProfiler}, 
 * the chart-block and the code-block.
 * @author mjerez
 *
 */
public class ChartArea {
	
	/* chartArea & chartBlock & variables in the template. */	
	private static final String CHARTAREA = "chartArea";	
	private static final String CHARTBLOCK = "chartBlock";
	
	private static final String VAR_AREASIZE = "small";
	private static final String VAR_SCRIPT = "script";
	private static final String VAR_BOTTOM = "bottom";
	private static final String VAR_CONTAINERID = "containerID";
	
	
	/** The {@link TimeProfiler} to render. */
	private final PhasesProfiler phasesProfiler;
	/** The render options */
	private final ReportOptions options;
	

	/** Creates a object responsible for generate the chart Area.
	 * @param phasesProfiler object from which extract the time data.
	 * @param options the render options. */
	public ChartArea(PhasesProfiler phasesProfiler, ReportOptions options){
		
		this.phasesProfiler = phasesProfiler;
		this.options = options;
		
		TimeProfiler tpLoad = phasesProfiler.getProfiler(Phase.LOADING);
		TimeProfiler tpProfile = phasesProfiler.getProfiler(Phase.PROFILING);
		
		if(options.drawProfiling && options.drawLoad){			
			HighChart.toDoubleChart(tpLoad,tpProfile);
			if(options.bars == false){
				tpLoad.getChart().toColumns();
				tpProfile.getChart().toColumns();
			}			
		}else if(options.drawLoad){
			HighChart.toSingleChart(tpLoad);
			if(options.bars == false)
				tpLoad.getChart().toColumns();			
		}else if(options.drawProfiling){
			HighChart.toSingleChart(tpProfile);
			if(options.bars == false)
				tpProfile.getChart().toColumns();
		}			
	}
	
	/** Generates the chartArea */
	public void  genarateChartArea(){		
		TimeProfiler tpLoad = phasesProfiler.getProfiler(Phase.LOADING);
		TimeProfiler tpProfile = phasesProfiler.getProfiler(Phase.PROFILING);
		
		/* areaSize */
		if(options.smallSize)
			options.HtmlTemplate.assign(VAR_AREASIZE, VAR_AREASIZE);
		else 
			options.HtmlTemplate.assign(VAR_AREASIZE, "");
		
		if(options.drawProfiling && options.drawLoad){			
			generateChartBlock(tpLoad, false);
			generateChartBlock(tpProfile, true);
		}else if(options.drawLoad)
			generateChartBlock(tpLoad, false);			
		else if(options.drawProfiling)
			generateChartBlock(tpProfile, false);		
			
		options.HtmlTemplate.parse(CHARTAREA);
	}
	

	/** Generates each chartBlock.
	 * @param tp the {@link TimeProfiler} to render.
	 * @param bottom if true makes the chart render correctly bottom of another chart.
	 */
	private void generateChartBlock(TimeProfiler tp, boolean bottom){		
		String optsName = "options_"+tp.getID();
		String chartOpts = "var "+optsName+" = "+tp.getChart().toJson()+"; \n ";
		String highChart = "var chart_"+tp.getID()+" = new Highcharts.Chart("+optsName+");";		
		
		Template tpl = options.HtmlTemplate;
		tpl.assign(VAR_SCRIPT,	chartOpts+"$(document).ready(function() {"+highChart+"});");
		if(bottom) 
			tpl.assign(VAR_BOTTOM,"bottom");
		else 
			tpl.assign(VAR_BOTTOM,"");
		tpl.assign(VAR_CONTAINERID, tp.getCointainerId());
		
		tpl.parse(CHARTAREA+"."+CHARTBLOCK);		
	}
	
	
	
	
	

}
