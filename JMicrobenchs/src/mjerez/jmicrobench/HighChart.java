package mjerez.jmicrobench;

import java.util.HashMap;

import com.google.gson.Gson;

/** Partial implementation in Java of the HighCharts JavaScript object.
 * This object is required to render the result using the HighCharts JavaScript API.</br>
 * <a href="http://api.highcharts.com/highcharts" >www.highcharts.com</a>
 * @author mjerez
 */
public class HighChart {
	
	/* ------------------------ STATIC SECTION -------------------------- */	
	/** Used to transforms this object to JSon */
	private static final Gson gson = new Gson();	
	/** Minimum height of the bar chart */
	public static final int baseHeightBar = 250;
	/** Minimum height of the Column chart */
	public static final int baseHeightColumn = 300;
	/** The chart increases this size by each bar added to it.*/
	public static final int barHeight = 25;
	
	
	private static HashMap<String , String > colorByTitle = new HashMap<String, String>();
	private static int colorcount = 0;		
	
	/** Return a different HTml color (i.e. : #F4C799) corresponding each title.
	 * @param title
	 * @return One HTml color for each title.
	 */
	public static String getColor(String title){
		if(colorByTitle.containsKey(title))
			return colorByTitle.get(title);		if(colorcount==predefColors.length)
			colorcount = 0;
		String col = predefColors[colorcount];
		colorcount++;
		colorByTitle.put(title, col );
		return col;
	}
	
	/** Default colors of the bars */
	public final static String[] predefColors = {
		"#6f9cd1","#B5CA92","#DB843D","#80699B","#235295",
		"#568956","#AA4643","#c6a80D","#A47D7C","#44cc79"
	};
	
	/** Reconfigure two charts to be displayed together one in top of the other.
	 * @param top the chart in top position.
	 * @param bottom the chart in the bottom.
	 */
	public static void toDoubleChart(TimeProfiler top,TimeProfiler bottom){
		if(top!=null && bottom!=null){
			top.getChart().updateHeight();
			bottom.getChart().updateHeight();
			
			HighChart chartL = top.getChart();
			chartL.getChart().height = Math.round(chartL.getChart().height*0.7f);
			chartL.credits.enabled = false;
			chartL.getSubtitle().align = "left";
			
			HighChart chartR = bottom.getChart();
			chartR.getChart().height = Math.round(chartR.getChart().height*0.7f);;
			chartR.getTitle().text = "";
			chartR.getSubtitle().align = "left";
		}
	}
	
	/** Reconfigure one chart to display as the original (Single chart).
	 * @param tp the chart to be reconfigured.
	 */
	public static void toSingleChart(TimeProfiler tp){		
			HighChart chart = tp.getChart();
			chart.updateHeight();
			chart.credits.enabled = true;
			chart.getSubtitle().align = "center";		
			chart.getTitle().text = tp.getTitle();		
	}
	/* ------------------------ DINAMIC SECTION -------------------------- */
	
	/* Implementation in java of the HighChart Object */	
	
	protected chart chart  = new chart();
	private title title = new title();
	private subtitle subtitle = new subtitle();
	private xAxis xAxis = new xAxis();
	protected yAxis yAxis = new yAxis();
	protected plotOptions plotOptions = new plotOptions();
	protected legend legend = new legend();
	protected credits credits = new credits();
	private series[] series = null;
	protected String[] colors = predefColors;
	private boolean millis = true;
	
	/* !!IMPORTANT!! Keep lower case because have to match the values of the HightCharts API. */
	/** The type of chart: Columns or Bars. */
	public static enum ChartType {bar, column};
	
	protected static class chart {
		public String renderTo = "container";
		public String type = ChartType.bar.toString();
		public int marginBottom = 37;
		public int height = baseHeightBar;
		public String backgroundColor = "none";
	}	
	protected static class title {
		public String text = null;
		public String align = "center";
		public int margin = 15;
		public int y = 10;
		public int x = 0;
		public String verticalAlign = "top";
	}	
	protected static class subtitle {
		public String text = null;
		public String align = "center";
		public int y = 30;
		public int x = 0;
	}
	protected static class xAxis {
		public String[] categories = null;
		public xTitle title = new xTitle();
		public static class xTitle {
			public String text = null;
		};
		private xAxis(){}
		public xAxis(String[] categories){
			this.categories = categories;
		}
	};
	protected static class yAxis {
		public float min = 0.0F;
		public String type = "linear";
		public yTitle title = new yTitle();
		public yLabels labels = new yLabels();		
		public static class yTitle {
			public String text = "milliseconds (ms).";
			public String align = "middle";
			public int x = 0;
			public int y = 0;
		};
		public static class yLabels {
			public String overflow = "justify";
		};
	};
	protected static class plotOptions {		
		public plotSeries series = new plotSeries();		
		public class plotSeries{
			dataLabels dataLabels = new dataLabels();
			public float groupPadding = 0.1f;
		}
	}
	protected static class legend {
		public String layout = "vertical";
		public String align = "right";
		public String verticalAlign = "top";
		public int x = 0;
		public int y = 0;
		public boolean floating = true;
		public int borderWidth = 1;
		public String backgroundColor = "#FFFFFF";
		public boolean shadow = true;
		public boolean enabled = false;
	};
	protected static class credits {
		public boolean enabled = true;
		public String href = "https://github.com/M-jerez/JMicrobenchs";
		public String text = " JMicrobenchs";
		public position position = new position();
		public static class position{
			public String align = "left";
			public int x = 10;
			public int y = -5;
		}
		
	};	
	protected static class series {		
		public String name = null;
		public data[] data = null;
		public int pointStart = 0;			
		public series(String name){
			this.name = name;
		}	
	}
	protected static class data{
		String color  = null;
		String id = null;
		String name = null;
		boolean sliced = false;
		Float x = null;
		private Float y = null;
		public data(String name, float value){
			this.name = name;
			setY(value);

		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public Float getY() {
			return y;
		}
		public void setY(Float y) {
			this.y = y;
		}
	}
	protected static class dataLabels{
        public boolean enabled= true;
        public int rotation = 0;
        public int y = 0;
        public int x = 0;
        public String color = "#aac";
    }
	
	

	/** @return the height of this chart */
	public int getHeight(){
		return getChart().height;
	}
	
	

	/* ------------------------ METHODS -------------------------- */
	
	
	/** Transforms this chart in a bars chart. 
	 * <a href="http://www.highcharts.com/demo/bar-basic">see this demo in www.highcharts.com.</a> 
	 */
	public void toBars(){
		this.getChart().type = "bar";
		this.yAxis.title.y = 0;
		fixLabels();
	}
	
	/** Transforms this chart in a columns chart. 
	 * <a href="http://www.highcharts.com/demo/column-basic">see this demo in www.highcharts.com.</a> 
	 */
	public void toColumns(){
		this.getChart().type = "column";
		this.yAxis.title.y = 15;
		fixLabels();
	};
	
	/** Transforms the units of the time axis to microseconds */
	public void toMicros(){
		millis=false;
		this.yAxis.title.text = "microsecons (µs).";
	}
	
	/** Transforms the units of the time axis to milliseconds */
	public void toMillis(){
		millis=true;
		this.yAxis.title.text = "milliseconds (ms).";
	}
	
	/** Fix the labels orientation depending on the chart type (bar/column) and the numbers of bars.
	 * <a href="http://www.highcharts.com/demo/column-rotated-labels>
	 * see this demo in www.highcharts.com </a>
	 */
	private void fixLabels(){	
		int lenght = this.getxAxis().categories.length;
		if(this.getChart().type.equals(ChartType.column.toString()) && lenght>=13){			
			this.plotOptions.series.dataLabels.rotation = -90;
			this.plotOptions.series.dataLabels.y = -25;
			this.plotOptions.series.dataLabels.x = 4;
		}else{
			this.plotOptions.series.dataLabels.rotation = 0;
			this.plotOptions.series.dataLabels.y = 0;
			this.plotOptions.series.dataLabels.x = 0;
		}
	}
	
	/** update the axes and height of this chart */
	public void update(float low, float hight){
		updateAxes(low, hight);
		if(this.getxAxis()==null || this.getxAxis().categories==null) return;
		if(this.getxAxis()==null || this.getxAxis().categories==null) return;
		updateHeight();
	}
	
	
	
	/** Updates the height of the chart depending on the number of columns/bars.  */
	public void updateHeight(){	
		int lenght = this.getxAxis().categories.length;
		int minH = (this.getChart().type.equals(ChartType.bar.toString()))?
				baseHeightBar:baseHeightColumn;
		this.getChart().height = minH + (barHeight * lenght) ;
	}
	
	/** Factor used to set the time axis logarithmic or linear in bar charts  */
	public static final float log_factor_bar = 20f;
	/** Factor used to set the time axis logarithmic or linear in column charts  */
	public static final float log_factor_column = 10f;
	
	/** Set the time axis logarithmic or linear.
	 * <code>if((high/log_factor)>low) set logarithmic; else set linear;<code>.
	 * @param low The lower time in the chart.
	 * @param high The higher time in the chart.
	 */
	public void updateAxes(float low, float high){
		float min_Logarithmic;
		if(this.getChart().type.equals(ChartType.bar.toString()))
			min_Logarithmic = log_factor_bar;
		else
			min_Logarithmic = log_factor_column;		
		if((high/min_Logarithmic)>low){
			this.yAxis.type = "logarithmic";
			if(millis)
				this.yAxis.title.text = "(ms)  - Logarithmic axe.";
			else
				this.yAxis.title.text = "(µs) - Logarithmic axe.";
			this.yAxis.min = low/2F;
		}else{
			this.yAxis.type = "linear";
			if(millis)
				this.yAxis.title.text = "milliseconds (ms).";
			else
				this.yAxis.title.text = "microsecons (µs).";
			this.yAxis.min = low/2F;
		}
	}	
	
	/** Transforms this object to JSon format.
	 * @return the JSon representation of this chart. 
	 * */
	public String toJson(){
		return gson.toJson(this);
	}

	/** Gets the {@link #chart} of this object.
	 * @return the chart */
	public chart getChart() {
		return chart;
	}

	/** Sets the {@link #chart} of this object.
	 * @param chart the chart to set */
	public void setChart(chart chart) {
		this.chart = chart;
	}

	/**  Gets the title of this object.
	 * @return the title */
	public title getTitle() {
		return title;
	}

	/** Sets the title of this object.
	 * @param title the title to set */
	public void setTitle(title title) {
		this.title = title;
	}

	/** Gets the subTitle of this object.
	 * @return the subtitle */
	public subtitle getSubtitle() {
		return subtitle;
	}

	/** Sets the subtitle of this object.
	 * @param subtitle the subtitle to set */
	public void setSubtitle(subtitle subtitle) {
		this.subtitle = subtitle;
	}

	/** Gets the {@link #series} of this object.
	 * @return the series */
	public series[] getSeries() {
		return series;
	}

	/** Gets the {@link #series} of this object
	 * @param series the series to set */
	public void setSeries(series[] series) {
		this.series = series;
	}

	/** Gets the {@link #xAxis} of this object
	 * @return the xAxis */
	public xAxis getxAxis() {
		return xAxis;
	}

	/** Sets the {@link #xAxis} of this object
	 * @param xAxis the xAxis to set */
	public void setxAxis(xAxis xAxis) {
		this.xAxis = xAxis;
	}
	
	protected HighChart(){}
}
