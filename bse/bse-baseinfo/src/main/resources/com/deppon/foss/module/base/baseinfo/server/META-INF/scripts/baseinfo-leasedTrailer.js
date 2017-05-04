/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义（挂车）列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
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
Ext.define('Foss.baseinfo.leasedTrailer.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.leasedTrailer.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_leasedTrailer_QueryForm_Id',
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
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRregisteredNumber'),
			columnWidth : .30
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingCardNumber'),
			columnWidth : .30
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingLicense'),
			columnWidth : .40
		}, {
			xtype : 'combobox',
			name : 'openVehicle',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRisCabriolet'),
			columnWidth : 0.3,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.all')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.no')
						}
					]
				}
			}),
			value : ''
		}, {
			//从车型获取，待实现
			xtype : 'commonvehicletypeselector',
			vehicleSort : 'ownership_leased',
			name : 'vehicleLengthCode',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRmodles'),
			columnWidth : 0.3,
			displayField : 'vehicleLengthCode'
			//displayField : 'vehicleLength'
		}, {
			xtype : 'combobox',
			name : 'bargainVehicle',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTwhetherContractVehicle'),
			columnWidth : 0.4,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.all')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.no')
						}
					]
				}
			}),
			value : ''
		}, {
			xtype : 'button',
			columnWidth : .08,
			text : baseinfo.leasedTrailer.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerQueryButton'),
			hidden:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedTrailer_QueryForm_Id').getForm().reset();
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
			text : baseinfo.leasedTrailer.i18n('foss.baseinfo.query'),
			disabled:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerQueryButton'),
			hidden:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerQueryButton'),
			handler : function () {
				Ext.getCmp('Foss_baseinfo_leasedTrailer_LeasedTrailerGrid_Id').getPagingToolbar().moveFirst();
			}
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')列表结果窗口 *****************************************************/
/*
 * 定义：一个baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedTrailer.LeasedTrailerModel', {
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
			name : 'plat', //有无高地板
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
			name : 'endTime', //超龄日期
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
			name : 'openVehicle', //是否敞篷车
			type : 'string'
		}, {
			name : 'railVehicle', //是否高栏车
			type : 'string'
		}, {
			name : 'material', //材质
			type : 'string'
		}, {
			name : 'bargainRoute', //合同线路
			type : 'string'
		}, {
			name : 'selfVolume', //净空
			type : 'float'
		}, {
			name : 'vehicleHeight', //车高
			type : 'float'
		}, {
			name : 'selfWeight', //自重
			type : 'float'
		}, {
			name : 'vehicleLength', //车长
			type : 'float'
		}, {
			name : 'vehicleWidth', //车宽
			type : 'float'
		}, {
			name : 'deadLoad', //载重
			type : 'float'
		}, {
			name : 'vehicleLengthCode', //车辆车型编码
			type : 'string'
		}, {
			name : 'producingArea', //产地（进口/出口）
			type : 'string'
		}, {
			name : 'brand', //品牌
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
			name : 'vehicleType', //车辆类型（挂车/厢式车/挂车）
			type : 'string',
			defaultValue : 'vehicletype_trailer'
		}, {
			name : 'leasedDriverIdCard', //司机身份证号
			type : 'string'
		}, {
			name : 'leasedDriverName', //司机姓名
			type : 'string'
		},{
			name :'isNotUpdateSelfVolume', //是否修改净空字段
			type :'string'
		}, {
			name : 'measureEmployeeCode', //测量人员
			type : 'string'
		}, {
			name : 'measureManagerCode', //测量经理
			type : 'string'
		},{
			name :'measureSeniorManagerCode', //测量高级经理
			type :'string'
		}, {
			name : 'measureEmployeeName', //测量人员
			type : 'string'
		}, {
			name : 'measureManagerName', //测量经理
			type : 'string'
		},{
			name :'measureSeniorManagerName', //测量高级经理
			type :'string'
		}
		
	]
});

/*
 * 定义：一个baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedTrailer.LeasedTrailerStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.leasedTrailer.LeasedTrailerModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedTrailerList.action'),
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
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedTrailer_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedVehicleVo.leasedVehicle.vehicleNo' : queryParams.vehicleNo,
						'leasedVehicleVo.leasedVehicle.operatingLic' : queryParams.operatingLic,
						'leasedVehicleVo.leasedVehicle.drivingLicense' : queryParams.drivingLicense,
						'leasedVehicleVo.leasedVehicle.openVehicle' : queryParams.openVehicle,
						'leasedVehicleVo.leasedVehicle.vehicleLengthCode' : queryParams.vehicleLengthCode,
						'leasedVehicleVo.leasedVehicle.bargainVehicle' : queryParams.bargainVehicle,
						'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_trailer' //baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')数据字典常量
					}
				});
			}
		}
	}
});

/*
 * 定义：baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.leasedTrailer.LeasedTrailerDetailForm', {
	extend : 'Ext.form.Panel',
	height : 410,
	width : 1000,
	defaults : {
		margin : '5 32 5 32',
		labelWidth : 125
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRregisteredNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleFrameNo',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTchassisNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'plat',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwheterTheFloor'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRhasTheFloor')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.LDRWSNo')
						}
					]
				}
			})
		}, {
			xtype : 'datefield',
			name : 'beginTime',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdateOfManufacture'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d'
		}, {
			xtype : 'datefield',
			name : 'endTime',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdiscardDate'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d'
		}, {
			xtype : 'combobox',
			name : 'bargainVehicle',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTwhetherContractVehicle'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.no')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'openVehicle',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRisCabriolet'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.no')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'railVehicle',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwhetherGaolanCar'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.no')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'material',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRcarCabinetMaterial'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRcupboard')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwitrine')
						}
					]
				}
			})
		}, {
			//公共选择器
			xtype : 'commonlineselector',
			name : 'bargainRoute',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTcontractLine'),
			readOnly : true,
			disabled : false,
			width : 300,
			triggerAction : 'all',
			editable : false,
			queryParam : 'lineVo.lineEntity.simpleCode',
			listeners : {
				change : function (me, newValue, oldValue, eOpts) {
					if (Ext.isEmpty(newValue) && Ext.isEmpty(newValue)) {
						return;
					} else {
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
			name : 'selfVolume',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRclearanceSide'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleHeight',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleHeightInMeters'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'selfWeight',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTweightTons'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleLength',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleLengthM'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleWidth',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleWidthM'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'deadLoad',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.loadTons'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'commonvehicletypeselector',
			vehicleSort : 'ownership_leased',
			name : 'vehicleLengthCode',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRmodles'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'vehicleLengthName',
			queryParam : 'vehicleTypeEntity.vehicleLengthCode',
			listeners : {
				change : function (me, newValue, oldValue, eOpts) {
					if (Ext.isEmpty(newValue)) {
						return;
					}
					me.store.load({
						params : {
							start : 0,
							limit : 1,
							'vehicleTypeEntity.vehicleLengthCode' : newValue
						}
					});
				}
			}
		}, {
			xtype : 'combobox',
			name : 'producingArea',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToriginVesting'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedTrailer.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdomestic')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTimports')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'brand',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTbrand'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_leasedTrailer_BridgeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'measureEmployeeName',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureEmployee'),//测量员工
			columnWidth : 0.5,
			readOnly : true
		}, {
			name : 'measureManagerName',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureManager'),//测量经理
			columnWidth : 0.5,
			readOnly : true
		}, {
			name : 'measureSeniorManagerName',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureSeniorManager'),//测量经理
			columnWidth : 0.5,
			readOnly : true
		},{
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingCardNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingPermitEffectiveStartDate'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingPermitEffectiveEndDate'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeDrivingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingPermitValidStartDate'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeDrivingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingPermitValidEndDate'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			name : 'insureCard',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeInsureCard',
			readOnly : true,
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardValidStartDate'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeInsureCard',
			readOnly : true,
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardValidEndDate'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			name : 'contact',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTnameOfOwner'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTownerPhone'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'leasedDriverName',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.ownDriversInformation'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactAddress',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.airagencycompany.linkAddress'),
			readOnly : true,
			colspan : 3
		}, {
			xtype : 'textareafield',
			name : 'notes',
			readOnly : true,
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.airagencycompany.remark'),
			colspan : 3
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.leasedTrailer.LeasedTrailerDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRoutsideTrailerDetails'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请挂车详细信息
	 * @return {Object} LeasedTrailerDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.leasedTrailer.LeasedTrailerDetailForm');
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
		//做业务动作显示/隐藏动作
		var formModel = record.data;
		if (!Ext.isEmpty(formModel)) {
			var formDOM = this.getInformationForm().getForm();
			var openVehicle = formModel.openVehicle;
			if (openVehicle && openVehicle === 'Y') {
				formDOM.findField('material').hide().setDisabled(true);
				formDOM.findField('railVehicle').show();
			} else {}
			if (openVehicle && openVehicle === 'N') {
				formDOM.findField('railVehicle').hide().setDisabled(false);
				formDOM.findField('material').show();
			} else {}
			formDOM = null;
			openVehicle = null;
		} else {}
		formModel = null;
	}
});



/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.leasedTrailer.LeasedTrailerGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_leasedTrailer_LeasedTrailerGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRoutsideTrailerInformation'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.leasedTrailer.LeasedTrailerDetailPanel' // 行体内容
		}
	],
	//新增修改窗口
	addUpdateWindow : null,
	getAddUpdateWindow : function (windowTitle, actionType) {
		var me = this;
		if (this.addUpdateWindow == null) {
			this.addUpdateWindow = Ext.create('Foss.baseinfo.leasedTrailer.LeasedTrailerAddUpdateWindow');
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
		var tempForm = me.addUpdateWindow.getAddUpdateForm().getForm().reset();
		if (!Ext.isEmpty(tempForm)) {
			if (actionType && actionType === 'add') {
				tempForm.findField('vehicleNo').setReadOnly(false);
				tempForm.findField('selfVolume').setReadOnly(false);
				tempForm.findField('selfVolume').allowBlank=false;
				//敞篷、高栏、材质联动默认值
				tempForm.findField('openVehicle').setValue('N');
				tempForm.findField('material').setValue('N');
				tempForm.findField('railVehicle').setValue(null);
				//合同车、产地、高地板默认值
				tempForm.findField('bargainVehicle').setValue('N');
				tempForm.findField('producingArea').setValue('Y');
				tempForm.findField('plat').setValue('N');
				//公共选择器联动
				tempForm.findField('bargainRoute').setValue(null);
				tempForm.findField('vehicleLengthCode').setValue(null);
				//测量人员
				tempForm.findField('measureEmployeeCode').setReadOnly(false);
				tempForm.findField('measureManagerCode').setReadOnly(false);
				tempForm.findField('measureSeniorManagerCode').setReadOnly(false);
				
				tempForm.findField('measureEmployeeCode').show(true);
				tempForm.findField('measureManagerCode').show(true);
				tempForm.findField('measureSeniorManagerCode').show(true);
				
			}
			if (actionType && actionType === 'upd') {
				tempForm.findField('vehicleNo').setReadOnly(true);
				tempForm.findField('selfVolume').setReadOnly(true);
				tempForm.findField('selfVolume').allowBlank=true;
				//敞篷、高栏、材质联动默认值
				tempForm.findField('openVehicle').setValue(null);
				tempForm.findField('material').setValue(null);
				tempForm.findField('railVehicle').setValue(null);
				//合同车、产地、高地板默认值
				tempForm.findField('bargainVehicle').setValue(null);
				tempForm.findField('producingArea').setValue(null);
				tempForm.findField('plat').setValue(null);
				//测量人员
				tempForm.findField('measureEmployeeCode').setReadOnly(true);
				tempForm.findField('measureManagerCode').setReadOnly(true);
				tempForm.findField('measureSeniorManagerCode').setReadOnly(true);
				//测量人员
				tempForm.findField('measureEmployeeCode').hide(true);
				tempForm.findField('measureManagerCode').hide(true);
				tempForm.findField('measureSeniorManagerCode').hide(true);
			}
		} else {}
		tempForm = null;
		return this.addUpdateWindow;
	},
	//修改净空窗口
	updateSelfVolumeWindow:null,
	getUpdateSelfVolumeWindow:function(){
		if (this.updateSelfVolumeWindow == null) {
			this.updateSelfVolumeWindow = Ext.create('Foss.baseinfo.leasedTrailer.UpdateSelfVolumeWindow');
		}
		return this.updateSelfVolumeWindow;
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
			text : baseinfo.leasedTrailer.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.leasedTrailer.i18n('foss.baseinfo.update'),
					disabled:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerUpdateButton'),
					/**
					 * baseinfo.leasedTrailer.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
						var form = addUpdateWindow.getAddUpdateForm().getForm();
						form.findField('bargainRoute').selector(record.get('bargainRoute'));// //合同线路 
						//所属司机
						form.findField('leasedDriverIdCard').setCombValue(record.get('leasedDriverName'),record.get('leasedDriverIdCard'));
						addUpdateWindow.loadAddUpdateForm(record);
						form.findField('measureEmployeeCode1').setValue(record.get('measureEmployeeCode'));
						form.findField('measureEmployeeName1').setValue(record.get('measureEmployeeName'));
						form.findField('measureManagerCode1').setValue(record.get('measureManagerCode'));
						form.findField('measureManagerName1').setValue(record.get('measureManagerName'));
						form.findField('measureSeniorManagerCode1').setValue(record.get('measureSeniorManagerCode'));
						form.findField('measureSeniorManagerName1').setValue(record.get('measureSeniorManagerName'));
						addUpdateWindow.show();
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.leasedTrailer.i18n('foss.baseinfo.void'),
					disabled:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerVoidButton'),
					/**
					 * baseinfo.leasedTrailer.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.leasedTrailer.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvoidOutsideTrailersConfirmMessage'),
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
										//baseinfo.leasedTrailer.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedTrailer.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.leasedTrailer.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRregisteredNumber'),
			dataIndex : 'vehicleNo',
			width : 82
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.ownDriversInformation'),
			dataIndex : 'leasedDriverName',
			width : 82
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTchassisNumber'),
			dataIndex : 'vehicleFrameNo',
			width : 82
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleLengthM'),
			dataIndex : 'vehicleLength',
			width : 81
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRclearanceSide'),
			dataIndex : 'selfVolume',
			width : 81
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.loadTons'),
			dataIndex : 'deadLoad',
			width : 81
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTweightTons'),
			dataIndex : 'selfWeight',
			width : 81
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwheterTheFloor'),
			dataIndex : 'plat',
			width : 90,
			renderer : function (value) {
				if (value === 'Y') {
					return baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRhasTheFloor');
				} else if (value === 'N') {
					return baseinfo.leasedTrailer.i18n('foss.baseinfo.LDRWSNo');
				} else {
					return '-';
				}
			}
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingCardNumber'),
			dataIndex : 'operatingLic',
			width : 85
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingLicense'),
			dataIndex : 'drivingLicense',
			width : 85
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTwhetherContractVehicle'),
			dataIndex : 'bargainVehicle',
			width : 90,
			renderer : function (value) {
				if (value === 'Y') {
					return baseinfo.leasedTrailer.i18n('foss.baseinfo.yes');
				} else if (value === 'N') {
					return baseinfo.leasedTrailer.i18n('foss.baseinfo.no');
				} else {
					return '-';
				}
			}
		}, {
			header : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRisCabriolet'),
			dataIndex : 'openVehicle',
			width : 90,
			renderer : function (value) {
				if (value === 'Y') {
					return baseinfo.leasedTrailer.i18n('foss.baseinfo.yes');
				} else if (value === 'N') {
					return baseinfo.leasedTrailer.i18n('foss.baseinfo.no');
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
		me.store = Ext.create('Foss.baseinfo.leasedTrailer.LeasedTrailerStore');
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
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerAddButton'),
						hidden:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerAddButton'),
						width : 80,
						//弹出baseinfo.leasedTrailer.i18n('foss.baseinfo.add')的外请挂车的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerVoidButton'),
						hidden:!baseinfo.leasedTrailer.isPermission('leasedTrailer/leasedTrailerVoidButton'),
						width : 80,
						/**
						 * 作废外请挂车
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.leasedTrailer.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvoidOutsideTrailersConfirmMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes' && !Ext.isEmpty(ids)) {
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteLeasedVehicle.action'),
												jsonData : {
													'leasedVehicleVo' : {
														'batchIds' : ids
													}
												},
												//baseinfo.leasedTrailer.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedTrailer.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.leasedTrailer.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
									title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.leasedTrailer.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
					},{
						xtype : 'button',
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.export'),//导出
						width:80,
						handler:function(){
							var store =me.store;
							var queryForm = Ext.getCmp('Foss_baseinfo_leasedTrailer_QueryForm_Id').getForm(); //查询的form 
							
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
										'leasedVehicleVo.leasedVehicle.vehicleNo' : queryForm.findField('vehicleNo').getValue(),
										'leasedVehicleVo.leasedVehicle.operatingLic' : queryForm.findField('operatingLic').getValue(),
										'leasedVehicleVo.leasedVehicle.drivingLicense' : queryForm.findField('drivingLicense').getValue(),
										'leasedVehicleVo.leasedVehicle.openVehicle' :queryForm.findField('openVehicle').getValue(),
										'leasedVehicleVo.leasedVehicle.vehicleLengthCode' :queryForm.findField('vehicleLengthCode').getValue() ,
										'leasedVehicleVo.leasedVehicle.bargainVehicle' : queryForm.findField('bargainVehicle').getValue(),
										'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_trailer' 
									},
									isUpload: true
								});
							}else{
								Ext.MessageBox.show({
									title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.leasedTrailer.i18n('foss.baseinfo.anyOfTheGridRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
					},{
						xtype : 'button',
						text : '修改净空',
						disabled:!baseinfo.leasedTrailer.isPermission('leasedTrailer/updateSelfVolumeButton'),
						hidden:!baseinfo.leasedTrailer.isPermission('leasedTrailer/updateSelfVolumeButton'),
						width : 80,
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length != 1) {
								Ext.MessageBox.show({
									title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
									msg : '只能选择一条记录进行修改净空！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								return;
							}
							me.getUpdateSelfVolumeWindow().vehicleRecord=selectionRecord[0];
							me.getUpdateSelfVolumeWindow().getUpdateSelfVolumeForm().getForm().loadRecord(selectionRecord[0]);
							var selfVolumeHide = selectionRecord[0].data.selfVolume;
							me.getUpdateSelfVolumeWindow().getUpdateSelfVolumeForm().getForm().findField('selfVolumeHide').setValue(selfVolumeHide);
							me.getUpdateSelfVolumeWindow().show();
						}
					}]
			}
		];
		me.callParent([cfg]);
	}
});

/*****************************************************  定义baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')申请窗口 *****************************************************/
/*
 * 定义：baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.leasedTrailer.LeasedTrailerAddUpdateForm', {
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
			title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRtrailerBasicInformation'),
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRregisteredNumber'),//挂号
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRregisteredCanNotEmpty'),//挂号不能为空
					maxLength : 50,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedTrailer.i18n('foss.baseinfo.baseinfo-leasedTrailer.85'),//挂号不能（包含）特殊字符
					readOnly : false,
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
						message : ''
					},
					listeners : {
						/**
						 * 模拟选择器：失去焦点获取外请挂车信息
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
									//检测baseinfo.leasedTrailer.i18n('foss.baseinfo.baseinfo-leasedTrailer.83')是否已经存在
									var json = Ext.decode(response.responseText);
									if (Ext.isEmpty(json.leasedVehicleVo.leasedVehicle)) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRlicenseNumberAlreadyExists');
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTchassisNumber'),//车架号
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTchassisNumberCanNotEmpty'),//车架号不能为空
					maxLength : 50,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwheterTheFloor'),//有无高地板
					name : 'plat_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwhetherTheFloorCanNotEmpty'),//有无高地板不能为空
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRhasTheFloor'),//有
							name : 'plat',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LDRWSNo'),//无
							name : 'plat',
							inputValue : 'N'
						}
					]
				}, {
					xtype : 'datefield',
					name : 'beginTime',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdateOfManufactureCanNotEmpty'),//出厂日期不能为空
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdateOfManufacture'),//出厂日期
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
							var endTime = me.up('form').getForm().findField('endTime');
							var target = endTime.getValue();
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTfactoryDateEqualDiscardDate');//出厂日期不能（等于）超龄日期！
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTfactoryDateAfterDiscardDate');//出厂日期不能（晚于）超龄日期！
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
									endTime.validatorMessage.validity = true;
									endTime.validatorMessage.message = null;
									endTime.clearInvalid();
								}
							}
						}
					}
				}, {
					xtype : 'datefield',
					name : 'endTime',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdiscardDateCanNotEmpty'),//超龄日期不能为空
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdiscardDate'),//超龄日期
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
							var beginTime = me.up('form').getForm().findField('beginTime');
							var target = beginTime.getValue();
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '超龄日期不能（等于）出厂日期！';
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '超龄日期不能（早于）出厂日期！';
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
									beginTime.validatorMessage.validity = true;
									beginTime.validatorMessage.message = null;
									beginTime.clearInvalid();
								}
							} else {}
						}
					}
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTwhetherContractVehicle'),//是否合同车
					name : 'bargainVehicle_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTcontractVehiclesCanNotEmpty'),//合同车不能为空
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes'),
							name : 'bargainVehicle',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.no'),
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
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRisCabriolet'),//是否敞篷车
					name : 'openVehicle_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRcabrioletCanNotEmpty'),//是否敞篷车不能为空
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes'),
							name : 'openVehicle',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.no'),
							name : 'openVehicle',
							inputValue : 'N'
						}
					],
					listeners : {
						change : function (me, newVal, oldVal) {
							var formObj = me.up('form').getForm();
							//var windowModel = me.up('window').getAddUpdateFormModel();
							if (!Ext.isEmpty(newVal.openVehicle)) {
								if (newVal.openVehicle === 'Y') {
									var railVehicleObj = formObj.findField('railVehicle_RadioGroup');
									if (!Ext.isEmpty(railVehicleObj)) {
										railVehicleObj.show().setDisabled(false);
										var railVehicle = formObj.findField('railVehicle');
										if (!Ext.isEmpty(railVehicle)) {
											var val = railVehicle.getGroupValue();
											if (Ext.isEmpty(val)) {
												railVehicle.setValue('N');
											} else {
												railVehicle.setValue(val);
											}
											val = null;
										} else {}
										railVehicle = null;
									} else {}
									railVehicleObj = null;

									var materialObj = formObj.findField('material_RadioGroup');
									if (!Ext.isEmpty(materialObj)) {
										materialObj.hide().setDisabled(true);
									} else {}
									materialObj = null;

									var selfVolume = formObj.findField('selfVolume');
									if (!Ext.isEmpty(selfVolume)) {
										//selfVolume.setDisabled(true);
										selfVolume.setValue(null);
									} else {}
									selfVolume = null;

									var vehicleHeight = formObj.findField('vehicleHeight');
									if (!Ext.isEmpty(vehicleHeight)) {
										//vehicleHeight.setDisabled(true);
										vehicleHeight.setValue(null);
									} else {}
									vehicleHeight = null;
								}
								if (newVal.openVehicle === 'N') {
									var materialObj = formObj.findField('material_RadioGroup');
									if (!Ext.isEmpty(materialObj)) {
										materialObj.show().setDisabled(false);
										var material = formObj.findField('material');
										if (!Ext.isEmpty(material)) {
											var val = material.getGroupValue();
											if (Ext.isEmpty(val)) {
												material.setValue('N');
											} else {
												material.setValue(val);
											}
											val = null;
										} else {}
										material = null;
									} else {}
									materialObj = null;

									var railVehicleObj = formObj.findField('railVehicle_RadioGroup');
									if (!Ext.isEmpty(railVehicleObj)) {
										railVehicleObj.hide().setDisabled(true);
									} else {}
									railVehicleObj = null;

									var selfVolume = formObj.findField('selfVolume');
									if (!Ext.isEmpty(selfVolume)) {
										//selfVolume.setDisabled(false);
										selfVolume.setValue(null);
									} else {}
									selfVolume = null;

									var vehicleHeight = formObj.findField('vehicleHeight');
									if (!Ext.isEmpty(vehicleHeight)) {
										//vehicleHeight.setDisabled(false);
										vehicleHeight.setValue(null);
									} else {}
									
									vehicleHeight = null;
								}
							}
							formObj = null;
						}
					}
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwhetherGaolanCar'),//是否高栏车
					name : 'railVehicle_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					hidden : true,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwhetherTheGaolanCanNotEmpty'),//是否高栏车不能为空D
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.yes'),
							name : 'railVehicle',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.no'),
							name : 'railVehicle',
							inputValue : 'N'
						}
					]
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRcarCabinetMaterial'),//车柜材质
					name : 'material_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRcarCabinetMaterialCanNotEmpty'),//车柜材质不能为空
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRcupboard'),//铁皮柜
							name : 'material',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRwitrine'),//玻璃柜
							name : 'material',
							inputValue : 'N'
						}
					]
				}, {
					/*
					 * 这个是公共选择器
					 */
					xtype : 'commonlineselector',
					name : 'bargainRoute',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTcontractLine'),//合同线路
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTcontractLineCanNotEmpty'),//合同线路不能为空
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
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 200,
					name : 'selfVolume',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRclearanceSide'),//净空（方）
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRclearanceCanNotEmpty'),//净空不能为空
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					minValue : 1,
					maxValue : 10,
					hideTrigger : false,
					name : 'vehicleHeight',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleHeightInMeters'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleHeightCanNotEmpty'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 50,
					name : 'selfWeight',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTweightTons'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 20,
					name : 'vehicleLength',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleLengthM'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleLengthCanNotEmpty'),
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 10,
					name : 'vehicleWidth',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleWidthM'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleWidthCanNotEmpty'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					minValue : 1,
					maxValue : 100,
					hideTrigger : false,
					name : 'deadLoad',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.loadTons'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					/*
					 * 这个是公共选择器
					 */
					xtype : 'commonvehicletypeselector',
					vehicleSort : 'ownership_leased',
					name : 'vehicleLengthCode',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRmodles'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvehicleTypeCanNotEmpty'),
					readOnly : false,
					columnWidth : 0.5,
					editable : false,
					displayField : 'vehicleLengthName',
					valueField : 'vehicleLengthCode', // 值
					queryParam : 'vehicleTypeEntity.vehicleLengthCode',
					listeners : {
						beforerender : function (me, eOpts) {
							//							if(Ext.isEmpty(newValue)){
							//								return;
							//							}
							me.store.load({
								params : {
									start : 0,
									limit : 1,
									'vehicleTypeEntity.vehicleLengthCode' : null
								}
							});
						}
					}
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToriginVesting'),
					name : 'producingArea_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTorginAttributionCanNotEmpty'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdomestic'),
							name : 'producingArea',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTimports'),
							name : 'producingArea',
							inputValue : 'N'
						}
					]
				}, {
					xtype : 'combobox',
					name : 'brand',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTbrand'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTbrandCanNotEmpty'),
					columnWidth : 0.5,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_leasedTrailer_BridgeStore_Id')
				}
			]
		}, {
			// 测量人员信息
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRtrailerSurveyCrew'),
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
					xtype : 'commonemployeemultiselector',
					status : 'Y',
					name : 'measureEmployeeCode',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureEmployee'),//测量员工
					allowBlank : false,
					hidden : true,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureEmployeeCanNotEmpty'),//测量员工不能为空！
					width:280,
					listeners:{
						select : function(me,vaO,vaN){
							var array = me.getValue()
							if(array.length!=0){
							Ext.Ajax.request({
								url : baseinfo.realPath('judgeTitleByEmpcode.action'),
								jsonData : {
									'leasedVehicleVo' : {
										'empCode' : array[array.length-1],
										'empNum':'0'
										
									}
								},
								success : function (response) {
									var json = Ext.decode(response.responseText);
									var str=json.leasedVehicleVo.returnYorN
									if(str!="Y"){
										array.pop();
										me.setValue(array);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : '只能选择员工',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
												});
									}
									if(array.length>6){
										//me.markInvalid('选择不能超过6个');
										array.pop();
										me.setValue(array);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : '选择不能超过6个！',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
												});
									}
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '温馨提示',
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
											});
								}
							});
							}
				        	
				         }
				    }
				}, {
					xtype : 'commonemployeemultiselector',
					status : 'Y',
					name : 'measureManagerCode',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureManager'),//测量经理
					allowBlank : false,
					hidden : true,
					//queryAllFlag:false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureManagerCanNotEmpty'),//测量经理不能为空！
					width:280,
					listeners:{
						select : function(me,vaO,vaN){
							var array = me.getValue()
							if(array.length!=0){
							Ext.Ajax.request({
								url : baseinfo.realPath('judgeTitleByEmpcode.action'),
								jsonData : {
									'leasedVehicleVo' : {
										'empCode' : array[array.length-1],
										'empNum':'1'
										
									}
								},
								success : function (response) {
									var json = Ext.decode(response.responseText);
									var str=json.leasedVehicleVo.returnYorN
									if(str!="Y"){
										array.pop();
										me.setValue(array);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : '只能选择经理',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
												});
									}
									if(array.length>3){
										//me.markInvalid('选择不能超过6个');
										array.pop();
										me.setValue(array);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : '选择不能超过3个！',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
												});
									}
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '温馨提示',
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
											});
								}
							});
							}
						}
				    }
				}, {
					xtype : 'commonemployeemultiselector',
					status : 'Y',
					name : 'measureSeniorManagerCode',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureSeniorManager'),//测量经理
					allowBlank : false,
					hidden : true,
					//queryAllFlag:false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureSeniorManagerCanNotEmpty'),//测量经理不能为空！
					width:280,
					listeners:{
						select : function(me,vaO,vaN){
							var array = me.getValue()
							if(array.length!=0){
							Ext.Ajax.request({
								url : baseinfo.realPath('judgeTitleByEmpcode.action'),
								jsonData : {
									'leasedVehicleVo' : {
										'empCode' : array[array.length-1],
										'empNum':'2'
										
									}
								},
								success : function (response) {
									var json = Ext.decode(response.responseText);
									var str=json.leasedVehicleVo.returnYorN
									if(str!="Y"){
										array.pop();
										me.setValue(array);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : '只能选择高级经理',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
												});
									}
									if(array.length>3){
										//me.markInvalid('选择不能超过6个');
										array.pop();
										me.setValue(array);
										Ext.MessageBox.show({
											title : '温馨提示',
											msg : '选择不能超过3个！',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
												});
									}
								},
								exception : function (response) {
									var json = Ext.decode(response.responseText);
									Ext.MessageBox.show({
										title : '温馨提示',
										msg : json.message,
										width : 300,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
											});
								}
							});
							}
				        	
				         }
				    }
				},{
					name : 'measureEmployeeName1',
					fieldLabel : 'measureEmployeeName1',
					readOnly : true,
					hidden : true,
					columnWidth : 0.5
				}, {
					name : 'measureEmployeeCode1',
					fieldLabel : 'measureEmployeeCode1',
					readOnly : true,
					hidden : true,
					columnWidth : 0.5
				}, {
					name : 'measureManagerName1',
					fieldLabel : 'measureManagerName1',
					readOnly : true,
					hidden : true,
					columnWidth : 0.5
				}
			,{
				name : 'measureManagerCode1',
				fieldLabel : 'measureManagerCode1',
				readOnly : true,
				hidden : true,
				columnWidth : 0.5
			}, {
				name : 'measureSeniorManagerName1',
				fieldLabel : 'measureSeniorManagerName1',
				readOnly : true,
				hidden : true,
				columnWidth : 0.5
			}, {
				name : 'measureSeniorManagerCode1',
				fieldLabel : 'measureSeniorManagerCode1',
				readOnly : true,
				hidden : true,
				columnWidth : 0.5
			}
			]
		}, {
			// Fieldset in Column 2 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRtrailerCertificateInformation'),
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingCardNumber'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperationCardNumberCanNotEmpty'),
					maxLength : 50,
					columnWidth : 0.5,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingLicNotContSpeChar'),
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
						 * 模拟选择器：失去焦点获取外请挂车信息
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
									//检测baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingCardNumber')是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtheOperationCardMessage') + leasedVehicle.vehicleNo + baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtheOperationCardMessageAlreadyExists');
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
											title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingPermitEffectiveStartDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperationStartDateCanNotEmpty'),
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
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperationStartDateEqualEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTToperationStartDateAfterEndDate');
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperatingPermitEffectiveEndDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LT.operationEndDateCanNotEmpty'),
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
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperationEndDateEqualStartDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LToperationEndDateBeforeStartDate');
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingLicense'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardCanNotEmpty'),
					maxLength : 50,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedTrailer.i18n('foss.baseinfo.baseinfo-leasedTractors.79'),
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
						 * 模拟选择器：失去焦点获取外请挂车信息
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
									//检测是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardMessage') + leasedVehicle.vehicleNo + baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtheOperationCardMessageAlreadyExists');
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
											title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingPermitValidStartDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardStartDateCanNotEmpty'),
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
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardStartDateEqualEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardStartDateAfterEndDate');
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTdrivingPermitValidEndDate'),
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardEndDateCanNotEmpty'),
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
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardStartDateEqualEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtravelingCardStartDateBeforeEndDate');
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardNumber'),
					readOnly : false,
					maxLength : 50,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedTrailer.i18n('foss.baseinfo.baseinfo-leasedTrailer.86'),
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
						 * 模拟选择器：失去焦点获取外请挂车信息
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
									//检测baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardNumber')是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardAlreadyExists') + leasedVehicle.vehicleNo + baseinfo.leasedTrailer.i18n('foss.baseinfo.LTtheOperationCardMessageAlreadyExists');
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
											title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
										title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
					name : 'beginTimeInsureCard',
					readOnly : false,
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardValidStartDate'),
					columnWidth : 0.5,
//					blankText : '保险卡有效开始日期不能为空！',
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
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardStartDateEqualEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardStartDateAfterEndDate');
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardValidEndDate'),
					columnWidth : 0.5,
					format : 'Y-m-d',
//					blankText : '保险卡有效结束日期不能为空！',
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
									me.validatorMessage.message = baseinfo.leasedTrailer.i18n('foss.baseinfo.LTinsuranceCardStartDateEqualEndDate');
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
							}
						}
					}
				}
			]
		}, {
			// Fieldset in Column 3 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRtrailerOwnerInformation'),
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTnameOfOwner'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTownerNameCanNotEmpty'),
					maxLength : 50,
					columnWidth : 0.5
				}, {
					name : 'contactPhone',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTownerPhone'),
					maxLength : 50,
					regex: /(^\d{11}$)/,
					regexText : baseinfo.leasedTrailer.i18n('foss.baseinfo.baseinfoTelphoneError'),
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'commonleaseddriverselector',
					status : 'Y',
					name : 'leasedDriverIdCard',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.ownDriversInformation'),//所属司机
					allowBlank : false,
					queryAllFlag:false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.ownDriversInformationCanNotEmpty'),//所属司机不能为空！
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.airagencycompany.linkAddress'),
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
			title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRtrailerRemarks'),
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
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.airagencycompany.remark'),
					colspan : 3,
					maxLength : 500,
					width : 940
				}, {
					name : 'vehicleType',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTvehicleType'),
					readOnly : true,
					hidden : true,
					columnWidth : 0.5,
					colspan : 2,
					value : 'vehicletype_trailer'
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

//新增、修改窗口
Ext.define('Foss.baseinfo.leasedTrailer.LeasedTrailerAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRaddModifyTrailerInformation'),
	width : 1100,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.leasedTrailer.LeasedTrailerAddUpdateForm');
		}
		return this.addUpdateForm;
	},
	loadAddUpdateForm : function (record) {
		this.addUpdateFormModel = record;
		this.getAddUpdateForm().getForm().loadRecord(record);
	},
	addUpdateFormModel : null,
	getAddUpdateFormModel : function () {
		if (null == this.addUpdateFormModel) {
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.leasedTrailer.LeasedTrailerModel");
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
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.cancel'),
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
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.reset'),
						handler : function () {
							if (me.getWindowAction() === 'upd') {
								me.loadAddUpdateForm(new Foss.baseinfo.leasedTrailer.LeasedTrailerModel(me.getAddUpdateFormModel().data));
							}
							if (me.getWindowAction() === 'add') {
								var form = me.getAddUpdateForm().getForm().reset();
								//敞篷、高栏、材质联动默认值
								form.findField('plat').setValue('N');
								form.findField('openVehicle').setValue('N');
								form.findField('material').setValue('N');
								//合同车、产地默认值
								form.findField('bargainVehicle').setValue('N');
								form.findField('producingArea').setValue('Y');
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
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.save'),
						plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									//修改前净空的值
									var oldSelfVolume =me.getAddUpdateFormModel().data.selfVolume;
									//刷新数据Model
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());
									//修改后净空的值
									var newSelfVolume =me.getAddUpdateFormModel().data.selfVolume;
									var isNotUpdateSelfVolume='N';//默认净空没有修改
									if(oldSelfVolume!=newSelfVolume){
									 isNotUpdateSelfVolume='Y';
									}
									me.getAddUpdateFormModel().set('isNotUpdateSelfVolume', isNotUpdateSelfVolume);
									var measureEmployeeCode = addUpdateForm.findField('measureEmployeeCode1').value;
									var measureEmployeeName = addUpdateForm.findField('measureEmployeeName1').value;
									var measureManagerName = addUpdateForm.findField('measureManagerName1').value;
									var measureManagerCode = addUpdateForm.findField('measureManagerCode1').value;
									var measureSeniorManagerName = addUpdateForm.findField('measureSeniorManagerName1').value;
									var measureSeniorManagerCode = addUpdateForm.findField('measureSeniorManagerCode1').value;
									
									if(measureEmployeeCode==null||measureEmployeeCode==''){
										var measureEmployeeCode = addUpdateForm.findField('measureEmployeeCode').lastValue;
										var measureEmployeeName = addUpdateForm.findField('measureEmployeeCode').rawValue;
										var measureManagerName = addUpdateForm.findField('measureManagerCode').rawValue;
										var measureManagerCode = addUpdateForm.findField('measureManagerCode').lastValue;
										var measureSeniorManagerName = addUpdateForm.findField('measureSeniorManagerCode').rawValue;
										var measureSeniorManagerCode = addUpdateForm.findField('measureSeniorManagerCode').lastValue;
										
									}
									me.getAddUpdateFormModel().set('measureEmployeeCode', measureEmployeeCode);
									me.getAddUpdateFormModel().set('measureEmployeeName', measureEmployeeName);
									me.getAddUpdateFormModel().set('measureManagerName', measureManagerName);
									me.getAddUpdateFormModel().set('measureManagerCode', measureManagerCode);
									me.getAddUpdateFormModel().set('measureSeniorManagerName', measureSeniorManagerName);
									me.getAddUpdateFormModel().set('measureSeniorManagerCode', measureSeniorManagerCode);
									
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
									var openVehicle = addUpdateForm.findField('openVehicle');
									if (!Ext.isEmpty(openVehicle)) {
										if (openVehicle.getGroupValue() === 'Y') {
											var material = addUpdateForm.findField('material_RadioGroup');
											if (!Ext.isEmpty(material)) {
												if (material.isDisabled()) {
													me.getAddUpdateFormModel().set('material', null);
												} else {}
											} else {
												return false;
											}
											material = null;
										} else {}
										if (openVehicle.getGroupValue() === 'N') {
											var railVehicle = addUpdateForm.findField('railVehicle_RadioGroup');
											if (!Ext.isEmpty(railVehicle)) {
												if (railVehicle.isDisabled()) {
													me.getAddUpdateFormModel().set('railVehicle', null);
												} else {}
											} else {
												return false;
											}
											railVehicle = null;
										} else {}
									} else {
										return false;
									}
									openVehicle = null;
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
											Ext.getCmp('T_baseinfo-leasedTrailer_content').getLeasedTrailerGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.leasedTrailer.i18n('foss.baseinfo.successPrompt'),
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
												title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												callback : function () {
													me.getAddUpdateForm().up('window').hide();
												},
												icon : Ext.MessageBox.WARNING
											});
											addUpdateForm = null;
										}
									});
								} else {
									top.Ext.MessageBox.show({
										title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
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
												if (value.name === 'railVehicle_RadioGroup') {
													var railVehicle = addUpdateForm.findField('railVehicle');
													if (!Ext.isEmpty(railVehicle.getValue())) {
														value.clearInvalid();
														return false;
													}
													railVehicle = null;
												}
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
		}
	},
	beforehide : function (me, eOpts) {
		me.getAddUpdateForm().getForm().reset();
	},
	beforeclose : function (me, eOpts) {
		me.fireEvent('beforehide', me);
	}
});

//修改净空窗口——232607——2015/10/13
Ext.define('Foss.baseinfo.leasedTrailer.UpdateSelfVolumeWindow', {
	extend : 'Ext.window.Window',
	title : '修改净空',
	resizable : false,//允许用户修改窗口大小
	closeAction : 'hide',//关闭窗口并不销毁窗口，而是隐藏它
	//修改净空表格
	updateSelfVolumeForm : null,
	getUpdateSelfVolumeForm : function () {
		if (null == this.updateSelfVolumeForm) {
			this.updateSelfVolumeForm = Ext.create('Foss.baseinfo.leasedTrailer.UpdateSelfVolumeForm');
		}
		return this.updateSelfVolumeForm;
	},
	//用于保存某一条车辆信息记录，再点击修改净空时将记录存进来，用于重置
	vehicleRecord:null,
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getUpdateSelfVolumeForm()];
		me.fbar = [{
						xtype : 'button',
						columnWidth : .2,
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.cancel'),
						handler : function () {
							me.close();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;',
						hidden:true
					}, {
						xtype : 'button',
						columnWidth : .2,
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.reset'),
						handler : function () {
							me.getUpdateSelfVolumeForm().getForm().loadRecord(me.vehicleRecord);
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;',
						hidden:true
					}, {
						columnWidth : .2,
						xtype : 'button',
						cls : 'yellow_button',
						text : baseinfo.leasedTrailer.i18n('foss.baseinfo.save'),
						plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var updateSelfVolumeForm = me.getUpdateSelfVolumeForm().getForm();
								if (updateSelfVolumeForm.isValid()) { //校验FORM是否通过校验
									var record=new Foss.baseinfo.leasedTrailer.LeasedTrailerModel();
									var measureEmployeeCode = updateSelfVolumeForm.findField('measureEmployeeCode').lastValue;
									var measureEmployeeName = updateSelfVolumeForm.findField('measureEmployeeCode').rawValue;
									var measureManagerName = updateSelfVolumeForm.findField('measureManagerCode').rawValue;
									var measureManagerCode = updateSelfVolumeForm.findField('measureManagerCode').lastValue;
									var measureSeniorManagerName = updateSelfVolumeForm.findField('measureSeniorManagerCode').rawValue;
									var measureSeniorManagerCode = updateSelfVolumeForm.findField('measureSeniorManagerCode').lastValue;
									updateSelfVolumeForm.updateRecord(record);
									record.set('measureEmployeeCode', measureEmployeeCode);
									record.set('measureEmployeeName', measureEmployeeName);
									record.set('measureManagerName', measureManagerName);
									record.set('measureManagerCode', measureManagerCode);
									record.set('measureSeniorManagerName', measureSeniorManagerName);
									record.set('measureSeniorManagerCode', measureSeniorManagerCode);
									var selfVolumeBack = record.data.selfVolume;
									var selfVolumeFront = updateSelfVolumeForm.findField('selfVolumeHide').getValue();
									var range = Math.abs(selfVolumeBack-selfVolumeFront);
									if(range/selfVolumeFront>0.05){
										Ext.MessageBox.show({
											title : baseinfo.leasedTrailer.i18n('foss.baseinfo.confirmationPrompt'),
											msg : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRvoidReportedErrorsConfirmMessage'),
											buttons : Ext.Msg.YESNO,
											icon : Ext.MessageBox.QUESTION,
											fn : function (btn) {
												if (btn == 'yes') {
													//执行数据请求
													Ext.Ajax.request({
														url : baseinfo.realPath('updateSelfVolume.action'),
														jsonData : {
															'leasedVehicleVo' : {
																'leasedVehicle' : record.data
															}
														},
														success : function (response) {
															var json = Ext.decode(response.responseText);
															Ext.getCmp('T_baseinfo-leasedTrailer_content').getLeasedTrailerGrid().store.load();
															Ext.MessageBox.show({
																title : baseinfo.leasedTrailer.i18n('foss.baseinfo.successPrompt'),
																msg : json.message,
																width : 300,
																buttons : Ext.Msg.OK,
																callback : function () {
																	me.hide();
																},
																icon : Ext.MessageBox.INFO
															});
														},
														exception : function (response) {
															var json = Ext.decode(response.responseText);
															Ext.MessageBox.show({
																title : baseinfo.leasedTrailer.i18n('foss.baseinfo.failureInformationTips'),
																msg : json.message,
																width : 300,
																buttons : Ext.Msg.OK,
																callback : function () {
																	
																},
																icon : Ext.MessageBox.WARNING
															});
														}
													});
												}
											}
										});
										}else{
											Ext.MessageBox.show({
											title : '温馨提示',
											msg : '净空修改幅度不能小于5%！',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
											return false;
											}
									
									
									
								}
							}
						}
					}]
				me.callParent([cfg]);
				}
});

//修改净空表格——232607——2015/10/13
Ext.define('Foss.baseinfo.leasedTrailer.UpdateSelfVolumeForm', {
	extend : 'Ext.form.Panel',
	width : 440,
//	height : 200,
	frame: true,
	layout : {
		type : 'table',
		columns : 1
	},
	defaults : {
		margin : '25 10 13 40',
		labelWidth:100,
		width: 300
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items=[{
					xtype : 'textfield',
					name : 'vehicleNo',
					fieldLabel : '车牌号',
					readOnly : true
				},{
					xtype : 'numberfield',
					minValue : 0,
					maxValue : 999,
					allowDecimals:false,
					name : 'selfVolume',
					fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRclearanceSide'),//净空（方）
					allowBlank : false,
					blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.LTRclearanceCanNotEmpty'),//净空不能为空
					maxLength : 20
		},{
			xtype : 'commonemployeemultiselector',
			status : 'Y',
			name : 'measureEmployeeCode',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureEmployee'),//测量员工
			allowBlank : false,
			blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureEmployeeCanNotEmpty'),//测量员工不能为空！
			width:280,
			listeners:{
				select : function(me,vaO,vaN){
					var array = me.getValue()
					if(array.length!=0){
					Ext.Ajax.request({
						url : baseinfo.realPath('judgeTitleByEmpcode.action'),
						jsonData : {
							'leasedVehicleVo' : {
								'empCode' : array[array.length-1],
								'empNum':'0'
								
							}
						},
						success : function (response) {
							var json = Ext.decode(response.responseText);
							var str=json.leasedVehicleVo.returnYorN
							if(str!="Y"){
								array.pop();
								me.setValue(array);
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '只能选择员工',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
										});
							}
							if(array.length>6){
								//me.markInvalid('选择不能超过6个');
								array.pop();
								me.setValue(array);
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '选择不能超过6个！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
										});
							}
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
									});
						}
					});
					}
		        	
		         }
		    }
		}, {
			xtype : 'commonemployeemultiselector',
			status : 'Y',
			name : 'measureManagerCode',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureManager'),//测量经理
			allowBlank : false,
			//queryAllFlag:false,
			blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureManagerCanNotEmpty'),//测量经理不能为空！
			width:280,
			listeners:{
				select : function(me,vaO,vaN){
					var array = me.getValue()
					if(array.length!=0){
					Ext.Ajax.request({
						url : baseinfo.realPath('judgeTitleByEmpcode.action'),
						jsonData : {
							'leasedVehicleVo' : {
								'empCode' : array[array.length-1],
								'empNum':'1'
								
							}
						},
						success : function (response) {
							var json = Ext.decode(response.responseText);
							var str=json.leasedVehicleVo.returnYorN
							if(str!="Y"){
								array.pop();
								me.setValue(array);
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '只能选择经理',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
										});
							}
							if(array.length>3){
								//me.markInvalid('选择不能超过6个');
								array.pop();
								me.setValue(array);
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '选择不能超过3个！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
										});
							}
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
									});
						}
					});
					}
				}
		    }
		}, {
			xtype : 'commonemployeemultiselector',
			status : 'Y',
			name : 'measureSeniorManagerCode',
			fieldLabel : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureSeniorManager'),//测量经理
			allowBlank : false,
			//queryAllFlag:false,
			blankText : baseinfo.leasedTrailer.i18n('foss.baseinfo.MeasureSeniorManagerCanNotEmpty'),//测量经理不能为空！
			width:280,
			listeners:{
				select : function(me,vaO,vaN){
					var array = me.getValue()
					if(array.length!=0){
					Ext.Ajax.request({
						url : baseinfo.realPath('judgeTitleByEmpcode.action'),
						jsonData : {
							'leasedVehicleVo' : {
								'empCode' : array[array.length-1],
								'empNum':'2'
								
							}
						},
						success : function (response) {
							var json = Ext.decode(response.responseText);
							var str=json.leasedVehicleVo.returnYorN
							if(str!="Y"){
								array.pop();
								me.setValue(array);
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '只能选择高级经理',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
										});
							}
							if(array.length>3){
								//me.markInvalid('选择不能超过6个');
								array.pop();
								me.setValue(array);
								Ext.MessageBox.show({
									title : '温馨提示',
									msg : '选择不能超过3个！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
										});
							}
						},
						exception : function (response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.show({
								title : '温馨提示',
								msg : json.message,
								width : 300,
								buttons : Ext.Msg.OK,
								icon : Ext.MessageBox.WARNING
									});
						}
					});
					}
		        	
		         }
		    }
		},{
			xtype : 'textfield',
			name : 'selfVolumeHide',
			fieldLabel : '修改前净空值',
			maxLength : 20,
			hidden:true
		}
	];
		me.callParent([cfg]);
	}
});
	
	
/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.leasedTrailer.QueryForm');
	/*
	 * 创建查询baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.leasedTrailer.LeasedTrailerGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-leasedTrailer').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedTrailer_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询baseinfo.leasedTrailer.i18n('foss.baseinfo.LVTtrailer')结果列表结果窗口
		getLeasedTrailerGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
//		renderTo : 'T_baseinfo-leasedTrailer-body'
	}));
});
