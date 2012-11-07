
<!-- JMicrobench Report -->
<div class="JbenchReport">
<div class="chartArea">
<script type="text/javascript">var options_chartID8761216 = {"chart":{"renderTo":"container_chartID8761216_PROFILING","type":"bar","marginBottom":37,"height":325,"backgroundColor":"none"},"title":{"text":"FirstTest Performance","align":"center","margin":15,"y":10,"x":0,"verticalAlign":"top"},"subtitle":{"text":"PROFILING phase. 100 times executed.","align":"center","y":30,"x":0},"xAxis":{"categories":["void","HashMap","HashMap-put"],"title":{}},"yAxis":{"min":0.0195,"type":"linear","title":{"text":"microsecons (µs).","align":"middle","x":0,"y":0},"labels":{"overflow":"justify"}},"plotOptions":{"series":{"dataLabels":{"enabled":true,"rotation":0,"y":0,"x":0,"color":"#aac"},"groupPadding":0.1}},"legend":{"layout":"vertical","align":"right","verticalAlign":"top","x":0,"y":0,"floating":true,"borderWidth":1,"backgroundColor":"#FFFFFF","shadow":true,"enabled":false},"credits":{"enabled":true,"href":"https://github.com/M-jerez/JMicrobenchs","text":" JMicrobenchs","position":{"align":"left","x":10,"y":-5}},"series":[{"name":"Average Time","data":[{"color":"#6f9cd1","sliced":false,"y":0.039},{"color":"#B5CA92","sliced":false,"y":0.054},{"color":"#DB843D","sliced":false,"y":0.103}],"pointStart":0}],"colors":["#6f9cd1","#B5CA92","#DB843D","#80699B","#235295","#568956","#AA4643","#c6a80D","#A47D7C","#44cc79"],"millis":false}; 
 $(document).ready(function() {var chart_chartID8761216 = new Highcharts.Chart(options_chartID8761216);});</script>
<div class="" id="container_chartID8761216_PROFILING"></div>
</div>
<div class="codeArea ">
<h3 >Executed Code.</h3>
<div class="codeContainer" style="height: 285px">
<table>
<tbody>
<tr>
<td class="codeInfo"><h4><strong style='color:#6f9cd1'>void</strong><br />Class: com.test.FirstTest <br />Lines 20 to 21.</h4></td>
<td class="code"><pre><code id="code_22413802" >/* Nothing Executed here */
/* time consumed by startCount() & stopCount() */
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#B5CA92'>HashMap</strong><br />Class: com.test.FirstTest <br />Line 26.</h4></td>
<td class="code"><pre><code id="code_7494106" >HashMap<String, String> h = new HashMap<String, String>();
</code></pre></td>
</tr>
<tr>
<td class="codeInfo"><h4><strong style='color:#DB843D'>HashMap-put</strong><br />Class: com.test.FirstTest <br />Line 31.</h4></td>
<td class="code"><pre><code id="code_23671010" >h.put("hello", "world");
</code></pre></td>
</tr>
</tbody>
</table>
</div>
</div>
</div>
<!-- /JMicrobench Report -->

