/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.75')列表查询条件窗口 *************************************************/
baseinfo.regCompCodeLimit = {vehicleNo:/^([\u4e00-\u9fa5]|[a-z]|[A-Z]|[0-9])+$/};
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.leasedTractors.QueryFormParameterStore', {
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
Ext.define('Foss.baseinfo.leasedTractors.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.leasedTractors.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_leasedTractors_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 2
	},
	defaults : {
		labelSeparator : ':',
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 170,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTlicensePlateNumber'),
			columnWidth : .30
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingCardNumber'),
			columnWidth : .30
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdrivingLicense'),
			columnWidth : .40
		}, {
			xtype : 'combobox',
			name : 'bridge',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTsingleDoubleBridge'),
			columnWidth : 0.3,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRIGE_TYPE', 'Foss_baseinfo_leasedTractors_BridgeStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.all')
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'gps',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTloadingGps'),
			columnWidth : 0.3,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedTractors.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.all')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.no')
						}
					]
				}
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'bargainVehicle',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTwhetherContractVehicle'),
			columnWidth : 0.4,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedTractors.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.all')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.no')
						}
					]
				}
			}),
			value : ''
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.leasedTractors.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsQueryButton'),
			hidden:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedTractors_QueryForm_Id').getForm().reset();
			}
		}, {
			border : false,
			xtype : 'container',
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : baseinfo.leasedTractors.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsQueryButton'),
			hidden:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedTractors_LeasedTractorsGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')列表结果窗口 *****************************************************/
/*
 * 定义：一个baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedTractors.LeasedTractorsModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'vehicleNo', //车牌号
			type : 'string'
		}, {
			name : 'vehicleFrameNo', //车架号
			type : 'string'
		}, {
			name : 'engineNo', //发动机号
			type : 'string'
		}, {
			name : 'bridge', //单双桥
			type : 'string'
		}, {
			name : 'beginTime', //出厂日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'endTime', //报废日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'bargainVehicle', //是否合同车
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
			name : 'bargainRoute', //合同线路
			type : 'string'
		}, {
			name : 'producingArea', //产地（进口/出口）
			type : 'string'
		}, {
			name : 'gps', //是否装GPS
			type : 'string'
		}, {
			name : 'gpsProvider', //GPS供应商
			type : 'string'
		}, {
			name : 'operatingLic', //营运证号
			type : 'string'
		}, {
			name : 'beginTimeOperatingLic', //营运证有效开始日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'endTimeOperatingLic', //营运证有效结束日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'drivingLicense', //行驶证号
			type : 'string'
		}, {
			name : 'beginTimeDrivingLic', //行驶证有效开始日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'endTimeDrivingLic', //行驶证有效结束日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'insureCard', //保险卡号
			type : 'string'
		}, {
			name : 'beginTimeInsureCard', //保险卡有效开始日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'endTimeInsureCard', //保险卡有效结束日期
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'contact', //车主姓名
			type : 'String'
		}, {
			name : 'contactPhone', //车主电话
			type : 'String'
		}, {
			name : 'contactAddress', //联系地址
			type : 'String'
		}, {
			name : 'notes', //备注
			type : 'string'
		}, {
			name : 'status', //状态
			type : 'string'
		}, {
			name : 'vehicleType', //车辆类型（挂车/厢式车/拖头）
			type : 'string',
			defaultValue : 'vehicletype_tractors'
		}, {
			name : 'leasedDriverIdCard', //司机身份证号
			type : 'string'
		}, {
			name : 'leasedDriverName', //司机姓名
			type : 'string'
		}
	]
});

/*
 * 定义：一个baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedTractors.LeasedTractorsStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.leasedTractors.LeasedTractorsModel',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedTractors_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedVehicleVo.leasedVehicle.vehicleNo' : queryParams.vehicleNo,
						'leasedVehicleVo.leasedVehicle.operatingLic' : queryParams.operatingLic,
						'leasedVehicleVo.leasedVehicle.drivingLicense' : queryParams.drivingLicense,
						'leasedVehicleVo.leasedVehicle.bridge' : queryParams.bridge,
						'leasedVehicleVo.leasedVehicle.gps' : queryParams.gps,
						'leasedVehicleVo.leasedVehicle.bargainVehicle' : queryParams.bargainVehicle,
						'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_tractors' //baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')数据字典常量
					}
				});
			}
		}
	}
});

/*
 * 定义：baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.leasedTractors.LeasedTractorsDetailForm', {
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
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTlicensePlateNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleFrameNo',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTchassisNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'engineNo',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTnumberOfEngines'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'bridge',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTsingleDoubleBridge'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRIGE_TYPE', 'Foss_baseinfo_leasedTractors_BridgeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			xtype : 'datefield',
			name : 'beginTime',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdateOfManufacture'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d'
		}, {
			xtype : 'datefield',
			name : 'endTime',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdiscardDate'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d'
		}, {
			xtype : 'combobox',
			name : 'brand',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTbrand'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_leasedTractors_BridgeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'deadLoad',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.loadTons'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'selfWeight',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTweightTons'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'bargainVehicle',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTwhetherContractVehicle'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTractors.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.no')
						}
					]
				}
			})
		}, {
			xtype : 'commonlineselector',
			name : 'bargainRoute',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTcontractLine'),
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
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToriginVesting'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTractors.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdomestic')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.LTimports')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'gps',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTloadingGps'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTractors.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTractors.i18n('foss.baseinfo.no')
						}
					]
				}
			})
		}, {
			name : 'gpsProvider',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTgpsSuppliers'),
			colspan : 2,
			maxlength : 1000,
			width : 635
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingCardNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingPermitEffectiveStartDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingPermitEffectiveEndDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdrivingLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeDrivingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdrivingPermitValidStartDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeDrivingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdrivingPermitValidEndDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			name : 'insureCard',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeInsureCard',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardValidStartDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeInsureCard',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardValidEndDate'),
			columnWidth : 0.5,
			format:'Y-m-d',
			editable : false
		}, {
			name : 'contact',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTnameOfOwner'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTownerPhone'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'leasedDriverName',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.ownDriversInformation'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactAddress',
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.airagencycompany.linkAddress'),
			readOnly : true,
			colspan : 3,
			maxlength : 1000,
			width : 1000
		}, {
			xtype : 'textareafield',
			name : 'notes',
			readOnly : true,
			fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.airagencycompany.remark'),
			colspan : 3,
			maxlength : 1000,
			width : 1000
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.leasedTractors.LeasedTractorsDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.leasedTractors.i18n('foss.baseinfo.LTouterTractorsDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请拖头详细信息
	 * @return {Object} LeasedTractorsDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.leasedTractors.LeasedTractorsDetailForm');
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
Ext.define('Foss.baseinfo.leasedTractors.LeasedTractorsGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_leasedTractors_LeasedTractorsGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.leasedTractors.i18n('foss.baseinfo.LTouterTractorsInformation'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.leasedTractors.LeasedTractorsDetailPanel' // 行体内容
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
			this.addUpdateWindow = Ext.create('Foss.baseinfo.leasedTractors.LeasedTractorsAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addLeasedVehicle.action');
				} else {}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateLeasedVehicle.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		var tempFormDOM = me.addUpdateWindow.getAddUpdateForm().getForm().reset();
		if (actionType && actionType === 'add') {
			tempFormDOM.findField('vehicleNo').setReadOnly(false);
			tempFormDOM.findField('gps').setValue('N');
			tempFormDOM.findField('producingArea').setValue('Y');
			tempFormDOM.findField('bargainVehicle').setValue('N');
		}
		if (actionType && actionType === 'upd') {
			tempFormDOM.findField('gps').setValue('');
			tempFormDOM.findField('producingArea').setValue('');
			tempFormDOM.findField('bargainVehicle').setValue('');
			tempFormDOM.findField('vehicleNo').setReadOnly(true);
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
					prependButtons : true,
					defaults : {
						margin : '0 0 15 3'
					}
				});
		}
		return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
			xtype : 'actioncolumn',
			width : 60,
			text : baseinfo.leasedTractors.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.leasedTractors.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsUpdateButton'),
					/**
					 * baseinfo.leasedTractors.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
						var form = addUpdateWindow.getAddUpdateForm().getForm();
						form.findField('bargainRoute').selector(record.get('bargainRoute'));
						addUpdateWindow.loadAddUpdateForm(record);
						form.findField('leasedDriverIdCard').setCombValue(record.get('leasedDriverName'),record.get('leasedDriverIdCard'));
						addUpdateWindow.show();
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.leasedTractors.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsCancelButton'),
					/**
					 * baseinfo.leasedTractors.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.leasedTractors.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.leasedTractors.i18n('foss.baseinfo.LTvoidOutisdeTracorsMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteLeasedVehicle.action'),
										jsonData : {
											'leasedVehicleVo' : {
												'batchIds' : new Array(record.data.id)
											}
										},
										//baseinfo.leasedTractors.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedTractors.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.leasedTractors.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
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
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LTlicensePlateNumber'),
			dataIndex : 'vehicleNo',
			width : 115
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.ownDriversInformation'),
			dataIndex : 'leasedDriverName',
			width : 115
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LTchassisNumber'),
			dataIndex : 'vehicleFrameNo',
			width : 115
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LTsingleDoubleBridge'),
			dataIndex : 'bridge',
			width : 100,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'VEHICLE_BRIGE_TYPE');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.loadTons'),
			dataIndex : 'deadLoad',
			width : 90
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LTweightTons'),
			dataIndex : 'selfWeight',
			width : 90
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LTloadingGps'),
			dataIndex : 'gps',
			width : 90,
			renderer : function (value) {
				if (value === 'Y') {
					return baseinfo.leasedTractors.i18n('foss.baseinfo.yes');
				} else if (value === 'N') {
					return baseinfo.leasedTractors.i18n('foss.baseinfo.no');
				} else {
					return '-';
				}
			}
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingCardNumber'),
			dataIndex : 'operatingLic',
			width : 115
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdrivingLicense'),
			dataIndex : 'drivingLicense',
			width : 115
		}, {
			header : baseinfo.leasedTractors.i18n('foss.baseinfo.LTwhetherContractVehicle'),
			dataIndex : 'bargainVehicle',
			width : 90,
			renderer : function (value) {
				if (value === 'Y') {
					return baseinfo.leasedTractors.i18n('foss.baseinfo.yes');
				} else if (value === 'N') {
					return baseinfo.leasedTractors.i18n('foss.baseinfo.no');
				} else {
					return '-';
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
		me.store = Ext.create('Foss.baseinfo.leasedTractors.LeasedTractorsStore');
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
						text : baseinfo.leasedTractors.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsAddButton'),
						hidden:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsAddButton'),
						width : 80,
						//弹出baseinfo.leasedTractors.i18n('foss.baseinfo.add')的外请拖头的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.leasedTractors.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsCancelButton'),
						hidden:!baseinfo.leasedTractors.isPermission('leasedTractors/leasedTractorsCancelButton'),
						width : 80,
						/**
						 * 作废外请拖头
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.leasedTractors.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.leasedTractors.i18n('foss.baseinfo.LTvoidOutisdeTracorsMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes' && !Ext.isEmpty(ids)) {
											//获取结果表格对象
											var record = grid.getStore().getAt(rowIndex);
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteLeasedVehicle.action'),
												jsonData : {
													'leasedVehicleVo' : {
														'batchIds' : ids
													}
												},
												//baseinfo.leasedTractors.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedTractors.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.leasedTractors.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
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
							} else {
								Ext.MessageBox.show({
									title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.leasedTractors.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
					},{
						xtype : 'button',
						text : baseinfo.leasedTractors.i18n('foss.baseinfo.export'),//导出
						width:80,
						handler:function(){
							var store =me.store;
							var queryForm = Ext.getCmp('Foss_baseinfo_leasedTractors_QueryForm_Id').getForm(),
							queryParams =queryForm.getValues();
							if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
							}
							if(store.getCount()!=0){
								Ext.Ajax.request({
									url : baseinfo.realPath('exportLeasedVehicle.action'),
									form : Ext.fly('downloadAttachFileForm'),
									method :'POST',
									params : {
										'leasedVehicleVo.leasedVehicle.vehicleNo' : queryParams.vehicleNo,
										'leasedVehicleVo.leasedVehicle.operatingLic' : queryParams.operatingLic,
										'leasedVehicleVo.leasedVehicle.drivingLicense' : queryParams.drivingLicense,
										'leasedVehicleVo.leasedVehicle.bridge' : queryParams.bridge,
										'leasedVehicleVo.leasedVehicle.gps' : queryParams.gps,
										'leasedVehicleVo.leasedVehicle.bargainVehicle' : queryParams.bargainVehicle,
										'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_tractors' 
									},
									isUpload: true
								});
							}else{
								Ext.MessageBox.show({
									title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.leasedTractors.i18n('foss.baseinfo.anyOfTheGridRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}			
						}
				
					}]
			}
		];
		me.callParent([cfg]);
	}
});

/*****************************************************  定义baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')申请窗口 *****************************************************/
/*
 * 定义：baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.leasedTractors.LeasedTractorsAddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	width : '100%',
	collapsible : true,
	isSearchComb : true,
	defaults : {
		//margin : '5 25 5 25',
		//labelWidth : 125
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 1
	},
	items : [{
			// Fieldset in Column 1 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtractorsBasicInformation'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 125,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					name : 'vehicleNo',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTlicensePlateNumber'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtractorsLicensePlateCanNotEmpty'),
					maxLength : 50,
//					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
//					regexText : baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.77'),
					regex :baseinfo.regCompCodeLimit.vehicleNo,
					regexText : "车牌号不能（包含）特殊字符，只能由汉字、数字或者字母任意组合而成！",
					readOnly : false,
					columnWidth : 0.5,
					validateOnBlur : true,
					validateOnChange : true,
					/*validator : function (val) {
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
					},*/
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
//							if(!me.wasValid){
//								me.setValue(null);
//								return;
//								}
//							if(!Ext.isEmpty(me.getValue())){
//								 if(me.getValue().length != 7){
//								 	Ext.MessageBox.show({
//										title : "信息（失败）提示",
//										msg : "输入长度必须满足七位，请重新输入!",
//										width : 300,
//										buttons : Ext.Msg.OK,
//										icon : Ext.MessageBox.WARNING
//									});
//								 	me.setValue(null);
//								 	return;
//								 }
//							}
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
									//检测baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.75')是否已经存在
									var json = Ext.decode(response.responseText);
									if (Ext.isEmpty(json.leasedVehicleVo.leasedVehicle)) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
//										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTtractorsLicenseAlreadyExists');
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
						}
					}
				}, {
					name : 'vehicleFrameNo',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTchassisNumber'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTchassisNumberCanNotEmpty'),
					maxLength : 50,
					readOnly : false,
					columnWidth : 0.5
				}, {
					name : 'engineNo',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTnumberOfEngines'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTengineNumberCanNotEmpty'),
					maxLength : 50,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'combobox',
					name : 'bridge',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTsingleDoubleBridge'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LT.singleDoubleBredgeCanNotEmpty'),
					columnWidth : 0.5,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRIGE_TYPE', 'Foss_baseinfo_leasedTractors_BridgeStore_Id')
				}, {
					xtype : 'datefield',
					name : 'beginTime',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdateOfManufactureCanNotEmpty'),
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdateOfManufacture'),
					columnWidth : 0.5,
					format:'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('endTime').getValue();
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTfactoryDateEqualDiscardDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTfactoryDateAfterDiscardDate');
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							}
						},
						change : function(me, newValue, oldValue, eOpts) {
							if(Ext.isEmpty(newValue)){ return; }
							var tempForm = me.up('form').getForm();
							var endTime = tempForm.findField('endTime');
							var producingArea = tempForm.findField('producingArea');
							if(!Ext.isEmpty(endTime)){
								//时间定义
								var Y_producingArea = 5, N_producingArea = 7;
								var beginDate = new Date(Ext.Date.format(newValue, 'Y-m-d'));
								//修正超龄
								var newYear = null;
								if(!Ext.isEmpty(producingArea)){
									var isArea = producingArea.getValue();
									if(isArea || 'Y' === isArea){
										newYear = beginDate.getFullYear() + Y_producingArea;
									} else {
										newYear = beginDate.getFullYear() + N_producingArea;
									}
									beginDate.setFullYear(newYear, beginDate.getMonth(), beginDate.getDay());
									endTime.setValue(beginDate);
								} beginDate = null;
							} endTime = null; tempForm = null; 
						}
					}
				}, {
					xtype : 'datefield',
					name : 'endTime',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdiscardDateCanNotEmpty'),
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdiscardDate'),
					columnWidth : 0.5,
					format:'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('beginTime').getValue();
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '报废日期不能（等于）出厂日期！';
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '报废日期不能（早于）出厂日期！';
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							} else {}
						}
					}
				}, {
					xtype : 'combobox',
					name : 'brand',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTbrand'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTbrandCanNotEmpty'),
					columnWidth : 0.5,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_leasedTractors_BridgeStore_Id')
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 100,
					name : 'deadLoad',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.loadTons'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 50,
					name : 'selfWeight',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTweightTons'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToriginVesting'),
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTorginAttributionCanNotEmpty'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdomestic'),
							name : 'producingArea',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTimports'),
							name : 'producingArea',
							inputValue : 'N'
						}
					],
					listeners : {
						change : function (me, newVal, oldVal) {
							var tempForm = me.up('form').getForm();
							var endTime = tempForm.findField('endTime');
							var beginTime = tempForm.findField('beginTime');
							var producingArea = newVal.producingArea;
							if(!Ext.isEmpty(endTime.getValue())){
								//时间定义
								var Y_producingArea = 5, N_producingArea = 7;
								var beginDate = new Date(Ext.Date.format(beginTime.getValue(), 'Y-m-d'));
								//修正超龄
								var newYear = null;
								if(!Ext.isEmpty(producingArea)){
									var isArea = producingArea;
									if('Y' == isArea){
										newYear = beginDate.getFullYear() + Y_producingArea;
									} else {
										newYear = beginDate.getFullYear() + N_producingArea;
									}
									beginDate.setFullYear(newYear, beginDate.getMonth(), beginDate.getDay());
									endTime.setValue(beginDate);
								} beginDate = null;
							} endTime = null; tempForm = null; 
							}
						}
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTwhetherContractVehicle'),
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTcontractVehiclesCanNotEmpty'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.yes'),
							name : 'bargainVehicle',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.no'),
							name : 'bargainVehicle',
							inputValue : 'N'
						}
					],
					listeners : {
						change : function (me, newVal, oldVal) {
							var disabled = true;
							if (!Ext.isEmpty(newVal.bargainVehicle)) {
								if (newVal.bargainVehicle === 'Y') {
									disabled = false;
								} else {}
								if (newVal.bargainVehicle === 'N') {
									disabled = true;
								} else {}
								var bargainRouteVal = null;
								var windowModel = me.up('window').getAddUpdateFormModel();
								if (!disabled) {
									bargainRouteVal = windowModel.get('bargainRoute');
								} else {}
								windowModel = null;
								var bargainRouteObj = me.up('form').getForm().findField('bargainRoute');
								if (!Ext.isEmpty(bargainRouteObj)) {
									bargainRouteObj.clearInvalid();
									bargainRouteObj.setDisabled(disabled);
									if (disabled) {
										//因"disabled"强制假值骗取验证通过
										bargainRouteObj.setValue(null);
									} else {
										if (me.up('window').getWindowAction() === 'add') {
											bargainRouteObj.setValue(null);
										} else {
											bargainRouteObj.setValue(bargainRouteVal);
										}
									}
								}
							}
						}
					}
				}, {
					/*
					 * 这个是公共选择器
					 */
					xtype : 'commonlineselector',
					name : 'bargainRoute',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTcontractLine'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTcontractLineCanNotEmpty'),
					readOnly : false,
					columnWidth : 0.5,
					editable : true,
					selector : function (_val) {
						var me = this;
						if (Ext.isEmpty(_val)) {
							return;
						} else {
							me.store.removeAll(false);
							me.queryParam = 'lineVo.lineEntity.simpleCode';
							me.rawValue = _val;
							me.store.load({
								scope : me,
								params : {
									start : 0,
									limit : 1,
									'lineVo.lineEntity.simpleCode' : _val
								},
								callback : function (rs) {
									if (!Ext.isEmpty(rs)) {
										me.setValue(me.rawValue);
									} else {
										me.setValue(null);
									}
									me.queryParam = 'lineVo.lineEntity.lineName';
								}
							});
						}
					}
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTloadingGps'),
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTgpsCanNotEmpty'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.yes'),
							name : 'gps',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.no'),
							name : 'gps',
							inputValue : 'N'
						}
					],
					listeners : {
						change : function (me, newVal, oldVal) {
							var disabled = true;
							if (!Ext.isEmpty(newVal.gps)) {
								if (newVal.gps === 'Y') {
									disabled = false;
								} else {}
								if (newVal.gps === 'N') {
									disabled = true;
								} else {}
								var gpsProvider = null;
								var fieldObj = me.up('form').getForm().findField('gpsProvider');
								var windowModel = me.up('window').getAddUpdateFormModel();
								if (!disabled) {
									gpsProvider = windowModel.get('gpsProvider'); ;
								}
								fieldObj.setDisabled(disabled);
								fieldObj.setValue(gpsProvider);
								windowModel = null;
								fieldObj = null;
							} else {}
						}
					}
				}, {
					name : 'gpsProvider',
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTgpsSuppliers'),
					colspan : 2,
					maxLength : 200,
					width : 615
				}
			]
		}, {
			// Fieldset in Column 2 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtractorsDocumentsInformation'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 127,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					name : 'operatingLic',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingCardNumber'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperationCardNumberCanNotEmpty'),
					maxLength : 50,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.78'),
					columnWidth : 0.5,
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
						message : '',
						value : null
					},
					listeners : {
						focus : function (me, event, eOpts) {
							me.validatorMessage.value = Ext.String.trim(me.getValue()); ;
						},
						/**
						 * 模拟选择器：失去焦点获取外请拖头信息
						 * @param {Ext.Component} me 当前对象
						 * @param {Ext.EventObject} event 当前触发事件对象
						 */
						blur : function (me, event) {
							//临时参数
							var isBreak = false;
							var mineVal = Ext.String.trim(me.getValue());
							if (Ext.isEmpty(mineVal) || me.readOnly) {
								me.setValue(null);
								isBreak = true;
							} else {
								if (Ext.isEmpty(me.validatorMessage.value)) {
									me.validatorMessage.value = mineVal;
								} else {
									if (me.validatorMessage.value === mineVal) {
										isBreak = true;
									} else {
										me.validatorMessage.value = mineVal;
									}
								}
							}
							//判断是否验证是否需要继续
							if (isBreak) {
								return;
							}
							//执行验证
							Ext.Ajax.request({
								url : baseinfo.realPath('queryLeasedVehicle.action'),
								jsonData : {
									'leasedVehicleVo' : {
										'leasedVehicle' : {
											'operatingLic' : mineVal
										}
									}
								},
								success : function (response) {
									//检测baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingCardNumber')是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTtheOperationCardMessage') + leasedVehicle.vehicleNo + baseinfo.leasedTractors.i18n('foss.baseinfo.LTtheOperationCardMessageAlreadyExists');
										/*
										 * "修改动作"的特殊验证逻辑
										 */
										if (me.up('window').getWindowAction() === 'upd') {
											var formModel = me.up('window').getAddUpdateFormModel();
											if (!Ext.isEmpty(formModel)) {
												if (leasedVehicle.id.toString() === formModel.get('id').toString()) {
													me.validatorMessage.validity = true;
													me.validatorMessage.message = null;
												}
											}
											formModel = null;
										}
									}
									/*
									 * 错误信息提示方式
									 */
									if (Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
											msg : me.validatorMessage.message,
											width : 350,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
									json = null;
									leasedVehicle = null;
									me.validatorMessage.value = null;
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									me.validatorMessage.validity = false;
									me.validatorMessage.message = json.message;
									me.validatorMessage.value = null;
									Ext.MessageBox.show({
										title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
							mineVal = null;
						}
					}
				}, {
					xtype : 'datefield',
					name : 'beginTimeOperatingLic',
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingPermitEffectiveStartDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperationStartDateCanNotEmpty'),
					columnWidth : 0.5,
					format : 'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('endTimeOperatingLic').getValue();
							var targetObj = me.up('form').getForm().findField('endTimeOperatingLic');
							if(Ext.isEmpty(target)){
								targetObj.validatorMessage.validity = false;
								targetObj.validatorMessage.message = '营运证有效结束日期不能为空！';
								targetObj.markInvalid(me.validatorMessage.message);
							}else{
								targetObj.validatorMessage.validity = true;
								targetObj.validatorMessage.message = null;
								targetObj.clearInvalid();
							}
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '营运证有效开始日期不能等于营运证有效结束日期!';
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '营运证有效开始日期不能早于营运证有效结束日期!';
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							} else {}
						}
					}
				}, {
					xtype : 'datefield',
					name : 'endTimeOperatingLic',
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LToperatingPermitEffectiveEndDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LT.operationEndDateCanNotEmpty'),
					columnWidth : 0.5,
					format : 'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('beginTimeOperatingLic').getValue();
							var targetObj = me.up('form').getForm().findField('beginTimeOperatingLic');
							if(Ext.isEmpty(target)){
								targetObj.validatorMessage.validity = false;
								targetObj.validatorMessage.message = '营运证有效开始日期不能为空！';
								targetObj.markInvalid(me.validatorMessage.message);
							}else{
								targetObj.validatorMessage.validity = true;
								targetObj.validatorMessage.message = null;
								targetObj.clearInvalid();
							}
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '营运证有效结束日期不能等于营运证有效开始日期!';
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '营运证有效结束日期不能早于营运证有效开始日期!';
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							} else {}
						}
					}
				}, {
					name : 'drivingLicense',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtravelingCardNumber'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtravelingCardCanNotEmpty'),
					maxLength : 50,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdriveringLicNotContSpeChar'),
					columnWidth : 0.5,
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
						message : '',
						value : null
					},
					listeners : {
						focus : function (me, event, eOpts) {
							me.validatorMessage.value = Ext.String.trim(me.getValue()); ;
						},
						/**
						 * 模拟选择器：失去焦点获取外请拖头信息
						 * @param {Ext.Component} me 当前对象
						 * @param {Ext.EventObject} event 当前触发事件对象
						 */
						blur : function (me, event) {
							//临时参数
							var isBreak = false;
							var mineVal = Ext.String.trim(me.getValue());
							if (Ext.isEmpty(mineVal) || me.readOnly) {
								me.setValue(null);
								isBreak = true;
							} else {
								if (Ext.isEmpty(me.validatorMessage.value)) {
									me.validatorMessage.value = mineVal;
								} else {
									if (me.validatorMessage.value === mineVal) {
										isBreak = true;
									} else {
										me.validatorMessage.value = mineVal;
									}
								}
							}
							//判断是否验证是否需要继续
							if (isBreak) {
								return;
							}
							//执行验证
							Ext.Ajax.request({
								url : baseinfo.realPath('queryLeasedVehicle.action'),
								jsonData : {
									'leasedVehicleVo' : {
										'leasedVehicle' : {
											'drivingLicense' : mineVal
										}
									}
								},
								success : function (response) {
									//检测baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.80')是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTtravelingCardMessage') + leasedVehicle.vehicleNo + baseinfo.leasedTractors.i18n('foss.baseinfo.LTtheOperationCardMessageAlreadyExists');
										/*
										 * "修改动作"的特殊验证逻辑
										 */
										if (me.up('window').getWindowAction() === 'upd') {
											var formModel = me.up('window').getAddUpdateFormModel();
											if (!Ext.isEmpty(formModel)) {
												if (leasedVehicle.id.toString() === formModel.get('id').toString()) {
													me.validatorMessage.validity = true;
													me.validatorMessage.message = null;
												}
											}
											formModel = null;
										}
									}
									/*
									 * 错误信息提示方式
									 */
									if (Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
											msg : me.validatorMessage.message,
											width : 350,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
									json = null;
									leasedVehicle = null;
									me.validatorMessage.value = null;
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									me.validatorMessage.validity = false;
									me.validatorMessage.message = json.message;
									me.validatorMessage.value = null;
									Ext.MessageBox.show({
										title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
							mineVal = null;
						}
					}
				}, {
					xtype : 'datefield',
					name : 'beginTimeDrivingLic',
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdrivingPermitValidStartDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtravelingCardStartDateCanNotEmpty'),
					columnWidth : 0.5,
					format : 'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('endTimeDrivingLic').getValue();
							var targetObj = me.up('form').getForm().findField('endTimeDrivingLic');
							if(Ext.isEmpty(target)){
								targetObj.validatorMessage.validity = false;
								targetObj.validatorMessage.message = '行驶证有效结束日期不能为空！';
								targetObj.markInvalid(me.validatorMessage.message);
							}else{
								targetObj.validatorMessage.validity = true;
								targetObj.validatorMessage.message = null;
								targetObj.clearInvalid();
							}
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTtravelingCardStartDateEqualEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTtravelingCardStartDateAfterEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							} else {}
						}
					}
				}, {
					xtype : 'datefield',
					name : 'endTimeDrivingLic',
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTdrivingPermitValidEndDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtravelingCardEndDateCanNotEmpty'),
					columnWidth : 0.5,
					format : 'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('beginTimeDrivingLic').getValue();
							var targetObj = me.up('form').getForm().findField('beginTimeDrivingLic');
							if(Ext.isEmpty(target)){
								targetObj.validatorMessage.validity = false;
								targetObj.validatorMessage.message = '行驶证有效开始日期不能为空！';
								targetObj.markInvalid(me.validatorMessage.message);
							}else{
								targetObj.validatorMessage.validity = true;
								targetObj.validatorMessage.message = null;
								targetObj.clearInvalid();
							}
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '行驶证有效结束日期不能（等于）其开始日期！';
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '行驶证有效结束日期不能（早于）其开始日期！';
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							} else {}
						}
					}
				}, {
					name : 'insureCard',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardNumber'),
					maxLength : 50,
					readOnly : false,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.81'),
					columnWidth : 0.5,
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
						message : '',
						value : null
					},
					listeners : {
						focus : function (me, event, eOpts) {
							me.validatorMessage.value = Ext.String.trim(me.getValue()); ;
						},
						/**
						 * 模拟选择器：失去焦点获取外请拖头信息
						 * @param {Ext.Component} me 当前对象
						 * @param {Ext.EventObject} event 当前触发事件对象
						 */
						blur : function (me, event) {
							//临时参数
							var isBreak = false;
							var mineVal = Ext.String.trim(me.getValue());
							if (Ext.isEmpty(mineVal) || me.readOnly) {
								me.setValue(null);
								isBreak = true;
							} else {
								if (Ext.isEmpty(me.validatorMessage.value)) {
									me.validatorMessage.value = mineVal;
								} else {
									if (me.validatorMessage.value === mineVal) {
										isBreak = true;
									} else {
										me.validatorMessage.value = mineVal;
									}
								}
							}
							//判断是否验证是否需要继续
							if (isBreak) {
								return;
							}
							//执行验证
							Ext.Ajax.request({
								url : baseinfo.realPath('queryLeasedVehicle.action'),
								jsonData : {
									'leasedVehicleVo' : {
										'leasedVehicle' : {
											'insureCard' : mineVal
										}
									}
								},
								success : function (response) {
									//检测baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardNumber')是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardAlreadyExists') + leasedVehicle.vehicleNo + baseinfo.leasedTractors.i18n('foss.baseinfo.LTtheOperationCardMessageAlreadyExists');
										/*
										 * "修改动作"的特殊验证逻辑
										 */
										if (me.up('window').getWindowAction() === 'upd') {
											var formModel = me.up('window').getAddUpdateFormModel();
											if (!Ext.isEmpty(formModel)) {
												if (leasedVehicle.id.toString() === formModel.get('id').toString()) {
													me.validatorMessage.validity = true;
													me.validatorMessage.message = null;
												}
											}
											formModel = null;
										}
									}
									/*
									 * 错误信息提示方式
									 */
									if (Ext.isBoolean(me.validatorMessage.validity) && me.validatorMessage.validity) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
											msg : me.validatorMessage.message,
											width : 350,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
									}
									json = null;
									leasedVehicle = null;
									me.validatorMessage.value = null;
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									me.validatorMessage.validity = false;
									me.validatorMessage.message = json.message;
									me.validatorMessage.value = null;
									Ext.MessageBox.show({
										title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							});
							mineVal = null;
						}
					}
				}, {
					xtype : 'datefield',
					name : 'beginTimeInsureCard',//保险卡有效开始日期
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardValidStartDate'),
					columnWidth : 0.5,
					format : 'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('endTimeInsureCard').getValue();
							var targetObj = me.up('form').getForm().findField('endTimeInsureCard');
							if(Ext.isEmpty(target)){
								targetObj.validatorMessage.validity = false;
								targetObj.validatorMessage.message = '保险卡有效结束日期不能为空！';
								targetObj.markInvalid(me.validatorMessage.message);
							}else{
								targetObj.validatorMessage.validity = true;
								targetObj.validatorMessage.message = null;
								targetObj.clearInvalid();
							}
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardStartDateEqualEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardStartDateAfterEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							} else {}
						}
					}
				}, {
					xtype : 'datefield',
					name : 'endTimeInsureCard',
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTinsuranceCardValidEndDate'),
					columnWidth : 0.5,
					format : 'Y-m-d',
					editable : false,
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
						blur : function (me, event) {
							var source = me.getValue();
							var target = me.up('form').getForm().findField('beginTimeInsureCard').getValue();
							var targetObj = me.up('form').getForm().findField('beginTimeInsureCard');
							if(Ext.isEmpty(target)){
								targetObj.validatorMessage.validity = false;
								targetObj.validatorMessage.message = '保险卡有效开始日期不能为空！';
								targetObj.markInvalid(me.validatorMessage.message);
							}else{
								targetObj.validatorMessage.validity = true;
								targetObj.validatorMessage.message = null;
								targetObj.clearInvalid();
							}
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '保险卡有效结束日期不能（等于）其开始日期！';
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '保险卡有效结束日期不能（早于）其开始日期！';
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							} else {}
						}
					}
				}
			]
		}, {
			// Fieldset in Column 3 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtractorOwnersInformation'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 125,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					name : 'contact',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTnameOfOwner'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.LTownerNameCanNotEmpty'),
					maxLength : 50,
					columnWidth : 0.5
				}, {
					name : 'contactPhone',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTownerPhone'),
					maxLength : 50,
					regex: /(^\d{11}$)/,
					regexText : baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfoTelphoneError'),
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'commonleaseddriverselector',
					status : 'Y',
					name : 'leasedDriverIdCard',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.ownDriversInformation'),
					allowBlank : false,
					queryAllFlag:false,
					blankText : baseinfo.leasedTractors.i18n('foss.baseinfo.ownDriversInformationCanNotEmpty'),
					readOnly : false,
					editable : true/*,
					selector : function (_val) {
						var me = this;
						if (Ext.isEmpty(_val)) {
							return;
						} else {
							me.store.removeAll(false);
							me.queryParam = 'leasedDriverVo.leasedDriver.idCard';
							me.rawValue = _val;
							me.store.load({
								scope : me,
								params : {
									start : 0,
									limit : 1,
									'leasedDriverVo.leasedDriver.idCard' : _val
								},
								callback : function (rs) {
									if (!Ext.isEmpty(rs)) {
										me.setValue(me.rawValue);
									} else {
										me.setValue(null);
									}
									me.queryParam = 'leasedDriverVo.leasedDriver.driverName';
								}
							});
						}
					}*/
				}, {
					name : 'contactAddress',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.airagencycompany.linkAddress'),
					readOnly : false,
					colspan : 3,
					maxLength : 300,
					width : 940
				}
			]
		}, {
			// Fieldset in Column 4 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedTractors.i18n('foss.baseinfo.LTtractorsRemarks'),
			collapsible : true,
			defaultType : 'textfield',
			defaults : {
				anchor : '100%',
				margin : '5 25 5 25',
				labelWidth : 125,
				labelStyle : 'text-align: left;'
			},
			layout : {
				type : 'table',
				columns : 3
			},
			items : [{
					xtype : 'textareafield',
					name : 'notes',
					readOnly : false,
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.airagencycompany.remark'),
					colspan : 3,
					maxLength : 500,
					width : 940
				}, {
					name : 'vehicleType',
					fieldLabel : baseinfo.leasedTractors.i18n('foss.baseinfo.LTvehicleType'),
					readOnly : true,
					hidden : true,
					columnWidth : 0.5,
					colspan : 2,
					value : 'vehicletype_tractors'
				}, {
					name : 'id',
					fieldLabel : 'ID',
					readOnly : true,
					hidden : true,
					columnWidth : 0.5
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
 * 定义baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')申请的表单窗口
 */
Ext.define('Foss.baseinfo.leasedTractors.LeasedTractorsAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedTractors.i18n('foss.baseinfo.LTaddModifyTracotrInformation'),
	width : 1100,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.leasedTractors.LeasedTractorsAddUpdateForm');
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
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.leasedTractors.LeasedTractorsModel");
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
		if (this.operationUrl.toString().indexOf('addLeasedVehicle') != -1) {
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
						text : baseinfo.leasedTractors.i18n('foss.baseinfo.cancel'),
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
						text : baseinfo.leasedTractors.i18n('foss.baseinfo.reset'),
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
						text : baseinfo.leasedTractors.i18n('foss.baseinfo.save'),
						plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									//刷新数据Model
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());
									//处理因"disabled"不能赋值的业务规则数据
									var bargainVehicle = addUpdateForm.findField('bargainVehicle');
									if (!Ext.isEmpty(bargainVehicle)) {
										if (bargainVehicle.getGroupValue() === 'N') {
											var bargainRoute = addUpdateForm.findField('bargainRoute');
											if (!Ext.isEmpty(bargainRoute)) {
												if (bargainRoute.isDisabled()) {
													me.getAddUpdateFormModel().set('bargainRoute', null);
												} else {}
											} else {
												return false;
											}
											bargainRoute = null;
										} else {}
									} else {
										return false;
									}
									bargainVehicle = null;
									var gps = addUpdateForm.findField('gps');
									if (!Ext.isEmpty(gps)) {
										if (gps.getGroupValue() === 'N') {
											var gpsProvider = addUpdateForm.findField('gpsProvider');
											if (!Ext.isEmpty(gpsProvider)) {
												if (gpsProvider.isDisabled()) {
													me.getAddUpdateFormModel().set('gpsProvider', null);
												} else {}
											} else {
												return false;
											}
											gpsProvider = null;
										} else {}
									} else {
										return false;
									}
									gps = null;
									//执行数据请求
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'leasedVehicleVo' : {
												'leasedVehicle' : me.getAddUpdateFormModel().data
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-leasedTractors_content').getLeasedTractorsGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.leasedTractors.i18n('foss.baseinfo.successPrompt'),
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
												title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
//													me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.WARNING
											});
											addUpdateForm = null;
										}
									});
								} else {
									top.Ext.MessageBox.show({
										title : baseinfo.leasedTractors.i18n('foss.baseinfo.failureInformationTips'),
										msg : (function () {
											var message = "<ul>";
											/*
											var idCardError = addUpdateForm.findField('idCard').validatorMessage.message;
											if(!Ext.isEmpty(idCardError)){
											message = "<li>" + addUpdateForm.findField('idCard').validatorMessage.message + "</li>";
											}
											 */
											addUpdateForm.getFields().filterBy(function (value) {
												if (value.name === 'bargainRoute') {
													var disabled = value.hasOwnProperty('disabled');
													if (disabled) {
														return !value.disabled;
													} else {}
													disabled = null;
												} else {}
												return value.getErrors().length > 0;
											}).each(function (item, index, length) {
												message += "<li>" + item.getErrors() + "</li>";
											});
											return message + "</ul>";
										})(),
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
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
			var fielsds = me.getAddUpdateForm().getForm().getFields();
			if (!Ext.isEmpty(fielsds)) {
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
		},
		beforehide : function (me, eOpts) {
			me.getAddUpdateForm().getForm().reset();
		},
		beforeclose : function (me, eOpts) {
			me.fireEvent('beforehide', me);
		}
	}
});

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.leasedTractors.QueryForm');
	/*
	 * 创建查询baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.leasedTractors.LeasedTractorsGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-leasedTractors').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedTractors_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询baseinfo.leasedTractors.i18n('foss.baseinfo.baseinfo-leasedTractors.76')结果列表结果窗口
		getLeasedTractorsGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
//		renderTo : 'T_baseinfo-leasedTractors-body'
	}));
});
