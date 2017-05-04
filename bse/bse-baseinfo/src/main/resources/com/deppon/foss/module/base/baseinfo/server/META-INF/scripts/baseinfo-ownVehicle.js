/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义"公司车辆"数据Model窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
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
 * 定义：一个"司机"的数据模型"Model"
 */
Ext.define('Foss.baseinfo.ownVehicle.OwnVehicleModel', {
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
			name : 'orgId', //所属部门
			type : 'string'
		}, {
			name : 'status', //车辆状态
			type : 'string'
		}, {
			name : 'selfWeight', //自重
			type : 'float'
		}, {
			name : 'brand', //品牌
			type : 'string'
		}, {
			name : 'usedType', //车辆使用类型
			type : 'string'
		}, {
			name : 'vehicleLength', //车辆长
			type : 'float'
		}, {
			name : 'vehicleWidth', //车辆宽
			type : 'float'
		}, {
			name : 'vehicleHeight', //车辆高
			type : 'float'
		}, {
			name : 'selfVolume', //净空
			type : 'float'
		}, {
			name : 'tailBoard', //是否有尾板
			type : 'string'
		}, {
			name : 'deadLoad', //额定载重
			type : 'float'
		}, {
			name : 'consumeFuel', //百公里油耗
			type : 'float'
		}, {
			name : 'gps', //是否有GPS
			type : 'string'
		}, {
			name : 'gpsNo', //GPS设备号
			type : 'string'
		}, 
//		{
//			name : 'vehicleDeadLoad', //车货总重
//			type : 'float'
//		}, 
		{
			name : 'tankVolume', //油箱容积
			type : 'float'
		}, {
			name : 'bridge', //单双桥
			type : 'string'
		}, {
			name : 'active', //是否启用
			type : 'string'
		}, {
			name : 'vehicleType', //车辆类型
			type : 'string'
		}, {
			name : 'containerCode', //货柜编号
			type : 'string'
		}, {
			name : 'vehicleLmsCode', //LMS同步过来的车辆唯一标识
			type : 'string'
		}, {
			name : 'vehcleLengthCode', //车辆车型编码
			type : 'string'
		}, {
			name : 'versionNo', //执行LMS的版本号
			type : 'string'
		}, {
			name : 'unavilableCode', //关联停车原因代码
			type : 'string'
		}, {
			name : 'beginDate', //停车计划开始时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'endDate', //停车计划结束时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'trailerType', //挂车类型编码
			type : 'string'
		}, {
			name : 'isBallon', //是否气囊柜
			type : 'string'
		}, {
			//实体扩展字段
			name : 'orgName', //组织名称
			type : 'string'
		}, {
			//实体扩展字段
			name : 'unavilableName', //不可用原因
			type : 'string'
		}
	]
});

/*******************************************************  定义"公共车辆"列表结果窗口 *****************************************************/
/*
 * 定义查询条件的表单FORM
 */
Ext.define('Foss.baseinfo.ownVehicle.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.queryCondition'),
	id : 'Foss_baseinfo_ownVehicle_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		labelWidth : 100,
		width : 316,
		margin : '8 10 5 10',
		anchor : '100%'
	},
	height : 245,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			xtype : 'combobox',
			name : 'vehicleType',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleType'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_TYPE', 'Foss_baseinfo_ownVehicle_VehicleType_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
			}),
			value : '',
			listeners : {
				change : function (me, newValue, oldVaue, eOpts) {
					var tempFormPanel = me.up();
					var tempForm = me.up().getForm();
					if (!Ext.isEmpty(tempForm) && !Ext.isEmpty(tempFormPanel)) {
						//还原初始化
						me.up().setHeight(185);
						tempForm.findField('brand').hide();
						tempForm.findField('bridge').hide();
						tempForm.findField('gps').hide();
						tempForm.findField('trailerType').hide();
						tempForm.findField('isBallon').hide();
						tempForm.findField('tailBoard').hide();
						if (!Ext.isEmpty(newValue)) {
							//拖头面板
							if (newValue === 'vehicletype_tractors') {
								me.up().setHeight(215);
								tempForm.findField('brand').show();
								tempForm.findField('bridge').show();
								tempForm.findField('gps').show();
							}
							//挂车面板
							if (newValue === 'vehicletype_trailer') {
								me.up().setHeight(215);
								tempForm.findField('brand').show();
								tempForm.findField('trailerType').show();
								tempForm.findField('isBallon').show();
							}
							//厢式车面板
							if (newValue === 'vehicletype_van') {
								me.up().setHeight(215);
								tempForm.findField('brand').show();
								tempForm.findField('gps').show();
								tempForm.findField('tailBoard').show();
							}
							//骨架车面板
							if (newValue === 'vehicletype_rqsvc') {
								me.up().setHeight(215);
								tempForm.findField('brand').show();
								tempForm.findField('gps').show();
								tempForm.findField('tailBoard').show();
							}
						}
					}
					tempForm = null;
					tempFormPanel = null;
				}
			}
		}, {
			name : 'vehicleNo',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleNo')
		}, {
			xtype : 'combobox',
			name : 'usedType',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleUseType'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_USED_TYPE', 'Foss_baseinfo_ownVehicle_UsedType_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleStatus'),
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.valid')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.inValid')
						}
					]
				}
			}),
			value : ''
		}, {
			xtype : 'dynamicorgcombselector',
			name : 'orgId',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.belongOrg'),//所属部门
			transDepartment : 'Y'
		}, {
			xtype : 'label',
			height : 25
		}, {
			xtype : 'combobox',
			name : 'brand',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.brand'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			hidden : true,
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_ownVehicle_BrandStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'bridge',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.singleDoubleBr'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			hidden : true,
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRIGE_TYPE', 'Foss_baseinfo_ownVehicle_BridgeStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'gps',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherGPS'),
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			hidden : true,
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'trailerType',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.trailerType'),
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			hidden : true,
			store : FossDataDictionary.getDataDictionaryStore('LMS_TRAILER_TYPE', 'Foss_baseinfo_ownVehicle_TrailerType_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'isBallon',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherAirbagCabinet'),
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			hidden : true,
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			}),
			value : ''
		}, {
			xtype : 'combobox',
			name : 'tailBoard',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherTailgate'),
			displayField : 'valueName',
			valueField : 'valueCode',
			editable : false,
			hidden : true,
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : '',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectAll')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo')
						}
					]
				}
			}),
			value : ''
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				xtype : 'button',
				width : 70,
				text : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.resetBtn'), //重置
				disabled:!baseinfo.ownVehicle.isPermission('ownVehicle/ownVehicleQueryButton'),
				hidden:!baseinfo.ownVehicle.isPermission('ownVehicle/ownVehicleQueryButton'),
				margin : '8 800 5 0',
				handler : function () {
					me.getForm().reset();
				}
			}, {
				xtype : 'button',
				width : 70,
				text : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.queryBtn'), //查询
				disabled:!baseinfo.ownVehicle.isPermission('ownVehicle/ownVehicleQueryButton'),
				hidden:!baseinfo.ownVehicle.isPermission('ownVehicle/ownVehicleQueryButton'),
				cls : 'yellow_button',
				handler : function () {
					if (me.getForm().isValid()) {
						me.up().getOwnVehicleGrid().getPagingToolbar().moveFirst();
					}
				}
			}
		];
		me.callParent([cfg]);
	}
});

/*
 * 定义：一个"司机"的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.ownVehicle.OwnVehicleStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.ownVehicle.OwnVehicleModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryOwnVehicleList.action'),
		reader : {
			type : 'json',
			root : 'ownVehicleVo.ownVehicleList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_ownVehicle_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				//动态设置URL
				if (!Ext.isEmpty(queryParams)) {
					var vehicleType = queryParams.vehicleType;
					var url = 'queryOwnVehicleList.action';
					var indexArray = new Array(0, 1, 2, 3, 4);
					var params = {
						'ownVehicleVo.ownVehicle.vehicleType' : queryParams.vehicleType,
						'ownVehicleVo.ownVehicle.vehicleNo' : queryParams.vehicleNo,
						'ownVehicleVo.ownVehicle.usedType' : queryParams.usedType,
						'ownVehicleVo.ownVehicle.status' : queryParams.status,
						'ownVehicleVo.ownVehicle.orgId' : queryParams.orgId
					};
					if (!Ext.isEmpty(vehicleType)) {
						if (vehicleType === 'vehicletype_tractors') {
							url = 'queryOwnTractorsList.action';
							params = {
								'ownVehicleVo.ownVehicle.vehicleType' : queryParams.vehicleType,
								'ownVehicleVo.ownVehicle.vehicleNo' : queryParams.vehicleNo,
								'ownVehicleVo.ownVehicle.usedType' : queryParams.usedType,
								'ownVehicleVo.ownVehicle.status' : queryParams.status,
								'ownVehicleVo.ownVehicle.orgId' : queryParams.orgId,
								'ownVehicleVo.ownVehicle.brand' : queryParams.brand,
								'ownVehicleVo.ownVehicle.bridge' : queryParams.bridge,
								'ownVehicleVo.ownVehicle.gps' : queryParams.gps
							}
							indexArray = new Array(0, 1, 2, 3, 4, 5, 6, 7, 9, 10);
							//indexArray = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
							
						}
						if (vehicleType === 'vehicletype_trailer') {
							url = 'queryOwnTrailerList.action';
							params = {
								'ownVehicleVo.ownVehicle.vehicleType' : queryParams.vehicleType,
								'ownVehicleVo.ownVehicle.vehicleNo' : queryParams.vehicleNo,
								'ownVehicleVo.ownVehicle.usedType' : queryParams.usedType,
								'ownVehicleVo.ownVehicle.status' : queryParams.status,
								'ownVehicleVo.ownVehicle.orgId' : queryParams.orgId,
								'ownVehicleVo.ownVehicle.brand' : queryParams.brand,
								'ownVehicleVo.ownVehicle.trailerType' : queryParams.trailerType,
								'ownVehicleVo.ownVehicle.isBallon' : queryParams.isBallon
							}
							indexArray = new Array(0, 1, 2, 3, 4, 5, 11, 12, 13, 14, 15, 16, 9);
						}
						if (vehicleType === 'vehicletype_van') {
							url = 'queryOwnVanList.action';
							params = {
								'ownVehicleVo.ownVehicle.vehicleType' : queryParams.vehicleType,
								'ownVehicleVo.ownVehicle.vehicleNo' : queryParams.vehicleNo,
								'ownVehicleVo.ownVehicle.usedType' : queryParams.usedType,
								'ownVehicleVo.ownVehicle.status' : queryParams.status,
								'ownVehicleVo.ownVehicle.orgId' : queryParams.orgId,
								'ownVehicleVo.ownVehicle.brand' : queryParams.brand,
								'ownVehicleVo.ownVehicle.gps' : queryParams.gps,
								'ownVehicleVo.ownVehicle.tailBoard' : queryParams.tailBoard
							}
							//indexArray = new Array(0, 1, 2, 3, 4, 5, 17, 7, 13, 14, 15, 8, 9);    显示grid列名称
							indexArray = new Array(0, 1, 2, 3, 4, 5, 17, 7, 13, 14, 15, 9);
						}
						if (vehicleType === 'vehicletype_rqsvc') {
							url = 'queryOwnRQSVCList.action';
							params = {
								'ownVehicleVo.ownVehicle.vehicleType' : queryParams.vehicleType,
								'ownVehicleVo.ownVehicle.vehicleNo' : queryParams.vehicleNo,
								'ownVehicleVo.ownVehicle.usedType' : queryParams.usedType,
								'ownVehicleVo.ownVehicle.status' : queryParams.status,
								'ownVehicleVo.ownVehicle.orgId' : queryParams.orgId,
								'ownVehicleVo.ownVehicle.brand' : queryParams.brand,
								'ownVehicleVo.ownVehicle.gps' : queryParams.gps,
								'ownVehicleVo.ownVehicle.tailBoard' : queryParams.tailBoard
							}
							//indexArray = new Array(0, 1, 2, 3, 4, 5, 17, 7, 13, 14, 15, 8, 9);    显示grid列名称
							indexArray = new Array(0, 1, 2, 3, 4, 5, 17, 7, 13, 14, 15, 9);
						}
					}
					//列同步操作
					var vehicleGrid = Ext.getCmp('Foss_baseinfo_ownVehicle_OwnVehicleGrid_Id');
					vehicleGrid.showGridColumns(indexArray, false, indexArray.length <= 5 ? true : false);
					vehicleGrid = null;
					indexArray = null;
					//执行应用
					this.proxy.url = baseinfo.realPath(url);
					Ext.apply(operation, {
						params : params
					});
					url = null;
					params = null;
					vehicleType = null;
				}
			}
		}
	}
});

/*
 * 定义："司机"内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.ownVehicle.OwnVehicleDetailForm', {
	extend : 'Ext.form.Panel',
	height : 230,
	//autoScroll : true,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 130
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			xtype : 'combobox',
			name : 'vehicleType',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleType'),
			readOnly : true,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_TYPE', 'Foss_baseinfo_ownVehicle_VehicleTypeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			xtype : 'combobox',
			name : 'brand',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.brand'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRAND_TYPE', 'Foss_baseinfo_leasedVan_BrandStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'vehicleNo',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleNo'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'combobox',
			name : 'trailerType',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.trailerType'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : FossDataDictionary.getDataDictionaryStore('LMS_TRAILER_TYPE', 'Foss_baseinfo_leasedVan_TrailerTypeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			xtype : 'combobox',
			name : 'isBallon',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherAirbagCabinet'),
			readOnly : true,
			columnWidth : 0.9,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes')
						}
					]
				}
			})
		}, {
			name : 'orgName',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.belongOrg'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'combobox',
			name : 'bridge',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.singleDoubleBr'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : FossDataDictionary.getDataDictionaryStore('VEHICLE_BRIGE_TYPE', 'Foss_baseinfo_leasedVan_BridgeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			name : 'consumeFuel',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.kiloOilWear'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'combobox',
			name : 'gps',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherGPS'),
			readOnly : true,
			columnWidth : 0.9,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'tailBoard',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherTailgate'),
			readOnly : true,
			columnWidth : 0.9,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo')
						}, {
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes')
						}
					]
				}
			})
		}, {
			xtype : 'combobox',
			name : 'status',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleStatus'),
			readOnly : true,
			columnWidth : 0.9,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : Ext.create('Foss.baseinfo.ownVehicle.QueryFormParameterStore', {
				data : {
					'items' : [{
							'valueCode' : 'Y',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.valid')
						}, {
							'valueCode' : 'N',
							'valueName' : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.inValid')
						}
					]
				}
			})
		}, {
			name : 'containerCode',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.containerNo'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'selfVolume',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selfVolume'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'selfWeight',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selfWeight'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'vehicleLength',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleLength'),//车长
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'vehicleHeight',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleHeight'),//车高
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'vehicleWidth',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleWidth'),//车宽
			readOnly : true,
			columnWidth : 0.9
		},
//		{
//			name : 'vehicleDeadLoad',
//			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleDeadLoad'),  //车货总重
//			readOnly : true,
//			columnWidth : 0.9
//		}, 
		{
			name : 'deadLoad',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.deadLoad'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'tankVolume',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.tankVolume'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'combobox',
			name : 'usedType',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleUseType'),
			readOnly : true,
			columnWidth : 0.5,
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : FossDataDictionary.getDataDictionaryStore('BES_VEHICLE_USED_TYPE', 'Foss_baseinfo_leasedVan_UsedTypeStore_Id', {
				'valueCode' : '',
				'valueName' : '-'
			})
		}, {
			xtype : 'datefield',
			name : 'beginDate',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.beginDate'),
			readOnly : true,
			columnWidth : 0.9,
			format : 'Y-m-d H:i:s'
		}, {
			xtype : 'datefield',
			name : 'endDate',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.endDate'),
			readOnly : true,
			columnWidth : 0.9,
			format : 'Y-m-d H:i:s'
		}, {
			xtype : 'combobox',
			name : 'unavilableName',
			fieldLabel : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.unavilableName'),
			readOnly : true,
			columnWidth : 0.9
		}
	],
	showDetailPanel : function (nameArray, isHidden) {
		var me = this;
		if (!Ext.isEmpty(nameArray) && !Ext.isEmpty(isHidden)) {
			Ext.each(me.getForm().getFields().items, function (item, itemIndex) {
				var isHide = false;
				for (var index in nameArray) {
					if (item.getName() === nameArray[index]) {
						isHide = true;
						break;
					}
				}
				if (isHide) {
					if (isHidden) {
						item.hide();
					} else {
						item.show();
					}
				} else {
					item.hide();
				}
			});
		}
	}
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.ownVehicle.OwnVehicleDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.ownVehicleDetailInfo'),
	frame : true,
	informationForm : null,
	/**
	 * 获取公司车辆详细信息
	 * @return {Object} OwnVehicleDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.ownVehicle.OwnVehicleDetailForm');
		}
		return this.informationForm;
	},
	constructor : function (config) {
		Ext.apply(this, config);
		this.items = [this.getInformationForm()];
		this.callParent(arguments);
	},
	bindData : function (record) {
		var me = this;
		//绑定表格数据到表单上
		this.getInformationForm().getForm().loadRecord(record);
		//做业务动作显示/隐藏动作
		if (!Ext.isEmpty(record)) {
			var vehicleType = record.get('vehicleType');
			if (Ext.isEmpty(vehicleType)) {
				var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType');
				me.getInformationForm().showDetailPanel(nameArray, false);
				me.setTitle(baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.ownVehicleDetailInfo'));
				nameArray = null;
				return;
			}
			if (vehicleType === 'vehicletype_tractors') {
				//var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType', 'brand', 'bridge', 'consumeFuel', 'gps', 'selfWeight', 'vehicleDeadLoad', 'deadLoad', 'tankVolume', 'unavilableName', 'beginDate', 'endDate');
				var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType', 'brand', 'bridge', 'consumeFuel', 'gps', 'selfWeight', 'deadLoad', 'tankVolume', 'unavilableName', 'beginDate', 'endDate');
				me.getInformationForm().showDetailPanel(nameArray, false);
				me.setTitle(baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.ownVehicleTowDetailInfo'));
				nameArray = null;
				return;
			}
			if (vehicleType === 'vehicletype_trailer') {
				
				var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType', 'brand', 'trailerType', 'isBallon', 'containerCode', 'selfVolume', 'selfWeight', 'vehicleLength', 'vehicleHeight', 'vehicleWidth', 'deadLoad', 'unavilableName', 'beginDate', 'endDate');
				me.getInformationForm().showDetailPanel(nameArray, false);
				me.setTitle(baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.ownVehicleTraDetailInfo'));
				nameArray = null;
				return;
			}
			if (vehicleType === 'vehicletype_van') {
				var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType', 'brand', 'gps', 'tailBoard', 'selfVolume', 'selfWeight', 'vehicleLength', 'vehicleHeight', 'vehicleWidth', 'deadLoad', 'unavilableName', 'consumeFuel', 'beginDate', 'endDate');
				//var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType', 'brand', 'gps', 'tailBoard', 'vehicleDeadLoad', 'selfVolume', 'selfWeight', 'vehicleLength', 'vehicleHeight', 'vehicleWidth', 'deadLoad', 'unavilableName', 'consumeFuel', 'beginDate', 'endDate');
				me.getInformationForm().showDetailPanel(nameArray, false);
				me.setTitle(baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.ownVehicleVanDetailInfo'));
				nameArray = null;
				return;
			}
			if (vehicleType === 'vehicletype_rqsvc') {
				var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType', 'brand', 'gps', 'tailBoard', 'selfVolume', 'selfWeight', 'vehicleLength', 'vehicleHeight', 'vehicleWidth', 'deadLoad', 'unavilableName', 'consumeFuel', 'beginDate', 'endDate');
				//var nameArray = new Array('vehicleType', 'vehicleNo', 'status', 'orgName', 'usedType', 'brand', 'gps', 'tailBoard', 'vehicleDeadLoad', 'selfVolume', 'selfWeight', 'vehicleLength', 'vehicleHeight', 'vehicleWidth', 'deadLoad', 'unavilableName', 'consumeFuel', 'beginDate', 'endDate');
				me.getInformationForm().showDetailPanel(nameArray, false);
				me.setTitle(baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.ownVehicleRQSVCDetailInfo'));
				nameArray = null;
				return;
			}
			vehicleType = null;
		}
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.ownVehicle.OwnVehicleGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_ownVehicle_OwnVehicleGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.ownVehicleTitleInfo'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.ownVehicle.OwnVehicleDetailPanel' // 行体内容
		}
	],
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
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleType'),
			dataIndex : 'vehicleType',
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_VEHICLE_TYPE');
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleNo'),
			dataIndex : 'vehicleNo'
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleStatus'),
			dataIndex : 'status',
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					if (value === 'Y') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.valid');
					} else if (value === 'N') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.inValid');
					} else {
						return '-';
					}
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.belongOrg'),
			dataIndex : 'orgName',
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return value;
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleUseType'),
			dataIndex : 'usedType',
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'BES_VEHICLE_USED_TYPE');
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.brand'),
			dataIndex : 'brand',
			hidden : true,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'VEHICLE_BRAND_TYPE');
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.singleDoubleBr'),
			dataIndex : 'bridge',
			hidden : true,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'VEHICLE_BRIGE_TYPE');
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherGPS'),
			dataIndex : 'gps',
			hidden : true,
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					if (value === 'Y') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes');
					} else if (value === 'N') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo');
					} else {
						return '-';
					}
				}
			}
		},
		{
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleDeadLoad'),
			dataIndex : 'vehicleDeadLoad',
			hidden : true
		},
		{
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.deadLoad'),
			dataIndex : 'deadLoad',
			hidden : true
		}, 
		{
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.tankVolume'),
			dataIndex : 'tankVolume',
			hidden : true
		}, 
		{
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.trailerType'),
			dataIndex : 'trailerType',
			hidden : true,
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'LMS_TRAILER_TYPE');
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherAirbagCabinet'),
			dataIndex : 'isBallon',
			hidden : true,
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					if (value === 'Y') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes');
					} else if (value === 'N') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo');
					} else {
						return '-';
					}
				}
			}
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.vehicleLength'),
			dataIndex : 'vehicleLength',
			hidden : true
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selfVolume'),
			dataIndex : 'selfVolume',
			hidden : true
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selfWeight'),
			dataIndex : 'selfWeight',
			hidden : true
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.containerNo'),
			dataIndex : 'containerCode',
			hidden : true
		}, {
			header : baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.whetherTailgate'),
			dataIndex : 'tailBoard',
			hidden : true,
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else {
					if (value === 'Y') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectYes');
					} else if (value === 'N') {
						return baseinfo.ownVehicle.i18n('foss.baseinfo.vehicle.selectNo');
					} else {
						return '-';
					}
				}
			}
		}
	],
	showGridColumns : function (indexArray, isHidden, isInit) {
		var me = this;
		if (!Ext.isEmpty(indexArray) && !Ext.isEmpty(isHidden)) {
			Ext.each(me.columns, function (column, columnIndex) {
				var isHide = false;
				for (var index in indexArray) {
					if (columnIndex === indexArray[index]) {
						isHide = true;
						break;
					}
				}
				if (isHide) {
					if (isHidden) {
						column.hide();
					} else {
						column.show();
					}
				} else {
					column.hide();
				}
				if (isInit) {
					column.setWidth(200);
				} else {
					column.setWidth(120);
				}
			});
		}
	},
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.ownVehicle.OwnVehicleStore');
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.ownVehicle.QueryForm');
	/*
	 * 创建查询"司机"结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.ownVehicle.OwnVehicleGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-ownVehicle').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-ownVehicle_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询"司机"结果列表结果窗口
		getOwnVehicleGrid : function () {
			return queryResult;
			
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		] 
	}));
});

