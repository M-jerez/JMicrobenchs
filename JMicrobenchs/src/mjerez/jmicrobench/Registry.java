package mjerez.jmicrobench;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/** 
 * This Class acts as a central Registry of all {@link TimeProfiler} generated during the benchmarks.
 * 
 * To enable all Classes to access or create one {@link TimeProfiler}, this class offers
 * an static access to only one {@link TimeProfiler} Dictionary.
 * 
 * As the access is static, if more than one benchmark is running during the program execution
 * all the {@link TimeProfiler} will be in the same dictionary regardless of the benchmark
 * in which the {@link TimeProfiler} is created or accessed.
 * 
 * Two different benchmarks can't access or create the same {@link TimeProfiler} even if it
 * have the same title.
 * @author mjerez
 */
public class Registry {	

	
	
	
	private static final HashMap<BenchmarkRunner, LinkedHashMap<String, PhasesProfiler>> benchMarks = 
			new  HashMap<BenchmarkRunner, LinkedHashMap<String,PhasesProfiler>>();
			
			
	
	/** Creates or Access the {@link TimeProfiler} specified by the title and the id of the
	 * current running benchmark.
	 * Only returns the {@link TimeProfiler} of the current or last running benchmark.
	 * @param title The title required to create the {@link TimeProfiler}.
	 * @return The corresponding {@link TimeProfiler}.
	 */
	public static synchronized TimeProfiler getTimeProfiler(String title){
		
		LinkedHashMap<String, PhasesProfiler> phaseProfilers = getBenchMark();		
		String key = title;
		if(phaseProfilers.containsKey(key))
			return phaseProfilers.get(key).getProfiler(BenchmarkRunner.getPhase());
		
		PhasesProfiler phasesp = new PhasesProfiler(title);		
		phaseProfilers.put(key, phasesp);
		return phasesp.getProfiler(BenchmarkRunner.getPhase());		
	}	
	
	/** Gets the {@link LinkedHashMap} associated with the current running benchmark
	 * @return A {@link LinkedHashMap} within the {@link PhasesProfiler}.
	 */
	private static LinkedHashMap<String, PhasesProfiler> getBenchMark(){
		BenchmarkRunner bench = BenchmarkRunner.getCurrentBench();
		if(benchMarks.containsKey(bench)){
			return benchMarks.get(bench);
		}
		LinkedHashMap<String, PhasesProfiler> phaseProfilers = new LinkedHashMap<String, PhasesProfiler>();
		benchMarks.put(bench, phaseProfilers);
		return phaseProfilers;
	}
	
	
	/** @return All the stored {@link PhasesProfiler} 
	 * within it's corresponding {@link TimeProfiler}. */
	public static Collection<PhasesProfiler> getAllPhasesProfilers(BenchmarkRunner bench){		
		if(!benchMarks.containsKey(bench))
			throw new IllegalArgumentException(
					"The Benchmark has not yet been run.");
		return benchMarks.get(bench).values();
	} 
	
	private Registry(){}

}
