/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义"外请厢式车"列表查询条件窗口 *************************************************/
baseinfo.regCompCodeLimit = {vehicleNo:/^([\u4e00-\u9fa5]|[a-z]|[A-Z]|[0-9])+$/};
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
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
Ext.define('Foss.baseinfo.leasedVan.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.queryCondition'),
	id : 'Foss_baseinfo_leasedVan_QueryForm_Id',
	frame : true,
	collapsible : true,
	defaults : {
		labelSeparator : ':',
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 210,
	bodyCls: 'autoHeight',
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleNo'),
			columnWidth : .30
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.operatingLic'),
			columnWidth : .30
		}, {
			name : 'leasedDriverIdCard',   //司机身份证
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRDriverIdCard'),
			columnWidth : .40
		}, {
			name : 'driverPhone',    //司机电话
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRDriverTelphone'),
			columnWidth : 0.3
		}, {
			name : 'leasedDriverName',    //司机姓名
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRDriverName'),
			columnWidth : 0.3
		}, {
			xtype : 'combobox',
			name : 'bargainVehicle',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherContract'),
			columnWidth : 0.4,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectAll')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			}),
			value : ''
		},  {
			xtype : 'combobox',
			name : 'gps',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.toLoadGPS'),
			columnWidth : 0.3,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectAll')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo')
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
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LTRmodles'),
			columnWidth : 0.3,
			displayField : 'vehicleLengthCode'
			//displayField : 'vehicleLength'
		},{
			xtype:'combobox',
			name:'active',
			fieldLabel:baseinfo.leasedVan.i18n('foss.baseinfo.passwordReset.active'), //是否有效
			columnWidth:0.3,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			value:'Y',
			store:FossDataDictionary.getDataDictionaryStore('TFR_IS_ON_SCHEDULING',null,{
				'valueCode':'',
				'valueName':baseinfo.leasedVan.i18n('foss.baseinfo.all')
			})
		}],
	buttons:[{
				xtype : 'button',
				margin : '0 810 0 0',
				text : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.resetBtn'),
				disabled:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanQueryButton'),
				hidden:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanQueryButton'),
				handler : function () {
					Ext.getCmp('Foss_baseinfo_leasedVan_QueryForm_Id').getForm().reset();
				}
			}, {
				xtype : 'button',
				cls : 'yellow_button',
				text : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.queryBtn'),
				disabled:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanQueryButton'),
				hidden:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanQueryButton'),
				handler : function () {
					Ext.getCmp('Foss_baseinfo_leasedVan_LeasedVanGrid_Id').getPagingToolbar().moveFirst();
				}
	}],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*******************************************************  定义"厢式车"列表结果窗口 *****************************************************/
/*
 * 定义：一个"厢式车"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.leasedVan.LeasedVanModel', {
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
			name : 'vehicleLength', //车长
			type : 'float'
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
			name : 'vehicleWidth', //车宽
			type : 'float'
		}, {
			name : 'selfVolume', //净空
			type : 'float'
		}, {
			name : 'vehicleHeight', //车高
			type : 'float'
		}, {
			name : 'bargainVehicle', //是否合同车
			type : 'string'
		}, {
			name : 'selfWeight', //自重
			type : 'float',
			convert:function(value){
				if(0==value){
					return null;
				}else{
					return value;
				}
			}
		}, {
			name : 'deadLoad', //载重
			type : 'float',
			convert:function(value){
				if(0==value){
					return null;
				}else{
					return value;
				}
			}	
		}, {
			name : 'bargainRoute', //合同线路
			type : 'string'
		}, {
			name : 'vehicleLengthCode', //车辆车型编码
			type : 'string'
		}, {
			name : 'brand', //品牌
			type : 'string'
		}, {
			name : 'tailBoard', //是否有尾板
			type : 'string'
		}, {
			name : 'gps', //是否装GPS
			type : 'string'
		}, {
			name : 'gpsProvider', //GPS供应商
			type : 'string'
		}, {
			name : 'producingArea', //产地（进口/出口）
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
			name : 'vehicleType', //车辆类型（厢式车/厢式车/厢式车）
			type : 'string',
			defaultValue : 'vehicletype_van'
		}, {
			name : 'leasedDriverIdCard', //司机身份证号
			type : 'string'
		}, {
			name : 'driverPhone', //司机电话
			type : 'string'
		}, {
			name : 'whiteStatus', //白名单状态
			type : 'string'
		}, {
			name : 'approveUser', //白名单审核人
			type : 'string'
		}, {
			name : 'approveTime', //白名单审核时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'leasedDriverName', //司机姓名
			type : 'string'
		}, {
			name : 'approveUserName', //白名单审核人姓名
			type : 'string'
		},{
			name:'createUser', //创建人
			type:'string'
		},{
			name:'createUserName', //创建人姓名
			type:'string'
		},{
			name:'createDate', //创建时间
			type:'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}	
		},{
			name:'modifyUser',//修改人
			type:'string'
		},{
			name:'modifyUserName',//修改人姓名
			type:'string'
		},{
			name:'modifyDate', //修改时间
			type:'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		},{
			name:'active',//是否有效
			type:'string'
		},{
			name:'createUserDept', //创建人部门
			type:'string'
		},{
			name:'modifyUserDept',//修改人部门
			type:'string'
		},{
			name:'createUserDeptName', //创建人部门名称
			type:'string'
		},{
			name:'modifyUserDeptName',//修改人部门名称
			type:'string'
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
			name : 'measureEmployeeName', //测量人员姓名
			type : 'string'
		}, {
			name : 'measureManagerName', //测量经理姓名
			type : 'string'
		},{
			name :'measureSeniorManagerName', //测量高级经理姓名
			type :'string'
		}
	]
});

/*
 * 定义：一个"厢式车"的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.leasedVan.LeasedVanStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.leasedVan.LeasedVanModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryLeasedVanList.action'),
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
			var queryForm = Ext.getCmp('Foss_baseinfo_leasedVan_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'leasedVehicleVo.leasedVehicle.vehicleNo' : queryParams.vehicleNo,
						'leasedVehicleVo.leasedVehicle.operatingLic' : queryParams.operatingLic,
						'leasedVehicleVo.leasedVehicle.gps' : queryParams.gps,
						'leasedVehicleVo.leasedVehicle.vehicleLengthCode' : queryParams.vehicleLengthCode,
						'leasedVehicleVo.leasedVehicle.bargainVehicle' : queryParams.bargainVehicle,
						'leasedVehicleVo.leasedVehicle.leasedDriverIdCard' : queryParams.leasedDriverIdCard,
						'leasedVehicleVo.leasedVehicle.leasedDriverName' : queryParams.leasedDriverName,
						'leasedVehicleVo.leasedVehicle.driverPhone' : queryParams.driverPhone,
						'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_van', //"厢式车"数据字典常量
						'leasedVehicleVo.leasedVehicle.active' : queryParams.active
					}
				});
			}
		}
	}
});

/*
 * 定义："厢式车"内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.leasedVan.LeasedVanDetailForm', {
	extend : 'Ext.form.Panel',
	height : 570,
	width : 1000,
	defaults : {
		margin : '5 15 5 15',
		labelWidth : 125
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			name : 'vehicleNo',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleNo'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTime',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.releaseDate'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d'
		}, {
			xtype : 'datefield',
			name : 'endTime',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.dateOfRetirement'),
			readOnly : true,
			columnWidth : 0.5,
			format : 'Y-m-d'
		}, {
			name : 'engineNo',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.motorNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleFrameNo',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.chaissisNumber'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleLength',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleLength'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'openVehicle',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherOpenCar'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'railVehicle',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherHighhurdleCar'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'material',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.material'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.ironhide')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.glasses')
						}
					]
				}
			})
		}, {
			name : 'vehicleWidth',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleWidth'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'selfVolume',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selfVolume'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'vehicleHeight',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleHeight'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'bargainVehicle',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherContract'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			})
		}, {
			name : 'deadLoad',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.deadLoad'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'selfWeight',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selfWeight'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			//公共选择器
			xtype : 'commonlineselector',
			name : 'bargainRoute',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.bargainRoute'),
			readOnly : true,
			width : 300,
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
			xtype : 'commonvehicletypeselector',
			vehicleSort : 'ownership_leased',
			name : 'vehicleLengthCode',
			fieldLabel : '车型',
			readOnly : true,
			columnWidth : 0.5,
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
			name : 'brand',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.brand'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_leasedVan_BridgeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			xtype : 'combobox',
			name : 'tailBoard',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.whetherTailgate'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'gps',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.toLoadGPS'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'producingArea',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.producingArea'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : Ext.create('Foss.baseinfo.leasedVan.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.madeInChina')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.import')
						}
					]
				}
			})
		}, {
			xtype : 'label'
		}, {
			name : 'gpsProvider',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.gpsProvider'),
			colspan : 3,
			maxlength : 1000,
			width : 970
		}, {
			name : 'measureEmployeeName',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureEmployee'),//测量员工
			columnWidth : 0.5,
			readOnly : true
		}, {
			name : 'measureManagerName',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureManager'),//测量经理
			columnWidth : 0.5,
			readOnly : true
		}, {
			name : 'measureSeniorManagerName',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureSeniorManager'),//测量经理
			columnWidth : 0.5,
			readOnly : true
		}, {
			name : 'operatingLic',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.operatingLic'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeOperatingLic'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeOperatingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeOperatingLic'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			name : 'drivingLicense',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.drivingLicense'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeDrivingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeDrivingLic'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeDrivingLic',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeDrivingLic'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			name : 'insureCard',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.insureCard'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'beginTimeInsureCard',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeInsureCard'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			xtype : 'datefield',
			name : 'endTimeInsureCard',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeInsureCard'),
			columnWidth : 0.5,
			format : 'Y-m-d',
			editable : false
		}, {
			name : 'contact',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contact'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactPhone',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contactPhone'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'leasedDriverName',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.ownDriversInformation'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'contactAddress',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contactAddress'),
			readOnly : true,
			columnWidth : 0.5
		},{
			name : 'driverPhone',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRDriverTelphone'),  //司机电话
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'leasedDriverIdCard',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRDriverIdCard'),  //司机身份证
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'combobox',
			name : 'status',
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRWSWhitelistStatus'),  //白名单状态
			readOnly : true,
			colspan : 3,
			//columnWidth : 0.5,
			/*store : FossDataDictionary.getDataDictionaryStore('BES_WHITELISTS_AUDIT', 'Foss_baseinfo_leasedVehicleWhitelists_StatusStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			}),*/
			listeners:{
				change:function(_this,new_v,old_v,eOpts){
					if(Ext.isEmpty(new_v)){
						return;
					}
					if(new_v =='Y'){
						_this.setValue('可用');
					}else if(new_v =='N'){
						_this.setValue('不可用');
					}
				}
			}
		}, /*{
			name : 'approveUser',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSReviewer'),  //白名单审核人
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'datefield',
			name : 'approveTime',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSAuditTime'),  //白名单审核时间
			readOnly : true,
			format: 'Y-m-d H:i:s',
			columnWidth : 0.5
		}*/,{
			name : 'createUser',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.creator'),  //创建人
			readOnly : true,
			columnWidth : 0.5
		},{
			xtype : 'datefield',
			name : 'createDate',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.createTime'),  //创建时间
			readOnly : true,
			format: 'Y-m-d H:i:s',
			columnWidth : 0.5
		},{
			name:'createUserDeptName',
			fieldLabel : '创建部门',//baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSReviewer'),  //创建部门
			readOnly : true,
			columnWidth : 0.5
		}/*不用该方法了，直接获取创建部门名称，todo：方法可以借鉴
		{
			name : 'createUserDept',
			xtype:'dynamicorgcombselector',
			fieldLabel : '创建部门',//baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSReviewer'),  //创建部门
			readOnly : true,
			columnWidth : 0.5,
			type : 'ORG',
			queryParam : 'commonOrgVo.code',// 查询参数
			listeners:{
				change:function(_this,new_v,old_v,eOpts){
					if(Ext.isEmpty(new_v)){
						return;
					}
					//根据参数，查询所对应的值
					_this.store.load({
						params:{
							start:0,
							limit:1,
							'commonOrgVo.code':new_v
						}
					});
				}
			}
		}*/,{
			name : 'modifyUser',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.modifyUser'),  //修改人
			readOnly : true,
			columnWidth : 0.5
		},{
			xtype : 'datefield',
			name : 'modifyDate',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.modifyTime'),  //修改时间
			readOnly : true,
			format: 'Y-m-d H:i:s',
			columnWidth : 0.5
		},{
			name :'modifyUserDeptName',
			fieldLabel : '修改部门',//baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSReviewer'),  //修改部门
			readOnly : true,
			columnWidth : 0.5
		}/*{
			name : 'modifyUserDept',
			xtype:'dynamicorgcombselector',
			fieldLabel : '修改部门',//baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSReviewer'),  //修改部门
			readOnly : true,
			columnWidth : 0.5,
			type:'ORG',
			queryParam:'commonOrgVo.code',
			listeners:{
				change:function(_this,new_v,old_v,eOpts){
					if(Ext.isEmpty(new_v)){
						return;
					}
					_this.store.load({
						param:{
							start : 0,
							limit : 1,
							'commonOrgVo.code' : new_v
						}
					});
				}
			}
			
		}*/,{
			name :'active',
			fieldLabel :  baseinfo.leasedVan.i18n('foss.baseinfo.passwordReset.active'),// 是否有效
			readOnly : true,
			columnWidth : 0.5,
			listeners:{
				change:function(_this,new_v,old_v,eOpts){
					if(Ext.isEmpty(new_v)){
						return;
					}
					if(new_v =='Y'){
						_this.setValue('是');
					}else if(new_v =='N'){
						_this.setValue('否');
					}
				}
			}
		},{
			//xtype : 'textareafield',
			name : 'notes',
			readOnly : true,
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.notes'),
			//colspan : 3
			columnWidth : 0.5
		}
	]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.leasedVan.LeasedVanDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.LeasedVanDetailPanel'),
	frame : true,
	informationForm : null,
	/**
	 * 获取外请厢式车详细信息
	 * @return {Object} LeasedVanDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.leasedVan.LeasedVanDetailForm');
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
			}
			if (openVehicle && openVehicle === 'N') {
				formDOM.findField('railVehicle').hide().setDisabled(false);
				formDOM.findField('material').show();
			}
		}
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.leasedVan.LeasedVanGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_leasedVan_LeasedVanGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.LeasedVanGrid'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.leasedVan.LeasedVanDetailPanel' // 行体内容
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
			this.addUpdateWindow = Ext.create('Foss.baseinfo.leasedVan.LeasedVanAddUpdateWindow');
			
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addLeasedVehicle.action');
				}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateLeasedVehicle.action');
				}
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
				//合同车、产地、GPS、尾板默认值
				tempForm.findField('bargainVehicle').setValue('N');
				tempForm.findField('producingArea').setValue('Y');
				tempForm.findField('gps').setValue('N');
				tempForm.findField('tailBoard').setValue('N');
				//公共选择器联动
				tempForm.findField('bargainRoute').setValue(null);
				tempForm.findField('vehicleLengthCode').setValue(null);
				//测量人员
				tempForm.findField('measureEmployeeCode').setReadOnly(false);
				tempForm.findField('measureManagerCode').setReadOnly(false);
				tempForm.findField('measureSeniorManagerCode').setReadOnly(false);
				
				tempForm.findField('measureEmployeeCode').allowBlank=false;
				tempForm.findField('measureManagerCode').allowBlank=false;
				tempForm.findField('measureSeniorManagerCode').allowBlank=false;
				tempForm.findField('measureEmployeeCode').show(true);
				tempForm.findField('measureManagerCode').show(true);
				tempForm.findField('measureSeniorManagerCode').show(true);
			}
			if (actionType && actionType === 'upd') {
				tempForm.findField('vehicleNo').setReadOnly(true);
				tempForm.findField('selfVolume').setReadOnly(true);
				tempForm.findField('selfVolume').allowBlank=true;
				//敞篷、高栏、材质联动默认值
				tempForm.findField('material').setValue(null);
				tempForm.findField('railVehicle').setValue(null);
				tempForm.findField('openVehicle').setValue(null);
				//合同车、产地、GPS、尾板板默认值
				tempForm.findField('gps').setValue(null);
				tempForm.findField('tailBoard').setValue(null);
				tempForm.findField('producingArea').setValue(null);
				tempForm.findField('bargainVehicle').setValue(null);
				//测量人员
				tempForm.findField('measureEmployeeCode').setReadOnly(true);
				tempForm.findField('measureManagerCode').setReadOnly(true);
				tempForm.findField('measureSeniorManagerCode').setReadOnly(true);
				//测量人员
				tempForm.findField('measureEmployeeCode').allowBlank=true;
				tempForm.findField('measureManagerCode').allowBlank=true;
				tempForm.findField('measureSeniorManagerCode').allowBlank=true;
				tempForm.findField('measureEmployeeCode').hide(true);
				tempForm.findField('measureManagerCode').hide(true);
				tempForm.findField('measureSeniorManagerCode').hide(true);
			}
		}
		
		return this.addUpdateWindow;
	},
	//修改净空窗口
	updateSelfVolumeWindow:null,
	getUpdateSelfVolumeWindow:function(){
		if (this.updateSelfVolumeWindow == null) {
			this.updateSelfVolumeWindow = Ext.create('Foss.baseinfo.leasedVan.UpdateSelfVolumeWindow');
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
					plugins: Ext.create('Deppon.ux.PageSizePlugin', {
                    limitWarning:'最大查询记录不能超过'
                    }),
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
			text : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.operator'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.modify'),
					disabled:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanUpdateButton'),
					getClass:function(value,metadata,record,rowIndex,colIndex,store){
						//获取数据数是否有效
						var active =FossDataDictionary.rendererDisplayToSubmit(record.get('active'),'FOSS_ACTIVE');
						//无效的话，做隐藏操作
						if(active =='Y'){
							return 'deppon_icons_edit';
						}else{
							return 'deppon_icons_edit_hide';
						}
					},
					/**
					 * "修改"响应事件
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
					tooltip : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.delete'),
					disabled:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanCancelButton'),
					getClass:function(value,metadata,record,rowIndex,colIndex,store){
						//获取数据数是否有效
						var active =FossDataDictionary.rendererDisplayToSubmit(record.get('active'),'FOSS_ACTIVE');
						if(active =='Y'){
							return 'deppon_icons_delete';
						}else{
							return 'deppon_icons_delete_hide';
						}
					},
					/**
					 * "修改"响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.confirmPrompt'),
							msg : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherDeleteLease'),
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
										//"作废"成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgSuccessPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//"作废"失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
			header : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleNo'),
			dataIndex : 'vehicleNo',
			width : 105
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.ownDriversInformation'),  //所属司机
			dataIndex : 'leasedDriverName',
			width : 105
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.chaissisNumber'),
			dataIndex : 'vehicleFrameNo',
			width : 105
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.deadLoad'),
			dataIndex : 'deadLoad',
			width : 85
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selfWeight'),
			dataIndex : 'selfWeight',
			width : 85
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selfVolume'),
			dataIndex : 'selfVolume',
			width : 85
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleLength'),
			dataIndex : 'vehicleLength',
			width : 85
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.toLoadGPS'),
			dataIndex : 'gps',
			width : 80,
			renderer : function (value) {
				if (value === 'Y') {
					return baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes');
				} else if (value === 'N') {
					return baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo');
				} else {
					return '-';
				}
			}
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.operatingLic'),
			dataIndex : 'operatingLic',
			width : 105
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.drivingLicense'),
			dataIndex : 'drivingLicense',
			width : 105
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherContract'),
			dataIndex : 'bargainVehicle',
			width : 85,
			renderer : function (value) {
				if (value === 'Y') {
					return baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes');
				} else if (value === 'N') {
					return baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo');
				} else {
					return '-';
				}
			}
		},{
			header : baseinfo.leasedVan.i18n('foss.baseinfo.LDRDriverTelphone'),  //司机电话
			dataIndex : 'driverPhone',
			width : 105
		}, {
			header : baseinfo.leasedVan.i18n('foss.baseinfo.LDRDriverIdCard'),  //司机身份证
			dataIndex : 'leasedDriverIdCard',
			width : 105
		},{
			header : baseinfo.leasedVan.i18n('foss.baseinfo.LDRWSWhitelistStatus'),  //白名单状态
			dataIndex : 'status',
			width : 105,
			renderer : function (value) {
				//var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_WHITELISTS_AUDIT');
				if (Ext.isEmpty(value)) {
					return '-';
				} else if(value =='Y'){
					return '可用';
				}else{
					return '不可用'
				}
			}
		}, /*{
			header : baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSReviewer'),  //白名单审核人
			dataIndex : 'approveUserName',
			width : 105
		},{
			header : baseinfo.leasedVan.i18n('foss.baseinfo.LDRWhiteSAuditTime'),  //白名单审核时间
			dataIndex : 'approveTime',
			width : 145,
			renderer:function(v){
				if(!Ext.isEmpty(v)){
					return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
				}
				return v;
			}
		},*/{
			header : baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.creator'),//创建人
			dataIndex:'createUserName',
			width:145
		},{
			header : baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.createTime'),//创建时间
			dataIndex:'createDate',
			width:145,
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value),'Y-m-d H:i:s');
				}
			}
		},{
			header :  baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.modifyUser'),//修改人
			dataIndex :'modifyUserName',
			width:145
		},{
			header :  baseinfo.leasedVan.i18n('foss.baseinfo.downLoadExcelFileInfo.modifyTime'),//修改时间
			dataIndex : 'modifyDate',
			width:145,
			renderer:function(value){
				if(Ext.Date.format(new Date(value),'Y-m-d H:i:s')> Ext.Date.format(new Date(Date.parse(new Date())+1000*60*60*24), 'Y-m-d H:i:s')){
					return null;
				}
				if(!Ext.isEmpty(value)){
					return Ext.Date.format(new Date(value),'Y-m-d H:i:s');
				}
			}
		},{
			header : '创建部门',// baseinfo.leasedVan.i18n(''),
			dataIndex : 'createUserDeptName',
			width:145
		},{
			header :  '修改部门',//baseinfo.leasedVan.i18n(''),
			dataIndex : 'modifyUserDeptName',
			width:145
		},{
			header : baseinfo.leasedVan.i18n('foss.baseinfo.passwordReset.active'),// 是否有效
			dataIndex : 'active',
			width:145,
			renderer:function(value){
				if(!Ext.isEmpty(value)){
					if (value === 'Y') {
						return baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes');
					} else if (value === 'N') {
						return baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo');
					} else {
						return '-';
					}
				}
			}
		}],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.leasedVan.LeasedVanStore');
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
						text : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.add'),
						disabled:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanAddButton'),
						hidden:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanAddButton'),
						width : 80,
						//弹出"新增"的外请厢式车的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.delete'),
						disabled:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanCancelButton'),
						hidden:!baseinfo.leasedVan.isPermission('leasedVan/leasedVanCancelButton'),
						width : 80,
						/**
						 * 作废外请厢式车
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									if(selectionRecord[i].data.active =='N'){
										Ext.MessageBox.show({
											title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
											msg : '由于包含无效的记录,不能批量作废！',
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.INFO
										});
										return;
									}
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.confirmPrompt'),
									msg : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherDeleteLease'),
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
												//"作废"成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgSuccessPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//"作废"失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
									title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
									msg : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.noRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
					},{
						xtype:'button',
						text :baseinfo.leasedVan.i18n('foss.baseinfo.export'),//导出
						width:80,
						handler:function(){
							var store =me.store;
							var queryForm = Ext.getCmp('Foss_baseinfo_leasedVan_QueryForm_Id').getForm();
							var queryParams = queryForm.getValues();
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
										'leasedVehicleVo.leasedVehicle.gps' : queryParams.gps,
										'leasedVehicleVo.leasedVehicle.vehicleLengthCode' : queryParams.vehicleLengthCode,
										'leasedVehicleVo.leasedVehicle.bargainVehicle' : queryParams.bargainVehicle,
										'leasedVehicleVo.leasedVehicle.leasedDriverIdCard' : queryParams.leasedDriverIdCard,
										'leasedVehicleVo.leasedVehicle.leasedDriverName' : queryParams.leasedDriverName,
										'leasedVehicleVo.leasedVehicle.driverPhone' : queryParams.driverPhone,
										'leasedVehicleVo.leasedVehicle.vehicleType' : 'vehicletype_van', //"厢式车"数据字典常量
										'leasedVehicleVo.leasedVehicle.active':queryParams.active
									},
									isUpload:true
								});
							}else{
								Ext.MessageBox.show({
									title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
									msg : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.noRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								
							}
									
						}
					},{
						xtype : 'button',
						text : '修改净空',
						disabled:!baseinfo.leasedVan.isPermission('leasedVan/updateSelfVolumeButton'),
						hidden:!baseinfo.leasedVan.isPermission('leasedVan/updateSelfVolumeButton'),
						width : 80,
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length != 1) {
								Ext.MessageBox.show({
									title : baseinfo.leasedVan.i18n('foss.baseinfo.failureInformationTips'),
									msg : '只能选择一条记录进行修改净空！',
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								return;
							}
							me.getUpdateSelfVolumeWindow().vehicleRecord=selectionRecord[0];
							var form=me.getUpdateSelfVolumeWindow().getUpdateSelfVolumeForm().getForm();
							form.findField('measureEmployeeCode').setCombValue(selectionRecord[0].get('measureEmployeeName'),selectionRecord[0].get('measureEmployeeCode'));
							form.findField('measureManagerCode').setCombValue(selectionRecord[0].get('measureManagerName'),selectionRecord[0].get('measureManagerCode'));
							form.findField('measureSeniorManagerCode').setCombValue(selectionRecord[0].get('measureSeniorManagerName'),selectionRecord[0].get('measureSeniorManagerCode'));
							form.loadRecord(selectionRecord[0]);
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

/*****************************************************  定义"厢式车"申请窗口 *****************************************************/
/*
 * 定义："厢式车"新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.leasedVan.LeasedVanAddUpdateForm', {
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
			title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vanVihicleBaseInfo'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleNo'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vehicleNoIsNotNull'),
					maxLength : 10,
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
						 * 模拟选择器：失去焦点获取外请厢式车信息
						 * @param {Ext.Component} me 当前对象
						 * @param {Ext.EventObject} event 当前触发事件对象
						 */
						blur : function (me, event) {
							//if(!me.wasValid){
								//me.setValue(null);
							//	return;
								//}
//								if(!Ext.isEmpty(me.getValue())){
//									 if(me.getValue().length != 7){
//									 	Ext.MessageBox.show({
//											title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
//											msg : "输入长度必须满足七位，请重新输入!",
//											width : 300,
//											buttons : Ext.Msg.OK,
//											icon : Ext.MessageBox.WARNING
//										});
// 				        		 me.setValue(null);
// 				             return;
//									 	}
//									}
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
									//检测"外请厢式车"是否已经存在
									var json = Ext.decode(response.responseText);
									if (Ext.isEmpty(json.leasedVehicleVo.leasedVehicle)) {
										me.validatorMessage.validity = true;
										me.validatorMessage.message = null;
										//me.clearInvalid();
									} else {
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.theVehicleNoInfoIsExists');
										me.markInvalid(me.validatorMessage.message);
										Ext.MessageBox.show({
											title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
					xtype : 'datefield',
					name : 'beginTime',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.releaseDateIsNotNull'),
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.releaseDate'),
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
							var target = me.up('form').getForm().findField('endTime').getValue();
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.releaseDateIsNotEqDateOfRetirement');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.releaseDateIsNotPastDateOfRetirement');
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							}target = null;
						},
						change : function(me, newValue, oldValue, eOpts) {
							if(Ext.isEmpty(newValue)){ return; }
							var tempForm = me.up('form').getForm();
							var endTime = tempForm.findField('endTime');
							var producingArea = tempForm.findField('producingArea');
							if(!Ext.isEmpty(endTime)){
								//时间定义
								var Y_producingArea = 4, N_producingArea = 7;
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
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.dateOfRetirementIsNotNull'),
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.dateOfRetirement'),
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
							var target = me.up('form').getForm().findField('beginTime').getValue();
							if (!Ext.isEmpty(source) && !Ext.isEmpty(target)) {
								if (Ext.Date.isEqual(source, target)) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.dateOfRetirementNotEqdateOfRetirement');
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.dateOfRetirementNotBeforedateOfRetirement');
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
					name : 'engineNo',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.motorNumber'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.motorNumberIsNotNull'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					name : 'vehicleFrameNo',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.chaissisNumber'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.chaissisNumberIsNotNull'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 20,
					name : 'vehicleLength',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleLength'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vehicleLenghtIsNotNull'),
					maxLength : 10,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherOpenCar'),
					name : 'openVehicle_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherOpenCarIsNotNull'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes'),
							name : 'openVehicle',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo'),
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
								} else {}
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
								} else {}
							}
							formObj = null;
						}
					}
				}, {
					xtype : 'radiogroup',
					fieldLabel : '<span style="color:red;">*</span>'+baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherHighhurdleCar'),
					name : 'railVehicle_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					//allowBlank : false,
					hidden : true,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherHighhurdleCarIsNotNull'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes'),
							name : 'railVehicle',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo'),
							name : 'railVehicle',
							inputValue : 'N'
						}
					]
				}, {
					xtype : 'radiogroup',
					fieldLabel : '<span style="color:red;">*</span>'+baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.material'),
					name : 'material_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					//allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.materialIsNotNull'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.ironhide'),
							name : 'material',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.glasses'),
							name : 'material',
							inputValue : 'N'
						}
					]
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 10,
					name : 'vehicleWidth',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleWidth'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vechleWidthIsNotNull'),
					maxLength : 10,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 200,
					name : 'selfVolume',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selfVolume'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.selfVolumeIsNotNull'),
					maxLength : 10,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					minValue : 1,
					maxValue : 10,
					hideTrigger : false,
					name : 'vehicleHeight',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleHeight'),
					maxLength : 10,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vehicleHeightIsNotNull'),
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherContract'),
					name : 'bargainVehicle_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contractIsNotNull'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes'),
							name : 'bargainVehicle',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo'),
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
								}
								if (newVal.bargainVehicle === 'N') {
									disabled = true;
								}
								var bargainRouteVal = null;
								var windowModel = me.up('window').getAddUpdateFormModel();
								if (!disabled) {
									bargainRouteVal = windowModel.get('bargainRoute');
								}
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
					xtype : 'numberfield',
					minValue : 1,
					maxValue : 100,
					hideTrigger : false,
					name : 'deadLoad',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.deadLoad'),
					maxLength : 20,
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'numberfield',
					hideTrigger : false,
					minValue : 1,
					maxValue : 50,
					name : 'selfWeight',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selfWeight'),
					maxLength : 10,
					readOnly : false,
					columnWidth : 0.5
				}, {
					name : 'bargainRouteCode',
					fieldLabel : 'bargainRouteCode',
					maxLength : 50,
					columnWidth : 0.5,
					hidden:true
				},{
					/*
					 * 这个是公共选择器
					 */
					xtype : 'commonlineselector',
					name : 'bargainRoute',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.bargainRoute'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contractRoutIsNotNull'),
					readOnly : false,
					columnWidth : 0.5,
					editable : true,
					selector : function (_val) {
						var me = this;
						if (Ext.isEmpty(_val)) {
							return;
						} else {
							me.up('form').getForm().findField('bargainRouteCode').setValue(_val);
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
					/*
					 * 这个是公共选择器
					 */
					xtype : 'commonvehicletypeselector',
					vehicleSort : 'ownership_leased',
					name : 'vehicleLengthCode',
					fieldLabel : '车型',
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vehicleLengthCode'),
					readOnly : false,
					columnWidth : 0.5,
					editable : false,
					displayField : 'vehicleLengthName',
					valueField : 'vehicleLengthCode', // 值
					queryParam : 'vehicleTypeEntity.vehicleLengthCode',
					listeners : {
						beforerender : function (me, eOpts) {
							if (Ext.isEmpty(me.getValue())) {
								return;
							}
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
					xtype : 'combobox',
					name : 'brand',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.brand'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.brandIsNotNull'),
					columnWidth : 0.5,
					displayField : 'valueName',
					valueField : 'valueCode',
					queryMode : 'local',
					triggerAction : 'all',
					editable : false,
					store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_leasedVan_BridgeStore_Id')
				}, {
					xtype : 'radiogroup',
					name : 'tailBoard_RadioGroup',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.whetherTailgate'),
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.whetherTailBoard'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes'),
							name : 'tailBoard',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo'),
							name : 'tailBoard',
							inputValue : 'N'
						}
					]
				}, {
					xtype : 'radiogroup',
					name : 'gps_RadioGroup',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.toLoadGPS'),
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.gpsIsNotNull'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectYes'),
							name : 'gps',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.selectNo'),
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
					xtype : 'radiogroup',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.producingArea'),
					name : 'producingArea_RadioGroup',
					defaultType : 'radiofield',
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.producingAreaIsNotNull'),
					columnWidth : 0.5,
					columns : [70, 70],
					items : [{
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.madeInChina'),
							name : 'producingArea',
							inputValue : 'Y'
						}, {
							boxLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.import'),
							name : 'producingArea',
							inputValue : 'N'
						}
					]
				}, {
					xtype : 'label',
					columnWidth : 0.5,
					margin : '0 0 0 314'
				}, {
					name : 'gpsProvider',
					readOnly : false,
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.gpsProvider'),
					colspan : 3,
					maxLength : 1000,
					width : 940
				}
			]
		}, {
			// 测量人员信息
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedVan.i18n('foss.baseinfo.LTRtrailerSurveyCrew'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureEmployee'),//测量员工
					allowBlank : false,
					hidden : true,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureEmployeeCanNotEmpty'),//测量员工不能为空！
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureManager'),//测量经理
					allowBlank : false,
					hidden : true,
					//queryAllFlag:false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureManagerCanNotEmpty'),//测量经理不能为空！
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureSeniorManager'),//测量经理
					allowBlank : false,
					hidden : true,
					//queryAllFlag:false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureSeniorManagerCanNotEmpty'),//测量经理不能为空！
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
				}
			,{
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
			title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vanVihiclePaperInfo'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.operatingLic'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.runLicenseNoIsNotNull'),
					maxLength : 20,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.runLicNotContSpeChar'),
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
							me.validatorMessage.value = Ext.String.trim(me.getValue());
						},
						/**
						 * 模拟选择器：失去焦点获取外请厢式车信息
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
									//检测"营运证号"是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.licenseIsExistsFrom') + leasedVehicle.vehicleNo + baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.licenseTo');
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
											title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeOperatingLic'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeOperatingLicIsNotNull'),
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
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeOperatingLicIsNotEqEndTime');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeOperatingLicIsNotPastEndTime');
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							}
						}
					}
				}, {
					xtype : 'datefield',
					name : 'endTimeOperatingLic',//营运证有效结束日期
					readOnly : false,
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeOperatingLic'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeOperatingLicIsNotNull'),
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
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeOperatingLicIsNotEqEndTime');
									me.markInvalid(me.validatorMessage.message);
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeOperatingLicIsNotBefEndTime');
									me.markInvalid(me.validatorMessage.message);
								} else {
									me.validatorMessage.validity = true;
									me.validatorMessage.message = null;
									me.clearInvalid();
								}
							}
						}
					}
				}, {
					name : 'drivingLicense',    //行驶证
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.drivingLicense'),
					readOnly : false,
					allowBlank : true,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.drivingLicenseIsNotNull'),
					maxLength : 20,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.runDriverNoNotContSpeChar'),
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
						 * 模拟选择器：失去焦点获取外请厢式车信息
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
									//检测"行驶证号"是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.drivingLicenseIsExists') + leasedVehicle.vehicleNo + baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.licenseTo');
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
											title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
					name : 'beginTimeDrivingLic',          //行驶证有效开始日期
					readOnly : false,
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeDrivingLic'),
					allowBlank : true,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeDrivingLicIsNotNull'),
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
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.LTtravelingCardStartDateEqualEndDate');
									me.markInvalid(me.validatorMessage.message);
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.LTtravelingCardStartDateAfterEndDate');
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
					name : 'endTimeDrivingLic',        //行驶证有效结束日期
					readOnly : false,
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeDrivingLic'),
					allowBlank : true,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeDrivingLicIsNotNull'),
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
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeDrivingLicNotEqlEndTime');
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.insureCard'),
					readOnly : false,
					maxLength : 50,
					regex : /(^(a-z|A-Z|0-9)*[^~!@#$%^&*()']*$)/,
					regexText : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.insureCardNoNotContSpeChar'),
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
						 * 模拟选择器：失去焦点获取外请厢式车信息
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
									//检测"保险卡号"是否已经存在
									var json = Ext.decode(response.responseText);
									var leasedVehicle = json.leasedVehicleVo.leasedVehicle;
									//数据"已存在"
									if (Ext.isEmpty(leasedVehicle)) {
										me.validatorMessage.validity = true;
									} else {
										//初始化：错误数据
										me.validatorMessage.validity = false;
										me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.thisInsuranceCardIsExists') + leasedVehicle.vehicleNo + baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.licenseTo');
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
											title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.beginTimeInsureCard'),
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
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.insuranceCardValidStartDateNotEqEndDate');
									me.markInvalid(me.validatorMessage.message);
									Ext.MessageBox.show({
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								} else if (source > target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.insuranceCardValidStartDateNotPastEndDate');
									me.markInvalid(me.validatorMessage.message);
									Ext.MessageBox.show({
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.endTimeInsureCard'),
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
									me.validatorMessage.message = baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.insuranceCardValidStartDateNotEqEndDate');
									me.markInvalid(me.validatorMessage.message);
									Ext.MessageBox.show({
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								} else if (source < target) {
									me.validatorMessage.validity = false;
									me.validatorMessage.message = '保险卡有效结束日期不能（早于）其开始日期！';
									me.markInvalid(me.validatorMessage.message);
									Ext.MessageBox.show({
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
										msg : me.validatorMessage.message,
										width : 350,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
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
			title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vanVehicleCarOwnerInfo'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contact'),
					readOnly : false,
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.carOwnerNameNotNull'),
					maxLength : 20,
					columnWidth : 0.5
				}, {
					name : 'contactPhone',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contactPhone'),
					maxLength : 20,
					regex: /(^\d{11}$)/,
					regexText : baseinfo.leasedVan.i18n('foss.baseinfo.baseinfoTelphoneError'),
					readOnly : false,
					columnWidth : 0.5
				}, {
					xtype : 'commonleaseddriverselector',
					status : 'Y',
					name : 'leasedDriverIdCard',
					queryAllFlag:false,
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.ownDriversInformation'),
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.ownDriversInformationCanNotEmpty'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.contactAddress'),
					maxLength : 200,
					readOnly : false,
					colspan : 3,
					width : 940
				}
			]
		}, {
			// Fieldset in Column 4 - collapsible via toggle button
			xtype : 'fieldset',
			columnWidth : 0.5,
			title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.vanVehicleRemarkInfo'),
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.notes'),
					colspan : 3,
					maxLength : 500,
					width : 940
				}, {
					name : 'vehicleType',
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.vehicleType'),
					readOnly : true,
					hidden : true,
					columnWidth : 0.5,
					colspan : 2,
					value : 'vehicletype_van'
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
 * 定义"厢式车"申请的表单窗口
 */
Ext.define('Foss.baseinfo.leasedVan.LeasedVanAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.addOrUpdate'),
	width : 1100,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.leasedVan.LeasedVanAddUpdateForm');
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
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.leasedVan.LeasedVanModel");
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
						text : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.cancel'),
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
						text : baseinfo.leasedVan.i18n('foss.baseinfo.vehicle.resetBtn'),
						handler : function () {
							if (me.getWindowAction() === 'upd') {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
							}
							if (me.getWindowAction() === 'add') {
								var form = me.getAddUpdateForm().getForm().reset();
								//敞篷、高栏、材质联动默认值
								form.findField('openVehicle').setValue('N');
								form.findField('material').setValue('N');
								form.findField('railVehicle').setValue(null);
								//合同车、产地默认值
								form.findField('bargainVehicle').setValue('N');
								form.findField('producingArea').setValue('Y');
								form.findField('gps').setValue('N');
								form.findField('tailBoard').setValue('N');
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
						text : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.save'),
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
									me.getAddUpdateFormModel().set('measureManagerCode', measureManagerCode);
									me.getAddUpdateFormModel().set('measureSeniorManagerCode', measureSeniorManagerCode);
									me.getAddUpdateFormModel().set('measureEmployeeName', measureEmployeeName);
									me.getAddUpdateFormModel().set('measureManagerName', measureManagerName);
									me.getAddUpdateFormModel().set('measureSeniorManagerName', measureSeniorManagerName);
									
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
										} else {
											if(addUpdateForm.findField('bargainRoute').valueModels.length==0){
												var bargainRoute = addUpdateForm.findField('bargainRouteCode').getValue();
												me.getAddUpdateFormModel().set('bargainRoute', bargainRoute);
												bargainRoute = null;
											}else{
												var bargainRoute = addUpdateForm.findField('bargainRoute').valueModels[0].data.simpleCode;
												me.getAddUpdateFormModel().set('bargainRoute', bargainRoute);
												bargainRoute = null;
											}
										}
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
											Ext.getCmp('T_baseinfo-leasedVan_content').getLeasedVanGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgSuccessPrompt'),
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
												title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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
										title : baseinfo.leasedVan.i18n('foss.baseinfo.leasedVan.msgErrorPrompt'),
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


//修改净空窗口——232607——2015/10/13
Ext.define('Foss.baseinfo.leasedVan.UpdateSelfVolumeWindow', {
	extend : 'Ext.window.Window',
	title : '修改净空',
	resizable : false,//允许用户修改窗口大小
	closeAction : 'hide',//关闭窗口并不销毁窗口，而是隐藏它
	//修改净空表格
	updateSelfVolumeForm : null,
	getUpdateSelfVolumeForm : function () {
		if (null == this.updateSelfVolumeForm) {
			this.updateSelfVolumeForm = Ext.create('Foss.baseinfo.leasedVan.UpdateSelfVolumeForm');
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
						text : baseinfo.leasedVan.i18n('foss.baseinfo.cancel'),
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
						text : baseinfo.leasedVan.i18n('foss.baseinfo.reset'),
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
						text : baseinfo.leasedVan.i18n('foss.baseinfo.save'),
						plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds : 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var updateSelfVolumeForm = me.getUpdateSelfVolumeForm().getForm();
								if (updateSelfVolumeForm.isValid()) { //校验FORM是否通过校验
									var record=new Foss.baseinfo.leasedVan.LeasedVanModel();
									var measureEmployeeCode = updateSelfVolumeForm.findField('measureEmployeeCode').lastValue;
									var measureEmployeeName = updateSelfVolumeForm.findField('measureEmployeeCode').rawValue;
									var measureManagerName = updateSelfVolumeForm.findField('measureManagerCode').rawValue;
									var measureManagerCode = updateSelfVolumeForm.findField('measureManagerCode').lastValue;
									var measureSeniorManagerName = updateSelfVolumeForm.findField('measureSeniorManagerCode').rawValue;
									var measureSeniorManagerCode = updateSelfVolumeForm.findField('measureSeniorManagerCode').lastValue;
									updateSelfVolumeForm.updateRecord(record);
									record.set('measureEmployeeCode', measureEmployeeCode);
									record.set('measureManagerCode', measureManagerCode);
									record.set('measureSeniorManagerCode', measureSeniorManagerCode);
									record.set('measureEmployeeName', measureEmployeeName);
									record.set('measureManagerName', measureManagerName);
									record.set('measureSeniorManagerName', measureSeniorManagerName);
									var selfVolumeBack = record.data.selfVolume;
									var selfVolumeFront = updateSelfVolumeForm.findField('selfVolumeHide').getValue();
									var range = Math.abs(selfVolumeBack-selfVolumeFront);
									if(range/selfVolumeFront>0.05){
										Ext.MessageBox.show({
											title : baseinfo.leasedVan.i18n('foss.baseinfo.confirmationPrompt'),
											msg : baseinfo.leasedVan.i18n('foss.baseinfo.LTRvoidReportedErrorsConfirmMessage'),
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
															Ext.getCmp('T_baseinfo-leasedVan_content').getLeasedVanGrid().store.load();
															Ext.MessageBox.show({
																title : baseinfo.leasedVan.i18n('foss.baseinfo.successPrompt'),
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
																title : baseinfo.leasedVan.i18n('foss.baseinfo.failureInformationTips'),
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
Ext.define('Foss.baseinfo.leasedVan.UpdateSelfVolumeForm', {
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
					fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.LTRclearanceSide'),//净空（方）
					allowBlank : false,
					blankText : baseinfo.leasedVan.i18n('foss.baseinfo.LTRclearanceCanNotEmpty'),//净空不能为空
					maxLength : 20
		},{
			xtype : 'commonemployeemultiselector',
			status : 'Y',
			name : 'measureEmployeeCode',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureEmployee'),//测量员工
			allowBlank : false,
			blankText : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureEmployeeCanNotEmpty'),//测量员工不能为空！
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
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureManager'),//测量经理
			allowBlank : false,
			//queryAllFlag:false,
			blankText : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureManagerCanNotEmpty'),//测量经理不能为空！
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
				}}
		}, {
			xtype : 'commonemployeemultiselector',
			status : 'Y',
			name : 'measureSeniorManagerCode',
			fieldLabel : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureSeniorManager'),//测量经理
			allowBlank : false,
			//queryAllFlag:false,
			blankText : baseinfo.leasedVan.i18n('foss.baseinfo.MeasureSeniorManagerCanNotEmpty'),//测量经理不能为空！
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
		}];
		me.callParent([cfg]);
	}
});
	

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.leasedVan.QueryForm');
	/*
	 * 创建查询"厢式车"结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.leasedVan.LeasedVanGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-leasedVan').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-leasedVan_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询"厢式车"结果列表结果窗口
		getLeasedVanGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		]
//		renderTo : 'T_baseinfo-leasedVan-body'
	}));

});
