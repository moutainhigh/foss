<%
response.setContentType("application/x-java-jnlp-file");
String number = request.getParameter("number");
%>
<?xml version="1.0" encoding="utf-8"?>
<jnlp
    spec="1.0+"
    codebase="http://localhost:8088/jasper/webstart/" >
  <information>
    <title>Foss Label Print</title>
    <vendor>Deppon Foss<vendor/>
    <homepage>http://www.deppon.com<homepage/>
    <description kind="one-line">Deppon Label Print Project</description>
  </information>
  <security>
     <all-permissions/>
  </security>
  <resources>
    <j2se version="1.4+"
     initial-heap-size="32m"
     max-heap-size="128m"
     />
         
    <jar href="lib/foss-labelprint-1.1.0.jar" main="true"/>
	
  </resources>
  <application-desc main-class="com.deppon.foss.print.labelprint.WSLabelPrinter">
    <argument><%=number%></argument>
  </application-desc>
</jnlp>

