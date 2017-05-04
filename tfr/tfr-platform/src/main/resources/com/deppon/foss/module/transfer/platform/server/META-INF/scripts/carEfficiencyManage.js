//获取后台传回的上级转运场信息
if(!Ext.isEmpty(platform.carEfficiencyManage.outfieldCode)){
	platform.carEfficiencyManage.outfieldCanBeModified = true;
}else{
	platform.carEfficiencyManage.outfieldCanBeModified = false;
}
//操作类型
Ext.define('Foss.platform.carEfficiencyManage.opTypeStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"1", "name":"装车"},
        {"value":"2", "name":"卸车"},
        {"value":"0", "name":"全部"}
    ]
});
//是否合格
Ext.define('Foss.platform.carEfficiencyManage.opResultStore', {
	extend:'Ext.data.Store',
    fields: ['value', 'name'],
    data : [
        {"value":"1", "name":"是"},
        {"value":"0", "name":"否"},
        {"value":"3", "name":"全部"}
    ]
});
//长途装卸车效率 查询form
Ext.define('Foss.platform.carEfficiencyManage.longWayQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85
		//columnWidth : 1 / 4,
		//xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		xtype : 'textfield',
		fieldLabel : '车次号',
		columnWidth : .25,
		name : 'billNo'
	},{
		xtype : 'textfield',
		fieldLabel : '车牌号',
		columnWidth : .25,
		xtype : 'commontruckselector',
		name : 'vehicleNo'
	},{
		name : 'actualDepartTime',
		columnWidth : .25,
		xtype: 'datefield',
		fieldLabel:  '日期',
		allowBlank: false,
		dateType: 'datetimefield_date97',
		format: 'Y-m-d', 
		value: Ext.Date.format(new Date(new Date().getFullYear(),
				new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		maxValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-1), 'Y-m-d')
	},{
		fieldLabel : '外场',
		columnWidth : .25,
		name : 'orgCode',
		allowBlank: false,
		xtype : 'dynamicorgcombselector',
		readOnly : platform.carEfficiencyManage.outfieldCanBeModified,
		value : platform.carEfficiencyManage.outfieldName,
		type : 'ORG',
		transferCenter : 'Y'
	},{
		xtype: 'combo',
		columnWidth : .25,
		name: 'typeLoad',
		editable :false,
		fieldLabel:  '操作类型',//'操作类型',
		store: Ext.create('Foss.platform.carEfficiencyManage.opTypeStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : '0'
	},{
		xtype: 'combo',
		columnWidth : .25,
		name: 'flagPassing',
		editable :false,
		fieldLabel:  '是否合格',//'是否合格',
		store: Ext.create('Foss.platform.carEfficiencyManage.opResultStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : '3'
	}, {
		border : false,
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border: true,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
		xtype : 'button',
		text : '重置',
		columnWidth : 0.08,
		handler : function() {
			this.up('form').getForm().reset();
		}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.84
		},{
		xtype : 'button',
		cls : 'yellow_button',
		columnWidth : 0.08,
		text : '查询',
		handler : function() {
					if(platform.carEfficiencyManage.queryLongWayForm.getForm().isValid()){
					platform.carEfficiencyManage.queryLongWayGrid.store.load();
				}
		}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//长途装卸车效率 列表之Model
Ext.define('Foss.platform.carEfficiencyManage.queryLongWayModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'actualDepartTime',
		type : 'string'
	},{
		name : 'billNo',
		type : 'string'
	},{
		name : 'vehicleNo',
		type : 'string'
	},{
		name : 'orgName',
		type : 'string'
	},{
		name : 'typeLoad',
		type : 'string'
	},{
		name : 'weightTotal',
		type : 'string'
	},{
		name : 'startTime',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{
		name : 'endTime',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{
		name : 'timeSection',
		type : 'string'
	},{
		name : 'efficiency',
		type : 'string'
	},{
		name : 'flagPassing',
		type : 'string'
	},{
		name : 'loaderCode',
		type : 'string'
	},{
		name : 'loaderName',
		type : 'string'
	},{
		name : 'taskIds',
		type : 'string'
	}]
});

//长途装卸车效率 列表之store
Ext.define('Foss.platform.carEfficiencyManage.queryLongWayStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.carEfficiencyManage.queryLongWayModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('carEfficiencyManageLongWay.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'carEfficiencyManageVo.carEfficiencyList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
		   var valuesForm = platform.carEfficiencyManage.queryLongWayForm.getValues();
			 if(platform.carEfficiencyManage.outfieldCanBeModified){
				valuesForm.orgCode=platform.carEfficiencyManage.outfieldCode;
			 }
				Ext.apply(operation, {
					params : {
						'carEfficiencyManageVo.carEfficiencyDto.actualDepartTime':valuesForm.actualDepartTime,
						'carEfficiencyManageVo.carEfficiencyDto.orgCode':valuesForm.orgCode,
						'carEfficiencyManageVo.carEfficiencyDto.billNo':valuesForm.billNo,
						'carEfficiencyManageVo.carEfficiencyDto.vehicleNo':valuesForm.vehicleNo,
						'carEfficiencyManageVo.carEfficiencyDto.typeLoad':valuesForm.typeLoad,
						'carEfficiencyManageVo.carEfficiencyDto.flagPassing':valuesForm.flagPassing
					}
				});	
		},
		'load': function(records, operation, success){
			}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//长途装卸车效率 列表
Ext.define('Foss.platform.carEfficiencyManage.queryLongWayGridGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '长途装卸车效率',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.carEfficiencyManage.queryLongWayStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('carEfficiencyLongWayExport.action');
			var valuesForm = platform.carEfficiencyManage.queryLongWayForm.getValues();
				 if(platform.carEfficiencyManage.outfieldCanBeModified){
				valuesForm.orgCode=platform.carEfficiencyManage.outfieldCode;
			 }
	
			var exportParams = {
					'carEfficiencyManageVo.carEfficiencyDto.actualDepartTime':valuesForm.actualDepartTime,
					'carEfficiencyManageVo.carEfficiencyDto.orgCode':valuesForm.orgCode,
					'carEfficiencyManageVo.carEfficiencyDto.billNo':valuesForm.billNo,
					'carEfficiencyManageVo.carEfficiencyDto.vehicleNo':valuesForm.vehicleNo,
					'carEfficiencyManageVo.carEfficiencyDto.typeLoad':valuesForm.typeLoad,
					'carEfficiencyManageVo.carEfficiencyDto.flagPassing':valuesForm.flagPassing
			}; 
			if(!Ext.fly('downloadLongWayCarEfficiencyFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadLongWayCarEfficiencyFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadLongWayCarEfficiencyFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'actualDepartTime',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '日期'
	},{
		dataIndex : 'billNo',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '车次号',
		renderer : function(value,metaData,record){
			if(value!=null){
			var tmpTaskIds = record.get('taskIds');
			return '<a href="javascript:platform.carEfficiencyManage.windowsNew('+"'"+tmpTaskIds+"'"+')">' + value + '</a>';
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '车牌号'
	}, {
		dataIndex : 'orgName',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '外场'
	}, {
		dataIndex : 'typeLoad',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '操作类型',
		renderer : function(value){
			if(value != null){
				if(value==='1'){
					return '装车';
				}
				if(value==='2'){
					return '卸车';
				}
			}else{
				return value;
			}
		}	
	}, {
		dataIndex : 'weightTotal',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '重量(吨)'
	}, {
		dataIndex : 'startTime',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '任务建立时间'
	}, {
		dataIndex : 'endTime',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '任务完成时间'
	}, {
		dataIndex : 'timeSection',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '时长(小时)'
	}, {
		dataIndex : 'efficiency',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '效率'
	}, {
		dataIndex : 'flagPassing',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '是否合格',
		renderer : function(value){
			if(value != null){
				if(value==='1'){
					return '是';
				}
				if(value==='0'){
					return '否';
				}
			}else{
				return value;
			}
		}	
	}],
	listeners:{
		//cellclick:function(table, td, cellIndex,record, tr, rowIndex,  e, eOpts){
		//platform.carEfficiencyManage.taskIds = record.get('taskIds');
		//var operatorWindow = Ext.create('Foss.platform.carEfficiencyManage.operatorWindow');
		//operatorWindow.show();
		//}
	}
});

//短途装卸车效率 之查询form
Ext.define('Foss.platform.carEfficiencyManage.shortWayQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		//labelWidth : 85
		//columnWidth : 1 / 4,
		//xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '车牌号',
		columnWidth : 1 / 4,
		xtype : 'commontruckselector',
		name : 'vehicleNo'
	},{
		fieldLabel : '外场',
		columnWidth : 1 / 4,
		name : 'orgCode',
		allowBlank: false,
		xtype : 'dynamicorgcombselector',
		readOnly : platform.carEfficiencyManage.outfieldCanBeModified,
		value : platform.carEfficiencyManage.outfieldName,
		type : 'ORG',
		transferCenter : 'Y'
	},{
		name : 'actualDepartTime',
		columnWidth : 1 / 4,
		xtype: 'datefield',
		fieldLabel:  '日期',
		allowBlank: false,
		dateType: 'datetimefield_date97',
		format: 'Y-m-d', 
		value: Ext.Date.format(new Date(new Date().getFullYear(),
				new Date().getMonth(), new Date().getDate()-1), 'Y-m-d'),
		maxValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-1), 'Y-m-d')
		
	},{
		xtype: 'combo',
		columnWidth : 1 / 4,
		name: 'typeLoad',
		editable :false,
		fieldLabel:  '操作类型',//'操作类型',
		store: Ext.create('Foss.platform.carEfficiencyManage.opTypeStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : '0'
	},{
		xtype: 'combo',
		columnWidth : 0.25,
		name: 'flagPassing',
		editable :false,
		fieldLabel:  '是否合格',//'是否合格',
		store: Ext.create('Foss.platform.carEfficiencyManage.opResultStore'),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		value : '3'
	}, {
		border : false,
		columnWidth : 0.75,
		html : '&nbsp;'
	},{
		border: true,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
		xtype : 'button',
		text : '重置',
		columnWidth : 0.08,
		handler : function() {
			this.up('form').getForm().reset();
		}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.84
		},{
		xtype : 'button',
		cls : 'yellow_button',
		columnWidth : 0.08,
		text : '查询',
		handler : function() {
				if(platform.carEfficiencyManage.queryShortWayForm.getForm().isValid()){
					platform.carEfficiencyManage.queryShortWayGrid.store.load();
				}
		}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//短途装卸车效率 列表之Model
Ext.define('Foss.platform.carEfficiencyManage.queryShortWayModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'actualDepartTime',
		type : 'string'
	},{
		name : 'vehicleNo',
		type : 'string'
	},{
		name : 'orgName',
		type : 'string'
	},{
		name : 'typeLoad',
		type : 'string'
	},{
		name : 'weightTotal',
		type : 'string'
	},{
		name : 'startTime',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{
		name : 'endTime',
		type : 'date',
		convert: function(value) {
			if (value != null) {
				var date = new Date(value);						
				return Ext.Date.format(date,'Y-m-d H:i:s');
			} else {
				return null;
			}
		}
	},{
		name : 'timeSection',
		type : 'string'
	},{
		name : 'efficiency',
		type : 'string'
	},{
		name : 'flagPassing',
		type : 'string'
	},{
		name : 'loaderCode',
		type : 'string'
	},{
		name : 'loaderName',
		type : 'string'
	},{
		name : 'taskIds',
		type : 'string'
	}]
});

//短途装卸车效率 列表之store
Ext.define('Foss.platform.carEfficiencyManage.queryShortWayStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.carEfficiencyManage.queryShortWayModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('carEfficiencyManageShortWay.action'),
		timeout:300000,
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'carEfficiencyManageVo.carEfficiencyList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
				 var valuesForm = platform.carEfficiencyManage.queryShortWayForm.getValues();
				 if(platform.carEfficiencyManage.outfieldCanBeModified){
				valuesForm.orgCode=platform.carEfficiencyManage.outfieldCode;
			 }
				Ext.apply(operation, {
					params : {
						'carEfficiencyManageVo.carEfficiencyDto.actualDepartTime':valuesForm.actualDepartTime,
						'carEfficiencyManageVo.carEfficiencyDto.orgCode':valuesForm.orgCode,
						'carEfficiencyManageVo.carEfficiencyDto.vehicleNo':valuesForm.vehicleNo,
						'carEfficiencyManageVo.carEfficiencyDto.typeLoad':valuesForm.typeLoad,
						'carEfficiencyManageVo.carEfficiencyDto.flagPassing':valuesForm.flagPassing
					}
				});	
		},
		'load': function(records, operation, success){
//				for(var i = 0; i<records.data.length;i++){
//					var record = records.data.items[i];
//				}
			}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//短途装卸车效率 列表
Ext.define('Foss.platform.carEfficiencyManage.queryShortWayGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '短途装卸车效率',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	sortableColumns : false,
	enableColumnHide : false,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.carEfficiencyManage.queryShortWayStore');
		
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler: function(){
			var actionUrl=platform.realPath('carEfficiencyShortWayExport.action');
			var valuesForm = platform.carEfficiencyManage.queryShortWayForm.getValues();
				 if(platform.carEfficiencyManage.outfieldCanBeModified){
				valuesForm.orgCode=platform.carEfficiencyManage.outfieldCode;
			 }
	
			var exportParams = {
					'carEfficiencyManageVo.carEfficiencyDto.actualDepartTime':valuesForm.actualDepartTime,
					'carEfficiencyManageVo.carEfficiencyDto.orgCode':valuesForm.orgCode,
					'carEfficiencyManageVo.carEfficiencyDto.vehicleNo':valuesForm.vehicleNo,
					'carEfficiencyManageVo.carEfficiencyDto.typeLoad':valuesForm.typeLoad,
					'carEfficiencyManageVo.carEfficiencyDto.flagPassing':valuesForm.flagPassing
			}; 
			if(!Ext.fly('downloadShortWayCarEfficiencyFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadShortWayCarEfficiencyFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
			url:actionUrl,
			form: Ext.fly('downloadShortWayCarEfficiencyFileForm'),
			method : 'POST',
			params : exportParams,
			isUpload: true,
			exception : function(response,opts) {
				var result = Ext.decode(response.responseText);
				Ext.MessageBox.alert('提示','导出失败',result.message);
				//myMask.hide();
			}	
			});
		}
	}],
	columns : [{
		dataIndex : 'actualDepartTime',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '日期'
	}, {
		dataIndex : 'vehicleNo',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '车牌号',
		renderer : function(value,metaData,record){
			if(value!=null){
			var tmpTaskIds = record.get('taskIds');
			return '<a href="javascript:platform.carEfficiencyManage.windowsNew('+"'"+tmpTaskIds+"'"+')">' + value + '</a>';
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'orgName',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '外场'
	}, {
		dataIndex : 'typeLoad',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '操作类型',
		renderer : function(value){
			if(value != null){
				if(value==='1'){
					return '装车';
				}
				if(value==='2'){
					return '卸车';
				}
			}else{
				return value;
			}
		}
	}, {
		dataIndex : 'weightTotal',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '重量(吨)'
	}, {
		dataIndex : 'startTime',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '任务建立时间'
	}, {
		dataIndex : 'endTime',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '任务完成时间'
	}, {
		dataIndex : 'timeSection',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '时长(小时)'
	}, {
		dataIndex : 'efficiency',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '效率'
	}, {
		dataIndex : 'flagPassing',
		align : 'center',
		xtype : 'ellipsiscolumn',
		text : '是否合格',
		renderer : function(value){
			if(value != null){
				if(value==='1'){
					return '是';
				}
				if(value==='0'){
					return '否';
				}
			}else{
				return value;
			}
		}		
	}],
	listeners:{
		//cellclick:function(table, td, cellIndex,record, tr, rowIndex,  e, eOpts){
		//platform.carEfficiencyManage.taskIds = record.get('taskIds');
		//var operatorWindow = Ext.create('Foss.platform.carEfficiencyManage.operatorWindow');
		//operatorWindow.show();
		//}
	}
});

//理货员的信息 Store
Ext.define('Foss.platform.carEfficiencyManage.operatorStore',{
	extend: 'Ext.data.Store',
	autoLoad: true,
	model : 'Foss.platform.carEfficiencyManage.queryLongWayModel',
	pageSize:10,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: platform.realPath('queryLoaderInfo.action'),
        timeout:300000,
		reader : {
			type : 'json',
			root : 'carEfficiencyManageVo.carEfficiencyList',
			//totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var tmpTaskIds = platform.carEfficiencyManage.taskIds;
				platform.carEfficiencyManage.taskIds='';
				Ext.apply(operation, {
					params : {
						'carEfficiencyManageVo.carEfficiencyDto.taskIds':tmpTaskIds
					}
				});	
		},
		'load': function(records, operation, success){
				//for(var i = 0; i<records.data.length;i++){
					//var record = records.data.items[i];
					//var recordStatic = record.get('stockStatic');
				//}
			}
	}
});

//理货员的号表格
Ext.define('Foss.platform.carEfficiencyManage.operatorGrid', {
	extend:'Ext.grid.Panel',
	columnLines: true,
    frame: true,
    //height: 500,
	columns: [{
			header: '工号', 
			dataIndex: 'loaderCode' 
		},{
			header: '理货员',
			dataIndex: 'loaderName' 
		}],   
		
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.carEfficiencyManage.operatorStore');
		me.callParent([cfg]);
	},
	listeners: {
	}
});

//理货员 的窗口
Ext.define('Foss.platform.carEfficiencyManage.operatorWindow', {
	extend: 'Ext.window.Window',
	title: '理货员',
	modal:true,
	closeAction: 'hide',
	width: 300,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var operatorGrid = Ext.create('Foss.platform.carEfficiencyManage.operatorGrid'); 	
		me.items = [operatorGrid];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
		}
	}
});

//定义打开新tab方法
platform.carEfficiencyManage.windowsNew = function(taskIds){
	//platform.carEfficiencyManage.taskIds = '';
	platform.carEfficiencyManage.taskIds = taskIds;
	var operatorWindow = Ext.create('Foss.platform.carEfficiencyManage.operatorWindow');
	operatorWindow.show();
};

//TabPanel
Ext.onReady(function() {
	Ext.QuickTips.init();
	//长途装卸效率查询表单
	var queryLongWayForm = Ext.create('Foss.platform.carEfficiencyManage.longWayQueryForm'); 
	//短途装卸效率查询表单
	var queryShortWayForm = Ext.create('Foss.platform.carEfficiencyManage.shortWayQueryForm'); 
	
	//加入全局变更
	platform.carEfficiencyManage.queryLongWayForm = queryLongWayForm;//长途装卸效率查询表单
	platform.carEfficiencyManage.queryShortWayForm = queryShortWayForm;//短途装卸效率查询表单
	
	//长途装卸效率信息表格
	var queryLongWayGrid = Ext.create('Foss.platform.carEfficiencyManage.queryLongWayGridGrid');
	//短途装卸效率查询表格
	var queryShortWayGrid = Ext.create('Foss.platform.carEfficiencyManage.queryShortWayGrid');
	
	//加入全局变更
	platform.carEfficiencyManage.queryLongWayGrid = queryLongWayGrid;
	platform.carEfficiencyManage.queryShortWayGrid = queryShortWayGrid;
	
	
	//装卸车对应任务ID
	platform.carEfficiencyManage.taskIds = '';
	
	
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-carEfficiencyManageIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '长途装卸车效率',
				tabConfig : {
					width : 180
				},
				items : [queryLongWayForm,queryLongWayGrid]
			},{
				title : '短途装卸车效率',
				tabConfig : {
					width : 180
				},
				items : [queryShortWayForm,queryShortWayGrid]
			}]
		}],
		renderTo: 'T_platform-carEfficiencyManageIndex-body'
	});
});