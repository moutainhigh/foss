//定义方法，生产查询条件"托盘扫描时间"的起始时间和结束时间
load.trayscanquery.getTrayScanTime4QueryForm=function(isBegin){
	var nowDate = new Date();
	if(isBegin){
		nowDate.setHours(0);
		nowDate.setMinutes(0);
		nowDate.setSeconds(0);
	}else{
		nowDate.setHours(23);
		nowDate.setMinutes(59);
		nowDate.setSeconds(59);
	}
	return nowDate;
}
/**-----------------------------------------------queryForm-----------------------------------------------------*/
//定义托盘扫描查询条件form 零担
Ext.define('Foss.load.trayscanquery.QueryForm', {

	extend : 'Ext.form.Panel',
	title : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.title'),//查询条件
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [ {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.billNoLabel'),//单号
		name : 'billNo'
	},{
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.forkliftDriverLabel'),//叉车司机
		name : 'forkLiftDriverName',
		xtype:'commonemployeeselector'
		
	}, {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.forkliftDept'),//叉车部门
		name : 'forkLiftDept',
		xtype:'dynamicorgcombselector'
		
	}, {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.bindingName'),//绑定人
		name : 'bindingName',
		xtype:'commonemployeeselector'
		
	}, {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.bindingDept'),//绑定人部门
		name : 'bindingDept',
		xtype:'dynamicorgcombselector'
		
	},{
		fieldLabel : '状态',//load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.trayScanStatus'),//状态
		name :'trayScanStatus',
		xtype: 'combobox',
		queryMode : 'local',
		displayField : 'value',
		valueField : 'key',
		columnWidth : 0.25,
		value:'SCANNED',
		editable : false,
		store :Ext.create('Ext.data.Store',{
			fields :['key','value'],
			data : [{
				'key':'SCANNED','value':'已扫描'
			},{
				'key':'UNSCAN','value':'未扫描'
			},{
			    'key':'HANDSCAN','value':'手动拉车'
			}]
		}),
		
		listeners:{
			'change' :function(cmp, newValue,oldValue, eOpts ){
				var form = this.up('form').getForm();
				if(newValue=='SCANNED'){
					//如果是已扫描状态 则显示扫描时间，隐藏创建时间
					Ext.getCmp('Foss_stock_QueryForm_TrayScanTime_ID').setVisible(true);
					Ext.getCmp('Foss_stock_QueryForm_TrayCeateTime_ID').setVisible(false);
				}else if(newValue=='UNSCAN'){
					//如果是已扫描状态 则显示创建时间，隐藏扫描时间
					Ext.getCmp('Foss_stock_QueryForm_TrayScanTime_ID').setVisible(false);
					Ext.getCmp('Foss_stock_QueryForm_TrayCeateTime_ID').setVisible(true);
				}
			}
		}
	}, {
		xtype : 'rangeDateField',
		fieldLabel :load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.trayScanTaskTimeLabel'),//任务建立时间
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_stock_QueryForm_TrayScanTime_ID',
		id:'Foss_stock_QueryForm_TrayScanTime_ID',
		dateType: 'datetimefield_date97',
		
		dateRange : 7,
		fromName : 'beginTrayScanTime',
		fromValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endTrayScanTime',
		allowBlank : false,
		//format : 'Y-m-d H:i:s',
		emptyText:'请选择时间',
		disallowBlank : true	
	},{
		xtype : 'rangeDateField',
		fieldLabel :'绑定托盘任务时间' ,// load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.trayScanTaskTimeLabel'),//任务建立时间
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_stock_QueryForm_TrayCeateTime_ID',
		id:'Foss_stock_QueryForm_TrayCeateTime_ID',
		dateType: 'datetimefield_date97',
		hidden : true,
		dateRange : 7,
		fromName : 'beginTrayCreateTime',
		fromValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endTrayCreateTime',
		allowBlank : false,
		//format : 'Y-m-d H:i:s',
		emptyText:'请选择时间',
		disallowBlank : true	
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text : load.trayscanquery.i18n('foss.load.trayscanqury.queryForm.resetButtonText'),//'重置'
			handler : function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginTrayScanTime').setValue(Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(true),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTrayScanTime').setValue(Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(false),'Y-m-d H:i:s'));	
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!load.trayscanquery.isPermission('load/querytrayscanButton'),
			hidden:!load.trayscanquery.isPermission('load/querytrayscanButton'),
			text : load.trayscanquery.i18n('foss.load.trayscanquery.queryButtonText'),//'查询'
			handler : function(){
				var form = this.up('form').getForm();
				var queryParams = load.trayscanquery.TrayScanQueryForm.getForm().getValues();
//				if(queryParams.trayScanStatus=='SCANNED'&& (queryParams.beginTrayScanTime==''|| queryParams.endTrayScanTime=='') ){
//					Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
//					return;
//				}else if(queryParams.trayScanStatus=='UNSCAN'&&( queryParams.beginTrayCreateTime==''|| queryParams.endTrayCreateTime=='')){
//					Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
//					return;
//				}
				var timeReg = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
				if(queryParams.trayScanStatus=='SCANNED'){
					var bsTime=queryParams.beginTrayScanTime;
					if(!timeReg.test(bsTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					var esTime=queryParams.endTrayScanTime;
					if(!timeReg.test(esTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					
				}else 
				if(queryParams.trayScanStatus=='UNSCAN'){
					var bcTime=queryParams.beginTrayCreateTime;
					if(!timeReg.test(bcTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
					var ecTime=queryParams.endTrayCreateTime;
					if(!timeReg.test(ecTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
				}
				
//				if(!form.isValid()) {
//					return;
//				};
				load.trayscanquery.pagingBar.moveFirst();
				params = {
					'trayScanVo.queryTrayScanConditionDto.bindingDept' :queryParams.bindingDept,
					'trayScanVo.queryTrayScanConditionDto.bindingName' :queryParams.bindingName,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDriverName' :queryParams.forkLiftDriverName,
					'trayScanVo.queryTrayScanConditionDto.billNo' : queryParams.billNo,
					'trayScanVo.queryTrayScanConditionDto.beginTrayScanTime' : queryParams.beginTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayScanTime' : queryParams.endTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.beginTrayCreateTime' : queryParams.beginTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayCreateTime' : queryParams.endTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.trayScanStatus' : queryParams.trayScanStatus
				}
				Ext.Ajax.request({
					url : load.realPath('queryTrayScanSummary.action'),
					params : params,
					success : function(response){
						var result = Ext.decode(response.responseText),
						  vo = result.trayScanVo;
						var dto = vo.trayScanList[0];
						if(dto==null||dto==''){
							//load.trayscanquery.queryTrayScanDetailGrid.dockedItems.items[2].items.items[0].setValue();
							Ext.getCmp('Foss_load_trayscanquery_ID').setValue(0);
						}else{
							Ext.getCmp('Foss_load_trayscanquery_ID').setValue(dto.totalCount);
						}
					}
					
				})
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义托盘扫描查询条件form 快递
Ext.define('Foss.load.trayscanquery.QueryFormExpress', {

	extend : 'Ext.form.Panel',
	title : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.title'),//查询条件
	frame : true,
	collapsible : true,//允许展开收缩
	animCollapse : true,//显示动画效果
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 4,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [ {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.billNoLabel'),//单号
		name : 'billNo'
	},{
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.forkliftDriverLabel'),//叉车司机
		name : 'forkLiftDriverName',
		xtype:'commonemployeeselector'
		
	}, {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.forkliftDept'),//叉车部门
		name : 'forkLiftDept',
		xtype:'dynamicorgcombselector'
		
	}, {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.bindingName'),//绑定人
		name : 'bindingName',
		xtype:'commonemployeeselector'
		
	}, {
		fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.bindingDept'),//绑定人部门
		name : 'bindingDept',
		xtype:'dynamicorgcombselector'
		
	},{
		fieldLabel : '状态',//load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.trayScanStatus'),//状态
		name :'trayScanStatus',
		xtype: 'combobox',
		queryMode : 'local',
		displayField : 'value',
		valueField : 'key',
		columnWidth : 0.25,
		value:'SCANNED',
		editable : false,
		store :Ext.create('Ext.data.Store',{
			fields :['key','value'],
			data : [{
				'key':'SCANNED','value':'已扫描'
			},{
				'key':'UNSCAN','value':'未扫描'
			},{
			    'key':'HANDSCAN','value':'手动拉车'
			}]
		}),
		
		listeners:{
			'change' :function(cmp, newValue,oldValue, eOpts ){
				var form = this.up('form').getForm();
				if(newValue=='SCANNED'){
					//如果是已扫描状态 则显示扫描时间，隐藏创建时间
					Ext.getCmp('Foss_stock_QueryForm_TrayScanTime_ID_Express').setVisible(true);
					Ext.getCmp('Foss_stock_QueryForm_TrayCeateTime_ID_Express').setVisible(false);
				}else if(newValue=='UNSCAN'||newValue=="HANDSCAN"){
					//如果是未扫描状态或手动拉车 则显示创建时间，隐藏扫描时间
					Ext.getCmp('Foss_stock_QueryForm_TrayScanTime_ID_Express').setVisible(false);
					Ext.getCmp('Foss_stock_QueryForm_TrayCeateTime_ID_Express').setVisible(true);
				}
			}
		}
	}, {
		xtype : 'rangeDateField',
		fieldLabel :load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.trayScanTaskTimeLabel'),//任务建立时间
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_stock_QueryForm_TrayScanTime_ID_Express',
		id:'Foss_stock_QueryForm_TrayScanTime_ID_Express',
		dateType: 'datetimefield_date97',
		
		dateRange : 7,
		fromName : 'beginTrayScanTime',
		fromValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endTrayScanTime',
		allowBlank : false,
		//format : 'Y-m-d H:i:s',
		emptyText:'请选择时间',
		disallowBlank : true	
	},{
		xtype : 'rangeDateField',
		fieldLabel :'绑定托盘任务时间' ,// load.trayscanquery.i18n('foss.load.trayscanquery.queryForm.trayScanTaskTimeLabel'),//任务建立时间
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标 //识的String值
		fieldId : 'Foss_stock_QueryForm_TrayCeateTime_ID_Express',
		id:'Foss_stock_QueryForm_TrayCeateTime_ID_Express',
		dateType: 'datetimefield_date97',
		hidden : true,
		dateRange : 7,
		fromName : 'beginTrayCreateTime',
		fromValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(true), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(false), 'Y-m-d H:i:s'),
		toName : 'endTrayCreateTime',
		allowBlank : false,
		//format : 'Y-m-d H:i:s',
		emptyText:'请选择时间',
		disallowBlank : true	
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [{
			xtype : 'button',
			columnWidth:.08,
			text : load.trayscanquery.i18n('foss.load.trayscanqury.queryForm.resetButtonText'),//'重置'
			handler : function(){
				this.up('form').getForm().reset();
				this.up('form').getForm().findField('beginTrayScanTime').setValue(Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(true),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTrayScanTime').setValue(Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(false),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('beginTrayCreateTime').setValue(Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(true),'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endTrayCreateTime').setValue(Ext.Date.format(load.trayscanquery.getTrayScanTime4QueryForm(false),'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!load.trayscanquery.isPermission('load/querytrayscanButton'),
			hidden:!load.trayscanquery.isPermission('load/querytrayscanButton'),
			text : load.trayscanquery.i18n('foss.load.trayscanquery.queryButtonText'),//'查询'
			handler : function(){
				var form = this.up('form').getForm();
				var queryParams = load.trayscanquery.TrayScanQueryFormExpress.getForm().getValues();
				
//				if(queryParams.trayScanStatus=='SCANNED'&& (queryParams.beginTrayScanTime==''|| queryParams.endTrayScanTime=='') ){
//					Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
//					return;
//				}else if(queryParams.trayScanStatus=='UNSCAN'&&( queryParams.beginTrayCreateTime==''|| queryParams.endTrayCreateTime=='')){
//					Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
//					return;
//				}
				var timeReg = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
				if(queryParams.trayScanStatus=='SCANNED'){
					var bsTime=queryParams.beginTrayScanTime;
					if(!timeReg.test(bsTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					var esTime=queryParams.endTrayScanTime;
					if(!timeReg.test(esTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					
				}else 
				if(queryParams.trayScanStatus=='UNSCAN'){
					var bcTime=queryParams.beginTrayScanTime;
					if(!timeReg.test(bcTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
					var ecTime=queryParams.endTrayScanTime;
					if(!timeReg.test(ecTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
				}
				
//				if(!form.isValid()) {
//					return;
//				};
				load.trayscanquery.pagingBarExpress.moveFirst();
				params = {
					'trayScanVo.queryTrayScanConditionDto.bindingDept' :queryParams.bindingDept,
					'trayScanVo.queryTrayScanConditionDto.bindingName' :queryParams.bindingName,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDriverName' :queryParams.forkLiftDriverName,
					'trayScanVo.queryTrayScanConditionDto.billNo' : queryParams.billNo,
					'trayScanVo.queryTrayScanConditionDto.beginTrayScanTime' : queryParams.beginTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayScanTime' : queryParams.endTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.beginTrayCreateTime' : queryParams.beginTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayCreateTime' : queryParams.endTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.trayScanStatus' : queryParams.trayScanStatus
				}
				Ext.Ajax.request({
					url : load.realPath('queryTrayScanSummaryExpress.action'),
					params : params,
					success : function(response){
						var result = Ext.decode(response.responseText),
						  vo = result.trayScanVo;
						var dto = vo.trayScanList[0];
						if(dto==null||dto==''){
							//load.trayscanquery.queryTrayScanDetailGrid.dockedItems.items[2].items.items[0].setValue();
							Ext.getCmp('Foss_load_trayscanquery_ID_Express').setValue(0);
						}else{
							Ext.getCmp('Foss_load_trayscanquery_ID_Express').setValue(dto.totalCount);
						}
					}
					
				})
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
/**-----------------------------------------------QueryTrayScanModel-----------------------------------------------------*/
//定义托盘扫描查询结果详细列表 零担
Ext.define('Foss.load.trayscanquery.QueryTrayScanModel',{
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'taskNo',
		type : 'string'
	}, {
		name : 'trayscanTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'forkliftDriverCode',
		type : 'string'
	}, {
		name : 'forkliftDriverName',
		type : 'string'
	}, {
		name : 'forkliftDepartment',
		type : 'string'
	}, {
		name : 'bindingCode',
		type : 'string'
	}, {
		name : 'bindingName',
		type : 'string'
	}, {
		name : 'bindingDept',
		type : 'string'
	}, {
		name : 'traytaskCreatTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'statu',
		type : 'string'
	}
	, {
		name : 'forkliftCount',
		type : 'number'
	}]
	
});

//定义托盘扫描查询结果详细列表 快递
Ext.define('Foss.load.trayscanquery.QueryTrayScanModelExpress',{
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'taskNo',
		type : 'string'
	}, {
		name : 'trayscanTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'forkliftDriverCode',
		type : 'string'
	}, {
		name : 'forkliftDriverName',
		type : 'string'
	}, {
		name : 'forkliftDepartment',
		type : 'string'
	}, {
		name : 'bindingCode',
		type : 'string'
	}, {
		name : 'bindingName',
		type : 'string'
	}, {
		name : 'bindingDept',
		type : 'string'
	}, {
		name : 'traytaskCreatTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'statu',
		type : 'string'
	}, {
		name : 'forklifrCount',
		type : 'number'
	}, {
		name : 'waybillNumber',
		type : 'string'
	}, {
		name : 'destinationStation',
		type : 'string'
	}]
	
});
/**-----------------------------------------------QueryTrayScanStore-----------------------------------------------------*/
//定义托盘扫描列表store 零担
Ext.define('Foss.load.trayscanquery.QueryTrayScanStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	//绑定托盘扫描模型
	model : 'Foss.load.trayscanquery.QueryTrayScanModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryTrayScanList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'trayScanVo.trayScanList',
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
			var queryParams = load.trayscanquery.TrayScanQueryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'trayScanVo.queryTrayScanConditionDto.bindingDept' :queryParams.bindingDept,
					'trayScanVo.queryTrayScanConditionDto.bindingName' :queryParams.bindingName,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDriverName' :queryParams.forkLiftDriverName,
					'trayScanVo.queryTrayScanConditionDto.billNo' : queryParams.billNo,
					'trayScanVo.queryTrayScanConditionDto.beginTrayScanTime' : queryParams.beginTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayScanTime' : queryParams.endTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.beginTrayCreateTime' : queryParams.beginTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayCreateTime' : queryParams.endTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.trayScanStatus' : queryParams.trayScanStatus
				}
			});	
		}
	}
});

//定义托盘扫描列表store 快递
Ext.define('Foss.load.trayscanquery.QueryTrayScanStoreExpress', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	//绑定托盘扫描模型
	model : 'Foss.load.trayscanquery.QueryTrayScanModelExpress',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryTrayScanListExpress.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'trayScanVo.trayScanListExpress',
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
			var queryParams = load.trayscanquery.TrayScanQueryFormExpress.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'trayScanVo.queryTrayScanConditionDto.bindingDept' :queryParams.bindingDept,
					'trayScanVo.queryTrayScanConditionDto.bindingName' :queryParams.bindingName,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
					'trayScanVo.queryTrayScanConditionDto.forkLiftDriverName' :queryParams.forkLiftDriverName,
					'trayScanVo.queryTrayScanConditionDto.billNo' : queryParams.billNo,
					'trayScanVo.queryTrayScanConditionDto.beginTrayScanTime' : queryParams.beginTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayScanTime' : queryParams.endTrayScanTime,
					'trayScanVo.queryTrayScanConditionDto.beginTrayCreateTime' : queryParams.beginTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.endTrayCreateTime' : queryParams.endTrayCreateTime,
					'trayScanVo.queryTrayScanConditionDto.trayScanStatus' : queryParams.trayScanStatus
				}
			});	
		}
	}
});
/**-----------------------------------------------WaybillDetailModel-----------------------------------------------------*/
//定义运单明细model  零担
Ext.define('Foss.load.trayscanquery.WaybillDetailModel',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'packageNo',
		type : 'string'
	},{
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'serialNo',
		type : 'string'
	}, {
		name : 'destDeptName',
		type : 'string'
	}, {
		name : 'destDeptCode',
		type : 'string'
	}]
	
});

//定义运单明细model  快递
Ext.define('Foss.load.trayscanquery.WaybillDetailModelExpress',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'waybillNumber',
		type : 'string'
	}, {
		name : 'destinationStation',
		type : 'string'
	}]
	
});
/**-----------------------------------------------WaybillDetailStore-----------------------------------------------------*/
//定义运单明细store  零担
Ext.define('Foss.load.trayscanquery.WaybillDetailStore',{
	extend : 'Ext.data.Store',
	model : 'Foss.load.trayscanquery.WaybillDetailModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryWaybillByTaskNo.action'),
		reader : {
			type : 'json',
			root : 'trayScanVo.serialNoList',
			successPorperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义运单明细store  快递
Ext.define('Foss.load.trayscanquery.WaybillDetailStoreExpress',{
	extend : 'Ext.data.Store',
	model : 'Foss.load.trayscanquery.WaybillDetailModelExpress',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : load.realPath('queryWaybillByTaskNoExpress.action'),
		reader : {
			type : 'json',
			root : 'trayScanVo.trayScanListExpress',
			successPorperty : 'success'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
/**-----------------------------------------------WaybillDetailGrid-----------------------------------------------------*/
//零担
Ext.define('Foss.load.trayscanquery.WaybillDetailGrid',{
	extend : 'Ext.grid.Panel',
	columnLines : true,
	frame : true,
	//baseCls: '',
	atuoScoll : true,
	width : 400,
	store : Ext.create('Foss.load.trayscanquery.WaybillDetailStore'),
	columns : [{
		dataIndex : 'packageNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : '包号'
	},{
		dataIndex : 'waybillNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.waybillDetailGrid.waybillNo')/*'运单号'*/
	}, {
		dataIndex : 'serialNo',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.waybillDetailGrid.serialNo')/*'流水号'*/
	}, {
		dataIndex : 'destDeptName',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.waybillDetailGrid.destDeptName')/*'目的站'*/
	}],
	bindData : function(record){
		var taskNo = record.get('taskNo');
		var recordsMap = this.store.load({
			params : {
				'trayScanVo.traytaskCode' : taskNo
			}
		});
	}
	
});

//快递
Ext.define('Foss.load.trayscanquery.WaybillDetailGridExpress',{
	extend : 'Ext.grid.Panel',
	columnLines : true,
	frame : true,
	//baseCls: '',
	atuoScoll : true,
	width : 400,
	store : Ext.create('Foss.load.trayscanquery.WaybillDetailStoreExpress'),
	columns : [{
		dataIndex : 'waybillNumber',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.waybillDetailGrid.waybillNo')/*'运单号'*/
	}, {
		dataIndex : 'destinationStation',
		align : 'center',
		width : 120,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.waybillDetailGrid.destDeptName')/*'目的站'*/
	}],
	bindData : function(record){
		var taskNo = record.get('taskNo');
		var recordsMap = this.store.load({
			params : {
				'trayScanVo.traytaskCode' : taskNo
			}
		});
	}
	
});

/**-----------------------------------------------TrayScanDetailGird-----------------------------------------------------*/
//定义托盘扫描明细列表 零担
Ext.define('Foss.load.trayscanquery.TrayScanDetailGird',{
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.trayscanquery.i18n('foss.load.trayscanqury.QueryTrayScanGird.title'),/*'托盘扫描明细列表'*/
	height : 500,
	emptyText : load.trayscanquery.i18n('foss.load.trayscanqury.QueryTrayScanGird.emptyText'),/*'查询结果为空'*/
	autoScroll : true,//显示滚动条
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.trayscanquery.QueryTrayScanStore');
		me.plugins = [{
			header : true,
			ptype : 'rowexpander',
			rowsExpander : false,
			rowBodyElement : 'Foss.load.trayscanquery.WaybillDetailGrid'
		}],
		me.tbar = [{
			xtype : 'button',
			disabled:!load.trayscanquery.isPermission('load/exporttrayscanButton'),
			hidden:!load.trayscanquery.isPermission('load/exporttrayscanButton'),
			text : load.trayscanquery.i18n('foss.load.trayscanquery.ExportButtonText'),/*'导出'*/
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm',
					frm.style.display = 'none',
					document.body.appendChild(frm)
				}
				var form = load.trayscanquery.TrayScanQueryForm.getForm();
				var queryParams = form.getValues();
				var timeReg = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
				if(queryParams.trayScanStatus=='SCANNED'){
					var bsTime=queryParams.beginTrayScanTime;
					if(!timeReg.test(bsTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					var esTime=queryParams.endTrayScanTime;
					if(!timeReg.test(esTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					
				}else 
				if(queryParams.trayScanStatus=='UNSCAN'){
					var bcTime=queryParams.beginTrayCreateTime;
					if(!timeReg.test(bcTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
					var ecTime=queryParams.endTrayCreateTime;
					if(!timeReg.test(ecTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
				}
				
//				if(!form.isValid()){
//					Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
//					return;
//				}
				Ext.Ajax.request({
					url : load.realPath('exportTrayScanTaskExcel.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
							'trayScanVo.queryTrayScanConditionDto.bindingDept' :queryParams.bindingDept,
							'trayScanVo.queryTrayScanConditionDto.bindingName' :queryParams.bindingName,
							'trayScanVo.queryTrayScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
							'trayScanVo.queryTrayScanConditionDto.forkLiftDriverName' :queryParams.forkLiftDriverName,
							'trayScanVo.queryTrayScanConditionDto.billNo' : queryParams.billNo,
							'trayScanVo.queryTrayScanConditionDto.beginTrayScanTime' : queryParams.beginTrayScanTime,
							'trayScanVo.queryTrayScanConditionDto.endTrayScanTime' : queryParams.endTrayScanTime,
							'trayScanVo.queryTrayScanConditionDto.beginTrayCreateTime' : queryParams.beginTrayCreateTime,
							'trayScanVo.queryTrayScanConditionDto.endTrayCreateTime' : queryParams.endTrayCreateTime,
							'trayScanVo.queryTrayScanConditionDto.trayScanStatus' : queryParams.trayScanStatus
					},
					isUpload: true,
					success:function(response){
					
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(load.trayscanquery.i18n('foss.load.handoverbillshow.waybillGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
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
	   load.trayscanquery.pagingBar = me.bbar;
	   me.callParent([cfg]);
	},
	dockedItems : [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    id:'Foss_load_trayscanquery_toolbar',
	    name: 'Foss_load_trayscanquery_toolbar',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
			fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.totalforkliftLabel')/*'总票数'*/,
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 1/5,
			value : 0,
			id : 'Foss_load_trayscanquery_ID'
			}]
		}],
	columns : [{
		dataIndex : 'taskNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.traytaskCodeColumn')/*'托盘任务编号'*/
	}, {
		dataIndex:'statu',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '状态' ,/*'状态'*/
		renderer : function(value){
			if(value=='SCANNED'){
				return '已扫描';
			}else if(value=='UNSCAN'){
				return '未扫描';
			}else if(value=='HANDSCAN'){
			    return '手动拉车';
			}
		}
	},{
		dataIndex : 'trayscanTime',
		align : 'center',
		width : 130,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.trayscanTimeColumn')/*'叉车扫描时间'*/
	}, {
		dataIndex : 'forkliftDriverCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftDriverCodeColumn')/*'叉车司机工号'*/
	}, {
		dataIndex : 'forkliftDriverName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftDriverNameColumn')/*'叉车司机姓名'*/
	}, {
		dataIndex : 'forkliftDepartment',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftDepartmentColumn')/*'叉车司机部门'*/
	}, {
		dataIndex : 'bindingCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.bindingCodeColumn')/*'绑定人工号'*/
	}, {
		dataIndex : 'bindingName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.bindingNameColumn')/*'绑定人姓名'*/
	}, {
		dataIndex : 'bindingDept',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.bindingDeptColumn')/*'绑定人部门'*/
	}, {
		dataIndex : 'traytaskCreatTime',
		align : 'center',
		width : 130,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.traytaskCreatTimeColumn')/*'绑定托盘任务时间'*/
	}, {
		dataIndex : 'forkliftCount',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftVotesColumn')/*'叉车票数'*/
	}
//	,{
//		dataIndex : 'trayscanType',
//		align : 'center',
//		width : 90,
//		xtype : 'ellipsiscolumn',
//		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.trayscanTypeColumn')/*'类型'*/
//	}
	]
		
});

//定义托盘扫描明细列表 快递
Ext.define('Foss.load.trayscanquery.TrayScanDetailGirdExpress',{
	extend : 'Ext.grid.Panel',
	frame : true,
	title : load.trayscanquery.i18n('foss.load.trayscanqury.QueryTrayScanGird.title'),/*'托盘扫描明细列表'*/
	height : 500,
	emptyText : load.trayscanquery.i18n('foss.load.trayscanqury.QueryTrayScanGird.emptyText'),/*'查询结果为空'*/
	autoScroll : true,//显示滚动条
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.trayscanquery.QueryTrayScanStoreExpress');
		me.plugins = [{
			header : true,
			ptype : 'rowexpander',
			rowsExpander : false,
			rowBodyElement : 'Foss.load.trayscanquery.WaybillDetailGridExpress'
		}],
		me.tbar = [{
			xtype : 'button',
			disabled:!load.trayscanquery.isPermission('load/exporttrayscanButton'),
			hidden:!load.trayscanquery.isPermission('load/exporttrayscanButton'),
			text : load.trayscanquery.i18n('foss.load.trayscanquery.ExportButtonText'),/*'导出'*/
			handler : function(){
				if(!Ext.fly('downloadAttachFileForm')){
					var frm = document.createElement('form');
					frm.id = 'downloadAttachFileForm',
					frm.style.display = 'none',
					document.body.appendChild(frm)
				}
				var form = load.trayscanquery.TrayScanQueryFormExpress.getForm();
				var queryParams = form.getValues();
				var timeReg = /^(?:19|20)[0-9][0-9]-(?:(?:0[1-9])|(?:1[0-2]))-(?:(?:[0-2][1-9])|(?:[1-3][0-1])) (?:(?:[0-2][0-3])|(?:[0-1][0-9])):[0-5][0-9]:[0-5][0-9]$/;
				if(queryParams.trayScanStatus=='SCANNED'){
					var bsTime=queryParams.beginTrayScanTime;
					if(!timeReg.test(bsTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					var esTime=queryParams.endTrayScanTime;
					if(!timeReg.test(esTime)){
						Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
						return false;
					}
					
				}else 
				if(queryParams.trayScanStatus=='UNSCAN'){
					var bcTime=queryParams.beginTrayCreateTime;
					if(!timeReg.test(bcTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
					var ecTime=queryParams.endTrayCreateTime;
					if(!timeReg.test(ecTime)){
						Ext.ux.Toast.msg('提示','请输入合法的创建时间！','error', 2000);
						return false;
					}
				}
				
//				if(!form.isValid()){
//					Ext.ux.Toast.msg('提示','请输入合法的叉车扫描时间！','error', 2000);
//					return;
//				}
				Ext.Ajax.request({
					url : load.realPath('exportTrayScanTaskExcelExpress.action'),
					form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
					params : {
							'trayScanVo.queryTrayScanConditionDto.bindingDept' :queryParams.bindingDept,
							'trayScanVo.queryTrayScanConditionDto.bindingName' :queryParams.bindingName,
							'trayScanVo.queryTrayScanConditionDto.forkLiftDept' :queryParams.forkLiftDept,
							'trayScanVo.queryTrayScanConditionDto.forkLiftDriverName' :queryParams.forkLiftDriverName,
							'trayScanVo.queryTrayScanConditionDto.billNo' : queryParams.billNo,
							'trayScanVo.queryTrayScanConditionDto.beginTrayScanTime' : queryParams.beginTrayScanTime,
							'trayScanVo.queryTrayScanConditionDto.endTrayScanTime' : queryParams.endTrayScanTime,
							'trayScanVo.queryTrayScanConditionDto.beginTrayCreateTime' : queryParams.beginTrayCreateTime,
							'trayScanVo.queryTrayScanConditionDto.endTrayCreateTime' : queryParams.endTrayCreateTime,
							'trayScanVo.queryTrayScanConditionDto.trayScanStatus' : queryParams.trayScanStatus
					},
					isUpload: true,
					success:function(response){
					
					},
					exception : function(response) {
						top.Ext.MessageBox.alert(load.trayscanquery.i18n('foss.load.handoverbillshow.waybillGrid.expertFailureAlertInfo')/*'导出失败'*/,result.message);
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
	   load.trayscanquery.pagingBarExpress = me.bbar;
	   me.callParent([cfg]);
	},
	dockedItems : [{
	    xtype: 'toolbar',
	    dock: 'bottom',
	    layout : 'column',
	    id:'Foss_load_trayscanquery_toolbar_Express',
	    name: 'Foss_load_trayscanquery_toolbar',
	    defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 120
		},
		items: [{
			fieldLabel : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.totalforkliftLabel')/*'总票数'*/,
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 1/5,
			value : 0,
			id : 'Foss_load_trayscanquery_ID_Express'
			}]
		}],
	columns : [{
		dataIndex : 'taskNo',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.traytaskCodeColumn')/*'托盘任务编号'*/
	}, {
		dataIndex:'statu',
		align : 'center',
		width : 80,
		xtype : 'ellipsiscolumn',
		text : '状态' ,/*'状态'*/
		renderer : function(value){
			if(value=='SCANNED'){
				return '已扫描';
			}else if(value=='UNSCAN'){
				return '未扫描';
			}else if(value=='HANDSCAN'){
			    return '手动拉车';
			}
		}
	},{
		dataIndex : 'trayscanTime',
		align : 'center',
		width : 130,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.trayscanTimeColumn')/*'叉车扫描时间'*/
	}, {
		dataIndex : 'forkliftDriverCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftDriverCodeColumn')/*'叉车司机工号'*/
	}, {
		dataIndex : 'forkliftDriverName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftDriverNameColumn')/*'叉车司机姓名'*/
	}, {
		dataIndex : 'forkliftDepartment',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftDepartmentColumn')/*'叉车司机部门'*/
	}, {
		dataIndex : 'bindingCode',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.bindingCodeColumn')/*'绑定人工号'*/
	}, {
		dataIndex : 'bindingName',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.bindingNameColumn')/*'绑定人姓名'*/
	}, {
		dataIndex : 'bindingDept',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.bindingDeptColumn')/*'绑定人部门'*/
	}, {
		dataIndex : 'traytaskCreatTime',
		align : 'center',
		width : 130,
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.traytaskCreatTimeColumn')/*'绑定托盘任务时间'*/
	}, {
		dataIndex : 'forklifrCount',
		align : 'center',
		width : 90,
		xtype : 'ellipsiscolumn',
		text : load.trayscanquery.i18n('foss.load.trayscanquery.queryTrayScanGird.forkliftVotesColumn')/*'叉车票数'*/
	}
	]
		
});

//定义叉车工作量查询结果列表
load.trayscanquery.queryTrayScanDetailGrid = Ext.create('Foss.load.trayscanquery.TrayScanDetailGird');
load.trayscanquery.TrayScanQueryForm = Ext.create('Foss.load.trayscanquery.QueryForm');
load.trayscanquery.queryTrayScanDetailGridExpress = Ext.create('Foss.load.trayscanquery.TrayScanDetailGirdExpress');
load.trayscanquery.TrayScanQueryFormExpress = Ext.create('Foss.load.trayscanquery.QueryFormExpress');
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.create('Ext.panel.Panel',{
		id : 'T_load-trayscanqueryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			activeTab : 0,
			items:[{
				//零担
				title : load.trayscanquery.i18n('foss.load.assignLoadTask.personCount'),
				tabConfig : {
					width : 120
				},
				itemId : 'TemporaryAssignments',
				items : [load.trayscanquery.TrayScanQueryForm,load.trayscanquery.queryTrayScanDetailGrid]
		     }, {
		    	//快递
				title : load.trayscanquery.i18n('foss.load.assignLoadTask.teamCountPanel'),
				tabConfig : {
					width : 120
				},
				itemId : 'TaskAssignments',
				items : [load.trayscanquery.TrayScanQueryFormExpress,load.trayscanquery.queryTrayScanDetailGridExpress]
			 }]
		}],
		renderTo : 'T_load-trayscanqueryindex-body'
		
	});
});