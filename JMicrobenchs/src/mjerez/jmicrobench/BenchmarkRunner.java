package mjerez.jmicrobench;




/**
 * Base class which runs the Benchmarks. </br>
 * 
 * Call the {@link #run()} method to run the benchmark, this method is synchronized which means
 * only one benchmark can be running at the same time.</br>
 * 
 * Set {@link #printPhase} to true before start any benchmark to print a message to {@link System#err} when
 * each phase begins and ends, use this function together with the java compiler flag -verbose, this can help you
 * to identify which classes are loaded during each one of the phases.</br>
 * To obtain a correct benchmark neither the garbage collector nor any other class must be loaded during the 
 * profiling phase.
 *
 * @author mjerez
 */
public class BenchmarkRunner {
	
	/* ------------------------------- STATIC SECTION -------------------------- */
	

	 /** The static fields {@link #phase} and {@link BenchmarkRunner#profilingLoops} always 
	  * point to the  current running benchmark.</br> */
	
	/** The possible phases of the benchmark: stopped, loading, warmup and profiling. */
	public static enum Phase{ STOPPED, LOADING, WARMUP,  PROFILING};	
	
	/** If true the system will print messages to {@link System#err} when the phases begins or ends. */
	private static boolean printPhase = false;	


	/** Current phase of the current bechmark */
	private static Phase phase = Phase.STOPPED;
	
	/** number of loops in the {@link Phase#PROFILING} of the current running benchmark. */
	private static int profileLoops;
	
	/** the current benchmark running */
	private static BenchmarkRunner currentBench;

	/** Gets the current {@link Phase} of the current running benchmark.
	 * @return the current {@link Phase} */
	public synchronized static Phase getPhase(){
		return phase;
	}
	
	/** Gets the number of loops in the {@link Phase#PROFILING} of the current running benchmark.
	 * @return the number of loops. */
	public synchronized static int getProfilengLoops(){
		return profileLoops;
	}
	
	
	/** Enable/disable the functionality to print to the system.err when each phase starts and ends.
	 * @param printPhase */
	public static void setPrintPhase(boolean printPhase) {
		BenchmarkRunner.printPhase = printPhase;
	}
	
	
	/** Gets the current runnig benchmark 
	 * @return A {@link BenchmarkRunner};
	 */ 
	public static BenchmarkRunner getCurrentBench() {
		return currentBench;
	}
	
	/* ------------------------ DINAMIC SECTION ------------------------ */
	
	
	
	
	/** number of loops of the warm-up phase */
	private final int warmupLoops; 
	/** number of loops of the profiling-phase phase */
	private final int profilingLoops;
	/** the {@link BenchmarkRunner} to run */
	private final JMicrobench bench;
	
	/** Creates a benchmark with the defined loops, and 1 loop for the loading phase.
	 * @param warmupLoops Loops that will be executed during the {@link Phase#WARMUP} phase.
	 * @param runLoops Loops that will be executed during the {@link Phase#PROFILING} phase.
	 */
	public  BenchmarkRunner( int warmupLoops, int runLoops, JMicrobench bench) {	
		this.warmupLoops = warmupLoops;
		this.profilingLoops = runLoops;
		this.bench = bench;
	}

	/** Runs the benchmark. */
	public  synchronized void run(){
		profileLoops = profilingLoops;
		currentBench = this;
		// load phase.			
		if(printPhase) 
			System.err.println("\nStart "+Phase.LOADING+" phase : "+this.toString());
		phase = Phase.LOADING;
		for (int i = 0; i < 1; i++) {
			bench.runBench();
		}
		if(printPhase) 
			System.err.println("End "+Phase.LOADING+" phase : "
		+this.toString()+"\n");				
		// WarmUp phase.
		if(printPhase) 
			System.err.println("\nStart "+Phase.WARMUP+" phase : "
		+this.toString());
		phase = Phase.WARMUP;
		for (int i = 0; i < warmupLoops; i++) {
			bench.runBench();
		}	
		if(printPhase) 
			System.err.println("End "+Phase.WARMUP+" phase : "+this.toString()+"\n");
		// Test phase.	
		if(printPhase) 
			System.err.println("\nStart "+Phase.PROFILING+" phase : "+this.toString());
		phase = Phase.PROFILING;
		for (int i = 0; i < profilingLoops; i++) {
			bench.runBench();
		}
		phase = Phase.STOPPED;
		if(printPhase) 
			System.err.println("End "+Phase.PROFILING+" phase : "+this.toString()+"\n");		
	}
	
	


	
}
