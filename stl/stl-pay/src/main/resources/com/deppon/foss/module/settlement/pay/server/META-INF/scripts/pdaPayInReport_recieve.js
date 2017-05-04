// init prt window 
var KEY_def_width = window.screen.availWidth;
var KEY_def_height = window.screen.availHeight;
// setup css
document.write("<style>");
document.write(".prtpreviewbtnup {margin:0px;padding:0px;width:100%;height:100%;background:#dfdfdf;cursor:hand;border:2 solid black;border-top:2 solid silver;border-left: 2 solid silver;}");
document.write(".prtpreviewbtndown {margin:0px;padding:0px;width:100%;height:100%;background:#dfdfdf;cursor:hand;border:2 solid silver;border-top:2 solid black;border-left: 2 solid black;}");
document.write(".prtpreviewtoolbar { }");
document.write(".prtpreviewtoolbar td { font-size:9pt;font-family:微软雅黑;text-align:center; }");
document.write(".prtpreviewtoolbar button { font-size:9pt;font-family:微软雅黑;text-align:center; }");
document.write("</style>");

var viewform_html = []; 
viewform_html.push('<table border=0 width=100% cellpadding=0 cellspacing=0>');
viewform_html.push('<tr><td><table class="prtpreviewtoolbar" border=0 cellpadding=0 cellspacing=0>');
viewform_html.push('<tr align=left>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(1)" value="适高"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(2)" value="正常"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(3)" value="适宽"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(4)" value="放大"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(5)" value="缩小"></td>');
viewform_html.push('<td><select id="percent" onchange="prtViewAction(6)">');
viewform_html.push('<option value=0>30%</option><option value=1>50%</option><option value=2>60%</option>');
viewform_html.push('<option value=3>70%</option><option value=4>80%</option><option value=5>85%</option>');
viewform_html.push('<option value=6>90%</option><option value=7>95%</option><option value=8>100%</option>');
viewform_html.push('<option value=9>125%</option><option value=10>150%</option><option value=11>200%</option>');
viewform_html.push('<option value=12>按整宽</option><option value=13>按整高</option><option value=14 selected>按整页</option>');
viewform_html.push('<option value=15>整宽不变形</option><option value=16>整高不变形</option><option value=17>其它比例</option>');
viewform_html.push('</select></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(7)" value="首页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(8)" value="上页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(9)" value="下页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(10)" value="尾页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(12)" value="设置"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(13)" value="打印全部"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(14)" value="打印本页"></td>');
viewform_html.push('<td><input type=button style="padding:3px;" onclick="prtViewAction(15)" value="关闭"></td>');
viewform_html.push('<td><span id="chkdisplayimg" style="display:none;"><input id="ckhView" type=checkbox onclick="prtViewAction(16,this)" checked>显示背景</span></td>');
viewform_html.push('</tr></table></td></tr><tr><td>');
viewform_html.push('<div style="background:silver;padding:5px;">');
viewform_html.push('<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=100% height=100%>');
viewform_html.push('<embed id="LODOP_EM" TYPE="application/x-print-lodop" style="border:1px solid #000000;" width=100% height='+(KEY_def_height-200)+' color="#ffffff" '); 
viewform_html.push('PLUGINSPAGE="http://file.deppon.com.cn/FOSS_WEB_PRINT.exe"></embed></object></div>');
viewform_html.push('</td></tr></table><div id="printresultdiv" style="width:1px;height:1px;"></div>');

var hiddenhtml = [];
hiddenhtml.push('<div style="width:1px;height:1px;background:silver;padding:5px;">');
hiddenhtml.push('<object id="LODOP_OB_dprt" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=1 height=1>');
hiddenhtml.push('<embed id="LODOP_EM_dprt" TYPE="application/x-print-lodop" style="border:1px solid #000000;" width=1 height=1 color="#ffffff" '); 
hiddenhtml.push('PLUGINSPAGE="http://file.deppon.com.cn/FOSS_WEB_PRINT.exe"></embed></object></div>');
hiddenhtml.push('<div id="printresultdiv_dprt" style="width:1px;height:1px;"></div>');
//--

function btncssdown( pbtnobj){
	pbtnobj.className="prtpreviewbtndown";
}

function btncssup( pbtnobj){
	pbtnobj.className="prtpreviewbtnup";
}

function prtViewAction( pActionType, pobj){
	if (mLODOP){
		switch(pActionType){
			case 1:mLODOP.DO_ACTION("PREVIEW_ZOOM_HIGHT",0);break;
			case 2:mLODOP.DO_ACTION("PREVIEW_ZOOM_NORMAL",0);break;
			case 3:mLODOP.DO_ACTION("PREVIEW_ZOOM_WIDTH",0);break;
			case 4:mLODOP.DO_ACTION("PREVIEW_ZOOM_IN",0);break;
			case 5:mLODOP.DO_ACTION("PREVIEW_ZOOM_OUT",0);break;
			case 6:mLODOP.DO_ACTION("PREVIEW_PERCENT",document.getElementById('percent').value);break;
			case 7:mLODOP.DO_ACTION("PREVIEW_GOFIRST",0);break;
			case 8:mLODOP.DO_ACTION("PREVIEW_GOPRIOR",0);break;
			case 9:mLODOP.DO_ACTION("PREVIEW_GONEXT",0);break;
			case 10:mLODOP.DO_ACTION("PREVIEW_GOLAST",0);break;
			case 11:mLODOP.DO_ACTION("PREVIEW_GOTO",document.getElementById('inputpage').value);break;
			case 12:mLODOP.DO_ACTION("PREVIEW_SETUP",0);break;
			case 13:
				if (chkDisplayimg() == false) {
					break;
				} else {
					doPrintAll();break;
				}
			case 14:
				if (chkDisplayimg() == false) {
					break;
				} else {
					doPrintCurrentPage();break;
				}
			case 15:doClose();break;
			case 16:displaybkimg(pobj);break;
			default:break;
		}
	}
}

function doPrintAll(){	
	if (mLODOP) {
		var iPageCount=mLODOP.GET_VALUE("PREVIEW_PAGE_COUNT",0);
		mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",1);
		mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iPageCount);	
		mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	}
}

function doPrintCurrentPage(){	
	if (mLODOP) {
		var iThisNumber=mLODOP.GET_VALUE("PREVIEW_PAGE_NUMBER",0);//获得当前页号
		mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",iThisNumber);
		mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iThisNumber);	
		mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	}
}

function doClose(){	
	//alert("close this window");
	priviewWin.close();
}

function getLodop(oOBJECT,oEMBED){
    var strHtml1="<br><font color='#FF00FF'>打印控件未安装!点击这里<a href='install_lodop.exe'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
    var strHtml2="<br><font color='#FF00FF'>打印控件需要升级!点击这里<a href='install_lodop.exe'>执行升级</a>,升级后请重新进入。</font>";
    var strHtml3="<br><br><font color='#FF00FF'>注意：<br>1：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它;<br>2：如果浏览器表现出停滞不动等异常，建议关闭其“plugin-container”(网上搜关闭方法)功能;</font>";
    var LODOP=oEMBED;	
     
	try{
	     if (navigator.appVersion.indexOf("MSIE")>=0) LODOP=oOBJECT;
	
	     if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
			 if (navigator.userAgent.indexOf('Firefox')>=0)
			         document.documentElement.innerHTML=strHtml3+document.documentElement.innerHTML;
			 if (navigator.appVersion.indexOf("MSIE")>=0) 
				 document.write(strHtml1); 
			 else
				 document.documentElement.innerHTML=strHtml1+document.documentElement.innerHTML;
			 	return LODOP; 
	     } 
	     else if (LODOP.VERSION<"6.0.5.8") {
	    	 if (navigator.appVersion.indexOf("MSIE")>=0){
	    		 document.write(strHtml2);
	    	 }	    		  	    	 
	    	 else {
	    		 document.documentElement.innerHTML=strHtml2+document.documentElement.innerHTML;
	    	 }
	    	 return LODOP;
	     }
         LODOP.SET_LICENSES("德邦物流股份有限公司","49E144B7AB0D468100D8D05CDDAB3A47","","");
	     return LODOP; 
	}catch(err){
	     document.documentElement.innerHTML="Error:"+strHtml1+document.documentElement.innerHTML;
	     return LODOP; 
	}
	
}

var mLODOP= null;
function fillLodopByFullJasperPrint(){
	var maindiv = document.getElementById("printresultdiv");
	
	var fulljpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("fulljpdiv"==odiv.id){
			fulljpdivobj = odiv;
			break;
		}
	}
	
	if(fulljpdivobj!=null){
		var divs = fulljpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示 
		mLODOP.PREVIEW();
	}
}

function fillLodopByNoImgJasperPrint(){
	
	var maindiv = document.getElementById("printresultdiv");
	
	var noimgjpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("noimgjpdiv"==odiv.id){
			noimgjpdivobj = odiv;
			break;
		}
	}
	
	if(noimgjpdivobj!=null){
		var divs = noimgjpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示
		mLODOP.PREVIEW();
	}
}

function newpage( pLODOP, pdivobj){
	var vwidth = pdivobj.offsetWidth;
	var vheight = pdivobj.offsetHeight
	pLODOP.NewPage();
	
	var imgs = pdivobj.getElementsByTagName("IMG");
	if(imgs!=null){
		for(var a=0;a<imgs.length;a++){
			var vsrc = imgs[a].src;
			if(vsrc.indexOf("data:image/")!=-1){
				imgs[a].style.display="none";
			}
		}
	}

	pLODOP.ADD_PRINT_HTM(0,0,"100%","100%",pdivobj.innerHTML);
	var imgs = pdivobj.getElementsByTagName("IMG");
	if(imgs!=null){
		for(var a=0;a<imgs.length;a++){
			var vsrc = imgs[a].src;
			if(vsrc.indexOf("data:image/")!=-1){
				//alert(parseInt(imgs[a].parentElement.style.top,10));
				//alert(parseInt(imgs[a].parentElement.style.left,10));
				//alert(imgs[a].style.top);
				//alert(imgs[a].style.left);
				var vtop = parseInt(imgs[a].parentElement.style.top,10) + parseInt(imgs[a].style.top,10);
				var vleft = parseInt(imgs[a].parentElement.style.left,10) + parseInt(imgs[a].style.left,10);
				
				var imgwidth = parseInt(imgs[a].parentElement.style.width);
				var imgheight = parseInt(imgs[a].parentElement.style.height);
				
				mLODOP.ADD_PRINT_IMAGE(vtop+"pt",vleft+"pt",imgwidth+"pt",imgheight+"pt",vsrc);
			}
		}
	}	
}
function chkDisplayimg() {
	var ckhView = document.getElementById("ckhView");
	var chkdisplay = document.getElementById("chkdisplayimg").style.display;
	if (chkdisplay == "block" && ckhView.checked) {
		alert("请点击显示背景按钮，以取消打印背景图。");
		return false;
	}
}
function opentosetup(){
	//mLODOP.PRINT_SETUP();	
}

function displaybkimg( pchkobj){
	if(pchkobj.checked){		
		fillLodopByFullJasperPrint();
	}
	else {
		fillLodopByNoImgJasperPrint();
	}
}

function do_printpreview(pPrtKey, pParam, pCtxPath, pWidth, pHeight){
	var vwidth = KEY_def_width-50;
	var vheight = KEY_def_height-100;
	if(pWidth==null){
		pWidth = vwidth;
	}
	if(pHeight==null){
		pHeight = vheight;
	}	
	
	if(pParam==null){
		pParam = {};
	}
	pParam = Ext.apply(pParam,{ prtkey: pPrtKey});
	var vCtxPath = pCtxPath;
	if(vCtxPath==null || vCtxPath==""){
		vCtxPath = "..";
	}
	do_printpreview_native(pParam,vCtxPath, pWidth,pHeight);
}

var priviewWin = null;
function do_printpreview_native( pParam,pCtxPath, pWidth, pHeight){

	priviewWin = new Ext.Window({
		title:'打印预览',
	    layout: 'fit',
		height: KEY_def_height,
		width: KEY_def_width,
	    border: false,
	    html: viewform_html.join('')
	});
	
	priviewWin.setWidth(pWidth);
	priviewWin.setHeight(pHeight);
	priviewWin.show();
	
	//var myMask = new Ext.LoadMask(priviewWin, {msg:"Please wait..."});
	//myMask.show();
	
	Ext.Ajax.request({
	    url: pCtxPath+'/servlets/printexporhtml',
	    params:pParam,
	    success: function(response){
	    	var reesultdivobj = document.getElementById('printresultdiv');
	    	reesultdivobj.innerHTML = response.responseText;
	    	try{
		    	var hasimg = document.getElementById("hasimg").value;
		    	if("true"==hasimg){
		    		document.getElementById("chkdisplayimg").style.display="block";
		    	}	    		
	    	}catch(exp1){
	    		document.getElementById("chkdisplayimg").style.display="none";
	    	}

	    	try{
	    		fillLodopByFullJasperPrint();
	    	}catch(exp) {
	    		alert(exp.message);
	    	}
	    	//myMask.hide();
	    }
	});
}

function fillLodopByFullJasperPrint_dprt(){
	var maindiv = document.getElementById("printresultdiv_dprt");
	
	var fulljpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("fulljpdiv"==odiv.id){
			fulljpdivobj = odiv;
			break;
		}
	}
	
	if(fulljpdivobj!=null){
		var divs = fulljpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示 
		mLODOP.PREVIEW();
	}
}

function fillLodopByNoImgJasperPrint_dprt(){
	
	var maindiv = document.getElementById("printresultdiv_dprt");
	
	var noimgjpdivobj = null;
	var subdivs = maindiv.getElementsByTagName("DIV");
	for(var i=0;i<subdivs.length;i++){
		var odiv = subdivs[i];
		if("noimgjpdiv"==odiv.id){
			noimgjpdivobj = odiv;
			break;
		}
	}
	
	if(noimgjpdivobj!=null){
		var divs = noimgjpdivobj.getElementsByTagName("DIV");
		mLODOP=getLodop(document.getElementById('LODOP_OB_dprt'),document.getElementById('LODOP_EM_dprt'));  
		var pagewidth = document.getElementById("pagewidth").value;
		var pageheight = document.getElementById("pageheight").value;
		var ororientation = document.getElementById("orientation").value;
		mLODOP.PRINT_INIT(0,0,pagewidth,pageheight,"jasper_print");
		try{
			if(document.getElementById("fixpagesize").value=="true"){
				mLODOP.SET_PRINT_PAGESIZE(1,pagewidth,pageheight,"A4");
			}
			else {
				mLODOP.DO_ACTION("PRINTSETUP_ORIENT",ororientation);
			}
		}catch(exp) {}
		for(var i=0;i<divs.length;i++){
			var clsname = divs[i].className;
			if("jrPage"==clsname){
				newpage(mLODOP,divs[i]);
			}
		}
		//mLODOP.DO_ACTION("PREVIEW_PERCENT",8);//按100%缩放
		mLODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
		mLODOP.SET_PREVIEW_WINDOW(1,3,0,0,0,""); //隐藏工具条，正常大小
		mLODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true); //预览界面内嵌到页面内
		mLODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED",true); //横向式正向显示
		mLODOP.PREVIEW();
	}
}

var dprtform = null;
function do_print_full(pPrtKey, pParam,pCtxPath ){
	var vCtxPath = pCtxPath;
	if(vCtxPath==null || vCtxPath==""){
		vCtxPath = "..";
	}
	
	var vwidth = 200;
	var vheight = 100;
	
	if(pParam==null){
		pParam = {};
	}
	pParam = Ext.apply(pParam,{ prtkey: pPrtKey});

	if(dprtform==null){
	    var dprtform = Ext.create('Ext.Panel', {
	        floating: true,
	        bottomed: true,
	        width: 1,
	        height: 1,
	        html: hiddenhtml.join()
	    });
	}
	dprtform.show();
    
	Ext.Ajax.request({
	    url: vCtxPath+'/servlets/printexporhtml',
	    params:pParam,
	    success: function(response){
	    	var vobj = document.getElementById('printresultdiv_dprt');
	    	vobj.innerHTML = response.responseText;
	    	  	    	
	    	try{
	    		fillLodopByFullJasperPrint_dprt();
	    		if (mLODOP) {
	    			var iPageCount=mLODOP.GET_VALUE("PREVIEW_PAGE_COUNT",0);
	    			mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",1);
	    			mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iPageCount);	
	    			mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	    			//dprtform.hide();
	    		}
	    	}catch(exp) {
	    		alert(exp.message);
	    	}
	    }
	});
}

function do_print_noimg(pPrtKey, pParam,pCtxPath ){
	
	var vCtxPath = pCtxPath;
	if(vCtxPath==null || vCtxPath==""){
		vCtxPath = "..";
	}
	
	var vwidth = 200;
	var vheight = 100;
	
	if(pParam==null){
		pParam = {};
	}
	pParam = Ext.apply(pParam,{ prtkey: pPrtKey});

	if(dprtform==null){
	    var dprtform = Ext.create('Ext.Panel', {
	        floating: true,
	        bottomed: true,
	        width: 1,
	        height: 1,
	        html: hiddenhtml.join()
	    });
	}
	dprtform.show();
    
	Ext.Ajax.request({
	    url: vCtxPath+'/servlets/printexporhtml',
	    params:pParam,
	    success: function(response){
	    	var vobj = document.getElementById('printresultdiv_dprt');
	    	vobj.innerHTML = response.responseText;
	    	  	    	
	    	try{
	    		fillLodopByNoImgJasperPrint_dprt();
	    		if (mLODOP) {
	    			var iPageCount=mLODOP.GET_VALUE("PREVIEW_PAGE_COUNT",0);
	    			mLODOP.SET_PRINT_MODE("PRINT_START_PAGE",1);
	    			mLODOP.SET_PRINT_MODE("PRINT_END_PAGE",iPageCount);	
	    			mLODOP.DO_ACTION("PREVIEW_PRINT",0);
	    			//dprtform.hide();
	    		}
	    	}catch(exp) {
	    		alert(exp.message);
	    	}
	    }
	});
}
;/**
 * 刷新界面
 */
pay.pdaPayInReport_receive.refreshPage = function(){
	//获取接货列表
	var receiveGrid = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getDetailReceiveGrid();
	
	//获取总计panel
	var totalForm = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getTotalFormForAdd();
	
	//反写接货总金额
	receiveGrid.down('toolbar').items.items[1].setValue(0);
	//反写接货总票数
	receiveGrid.down('toolbar').items.items[2].setValue(0);
	//反写接货签收单数
	receiveGrid.down('toolbar').items.items[3].setValue(0);
	
	
	receiveGrid.store.removeAll();
	
	totalForm.getForm().reset();
}


/**
 * 查询
 */
pay.pdaPayInReport_receive.query = function(){
	//获取form表单
	var form = this.up('form').getForm();
	//获取接货列表
	var receiveGrid = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getDetailReceiveGrid();
	
	//获取总计panel
	var totalForm = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getTotalFormForAdd();
	//刷新界面
	pay.pdaPayInReport_receive.refreshPage();
	
	//判断界面输入条件是否都合法
	if(form.isValid()){
		//获取当前页面
		var loadMask = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getLoadMask();
		//获取遮罩,进行遮罩
		loadMask.show();
		//发送请求
		Ext.Ajax.request({
			url:pay.realPath('queryReceiptGoodsInfo.action'),
			actionMethods:'post',
			params:{
				'vo.dto.driverCode':form.findField('driverCode').getValue(),
				'vo.dto.driverName':form.findField('driverCode').getRawValue()
				//'vo.dto.vehicleNo':form.findField('vehicleNo').getValue(),
				//'vo.dto.reportBeginDate':form.findField('reportDate').getValue(),
			},
			success:function(response){
				//遮罩窗口
				loadMask.hide();
				//获取返回结果
				var result = Ext.decode(response.responseText);	
				//如果dto为空，则提示
				if(Ext.isEmpty(result.vo.dto)){
					Ext.Msg.alert(pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.noResult'));
					//反写接货总金额
					receiveGrid.down('toolbar').items.items[1].setValue(0);
					//反写接货总票数
					receiveGrid.down('toolbar').items.items[2].setValue(0);
					//反写接货签收单数
					receiveGrid.down('toolbar').items.items[3].setValue(0);
									
					return false;
				}
				var receivedList = result.vo.dto.receivedList;
				//var deliverList = result.vo.dto.deliverList;
				var entity = result.vo.dto.entity;
				
				//如果接货为空，则提示
				if(Ext.isEmpty(receivedList)){
					Ext.Msg.alert(pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.noResult'));
					//反写接货总金额
					receiveGrid.down('toolbar').items.items[1].setValue(0);
					//反写接货总票数
					receiveGrid.down('toolbar').items.items[2].setValue(0);
					//反写接货签收单数
					receiveGrid.down('toolbar').items.items[3].setValue(0);
					
					return false;
				}
				
				 if (!Ext.isEmpty(receivedList)) {
					 receiveGrid.store.loadData(receivedList);
					 receiveGrid.down('toolbar').items.items[1].setValue(result.vo.dto.receivedTotalAmount);
					 receiveGrid.down('toolbar').items.items[2].setValue(result.vo.dto.receivedTotalCount);
					 receiveGrid.down('toolbar').items.items[3].setValue(result.vo.dto.signwaybillTotal);
	                }
		
				//声明总计实体
				var formModel = new Foss.pdaPayInReport_recieve.TotalFormModel(entity);
				//加载数据
				totalForm.loadRecord(formModel);
				//显示隐藏列表		
				var detailPanel = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getDetailPanel();
				detailPanel.show();
				totalForm.show();
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				//隐藏掉遮罩
				loadMask.hide();
				//弹出异常提示
				Ext.Msg.alert(pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.alert'),result.message);
			},
			unknownException:function(form,action){
				//隐藏掉遮罩
				loadMask.hide();
			},
			failure:function(form,action){
				//隐藏掉遮罩
				loadMask.hide();
			}
		});
	}else{
		Ext.Msg.alert(pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.noDriverWarning'));
		return false;
	}
}

/**
 * 新增pda报表
 */
pay.pdaPayInReport_receive.addReceiveReportBill = function(){
	//声明要用变量
	var vo,dto,receiveGrid,deliverGrid,
		totalForm,receivedList,deliverList,entity;
	//获取接货列表
	receiveGrid = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getDetailReceiveGrid();
	
	//获取总计panel
	totalForm = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getTotalFormForAdd();
	//循环遍历接货列表，获取grid数据
	receivedList = new Array();
	//循环获取数据
	receiveGrid.store.each(function(record){
		receivedList.push(record.data);
	});
	
	//初始化form表单实体
	entity = totalForm.getRecord();
	totalForm.getForm().updateRecord(entity);
	//初始化vo	
	vo = new Object();
	//初始化dto	
	dto = new Object();
	//建立vo和dto关系
	vo.dto = dto;
	//给dto属性赋值
	dto.receivedList = receivedList;
	
	dto.entity = entity.data;
	
	//获取当前页面
	var loadMask = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getLoadMask();
	//获取遮罩,进行遮罩
	loadMask.show();
	
	//调用后台方法继续保存操作
	Ext.Ajax.request({
		url:pay.realPath('addReceiveReportBill.action'),
		actionMethods:'POST',
		jsonData:{
			'vo':vo
		},
		success:function(response){
			//获取返回结果
			var result = Ext.decode(response.responseText);		
			
			//遮罩窗口
			loadMask.hide();
			//刷新界面
			pay.pdaPayInReport_receive.refreshPage();
			
			//获得打印编号
			totalForm.getForm().findField('reportNo').setValue(result.vo.dto.entity.reportNo);
			
			Ext.Msg.alert(pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.alert'), pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.saveSuccess')+result.vo.dto.entity.reportNo);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//隐藏掉遮罩
			loadMask.hide();
			//弹出异常提示
			Ext.Msg.alert(pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.alert'),result.message);
		},
		unknownException:function(form,action){
			//隐藏掉遮罩
			loadMask.hide();
		},
		failure:function(form,action){
			//隐藏掉遮罩
			loadMask.hide();
		}
	});
}
/**
 * 打印
 */
pay.pdaPayInReport_receive.print = function(){
	//获取总计panel
	totalForm = Ext.getCmp('T_pay-pdaPayInReport_recieve_content').getTotalFormForAdd();
	//获取报表编号 
	var reportNo = totalForm.getForm().findField('reportNo').getValue();
	//如果报表编号为空，表示没有生产
	if(Ext.isEmpty(reportNo)){
		Ext.Msg.alert(pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.alert'),pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.unSaveToPrintWarning'));
		return false;
	}
	//打印
	do_printpreview('pdapayinreport',{reportNos:reportNo},ContextPath.STL_WEB);
	
}

/**
 * 司机收款报表表格model
 */
Ext.define('Foss.pdaPayInReport_recieve.DetailModel',{
	extend:'Ext.data.Model',
	fields:[{
		//类型
		name:'type'
	},{
		//运单号
		name:'waybillNo'
	},{
		//重量
		name:'weight',
		type:'double'
	},{
		//体积
		name:'volume',
		type:'double'
	},{
		//件数
		name:'qty',
		type:'int'
	},{
		//金额
		name:'amount',
		type:'double'
	},{
		//是否有签收单
		name:'isSignwaybill'
	},{
		//是否有签收单
		name:'vehicleNo'
	},{
		//是否有返单
		name:'isReturnTicket'
	},{
		//创建时间
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		name:'id'
	}]
});

/**
 * 总计表单
 */
Ext.define('Foss.pdaPayInReport_recieve.TotalFormModel',{
	extend:'Ext.data.Model',
	fields:[{
		//司机编号
		name:'driverCode'
	},{
		//司机名称
		name:'driverName'
	}
//	,{
//		//车牌号
//		name:'vehicleNo'
//	}
	,{
		//总票数
		name:'waybillQtyTotal',
		type:'int'
	},{
		//总票数
		name:'piecesTotal',
		type:'int'
	},{
		//总体积
		name:'volumeTotal',
		type:'double'
	},{
		//总重量
		name:'weightTotal',
		type:'double'
	},{
		//返单总数
		name:'returnTicketTotal',
		type:'int'
	},{
		//签收单总数
		name:'signwaybillTotal',
		type:'int'
	},{
		//现金总额
		name:'receiveAmountTotal',
		type:'double'
	},{
		//办理人Code
		name:'createUserCode'
	},{
		//报表编号
		name:'reportNo'
	},{
		//办理人名称
		name:'createUserName'
	},{
		//办理时间
		name:'createTime',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//实收总额
		name:'receivedAmountTotal',
		type:'double'
	},{
		//报表起始日期
		name:'reportBeginDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//报表结束日期
		name:'reportEndDate',
		type:'date',
		convert:stl.longToDateConvert 
	},{
		//异常备注
		name:'notes'
	}]
});

/**
 * 接货列表store
 */
Ext.define('Foss.pdaPayInReport_recieve.DetailReceiveGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.pdaPayInReport_recieve.DetailModel'
});


/**
 * 新增页面查询form
 */
Ext.define('Foss.pdaPayInReport_recieve.QueryFormForAdd',{
	extend:'Ext.form.Panel',
	frame:true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.pdaPayReport'),
	layout:'column',
	defaults:{
		labelWidth:75,
		labelAlign:'right'
	},
	items:[/*{
		xtype:'datefield',
		fieldLabel:'报表日期',
		name:'reportDate',
		columnWidth:.25,
		allowBlank:false,
		format:'Y-m-d',
		value:new Date()
	},*/{
		xtype:'commonowndriverselector',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.driverName'),
		name:'driverCode',
		columnWidth:.25,
		allowBlank:false,
		listeners:{
			'change':function(th,newValue,oldValue){
				//获取司机工号控件
				var driverName = this.up('form').getForm().findField('driverName');
				//如果当前所选值不为空，则将司机工号设置到工号组件中
				if(!Ext.isEmpty(newValue)){
					driverName.setValue(newValue);
				}else{
					driverName.setValue(null);
				}
			}
		}
	}/*,{
		xtype:'commonleasedvehicleselector',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.vehicleNo'),
		columnWidth:.25,
		name:'vehicleNo',
		allowBlank:false
	}*/,{
		xtype:'textfield',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.driverCode'),
		readOnly:true,
		name:'driverName',
		columnWidth:.25
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.9
	},{
		xtype:'button',
		text:pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.query'),
		columnWidth:.1,
		handler:pay.pdaPayInReport_receive.query
	}]
	
});


Ext.define('Foss.pdaPayInReport_recieve.DetailReceiveGridStore',{
	extend:'Ext.data.Store',
	model:'Foss.pdaPayInReport_recieve.DetailModel'
});


/**
 * 接货表格
 */
Ext.define('Foss.pdaPayInReport_recieve.DetailReceiveGrid',{
	extend:'Ext.grid.Panel',
    height:160,
    frame:true,
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
	emptyText:pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.noResult'),
    store:Ext.create('Foss.pdaPayInReport_recieve.DetailReceiveGridStore'),
  	viewConfig:{
  		enableTextSelection : true//设置行可以选择，进而可以复制
  	},
  	defaults:{
  		align:'center'
  	},
  	columns:[{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.type'),
		dataIndex:'type',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.DRIVER_COLLECTION_RPT_D__TYPE);
    		return displayField;
		},
		flex:1
	},{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.waybillNo'),
		dataIndex:'waybillNo',
		flex:1
	},{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.vehicleNo'),
		dataIndex:'vehicleNo',
		flex:1
	},{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.weight'),
		dataIndex:'weight',
		flex:1
	},{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.qty'),
		dataIndex:'qty',
		flex:1
	},{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.volume'),
		dataIndex:'volume',
		flex:1
	},{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.amount'),
		dataIndex:'amount',
		flex:1
	},{
		header:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.isSignwaybill'),
		dataIndex:'isSignwaybill',
		renderer:function(value){
			var displayField = FossDataDictionary. rendererSubmitToDisplay (value,settlementDict.FOSS_BOOLEAN);
    		return displayField;
		},
		flex:1
	}],
    initComponent:function(){
		var me = this;
		me.dockedItems =[{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				xtype:'displayfield',
				allowBlank:true,
				columnWidth:.04,
				labelWidth:40,
				labelSeparator:'',
				fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.total')
			},{
				xtype:'textfield',
				readOnly:true,	
				name:'totalAmount',
				columnWidth:.15,
				labelWidth:90,
				fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.cashAmountTotal')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:75,
				fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalCount')
			},{
				xtype:'textfield',
				readOnly:true,
				columnWidth:.15,
				labelWidth:80,
				fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.singCount')
			}]
   		 }];
   		 me.callParent();
    }
});

/**
 * 明细区域
 */
Ext.define('Foss.pdaPayInReport_recieve.DetailPanel',{
	extend:'Ext.panel.Panel',
	//frame:true,
	items:[
		Ext.create('Foss.pdaPayInReport_recieve.DetailReceiveGrid')
		//Ext.create('Foss.pdaPayInReport.DetailDeliverGrid')
	]
});


/**
 * 新增页面查询form
 */
Ext.define('Foss.pdaPayInReport_recieve.TotalFormForAdd',{
	extend:'Ext.form.Panel',
	title:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalAll'),
	frame:true,
	layout:'column',
	defaultType:'textfield',
	defaults:{
		labelWidth:75,
		readOnly:true
	},
	items:[{
		xtype:'numberfield',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalCount'),
		name:'waybillQtyTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalPieces'),
		name:'piecesTotal',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalQty'),
		xtype:'numberfield',
		name:'volumeTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalWeight'),
		xtype:'numberfield',
		name:'weightTotal',
		columnWidth:.3
	},{
		xtype:'numberfield',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.returnTicketCount'),
		name:'returnTicketTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalSignwaybill'),
		xtype:'numberfield',
		name:'signwaybillTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.totalCashAmount'),
		xtype:'numberfield',
		name:'receiveAmountTotal',
		columnWidth:.3
	},{
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.createUserName'),
		name:'createUserName',
		columnWidth:.3
	},{
		fieldLabel:'办理人编码',
		hidden:true,
		name:'createUserCode',
		columnWidth:.3
	},{
		xtype:'datefield',
		name:'createTime',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.createTime'),
		columnWidth:.3,
		format:'Y-m-d H:i:s'
	},{
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.receivedAmountTotal'),
		name:'receivedAmountTotal',
		xtype:'numberfield',
		allowBlank:false,
		columnWidth:.3,
		readOnly:false,
		listeners:
			{
				'blur':function(){
				var me=this;
				var value=me.getValue();
				if(value>=100000000)
				{
					Ext.Msg.alert("实收金额不能大于8位数,请重新输入！");
					me.setValue(null);
				}else{
					return false;
				}
			}
		}		
	},{
		xtype:'textarea',
		name:'notes',
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.notes'),
		columnWidth:.9,
		readOnly:false
	},{
		fieldLabel:'报表编号',
		name:'reportNo',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:'司机编号',
		name:'driverCode',
		hidden:true,
		columnWidth:.3
	},{
		fieldLabel:'司机名称',
		name:'driverName',
		hidden:true,
		columnWidth:.3
	}/*,{
		fieldLabel:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.vehicleNo'),
		name:'vehicleNo',
		hidden:true,
		columnWidth:.3
	}*/,{
		fieldLabel:'报表起始日期',
		xtype:'datefield',
		labelWidth:90,
		hidden:true,
		name:'reportBeginDate',
		format:'Y-m-d H:i:s',
		columnWidth:.3
	},{
		fieldLabel:'报表结束日期',
		xtype:'datefield',
		labelWidth:90,
		hidden:true,
		name:'reportEndDate',
		format:'Y-m-d H:i:s',
		columnWidth:.3
	},{
		xtype:'container',
		html:'&nbsp;',
		columnWidth:.7
	},{
		xtype:'button',
		text:pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.commit'),
		columnWidth:.1,
		handler:pay.pdaPayInReport_receive.addReceiveReportBill,
		disabled:!pay.pdaPayInReport_receive.isPermission('/stl-web/pay/addReceiveReportBill.action'),
		hidden:!pay.pdaPayInReport_receive.isPermission('/stl-web/pay/addReceiveReportBill.action')
	},{
		xtype:'button',
		text:pay.pdaPayInReport_receive.i18n('foss.stl.pay.common.print'),
		columnWidth:.1,
		handler:pay.pdaPayInReport_receive.print
	}]
	
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	//查询表单
	var queryFormForAdd = Ext.create('Foss.pdaPayInReport_recieve.QueryFormForAdd');
	//明细区域
	var detailPanel = Ext.create('Foss.pdaPayInReport_recieve.DetailPanel',{hidden:true});
	//汇总表单
	var totalFormForAdd = Ext.create('Foss.pdaPayInReport_recieve.TotalFormForAdd',{hidden:true});
	
	//创建panel
	Ext.create('Ext.panel.Panel',{
		id: 'T_pay-pdaPayInReport_recieve_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		loadMask:null,//遮罩
		getLoadMask:function(){
			var me = this;
			//获取遮罩效果
			if(Ext.isEmpty(me.loadMask)){
				me.loadMask = new Ext.LoadMask(me, {
					msg:pay.pdaPayInReport_receive.i18n('foss.stl.pay.pdaPayInReport.saveMask'),
				    removeMask : true// 完成后移除
				});
			}
			return me.loadMask;
		},
		getDetailPanel:function(){
			return detailPanel;
		},
		getDetailReceiveGrid:function(){
			return detailPanel.items.items[0];
		},
		/*getDetailDeliverGrid:function(){
			return detailPanel.items.items[1];;
		},*/
		getTotalFormForAdd:function(){
			return totalFormForAdd;
		},
		items: [queryFormForAdd,detailPanel,totalFormForAdd],
		renderTo: 'T_pay-pdaPayInReport_recieve-body'
	});
});