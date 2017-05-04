/**
*
*装卸车效率查询界面
*
*/
Ext.define('Foss.platform.truckEfficiencyIndex.Model',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'statisticDate',
		type : 'date',
		convert : function(value) {
			if (value != null) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d');
			} else {
				return null;
			}
		}
	},{
		name : 'statisticMonth',
		type : 'string'
	},{
		name : 'transferCenterCode',
		type : 'string'
	},{
		name : 'transferCenterName',
		type : 'string'
	},{
		name : 'loadTotalWeight',
		type : 'string'
	},{
		name : 'unloadTotalWeight',
		type : 'string'
	},{
		name : 'loadTotalDuration',
		type : 'string'
	},{
		name : 'unloadTotalDuration',
		type : 'string'
	},{
		name : 'loadRatio',
		type : 'string'
	},{
		name : 'unloadRatio',
		type : 'string'
	},{
		name : 'totalRatio',
		type : 'string'
	},{
		name : 'createTime',
		type : 'date'
	}]
});


Ext.define('Foss.platform.truckEfficiencyIndex.storeByDay',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.truckEfficiencyIndex.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryLoadUnloadEfficiencyByDay.action'),
		reader : {
			type : 'json',
			root : 'truckEfficiencyVo.truckEfficiencyList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.truckEfficiencyIndex.queryFormByDay;
			if(queryForm != null){
				var queryParams = queryForm.getValues();
				Ext.apply(operation,{
					params : queryParams
				});
			}	
		},
		load : function(store,records,successful,eOpts){
			var truckEfficiencyVo = store.proxy.reader.rawData.truckEfficiencyVo;
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

platform.truckEfficiencyIndex.getCurrentDept = function(item,operationCode,transferCode,str){
	if(!Ext.isEmpty(transferCode) && Ext.isEmpty(operationCode)){
		if(str == 'transferCode'){
			item.readOnly = true;
		}
	}else if(Ext.isEmpty(transferCode) && !Ext.isEmpty(operationCode)){
		if(str == 'operationCode'){
			item.readOnly = true;
		}
	}
}


Ext.define('Foss.platform.truckEfficiencyIndex.formByDay', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'truckEfficiencyVo.operationDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
	//	readOnly : !Ext.isEmpty(platform.truckEfficiencyIndex.operationDeptCode),
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.truckEfficiencyIndex.getCurrentDept(_this,platform.truckEfficiencyIndex.operationDeptCode,platform.truckEfficiencyIndex.outfieldCode,'operationCode');
			}
		}
	},{
		name : 'truckEfficiencyVo.transferCenterCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
	//	readOnly : !Ext.isEmpty(platform.truckEfficiencyIndex.outfieldCode),
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.truckEfficiencyIndex.getCurrentDept(_this,platform.truckEfficiencyIndex.operationDeptCode,platform.truckEfficiencyIndex.outfieldCode,'transferCode');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'truckEfficiencyVo.statisticDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime,Ext.Date.DAY,-1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m-d',
		columnWidth :.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function() {
				var form = platform.truckEfficiencyIndex.queryFormByDay.getForm();
				form.reset();
				var operationCode = form.findField('truckEfficiencyVo.operationDeptCode');
				var transferCode = form.findField('truckEfficiencyVo.transferCenterCode');
				if(operationCode.readOnly == true){
						operationCode.setCombValue(
							platform.truckEfficiencyIndex.operationDeptName,
							platform.truckEfficiencyIndex.operationDeptCode
							)
				}
				if(transferCode.readOnly == true){
						transferCode.setCombValue(
							platform.truckEfficiencyIndex.outfieldName,
							platform.truckEfficiencyIndex.outfieldCode
							)
				}
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.truckEfficiencyIndex.queryFormByDay.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				platform.truckEfficiencyIndex.pagingBarByDay.moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


platform.truckEfficiencyIndex.queryFormByDay = Ext.create('Foss.platform.truckEfficiencyIndex.formByDay');

Ext.define('Foss.platform.truckEfficiencyIndex.gridByDay', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	title : '查询结果',
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '日期',
		dataIndex : 'statisticDate',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '转运场',
		dataIndex : 'transferCenterName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '装车操作货量',
		dataIndex : 'loadTotalWeight',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车操作货量',
		dataIndex : 'unloadTotalWeight',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '装车操作时长',
		dataIndex : 'loadTotalDuration',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车操作时长',
		dataIndex : 'unloadTotalDuration',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '装车效率',
		dataIndex : 'loadRatio',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车效率',
		dataIndex : 'unloadRatio',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '总效率',
		dataIndex : 'totalRatio',
		flex : 0.8
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.truckEfficiencyIndex.storeByDay');
		
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}
				//获取查询参数
				var form = platform.truckEfficiencyIndex.queryFormByDay.getForm();
				var queryParams = form.getValues();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的交接时间！','error', 2000);
					return;
				}
				Ext.Ajax.request({
					url : platform.realPath('exportLoadUnloadEfficiencyByDay.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'truckEfficiencyVo.transferCenterCode' : queryParams['truckEfficiencyVo.transferCenterCode'],
						'truckEfficiencyVo.operationDeptCode' : queryParams['truckEfficiencyVo.operationDeptCode'],
						'truckEfficiencyVo.statisticDate' : queryParams['truckEfficiencyVo.statisticDate']
					},
					isUpload: true,
					success:function(response){
					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败',result.message);
					}
				});
			}
		}],
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.truckEfficiencyIndex.pagingBarByDay = me.bbar;
		me.callParent([cfg]);
	}
//	viewConfig : {
//		enableTextSelection : true
//	}
});

platform.truckEfficiencyIndex.gridByDay =Ext.create('Foss.platform.truckEfficiencyIndex.gridByDay') ;

Ext.define('Foss.platform.truckEfficiencyIndex.PanelByDay', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var gridByDay = platform.truckEfficiencyIndex.gridByDay;
		var queryFormByDay = platform.truckEfficiencyIndex.queryFormByDay;
		me.items = [queryFormByDay, gridByDay];
		me.callParent([cfg]);
	}
})


/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!上面是   日均装卸车效率  store  grid  panel
 * ！！！！！！！！！！！！！！！！！下面 是    月均装卸车效率  store grid panel
 */
Ext.define('Foss.platform.truckEfficiencyIndex.storeByMonth',{
	extend : 'Ext.data.Store',
	model : 'Foss.platform.truckEfficiencyIndex.Model',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryLoadUnloadEfficiencyByMonth.action'),
		reader : {
			type : 'json',
			root : 'truckEfficiencyVo.truckEfficiencyList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	listeners : {
		beforeload : function(store,operation,eOpts) {
			var queryForm = platform.truckEfficiencyIndex.queryFormByMonth;
			if(queryForm != null){
				var queryParams = queryForm.getValues();
				var tempDate = queryParams['truckEfficiencyVo.statisticDate'];
				if (tempDate != null) {
					var date = new Date(tempDate);
					queryParams['truckEfficiencyVo.statisticDate'] = date;
				}
				Ext.apply(operation,{
					params : queryParams
				});
			}	
		},
		load : function(store,records,successful,eOpts){
			var truckEfficiencyVo = store.proxy.reader.rawData.truckEfficiencyVo;
			if(!store.proxy.reader.rawData.success){
				Ext.ux.Toast.msg('提示', '查询失败，' + store.proxy.reader.rawData.message, 'error');
			}
		}
	}
});

Ext.define('Foss.platform.truckEfficiencyIndex.formByMonth', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'truckEfficiencyVo.operationDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.truckEfficiencyIndex.getCurrentDept(_this,platform.truckEfficiencyIndex.operationDeptCode,platform.truckEfficiencyIndex.outfieldCode,'operationCode');
			}
		}
	},{
		name : 'truckEfficiencyVo.transferCenterCode',
		fieldLabel : '转运场',
		columnWidth : 0.25,
		allowBlank : true,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				platform.truckEfficiencyIndex.getCurrentDept(_this,platform.truckEfficiencyIndex.operationDeptCode,platform.truckEfficiencyIndex.outfieldCode,'transferCode');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'月份',
    	allowBlank:false,
		name:'truckEfficiencyVo.statisticDate',
		editable:false,
		value: login.currentServerTime,
		format:'Y-m',
		columnWidth:.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function() {
				var form = platform.truckEfficiencyIndex.queryFormByMonth.getForm();
				form.reset();
				var operationCode = form.findField('truckEfficiencyVo.operationDeptCode');
				var transferCode = form.findField('truckEfficiencyVo.transferCenterCode');
				if(operationCode.readOnly == true){
						operationCode.setCombValue(
							platform.truckEfficiencyIndex.operationDeptName,
							platform.truckEfficiencyIndex.operationDeptCode
							)
				}
				if(transferCode.readOnly == true){
						transferCode.setCombValue(
							platform.truckEfficiencyIndex.outfieldName,
							platform.truckEfficiencyIndex.outfieldCode
							)
				}
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function() {
				var form = platform.truckEfficiencyIndex.queryFormByMonth.getForm();
				if (!form.isValid()) {
					Ext.Msg
					.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				platform.truckEfficiencyIndex.pagingBarByMonth.moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

platform.truckEfficiencyIndex.queryFormByMonth = Ext.create('Foss.platform.truckEfficiencyIndex.formByMonth');


Ext.define('Foss.platform.truckEfficiencyIndex.gridByMonth', {
	extend : 'Ext.grid.Panel',
	frame : true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height:400,
	autoScroll : true,
	defaults : {
		align : 'center'
	},
	title : '查询结果',
	columns : [{
		xtype : 'ellipsiscolumn',
		header : '月份',
		dataIndex : 'statisticDate',
		flex : 0.8,
		renderer : function(value){
			if (value != null) {
				return Ext.Date.format(new Date(value), 'Y-m');
			} else {
				return null;
			}
		}
	},{
		xtype : 'ellipsiscolumn',
		header : '转运场',
		dataIndex : 'transferCenterName',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '装车操作货量',
		dataIndex : 'loadTotalWeight',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车操作货量',
		dataIndex : 'unloadTotalWeight',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '装车操作时长',
		dataIndex : 'loadTotalDuration',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车操作时长',
		dataIndex : 'unloadTotalDuration',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '装车效率',
		dataIndex : 'loadRatio',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '卸车效率',
		dataIndex : 'unloadRatio',
		flex : 0.8
	},{
		xtype : 'ellipsiscolumn',
		header : '总效率',
		dataIndex : 'totalRatio',
		flex : 0.8
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.platform.truckEfficiencyIndex.storeByMonth');
		
		me.tbar = [{
			xtype : 'button',
			text : '导出',
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}
				//获取查询参数
				var form = platform.truckEfficiencyIndex.queryFormByMonth.getForm();
				var queryParams = form.getValues();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示','请输入合法的交接时间！','error', 2000);
					return;
				}
				var tempDate = queryParams['truckEfficiencyVo.statisticDate'];
				var date;
				if (tempDate != null) {
					date = new Date(tempDate);
				}
				Ext.Ajax.request({
					url : platform.realPath('exportLoadUnloadEfficiencyByMonth.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'truckEfficiencyVo.transferCenterCode' : queryParams['truckEfficiencyVo.transferCenterCode'],
						'truckEfficiencyVo.operationDeptCode' : queryParams['truckEfficiencyVo.operationDeptCode'],
						'truckEfficiencyVo.statisticDate' : date
					},
					isUpload: true,
					success:function(response){
					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败',result.message);
					}
				});
			}
		}],
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		}),
		platform.truckEfficiencyIndex.pagingBarByMonth = me.bbar;
		me.callParent([cfg]);
	}
//	viewConfig : {
//		enableTextSelection : true
//	}
});


platform.truckEfficiencyIndex.gridByMonth =Ext.create('Foss.platform.truckEfficiencyIndex.gridByMonth') ;

Ext.define('Foss.platform.truckEfficiencyIndex.PanelByMonth', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'auto',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var gridByMonth = platform.truckEfficiencyIndex.gridByMonth;
		var queryFormByMonth = platform.truckEfficiencyIndex.queryFormByMonth;
		me.items = [queryFormByMonth, gridByMonth];
		me.callParent([cfg]);
	}
});


/**
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!      mainPanel
 */
Ext.define('Foss.platform.truckEfficiencyIndex.MainTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,
	
	autoScroll : false,
	frame : false,
	items : [{
				tabConfig : {
					width : 100
				},
				title : '日均装卸车效率',
				items : Ext.create('Foss.platform.truckEfficiencyIndex.PanelByDay')
			}, {
				tabConfig : {
					width : 100
				},
				title : '月均装卸车效率',
				items : Ext.create('Foss.platform.truckEfficiencyIndex.PanelByMonth')
			}]
});
/** -----------------------------------------------viewpanel--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	platform.truckEfficiencyIndex.MainTabPanel = Ext
			.create('Foss.platform.truckEfficiencyIndex.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
				id : 'T_platform-truckEfficiencyIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContent-body',
				items : [platform.truckEfficiencyIndex.MainTabPanel],
				renderTo : 'T_platform-truckEfficiencyIndex-body'
			});

	//如果当前部门找不到外场，但是找到了经营本部，则自动填写经营本部
	if(!Ext.isEmpty(platform.truckEfficiencyIndex.operationDeptCode)){
		platform.truckEfficiencyIndex.queryFormByDay.getForm().findField('truckEfficiencyVo.operationDeptCode').setCombValue(
			platform.truckEfficiencyIndex.operationDeptName,		
			platform.truckEfficiencyIndex.operationDeptCode
		);
		platform.truckEfficiencyIndex.queryFormByMonth.getForm().findField('truckEfficiencyVo.operationDeptCode').setCombValue(
		platform.truckEfficiencyIndex.operationDeptName,		
		platform.truckEfficiencyIndex.operationDeptCode
		);
	};
	//如果当前部门为外场，则自动填写外场
	if(!Ext.isEmpty(platform.truckEfficiencyIndex.outfieldCode)){
		platform.truckEfficiencyIndex.queryFormByDay.getForm().findField('truckEfficiencyVo.transferCenterCode').setCombValue(
			platform.truckEfficiencyIndex.outfieldName,
			platform.truckEfficiencyIndex.outfieldCode
			);
		platform.truckEfficiencyIndex.queryFormByMonth.getForm().findField('truckEfficiencyVo.transferCenterCode').setCombValue(
			platform.truckEfficiencyIndex.outfieldName,
			platform.truckEfficiencyIndex.outfieldCode
			);
	}
});



































