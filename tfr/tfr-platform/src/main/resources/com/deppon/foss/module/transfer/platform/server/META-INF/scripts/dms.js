
Ext.onReady(function() {
	Ext.QuickTips.init();
	//窗口宽度调整
	var adjustWidth=0;
	//当前窗口宽度
	var winWidth = 0;
	//窗口高度调整
	var adjustHeiht=98;
	//当前窗口高度
    var winHeight = 0;
    //获取当前屏幕的长度、宽度
    winHeight=window.screen.height-adjustHeiht;
    winWidth=window.screen.width-adjustWidth;
	var dmsAddress='';
     //页面加载时去哪dms系统的配置地址
	Ext.Ajax.request({
		url : platform.realPath('findDmsAddress.action'),
		success : function(response) {
			var json = Ext.decode(response.responseText);
			dmsAddress=json.dmsAddress;
			window.open(dmsAddress,'newwindow', 'height='+winHeight+', width='+winWidth+', top=0, left=0, toolbar=yes, menubar=yes,resizable=yes,location=no, status=no');
			var tab = Ext.getCmp('mainAreaPanel').getActiveTab();
			tab.close();
		},
		exception : function(response) {
		   	 var result = Ext.decode(response.responseText);
			  Ext.MessageBox.alert('提示',result.message);
			
		}
	});
	
});
