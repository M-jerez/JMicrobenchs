package com.test;

import java.util.HashMap;

import mjerez.jmicrobench.BenchmarkRunner;
import mjerez.jmicrobench.JMicrobench;
import mjerez.jmicrobench.Registry;
import mjerez.jmicrobench.TimeProfiler;
import mjerez.jmicrobench.reports.FullWebReport;
import mjerez.jmicrobench.reports.ReportOptions;

public class FirstTest implements JMicrobench {	

	public void runBench() {
		String t1 = "void", t2 = "HashMap", t3 = "HashMap-put";
		
		TimeProfiler tp = Registry.getTimeProfiler("FirstTest Performance");		
		
		tp.startCount(t1);
		/* Nothing Executed here */
		/* time consumed by startCount() & stopCount() */
		tp.stopCount(t1);

		/* Measures the time required to create a HashMap. */
		tp.startCount(t2);
		HashMap<String, String> h = new HashMap<String, String>();
		tp.stopCount(t2);

		/* Measures the time required for the put operation */
		tp.startCount(t3);
		h.put("hello", "world");
		tp.stopCount(t3);	
		
	}
	
	
	public static void main(String[] args) {
		
		int profileLoops = 100;
		int warmupLoops = profileLoops * 1000;
		
		FirstTest firstTest = new FirstTest();

		/* create & run the benchmark */
		new BenchmarkRunner(warmupLoops, profileLoops,firstTest).run();
		boolean drawLoad = false, drawProfiling=true, drawCode= true ,  bars=true, smallSize= true;
		String path= "../test/";
		/* generate the report & write to file */	
		ReportOptions options = new  ReportOptions(drawLoad, drawProfiling, drawCode, bars, smallSize, path);
		new FullWebReport(options).writeFullWebToFile("C:/Users/mjerez/Desktop/report.html");

	}

}
