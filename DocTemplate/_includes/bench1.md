{% raw %}
<!-- JMicrobench Report -->
<div class="JbenchReport">
<div class="chartArea small">
<script type="text/javascript">var options_chartID2438296 = {"chart":{"renderTo":"container_chartID2438296_LOADING","type":"column","marginBottom":37,"height":333,"backgroundColor":"none"},"title":{"text":"FirstTest Performance","align":"center","margin":15,"y":10,"x":0,"verticalAlign":"top"},"subtitle":{"text":"LOADING phase. 1 times executed.","align":"left","y":30,"x":0},"xAxis":{"categories":["void","HashMap","HashMap-put","void1","HashMap1","HashMap-put1","void2","HashMap2","HashMap-put2"],"title":{}},"yAxis":{"min":2.688,"type":"linear","title":{"text":"microsecons (µs).","align":"middle","x":0,"y":15},"labels":{"overflow":"justify"}},"plotOptions":{"series":{"dataLabels":{"enabled":true,"rotation":0,"y":0,"x":0,"color":"#aac"},"groupPadding":0.1}},"legend":{"layout":"vertical","align":"right","verticalAlign":"top","x":0,"y":0,"floating":true,"borderWidth":1,"backgroundColor":"#FFFFFF","shadow":true,"enabled":false},"credits":{"enabled":false,"href":"https://github.com/M-jerez/JMicrobenchs","text":" JMicrobenchs","position":{"align":"left","x":10,"y":-5}},"series":[{"name":"Average Time","data":[{"color":"#6f9cd1","sliced":false,"y":5.864},{"color":"#B5CA92","sliced":false,"y":6.353},{"color":"#DB843D","sliced":false,"y":11.729},{"color":"#80699B","sliced":false,"y":5.865},{"color":"#235295","sliced":false,"y":6.842},{"color":"#568956","sliced":false,"y":6.353},{"color":"#AA4643","sliced":false,"y":5.376},{"color":"#c6a80D","sliced":false,"y":6.842},{"color":"#A47D7C","sliced":false,"y":6.353}],"pointStart":0}],"colors":["#6f9cd1","#B5CA92","#DB843D","#80699B","#235295","#568956","#AA4643","#c6a80D","#A47D7C","#44cc79"],"millis":false}; 
 $(document).ready(function() {var chart_chartID2438296 = new Highcharts.Chart(options_chartID2438296);});</script>
<div class="" id="container_chartID2438296_LOADING"></div>
<script type="text/javascript">var options_chartID29017928 = {"chart":{"renderTo":"container_chartID29017928_PROFILING","type":"column","marginBottom":37,"height":333,"backgroundColor":"none"},"title":{"text":"","align":"center","margin":15,"y":10,"x":0,"verticalAlign":"top"},"subtitle":{"text":"PROFILING phase. 100 times executed.","align":"left","y":30,"x":0},"xAxis":{"categories":["void","HashMap","HashMap-put","void1","HashMap1","HashMap-put1","void2","HashMap2","HashMap-put2"],"title":{}},"yAxis":{"min":0.012,"type":"linear","title":{"text":"microsecons (µs).","align":"middle","x":0,"y":15},"labels":{"overflow":"justify"}},"plotOptions":{"series":{"dataLabels":{"enabled":true,"rotation":0,"y":0,"x":0,"color":"#aac"},"groupPadding":0.1}},"legend":{"layout":"vertical","align":"right","verticalAlign":"top","x":0,"y":0,"floating":true,"borderWidth":1,"backgroundColor":"#FFFFFF","shadow":true,"enabled":false},"credits":{"enabled":true,"href":"https://github.com/M-jerez/JMicrobenchs","text":" JMicrobenchs","position":{"align":"left","x":10,"y":-5}},"series":[{"name":"Average Time","data":[{"color":"#6f9cd1","sliced":false,"y":0.024},{"color":"#B5CA92","sliced":false,"y":0.064},{"color":"#DB843D","sliced":false,"y":0.093},{"color":"#80699B","sliced":false,"y":0.166},{"color":"#235295","sliced":false,"y":0.2},{"color":"#568956","sliced":false,"y":0.21},{"color":"#AA4643","sliced":false,"y":0.147},{"color":"#c6a80D","sliced":false,"y":0.191},{"color":"#A47D7C","sliced":false,"y":0.225}],"pointStart":0}],"colors":["#6f9cd1","#B5CA92","#DB843D","#80699B","#235295","#568956","#AA4643","#c6a80D","#A47D7C","#44cc79"],"millis":false}; 
 $(document).ready(function() {var chart_chartID29017928 = new Highcharts.Chart(options_chartID29017928);});</script>
<div class="bottom" id="container_chartID29017928_PROFILING"></div>
</div>
<div class="codeArea small">
<h3 >Executed Code.</h3>
<div class="codeContainer" style="height: 626px">
<table>
<tbody>
<tr>
<td class="codeInfo"><h4><strong style='color:#6f9cd1'>void</strong><br />Class: com.test.FirstTest <br />Lines 20 to 21.</h4></td>
<td class="code"><pre><code id="code_30269696" >/* Nothing Executed here */
/* time consumed by startCount() & stopCount() */
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#B5CA92'>HashMap</strong><br />Class: com.test.FirstTest <br />Line 26.</h4></td>
<td class="code"><pre><code id="code_26022015" >HashMap<String, String> h = new HashMap<String, String>();
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#DB843D'>HashMap-put</strong><br />Class: com.test.FirstTest <br />Line 31.</h4></td>
<td class="code"><pre><code id="code_3541984" >h.put("hello", "world");
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#80699B'>void1</strong><br />Class: com.test.FirstTest <br />Lines 35 to 36.</h4></td>
<td class="code"><pre><code id="code_20392474" >/* Nothing Executed here */
/* time consumed by startCount() & stopCount() */
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#235295'>HashMap1</strong><br />Class: com.test.FirstTest <br />Line 41.</h4></td>
<td class="code"><pre><code id="code_11352996" >HashMap<String, String> h1 = new HashMap<String, String>();
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#568956'>HashMap-put1</strong><br />Class: com.test.FirstTest <br />Line 46.</h4></td>
<td class="code"><pre><code id="code_19313225" >h1.put("hello", "world");
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#AA4643'>void2</strong><br />Class: com.test.FirstTest <br />Lines 50 to 51.</h4></td>
<td class="code"><pre><code id="code_25358555" >/* Nothing Executed here */
/* time consumed by startCount() & stopCount() */
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#c6a80D'>HashMap2</strong><br />Class: com.test.FirstTest <br />Line 56.</h4></td>
<td class="code"><pre><code id="code_26399554" >HashMap<String, String> h2 = new HashMap<String, String>();
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#A47D7C'>HashMap-put2</strong><br />Class: com.test.FirstTest <br />Line 61.</h4></td>
<td class="code"><pre><code id="code_7051261" >h2.put("hello", "world");
</code></pre></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<!-- /JMicrobench Report -->
{% endraw %}
