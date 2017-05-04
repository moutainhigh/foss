/**
 * @author 100847-foss-GaoPeng
 */
/***************************************************  定义baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')列表查询条件窗口 *************************************************/
/*
 * 查询条件表单FORM中的KEY/VALUE的参数MAP存储器
 */
Ext.define('Foss.baseinfo.flight.QueryFormParameterStore', {
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
Ext.define('Foss.baseinfo.flight.QueryForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.flight.i18n('foss.baseinfo.queryCondition'),
	id : 'Foss_baseinfo_flight_QueryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 120,
		colspan : 1 
	}, 
	defaultType : 'textfield',
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'commonairlinesselector',
			name : 'airlines',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.airlines')
		}, {
			name : 'flightNo',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsNumber')
		}, {
			xtype : 'combobox',
			name : 'flightSort',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.flight.i18n('foss.baseinfo.all')
			}),
			value : ''
		}, {
			xtype : 'timefield',
			name : 'planLeaveTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.startTimeScheduledDeparture'),
			format : 'H:i',
			listConfig: {
		        maxHeight: 138
		    },
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
					var target = me.up('form').getForm().findField('planArriveTime');
					if (!Ext.isEmpty(source) && !Ext.isEmpty(target.getValue())) {
						if (Ext.Date.isEqual(source, target.getValue())) {
							me.validatorMessage.validity = false;
							me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.startTimeEqualEndMessage');
							me.markInvalid(me.validatorMessage.message);
						} else if (source > target.getValue()) {
							me.validatorMessage.validity = false;
							me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.startTimeAfterEndMessage');
							me.markInvalid(me.validatorMessage.message);
						} else {
							me.validatorMessage.validity = true;
							me.validatorMessage.message = null;
							me.clearInvalid();
							target.validatorMessage.validity = true;
							target.validatorMessage.message = null;
							target.clearInvalid();
						}
					} else {}
				}
			}
		}, {
			xtype : 'timefield',
			name : 'planArriveTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.planToTakeOffTheEndTime'),
			format : 'H:i',
			listConfig: {
		        maxHeight: 138
		    },
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
					var target = me.up('form').getForm().findField('planLeaveTime');
					if (!Ext.isEmpty(source) && !Ext.isEmpty(target.getValue())) {
						if (Ext.Date.isEqual(source, target.getValue())) {
							me.validatorMessage.validity = false;
							me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.endTimeEqualStartMessage');
							me.markInvalid(me.validatorMessage.message);
						} else if (source < target.getValue()) {
							me.validatorMessage.validity = false;
							me.validatorMessage.message = '计划起飞结束时间不能（晚于）开始时间！';
							me.markInvalid(me.validatorMessage.message);
						} else {
							me.validatorMessage.validity = true;
							me.validatorMessage.message = null;
							me.clearInvalid();
							target.validatorMessage.validity = true;
							target.validatorMessage.message = null;
							target.clearInvalid();
						}
					} else {}
				}
			}
		}, {
			xtype : 'combobox',
			name : 'isAgreementFlight',
			fieldLabel : '是否协议航班',
			displayField : 'name',
			valueField : 'code',
			queryMode: 'local',
			editable : false,
			store:Ext.create('Ext.data.Store', {
			    fields: ['code', 'name'],
			    data : [
			        {'code':'', 'name':'全部'},
			        {'code':'Y', 'name':'是'},
			        {'code':'N', 'name':'否'}
			    ]
			}),
			value : ''
		}, {
			name : 'departureAirport',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsOriginationStation')
		}, {
			name : 'destinationAirport',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsDestinationStation'),
			colspan:2
		},{
					border: 1,
					xtype:'container',
					columnWidth:1,
					colspan:3,
					defaultType:'button',
					layout:'column',
					items:[{
						  text : baseinfo.flight.i18n('foss.baseinfo.reset'), //重置
						  columnWidth:.12,
						  disabled:!baseinfo.flight.isPermission('flight/flightQueryButton'),
						  hidden:!baseinfo.flight.isPermission('flight/flightQueryButton'),
						  handler : function () {
								me.getForm().reset();
							}
					  	},{
							xtype:'container',
							border:false,
							html:'&nbsp;',
							columnWidth:.76
						},
					  	{
						  text : baseinfo.flight.i18n('foss.baseinfo.query'), //查询
						  columnWidth:.12,
						  cls:'yellow_button',  
						  disabled:!baseinfo.flight.isPermission('flight/flightQueryButton'),
						  hidden:!baseinfo.flight.isPermission('flight/flightQueryButton'),
						  handler : function () {
								if (me.getForm().isValid()) {
									me.up().getFlightGrid().getPagingToolbar().moveFirst();
								}
							}
					  	}]
				}
		];
		me.callParent([cfg]);
	}
});

/*******************************************************  定义baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')列表结果窗口 *****************************************************/
/*
 * 定义：一个baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')的数据模型"Model"
 */
Ext.define('Foss.baseinfo.flight.FlightModel', {
	extend : 'Ext.data.Model',
	idProperty : 'extid',
	idgen : 'uuid',
	fields : [{
			name : 'extid'
		}, {
			name : 'id', //ID标识
			type : 'string'
		}, {
			name : 'flightSort', //航班类别
			type : 'string'
		}, {
			name : 'flightNo', //航班号
			type : 'string'
		}, {
			name : 'airlines', //航空公司
			type : 'string'
		}, {
			name : 'departureAirport', //始发站
			type : 'string'
		}, {
			name : 'destinationAirport', //目的站
			type : 'string'
		}, {
			name : 'planLeaveTime', //计划起飞时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'planArriveTime', //计划到达时间
			type : 'date',
			convert : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				}
				return new Date(value);
			}
		}, {
			name : 'aircraftType', //飞机机型
			type : 'string'
		}, {
			name : 'origCode', //始发站代码
			type : 'string'
		}, {
			name : 'flyTime', //航行时间
			type : 'float'
		}, {
			name : 'flyOnMonday', //是否周一飞
			type : 'string'
		}, {
			name : 'flyOnTuesday', //是否周二飞
			type : 'string'
		}, {
			name : 'flyOnWednesday', //是否周三飞
			type : 'string'
		}, {
			name : 'flyOnThursday', //是否周四飞
			type : 'string'
		}, {
			name : 'flyOnFriday', //是否周五飞
			type : 'string'
		}, {
			name : 'flyOnSaturday', //是否周六飞
			type : 'string'
		}, {
			name : 'flyOnSunday', //是否周日飞
			type : 'string'
		}, {
			name : 'active', //是否启用
			type : 'string'
		}, {
			name : 'targetCode', //目的站代码
			type : 'string'
		},
		//扩展字段
		{
			name : 'airlinesName', //航空公司名称.
			type : 'string'
		}, {
			name : 'originatingStationName', //始发站城市名称.
			type : 'string'
		}, {
			name : 'destinationStationName', //目的站城市名称.
			type : 'string'
		},{
			//2014-01-20 王鹏
			name:'isAgreementFlight',//是否协议航班
			type :'string'
		},{
			name:'dailyAgreementVolume',//日均协议货量（公斤）
			type :'number'
		},{
			name:'belongsAirFreightDispatch',//所属空运总调
			type :'string'
		},{
			name:'belongsAirFreightDispatchName',//所属空运总调名称
			type:'string'
		 }]
});

/*
 * 定义：一个baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')的查询数据模型"Store"交互后台
 */
Ext.define('Foss.baseinfo.flight.FlightStore', {
	extend : 'Ext.data.Store',
	autoLoad : false,
	//页面条数定义
	pageSize : 10,
	//绑定model
	model : 'Foss.baseinfo.flight.FlightModel',
	proxy : {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryFlightList.action'),
		reader : {
			type : 'json',
			root : 'flightVo.flightList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_flight_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'flightVo.flight.planLeaveTime' : queryForm.findField('planLeaveTime').getValue(),
						'flightVo.flight.planArriveTime' : queryForm.findField('planArriveTime').getValue(),
						'flightVo.flight.airlines' : queryParams.airlines,
						'flightVo.flight.flightNo' : queryParams.flightNo,
						'flightVo.flight.flightSort' : queryParams.flightSort,
						'flightVo.flight.isAgreementFlight' : queryParams.isAgreementFlight,
						'flightVo.flight.departureAirport' : queryParams.departureAirport,
						'flightVo.flight.destinationAirport' : queryParams.destinationAirport
					}
				});
			}
		}
	}
});

/*
 * 定义：baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')内嵌在结果列表中的查询详细的窗口表单
 */
Ext.define('Foss.baseinfo.flight.FlightDetailForm', {
	extend : 'Ext.form.Panel',
	height : 270,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 120
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			xtype : 'combobox',
			name : 'flightSort',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsCategory'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			readOnly : true,
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id', {
				'valueCode' : '',
				'valueName' : baseinfo.flight.i18n('foss.baseinfo.all')
			})
		}, {
			name : 'flightNo',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsNumber'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'airlinesName',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.airlines'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'originatingStationName',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsOriginationStation'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'destinationStationName',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsDestinationStation'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'planLeaveTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.planedDepartureTme'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'origCode',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.originationStationCode'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'targetCode',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.destinationStationCode'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'planArriveTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.planedArrivalTme'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'flyTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.sailingTimeHours'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			name : 'aircraftType',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.aircraftModels'),
			readOnly : true,
			columnWidth : 0.9
		}, {
			xtype : 'label'
		},{
			//2014-01-20 王鹏
			xtype: 'fieldcontainer',
            defaultType: 'checkboxfield',
            readOnly : true,
			      columnWidth : 0.9,
            items: [
                {
                     boxLabel  : '是否协议航班',
                     name      : 'isAgreementFlight',
                     inputValue: '1'
                }]
		},{
			name : 'dailyAgreementVolume',
			fieldLabel : '日均协议货量（公斤）',
			readOnly : true,
			columnWidth : 0.9
		},{
			name : 'belongsAirFreightDispatchName',
			fieldLabel : '所属空运总调',
			readOnly : true,
			columnWidth : 0.9
		},{
			xtype : 'fieldcontainer',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsSchedule'),
			defaultType : 'checkboxfield',
			readOnly : true,
			layout : 'column',
			items : [{
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsMonday'),
					name : 'flyOnMonday',
					inputValue : 'Y',
					margin : '0 15 0 0'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsTursday'),
					name : 'flyOnTuesday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsWednesday'),
					name : 'flyOnWednesday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsThursday'),
					name : 'flyOnThursday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsFriday'),
					name : 'flyOnFriday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsSaturday'),
					name : 'flyOnSaturday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsSunday'),
					name : 'flyOnSunday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}
			],
			colspan : 3
		}]
});

/*
 * 定义：内嵌在结果列表中的查询详细的窗口
 */
Ext.define('Foss.baseinfo.flight.FlightDetailPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.flight.i18n('foss.baseinfo.flightsDetailInformation'),
	frame : true,
	height:270,
	informationForm : null,
	/**
	 * 获取航班详细信息
	 * @return {Object} FlightDetailForm
	 */
	getInformationForm : function () {
		if (this.informationForm == null) {
			this.informationForm = Ext.create('Foss.baseinfo.flight.FlightDetailForm');
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
		//处理详细面板数据转换
		var tempForm = this.getInformationForm().getForm();
		//2014-01-20 王鹏
		tempForm.findField('isAgreementFlight').setValue(record.get('isAgreementFlight') === 'Y' ? true : false);
		var planLeaveTime = record.get('planLeaveTime');
		if(Ext.isEmpty(planLeaveTime)){
			tempForm.findField('planLeaveTime').setValue('-');
		}else{
			var hours = planLeaveTime.getHours();
			var minutes = planLeaveTime.getMinutes();
			if(minutes.toString().length <= 1){
				minutes += '0';
			}
			tempForm.findField('planLeaveTime').setValue(hours + ":" + minutes);
			minutes = null;
			hours = null;
		}
		var planArriveTime = record.get('planArriveTime');
		if(Ext.isEmpty(planLeaveTime)){
			tempForm.findField('planArriveTime').setValue('-');
		}else{
			var hours = planArriveTime.getHours();
			var minutes = planArriveTime.getMinutes();
			if(minutes.toString().length <= 1){
				minutes += '0';
			}
			tempForm.findField('planArriveTime').setValue(hours + ":" + minutes);
			minutes = null;
			hours = null;
		}
		tempForm = null;
		planLeaveTime = null;
		planArriveTime = null;
	}
});

/*
 * 查询结果列表窗口
 */
Ext.define('Foss.baseinfo.flight.FlightGrid', {
	extend : 'Ext.grid.Panel',
	columnLines : true, // 增加表格列的分割线
	id : 'Foss_baseinfo_flight_FlightGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	frame : true, // 表格对象增加一个边框
	stripeRows : true,
	columnLines : true, // 增加表格列的分割线
	title : baseinfo.flight.i18n('foss.baseinfo.flightsInformation'), // 定义表格的标题
	collapsible : true,
	animCollapse : true,
	//表格多选插件
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	//表格行可展开的插件
	plugins : [{
			ptype : 'rowexpander',
			rowsExpander : false, // 定义行展开模式（单行与多行），默认是多行展开(值true)
			rowBodyElement : 'Foss.baseinfo.flight.FlightDetailPanel' // 行体内容
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
			this.addUpdateWindow = Ext.create('Foss.baseinfo.flight.FlightAddUpdateWindow');
		}
		this.addUpdateWindow.setOperationUrl((function () {
				var operationUrl = null;
				if (actionType === 'add') {
					operationUrl = baseinfo.realPath('addFlight.action');
				} else {}
				if (actionType === 'upd') {
					operationUrl = baseinfo.realPath('updateFlight.action');
				} else {}
				return operationUrl;
			})(actionType));
		this.addUpdateWindow.getAddUpdateForm().setTitle(windowTitle);
		if (actionType && actionType === 'add') {
			me.addUpdateWindow.getAddUpdateForm().getForm().reset();
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('dailyAgreementVolume').setDisabled(true);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('dailyAgreementVolume').setValue(null);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('belongsAirFreightDispatch').setDisabled(true);
			me.addUpdateWindow.getAddUpdateForm().getForm().findField('belongsAirFreightDispatch').setValue(null);
		} else {}
		if (actionType && actionType === 'upd') {
			//			me.addUpdateWindow.getAddUpdateForm().getForm().findField('idCard').setReadOnly(true);
		} else {}
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
			text : baseinfo.flight.i18n('foss.baseinfo.operate'),
			align : 'center',
			items : [{
					iconCls : 'deppon_icons_edit',
					tooltip : baseinfo.flight.i18n('foss.baseinfo.update'),
					disabled :!baseinfo.flight.isPermission('flight/flightEditButton'),
					/**
					 * baseinfo.flight.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 * @param {Object} grid 当前按钮
					 */
					handler : function (grid, rowIndex, colIndex, item) {
						var record = grid.getStore().getAt(rowIndex);
						var addUpdateWindow = grid.up().getAddUpdateWindow(item.tooltip, 'upd');
						addUpdateWindow.loadAddUpdateForm(record);
						//处理因checkbox提交值不正确问题
						var tempForm = addUpdateWindow.getAddUpdateForm().getForm();
						tempForm.findField('flyOnMonday').setValue(record.get('flyOnMonday') === 'Y' ? true : false);
						tempForm.findField('flyOnTuesday').setValue(record.get('flyOnTuesday') === 'Y' ? true : false);
						tempForm.findField('flyOnWednesday').setValue(record.get('flyOnWednesday') === 'Y' ? true : false);
						tempForm.findField('flyOnThursday').setValue(record.get('flyOnThursday') === 'Y' ? true : false);
						tempForm.findField('flyOnFriday').setValue(record.get('flyOnFriday') === 'Y' ? true : false);
						tempForm.findField('flyOnSaturday').setValue(record.get('flyOnSaturday') === 'Y' ? true : false);
						tempForm.findField('flyOnSunday').setValue(record.get('flyOnSunday') === 'Y' ? true : false);
						//2014-01-20 王鹏
						tempForm.findField('isAgreementFlight').setValue(record.get('isAgreementFlight') === 'Y' ? true : false);
						tempForm.findField('belongsAirFreightDispatch').setCombValue(record.get('belongsAirFreightDispatchName'),record.get('belongsAirFreightDispatch'));
						addUpdateWindow.show();
						addUpdateWindow = null;
						tempForm = null;
					}
				}, {
					iconCls : 'deppon_icons_delete',
					tooltip : baseinfo.flight.i18n('foss.baseinfo.void'),
					disabled :!baseinfo.flight.isPermission('flight/flightVoidButton'),
					/**
					 * baseinfo.flight.i18n('foss.baseinfo.update')响应事件
					 * @param {Object} grid 当前表格
					 * @param {Number} rowIndex 行索引
					 * @param {Number} colIndex 列索引
					 */
					handler : function (grid, rowIndex, colIndex) {
						Ext.MessageBox.show({
							title : baseinfo.flight.i18n('foss.baseinfo.confirmationPrompt'),
							msg : baseinfo.flight.i18n('foss.baseinfo.voidFlightsConfirmMessage'),
							buttons : Ext.Msg.YESNO,
							icon : Ext.MessageBox.QUESTION,
							fn : function (btn) {
								if (btn == 'yes') {
									//获取结果表格对象
									var record = grid.getStore().getAt(rowIndex);
									Ext.Ajax.request({
										url : baseinfo.realPath('deleteFlight.action'),
										jsonData : {
											'flightVo' : {
												'batchIds' : new Array(record.data.id)
											}
										},
										//baseinfo.flight.i18n('foss.baseinfo.void')成功
										success : function (response) {
											grid.up().getPagingToolbar().moveFirst();
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.flight.i18n('foss.baseinfo.successPrompt'),
												msg : json.message,
												width : 300,
												buttons : Ext.Msg.OK,
												icon : Ext.MessageBox.INFO
											});
										},
										//baseinfo.flight.i18n('foss.baseinfo.void')失败
										exception : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.MessageBox.show({
												title : baseinfo.flight.i18n('foss.baseinfo.failureInformationTips'),
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
			header : baseinfo.flight.i18n('foss.baseinfo.flightsNumber'),
			dataIndex : 'flightNo'
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.flightsCategory'),
			dataIndex : 'flightSort',
			renderer : function (value) {
				var val = FossDataDictionary.rendererSubmitToDisplay(value, 'AIR_FLIGHT_TYPE');
				if (Ext.isEmpty(val)) {
					return '-';
				} else {
					return val;
				}
			}
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.flightsModels'),
			dataIndex : 'aircraftType'
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.airlines'),
			dataIndex : 'airlinesName'
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.originationStationCode'),
			dataIndex : 'origCode'
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.flightsOriginationStation'),
			dataIndex : 'originatingStationName'
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.planedDepartureTme'),
			dataIndex : 'planLeaveTime',
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else{
					return Ext.Date.format(new Date(value), 'H:i');
				}
			}
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.destinationStationCode'),
			dataIndex : 'targetCode'
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.flightsDestinationStation'),
			dataIndex : 'destinationStationName'
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.planedArrivalTme'),
			dataIndex : 'planArriveTime',
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return '-';
				} else{
					return Ext.Date.format(new Date(value), 'H:i');
				}
			}
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.flightsSchedule'),
			dataIndex : 'extid',
			width : 250,
			renderer : function (value) {
				var me = this , val = '', temp = null;
				var model = me.getStore().getById(value);
				if((temp = model.get('flyOnMonday')) && !Ext.isEmpty(temp) && temp === 'Y') {
					val += baseinfo.flight.i18n('foss.baseinfo.flightsMondayBlank')
				}
				if((temp = model.get('flyOnTuesday')) && !Ext.isEmpty(temp) && temp === 'Y') {
					val += baseinfo.flight.i18n('foss.baseinfo.flightsTursdayBlank')
				}
				if((temp = model.get('flyOnWednesday')) && !Ext.isEmpty(temp) && temp === 'Y') {
					val += baseinfo.flight.i18n('foss.baseinfo.flightsWednesdayBlank')
				}
				if((temp = model.get('flyOnThursday')) && !Ext.isEmpty(temp) && temp === 'Y') {
					val += baseinfo.flight.i18n('foss.baseinfo.flightsThursdayBlank')
				}
				if((temp = model.get('flyOnFriday')) && !Ext.isEmpty(temp) && temp === 'Y') {
					val += baseinfo.flight.i18n('foss.baseinfo.flightsFridayBlank')
				}
				if((temp = model.get('flyOnSaturday')) && !Ext.isEmpty(temp) && temp === 'Y') {
					val += baseinfo.flight.i18n('foss.baseinfo.flightsSaturdayBlank')
				}
				if((temp = model.get('flyOnSunday')) && !Ext.isEmpty(temp) && temp === 'Y') {
					val += baseinfo.flight.i18n('foss.baseinfo.flightsSundayBlank')
				}
				temp = null;
				model = null;
				return val;
			}
		}, {
			header : baseinfo.flight.i18n('foss.baseinfo.sailingTimeHours'),
			dataIndex : 'flyTime'
		},{
			//2014-01-20 王鹏
			header : '是否协议航班',
			dataIndex : 'isAgreementFlight',
			renderer : function (value) {
				if (Ext.isEmpty(value)) {
					return null;
				} else{
					if(value === 'Y'){
						return '是';
					}else if(value === 'N'){
						return '否';
					};
				}
			}
		},{
			header : '日均协议货量（公斤）',
			dataIndex : 'dailyAgreementVolume'
		},{
			header : '所属空运总调',
			dataIndex : 'belongsAirFreightDispatchName'
		}
		//去掉了baseinfo.flight.i18n('foss.baseinfo.flightsSchedule')在gird中显示，改到了detail panel中
	],
	/**
	 * 构造函数
	 * @param {Object} config 构造函数配置项
	 */
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.flight.FlightStore');
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
						text : baseinfo.flight.i18n('foss.baseinfo.add'),
						width : 80,
						disabled:!baseinfo.flight.isPermission('flight/flightAddButton'),
						hidden:!baseinfo.flight.isPermission('flight/flightAddButton'),
						//弹出baseinfo.flight.i18n('foss.baseinfo.add')的航班的窗口
						handler : function (mine) {
							me.getAddUpdateWindow(mine.text, 'add').show();
						}
					}, {
						xtype : 'button',
						text : baseinfo.flight.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.flight.isPermission('flight/flightVoidButton'),
						hidden:!baseinfo.flight.isPermission('flight/flightVoidButton'),
						width : 80,
						/**
						 * 作废航班
						 */
						handler : function () {
							var selectionRecord = me.getSelectionModel().getSelection();
							if (selectionRecord && selectionRecord.length > 0) {
								var ids = new Array();
								for (var i = 0; i < selectionRecord.length; i++) {
									Ext.Array.include(ids, selectionRecord[i].data.id);
								}
								Ext.MessageBox.show({
									title : baseinfo.flight.i18n('foss.baseinfo.confirmationPrompt'),
									msg : baseinfo.flight.i18n('foss.baseinfo.voidFlightsConfirmMessage'),
									buttons : Ext.Msg.YESNO,
									icon : Ext.MessageBox.QUESTION,
									fn : function (btn) {
										if (btn == 'yes') {
											//获取结果表格对象
											Ext.Ajax.request({
												url : baseinfo.realPath('deleteFlight.action'),
												jsonData : {
													'flightVo' : {
														'batchIds' : ids
													}
												},
												//baseinfo.flight.i18n('foss.baseinfo.void')成功
												success : function (response) {
													me.getPagingToolbar().moveFirst();
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.flight.i18n('foss.baseinfo.successPrompt'),
														msg : json.message,
														width : 300,
														buttons : Ext.Msg.OK,
														icon : Ext.MessageBox.INFO
													});
												},
												//baseinfo.flight.i18n('foss.baseinfo.void')失败
												exception : function (response) {
													var json = Ext.decode(response.responseText);
													Ext.MessageBox.show({
														title : baseinfo.flight.i18n('foss.baseinfo.failureInformationTips'),
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
									title : baseinfo.flight.i18n('foss.baseinfo.failureInformationTips'),
									msg : baseinfo.flight.i18n('foss.baseinfo.anyOfTheSelectdRecord'),
									width : 300,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
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

/*****************************************************  定义baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')申请窗口 *****************************************************/
/*
 * 定义：baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')新增/修改的窗口表单
 */
Ext.define('Foss.baseinfo.flight.FlightAddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	isSearchComb : true,
	defaults : {
		margin : '5 25 5 25',
		labelWidth : 120
	},
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 3
	},
	items : [{
			xtype : 'combobox',
			name : 'flightSort',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsCategory'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.flightsCategoryCanNotBeEmpty'),
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			columnWidth : 0.5,
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('AIR_FLIGHT_TYPE', 'Foss_baseinfo_flight_FlightSortStore_Id')
		}, {
			name : 'flightNo',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsNumber'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.flightsNumberCanNotBeEmpty'),
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
//			listeners : {
//				/**
//				 * 模拟选择器：失去焦点获取航班信息
//				 * @param {Ext.Component} me 当前对象
//				 * @param {Ext.EventObject} event 当前触发事件对象
//				 */
//				blur : function (me, event) {
//					if (Ext.isEmpty(Ext.String.trim(me.getValue())) || me.readOnly) {
//						me.setValue(null);
//						return;
//					}
//					Ext.Ajax.request({
//						url : baseinfo.realPath('queryFlight.action'),
//						jsonData : {
//							'flightVo' : {
//								'flight' : {
//									'flightNo' : me.getValue()
//								}
//							}
//						},
//						success : function (response) {
//							//检测baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')是否已经存在
//							var json = Ext.decode(response.responseText);
//							if (Ext.isEmpty(json.flightVo.flight)) {
//								me.validatorMessage.validity = true;
//								me.validatorMessage.message = null;
//								me.clearInvalid();
//							} else {
//								me.validatorMessage.validity = false;
//								me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.flightsTheInformationAlreadyExists');
//								me.markInvalid(me.validatorMessage.message);
//								Ext.MessageBox.show({
//									title : baseinfo.flight.i18n('foss.baseinfo.failureInformationTips'),
//									msg : me.validatorMessage.message,
//									width : 300,
//									buttons : Ext.Msg.OK,
//									icon : Ext.MessageBox.WARNING
//								});
//							}
//						},
//						exception : function (response) {
//							var json = Ext.decode(response.responseText);
//							me.validatorMessage.validity = false;
//							me.validatorMessage.message = json.message;
//							Ext.MessageBox.show({
//								title : baseinfo.flight.i18n('foss.baseinfo.failureInformationTips'),
//								msg : json.message,
//								width : 300,
//								buttons : Ext.Msg.OK,
//								icon : Ext.MessageBox.WARNING
//							});
//						}
//					});
//				}
//			}
		}, {
			xtype : 'commonairlinesselector',
			name : 'airlines',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.airlines'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.flightsAirlinesCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5
		}, {
			xtype : 'commonairporwithcitynametselector',
			name : 'departureAirport',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsOriginationStation'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.flightsOriginatingStationCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5,
			displayField : 'cityName',// 显示名称
			valueField : 'cityName',// 值
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
			listeners: {
				select : function(me, records, eOpts){
					if(Ext.isEmpty(me.getValue())){
						return;
					}else{
						var tempForm = me.up('form').getForm();
						var destinationAirport = tempForm.findField('destinationAirport').getValue();
						if(!Ext.isEmpty(destinationAirport) && destinationAirport === me.getValue()){
							me.validatorMessage.validity = false;
							me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.flightsDestinationAndOriginatingCanNotSame');
							me.markInvalid(me.validatorMessage.message);
						}else{
							me.validatorMessage.validity = true;
							me.validatorMessage.message = null;
							me.clearInvalid();
							//业务数据处理
							tempForm.findField('origCode').setValue(records[0].get('airportCode'));
						}
						tempForm = null;
						destinationAirport = null;
					}
				}
			}
		}, {
			xtype : 'commonairporwithcitynametselector',
			name : 'destinationAirport',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsDestinationStation'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.flightsDestinationStationCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5,
			displayField : 'cityName',// 显示名称
			valueField : 'cityName',// 值
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
			listeners: {
				select : function(me, records, eOpts){
					if(Ext.isEmpty(me.getValue())){
						return;
					}else{
						var tempForm = me.up('form').getForm();
						var departureAirport = tempForm.findField('departureAirport').getValue();
						if(!Ext.isEmpty(departureAirport) && departureAirport === me.getValue()){
							me.validatorMessage.validity = false;
							me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.flightsOriginatngAndDestinationCanNotSame');
							me.markInvalid(me.validatorMessage.message);
						}else{
							me.validatorMessage.validity = true;
							me.validatorMessage.message = null;
							me.clearInvalid();
							//业务数据处理
							tempForm.findField('targetCode').setValue(records[0].get('airportCode'));
						}
						tempForm = null;
						departureAirport = null;
					}
				}
			}
		}, {
			xtype : 'timefield',
			name : 'planLeaveTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.planedDepartureTme'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.plannedDepartureTimeCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5,
			format : 'H:i',
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
			listeners: {
				blur : function(me, event){
					if(Ext.isEmpty(me.getValue())){
						return;
					}else{
						var tempForm = me.up('form').getForm();
						var planArriveTime = tempForm.findField('planArriveTime');
						//判断起飞时间
						if(Ext.isEmpty(planArriveTime.getValue())){
							return;
						}else{
							var leaveMinutesVal =  me.getValue().getHours() * 60 + me.getValue().getMinutes();
							var arriveMinutesVal = planArriveTime.getValue().getHours() * 60 + planArriveTime.getValue().getMinutes();
							var timeVal = leaveMinutesVal - arriveMinutesVal;
							//判断时间前后是否正确
							if(timeVal === 0){
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.plannedDepartureTimeEqualArrivalTime');
								me.markInvalid(me.validatorMessage.message);
							}else if(timeVal > 0){
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.plannedDepartureTimeAfterArrivalTime');
								me.markInvalid(me.validatorMessage.message);
							}else{
								planArriveTime.validatorMessage.validity = true;
								planArriveTime.validatorMessage.message = null;
								planArriveTime.clearInvalid();
								me.validatorMessage.validity = true;
								me.validatorMessage.message = null;
								me.clearInvalid();
								//设置航行需要的时间（小时）
								var time = parseFloat(-(timeVal) / 60);
								tempForm.findField('flyTime').setValue(time);
								time = null;
							}
							timeVal = null;
							arriveMinutesVal = null;
							leaveMinutesVal = null;
						}
						tempForm = null;
						planArriveTime = null;
					}
				}
			}
		}, {
			name : 'origCode',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.originationStationCode'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			name : 'targetCode',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.destinationStationCode'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'timefield',
			name : 'planArriveTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.planedArrivalTme'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.plannedArrivalTimeCanNotBeEmpty'),
			readOnly : false,
			columnWidth : 0.5,
			format : 'H:i',
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
			listeners: {
				blur : function(me, event){
					if(Ext.isEmpty(me.getValue())){
						return;
					}else{
						var tempForm = me.up('form').getForm();
						var planLeaveTime = tempForm.findField('planLeaveTime');
						//判断起飞时间
						if(Ext.isEmpty(planLeaveTime.getValue())){
							return;
						}else{
							var arriveMinutesVal =  me.getValue().getHours() * 60 + me.getValue().getMinutes();
							var leaveMinutesVal = planLeaveTime.getValue().getHours() * 60 + planLeaveTime.getValue().getMinutes();
							var timeVal = arriveMinutesVal - leaveMinutesVal;
							//判断时间前后是否正确
							if(timeVal === 0){
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.plannedArrivalTimeEqualDepartureTime');
								me.markInvalid(me.validatorMessage.message);
							}else if(timeVal < 0){
								me.validatorMessage.validity = false;
								me.validatorMessage.message = baseinfo.flight.i18n('foss.baseinfo.plannedArrivalTimeBeforeDepartureTime');
								me.markInvalid(me.validatorMessage.message);
							}else{
								planLeaveTime.validatorMessage.validity = true;
								planLeaveTime.validatorMessage.message = null;
								planLeaveTime.clearInvalid();
								me.validatorMessage.validity = true;
								me.validatorMessage.message = null;
								me.clearInvalid();
								//设置航行需要的时间（小时）
								var time = parseFloat(timeVal / 60);
								tempForm.findField('flyTime').setValue(time);
				 				time = null;
							}
							timeVal = null;
							leaveMinutesVal = null;
							arriveMinutesVal = null;
						}
						tempForm = null;
						planLeaveTime = null;
					}
				}
			}
		}, {
			xtype : 'commonaircrafttypeselector',
			name : 'aircraftType',
			showContent : '{code}',// 显示表格列
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.aircraftModels'),
			readOnly : false,
			columnWidth : 0.5
		}, {
			name : 'flyTime',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.sailingTimeHours'),
			readOnly : true,
			columnWidth : 0.5
		}, {
			xtype : 'label'
		},{
			//2014-01-20
			xtype : 'fieldcontainer',
            defaultType: 'checkboxfield',
            //fieldLabel:'是否协议航班',
            readOnly : false,
            forceSelection:true,
			columnWidth : 0.5,
            items: [
                {
                     boxLabel  : '是否协议航班',
                     name      : 'isAgreementFlight',
                     inputValue: '1',
                     listeners: {
           	   change:function(field, newValue, oldValue){
           	   	  var flag = field.getValue();
           	   	  var me = this;
           	   	  if(flag){
           	   	  	 me.up('form').getForm().findField('dailyAgreementVolume').setDisabled(false);
           	   	  	 me.up('form').getForm().findField('belongsAirFreightDispatch').setDisabled(false);
           	         me.up('form').getForm().findField('dailyAgreementVolume').allowBlank = false;
      	   	  	 	   me.up('form').getForm().findField('belongsAirFreightDispatch').allowBlank = false;
           	   	  	}else{
           	   	  	  me.up('form').getForm().findField('dailyAgreementVolume').setDisabled(true);
           	   	  	  me.up('form').getForm().findField('belongsAirFreightDispatch').setDisabled(true);
           	          me.up('form').getForm().findField('dailyAgreementVolume').setValue(null);
           	          me.up('form').getForm().findField('belongsAirFreightDispatch').setValue(null);
           	   	  	}
           	   	}
           	}
                }]
     },{
            xtype: 'numberfield',
			name : 'dailyAgreementVolume',
			fieldLabel : '日均协议货量（公斤）',
			readOnly : false,
			allowBlank : true,
			columnWidth : 0.5,
			decimalPrecision:3,
			step:0.01,
			minValue: 0,
			maxValue: 99999999
		},{
			xtype:'dynamicorgcombselector',
			type : 'ORG',
			airDispatch:'Y',
			name : 'belongsAirFreightDispatch',
			fieldLabel : '所属空运总调',
			readOnly : false,
			allowBlank : true,
			columnWidth : 0.9
		},{
			xtype : 'fieldcontainer',
			fieldLabel : baseinfo.flight.i18n('foss.baseinfo.flightsSchedule'),
			allowBlank : false,
			blankText : baseinfo.flight.i18n('foss.baseinfo.flightsSchedule'),
			defaultType : 'checkboxfield',
			layout : 'column',
			items : [{
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsMonday'),
					name : 'flyOnMonday',
					inputValue : 'Y',
					margin : '0 15 0 0'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsTursday'),
					name : 'flyOnTuesday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsWednesday'),
					name : 'flyOnWednesday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsThursday'),
					name : 'flyOnThursday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsFriday'),
					name : 'flyOnFriday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsSaturday'),
					name : 'flyOnSaturday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}, {
					boxLabel : baseinfo.flight.i18n('foss.baseinfo.flightsSunday'),
					name : 'flyOnSunday',
					inputValue : 'Y',
					margin : '0 15 0 25'
				}
			],
			colspan : 3
		}, {
			name : 'id',
			fieldLabel : 'ID',
			readOnly : true,
			hidden : true,
			columnWidth : 0.5,
			colspan : 3
		}
	],
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*
 * 定义baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')申请的表单窗口
 */
Ext.define('Foss.baseinfo.flight.FlightAddUpdateWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.flight.i18n('foss.baseinfo.flightsAddModifyInformation'),
	width : 1055,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	modal : true,
	resizable : false,
	closeAction : 'hide',
	addUpdateForm : null,
	getAddUpdateForm : function () {
		if (null == this.addUpdateForm) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.flight.FlightAddUpdateForm');
		}
		return this.addUpdateForm;
	},
	loadAddUpdateForm : function (record) {
		this.getAddUpdateForm().getForm().loadRecord(record);
		this.addUpdateFormModel = record;
	},
	addUpdateFormModel : null,
	getAddUpdateFormModel : function () {
		if (null == this.addUpdateFormModel) {
			this.addUpdateFormModel = Ext.create("Foss.baseinfo.flight.FlightModel");
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
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getAddUpdateForm(), {
				border : false,
				xtype : 'container',
				layout : 'column',
				margin : '15 0 15 220',
				defaults : {
					margin : '0 0 30 0'
				},
				items : [{
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.flight.i18n('foss.baseinfo.cancel'),
						handler : function () {
							me.hide().close();
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						xtype : 'button',
						columnWidth : .16,
						text : baseinfo.flight.i18n('foss.baseinfo.reset'),
						handler : function () {
							if (me.operationUrl.toString().indexOf('updateFlight') != -1) {
								me.loadAddUpdateForm(me.getAddUpdateFormModel());
							} else {}
							
							if (me.operationUrl.toString().indexOf('addFlight') != -1) {
								me.getAddUpdateForm().getForm().reset();
							} else {}
						}
					}, {
						border : false,
						columnWidth : .10,
						html : '&nbsp;'
					}, {
						columnWidth : .16,
						xtype : 'button',
						text : baseinfo.flight.i18n('foss.baseinfo.save'),
						plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
							seconds: 5
						}),
						listeners : {
							click : function (mine, event, options) {
								var addUpdateForm = me.getAddUpdateForm().getForm();
								if (addUpdateForm.isValid()) { //校验FORM是否通过校验
									var flightNo =addUpdateForm.findField('flightNo').getValue();
									//验证航班号长度是否超过6
									if(flightNo.length>6){
										Ext.MessageBox.show({
											title : baseinfo.flight.i18n('foss.baseinfo.tips'),
											msg : baseinfo.flight.i18n('foss.baseinfo.flightNoIsGreaterThanSix'),
											width : 300,
											buttons : Ext.Msg.OK,
											icon : Ext.MessageBox.WARNING
										});
										return;
									}
									if(!(addUpdateForm._fields.items[14].checked || addUpdateForm._fields.items[15].checked || addUpdateForm._fields.items[16].checked || addUpdateForm._fields.items[17].checked || addUpdateForm._fields.items[18].checked || addUpdateForm._fields.items[19].checked || addUpdateForm._fields.items[20].checked)){
										Ext.Msg.alert(baseinfo.flight.i18n('foss.baseinfo.flightsDateCanNotBeNull'));
										return false;
									}
									addUpdateForm.updateRecord(me.getAddUpdateFormModel());
									//处理因checkbox提交值问题
									var tempModel = me.getAddUpdateFormModel();
									
									var flyOnMonday = tempModel.get('flyOnMonday');
									tempModel.set('flyOnMonday', Ext.isBoolean(flyOnMonday) ? (flyOnMonday ? 'Y' : 'N') : (flyOnMonday) === 'true' ? 'Y' : 'N');
									flyOnMonday = null;
									
									var flyOnTuesday = tempModel.get('flyOnTuesday');
									tempModel.set('flyOnTuesday', Ext.isBoolean(flyOnTuesday) ? (flyOnTuesday ? 'Y' : 'N') : (flyOnTuesday) === 'true' ? 'Y' : 'N');
									flyOnTuesday = null;
									
									var flyOnWednesday = tempModel.get('flyOnWednesday');
									tempModel.set('flyOnWednesday', Ext.isBoolean(flyOnWednesday) ? (flyOnWednesday ? 'Y' : 'N') : (flyOnWednesday) === 'true' ? 'Y' : 'N');
									flyOnMonday = null;
									
									var flyOnThursday = tempModel.get('flyOnThursday');
									tempModel.set('flyOnThursday', Ext.isBoolean(flyOnThursday) ? (flyOnThursday ? 'Y' : 'N') : (flyOnThursday) === 'true' ? 'Y' : 'N');
									flyOnMonday = null;
									
									var flyOnFriday = tempModel.get('flyOnFriday');
									tempModel.set('flyOnFriday', Ext.isBoolean(flyOnFriday) ? (flyOnFriday ? 'Y' : 'N') : (flyOnFriday) === 'true' ? 'Y' : 'N');
									flyOnFriday = null;
									
									var flyOnSaturday = tempModel.get('flyOnSaturday');
									tempModel.set('flyOnSaturday', Ext.isBoolean(flyOnSaturday) ? (flyOnSaturday ? 'Y' : 'N') : (flyOnSaturday) === 'true' ? 'Y' : 'N');
									flyOnSaturday = null;
									
									var flyOnSunday = tempModel.get('flyOnSunday');
									tempModel.set('flyOnSunday', Ext.isBoolean(flyOnSunday) ? (flyOnSunday ? 'Y' : 'N') : (flyOnSunday) === 'true' ? 'Y' : 'N');
									flyOnSunday = null;
									
									//2014-01-20 王鹏
									var isAgreementFlight = tempModel.get('isAgreementFlight');
									tempModel.set('isAgreementFlight', Ext.isBoolean(isAgreementFlight) ? (isAgreementFlight ? 'Y' : 'N') : (isAgreementFlight) === 'true' ? 'Y' : 'N');
									if(!Ext.isBoolean(isAgreementFlight)){
										me.getAddUpdateFormModel().set('belongsAirFreightDispatch',addUpdateForm.findField('belongsAirFreightDispatch').getValue());
										me.getAddUpdateFormModel().set('dailyAgreementVolume',addUpdateForm.findField('dailyAgreementVolume').getValue());
								    }
									isAgreementFlight = null;
									
									Ext.Ajax.request({
										url : me.operationUrl.toString(),
										jsonData : {
											'flightVo' : {
												'flight' : me.getAddUpdateFormModel().data
											}
										},
										success : function (response) {
											var json = Ext.decode(response.responseText);
											Ext.getCmp('T_baseinfo-flight_content').getFlightGrid().store.load();
											Ext.MessageBox.show({
												title : baseinfo.flight.i18n('foss.baseinfo.successPrompt'),
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
												title : baseinfo.flight.i18n('foss.baseinfo.failureInformationTips'),
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
									tempModel = null;
								} else {
									top.Ext.MessageBox.show({
										title : baseinfo.flight.i18n('foss.baseinfo.failureInformationTips'),
										msg : (function () {
											var message = "<ul>";
											/*
											var idCardError = addUpdateForm.findField('idCard').validatorMessage.message;
											if(!Ext.isEmpty(idCardError)){
											message = "<li>" + addUpdateForm.findField('idCard').validatorMessage.message + "</li>";
											}
											 */
											addUpdateForm.getFields().filterBy(function (value) {
												return value.getErrors().length > 0;
											}).each(function (item, index, length) {
												message += "<li>" + item.getErrors() + "</li>";
											});
											return message + "</ul>";
										})(),
										width : 300,
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
		beforeshow : function(me, eOpts){
			var fielsds = me.getAddUpdateForm().getForm().getFields();
			if(!Ext.isEmpty(fielsds)){
				fielsds.each(function (item, index, length) {
					item.clearInvalid();
					item.unsetActiveError();
				});
			}
			fielsds = null;
		}
	}
});

/****************************************************  窗口初始化 *****************************************************/
Ext.onReady(function () {
	Ext.QuickTips.init();
	/*
	 * 创建查询表单"FORM"
	 */
	var queryForm = Ext.create('Foss.baseinfo.flight.QueryForm');
	/*
	 * 创建查询baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')结果列表结果窗口
	 */
	var queryResult = Ext.create('Foss.baseinfo.flight.FlightGrid');
	/*
	 * 执行页面的初始化布局
	 */
	Ext.getCmp('T_baseinfo-flight').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-flight_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询表单"FORM"
		getQueryForm : function () {
			return queryForm;
		},
		//获得查询baseinfo.flight.i18n('foss.baseinfo.baseinfo-flight.55')结果列表结果窗口
		getFlightGrid : function () {
			return queryResult;
		},
		items : [
			queryForm, //加入查询表单"FORM"
			queryResult //加入结果列表
		] 
	}));
});


