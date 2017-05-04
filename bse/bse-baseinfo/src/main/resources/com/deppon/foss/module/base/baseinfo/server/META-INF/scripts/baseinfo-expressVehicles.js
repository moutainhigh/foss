baseinfo.showInfoMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.INFO,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
};
	
	//错误
	baseinfo.showErrorMes = function(message,fun) {
		var len = message.length;
		Ext.Msg.show({
		    title:'FOSS提醒您:',
		    width:110+len*15,
		    msg:'<div id="message">'+message+'</div>',
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.ERROR,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
	};
	
	//警告
	baseinfo.showWoringMessage = function(message,fun) {
		var len = message.length;
		Ext.Msg.show({
		    title:'FOSS提醒您:',
		    msg:message,
		    //cls:'mesbox',
		    width:110+len*15,
		    msg:'<div id="message">'+message+'</div>',
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
};
/*
 * 定义区域列表模型"Model"
 */
Ext.define('Foss.baseinfo.expressVehicles.AreaMode', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'areaName', //区域全称
			type : 'string'
		}, {
			name : 'areaCode', //区域Code
			type : 'string'
		}
	]
});

/*
 * 定义：区域列表"Store"交互后台
 */
Ext.define('Foss.baseinfo.expressVehicles.AreaStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.expressVehicles.AreaMode',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedTractorsList.action'),
		reader : {
			type : 'json',
			root : 'leasedVehicleVo.leasedVehicleList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_expressVehicles_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedVehicleVo.leasedVehicle.vehicleNo' : queryParams.vehicleNo,
						'leasedVehicleVo.leasedVehicle.operatingLic' : queryParams.operatingLic,
						'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_tractors' 
					}
				});
			}
		}
	}
});

/*
 * 所属区域列表
 */
Ext.define('Foss.baseinfo.expressVehicles.AreaGrid', {
	extend : 'Ext.grid.Panel',
columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_expressVehicles_AreaGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
//frame : true, // 表格对象增加一个边框
	stripeRows : true,
//columnLines : true, // 增加表格列的分割线
	//title : '所属区域列表', // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	height:200,
	width:560,

	//列表分页组件对象
//	pagingToolbar : null,
//	getPagingToolbar : function () {
//		var me = this;
//		if (Ext.isEmpty(me.pagingToolbar)) {
//			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
//					store : me.store,
//					pageSize : 10,
//					prependButtons : true,
//					defaults : {
//						margin : '0 0 15 3'
//					}
//				});
//		}
//		return me.pagingToolbar;
//	},
	// 定义表格列信息
	columns : [{
			header : '所属区域列表',
			dataIndex : 'areaName',
			name:'areaName',
			flex:1
		}
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.expressVehicles.AreaStore');
//		me.bbar = me.getPagingToolbar();
//		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.75')列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.expressVehicles.QueryFormParameterStore', {
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
	}
});

/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.expressVehicles.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.expressVehicles.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_expressVehicles_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		labelSeparator : ':',
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 190,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
		name:'vehicleLengthCode',//车型
		xtype : 'commonvehicletypeselector',
		fieldLabel : '车型',
		allowBlank : true
		},{
			xtype : 'combobox',
			name : 'active',
			hidden:true,
			fieldLabel : '车辆状态',
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.expressVehicles.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.expressVehicles.i18n('foss.baseinfo.all')
						}, {
							'valueCode' : 'Y',
							'valueName' : '可用'
						}, {
							'valueCode' : 'N',
							'valueName' : '不可用'
						}
					]
				}
			}),
			value : ''
		},{
			border : false,
			xtype : 'container',
			html : '&nbsp;'
		},{
		name : 'vehicleNo',
		fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTlicensePlateNumber'),
		allowBlank : true,
		blankText : baseinfo.expressVehicles.i18n('foss.baseinfo.LTtractorsLicensePlateCanNotEmpty'),
		maxLength : 50,
		regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
		regexText : '车牌号输入不符合规则，车牌号应由德+快递员工号组成！',
		readOnly : false,
		validateOnBlur : true,
		validateOnChange : true,
		validator : function (val) {
			var me = this;
			if (Ext.isEmpty(val)) {
				me.validatorMessage.message = me.blankText;
				return true;
			} else {
				if (!Ext.isEmpty(me.validatorMessage.validity) && me.validatorMessage.validity) {
					return me.validatorMessage.validity;
				} else {
					return me.validatorMessage.message;
				}
			}
		},
		validatorMessage : {
			validity : true,
			message : ''
		},
		listeners : {
			/**
			 * 模拟选择器：失去焦点获取外请拖头信息
			 * @param {Ext.Component} me 当前对象
			 * @param {Ext.EventObject} event 当前触发事件对象
			 */
			blur : function (me, event) {
				if (Ext.isEmpty(Ext.String.trim(me.getValue())) || me.readOnly) {
//					me.setValue(null);
					return;
				}
				Ext.Ajax.request({
					url : baseinfo.realPath('queryLeasedVehicle.action'),
					jsonData : {
						'leasedVehicleVo' : {
							'leasedVehicle' : {
								'vehicleNo' : me.getValue()
							}
						}
					},
					success : function (response) {
						//检测baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.75')是否已经存在
						var json = Ext.decode(response.responseText);
						if (Ext.isEmpty(json.leasedVehicleVo.leasedVehicle)) {
							me.validatorMessage.validity = true;
							me.validatorMessage.message = null;
							me.clearInvalid();
						} else {
							me.validatorMessage.validity = false;
							me.validatorMessage.message = baseinfo.expressVehicles.i18n('foss.baseinfo.LTtractorsLicenseAlreadyExists');
							me.markInvalid(me.validatorMessage.message);
							Ext.MessageBox.show({
								title : baseinfo.expressVehicles.i18n('foss.baseinfo.failureInformationTips'),
								msg : me.validatorMessage.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
							});
						}
					},
					exception : function (response) {
						var json = Ext.decode(response.responseText);
						me.validatorMessage.validity = false;
						me.validatorMessage.message = json.message;
						Ext.MessageBox.show({
							title : baseinfo.expressVehicles.i18n('foss.baseinfo.failureInformationTips'),
							msg : json.message,
							width : 300,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.WARNING
						});
					}
				});
			}
		}
	},{
		name:'empCode',
		xtype : 'commonExpressemployeeselector',
		fieldLabel : '所属快递员', 
		allowBlank : true,
		parentOrgCode : null
	},{
		valueField : 'areaName',
		colspan:2,//合并2列
		fieldLabel : '快递员所属行政区域',
		id:'test_ssq222222',
		allowBlank : true,
		//provinceWidth : 140,
		//cityWidth : 145,
		cityLabel : '市',
  	    cityName : 'cityName',//名称
//		provinceLabel : '省',
		provinceName:'privateName',//省名称
		areaLabel : '县',
		areaName : 'areaName',// 县名称
		//areaWidth : 145,
		labelWidth:120,
		width:1200,
		type : 'P-C-C',
		xtype : 'linkregincombselector'
	},{
			xtype : 'button',
			width: 90,
			text : baseinfo.expressVehicles.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.expressVehicles.isPermission('expressVehicles/leasedTractorsQueryButton'),
			hidden:!baseinfo.expressVehicles.isPermission('expressVehicles/leasedTractorsQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_expressVehicles_QueryForm_Id').getForm().reset();
			}
		},{
			border : false,
			xtype : 'container',
			width:650,
			html : '&nbsp;'
		}, {
			xtype : 'button',
			width: 90,
			cls : 'yellow_button',
			text : baseinfo.expressVehicles.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.expressVehicles.isPermission('expressVehicles/leasedTractorsQueryButton'),
			hidden:!baseinfo.expressVehicles.isPermission('expressVehicles/leasedTractorsQueryButton'),
			handler : function () {
				
//				if(!Ext.getCmp('Foss_baseinfo_expressVehicles_QueryForm_Id').getForm().isValid()){
//			        baseinfo.showWoringMessage("请检测数据必填项【*】是否填写完整且符合规范，有叉号的地方" +
//    				"表示输入有问题，将鼠标移动至有叉号的地方，会有详细提示！");
//			        return;
//			    }
				Ext.getCmp('Foss.baseinfo.expressVehicles.ExpressVehiclesGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')列表结果窗口 *****************************************************/
/*
 * 定义：一个baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')的数据模型"Model"
 */
Ext.define('Foss.baseinfo.expressVehicles.ExpressVehiclesModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'empDistrictId', //明细ID
			type : 'string'
		}, {
			name : 'vehicleNo', //车牌号
			type : 'string'
		}, {	    
			name : 'vehicleLengthCode', //车型code
			type : 'string'
		}, {	    
			name : 'vehicleLengthName', //车型name
			type : 'string'
		},{
			name : 'mobilePhone',//电话
			type : 'string'
		}, {
			name : 'empCode',//所属快递员name
			type : 'string'
		}, {
			name : 'empName',//所属快递员name
			type : 'string'
		}, {
			name : 'orgCode',//开单营业部code
			type : 'string'
		},{
			name : 'orgName',//开单营业部name
			type : 'string'
		},{
			name : 'notes',//开单营业部
			type : 'string'
		},{
			name : 'active',//车辆状态
			type : 'string'
		},{
			name : 'areaCode',//区域code 用于查询
			type : 'string'
		},{
			name : 'areaName',//区域Name 用于查询
			type : 'string'
		},{
			name : 'vehicleFrameNo', //车架号
			type : 'string'
		}, {
			name : 'engineNo', //发动机号
			type : 'string'
		}, {
			name : 'bridge', //单双桥
			type : 'string'
		}, {
			name : 'deadLoad', //载重
			type : 'float'
		}, {
			name : 'selfWeight', //自重
			type : 'float'
		}, {
			name : 'brand', //品牌
			type : 'string'
		}, {
			name : 'contactPhone', //车主电话
			type : 'String'
		},{
			name : 'notes', //备注
			type : 'string'
		},{
			name : 'modifyDate', //修改时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		},{
			name:'modifyUserName',//修改人
			type:'String'
		}
	]
});

/*
 * 定义：一个baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.expressVehicles.ExpressVehiclesStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.expressVehicles.ExpressVehiclesModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryExpressVehiclesList.action'),
		reader : {
			type : 'json',
			root : 'expressVehiclesVo.expressVehiclesEntityList',
			totalProperty : 'totalCount',
			successProperty : 'success'
		}
	},
	//构造函数
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_expressVehicles_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'expressVehiclesVo.expressVehiclesEntity.empCode' : queryParams.empCode,
						'expressVehiclesVo.expressVehiclesEntity.areaCode' : queryParams.areaName,
						'expressVehiclesVo.expressVehiclesEntity.CityCode' : queryParams.cityName,
						'expressVehiclesVo.expressVehiclesEntity.ProvCode' : queryParams.privateName,
						'expressVehiclesVo.expressVehiclesEntity.vehicleNo' : queryParams.vehicleNo,
						'expressVehiclesVo.expressVehiclesEntity.active' : queryParams.active,
						'expressVehiclesVo.expressVehiclesEntity.vehicleLengthCode' : queryParams.vehicleLengthCode
					}
				});
			}
		}
	}
});

/*
 * 定义：baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.expressVehicles.ExpressVehiclesDetailForm', {
	extend : 'Ext.form.Panel',
	height : 350,
	width : 1100,
	defaults : {
		margin : '5 5 5 5',
		labelWidth : 125
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			name : 'vehicleNo',
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTlicensePlateNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'bargainVehicle',
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTwhetherContractVehicle'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.expressVehicles.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.expressVehicles.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.expressVehicles.i18n('foss.baseinfo.no')
						}
					]
				}
			})
		}, {
			xtype : 'commonlineselector',
			name : 'bargainRoute',
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTcontractLine'),
			readOnly : true,
			width : 330,
			triggerAction : 'all',
			editable : false,
			queryParam : 'lineVo.lineEntity.simpleCode',
			listeners : {
				change : function (me, newValue, oldValue, eOpts) {
					if (Ext.isEmpty(newValue) && Ext.isEmpty(me.getValue())) {
						return;
					} else {
						me.store.removeAll(false);
						me.store.load({
							params : {
								start : 0,
								limit : 1,
								'lineVo.lineEntity.simpleCode' : newValue
							}
						});
					}
				}
			}
		}, {
			xtype : 'combobox',
			name : 'producingArea',
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LToriginVesting'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.expressVehicles.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.expressVehicles.i18n('foss.baseinfo.LTdomestic')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.expressVehicles.i18n('foss.baseinfo.LTimports')
						}
					]
				}
			})
		}, {
			name : 'gpsProvider',
			readOnly : true,
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTgpsSuppliers'),
			colspan : 2,
			maxlength : 1000,
			width : 635
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LToperatingCardNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LToperatingPermitEffectiveStartDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LToperatingPermitEffectiveEndDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTdrivingLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTownerPhone'),
			readOnly : true,
			columnWidth : 0.5
		},{
			xtype : 'textareafield',
			name : 'notes',
			readOnly : true,
			fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.airagencycompany.remark'),
			colspan : 3,
			maxlength : 1000,
			width : 1000
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.expressVehicles.ExpressVehiclesDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.expressVehicles.i18n('foss.baseinfo.LTouterTractorsDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请拖头详细信息
	 * @return {Object} LeasedTractorsDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.expressVehicles.ExpressVehiclesDetailForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		//绑定表格数据到表单上
		this.getInformationForm().getForm().loadRecord(record);
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.expressVehicles.ExpressVehiclesGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss.baseinfo.expressVehicles.ExpressVehiclesGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title :'快递车辆信息', // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.expressVehicles.ExpressVehiclesDetailPanel' // 行体内容
		}
	],
	addUpdateWindow : null,
	/**
	 * 获取"作废、申请可用、不可用"的窗口的对象
	 * @param {String} windowTitle 窗口的标题
	 * @param {Boolean} isNewApply true：作废，false：申请可用/不可用
	 * @param {String} actionType apply/available/unavailable
	 * @return {Ext.Component} 申请窗口的对象
	 */
	getAddUpdateWindow : function (windowTitle, actionType) {
		var me = this;
		if (this.addUpdateWindow == null) {
			this.addUpdateWindow = Ext.create('Foss.baseinfo.expressVehicles.ExpressVehiclesAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addExpressVehicles.action');
				} else {}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateExpressVehicles.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		var tempFormDOM = me.addUpdateWindow.getAddUpdateForm().getForm().reset();
		if (actionType && actionType === 'add') {
			tempFormDOM.findField('vehicleNo').setReadOnly(true);
			tempFormDOM.findField('empCode').setReadOnly(false);
		}
		if (actionType && actionType === 'upd') {
			tempFormDOM.findField('vehicleNo').setReadOnly(true);
			tempFormDOM.findField('empCode').setReadOnly(true);
		}
		tempFormDOM = null;
		return this.addUpdateWindow;
	},
	//列表分页组件对象
	pagingToolbar : null,
	getPagingToolbar : function () {
		var me = this;
		if (Ext.isEmpty(me.pagingToolbar)) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
					store : me.store,
					pageSize : 10,
					prependButtons : false,
					defaults : {
						margin : '0 0 25 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
			xtype : 'actioncolumn',
			width : 60,
			text : baseinfo.expressVehicles.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.expressVehicles.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.expressVehicles.isPermission('expressVehicles/expressVehiclesUpdateButton'),
					/**
					 * baseinfo.expressVehicles.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						//1 根据id 获取数据
						var id = record.get('id');
						var jsonData = {'expressVehiclesVo':{'expressVehiclesEntity':{'id' : id}}};
	    				//进行Ajax请求
                        Ext.Ajax.request({
							url:baseinfo.realPath('queryExpressVehiclesById.action'),
							jsonData:jsonData,
							//作废成功
							success : function(response) {
			                  	var json = Ext.decode(response.responseText);
			                  //获得修改窗口
			                  	var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
				                  
								//获取修改的Model信息
								addUpdateWindow.infoModel = new Foss.baseinfo.expressVehicles.ExpressVehiclesModel(json.expressVehiclesVo.expressVehiclesEntity);
								addUpdateWindow.areaList = json.expressVehiclesVo.expressVehiclesEntity.expressVehiclesDetailList;
//								var areaRecordName = addUpdateWindow.areaList[0].areaName.split('-');
//								var areaRecordCode = addUpdateWindow.areaList[0].areaCode.split(',');
//								addUpdateWindow.getAddUpdateForm().down('linkregincombselector').setReginValue(areaRecordName[0],areaRecordCode[0],'1');//省份
//								addUpdateWindow.getAddUpdateForm().down('linkregincombselector').setReginValue(areaRecordName[1],areaRecordCode[1],'2');//城市
//								addUpdateWindow.getAddUpdateForm().down('linkregincombselector').setReginValue(areaRecordName[2],areaRecordCode[2],'3');//区县
								addUpdateWindow.getAddUpdateForm().down('linkregincombselector').setDisabled(true);
								addUpdateWindow.show();
			                  	//保存成功列表数据重新加载
//			                  	grid.up('grid').getPagingToolbar().moveFirst();
				                },
				            //保存失败
				            exception : function(response) {
			                  	var json = Ext.decode(response.responseText);
				            }
						});
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.expressVehicles.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.expressVehicles.isPermission('expressVehicles/expressVehiclesDeleteButton'),
					/**
					 * baseinfo.expressVehicles.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						var record = grid.getStore().getAt(rowIndex);
						if(record.data.active == 'N'){
							baseinfo.showInfoMes('该记录已经作废，请选择可用状态的记录进行作废！');
							return;
						}
						Ext.MessageBox.show({
							title : baseinfo.expressVehicles.i18n('foss.baseinfo.confirmationPrompt'),
							msg : '确定要作废快递车辆',
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteExpressVehicles.action'),
										jsonData : {
											'expressVehiclesVo' : {
												'vehListIds' : new Array(record.data.id)
											}
										},
										//baseinfo.expressVehicles.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
//											grid.store.loadPage(1);
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.expressVehicles.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.expressVehicles.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.expressVehicles.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
										}
									});
								}
							}
						});
					}
				}
			]
		}, {
			header : '车型',
			dataIndex : 'vehicleLengthName',
			width : 115
		}, 
		{
			header : '车牌号',
			dataIndex : 'vehicleNo',
			width : 115
		}, {
			header : '所属区域',
			dataIndex : 'areaName',
			width : 250
		}, {
			header : '所属快递员',
			dataIndex : 'empName',
			width : 115
		}, {
			header : '手机号',
			dataIndex : 'mobilePhone',
			width : 90
		},{
			header : '车辆状态',
			dataIndex : 'active',
			hidden:true,
			width : 90,
			renderer : function (value) {
				if (value === 'Y') {
					return '可用';
				} else if (value === 'N') {
					return '不可用';
				} else {
					return '-';
				}
			}
		}, {
			header :'开单营业部',
			dataIndex : 'orgName',
			width : 90
		},{
			header : 'ID',
			dataIndex : 'id',
			hidden:true
		},{
			header:'修改人',
			dataIndex:'modifyUserName'
		},{
			header:'修改时间',
			dataIndex:'modifyDate',
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}else{
				 return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
				}
			}
		}
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.expressVehicles.ExpressVehiclesStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.dockedItems = [{
				xtype : 'toolbar',
				dock : 'top',
				layout : 'column',
				defaults : {
					margin : '0 0 5 3'
				},
				items : [{
						xtype : 'button',
						text : baseinfo.expressVehicles.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.expressVehicles.isPermission('expressVehicles/expressVehiclesAddButton'),
						hidden:!baseinfo.expressVehicles.isPermission('expressVehicles/expressVehiclesAddButton'),
						width : 80,
						//弹出baseinfo.expressVehicles.i18n('foss.baseinfo.add')的快递车辆的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.expressVehicles.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.expressVehicles.isPermission('expressVehicles/expressVehiclesDeleteButton'),
						hidden:!baseinfo.expressVehicles.isPermission('expressVehicles/expressVehiclesDeleteButton'),
						width : 80,
						/**
						 * 作废快递车辆
						 */
						handler : function(grid, rowIndex,colIndex) {
							var selectionRecord = me.getSelectionModel().getSelection();
							if(selectionRecord.length == 0){
								baseinfo.showInfoMes('请至少选择一行状态为可用的记录进行作废操作！');
							}
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									if(selectionRecord[i].data.active == 'N'){
										baseinfo.showInfoMes('选择的记录中存在状态不可用的，不能进行作废操作，只有状态为可用的记录才能进行作废操作！');
										return;
									}
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.expressVehicles.i18n('foss.baseinfo.confirmationPrompt'),
									msg : '确定要作废快递车辆',
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes' && !Ext.isEmpty(ids)) {
											//获取结果表格对象
//											var record = grid.getStore().getAt(rowIndex);
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteExpressVehicles.action'),
												jsonData : {
													'expressVehiclesVo' : {
														'vehListIds' : ids
													}
												},
												//baseinfo.expressVehicles.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
//													grid.store.loadPage(1);
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.expressVehicles.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.expressVehicles.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.expressVehicles.i18n('foss.baseinfo.failureInformationTips'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.WARNING
													});
												}
											});
										}
									}
								});
							} 
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	}
});


/*****************************************************  定义baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')申请窗口 *****************************************************/
/*
 * 定义：baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.expressVehicles.ExpressVehiclesAddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	width : 960,
	collapsible : true,
	isSearchComb : true,
	defaults : {
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
	
	items : [{
			xtype : 'fieldset',
			title : '快递车辆基本信息',
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
//				anchor : '100%',
				margin : '5 5 5 5',
				labelWidth : 80,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 2
			},
			items : [{
					name : 'vehicleNo',
					fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.LTlicensePlateNumber'),
					allowBlank : false,
					blankText : baseinfo.expressVehicles.i18n('foss.baseinfo.LTtractorsLicensePlateCanNotEmpty'),
					maxLength : 50,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
//					regexText : baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.77'),
					regexText : '选择车型为电动车时，车牌号属性控件默认值为：“德XXXXXX”。'+
						'不存在车牌号时用虚拟车牌号，虚拟车牌号命名如：“德102345“，'+
						'其规则是：以“德”开头后跟员工工号格式的虚拟车牌号且在公共选择器中能够查询到这种形式的车牌号',
					readOnly : false,
					validateOnBlur : true,
					validateOnChange : true,
					validator : function (val) {
						var me = this;
						if (Ext.isEmpty(val)) {
							me.validatorMessage.message = me.blankText;
							return true;
						} else {
							if (!Ext.isEmpty(me.validatorMessage.validity) && me.validatorMessage.validity) {
								return me.validatorMessage.validity;
							} else {
								return me.validatorMessage.message;
							}
						}
					},
					validatorMessage : {
						validity : true,
						message : ''
					},
					listeners : {
						/**
						 * 模拟选择器：失去焦点获取外请拖头信息
						 * @param {Ext.Component} me 当前对象
						 * @param {Ext.EventObject} event 当前触发事件对象
						 */
						blur : function (me, event) {
							if (Ext.isEmpty(Ext.String.trim(me.getValue())) || me.readOnly) {
//								me.setValue(null);
								return;
							}
							Ext.Ajax.request({
								url : baseinfo.realPath('queryLeasedVehicle.action'),
								jsonData : {
									'leasedVehicleVo' : {
										'leasedVehicle' : {
											'vehicleNo' : me.getValue()
										}
									}
								},
								success : function (response) {
									//检测baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.75')是否已经存在
									var json = Ext.decode(response.responseText);
									if (Ext.isEmpty(json.leasedVehicleVo.leasedVehicle)) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.expressVehicles.i18n('foss.baseinfo.LTtractorsLicenseAlreadyExists');
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.expressVehicles.i18n('foss.baseinfo.failureInformationTips'),
											msg : me.validatorMessage.message,
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									me.validatorMessage.validity = false;
									me.validatorMessage.message = json.message;
									Ext.MessageBox.show({
										title : baseinfo.expressVehicles.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
						}
					}
				},{
					name : 'vehicleLengthCode',
					xtype : 'commonvehicletypeselector',
					fieldLabel : '车型',
					allowBlank : false,
					displayField : 'vehicleLengthName',// 显示名称
					valueField : 'vehicleLengthCode',// 值
					labelWidth:100
				}
			]
		}, {
			xtype : 'fieldset',
			title : '快递车车主信息',
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
//				anchor : '100%',
				margin : '5 5 5 5',
				labelWidth : 80,
				width:235
			},
			layout : {
				type : 'table',
				columns : 3
			},

			items : [{
					name : 'mobilePhone',//电话
					fieldLabel :'手机号',
					maxLength : 50,
					regex:/(^\d{11}$)/,
				    regexText : baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfoTelphoneError'),
					allowBlank : false,
					readOnly : false
				}, {
					name : 'empCode',
					xtype : 'commonExpressemployeeselector',
					fieldLabel : '所属快递员', 
					allowBlank : false,
					displayField : 'empName',// 显示名称
					valueField : 'empCode',// 值
					parentOrgCode : null,
					listeners:{
						blur:function(field){
			        		//代理编码 唯一
		      			if(!Ext.isEmpty(field.getValue())){
		      				this.up('form').getForm().findField('vehicleNo').setValue('德'+field.getValue());
		      			}
		      		},

						change : function (me, newValue, oldValue, eOpts) {
							if (Ext.isEmpty(newValue) && Ext.isEmpty(me.getValue())) {
								return;
							} else {
								if(newValue != oldValue){
									this.up('form').items.items[1].items.items[3].getProvince().setValue(null);
									this.up('form').items.items[1].items.items[3].getCity().setValue(null);
									this.up('form').items.items[1].items.items[3].getCounty().setValue(null)
									var store =	Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id').getStore();
									store.removeAll();
								}
							}
						}
					}
				}, {
					name : 'orgCode',
					xtype : 'commonsaleDepartFilterselector' ,
					canExpressPickupToDoor : 'Z',
					canExpressDelivery : 'Z',
					//type : 'ORG',
					allowBlank : false,
					displayField : 'name',// 显示名称
					valueField : 'code',// 值
					fieldLabel : '开单营业部'//,
					
				},  {
					colspan:2,//合并2列
					height:24,
//					labelWidth : 120,
					fieldLabel : '快递员所属行政区域',
					id:'test_ssq',
//					provinceWidth : 135,
					allowBlank : true,
//					cityWidth : 130,
					cityLabel : '市',
					cityName : 'cityName',//名称
					//provinceLabel : '省',
					provinceName:'privateName',//省名称
					areaLabel : '县',
					areaName : 'areaName',// 县名称
//					areaWidth : 130,
					type : 'P-C-C',
					xtype : 'linkregincombselector',
					width:640
				},
				{
					xtype : 'button',
					width:75,
					height:24,
					text : '添加区域',
					handler : function () {
						this.up('form').down('linkregincombselector').setDisabled(false);
						var store =	Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id').getStore();
						
						var provinceName=this.up('form').getForm().findField('privateName').getRawValue();
						var cityName = this.up('form').getForm().findField('cityName').getRawValue();
						var areaName = this.up('form').getForm().findField('areaName').getRawValue();
						var areaCode = this.up('form').getForm().findField('areaName').getValue();
						if(''==areaName||''==cityName||''==provinceName){
//							baseinfo.showWoringMessage('快递员所属行政区域为必填项，至少添加一行！');
							return;
						}else{
							var rawAll =provinceName+cityName+areaName;
							var selectedExpressAreaDto = new Object();
							selectedExpressAreaDto.areaName=rawAll;
							selectedExpressAreaDto.areaCode=areaCode;
							var record2 = new Foss.baseinfo.expressVehicles.AreaMode(selectedExpressAreaDto);
							var exist = 'N';
							store.each(function(record){
								if(areaCode==record.get('areaCode')){
									exist = 'Y';
								}
							});
							if (exist=='Y'){
								baseinfo.showWoringMessage(rawAll+'已经已存在，不能重复添加！');
								return;
							}
							store.add(record2);
						}
					}
				},
				{
					xtype : 'container',//一个容器
					colspan :2,
					width : 600,
					defaults : {
				    	margin : '0 0 0 0',
				    	anchor : '100%'
				    },
				    layout: {
				        type: 'table',
				        columns: 1,
				        width : 600
				    },
				    items:[Ext.create('Foss.baseinfo.expressVehicles.AreaGrid')]
				},
				{
					xtype : 'button',
					width:75,
					height:24,
					text : '删除区域',
					handler : function () {
						var store =	Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id').getStore();
						var selections = Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id').getSelectionModel().getSelection();
						if (selections.length < 1) {
							return;
						}
						store.remove(selections);
					}
				}
			]
		}, {
			
			xtype : 'fieldset',
			title : '快递车备注信息',
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 100,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			
			items : [{
					name : 'notes',//'快递车备注信息'
					xtype : 'textareafield',
					readOnly : false,
					fieldLabel : baseinfo.expressVehicles.i18n('foss.baseinfo.airagencycompany.remark'),
					colspan : 3,
					maxLength : 325,
					width : 800
				},{
					name : 'id',
					fieldLabel : 'ID',
					readOnly : true,
					hidden : true
				},{
					name : 'empDistrictId',
					fieldLabel : 'empDistrictId',
					readOnly : true,
					hidden : true
				}
			]
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')申请的表单窗口
 */
Ext.define('Foss.baseinfo.expressVehicles.ExpressVehiclesAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : '新增/修改快递车辆信息',
	width : 1000,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	
	infoModel : null,//保存信息Model
	areaList : null,//区域
	
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.expressVehicles.ExpressVehiclesAddUpdateForm');
		}
		return this.addUpdateForm;
	},
	loadAddUpdateForm : function (record) {
		this.addUpdateFormModel = record;
		var tempForm = this.getAddUpdateForm().getForm().loadRecord(record);
	},
	addUpdateFormModel : null,
	getAddUpdateFormModel : function () {
		if (null == this.addUpdateFormModel) {
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.expressVehicles.ExpressVehiclesModel");
		}
		return this.addUpdateFormModel;
	},
	buttons : null,
	getButtons : function (index) {
		this.buttons = new Array();
		if (Ext.isEmpty(this.buttons)) {
			Ext.Array.include(this.buttons, this.items.items[1].items.items[0]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[2]);
			Ext.Array.include(this.buttons, this.items.items[1].items.items[4]);
		}
		if (arguments.length === 0) {
			return this.buttons;
		} else {
			return this.buttons[index];
		}
	},
	operationUrl : null,
	setOperationUrl : function (url) {
		this.operationUrl = url;
	},
	getWindowAction : function () {
		if (this.operationUrl.toString().indexOf('updateLeasedVehicle') != -1) {
			return 'upd';
		}
		if (this.operationUrl.toString().indexOf('addExpressVehicles') != -1) {
			return 'add';
		}
		return null;
	},
	
	
	
	
	
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getAddUpdateForm(), {
				border : false,
				xtype : 'container',
				layout : 'column',
				margin : '15 0 0 260',
				defaults : {
					margin : '0 0 30 0'
				},
				items : [{
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.expressVehicles.i18n('foss.baseinfo.cancel'),
						handler : function () {
							me.fireEvent('beforeshow', me);
							me.hide().close();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.expressVehicles.i18n('foss.baseinfo.reset'),
						handler : function () {
							if (me.getWindowAction() === 'upd') {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
							}
							if (me.getWindowAction() === 'add') {
								var form = me.getAddUpdateForm().getForm().reset();
								form.findField('bargainVehicle').setValue('N');
								form.findField('producingArea').setValue('Y');
								form.findField('gps').setValue('N');
								form = null;
							}
							me.fireEvent('beforeshow', me);
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						cls : 'yellow_button',
						text : baseinfo.expressVehicles.i18n('foss.baseinfo.save'),//保存
						plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									//刷新数据Model
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());			
									var selectedGrid = Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id');
//									var toDelRecord = selectedGrid.store.record;
									var selections = selectedGrid.store.data.items;
									if(selections.length==0){
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : '至少配置一条行政区域',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
								        return false;
									}
									
									var entityList = new Array();
									for(var i=0;i<selections.length;i++){
										entityList.push(selections[i].data.areaCode);
									}
								
									//执行数据请求
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'expressVehiclesVo' : {
												'expressVehiclesEntity' : me.getAddUpdateFormModel().data
												,'areaCodeList':entityList
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-expressVehicles_content').getExpressVehiclesGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.expressVehicles.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.INFO
											});
											addUpdateForm = null;
										},
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.expressVehicles.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.WARNING
											});
											addUpdateForm = null;
										}
									});
								} else {
									if(!addUpdateForm.findField('vehicleNo').isValid()){
										
										baseinfo.showWoringMessage('车牌号为必填项，不能为空！');
										return;
									}
									if(!addUpdateForm.findField('vehicleLengthCode').isValid()){
										
										baseinfo.showWoringMessage('车型为必填项，不能为空！');
										return;
									}
									if(!addUpdateForm.findField('mobilePhone').isValid()){
										
										baseinfo.showWoringMessage('手机号码为必填项，不能为空！');
										return;
									}
									if(!addUpdateForm.findField('empCode').isValid()){
										
										baseinfo.showWoringMessage('所属快递员为必填项，不能为空！');
										return;
									}
									if(!addUpdateForm.findField('orgCode').isValid()){
										
										baseinfo.showWoringMessage('开单营业部为必填项，不能为空！');
										return;
									}
									if(!addUpdateForm.findField('privateName').disabled == true
											|| !addUpdateForm.findField('cityName').disabled == true
											|| !addUpdateForm.findField('areaName').disabled == true){
										
										var provinceName=addUpdateForm.findField('privateName').getRawValue();
										var cityName = addUpdateForm.findField('cityName').getRawValue();
										var areaName = addUpdateForm.findField('areaName').getRawValue();
										if(''==areaName||''==cityName||''==provinceName){
											baseinfo.showWoringMessage('快递员所属行政区域为必填项，至少添加一行！');
											return;
										}
									}
									
									addUpdateForm = null;
								}
							}
						}
					}
				]
			}
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeshow : function (me, eOpts) {
			var form = me.getAddUpdateForm().getForm();
			
			if(!Ext.isEmpty(me.infoModel)){
				form.loadRecord(me.infoModel);
				form.findField('vehicleLengthCode').setCombValue(me.infoModel.get('vehicleLengthName'),me.infoModel.get('vehicleLengthCode'));
				form.findField('empCode').setCombValue(me.infoModel.get('empName'),me.infoModel.get('empCode'));
				form.findField('orgCode').setCombValue(me.infoModel.get('orgName'),me.infoModel.get('orgCode'));
			}
		    //加载前先清空列表数据
		    Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id').getStore().removeAll();
			if(!Ext.isEmpty(me.areaList)){
				//加载网点组信息列表
				Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id').getStore().loadData(me.areaList);
			}
		},

		beforehide : function (me, eOpts) {
			me.getAddUpdateForm().getForm().reset();
			Ext.getCmp('Foss_baseinfo_expressVehicles_AreaGrid_Id').getStore().removeAll();
		},
		beforeclose : function (me, eOpts) {
			me.fireEvent('beforehide', me);
			me.infoModel=null;
			me.areaList=null;
		}
	}
});

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	var queryForm = Ext.create('Foss.baseinfo.expressVehicles.QueryForm');
	var queryResult = Ext.create('Foss.baseinfo.expressVehicles.ExpressVehiclesGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-expressVehicles').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-expressVehicles_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询baseinfo.expressVehicles.i18n('foss.baseinfo.baseinfo-expressVehicles.76')结果列表结果窗口
		getExpressVehiclesGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
	}));
});
