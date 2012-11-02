package mjerez.jmicrobench;

import mjerez.jmicrobench.BenchmarkRunner.Phase;

/** This Class groups the {@link TimeProfiler} by phases of the benchmark.
 * @author mjerez
 */
public class PhasesProfiler {
	
	/** the title of this object and all it's {@link TimeProfiler} profilers */
	final String title;
	/** The profiler used during the {@link BenchmarkRunner.Phase#LOADING} phase.
	 * This profiler is useful only in cold starts, when the classes aren't loaded
	 * yet by the java ClassLoader*/
	final private TimeProfiler tpLoad;
	/** The profiler used during the {@link BenchmarkRunner.Phase#WARMUP} phase. 
	 * this profiler will not measure any time. just run the code many times
	 * to allow the JVM do some optimization and probably JIT compile*/
	final private TimeProfiler tpWarmup;
	/** The profiler used during the {@link BenchmarkRunner.Phase#PROFILING} phase.
	 * This is the main profiler */
	final private TimeProfiler tpRunning;
	
	/** Creates one {@link TimeProfiler} for each one of the phases of the benchmark.
	 * @see BenchmarkRunner.Phase
	 * @param title the title used to construct the profilers.
	 */
	public PhasesProfiler(String title) {
		this.title = title;
		this.tpLoad =  new TimeProfiler(title,1,true, Phase.LOADING);
		this.tpWarmup = new TimeProfiler("", 0, false, Phase.WARMUP);
		this.tpRunning = new TimeProfiler(title,BenchmarkRunner.getProfilengLoops(),true, Phase.PROFILING);
	}
	
	/** Return the corresponding {@link TimeProfiler} depending on the phase.
	 * @param phase The phase of the benchmark.
	 * @return The {@link TimeProfiler} of corresponding to phase.
	 */
	public TimeProfiler getProfiler(Phase phase){
		switch (phase) {
		case LOADING: return tpLoad; 
		case PROFILING: return tpRunning;
		default: return tpWarmup;
		}
	}

}
