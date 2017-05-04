/*
 @author:097972-foss-dengtingting
   Build date: 2012-11-22
*/

//根据选中的运单查询返回可打印的到达联编号并打印
function print(parentGrid,printValue,win){
    win.hide();
	var selectWaybill = parentGrid.getSelectionModel().getSelection();
	var waybillNos = '';
		for(var i = 0;i<selectWaybill.length;i++){
			if(waybillNos.length == 0) {
				waybillNos = selectWaybill[i].data.waybillNo;
			} else {
				waybillNos = waybillNos + "," + selectWaybill[i].data.waybillNo;
			}
		}
	if(printValue =='1'){
			Ext.Ajax.request({
//		    url: '../predeliver/batchPrintArriveSheet.action',
		    url:ContextPath.PKP_DELIVER + '/predeliver/batchPrintArriveSheet.action',
		    params: {
		    	'vo.waybillNos': waybillNos,
		    	'vo.source': win.source
		    },
		    success: function(response){
		    	var arriveSheetNos = Ext.decode(response.responseText).vo.arriveSheetNos;
		    	if (win.printType == 'preview') {
		    		do_printpreview('arriveSheet1',{arriveSheetNos:arriveSheetNos, 
		    				currentUserEmpName : FossUserContext.getCurrentUserEmp().empName, 
		    				currentUserDepCode : FossUserContext.getCurrentDeptCode(),printValue : printValue},ContextPath.PKP_DELIVER);
		    	} else {
		    		do_print_noimg('arriveSheet1',{arriveSheetNos:arriveSheetNos, 
		    				currentUserEmpName : FossUserContext.getCurrentUserEmp().empName, 
		    				currentUserDepCode : FossUserContext.getCurrentDeptCode(),printValue : printValue},ContextPath.PKP_DELIVER);
		    	}
		    },
		    exception: function(response){
		    	var json = Ext.decode(response.responseText);
		    	//win.hide();
		    	Ext.ux.Toast.msg('提示信息', json.message, 'error', 2000);
		    }
		});
	}else{
		Ext.Ajax.request({
//		    url: '../predeliver/batchPrintArriveSheet.action',
		    url:ContextPath.PKP_DELIVER + '/predeliver/batchPrintArriveSheet.action',
		    params: {
		    	'vo.waybillNos': waybillNos,
		    	'vo.source': win.source
		    },
		    success: function(response){
//		    	win.hide();
		    	var arriveSheetNos = Ext.decode(response.responseText).vo.arriveSheetNos;
		    	if (win.printType == 'preview') {
		    		if (printValue == '2') {
				    	do_printpreview('arriveSheet22',{arriveSheetNos:arriveSheetNos, 
				    		currentUserEmpName : FossUserContext.getCurrentUserEmp().empName, 
				    		currentUserDepCode : FossUserContext.getCurrentDeptCode(),
				    		printValue : printValue},ContextPath.PKP_DELIVER);
		    		} else {
				    	do_printpreview('arriveSheet33',{arriveSheetNos:arriveSheetNos, 
				    	currentUserEmpName : FossUserContext.getCurrentUserEmp().empName, 
				    	currentUserDepCode : FossUserContext.getCurrentDeptCode(),
				    	printValue : printValue},ContextPath.PKP_DELIVER);
		    		}
		    	} else {
		    		if (printValue == '2') {
			    		do_print_noimg('arriveSheet22',{arriveSheetNos:arriveSheetNos, 
			    		currentUserEmpName : FossUserContext.getCurrentUserEmp().empName, 
			    		currentUserDepCode : FossUserContext.getCurrentDeptCode(),
			    		printValue : printValue},ContextPath.PKP_DELIVER);
		    		} else {
			    		do_print_noimg('arriveSheet33',{arriveSheetNos:arriveSheetNos, 
			    		currentUserEmpName : FossUserContext.getCurrentUserEmp().empName, 
			    		currentUserDepCode : FossUserContext.getCurrentDeptCode(),
			    		printValue : printValue},ContextPath.PKP_DELIVER);
		    		}
		    	}
		    },
		    exception: function(response){
		    	var json = Ext.decode(response.responseText);
//		    	win.hide();
		    	Ext.ux.Toast.msg('提示信息', json.message, 'error', 2000);
		    }
		});
	}
}

//直接根据选择的到达联ID打印到达联
function printOne(parentGrid,printValue,win,fullPrint){
	var arriveSheetNos = '';
	win.hide();
	
	if(typeof(fullPrint) != "undefined" && fullPrint != null){
		parentGrid.store.each(function(record){
			if(arriveSheetNos.length == 0) {
				arriveSheetNos = record.get('arrivesheetNo');
			} else {
				/*arriveSheetNos = arriveSheetNos + "," + record.get('arrivesheetNo');*/
				if (!Ext.isEmpty(record.get('arrivesheetNo'))) {
					arriveSheetNos = arriveSheetNos + "," + record.get('arrivesheetNo');
				}
			}
		}
		)
	}else{
		var selectWaybill = parentGrid.getSelectionModel().getSelection();
	
		for(var i = 0;i<selectWaybill.length;i++){
			if (selectWaybill[i].data.destroyed == 'N') {
				if(arriveSheetNos.length == 0) {
					arriveSheetNos = selectWaybill[i].data.arrivesheetNo;
				} else {
					arriveSheetNos = arriveSheetNos + "," + selectWaybill[i].data.arrivesheetNo;
				}
			}
		}
	}
	
	if(printValue =='1'){
		Ext.Ajax.request({
//		    url: '../predeliver/printArriveSheet.action',
			url:ContextPath.PKP_DELIVER + '/predeliver/printArriveSheet.action',
		    params: {
		    	'vo.arriveSheetNos': arriveSheetNos
		    },
		    success: function(response){
		    	do_print_noimg('arriveSheet1',{arriveSheetNos:arriveSheetNos, 
		    		currentUserEmpName : FossUserContext.getCurrentUserEmp().empName, 
		    		currentUserDepCode : FossUserContext.getCurrentDeptCode(),
		    		printValue : printValue},ContextPath.PKP_DELIVER);
		    },
		    exception: function(response){
		    	var json = Ext.decode(response.responseText);
//		    	win.hide();
		    	Ext.ux.Toast.msg('提示信息', json.message, 'error', 3000);
		    }
		});
	}else{
		Ext.Ajax.request({
//		    url: '../predeliver/printArriveSheet.action',
			url:ContextPath.PKP_DELIVER + '/predeliver/printArriveSheet.action',
		    params: {
		    	'vo.arriveSheetNos': arriveSheetNos
		    },
		    success: function(response){
//		    	win.hide();
		    	if (printValue == '2') {
		    		do_print_noimg('arriveSheet22',{arriveSheetNos:arriveSheetNos, 
		    		currentUserEmpName : FossUserContext.getCurrentUserEmp().empName,
		    		currentUserDepCode : FossUserContext.getCurrentDeptCode(),
		    		printValue : printValue},ContextPath.PKP_DELIVER);
		    	} else {
		    		do_print_noimg('arriveSheet33',{arriveSheetNos:arriveSheetNos, 
		    		currentUserEmpName : FossUserContext.getCurrentUserEmp().empName,
		    		currentUserDepCode : FossUserContext.getCurrentDeptCode(),
		    		printValue : printValue},ContextPath.PKP_DELIVER);
		    	}
		    },
		     exception: function(response){
		    	var json = Ext.decode(response.responseText);
//		    	win.hide();
		    	Ext.ux.Toast.msg('提示信息', json.message, 'error', 3000);
		    }
		});
	}
}

//根据运单打印到达联选择打印panel
Ext.define('Foss.printArriveSheet.printPanel',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaults: {
		margin:'8',
		xtype : 'textfield',
		labelWidth:90
	},
	parentGrid: null,
	items: [{
				xtype: 'label',
				text:'请选择板式：',
				columnWidth: 1
			},{
				 xtype: 'radiofield',
				 boxLabel: '普通版',
				 inputValue: '1',
				 name: 'print',
				 checked :true,
				 columnWidth: .30
			},{
				 xtype: 'radiofield',
				 boxLabel :'激光旧版',
				 inputValue: '2',
				 name: 'print',
				 columnWidth: .30
			},{
				 xtype: 'radiofield',
				 boxLabel :'激光新版',
				 inputValue: '3',
				 name: 'print',
				 columnWidth: .30
			},{
    			border: 1,
    			xtype:'container',
    			columnWidth:1,
    			defaultType:'button',
    			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
    			layout:'column',
    			items:[{
    				text:'确定',
    				columnWidth:.3,
    				handler:function(){
    					var form = this.up('form'),
    					//var parentGrid = Ext.getCmp('T_predeliver-printArriveSheetIndex_content').getResultGrid();
    						printValue = form.getForm().getValues().print,
    						win = this.up('window');
    					print(form.parentGrid,printValue,win);
    				}
    		},{
    			xtype: 'container',
    			border : false,
    			columnWidth:.4,
    			html: '&nbsp;'
    		},{
    			text: '取消',
    			columnWidth:.3,
    			handler:function(){
    				this.up('window').hide();
    			}
    		}]
    	}],
	 constructor: function(parentGrid){
			var me = this;
			me.parentGrid = parentGrid;
			me.callParent();
	}
});
//predeliver.printPanel = Ext.create('Foss.printArriveSheet.printPanel');
//根据运单打印到达联 选择打印版式 window
Ext.define('Foss.printArriveSheet.printWindow', {
	extend: 'Ext.window.Window',
	width: 300,
	height : 180,
	title: '提示',
	layout:'column',
	border: true,
	//将window的关闭事件close 设成 hide
	closeAction : 'hide',
	parentGrid: null,
	modal: true,
	printType : null,
	source: null,
	constructor: function(parentGrid){
		var me = this;
		me.items = [Ext.create('Foss.printArriveSheet.printPanel',parentGrid)]
		me.callParent();
	},
	setPrintType : function(printType) {
		this.printType = printType;
	},
	setSource : function(source) {
		this.source = source;
	}
});

//到达联管理直接打印到达联panel
Ext.define('Foss.printArriveSheet.printOnePanel',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaults: {
		margin:'8',
		xtype : 'textfield',
		labelWidth:90
	},
	parentGrid: null,
	fullPrint: null,
	
	items: [{
				xtype: 'label',
				text:'请选择打印板式：',
				columnWidth: 1
			},{
				 xtype: 'radiofield',
				 boxLabel: '普通版',
				 inputValue: '1',
				 name: 'print',
				 checked :true,
				 columnWidth: .3
			},{
				 xtype: 'radiofield',
				 boxLabel :'激光旧版',
				 inputValue: '2',
				 name: 'print',
				 columnWidth: .3
			},{
				 xtype: 'radiofield',
				 boxLabel :'激光新版',
				 inputValue: '3',
				 name: 'print',
				 columnWidth: .3
			},{
    			border: 1,
    			xtype:'container',
    			columnWidth:1,
    			defaultType:'button',
    			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
    			layout:'column',
    			items:[{
    				text:'打印',
    				columnWidth:.3,
    				handler:function(){
    					var form = this.up('form'),
    					//var parentGrid = Ext.getCmp('T_predeliver-printArriveSheetIndex_content').getResultGrid();
    						printValue = form.getForm().getValues().print,
    						win = this.up('window');
    					printOne(form.parentGrid,printValue,win,form.fullPrint);
    				}
    		},{
    			xtype: 'container',
    			border : false,
    			columnWidth:.4,
    			html: '&nbsp;'
    		},{
    			text: '取消',
    			columnWidth:.3,
    			handler:function(){
    				this.up('window').hide();
    			}
    		}]
    	}],
	 constructor: function(parentGrid,fullPrint){
			var me = this;
			me.parentGrid = parentGrid;
			me.fullPrint = fullPrint;
			me.callParent();
	}
});

//到达联管理直接打印到达联window
Ext.define('Foss.printArriveSheet.printOneWindow', {
	extend: 'Ext.window.Window',
	width: 300,
	height : 180,
	title: '提示',
	layout:'column',
	border: true,
	//将window的关闭事件close 设成 hide
	closeAction : 'hide',
	parentGrid: null,
	modal: true,
	constructor: function(parentGrid,fullPrint){
		var me = this;
		me.items = [Ext.create('Foss.printArriveSheet.printOnePanel',parentGrid,fullPrint)]
		me.callParent();
	}
});