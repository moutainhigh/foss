/** -----------------------------------------------CalculateLoadForm------------------------------* */
Ext.define('Foss.adviseWorkNumber.CalculateLoadForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	columnWidth : 1,
	defaults : {
		margin : '5 5 5 5',
		columns : 4
	},
	items : [{
		xtype : 'numberfield',
		fieldLabel : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.loadEfficiency'),
		name : 'efficiencyPerPeople',
		columnWidth : 0.2
	}, {
		html : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.unit'),
		xtype : 'container',
		columnWidth : 0.1
	}, {
		xtype : 'button',
		text : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.createMsg'),
		columnWidth : 0.2,
		handler : function() {
			// 生成信息
			// 计算需要人员,向上取整
			var calculateLoadValues = this.ownerCt.getForm().getValues();
			var efficiencyPerPeople = calculateLoadValues.efficiencyPerPeople;
			if (efficiencyPerPeople == null || efficiencyPerPeople == 0
					|| efficiencyPerPeople == "") {
				Ext.MessageBox
						.alert(
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.hint'),
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.loadNotEmpty'));
				return false;
			}
			if (efficiencyPerPeople == "-" || efficiencyPerPeople < 0) {
				Ext.MessageBox
						.alert(
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.hint'),
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.loadNotNegative'));
				return false;
			}
			var format = function(value) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			};
			var needPeople = Math.ceil(scheduling.loadtotalWeight
					/ efficiencyPerPeople);
			var message = FossUserContext.getCurrentDept().name
					+ " "
					+ format(scheduling.loadforecastStartTime)
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.to')
					+ format(scheduling.loadforecastEndTime)
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.planStartWeight')
					+ scheduling.loadtotalWeight
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.weightUnit')
					+ scheduling.loadtotalVolume
					+ scheduling.loadtotalWeight
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.loadEfficiencyPerPeople')
					+ efficiencyPerPeople
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.loadUnits')
					+ needPeople
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.textMessage')
					+ format(new Date());
			this.ownerCt.getForm().findField('message').setValue(message);
		}
	}, {
		xtype : 'panel',
		height : 35,
		columnWidth : 0.5
	}, {
		html : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.needMsg'),
		xtype : 'container',
		columnWidth : 0.5
	}, {
		xtype : 'panel',
		height : 15,
		columnWidth : 0.5
	}, {
		xtype : 'textarea',
		name : 'message',
		columnWidth : 0.5
	}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/** -----------------------------------------------CalculateUnloadForm------------------------------* */
Ext.define('Foss.adviseWorkNumber.CalculateUnloadForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : false,
	columnWidth : 1,
	defaults : {
		margin : '5 5 5 5'
	},
	items : [{
		xtype : 'numberfield',
		fieldLabel : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.unloadEfficiency'),
		name : 'efficiencyPerPeople',
		columnWidth : 0.2
	}, {
		html : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.unit'),
		xtype : 'container',
		columnWidth : 0.1
	}, {
		xtype : 'button',
		text : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.createMsg'),
		columnWidth : 0.2,
		handler : function() {
			// 生成信息
			// 计算需要人员,向上取整
			var calculateUnloadValues = this.ownerCt.getForm().getValues();;
			var efficiencyPerPeople = calculateUnloadValues.efficiencyPerPeople;
			if (efficiencyPerPeople == null || efficiencyPerPeople == 0
					|| efficiencyPerPeople == "") {
				Ext.MessageBox
						.alert(
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.hint'),
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.unloadNotEmpty'));
				return false;
			}
			if (efficiencyPerPeople == "-" || efficiencyPerPeople < 0) {
				Ext.MessageBox
						.alert(
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.hint'),
								scheduling.adviseWorkNumber
										.i18n('foss.scheduling.adviseWorkNumber.unloadNotNegative'));
				return false;
			}
			var format = function(value) {
				var date = new Date(value);
				return Ext.Date.format(date, 'Y-m-d H:i:s');
			};
			var needPeople = Math.ceil(scheduling.unloadtotalWeight
					/ efficiencyPerPeople);
			var message = FossUserContext.getCurrentDept().name
					+ " "
					+ format(scheduling.unloadforecastStartTime)
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.to')
					+ format(scheduling.unloadforecastEndTime)
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.planStartWeight')
					+ scheduling.unloadtotalWeight
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.weightUnit')
					+ scheduling.unloadtotalVolume
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.unloadEfficiencyPerPeople')
					+ efficiencyPerPeople
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.unloadUnits')
					+ needPeople
					+ scheduling.adviseWorkNumber
							.i18n('foss.scheduling.adviseWorkNumber.textMessage')
					+ format(new Date());
			this.ownerCt.getForm().findField('message').setValue(message);
		}
	}, {
		xtype : 'panel',
		height : 35,
		columnWidth : 0.5
	}, {
		html : scheduling.adviseWorkNumber
				.i18n('foss.scheduling.adviseWorkNumber.needMsg'),
		xtype : 'container',
		columnWidth : 0.5
	}, {
		xtype : 'panel',
		height : 15,
		columnWidth : 0.5
	}, {
		xtype : 'textarea',
		name : 'message',
		columnWidth : 0.5
	}],

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/** -----------------------------------------------CountVehicleModel------------------------------* */
Ext.define('Foss.adviseWorkNumber.CountVehicleModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'vehicleType',
						type : 'string'
					}, {
						name : 'count',
						type : 'float'
					}]
		});

/** -----------------------------------------------CountVehicleStore------------------------------* */
Ext.define('Foss.adviseWorkNumber.CountVehicleStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.adviseWorkNumber.CountVehicleModel'
		});

/** -----------------------------------------------CountArriveVehicleGrid------------------------------* */
Ext.define('Foss.adviseWorkNumber.CountArriveVehicleGrid', {
			extend : 'Ext.grid.Panel',
			emptyText : scheduling.adviseWorkNumber
					.i18n('foss.scheduling.adviseWorkNumber.queryEmpty'),
			selType : 'rowmodel',
			autoScroll : true,
			columnLines : true,
			frame : false,
			stripeRows : true,
			columnWidth : 0.51,
			bodyCls : 'autoHeight',
			columns : [{
						// 车辆型号
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.vehicleType'),
						// 关联model中的字段名
						dataIndex : 'vehicleType',
						flex : 0.7
					}, {
						// 到达车辆台数
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.arriveCount'),
						dataIndex : 'count',
						flex : 0.3
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.adviseWorkNumber.CountVehicleStore');
				me.callParent([cfg]);
			}
		});

/** -----------------------------------------------CountDepartVehicleGrid------------------------------* */
Ext.define('Foss.adviseWorkNumber.CountDepartVehicleGrid', {
			extend : 'Ext.grid.Panel',
			emptyText : scheduling.adviseWorkNumber
					.i18n('foss.scheduling.adviseWorkNumber.queryEmpty'),
			selType : 'rowmodel',
			autoScroll : true,
			columnLines : true,
			frame : false,
			stripeRows : true,
			columnWidth : 0.51,
			bodyCls : 'autoHeight',
			columns : [{
						// 车辆型号
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.vehicleType'),
						// 关联model中的字段名
						dataIndex : 'vehicleType',
						flex : 0.7
					}, {
						// 出发车辆台数
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.startCount'),
						dataIndex : 'count',
						flex : 0.3
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.adviseWorkNumber.CountVehicleStore');
				me.callParent([cfg]);
			}
		});

/** -----------------------------------------------ForecastModel------------------------------* */
Ext.define('Foss.adviseWorkNumber.ForecastModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'forecastStartTime',
						type : 'date',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'forecastEndTime',
						type : 'date',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}, {
						name : 'totalWeight',
						type : 'float'
					}, {
						name : 'totalVolume',
						type : 'float'
					}]
		});

/** -----------------------------------------------ForecastStore------------------------------* */
Ext.define('Foss.adviseWorkNumber.ForecastStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.adviseWorkNumber.ForecastModel'
		});

/** -----------------------------------------------ForecastGrid------------------------------* */
Ext.define('Foss.adviseWorkNumber.ForecastGrid', {
			extend : 'Ext.grid.Panel',
			emptyText : scheduling.adviseWorkNumber
					.i18n('foss.scheduling.adviseWorkNumber.queryEmpty'),
			autoScroll : true,
			columnLines : true,
			frame : false,
			stripeRows : true,
			height : 240,
			columnWidth : 0.51,
			columns : [{
						// 起始时间
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.startTime'),
						// 关联model中的字段名
						dataIndex : 'forecastStartTime',
						flex : 1
					}, {
						// 截止时间
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.endTime'),
						dataIndex : 'forecastEndTime',
						flex : 1
					}, {
						// 总重量
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.totalWeight'),
						dataIndex : 'totalWeight',
						flex : 1
					}, {
						// 总体积
						header : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.totalVolume'),
						dataIndex : 'totalVolume',
						flex : 1
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.adviseWorkNumber.ForecastStore');
				me.callParent([cfg]);
			}
		});

/** -----------------------------------------------unloadPanel------------------------------* */
Ext.define('Foss.adviseWorkNumber.unloadPanel', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var ForecastGrid = Ext.create('Foss.adviseWorkNumber.ForecastGrid');
		var CountArriveVehicleGrid = Ext
				.create('Foss.adviseWorkNumber.CountArriveVehicleGrid');
		var CalculateUnloadForm = Ext
				.create('Foss.adviseWorkNumber.CalculateUnloadForm');
		var top = Ext.create('Foss.adviseWorkNumber.PanelTopForm');
		me.items = [top, ForecastGrid, CountArriveVehicleGrid,
				CalculateUnloadForm];
		me.callParent([cfg]);
		Ext.Ajax.request({
			url : scheduling.realPath('queryArrive.action'),
			params : {
				'adviseWorkNumberVO.orgCode' : FossUserContext
						.getCurrentDeptCode()
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				// 获取预测数据
				var forecastInfo = result.adviseWorkNumberVO.adviseWorkNumberDto;
				// 绑定ForecastGrid
				var forecastInfoRecord = Ext.ModelManager.create(forecastInfo,
						'Foss.adviseWorkNumber.ForecastModel');
				ForecastGrid.store.insert(0, forecastInfoRecord);
				// 获取车辆统计数据
				var countArray = new Array();
				var countList = result.adviseWorkNumberVO.adviseWorkNumberDto.countVehicleList;
				if (countList && countList.length == 0) {
					countList = [];
					CountArriveVehicleGrid.store.removeAll();
				}
				// 绑定CountArriveVehicleGrid
				CountArriveVehicleGrid.store.loadRawData(countList);
				// 获取效率数据
				var efficiencyPerPeople = result.adviseWorkNumberVO.efficiencyPerPeople;
				// 设置初始效率值
				CalculateUnloadForm.getForm().findField('efficiencyPerPeople')
						.setValue(efficiencyPerPeople);
				var date = new Date(forecastInfo.statisticsTime);
				top.getForm().findField('lastForecastTime').setValue(Ext.Date
						.format(date, 'Y-m-d H:i:s'));
				// 设置全局变量
				scheduling.unloadforecastStartTime = forecastInfo.forecastStartTime;
				scheduling.unloadforecastEndTime = forecastInfo.forecastEndTime;
				scheduling.unloadtotalWeight = forecastInfo.totalWeight;
				scheduling.unloadtotalVolume = forecastInfo.totalVolume;
				scheduling.unloadefficiencyPerPeople = efficiencyPerPeople;
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(scheduling.adviseWorkNumber
								.i18n('foss.scheduling.adviseWorkNumber.hint'),
						result.message, 'error');
			}
		})
	}
});

/** -----------------------------------------------PanelTopForm------------------------------* */
Ext.define('Foss.adviseWorkNumber.PanelTopForm', {
	extend : 'Ext.form.Panel',
	layout : 'column',
	bodyStyle : 'padding:5px 5px 10px',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : false,
	defaultType : 'textfield',
	columnWidth : 1,
	defaults : {
		margin : '5 5 0 5',
		labelWidth : 120
	},
	items : [{
				fieldLabel : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.lastForecastTime'),
				name : 'lastForecastTime',
				allowBlank : true,
				columnWidth : .3,
				readOnly : true

			}, {
				xtype : 'container',
				html : '<a href="javascript:scheduling.gotoForecastPage()">'+scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.queryForecast')+'</a>',
				columnWidth : .21
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

// 点击超联接,跳转到货量预测页
scheduling.gotoForecastPage = function() {
	var action;
	var tab = scheduling.adviseWorkNumber.MainTabPanel.getActiveTab();
	// 获取当前页签, 如果是装车页,则预测类型为depart,否则为arrive
	if (tab.itemId == "temporary") {
		action = 'DEPART';
	} else {
		action = 'ARRIVE';
	}
	addTab(
			'T_scheduling-forecastQuantityIndex',// 对应打开的目标页面js的onReady里定义的renderTo
			scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.forecast'),// 打开的Tab页的标题
			scheduling.realPath('forecastQuantityIndex.action') + '?action='
					+ action);// 对应的页面URL，可以在url后使用?x=123这种形式传参
}

/** -----------------------------------------------loadPanel------------------------------* */
Ext.define('Foss.adviseWorkNumber.loadPanel', {
	extend : 'Ext.panel.Panel',
	frame : false,
	layout : 'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var ForecastGrid = Ext.create('Foss.adviseWorkNumber.ForecastGrid');
		var CountDepartVehicleGrid = Ext
				.create('Foss.adviseWorkNumber.CountDepartVehicleGrid');
		var CalculateLoadForm = Ext
				.create('Foss.adviseWorkNumber.CalculateLoadForm');
		var top = Ext.create('Foss.adviseWorkNumber.PanelTopForm');
		me.items = [top, ForecastGrid, CountDepartVehicleGrid,
				CalculateLoadForm];
		me.callParent([cfg]);
		Ext.Ajax.request({
			url : scheduling.realPath('queryDepart.action'),
			params : {
				'adviseWorkNumberVO.orgCode' : FossUserContext
						.getCurrentDeptCode()
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				// 获取预测数据
				var forecastInfo = result.adviseWorkNumberVO.adviseWorkNumberDto;
				// 绑定ForecastGrid
				var forecastInfoRecord = Ext.ModelManager.create(forecastInfo,
						'Foss.adviseWorkNumber.ForecastModel');
				ForecastGrid.store.insert(0, forecastInfoRecord);
				// 获取车辆统计数据
				var countArray = new Array();
				var countList = result.adviseWorkNumberVO.adviseWorkNumberDto.countVehicleList;
				if (countList && countList.length == 0) {
					countList = [];
					CountDepartVehicleGrid.store.removeAll();
				}
				// 绑定CountArriveVehicleGrid
				CountDepartVehicleGrid.store.loadRawData(countList);
				// 获取效率数据
				var efficiencyPerPeople = result.adviseWorkNumberVO.efficiencyPerPeople;
				// 设置初始效率值
				CalculateLoadForm.getForm().findField('efficiencyPerPeople')
						.setValue(efficiencyPerPeople);

				var date = new Date(forecastInfo.statisticsTime);
				top.getForm().findField('lastForecastTime').setValue(Ext.Date
						.format(date, 'Y-m-d H:i:s'));
				// 设置全局变量
				scheduling.loadforecastStartTime = forecastInfo.forecastStartTime;
				scheduling.loadforecastEndTime = forecastInfo.forecastEndTime;
				scheduling.loadtotalWeight = forecastInfo.totalWeight;
				scheduling.loadtotalVolume = forecastInfo.totalVolume;
				scheduling.loadefficiencyPerPeople = efficiencyPerPeople;
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg(scheduling.adviseWorkNumber
								.i18n('foss.scheduling.adviseWorkNumber.hint'),
						result.message, 'error');
			}
		})
	}
});

/** -----------------------------------------------TabPanel------------------------------* */
Ext.define('Foss.adviseWorkNumber.MainTabPanel', {
			extend : 'Ext.tab.Panel',
			cls : "innerTabPanel",
			bodyCls : "overrideChildLeft",
			activeTab : 0,
			autoScroll : false,
			frame : false,
			items : [{
						tabConfig : {
							width : 100
						},
						itemId : 'temporary',
						title : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.loadCount'),// 装车人数页签
						items : Ext.create('Foss.adviseWorkNumber.loadPanel')
					}, {
						tabConfig : {
							width : 100
						},
						itemId : 'forever',
						title : scheduling.adviseWorkNumber.i18n('foss.scheduling.adviseWorkNumber.unloadCount'),// 卸车人数页签
						items : Ext.create('Foss.adviseWorkNumber.unloadPanel')
					}]
		});
/** -----------------------------------------------viewpanel--------------------------------------------------* */
Ext.onReady(function() {
			Ext.QuickTips.init();
			scheduling.adviseWorkNumber.MainTabPanel = Ext
					.create('Foss.adviseWorkNumber.MainTabPanel');
			Ext.create('Ext.panel.Panel', {
						id : 'T_scheduling-adviseWorkNumberIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContent-body',
						items : [scheduling.adviseWorkNumber.MainTabPanel],
						renderTo : 'T_scheduling-adviseWorkNumberIndex-body'
					});
		});