//是否电子运单
Ext.define('Foss.load.complement.beEwaybillStore',{
	extend : 'Ext.data.Store',			
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	data : {'items' : [{
					'valueCode' : '',
					'valueName' : '全部'
				}, {
					'valueCode' : 'Y',
					'valueName' :'是'
				}, {
					'valueCode' : 'N',
					'valueName' : '否'
				}]
			}
});
// 补码查询条件form
Ext.define('Foss.load.complement.QueryForm', {
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
		fieldLabel : '运单号',
		vtype : 'waybill',
		name : 'waybillNo'
	}, {
		fieldLabel : '出发城市',
		xtype : 'commoncityselector',
		name : 'departCityCode' 
	},{
		fieldLabel : '到达城市',
		xtype : 'commoncityselector',
		name : 'arriveCityCode',
			listeners : {
				'change' : function(field, newValue, oldValue, eOpts) {
					var form = field.up('form').getForm(),
					arriveDistCode = form.findField('arriveDistCode');
					
					if (!Ext.isEmpty(newValue)) {
						arriveDistCode.getStore().un('beforeload');
						arriveDistCode.getStore().on('beforeload', function(store,operation,eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
							}
							searchParams['cityVo.parentId'] = newValue;
							Ext.apply(operation, {
								params : searchParams
							});
						});
						arriveDistCode.getStore().load();
						//arriveDistCode.expand();
					}
					else
					{
						arriveDistCode.setValue('');
					}
					
					arriveCityCode = form.findField('arriveCityCode').getValue();
					if(!Ext.isEmpty(arriveCityCode)){
						form.findField('arriveDistCode').setDisabled(false);
					}
				}
			}
			
	},{
		fieldLabel : '到达区县', //commonareaselector
		xtype : 'commonareaselector',
		name : 'arriveDistCode',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts) {
				var form = field.up('form').getForm(),
				arriveCityCode = form.findField('arriveCityCode').getValue();
				if (Ext.isEmpty(arriveCityCode)&&!Ext.isEmpty(newValue)) {
					// form.findField('arriveDistCode').setValue("");
					Ext.MessageBox.alert('提示','选择到达区县之前必须选择到达城市!');
					form.findField('arriveDistCode').setDisabled(true);
					form.findField('arriveDistCode').setValue('');
				}else{
					form.findField('arriveDistCode').setDisabled(false);
				}
			}
		}
	},{
		fieldLabel : '提货网点',
		name : 'pkpOrgCode',
		xtype : 'dynamicVirtualDepartmentcombselector',
		transCenterCodes : load.complement.transferCenterCode,
		active : 'Y',
		expressSalesDepartment : 'Y',
		type : 'ORG'
	},{
		xtype : 'rangeDateField',
		fieldLabel : '开单时间',
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标识的String值
		fieldId : 'Foss_complement_QueryForm_BillingTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 7,
		fromName : 'beginBillingTime',
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1,0,0,0), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'),
		toName : 'endBillingTime',
		allowBlank : false,
		disallowBlank : true
	},{
		fieldLabel : '目的地址',
		name : 'address'
	},{
		fieldLabel : '开单营业部',
		name : 'billingOrgCode',
		type : 'ORG',
		xtype : 'dynamicorgcombselector',
		salesDepartment : 'Y'
	},{
		xtype:'combobox',
		fieldLabel : '是否电子运单',
		name:'beEwaybill',
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		store:Ext.create('Foss.load.complement.beEwaybillStore'),
		value:''
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
				//重新初始化建包时间
				this.up('form').getForm().findField('beginBillingTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()-1,0,0,0), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endBillingTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					load.complement.pagingBar.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//269701--2015/11/05--begin
//补码退回界面查询
//补码退回查询条件form
Ext.define('Foss.load.complement.QueryBackForm', {
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
		fieldLabel : '运单号',
		vtype : 'waybill',
		name : 'waybillNo'
	}, {
		fieldLabel : '出发城市',
		xtype : 'commoncityselector',
		name : 'departCityCode' 
	},{
		fieldLabel : '到达城市',
		xtype : 'commoncityselector',
		name : 'arriveCityCode',
			listeners : {
				'change' : function(field, newValue, oldValue, eOpts) {
					var form = field.up('form').getForm(),
					arriveDistCode = form.findField('arriveDistCode');
					
					if (!Ext.isEmpty(newValue)) {
						arriveDistCode.getStore().un('beforeload');
						arriveDistCode.getStore().on('beforeload', function(store,operation,eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
							}
							searchParams['cityVo.parentId'] = newValue;
							Ext.apply(operation, {
								params : searchParams
							});
						});
						arriveDistCode.getStore().load();
						//arriveDistCode.expand();
					}
					else
					{
						arriveDistCode.setValue('');
					}
					
					arriveCityCode = form.findField('arriveCityCode').getValue();
					if(!Ext.isEmpty(arriveCityCode)){
						form.findField('arriveDistCode').setDisabled(false);
					}
				}
			}
			
	},{
		fieldLabel : '到达区县', //commonareaselector
		xtype : 'commonareaselector',
		name : 'arriveDistCode',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts) {
				var form = field.up('form').getForm(),
				arriveCityCode = form.findField('arriveCityCode').getValue();
				if (Ext.isEmpty(arriveCityCode)&&!Ext.isEmpty(newValue)) {
					// form.findField('arriveDistCode').setValue("");
					Ext.MessageBox.alert('提示','选择到达区县之前必须选择到达城市!');
					form.findField('arriveDistCode').setDisabled(true);
					form.findField('arriveDistCode').setValue('');
				}else{
					form.findField('arriveDistCode').setDisabled(false);
				}
			}
		}
	},{
		fieldLabel : '提货网点',
		name : 'pkpOrgCode',
		xtype : 'dynamicVirtualDepartmentcombselector',
		transCenterCodes : load.complement.transferCenterCode,
		active : 'Y',
		expressSalesDepartment : 'N',
		type : 'ORG'
	},{
		xtype : 'rangeDateField',
		fieldLabel : '开单时间',
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标识的String值
		fieldId : 'Foss_complement_QueryBackForm_BillingTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 30,//查询时间最大可以查询30天
		fromName : 'beginBillingTime',
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),(new Date().getDate()-15),0,0,0), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'),
		toName : 'endBillingTime',
		allowBlank : false,
		disallowBlank : true
	},{
		fieldLabel : '目的地址',
		name : 'address'
	},{
		fieldLabel : '开单营业部',
		name : 'billingOrgCode',
		type : 'ORG',
		xtype : 'dynamicorgcombselector',
		salesDepartment : 'Y'
	},{
		xtype : 'rangeDateField',
		fieldLabel : '退回时间',
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标识的String值
		fieldId : 'Foss_complement_QueryBackForm_ReturnTime_ID',
		dateType: 'datetimefield_date97',
		//dateType: 'datefield',
		dateRange : 15,//查询时间最大可以查询30天
		fromName : 'beginReturnTime',
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),(new Date().getDate()-6),0,0,0), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'),
		toName : 'endReturnTime',
		allowBlank : false,
		disallowBlank : true
	},{
		xtype:'combobox',
		fieldLabel : '是否电子运单',
		name:'beEwaybill',
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		store:Ext.create('Foss.load.complement.beEwaybillStore'),
		value:''
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
				//重新初始化建包时间
				this.up('form').getForm().findField('beginBillingTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),(new Date().getDate()-15),0,0,0), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endBillingTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('beginReturnTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),(new Date().getDate()-6),0,0,0), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endReturnTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm();
				if(form.isValid()){
					load.complement.back.moveFirst();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});
//269701--2015/11/05--end

//定义查询包结果列表Model
Ext.define('Foss.load.complement.QueryComplementModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'beDone',
		type : 'string'
	}, {
		name : 'departCityCode', 
		type : 'string'
	}, {
		name : 'departCityName',  
		type : 'string'
	}, {
		name : 'arriveCityCode', 
		type : 'string'
	}, {
		name : 'arriveCityName',  
		type : 'string'
	}, {
		name : 'arriveDistCode',
		type : 'string'
	}, {
		name : 'arriveDistName',
		type : 'string'
	}, {
		name : 'arriveProName',
		type : 'string'
	}, {
		name : 'address',
		type : 'string'
	}/*, {
		name : 'historyComplementName',
		type : 'string'
	}*/, {
		name : 'pkpOrgCode',
		type : 'string'
	}, {
		name : 'pkpOrgName',
		type : 'string'
	}, {
		name : 'billingTime',
		type : 'date',
		convert: dateConvert
	}, {
		name : 'billingOrgCode',
		type : 'string'
	}, {
		name : 'billingOrgName',
		type : 'string'
	}, {
		name : 'consigneeName',
		type : 'string'
	}, {
		name : 'consigneeTel',
		type : 'string'
	},{
		name : 'returntype',
		type : 'string'
	},{
		name : 'pkpMethod',
		type : 'string'
	},{
		name : 'beEwaybill',
		type : 'string'
	}]
});

//定义查询包结果列表Model
Ext.define('Foss.load.complement.QueryComplementReturnModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'beDone',
		type : 'string'
	}, {
		name : 'departCityCode', 
		type : 'string'
	}, {
		name : 'departCityName',  
		type : 'string'
	}, {
		name : 'arriveCityCode', 
		type : 'string'
	}, {
		name : 'arriveCityName',  
		type : 'string'
	}, {
		name : 'arriveDistCode',
		type : 'string'
	}, {
		name : 'arriveDistName',
		type : 'string'
	}, {
		name : 'arriveProName',
		type : 'string'
	}, {
		name : 'address',
		type : 'string'
	}/*, {
		name : 'historyComplementName',
		type : 'string'
	}*/, {
		name : 'pkpOrgCode',
		type : 'string'
	}, {
		name : 'pkpOrgName',
		type : 'string'
	}, {
		name : 'billingTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'returnTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'billingOrgCode',
		type : 'string'
	}, {
		name : 'billingOrgName',
		type : 'string'
	}, {
		name : 'consigneeName',
		type : 'string'
	}, {
		name : 'consigneeTel',
		type : 'string'
	},{
		name : 'returntype',
		type : 'string'
	},{
		name : 'pkpMethod',
		type : 'string'
	},{
		name : 'beEwaybill',
		type : 'string'
	}]
});

//定义补码查询包列表store
Ext.define('Foss.load.complement.QueryComplementStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.complement.QueryComplementModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryComplementList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'complementVo.complementList',
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
			var queryParams = load.complement.queryForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'complementVo.queryComplementDto.waybillNo' : queryParams.waybillNo,
					'complementVo.queryComplementDto.departCityCode' :queryParams.departCityCode, 
					'complementVo.queryComplementDto.arriveCityCode' :queryParams.arriveCityCode, 
					'complementVo.queryComplementDto.arriveDistCode' :queryParams.arriveDistCode,
					'complementVo.queryComplementDto.pkpOrgCode' : queryParams.pkpOrgCode,
					'complementVo.queryComplementDto.billingOrgCode' : queryParams.billingOrgCode,
					'complementVo.queryComplementDto.beginBillingTime' : queryParams.beginBillingTime,
					'complementVo.queryComplementDto.endBillingTime' : queryParams.endBillingTime,
					'complementVo.queryComplementDto.address' : queryParams.address,
					//'complementVo.queryComplementDto.historyComplementName' : queryParams.historyComplementName,
					'complementVo.queryComplementDto.returntype' : queryParams.returntype,
					'complementVo.queryComplementDto.beEwaybill' : queryParams.beEwaybill
				}
			});	
		}
	}
});

//269701--2015/11/05--begin

//补码退回界面store
//定义补码查询包列表store
Ext.define('Foss.load.complement.QueryComplementBackStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.complement.QueryComplementReturnModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : load.realPath('queryComplementBackList.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'complementVo.complementList',
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
			var queryParams = load.complement.queryBackForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'complementVo.queryComplementDto.waybillNo' : queryParams.waybillNo,
					'complementVo.queryComplementDto.departCityCode' :queryParams.departCityCode, 
					'complementVo.queryComplementDto.arriveCityCode' :queryParams.arriveCityCode, 
					'complementVo.queryComplementDto.arriveDistCode' :queryParams.arriveDistCode,
					'complementVo.queryComplementDto.pkpOrgCode' : queryParams.pkpOrgCode,
					'complementVo.queryComplementDto.billingOrgCode' : queryParams.billingOrgCode,
					'complementVo.queryComplementDto.beginBillingTime' : queryParams.beginBillingTime,
					'complementVo.queryComplementDto.endBillingTime' : queryParams.endBillingTime,
					'complementVo.queryComplementDto.beginReturnTime' : queryParams.beginReturnTime,
					'complementVo.queryComplementDto.endReturnTime' : queryParams.endReturnTime,
					'complementVo.queryComplementDto.address' : queryParams.address,
					'complementVo.queryComplementDto.historyComplementName' : queryParams.historyComplementName,
					'complementVo.queryComplementDto.returntype' : queryParams.returntype,
					'complementVo.queryComplementDto.beEwaybill' : queryParams.beEwaybill
				}
			});	
		}
	}
});
//269701--2015/11/05--end

//失败界面查询form
Ext.define('Foss.load.complement.queryFailedForm', {
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
		fieldLabel : '补码部门',
		name : 'tfrCtr',
		xtype : 'dynamicorgcombselector',
		types : 'ORG',
		salesDepartment : 'Y',
		transferCenter : 'Y',
		allowBlank : false
	},{
		fieldLabel : '运单号',
		vtype : 'waybill',
		name : 'waybillNo'
	}, {
		fieldLabel : '提货网点',
		name : 'beforePkpOrg',
		xtype : 'dynamicVirtualDepartmentcombselector',
		transCenterCodes : load.complement.transferCenterCode,
		active : 'Y',
		type : 'ORG'
	}, {
		fieldLabel : '补码网点',
		name : 'pkpOrg',
		xtype : 'commonExpressAndOrgSelector',
		active : 'Y',
		typeParam : 'ORG,LDP'
	},{
		xtype : 'rangeDateField',
		fieldLabel : '补码时间',
		columnWidth : 1/2,
		// 类型为datetimefield_date97需要配置fieldId,可以赋予此属性任何唯一标识的String值
		fieldId : 'Foss_complement_queryFailedForm_createTime_ID',
		dateType: 'datetimefield_date97',
		dateRange : 7,
		fromName : 'beginTime',
		fromValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),(new Date().getDate()),0,0,0), 'Y-m-d H:i:s'),
		toValue : Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate(),23,59,59), 'Y-m-d H:i:s'),
		toName : 'endTime',
		allowBlank : false,
		disallowBlank : true
	}],
	buttons : ['->', {
		columnWidth : .08,
		xtype : 'button',
		cls : 'yellow_button',
		text : '查询',
		handler : function() {
			var form = this.up('form').getForm();
			if (form.isValid()) {
				load.complement.failedPagingBar.moveFirst();
			}
		}
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//失败界面model
Ext.define('Foss.load.complement.failedGridModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'waybillNo',
		type : 'string'
	},{
		name : 'beforePkpOrgCode',
		type : 'string'
	},{
		name : 'beforePkpOrgName',
		type : 'string'
	}, {
		name : 'pkpOrgCode',
		type : 'string'
	},{
		name : 'pkpOrgName',
		type : 'string'
	}, {
		name : 'empCode', 
		type : 'string'
	}, {
		name : 'empName',  
		type : 'string'
	},{
		name : 'tfrCtrCode',  
		type : 'string'
	},{
		name : 'tfrCtrName',  
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert: dateConvert
	},{
		name : 'failInfo',  
		type : 'string'
	}]
});

//失败界面store
Ext.define('Foss.load.complement.failedGridStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	model : 'Foss.load.complement.failedGridModel',
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		url : load.realPath('findAsyncComplementFailed.action'),
		reader : {
			type : 'json',
			root : 'complementVo.asyncComplementFailedList',
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
			var queryParams = load.complement.queryFailedForm.getForm().getValues();
			Ext.apply(operation, {
				params : {
					'complementVo.asyncComplementFailedQcDto.waybillNo' : queryParams.waybillNo,
					'complementVo.asyncComplementFailedQcDto.beforePkpOrgCode' : queryParams.beforePkpOrg,
					'complementVo.asyncComplementFailedQcDto.pkpOrgCode' : queryParams.pkpOrg,
					'complementVo.asyncComplementFailedQcDto.tfrCtrCode' : queryParams.tfrCtr,
					'complementVo.asyncComplementFailedQcDto.beginTime' : queryParams.beginTime,
					'complementVo.asyncComplementFailedQcDto.endTime' : queryParams.endTime
				}
			});	
		}
	}
});

//失败界面grid
Ext.define('Foss.load.complement.queryFailedResultGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '补码列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.complement.failedGridStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : '补码',
			handler : function(){
				var selectedList = me.getSelectionModel().getSelection();
				if(selectedList.length == 0){
					Ext.ux.Toast.msg('提示', '请至少勾选一个运单！', 'error', 1500);
					return;
				}
				load.complement.queryType=load.complement.QUERY_FAIL_COMPLEMENT;
				if(load.complement.complementWindow == null){
					load.complement.complementWindow = Ext.create('Foss.load.complement.ComplementWindow');
				}
				load.complement.complementWindow.show();
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		load.complement.failedPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	columns : [{
			dataIndex : 'waybillNo',
			align : 'center',
			width : 100,
			xtype : 'ellipsiscolumn',
			text : '运单号'
	},{
		dataIndex : 'beforePkpOrgName',
		align : 'center',
		width : 150,
		text : '提货网点'
	},{
		dataIndex : 'pkpOrgName', 
		align : 'center',
		width : 150,
		text : '补码网点'
	},{
		dataIndex : 'failInfo',
		align : 'center',
		width : 200,
		xtype : 'ellipsiscolumn',
		text : '失败原因'
	},{
		dataIndex : 'createTime',
		align : 'center',
		width : 136,
		text : '补码时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'empCode',
		align : 'center',
		width : 80,
		text : '员工工号'
	},{
		dataIndex : 'empName',
		align : 'center',
		width : 100,
		text : '员工姓名'
	},{
		dataIndex : 'tfrCtrName',
		align : 'center',
		width : 150,
		text : '补码部门'
	}]
});

//定义电子地图窗口
//定义弹出窗口
Ext.define('Foss.load.complement.GisMapWindow',{
	id : 'Foss_load_complement_GisMapWindow_id',
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	modal : true,
	title : '电子地图',
	height : 1000,
	width : 1200,
	html: '<iframe id=gisFrame width=100% height=100% src="' + load.complement.gisPageQuery +'"/>'
});

/*
 * 打开补码窗口，并将从complementMap.html取得的部门名称放在‘补码后提货网点中’进行显示
 */
load.complement.showComplementWindow = function(deptUnifiedCode,deptName){
	if(load.complement.complementWindow == null){
		load.complement.complementWindow = Ext.create('Foss.load.complement.ComplementWindow');
	}
	load.complement.complementWindow.show();
	load.complement.expressAndOrg = load.complement.complementWindow.items.items[0];
	load.complement.expressAndOrg.setValue(deptName);
};

//定义包查询结果列表
Ext.define('Foss.load.complement.QueryComplementGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '补码列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.complement.QueryComplementStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : '补码',
			disabled: !load.complement.isPermission('complementqueryindex/QueryComplementGridComplementButton'),
			hidden: !load.complement.isPermission('complementqueryindex/QueryComplementGridComplementButton'),
			handler : function(){
				var selectedList = me.getSelectionModel().getSelection();
				if(selectedList.length == 0){
					Ext.ux.Toast.msg('提示', '请至少勾选一个运单！', 'error', 1500);
					return;
				}
				load.complement.queryType=load.complement.QUERY_COMPLEMENT;
				if(load.complement.complementWindow == null){
					load.complement.complementWindow = Ext.create('Foss.load.complement.ComplementWindow');
				}
				load.complement.complementWindow.show();
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		load.complement.pagingBar = me.bbar;
		me.callParent([cfg]);
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '查看电子地图',
			iconCls : 'foss_icons_pkp_viewOrderlocation',
			handler : function(gridView, rowIndex, colIndex) {
				var grid = gridView.up('grid'),
					rec = grid.store.getAt(rowIndex);
				load.complement.arriveCityName = rec.get('arriveCityName'); 
				load.complement.address = rec.get('address').replace(/#/g, '')
									.replace(/\?/g, '').replace(/&/g, '');
				load.complement.arriveProName = rec.get('arriveProName');
				
				//请求gis查询网点url+参数
				var param = load.complement.gisPageQuery + 
						'&province=' + load.complement.arriveProName + 
						'&city=' + load.complement.arriveCityName + 
						'&otherAddress=' + load.complement.address + 
						'&expressDeliveryType=expressDeliver';
				
				var url = ContextPath.TFR_EXECUTION + '/scripts/load/complementMap.html?url=' + param;
				
				//打开gis查询网点界面
				load.complement.gidWindow = window.open(url,"_blank","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=1200, height=1000");
			} 
		}]
	}, {
			dataIndex : 'waybillNo',
			align : 'center',
			width : 100,
			xtype : 'ellipsiscolumn',
			text : '运单号'
	}, {
		dataIndex : 'departCityName',
		align : 'center',
		width : 80,
		text : '出发城市'
	},{
		dataIndex : 'arriveCityName', 
		align : 'center',
		width : 80,
		text : '到达城市'
	}, {
		dataIndex : 'address',
		align : 'center',
		width : 200,
		text : '目的地址'
	}/*, {
		dataIndex : 'historyComplementName',
		align : 'center',
		width : 200,
		text : '历史补码部门'
	}*/, {
		dataIndex : 'pkpOrgName',
		align : 'center',
		width : 150,
		text : '提货网点'
	}, {
		dataIndex : 'billingTime',
		align : 'center',
		width : 136,
		text : '开单时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'billingOrgName',
		align : 'center',
		width : 150,
		text : '开单营业部'
	}, {
		dataIndex : 'consigneeName',
		align : 'center',
		width : 80,
		text : '收货人姓名'
	}, {
		dataIndex : 'consigneeTel',
		align : 'center',
		width : 80,
		text : '收货人电话'
	},{
		dataIndex : 'returntype',
		align : 'center',
		width : 80,
		text : '返货类型',
		renderer : function(value){
			if(value == 'SAME_CITY'){
			return '同城转寄';
			}else if(value == 'OTHER_CITY'){
			return '非同城转寄';
			}else if(value == 'CUSTORMER_REFUSE'){
				return '客户拒收';
			}else if(value == 'CONTINUE_SENDING'){
				return '继续派送';
			}else if(value == 'DETAINED_GOODS'){
				return '滞留件';
			}else if(value == 'SEVEN_DAYS_RETURN'){
				return '7天返货';
			}else if(value == 'RETURN_FORWARD'){
				return '转寄退回';
			}
			else if(value == 'GIS_FAILURE'){
				return '';
			}
			else if(value == 'CITY_CLOSE'){
				return '';
			}
			else if(value == 'return_arrive'){
				return '';
			}else{
				return value;
			}
			}
	},{
		dataIndex : 'pkpMethod',
		align : 'center',
		width : 80,
		text : '提货方式',
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'PICKUPGOODSHIGHWAYS');
		}
	}],
	listeners : {
		'beforeselect' : function(rowModel,record,eOpts){
			var selectedList = load.complement.queryResultGrid.getSelectionModel().getSelection();
			if(selectedList.length != 0){
				var oldPkpOrgCode = selectedList[0].get('pkpOrgCode'),
					pkpOrgCode = record.get('pkpOrgCode');
				if(oldPkpOrgCode != pkpOrgCode){
					return false;
				}
			}
		}
	},
	viewConfig: {
		enableTextSelection: true,
		stripeRows : false,
		getRowClass: function(record, rowParams, rp, store) {
			var returntype = record.get('returntype');
			if(returntype == 'SAME_CITY') {
				return 'result_grid_failure_back_green';
			}else if(returntype == 'RETURN_FORWARD'){
				return 'result_grid_failure_back_red';
			}
		}
    }
});

//269701--2015/11/05--begin
//补码退回界面查询结果列表
//定义包查询结果列表
Ext.define('Foss.load.complement.back.QueryComplementBackGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '补码列表',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.complement.QueryComplementBackStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : '补码',
			disabled: !load.complement.isPermission('complementqueryindex/QueryComplementGridComplementButton'),
			hidden: !load.complement.isPermission('complementqueryindex/QueryComplementGridComplementButton'),
			handler : function(){
				var selectedList = me.getSelectionModel().getSelection();
				if(selectedList.length == 0){
					Ext.ux.Toast.msg('提示', '请至少勾选一个运单！', 'error', 1500);
					return;
				}
				load.complement.queryType=load.complement.QUERY_BACK_COMPLEMENT;
				if(load.complement.complementWindow == null){
					load.complement.complementWindow = Ext.create('Foss.load.complement.ComplementWindow');
				}
				load.complement.complementWindow.show();
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		  load.complement.back = me.bbar;
		me.callParent([cfg]);
	},
	columns : [{
		xtype : 'actioncolumn',
		width : 60,
		text : '操作',
		align : 'center',
		items : [ {
			tooltip : '查看电子地图',
			iconCls : 'foss_icons_pkp_viewOrderlocation',
			handler : function(gridView, rowIndex, colIndex) {
				var grid = gridView.up('grid'),
					rec = grid.store.getAt(rowIndex);
				load.complement.arriveCityName = rec.get('arriveCityName'); 
				load.complement.address = rec.get('address').replace(/#/g, '')
									.replace(/\?/g, '').replace(/&/g, '');
				load.complement.arriveProName = rec.get('arriveProName');
				
				//请求gis查询网点url+参数
				var param = load.complement.gisPageQuery + 
						'&province=' + load.complement.arriveProName + 
						'&city=' + load.complement.arriveCityName + 
						'&otherAddress=' + load.complement.address + 
						'&expressDeliveryType=expressDeliver';
				
				var url = ContextPath.TFR_EXECUTION + '/scripts/load/complementMap.html?url=' + param;
				
				//打开gis查询网点界面
				load.complement.gidWindow = window.open(url,"_blank","toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=yes, width=1200, height=1000");
			} 
		}]
	}, {
			dataIndex : 'waybillNo',
			align : 'center',
			width : 100,
			xtype : 'ellipsiscolumn',
			text : '运单号'
	}, {
		dataIndex : 'departCityName',
		align : 'center',
		width : 80,
		text : '出发城市'
	},{
		dataIndex : 'arriveCityName', 
		align : 'center',
		width : 80,
		text : '到达城市'
	}, {
		dataIndex : 'address',
		align : 'center',
		width : 200,
		text : '目的地址'
	}/*, {
		dataIndex : 'historyComplementName',
		align : 'center',
		width : 200,
		text : '历史补码部门'
	}*/, {
		dataIndex : 'pkpOrgName',
		align : 'center',
		width : 150,
		text : '提货网点'
	}, {
		dataIndex : 'billingTime',
		align : 'center',
		width : 136,
		text : '开单时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	},{
		dataIndex : 'returnTime',
		align : 'center',
		width : 136,
		text : '退回时间',
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s'
	}, {
		dataIndex : 'billingOrgName',
		align : 'center',
		width : 150,
		text : '开单营业部'
	}, {
		dataIndex : 'consigneeName',
		align : 'center',
		width : 80,
		text : '收货人姓名'
	}, {
		dataIndex : 'consigneeTel',
		align : 'center',
		width : 80,
		text : '收货人电话'
	},{
		dataIndex : 'returntype',
		align : 'center',
		width : 80,
		text : '返货类型',
		renderer : function(value){
			if(value == 'SAME_CITY'){
			return '同城转寄';
			}else if(value == 'OTHER_CITY'){
			return '非同城转寄';
			}else if(value == 'CUSTORMER_REFUSE'){
				return '客户拒收';
			}else if(value == 'CONTINUE_SENDING'){
				return '继续派送';
			}else if(value == 'DETAINED_GOODS'){
				return '滞留件';
			}else if(value == 'SEVEN_DAYS_RETURN'){
				return '7天返货';
			}
			else if(value == 'GIS_FAILURE'){
				return '';
			}
			else if(value == 'CITY_CLOSE'){
				return '';
			}
			else if(value == 'return_arrive'){
				return '';
			}else{
				return value;
			}
			}
	},{
		dataIndex : 'pkpMethod',
		align : 'center',
		width : 80,
		text : '提货方式',
		renderer : function(value){
			return FossDataDictionary.rendererSubmitToDisplay(value,'PICKUPGOODSHIGHWAYS');
		}
	}],
	listeners : {
		'beforeselect' : function(rowModel,record,eOpts){
			var selectedList = load.complement.queryBackResultGrid.getSelectionModel().getSelection();
			if(selectedList.length != 0){
				var oldPkpOrgCode = selectedList[0].get('pkpOrgCode'),
					pkpOrgCode = record.get('pkpOrgCode');
				if(oldPkpOrgCode != pkpOrgCode){
					return false;
				}
			}
		}
	},
	viewConfig: {
		enableTextSelection: true,
		stripeRows : false,
		getRowClass: function(record, rowParams, rp, store) {
			var returntype = record.get('returntype');
			if(returntype == 'SAME_CITY') {
				return 'result_grid_failure_back_green';
			}
		}
    }
});
//269701--2015/11/05--end

//补码查询和补码退回 补码操作调用一样的action
//定义弹出窗口
Ext.define('Foss.load.complement.ComplementWindow',{
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	modal : true,
	title : '补码',
	items : [{
		fieldLabel : '补码后提货网点',
		labelWidth : 110,
		width : 300,
		xtype : 'commonExpressAndOrgSelector',
		active : 'Y',
		typeParam : 'ORG,LDP',
		allowBlank : false
	}],
	buttons : [{
		text : '取消',
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	},{
		text : '确定',
		cls : 'yellow_button',
		handler : function(button){
			var cmp = load.complement.complementWindow.items.items[0];
			if(cmp.isValid()){
				var cmpStore = cmp.store,
					pkpOrgCode = cmp.getValue(),
					pkpOrg = cmpStore.findRecord('deptCode',pkpOrgCode,0,false,true,true),
					pkpOrgName;
				
				if(Ext.isEmpty(pkpOrg)){
					Ext.ux.Toast.msg('提示','请选择补码后提货网点！','error', 2000);
					return;
				}else{
					pkpOrgName = pkpOrg.get('deptName');
				}
				
				//请求.
				//收集运单号
				//269701--lln--2015/11/10--begin
				var selectedList;//选择运单列表
				if(load.complement.queryType==load.complement.QUERY_BACK_COMPLEMENT){
					//补码退回界面查询
					 selectedList = load.complement.queryBackResultGrid.getSelectionModel().getSelection();
				
				}else if(load.complement.queryType==load.complement.QUERY_COMPLEMENT){
					//补码界面查询
					 selectedList = load.complement.queryResultGrid.getSelectionModel().getSelection();
			    }else if(load.complement.queryType === load.complement.QUERY_FAIL_COMPLEMENT){
			    	selectedList = load.complement.queryFailedResultGrid.getSelectionModel().getSelection();
			    }
				//269701--lln--2015/11/10--end
				if(Ext.isEmpty(selectedList) || selectedList.length == 0){
						Ext.ux.Toast.msg('提示', '请至少勾选一个运单！', 'error', 1500);
						return;
				}
				
				var waybillNoList = new Array();
				if(selectedList.length == 1 && selectedList[0].get('beDone') == 'Y'){
					Ext.MessageBox.confirm('提示','该运单已补码，确定要重新补码吗？',
					function(btn){
						if(btn != 'yes'){
							return;
						}else{
							waybillNoList.push(selectedList[0].data.waybillNo);
							var data = {
								'complementVo' : {
									'waybillNoList' : waybillNoList,
									'pkpOrgCode' : pkpOrgCode,//补码部门code
									'pkpOrgName' : pkpOrgName //补码部门name
								}
							};
							load.complement.complementRequest(data,button,load.complement.queryType);
						}
					});
				}else{
					for(var i in selectedList){
						waybillNoList.push(selectedList[i].data.waybillNo);
					}
					var data = {
						'complementVo' : {
							'waybillNoList' : waybillNoList,
							'pkpOrgCode' : pkpOrgCode,
							'pkpOrgName' : pkpOrgName
						}
					};
					load.complement.complementRequest(data,button,load.complement.queryType);
				}
			}
		}
	}],
	listeners : {
		'beforeclose' : function(panel,eOpts){
			var cmp = panel.items.items[0];
			cmp.reset();
		}
	}
});

//补码请求
load.complement.complementRequest = function(jsonData,button,queryType){
	var url = load.complement.queryType === load.complement.QUERY_FAIL_COMPLEMENT 
		? load.realPath('complementFailed.action') : load.realPath('complementByHand.action');
	button.disable();
	Ext.Msg.wait('补码中，请稍候...', '提示');
	Ext.Ajax.request({
		url : url,
		jsonData : jsonData,
		timeout : 300000,
		success : function(response){
			var result = Ext.decode(response.responseText),
				resultList = result.complementVo.resultList;
			//弹出处理结果窗口
			if(load.complement.complementResultWindow == null){
				load.complement.complementResultWindow = Ext.create('Foss.load.complement.ComplementResultWindow');
			}
			load.complement.complementResultWindow.show();
			load.complement.complementResultGrid.store.loadData(resultList);
			load.complement.complementWindow.close();
			if(load.complement.queryType === load.complement.QUERY_COMPLEMENT){
				load.complement.queryResultGrid.store.load();
			}else if(load.complement.queryType === load.complement.QUERY_BACK_COMPLEMENT){
				load.complement.queryBackResultGrid.store.load();
			}else if(load.complement.queryType === load.complement.QUERY_FAIL_COMPLEMENT){
				load.complement.queryFailedResultGrid.store.load();
			}
		},
		exception : function(response){
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert('提示','补码失败，' + result.message);
		},
		failure : function(reponse){
			//do nothing
		},
		callback : function(options,success,response){
			//释放按钮
			button.enable();
			Ext.Msg.hide();
		}
	});
};

//定义补码结果Model
Ext.define('Foss.load.complement.ComplementResultModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'id',
		type : 'string'
	}, {
		name : 'waybillNo',
		type : 'string'
	}, {
		name : 'beSuccess',
		type : 'string'
	}, {
		name : 'message',
		type : 'string'
	}]
});

//定义补码结果store
Ext.define('Foss.load.complement.ComplementResultStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.load.complement.ComplementResultModel',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//定义补码结果grid
Ext.define('Foss.load.complement.ComplementResultGrid', {
	extend : 'Ext.grid.Panel',
	columnLines: true,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.load.complement.ComplementResultStore');
		me.callParent([cfg]);
	},
	columns : [{
			dataIndex : 'waybillNo',
			align : 'center',
			width : 100,
			xtype : 'ellipsiscolumn',
			text : '运单号'
	}, {
		dataIndex : 'beSuccess',
		align : 'center',
		width : 80,
		text : '补码结果',
		renderer : function(value){
			if(value === 'Y'){
				return '成功';
			}else if(value === 'N'){
				return '失败';
			}
			return value;
		}
	},{
		dataIndex : 'message',
		xtype : 'linebreakcolumn',
		width : 400,
		text : '失败原因'
	}],
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowParams, rp, store) {
			var beSuccess = record.get('beSuccess');
			if(beSuccess == 'N') {
				return 'result_grid_failure_back_red';
			}
		}
	}
});

//定义补码结果grid
load.complement.complementResultGrid = Ext.create('Foss.load.complement.ComplementResultGrid');

//定义补码结果弹出窗口
Ext.define('Foss.load.complement.ComplementResultWindow',{
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	modal : true,
	title : '补码结果',
	items : [load.complement.complementResultGrid],
	buttons : [{
		text : '确定',
		handler : function(){
			this.ownerCt.ownerCt.close();
		}
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	//定义查询结果列表和查询条件
	//补码界面查询条件
	var queryForm = Ext.create('Foss.load.complement.QueryForm');
	//补码退回界面查询条件
	var queryBackForm = Ext.create('Foss.load.complement.QueryBackForm');
	//补码界面查询结果列表&&补码退回界面查询结果列表
	var queryResultGrid = Ext.create('Foss.load.complement.QueryComplementGrid');
	//补码退回界面查询结果列表
	var queryBackResultGrid = Ext.create('Foss.load.complement.back.QueryComplementBackGrid');
	
	/**
	 * 定义全局变量
	 */
	load.complement.queryForm = queryForm;//补码界面查询
	load.complement.queryBackForm = queryBackForm;//补码退回界面查询
	load.complement.queryResultGrid = queryResultGrid;//补码界面查询GRID
	load.complement.queryBackResultGrid = queryBackResultGrid;//补码退回查询GRID
	load.complement.QUERY_COMPLEMENT='COMPLEMENT';//补码界面查询
	load.complement.QUERY_BACK_COMPLEMENT='BACK';//补码退回查询
	load.complement.QUERY_FAIL_COMPLEMENT='FAIL';
	
	load.complement.queryFailedForm = Ext.create('Foss.load.complement.queryFailedForm');
	load.complement.queryFailedResultGrid = Ext.create('Foss.load.complement.queryFailedResultGrid');
	
	var tfrCtrField = load.complement.queryFailedForm.getForm().findField('tfrCtr');
	tfrCtrField.setCombValue(load.complement.tfrCtrName, load.complement.tfrCtrCode);

	Ext.create('Ext.panel.Panel', {
		id : 'T_load-complementqueryindex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		layout : 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			activeTab : 0,
		    items :[{
						title : "补码界面",//'补码界面',
						tabConfig : {
							width : 120
						},
						itemId : 'complementTab',
						items : [queryForm,queryResultGrid]
					}, {
						title :"退回界面",// '退回界面',
						tabConfig : {
							width : 120
						},
						itemId : 'complementBackTab',
						items : [queryBackForm,queryBackResultGrid]
					}, {
						title :"失败界面",
						tabConfig : {
							width : 120
						},
						itemId : 'complementFailedTab',
						items : [load.complement.queryFailedForm, load.complement.queryFailedResultGrid]
					}]
			}],
		renderTo : 'T_load-complementqueryindex-body'
	});
});