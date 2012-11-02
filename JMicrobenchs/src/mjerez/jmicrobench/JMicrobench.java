package mjerez.jmicrobench;

/** Implements this interface to run benchmarks.
 * This Class defines only one method, All code inside this method will be
 * executed and profiled.
 * @author mjerez
 */
public interface JMicrobench {	
	
	/** All code inside this method will be executed during the benchmark */
	public abstract void runBench();

}
