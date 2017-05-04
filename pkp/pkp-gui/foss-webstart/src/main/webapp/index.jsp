<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>客户端下载</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<script type="text/javascript" src="js/deployJava.js"></script>
<script type="text/javascript">
	
	var canInstall = false;
	var installUrl = "jnlp.jsp";
	
	function check(){
		var jvmVersion = "";
		try{
			jvmVersion += deployJava.getJREs();
		}catch(err){
			alert(err.description);
		}
				
		document.getElementById("jvmVersion").innerHTML = jvmVersion;
		
		if (jvmVersion.indexOf("1.6") >= 0) {
			canInstall = true;
		}else{
			document.getElementById("notice").innerHTML = "您当前的Java虚拟机版本不符合要求,请点击链接下载 &gt;&gt; <a href='http://java.sun.com' target='_BLANK'>Java虚拟机下载</a>";
		}
	}
	
	function installWebstart(){
		if(canInstall)
			window.open(installUrl);
		else
			alert("您当前的Java虚拟机版本不符合要求,请点先下载安装");
	}
	
	function installExecution(){
		window.open("exec/depponApp.zip");
	}
	
</script>
</head>
<body onload="check()">
<br><br><br><br>
<div id="wrap">

	<div id="main">
	
		<h1 class="top bottom">FOSS客户端下载</h1>
		<div style="height:320px;">&nbsp;</div>
		<div>
			<form name="cmaForm" id="cmaForm" method="post">
						<div style="height:20px;float:right;position:relative;margin-right:70px;">
						<label for="recordPurchaseMetRealtor" class="input required">本机Java虚拟机的版本</label>
						&nbsp;&nbsp;
						<span id="jvmVersion"></span>
						</div>
						<br><br>
						<div class="buttonWrapper">
							<table align="center">
								<tr>
									<td width="300" align="center" title="下载后双击launcher.jnlp"><input id="install" type="button" class="open1 nextbutton" value="WebStart版" onClick="installWebstart()" /></td>
									<!-- <td width="300" align="center" title="下载后解压双击launcher.exe"><input id="install" type="button" class="open1 nextbutton" value="Windows版" onClick="installExecution()" /></td>
									 -->
								</tr>
							</table>
							
						</div>					
			</form>
		</div>
		
	</div>

</div>

</body>
</html>