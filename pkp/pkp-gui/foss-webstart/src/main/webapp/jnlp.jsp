<% response.setContentType("application/x-java-jnlp-file"); %>
<% response.setHeader("content-disposition", "attachment;filename=launcher.jnlp"); %>
<%
	String address = request.getLocalAddr();
	String httpPort = String.valueOf(request.getLocalPort());
	String contextpath = request.getContextPath();
%>

<?xml version="1.0" encoding="utf-8"?>
<jnlp
    spec="1.0+"
    codebase="http://<%= address %>:<%= httpPort %><%=contextpath %>/webstart/"   >
 
	<information>
		<title>Foss WebStart</title>
	    <vendor>Deppon Foss</vendor>
	    <homepage>http://www.deppon.com</homepage>
	    <description kind="one-line">Deppon Foss Gui</description>
		<icon href="images/dplogo.png"/>
		<shortcut online="true">
		   	<desktop/>
	 	</shortcut> 
	</information>
 	
	<security>
		<all-permissions/>
	</security>
	
	<resources>
		<j2se version="1.3 1.2+"/>
		<property name="foss" value="deppon1foss2gui3start4"/>
		<jar href="lib/foss-webstart.jar" version="0.0.1-SNAPSHOT" main="true"/>
		<jar href="lib/ftpserver-core.jar" version="1.0.6"/>
		<jar href="lib/slf4j-log4j12.jar" version="1.5.2"/>
		<jar href="lib/commons-logging.jar" version="1.1.1"/>
		<jar href="lib/commons-codec.jar" version="1.5"/>
		<jar href="lib/commons-httpclient.jar" version="3.1"/>
		<jar href="lib/commons-net-ftp.jar" version="2.0"/>
		<jar href="lib/commons-lang.jar" version="2.1"/>
		<jar href="lib/servlet-api.jar" version="2.5"/>
		<jar href="lib/log4j.jar" version="1.2.16"/>
		<jar href="lib/jgoodies-looks.jar" version="2.5.2"/>
		<jar href="lib/forms.jar" version="1.2.1"/>
		<jar href="lib/jgoodies-common.jar" version="1.4.0"/>
	</resources>
 
	<application-desc main-class="com.deppon.foss.client.ClientApp" >
	 	<argument><%= address %></argument>
        <argument>ftp</argument>
	</application-desc>
 </jnlp>