
//点击“交接单号”打开详情界面方法
load.connectionbillquery.showHandOverBillDetail = function(connectionBillNo){
	load.addTab('T_load-connectionbillshowindex',//对应打开的目标页面js的onReady里定义的renderTo
			'交接单详情',
			load.realPath('conectionbillshowindex.action') + '?connectionBillNo="' + connectionBillNo + '"');//对应的页面URL，可以在url后使用?x=123这种形式传参
}
//点击交接单号 打开详情界面方法（到达）
load.connectionbillquery.showHandOverBillDetails = function(arrivalConnectionBillNo){
	load.addTab('T_load-arrivalconnectionbillshowindex',
	        '到达接驳单详情',
	         load.realPath('arrivalconnectionbillshowindex.action')+'?arrivalConnectionBillNo="' + arrivalConnectionBillNo + '"');//对应的页面URL
}
//点击操作列“修改”图标打开修改界面方法
load.connectionbillquery.showConnectionBillModify = function(connectionBillNo){
		load.addTab('T_load-connectionbillmodifyindex',
		              '交接单修改'  ,
		load.realPath('connectionbillmodifyindex.action') + '?connectionBillNo="' + connectionBillNo+'"');
}




/********************************************接驳交接单列模型***************************************************************/
Ext.define('Foss.load.connectionbillquery.QueryConnectionBillModel',{
	extend:Ext.data.Model,
	fields : [{
		name : 'connectionBillNo',//交接单号
		type : 'string'
	},{
		name : 'statu',//状态
		type : 'string'
	}, {
		name : 'handOverTime',//交接日期
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	}, {
		name : 'vehicleNo',//车牌号
		type : 'string'
	}, {
		name : 'departDeptName',//出发部门
		type : 'string'
	}, {
		name : 'departDeptCode',//出发部门
		type : 'string'
	}, {
		name : 'arriveDeptName',//到达接驳点
		type : 'string'
	}, {
		name : 'arriveTime',//到达时间
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name : 'waybillQtyTotal',//总票数
		type : 'number'
	}, {
		name : 'goodsQtyTotal',//总件数
		type : 'number'
	}, {
		name : 'volumeTotal',//总体积 
		type : 'number'
	}, {
		name : 'weightTotal',//总重量
		type : 'number'
	}, {
		name : 'createUserName',//制单人
		type : 'string'
	}, {
		name : 'driverName',//司机
		type : 'string'
	}]
});

/********************************************接驳交接单到达列模型 218427 hongwy *****************************************************/
Ext.define('Foss.load.connectionbillquery.QueryArrivalConnectionBillModel',{
	extend:Ext.data.Model,
	fields : [{
		name : 'connectionBillNo',//交接单号
		type : 'string'
	},{
		name : 'statu',//状态
		type : 'string'
	}, {
		name : 'departTime',//出发时间
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	}, {
		name : 'vehicleNo',//车牌号
		type : 'string'
	},{
		name : 'arriveTime',//到达时间
		type : 'date',
		convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d H:i:s';
			return Ext.Date.format(date, formatStr);
		}
	},{
		name : 'waybillQtyTotal',//总票数
		type : 'number'
	}, {
		name : 'goodsQtyTotal',//总件数
		type : 'number'
	}, {
		name : 'volumeTotal',//总体积 
		type : 'number'
	}, {
		name : 'weightTotal',//总重量
		type : 'number'
	},  {
		name : 'driverName',//司机
		type : 'string'
	}]
});
/********************************************接驳交接单列表store**************************************************/
//定义交接单列表store
Ext.define('Foss.load.connectionbillquery.QueryConnectionBillStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.connectionbillquery.QueryConnectionBillModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryConnectionBillList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'connectionBillVo.connectionBillList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = load.connectionbillquery.ConnectionBillQueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'connectionBillVo.queryConnectionBillConditionDto.connectionBillNo' : queryParams.connectionBillNo,//交接单号
					'connectionBillVo.queryConnectionBillConditionDto.departDeptCode' : queryParams.departDeptCode,//出发部门编码
					'connectionBillVo.queryConnectionBillConditionDto.arriveDeptCode' : queryParams.arriveDeptCode,//到达接驳点编码
					'connectionBillVo.queryConnectionBillConditionDto.vehicleNo' : queryParams.vehicleNo,//车牌号
					'connectionBillVo.queryConnectionBillConditionDto.handOverType' : queryParams.handOverType,//交接类型
					'connectionBillVo.queryConnectionBillConditionDto.beginHandOverTime':Ext.Date.parse(queryParams.beginHandOverTime,'Y-m-d H:i:s'),//开始交接时间
					'connectionBillVo.queryConnectionBillConditionDto.endHandOverTime' : Ext.Date.parse(queryParams.endHandOverTime,'Y-m-d H:i:s'),//结束交接时间
					'connectionBillVo.queryConnectionBillConditionDto.isArrived' : queryParams.isArrived//是否已到达
				}
			});	
		}
	}
});

/********************************************接驳交接单(到达)列表store**************************************************/
Ext.define('Foss.load.connectionbillquery.QueryArrivalConnectionBillStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.connectionbillquery.QueryArrivalConnectionBillModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryArrivalConnectionBillList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'arrivalConnectionBillVo.connectionBillList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){
			var queryParams = load.connectionbillquery.ArrivalConnectionBillQueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'arrivalConnectionBillVo.queryArrivalConnectionBillConditionDto.connectionBillNo' : queryParams.connectionBillNo,//交接单号
					'arrivalConnectionBillVo.queryArrivalConnectionBillConditionDto.arriveDeptCode' : queryParams.arriveDeptCode,//到达接驳点编码(外场)
					'arrivalConnectionBillVo.queryArrivalConnectionBillConditionDto.vehicleNo' : queryParams.vehicleNo,//车牌号
					'arrivalConnectionBillVo.queryArrivalConnectionBillConditionDto.handOverType' : queryParams.handOverType,//交接类型
					'arrivalConnectionBillVo.queryArrivalConnectionBillConditionDto.departTime':Ext.Date.parse(queryParams.departTime,'Y-m-d H:i:s'),//开始交接时间
					'arrivalConnectionBillVo.queryArrivalConnectionBillConditionDto.arriveTime' : Ext.Date.parse(queryParams.arriveTime,'Y-m-d H:i:s'),//结束交接时间
					'arrivalConnectionBillVo.queryArrivalConnectionBillConditionDto.isArrived' : queryParams.isArrived//是否已到达
				}
			});	
		}
	}
});

/***************************************** 接驳交接单查询条件form*********************************/

Ext.define('Foss.load.connectionbillquery.ConnectionBillQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '交接单号',
		name : 'connectionBillNo'
	},{
		fieldLabel : '出发部门',
		name : 'departDeptCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		//readOnly:true,
		allowBlank:false
	},{
		fieldLabel : '到达接驳点',
		name : 'arriveDeptCode',
		xtype : 'accesspointselector'
		
	},{
		xtype : 'commontruckselector',
		name: 'vehicleNo',
		allowBlank: true,
		autoWidth:true,
		maxLength:100,
		forceSelection: true,
		fieldLabel:'车牌号'   //车牌号
	},{
		fieldLabel : '交接类型',
		name : 'handOverType',
	    readOnly:true,
		xtype : 'combobox',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'EXPRESS_CONNECTION_HANDOVER',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"EXPRESS_CONNECTION_HANDOVER", "value":"接驳交接单"}
	        ]
	    })
	
	},{
		xtype : 'rangeDateField',
		fieldLabel : '交接时间',
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_load_ConnectionQueryForm_HandOverTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 31,
		fromName : 'beginHandOverTime',
		toName : 'endHandOverTime',
		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), 
				new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'),
	   toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
				 new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
		allowBlank : false,
		disallowBlank : true
	},{
		fieldLabel :'是否已到达',
		name : 'isArrived',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'ALL',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"Yes", "value":"是"},
	            {"key":"No", "value":"否"}
	        ]
	    })
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : '重置',
			handler : function() {
				this.up('form').getForm().reset();
				if(load.connectionbillquery.transferCenter!='Y'){
					load.connectionbillquery.ConnectionBillQueryForm.getForm().findField('departDeptCode').setValue('');
				}else{
					load.connectionbillquery.ConnectionBillQueryForm.getForm().findField('departDeptCode').setCombValue(load.connectionbillquery.orgName,load.connectionbillquery.orgCode);
				}
				//重新初始化交接时间
				this.up('form').getForm().findField('beginHandOverTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), 
						new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endHandOverTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
						 new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled : !load.connectionbillquery.isPermission('load/queryConnectionBillListButton'),
			hidden : !load.connectionbillquery.isPermission('load/queryConnectionBillListButton'),
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					load.connectionbillquery.pagingBar.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

/***************************************** 到达交接单查询条件form  218427 hongwy *********************************/
Ext.define('Foss.load.connectionbillquery.ArrivalConnectionBillQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '交接单号',
		name : 'connectionBillNo'
	},{
		fieldLabel : '到达外场',
		name : 'arriveDeptCode',
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		airDispatch : 'Y',
		doAirDispatch : 'Y',
		//readOnly:true,
		allowBlank:false
	},{
		xtype : 'commontruckselector',
		name: 'vehicleNo',
		allowBlank: true,
		autoWidth:true,
		maxLength:100,
		forceSelection: true,
		fieldLabel:'车牌号'   //车牌号
	},{
		fieldLabel : '交接类型',
		name : 'handOverType',
	    readOnly:true,
		xtype : 'combobox',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'EXPRESS_DRIVER',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"EXPRESS_DRIVER", "value":"接驳交接单"}
	        ]
	    })
	
	},{
		xtype : 'rangeDateField',
		fieldLabel : '出发时间',
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标识的String值
		fieldId : 'Foss_load_ArrivalConnectionQueryForm_HandOverTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 31,
		fromName : 'departTime',
		toName : 'arriveTime',
		fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), 
				new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'),
	   toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
				 new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
		allowBlank : false,
		disallowBlank : true
	},{
		fieldLabel :'是否已到达',
		name : 'isArrived',
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value : 'ALL',
	    editable : false,
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"ALL", "value":"全部"},
	            {"key":"Yes", "value":"是"},
	            {"key":"No", "value":"否"}
	        ]
	    })
	},{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : '重置',
			handler : function() {
				this.up('form').getForm().reset();
				if(load.connectionbillquery.transferCenter!='Y'){
					load.connectionbillquery.ArrivalConnectionBillQueryForm.getForm().findField('arriveDeptCode').setValue('');
				}else{
					load.connectionbillquery.ArrivalConnectionBillQueryForm.getForm().findField('arriveDeptCode').setCombValue(load.connectionbillquery.orgName,load.connectionbillquery.orgCode);
				}
				//重新初始化交接时间
				this.up('form').getForm().findField('departTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(), 
						new Date().getDate(), 00, 00, 00), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('arriveTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
						 new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled : !load.connectionbillquery.isPermission('load/queryConnectionBillListButton'),
			hidden : !load.connectionbillquery.isPermission('load/queryConnectionBillListButton'),
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
			load1.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});


/************************************************************交接单打印widow*************************************************/

//定义打印模版window
Ext.define('Foss.load.connectionbillquery.PrintWindow', {
	extend: 'Ext.window.Window',
	title: '打印模板选择',
	layout:'column',
	height: 150,
	width: 300,
	closable:true,
	closeAction:'hide',
	modal: true,
	connectionBillNos : null,
	vehicleNo : null,
	grid : null,
	items : [{
		fieldLabel : '打印模版',
		name : 'printTemplate',
		columnWidth: 1,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    editable : false,
	    defaults: {
			margin: '10 5 10 5'
		},
	    store : Ext.create('Ext.data.Store', {
	        fields: ['key', 'value'],
	        data : [
	            {"key":"交接单", "value":"接驳交接单打印"},
	            {"key":"交接单(流水)", "value":"接驳交接单流水打印"}
	        ]
	    })
	},{
		xtype: 'container',
		columnWidth: .6,
		html: '&nbsp;'
	},{
		columnWidth : .39,
		xtype : 'button',
		text : '打印',
		handler : function(){
			var upwindow = this.up('window'),
				printTemplate = upwindow.items.items[0].getValue(),
				records	= upwindow.grid.getSelectionModel().getSelection(),
				vehicleNo	= upwindow.vehicleNo,
				connectionBillNos = upwindow.connectionBillNos;
			
			var currentDeptCode = FossUserContext.getCurrentDept().code;
			var currentDeptName = FossUserContext.getCurrentDept().name;
			var currentUserCode = FossUserContext.getCurrentUser().employee.empCode;
			var currentUserName = FossUserContext.getCurrentUser().employee.empName;
			if (printTemplate == '交接单') {
					do_printpreview('loadSC',{
						"connectionBillNos": connectionBillNos, 
						"currentDeptName" : currentDeptName,
						"currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, 
						"currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
				}else if(printTemplate == '交接单(流水)'){
					do_printpreview('loadSCsn',{
						"connectionBillNos": connectionBillNos, 
						"currentDeptName" : currentDeptName,
						"currentUserName" : currentUserName,
						"currentDeptCode" : currentDeptCode, 
						"currentUserCode" : currentUserCode}, ContextPath.TFR_EXECUTION)
					
				} 
		}
	}]
});



/***************************************** 接驳交接单查询列表grid*********************************/
Ext.define('Foss.load.connectionbillquery.queryConnectionBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '交接单列表',
	bodyCls : 'autoHeisght',
	cls : 'autoHeight',
	emptyText : '暂无数据',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		me.store = Ext.create('Foss.load.connectionbillquery.QueryConnectionBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text :'新增',
			disabled : !load.connectionbillquery.isPermission('button/connectionBillAddnewButton'),
			hidden : !load.connectionbillquery.isPermission('button/connectionBillAddnewButton'),
			handler : function(){
				load.addTab('T_load-connectionbilladdnewindex',//对应打开的目标页面js的onReady里定义的renderTo
							'新增接驳交接单',//打开的Tab页的标题
							load.realPath('connectionbilladdnewindex.action'));//对应的页面URL，可以在url后使用?x=123这种形式传参
			}
			
		},'->',{
			xtype : 'button',
			text :'导出',
			disabled : !load.connectionbillquery.isPermission('button/connectionBillExportButton'),
			hidden : !load.connectionbillquery.isPermission('button/connectionBillExportButton'),
			handler : function(){
				
				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
				}
				//获取查询参数
				var queryParams = load.connectionbillquery.ConnectionBillQueryForm.getForm().getValues();
				Ext.Ajax.request({
					url : load.realPath('exportConnectionBillExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
						'connectionBillVo.queryConnectionBillConditionDto.connectionBillNo' : queryParams.connectionBillNo,//交接单号
						'connectionBillVo.queryConnectionBillConditionDto.departDeptCode' : queryParams.departDeptCode,//出发部门编码
						'connectionBillVo.queryConnectionBillConditionDto.arriveDeptCode' : queryParams.arriveDeptCode,//到达接驳点编码
						'connectionBillVo.queryConnectionBillConditionDto.vehicleNo' : queryParams.vehicleNo,//车牌号
						'connectionBillVo.queryConnectionBillConditionDto.handOverType' : queryParams.handOverType,//交接类型
						'connectionBillVo.queryConnectionBillConditionDto.beginHandOverTime':Ext.Date.parse(queryParams.beginHandOverTime,'Y-m-d H:i:s'),//开始交接时间
						'connectionBillVo.queryConnectionBillConditionDto.endHandOverTime' : Ext.Date.parse(queryParams.endHandOverTime,'Y-m-d H:i:s'),//结束交接时间
						'connectionBillVo.queryConnectionBillConditionDto.isArrived' : queryParams.isArrived//是否已到达
					},
					isUpload: true,
					success:function(response){
						
					},
					exception : function(response) {
						top.Ext.MessageBox.alert('导出失败',result.message);
					}
				});
			
				
			 }
			
		},{
			xtype : 'button',
			text :'打印 ',
			disabled : !load.connectionbillquery.isPermission('button/connectionBillPrintButton'),
			hidden : !load.connectionbillquery.isPermission('button/connectionBillPrintButton'),
			handler : function(){
				var records = me.getSelectionModel().getSelection();
				if(records.length == 0){
					Ext.MessageBox.show({
						title:"提示",
						msg:"请先选择您要操作的行!"
					});
					return;
				}
				//如果选择的交接单属于多个不同的车牌则不能打印
				var vehicleNo = '',
					isdiff = false,
					connectionBillNos = new Array();
				for(var i = 0; i < records.length; i++) {
					vehicleNo = records[i].get('vehicleNo');
					break;
				}
				for(var i = 0; i < records.length; i++) {
					var no = records[i].get('vehicleNo');
					var connectionBillNo = records[i].get('connectionBillNo');
					connectionBillNos.push(connectionBillNo);
					if(vehicleNo != no) {
						//所选择的车牌号不相同
						isdiff = true;
						break;
					}
				}
				if(isdiff) {
					Ext.MessageBox.show({
						title:"提示",
						msg:"选择的交接单属于多个车牌, 不能打印!"
					});
					return;
				}
				Ext.create('Foss.load.connectionbillquery.PrintWindow', {
					vehicleNo : vehicleNo,
					connectionBillNos : connectionBillNos,
					grid : me
				}).show();
			
				
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
	load.connectionbillquery.pagingBar = me.bbar;
	me.callParent([cfg]);
},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '编辑',
			iconCls : 'deppon_icons_edit',
			handler : function(grid, rowIndex, colIndex) {
				var rec = grid.store.getAt(rowIndex),
				connectionBillNo = rec.get('connectionBillNo');
				load.connectionbillquery.showConnectionBillModify(connectionBillNo);
			}
		}
		,{
			tooltip : '删除',
			iconCls : 'deppon_icons_delete',
			handler : function(grid, rowIndex, colIndex) {

				var record = grid.store.getAt(rowIndex);
				var connectionBillNo = record.get('connectionBillNo');
				
				Ext.MessageBox.confirm('提示',
				'确定要作废此交接单？',
				function(btn){
					if(btn == 'yes'){
						var myMask = new Ext.LoadMask(grid, {
							msg : '正在作废交接单，请稍候......'
						});
						myMask.show();
						Ext.Ajax.request({
							url : load.realPath('cancelConnectionBillbyNo.action'),
							params:{'connectionBillVo.connectionBillNo': connectionBillNo},
							timeout : 1800000,
							success:function(response){
								grid.store.removeAt(rowIndex);
								var result = Ext.decode(response.responseText);
								Ext.MessageBox.alert('提示',
								'操作成功，交接单【' + connectionBillNo + '】已作废');
								myMask.hide();
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert('提示',
			    				result.message);
			    				myMask.hide();
			    			}
						});
					}
				});
		
			} 
		}],
	renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			
			if( record.get('statu') == 20
					&&load.connectionbillquery.orgCode==record.get('departDeptCode')){
				this.items[0].iconCls = 'deppon_icons_edit';
				this.items[1].iconCls = 'deppon_icons_delete';
			}else{
				this.items[0].iconCls = '';
				this.items[1].iconCls = '';
			}
			
		}
	}, {
		dataIndex : 'connectionBillNo',
		align : 'center',
		header:'交接单号',
		width : 80,
		xtype : 'ellipsiscolumn',
		renderer : function(value){
			if(value!=null){
				return '<a href="javascript:load.connectionbillquery.showHandOverBillDetail('+"'" + value + "'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	},{
		dataIndex : 'statu',
		align : 'center',
		header:'状态',
		width : 80,
		xtype : 'ellipsiscolumn',
		renderer:function(value){
			if(value!=null&&value!=''){
				if(value==20){
					return '已交接';
				}else if(value==40){
					return '已到达';
				}else if(value==50){
					return '已入库';
				}
			}
			
		}
	},{
		dataIndex : 'handOverTime',
		align : 'center',
		header:'交接日期',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'vehicleNo',
		align : 'center',
		header:'车牌号',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'departDeptName',
		align : 'center',
		header:'出发部门',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'departDeptCode',
		align : 'center',
		header:'出发部门编码',
		width : 80,
		hidden:true,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'arriveDeptName',
		align : 'center',
		header:'到达接驳点',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'arriveTime',
		align : 'center',
		header:'到达时间',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		header:'总票数',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		header:'总件数',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'volumeTotal',
		align : 'center',
		header:'总体积',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'weightTotal',
		align : 'center',
		header:'总重量',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'createUserName',
		align : 'center',
		header:'制单人',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'driverName',
		align : 'center',
		header:'司机',
		width : 80,
		xtype : 'ellipsiscolumn'
	}]
});

/***************************************** 接驳交接单（到达）查询列表grid*********************************/
Ext.define('Foss.load.connectionbillquery.queryArrivalConnectionBillGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '交接单列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '暂无数据',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config)
		me.store = Ext.create('Foss.load.connectionbillquery.QueryArrivalConnectionBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
	 load1 = me.bbar;
	me.callParent([cfg]);
},columns :[{
		dataIndex : 'connectionBillNo',
		align : 'center',
		header:'交接单号',
		width : 80,
		xtype : 'ellipsiscolumn',
		renderer : function(value){
			if(value!=null){
				return '<a href="javascript:load.connectionbillquery.showHandOverBillDetails('+"'" + value + "'"+')">' + value + '</a>';
		}else{
				return null;
				}
		}
	},{
		dataIndex : 'statu',
		align : 'center',
		header:'状态',
		width : 80,
		xtype : 'ellipsiscolumn',
		renderer:function(value){
			if(value!=null&&value!=''){
				if(value==0){
					return '未到';
				}else if(value==1){
					return '已到';
				}
			}
			
		}
	},{
		dataIndex : 'departTime',
		align : 'center',
		header:'出发时间',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'vehicleNo',
		align : 'center',
		header:'车牌号',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'arriveTime',
		align : 'center',
		header:'到达时间',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'waybillQtyTotal',
		align : 'center',
		header:'总票数',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'goodsQtyTotal',
		align : 'center',
		header:'总件数',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'volumeTotal',
		align : 'center',
		header:'总体积',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'weightTotal',
		align : 'center',
		header:'总重量',
		width : 80,
		xtype : 'ellipsiscolumn'
	},{
		dataIndex : 'driverName',
		align : 'center',
		header:'司机',
		width : 80,
		xtype : 'ellipsiscolumn'
	}]
});
/************************************************************js入口********************************************************/
//接驳交接单查询的grid数据
load.connectionbillquery.queryConnectionBillGrid = Ext.create('Foss.load.connectionbillquery.queryConnectionBillGrid');
//接驳交接单管理界面查询条件
load.connectionbillquery.ConnectionBillQueryForm = Ext.create('Foss.load.connectionbillquery.ConnectionBillQueryForm');
//接驳交接单到达本部门查询条件
load.connectionbillquery.ArrivalConnectionBillQueryForm = Ext.create('Foss.load.connectionbillquery.ArrivalConnectionBillQueryForm');
//接驳交接单到达本部门grid数据
load.connectionbillquery.queryArrivalConnectionBillGrid = Ext.create('Foss.load.connectionbillquery.queryArrivalConnectionBillGrid');
//js入口
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel', {
		id : 'T_load-connectionbillqueryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
	    items :[{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
				items :[{title : "本部门出发",
						tabConfig : {
							width : 120
						},
						itemId : 'departTab',
						items : [load.connectionbillquery.ConnectionBillQueryForm, load.connectionbillquery.queryConnectionBillGrid]
					}, {
						title :"到达本部门",
						tabConfig : {
							width : 120
						},
						itemId : 'arrivalTab',
						items : [load.connectionbillquery.ArrivalConnectionBillQueryForm, load.connectionbillquery.queryArrivalConnectionBillGrid]
					}]
				}],
				renderTo : 'T_load-connectionbillqueryindex-body'	
			}
		
	);
	
	/*if(load.connectionbillquery.transferCenter!='Y'){
		load.connectionbillquery.ConnectionBillQueryForm.getForm().findField('departDeptCode').setValue('');
	}else{
		load.connectionbillquery.ConnectionBillQueryForm.getForm().findField('departDeptCode').setCombValue(load.connectionbillquery.orgName,load.connectionbillquery.orgCode);
	}*/
	if(load.connectionbillquery.transferCenter!='Y'){
		load.connectionbillquery.ArrivalConnectionBillQueryForm.getForm().findField('arriveDeptCode').setValue('');
	}else{
		load.connectionbillquery.ArrivalConnectionBillQueryForm.getForm().findField('arriveDeptCode').setCombValue(load.connectionbillquery.orgName,load.connectionbillquery.orgCode);
	}
});