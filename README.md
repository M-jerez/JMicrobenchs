# ![LOgo](https://raw.github.com/M-jerez/JMicrobenchs/master/media/Jmicrobench-logox32.png) [ JMicrobenchs](http://m-jerez.github.com/JMicrobenchs/)
##Java micro-benchmarks  made easy.

### What is this library for.
This API is intended for make micro-benchmarks in java and generate nice html reports using [HighChars.com](http://www.highcharts.com/). It doesn't provide advanced profile functions, abnormal data detection or any other. *The main objective was to create a library for generate nice reports showing the performance's chart and the executed code at the same time,* leaving to the programmer the responsibility of the benchmark's reliability.

Sample report: *this is an just an image, the real report is generated in html. [demos web page.] (http://m-jerez.github.com/JMicrobenchs/)*

![Jmicrobech sample report](https://raw.github.com/M-jerez/JMicrobenchs/master/media/report-sample.png)

### What's a micro-benchmark. 
A micro-benchmark measures and compares the time performance of code portions, this can be useful to make a general image of the code performance, to find bottlenecks on it or unusual slow behavior, nevertheless micro-benchmarks are not intended for software profiling. 

To get more info about micro-benchmarks can read the following docs:
* [Caliper micro-benchmarks.](https://code.google.com/p/caliper/wiki/JavaMicrobenchmarks)
* [Robust benchmarking by Brent Boyer at ibm.](http://www.ibm.com/developerworks/java/library/j-benchmark1/index.html)
* [Anatomy of a flawed microbenchmark by Brian Goetz at ibm.](http://www.ibm.com/developerworks/java/library/j-jtp02225/index.html)
* [Benchmark tips, oracle HotSpot documentation.](https://wikis.oracle.com/display/HotSpotInternals/MicroBenchmarks) 
 
### Benchmark phases.
Each benchmark consist of three different phases while the code is executed a number times.
* *Loading:* This is the first phase and is executed only once. It shows useful results only when the program is being rung by the first time and java must load all classes. This phase is intended to measure cold start performance.
* *Warmup:* This phase must be executed many times to let the JVM make some code optimization, [JIT compile](http://en.wikipedia.org/wiki/Just-in-time_compilation), garbage collection, etc... During this phase the performance is not measured and will not generate any report.
* *Profiling:* This is the main phase and it doesn't must be executed many times but enough to calculate a reliable average. Execute this phase more than required could leave to incorrect results as garbage collection or other processes can distort this results.  
  

## Show me the code.

### Write the code.
Implement `JMicrobench` and put the code you want to profile inside the the `runBech()` method. To profile one portion of code create a `TimeProfiler` object and call it's function `startCount()` and `stopCount`.
```java
 public class FirstTest implements JMicrobench {
	public void runBench() {
		String t1 = "void", t2 = "create HashMap", t3 = "HashMap put";
		
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
}
```
As you can see it is not possible to create a `TimeProfiler` directly, all TimeProfilers are created/accessed in a static way through the `Registry` class, this class is basically a Map of TimeProfilers objects identified by it's title. This behavior allows to access the same `TimeProfiler` in any point of the executed program. 

As you can also see one of it's drawbacks is that write this kind of benchmark is too verbose, but this allows to make accurate measures, nested calls etc...


### Run the benchmark.
Create a new instance of your test class, in this case `FirstTest`; create a new `BenchmarkRunner` and run it `run()`;  finally create one of the available `Reports` and write to File or Output.
```java
	public static void main(String[] args) {		
		int profileLoops = 50;
		int warmupLoops = profileLoops * 1000;
		
		FirstTest firstTest = new FirstTest();

		/* create & run the benchmark */
		new BenchmarkRunner(warmupLoops, profileLoops,firstTest).run();

		/* generate the report & write to file */	
		ReportOptions options = new  ReportOptions("../test/");
		new FullWebReport(options).writeFullWebToFile("C:/Users/mjerez/Desktop/report.html");

	}
```

The `RenderOptions` object is required to configure the generated report, in this case we only have set the relative path were are the java source files. if the source files ".java" and the executables ".class" are in the same directories this path is a void string -> "". To see more options go to [javadoc.](http://m-jerez.github.com/JMicrobenchs/doc/mjerez/jmicrobench/reports/ReportOptions.html)

### Watch results.
The next image is a snapshot of the html generated from the previous code. One report like this will be generated for each `TimeProfiler` object created during the benchmark execution.

![FirstTest Jmicrobech report](https://raw.github.com/M-jerez/JMicrobenchs/master/media/report-FirstTest.png)


### Where to find more info.
* [JMicrobech web page.](http://m-jerez.github.com/JMicrobenchs/)
* [Javadoc.](http://m-jerez.github.com/JMicrobenchs/doc/)

