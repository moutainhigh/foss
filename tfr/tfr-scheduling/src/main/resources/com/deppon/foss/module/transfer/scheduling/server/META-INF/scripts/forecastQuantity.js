(function(){
	scheduling.forecastQuantity.forecastDate;
	// 根据出发到达部门,查询当前时间属于哪个预测日期
	Ext.Ajax.request({
		url : scheduling.realPath('nowForecastDate.action'),
		params : {
			'forecastVO.forecastQuantityEntity.belongOrgCode' : FossUserContext
					.getCurrentDeptCode()
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			// 获取预测时间区段
			scheduling.forecastQuantity.forecastDate = Ext.Date.format(new Date(result.forecastVO.maxStatisticsTime), 'Y-m-d');
			if (null == scheduling.forecastQuantity.forecastDate) {
				Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'),
						scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.cantFindTimeDuration'));
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), result.message, 'error');
		}
	});
})();

// 查询条件
Ext.define('Foss.forecastQuantity.QueryForm', {
	extend : 'Ext.form.Panel',
	title : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.queryForecast'),
	frame : true,
	collapsible : true,
	animCollapse : true,
	typeCombo : null,
	getTypeCombo : function() {
		var me = this;
		if (me.typeCombo == null) {
			me.typeCombo = FossDataDictionary.getDataDictionaryCombo(
					'TFR_FORECAST_TYPE', {
						name : 'type',
						emptyText : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.selectType'),
						allowBlank : false,
						editable : false,
						margin : '5 0 5 340',
						value : scheduling.forecastQuantity.action,
						width : 300,
						blankText : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.typeCantEmpty'),
						fieldLabel : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.forecastType'),
						listeners : {
							change : function(field, newValue, oldValue, eOpts) {
								me.getTimeCombo().store.load();
							}
						}
					});
		}
		return me.typeCombo;
	},
	timeCombo : null,
	getTimeCombo : function() {
		var me = this;
		if (me.timeCombo == null) {
			var store = Ext.create('Ext.data.Store', {
				autoLoad : true,
				fields : [{
							name : 'forecastDate',
							type : 'date',
							convert : function(value, record) {
								if (!Ext.isEmpty(record)
										&& !Ext.isEmpty(record.raw)) {
									var date = new Date(record.raw);
									return Ext.util.Format.date(date, 'Y-m-d');
								} else {
									return null;
								}
							}
						}],
				proxy : {
					type : 'ajax',
					actionMethods : 'POST',
					url : scheduling.realPath('queryTimeList.action'),
					reader : {
						type : 'json',
						root : 'forecastVO.forecastDateList'
					}
				},
				listeners : {
					load : function(store, records, successful, eOpts) {
						// scheduling.forecastQuantity.maxStatisticsTime =
						// store.getProxy().getReader().rawData.forecastVO.maxStatisticsTime;
						if (records.length > 0) {
							me.timeCombo.setValue(records[0]);
						} else {
							Ext.ux.Toast.msg(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.cantFindForecastTime'), 'error');
							me.timeCombo.setValue(null);
						}
					},
					beforeload : function(store, operation) {
						Ext.apply(operation, {
							params : {
								'forecastVO.forecastQuantityEntity.type' : me
										.getTypeCombo().getValue(),
								'forecastVO.forecastQuantityEntity.belongOrgCode' : FossUserContext
										.getCurrentDeptCode()
							}
						});
					}
				}
			});
			me.timeCombo = Ext.create('Ext.form.ComboBox', {
						name : 'forecastTime',
						emptyText : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.selectTime'),
						fieldLabel : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.time'),
						editable : false,
						queryMode : 'local',
						allowBlank : false,
						blankText : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.timeCantEmpty'),
						margin : '5 0 5 340',
						width : 300,
						value : store.getAt(0),
						displayField : 'forecastDate',
						valueField : 'forecastDate',
						store : store
					});
		}
		return this.timeCombo;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getTypeCombo(), me.getTimeCombo(), {
					xtype : 'dynamicorgcombselector',
					type : 'ORG',
					transferCenter: 'Y',
					salesDepartment : 'Y',
					airDispatch: 'Y',
					name : 'relevantOrgCode',
					margin : '0 0 0 340',
					labelWidth : 100,
					fieldLabel : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.relevantOrg'),
					emptyText : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.selectRelevantOrg'),
					width : 300
				}, {
					xtype : 'container',
					layout : 'hbox',
					margin : '5 0 0 540',
					items : [{
						xtype : 'button',
						disabled : !scheduling.forecastQuantity.isPermission('scheduling/queryForecastByPageButton'),
						hidden : !scheduling.forecastQuantity.isPermission('scheduling/queryForecastByPageButton'),
						text : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.query'),
						cls : 'yellow_button',
						arrowAlign : 'bottom',
						minWidth : 93,
						handler : function() {
							if (!me.getForm().isValid()) {
								Ext.ux.Toast.msg(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.needMustItem'), 'error');
								return;
							}
							scheduling.forecastQuantity.queryResult
									.getPagingToolbar().moveFirst();
						}
					}]
				}];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.ForecastQuantityModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'forecastQuantityId',
						type : 'string'
					}, {
						name : 'belongOrgCode',
						type : 'string'
					}, {
						name : 'belongOrgCodeName',
						type : 'string'
					}, {
						name : 'region',// 区域
						type : 'string'
					}, {
						name : 'regionName',// 区域
						type : 'string'
					}, {
						name : 'relevantOrgCode',// 目的地
						type : 'string'
					}, {
						name : 'relevantOrgCodeName',// 目的地
						type : 'string'
					}, {
						name : 'weightTotal',// 总重量
						type : 'float'
					}, {
						name : 'volumeTotal',// 总体积
						type : 'float'
					}, {
						name : 'waybillQtyTotal',// 总票数
						type : 'int'
					}, {
						name : 'gpsEnabledResWeightTotal',// 卡航重量
						type : 'float'
					}, {
						name : 'gpsEnabledResVolumeTotal',// 卡航体积
						type : 'float'
					}, {
						name : 'gpsEnabledResQtyTotal',// 卡航票数
						type : 'int'
					}, {
						name : 'precisionIfsWeightTotal',// 城运重量
						type : 'float'
					}, {
						name : 'precisionIfsVolumeTotal',// 城运体积
						type : 'float'
					}, {
						name : 'precisionIfsQtyTotal',// 城运票数
						type : 'int'
					}, {
						name : 'expressWeightTotal',// 快递重量
						type : 'float'
					}, {
						name : 'expressVolumeTotal',// 快递体积
						type : 'float'
					}, {
						name : 'expressQtyTotal',// 快递票数
						type : 'int'
					}, {
						name : 'inventoryWeightTotal',// 在库重量
						type : 'float'
					}, {
						name : 'inventoryVolumeTotal',// 在库体积
						type : 'float'
					}, {
						name : 'inventoryQtyTotal',// 在库票数
						type : 'int'
					}, {
						name : 'billingWeightTotal',// 开单重量
						type : 'float'
					}, {
						name : 'billingVolumeTotal',// 开单体积
						type : 'float'
					}, {
						name : 'billingQtyTotal',// 开单票数
						type : 'int'
					}, {
						name : 'intransitWeightTotal',// 在途重量
						type : 'float'
					}, {
						name : 'intransitVolumeTotal',// 在途体积
						type : 'float'
					}, {
						name : 'intransitQtyTotal',// 在途票数
						type : 'int'
					}, {
						name : 'deviationVolume',// 合车体积
						type : 'string'
					}, {
						name : 'statisticsTime',// 预测发起时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'type',// 预测类型 出发/到达
						type : 'string'
					}, {
						name : 'forecastTime',// 预测的日期
						type : 'date',
						convert : dateConvert
					}, {
						name : 'statisticsDate',// 预测发起日期
						type : 'date',
						convert : dateConvert
					}, {
						name : 'statisticsHHMM',// 预测发起小时分钟
						type : 'string'
					}]
		});

// 地区的store
Ext.define('Foss.forecastQuantity.QueryResultStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.forecastQuantity.ForecastQuantityModel',
	pageSize : 5,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : scheduling.realPath('queryForecastByPage.action'),
		reader : {
			type : 'json',
			root : 'forecastVO.forecastQuantityList',
			totalProperty: 'totalCount'
		}
	},
	listeners : {
		beforeload : function(store, operation) {
			var queryForm = scheduling.forecastQuantity.queryForm.getValues();
			Ext.apply(operation, {
				params : {
					'forecastVO.forecastQuantityEntity.type' : queryForm.type,
					'forecastVO.forecastQuantityEntity.relevantOrgCode' : queryForm.relevantOrgCode,
					'forecastVO.forecastQuantityEntity.forecastTime' : queryForm.forecastTime,
					'forecastVO.forecastQuantityEntity.belongOrgCode' : FossUserContext
							.getCurrentDeptCode()
				}
			});
		},
		load : function(store, records, successful, epots) {
			if (store.getCount() == 0) {
				Ext.ux.Toast.msg(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.cantFindResult'), 'error');
			}
			var forecastStartTime = new Date(store.getProxy().getReader().rawData.forecastVO.forecastStartTime), forecastEndTime = new Date(store
					.getProxy().getReader().rawData.forecastVO.forecastEndTime), timeRande = Ext.Date
					.format(forecastStartTime, 'Y-m-d H:i:s')
					+ '-' + Ext.Date.format(forecastEndTime, 'Y-m-d H:i:s');
			scheduling.forecastQuantity.queryResult.getTimeRangeLable()
					.update(timeRande);
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

scheduling.forecastQuantity.detailRecord = function(records, field, pram) {
	var i = 0, length = records.length, total, record, weight = 0, volume = 0, votes = 0;
	for (; i < length; ++i) {
		record = records[i];
		weight += parseFloat(record.get(field + 'Weight' + pram));
		volume += parseFloat(record.get(field + 'Volume' + pram));
		votes += parseFloat(record.get(field + 'Qty' + pram));
	}
	total = weight.toFixed(2) + '/' + volume.toFixed(2) + '/' + votes;
	return total;
};

//计算总体积,多出调用
scheduling.forecastQuantity.sumRecord = function(records, field) {
	var i = 0, length = records.length, total, record, sum = 0;
	for (; i < length; ++i) {
		record = records[i];
		sum += parseFloat(record.get(field));
	}
	
	//xiaobc update   start ............................
/*	if(field == 'volumeTotal'){
		var weight = scheduling.forecastQuantity.sumRecord(records,'weightTotal');
		sum = weight*scheduling.forecastQuantity.volumeRatio;
	}*/
//xiaobc update   end ............................
	
	total = sum.toFixed(2);
	return total;
};

Ext.define('Foss.forecastQuantity.JoinCarWindow', {
	extend : 'Ext.window.Window',
	title : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.joinCarChange'),
	width : 500,
	height : 280,
	modal : true,
	fromRecord : null,
	toRecord : null,
	resizable : false,
	layout : 'column',
	// 调整赋值方法,当点左键头时为右边向左边调整,值为正,反之为负.
	adjust : function(value) {
		var me = this;
		var adjustVolumeObject = me.adjustInputPanel.getForm()
				.findField('adjustVolume');
		if (null == adjustVolumeObject.getValue()) {
			Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.modifyCantEmpty'));
			return false;
		}
		var adjustVolume = parseFloat(adjustVolumeObject.getValue());
		// 左侧的改变值计算
		var one_path_adjust_volumeObject = me.lineInfoOnePanel.getForm()
				.findField('one_path_adjust_volume');
		var one_path_adjust_volume = one_path_adjust_volumeObject.getValue();
		// 如果改变值为空,则是第一次改变,取原值
		if (one_path_adjust_volume == null || one_path_adjust_volume == "") {
			one_path_adjust_volume = parseFloat(me.lineInfoOnePanel.getForm()
					.findField('oneOrigVolume').getValue());
		}
		// 改变后新改变值为之前的改变值加改变量
		var oneAdjustVolumeValue = (parseFloat(one_path_adjust_volume) + adjustVolume
				* value).toFixed(2);
		// 如果改变后值为负则不能改变
		if (oneAdjustVolumeValue < 0) {
			Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.modifyBecomeNegative'));
			return false;
		}
		// 右侧的改变值计算
		var two_path_adjust_volumeObject = me.lineInfoTwoPanel.getForm()
				.findField('two_path_adjust_volume');
		var two_path_adjust_volume = two_path_adjust_volumeObject.getValue();
		// 如果改变值为空,则是第一次改变,取原值
		if (two_path_adjust_volume == null || two_path_adjust_volume == "") {
			two_path_adjust_volume = parseFloat(me.lineInfoTwoPanel.getForm()
					.findField('twoOrigVolume').getValue());
		}
		// 改变后新改变值为之前的改变值减改变量
		var twoAdjustVolumeValue = (parseFloat(two_path_adjust_volume) - adjustVolume
				* value).toFixed(2);
		// 如果改变后值为负则不能改变
		if (twoAdjustVolumeValue < 0) {
			Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.modifyBecomeNegative'));
			return false;
		}
		me.adjustVolume = me.adjustVolume + adjustVolume * value;
		one_path_adjust_volumeObject.setValue(oneAdjustVolumeValue);
		two_path_adjust_volumeObject.setValue(twoAdjustVolumeValue);
	},
	bindData : function(fromRecord, toRecord) {
		var me = this;
		me.adjustVolume = 0;
		me.fromRecord = fromRecord;
		me.toRecord = toRecord;
		me.lineInfoOnePanel.bindData(fromRecord);
		me.lineInfoTwoPanel.bindData(toRecord);
		me.adjustInputPanel.bindData(0);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.adjustVolume = 0;
		// 保存form到全局变量中
		me.lineInfoOnePanel = Ext.create(
				'Foss.forecastQuantity.JoinCarAdjust.LineInfoOnePanel', {
					columnWidth : 0.35
				});
		me.adjustInputPanel = Ext.create(
				'Foss.forecastQuantity.JoinCarAdjust.AdjustInputPanel', {
					columnWidth : 0.3
				});
		me.lineInfoTwoPanel = Ext.create(
				'Foss.forecastQuantity.JoinCarAdjust.LineInfoTwoPanel', {
					columnWidth : 0.35
				});
		me.operatePanel = Ext.create(
				'Foss.forecastQuantity.JoinCarAdjust.OperatePanel', {
					columnWidth : .95
				});
		me.items = [me.lineInfoOnePanel, me.adjustInputPanel,
				me.lineInfoTwoPanel, me.operatePanel];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.QueryResult', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	enableColumnHide : false,
    //增加表格列的分割线
	columnLines: true,
	// 表格对象增加一个边框
	frame : true,
	collapsible: true,
	animCollapse: false,
	// 定义表格的标题
	title : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.forecast'),
	selModel : null,
	store : null,
	features : [{
				ftype : 'groupingsummary',
				hideGroupedHeader : true
			}],
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (me.pagingToolbar == null) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						dock : 'bottom',
						store : me.store
					});
		}
		return me.pagingToolbar;
	},
	joinCarAdjustWin : null,
	getJoinCarAdjustWin : function(fromRecord, toRecord) {
		var me = this;
		me.joinCarAdjustWin = Ext.create('Foss.forecastQuantity.JoinCarWindow');
		me.joinCarAdjustWin.bindData(fromRecord, toRecord);
		return me.joinCarAdjustWin;
	},
	timeRangeLable : null,
	getTimeRangeLable : function() {
		if (this.timeRangeLable == null) {
			this.timeRangeLable = Ext.create('Ext.form.Label');
		}
		return this.timeRangeLable;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.store = Ext.create('Foss.forecastQuantity.QueryResultStore', {
					groupField : 'regionName'
				});
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			padding : '0 5 0 5',
			items : [{
				xtype : 'button',
				text : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.joinCarChange'),
				handler : function() {
					var form = scheduling.forecastQuantity.queryForm, store = form
							.getTimeCombo().getStore();
					win = this.up('grid').getLineGoodChartsWin(), formData = form
							.getValues();
					// 拒绝必须有备注信息
					var forecastQuantityQueryGrid = scheduling.forecastQuantity.queryResult;
					if (!forecastQuantityQueryGrid.getSelectionModel()
							.hasSelection()) {
						Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.selectChange'));
						return false;
					}
					var dataList = forecastQuantityQueryGrid
							.getSelectionModel().getSelection();
					var fromRecord = dataList[0];
					var toRecord = dataList[1];
					if (dataList && dataList.length != 2) {
						Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.selectTwoChange'));
						return false;
					}
					if (Ext.Date.format(fromRecord.get('forecastTime'), 'Y-m-d') != scheduling.forecastQuantity.forecastDate) {
						Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.onlyTodayCanChange'));
						return;
					}
					me.getJoinCarAdjustWin(fromRecord, toRecord).show();
					var findField = function(operateForm, name) {
						return operateForm.findField(name);
					};
				}
			}, '->', {
				xtype : 'label',
				text : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.timeScope')
			}, me.getTimeRangeLable(), '->', {
				xtype : 'button',
				disabled : !scheduling.forecastQuantity.isPermission('scheduling/forecastExportExcelButton'),
				hidden : !scheduling.forecastQuantity.isPermission('scheduling/forecastExportExcelButton'),
				text : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.exportData'),
				handler : function() {
					var form = scheduling.forecastQuantity.queryForm;
					formData = form.getValues();
					var params = {
						'forecastVO.forecastQuantityEntity.type' : formData.type,
						'forecastVO.forecastQuantityEntity.forecastTime' : formData.forecastTime,
						'forecastVO.forecastQuantityEntity.relevantOrgCode' : formData.relevantOrgCode,
						'forecastVO.forecastQuantityEntity.belongOrgCode' : FossUserContext
								.getCurrentDeptCode()
					}
					if (!Ext.fly('downloadAttachFileForm')) {
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					Ext.Ajax.request({
						url : scheduling.realPath('forecastExportExcel.action'),
						form : Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : params,
						isUpload : true,
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							top.Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.exportFail'), result.message);
						}
					});
				}
			}, {
				xtype : 'button',
				disabled : !scheduling.forecastQuantity.isPermission('scheduling/showChartAllPathButton'),
				hidden : !scheduling.forecastQuantity.isPermission('scheduling/showChartAllPathButton'),
				iconCls : 'deppon_icons_viewChartWhite',
				text : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.queryTotalChart'),
				handler : function() {
					var form = scheduling.forecastQuantity.queryForm, store = form
							.getTimeCombo().getStore();
					var rawValue = form.getTypeCombo().rawValue;
					win = this.up('grid').getLineGoodChartsWin(), formData = form
							.getValues();
					if (formData.forecastTime != scheduling.forecastQuantity.forecastDate) {
						Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.onlyTodayCanQueryChart'));
						return;
					}
					var record = Ext.ModelManager.create({
								'type' : formData.type,
								'forecastTime' : formData.forecastTime,
								'statisticsDate' : formData.forecastTime,
								'belongOrgCode' : FossUserContext
										.getCurrentDeptCode()
							}, 'Foss.forecastQuantity.ForecastQuantityModel');
					record.data.rawValue=rawValue;
					win.bindData(record);
					win.show();
				}
			}]
		}, me.getPagingToolbar()];
		me.callParent([cfg]);
	},
	plugins : [{
		ptype : 'rowexpander',
		rowsExpander : false,
		rowBodyElement : 'Foss.forecastQuantity.OpenBillNotJoinGridAndTransitGridContainer'
	}],
	lineGoodChartsWin : null,
	// 获得查看出发线路统计图表
	getLineGoodChartsWin : function() {
		var me = this;
		if (me.lineGoodChartsWin == null) {
			me.lineGoodChartsWin = Ext.create(
					"Foss.forecastQuantity.LineGoodChartsWindow", {
						width : 1024
					});
		}
		return me.lineGoodChartsWin;
	},
	// 定义表格列信息
	columns : [{
			//地区名称
			header: '地区名称', 
			dataIndex: 'regionName',
			width:60
		},{
				// 字段标题
				xtype : 'actioncolumn',
				sortable : false,
				width : 70,
				align : 'center',
				text : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.operation'),
				items : [{
					iconCls : 'foss_icons_tfr_viewChart',
					tooltip : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.queryChart'),
					handler : function(gridView, rowIndex, colIndex) {
						var form = scheduling.forecastQuantity.queryForm, store = form
								.getTimeCombo().getStore();
						var rawValue = form.getTypeCombo().rawValue;
						win = this.up('grid').getLineGoodChartsWin(), rec = gridView
								.getStore().getAt(rowIndex), formData = form
								.getValues();
								
						if (Ext.Date.format(rec.get('forecastTime'), 'Y-m-d')!= scheduling.forecastQuantity.forecastDate) {
							Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.onlyTodayCanQueryChart'));
							return;
						}
						rec.data.rawValue=rawValue;
						win.bindData(rec);
						win.show();
					}
				}, {
					iconCls : 'foss_icons_tfr_departPlan',
					tooltip : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.departPlan'),
					handler : function(gridView, rowIndex, colIndex,item) {
						item.disable();
						var rec = gridView.getStore().getAt(rowIndex);
						var origOrgCode = rec.get('belongOrgCode');
						var destOrgCode = rec.get('relevantOrgCode');
						var origOrgCodeName = rec.get('belongOrgCodeName');
						var destOrgCodeName = rec.get('relevantOrgCodeName');
						var planDate = Ext.Date.format(rec.get('forecastTime'), 'Y-m-d');
						var type = rec.get('type');
						if ("ARRIVE" == type) {
							Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.arriveDontHaveDepartPlan'));
							return false;
						}
						// 根据出发到达部门,查询长短途
						Ext.Ajax.request({
							url : scheduling.realPath('queryDepartureType.action'),
							params : {
								'forecastVO.origOrgCode' : origOrgCode,
								'forecastVO.destOrgCode' : destOrgCode
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								// 获取长短途类型
								var departureType = result.forecastVO.departureType;
								if (null == departureType) {
									Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.line')
													+ origOrgCodeName + "-"
													+ destOrgCodeName
													+ scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.DontHaveDepartPlan'));
									return false;
								} else if ("L" == departureType) {
									scheduling.addAndInitLongTruckPlan(
											origOrgCode, destOrgCode, planDate);
								} else if ("S" == departureType) {
									scheduling.addAndInitShortTruckPlan(
											origOrgCode, destOrgCode, planDate);
								} else {
									Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.line')
													+ origOrgCodeName + "-"
													+ destOrgCodeName
													+ scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.wrongLineType'));
								}
								item.enable();
							},
							exception : function(response) {
								item.enable();
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.hint'), result.message, 'error');
							}
						})
					}
				}, {
					iconCls : 'deppon_icons_export',
					tooltip : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.detailExport'),
					handler : function(gridView, rowIndex, colIndex) {
						var rec = gridView.getStore().getAt(rowIndex);
						var params = {
							'forecastVO.forecastQuantityEntity.forecastQuantityId' : rec
									.get('forecastQuantityId')
						}
						if (!Ext.fly('downloadAttachFileForm')) {
							var frm = document.createElement('form');
							frm.id = 'downloadAttachFileForm';
							frm.style.display = 'none';
							document.body.appendChild(frm);
						}
						Ext.Ajax.request({
									url : scheduling
											.realPath('detailExportExcel.action'),
									form : Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : params,
									isUpload : true,
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										top.Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.exportFail'),
												result.message);
									}
								});
					}
				}]
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.relevantOrg'),
				// 关联model中的字段名
				dataIndex : 'relevantOrgCodeName',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 70,
				// 列统计信息显示
				summaryRenderer : function() {
					return scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.regionCount');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.waybillQtyTotal'),
				// 关联model中的字段名
				dataIndex : 'waybillQtyTotal',
				sortable : false,
				width : 60,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'sum'
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.weightTotal'),
				// 关联model中的字段名
				dataIndex : 'weightTotal',
				align : 'center',
				sortable : false,
				width : 60,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return scheduling.forecastQuantity.sumRecord(records,
							'weightTotal');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.volumeTotal'),
				// 关联model中的字段名
				dataIndex : 'volumeTotal',
				align : 'center',
				sortable : false,
				width : 60,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return scheduling.forecastQuantity.sumRecord(records,
							'volumeTotal');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.deviationVolume'),
				// 关联model中的字段名
				dataIndex : 'deviationVolume',
				xtype : 'tipcolumn',
				align : 'center',
				sortable : false,
				width : 60,
				tipConfig : {
					// 如果要设置宽度，一定要修改maxWidth值，因为tip的maxWidth最大只有300
					maxWidth : 650,
					width : 650,
					height : 210,
					// Tip的Body是否随鼠标移动
					trackMouse : false
				},
				// 配置tip内引用的自定义组件类名
				tipBodyElement : 'Foss.forecastQuantity.ChangeQuantityGrid'
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.gpsEnabledRes'),
				xtype : 'templatecolumn',
				tpl : '{gpsEnabledResWeightTotal}/{gpsEnabledResVolumeTotal}/{gpsEnabledResQtyTotal}',
				align : 'center',
				sortable : false,
				flex : 1,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'gpsEnabledRes', 'Total');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.precisionIfs'),
				xtype : 'templatecolumn',
				tpl : '{precisionIfsWeightTotal}/{precisionIfsVolumeTotal}/{precisionIfsQtyTotal}',
				align : 'center',
				sortable : false,
				flex : 1,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'precisionIfs', 'Total');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.express'),
				xtype : 'templatecolumn',
				tpl : '{expressWeightTotal}/{expressVolumeTotal}/{expressQtyTotal}',
				align : 'center',
				sortable : false,
				flex : 1,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'express', 'Total');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.billing'),
				xtype : 'templatecolumn',
				tpl : '{billingWeightTotal}/{billingVolumeTotal}/{billingQtyTotal}',
				align : 'center',
				sortable : false,
				flex : 1,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'billing', 'Total');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.inventory'),
				xtype : 'templatecolumn',
				tpl : '{inventoryWeightTotal}/{inventoryVolumeTotal}/{inventoryQtyTotal}',
				align : 'center',
				sortable : false,
				flex : 1,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'inventory', 'Total');
				}
			}, {
				// 字段标题
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.intransit'),
				xtype : 'templatecolumn',
				tpl : '{intransitWeightTotal}/{intransitVolumeTotal}/{intransitQtyTotal}',
				align : 'center',
				sortable : false,
				flex : 1,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'intransit', 'Total');
				}
			}]
});

// 短途
// 新增发车计划，并初始化发车计划
scheduling.addAndInitShortTruckPlan = function(origOrgCode, destOrgCode,
		planDate) {
	var actionUrl = scheduling.realPath('addTruckDepartPlan.action');
	var queryParams = {
		'vo.planDto.origOrgCode' : origOrgCode,// 出发部门
		'vo.planDto.destOrgCode' : destOrgCode,// 到达部门
		'vo.planDto.planDate' : planDate,// 计划日期
		'vo.planDto.planType' : 'SHORT'// 长途发车计划
	};
	// 验证并初始化数据
	Ext.Ajax.request({
				url : actionUrl,
				params : queryParams,
				// 动态创建表单，显示任务信息
				success : function(response) {
					var json = Ext.decode(response.responseText);

					if (json.vo.planDto.id) {
						// ID
						scheduling.shortplanId = json.vo.planDto.id;
						// 出发部门
						scheduling.shortorigOrgCode = origOrgCode;
						// 到达部门
						scheduling.shortdestOrgCode = destOrgCode;
						// 计划日期
						scheduling.shortplanDate = planDate;
						if (!Ext
								.isEmpty(Ext
										.getCmp('T_scheduling-shortScheduleDesignIndex'))) {
							removeTab('T_scheduling-shortScheduleDesignIndex');
						}

						addTab(
								'T_scheduling-shortScheduleDesignIndex',// 对应打开的目标页面js的onReady里定义的renderTo
								scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.shortDepartPlan'),// 打开的Tab页的标题
								scheduling
										.realPath('shortScheduleDesignIndex.action')
										+ '?planId="'
										+ scheduling.shortplanId
										+ '"'
										+ '&origOrgCode="'
										+ scheduling.shortorigOrgCode
										+ '"'
										+ '&destOrgCode="'
										+ scheduling.shortdestOrgCode
										+ '"'
										+ '&planDate="'
										+ scheduling.shortplanDate + '"');
					}
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.error'), json.message);
				}
			});
}
// 长途
// 新增发车计划，并初始化发车计划
scheduling.addAndInitLongTruckPlan = function(origOrgCode, destOrgCode,
		planDate) {
	var actionUrl = scheduling.realPath('addTruckDepartPlan.action');
	var queryParams = {
		'vo.planDto.origOrgCode' : origOrgCode,// 出发部门
		'vo.planDto.destOrgCode' : destOrgCode,// 到达部门
		'vo.planDto.planDate' : planDate,// 计划日期
		'vo.planDto.planType' : 'LONG'// 长途发车计划
	};
	// 验证并初始化数据
	Ext.Ajax.request({
		url : actionUrl,
		params : queryParams,
		// 动态创建表单，显示任务信息
		success : function(response) {
			var json = Ext.decode(response.responseText);
			if (json.vo.planDto.id) {
				// ID
				scheduling.longplanId = json.vo.planDto.id;
				// 出发部门
				scheduling.longorigOrgCode = origOrgCode;
				// 到达部门
				scheduling.longdestOrgCode = destOrgCode;
				// 计划日期
				scheduling.longplanDate = planDate;
				// 关闭原有标签
				if (!Ext.isEmpty(Ext
						.getCmp('T_scheduling-longScheduleDesignIndex'))) {
					removeTab('T_scheduling-longScheduleDesignIndex');
				}
				addTab(
						'T_scheduling-longScheduleDesignIndex',// 对应打开的目标页面js的onReady里定义的renderTo
						scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.longDepartPlan'),// 打开的Tab页的标题
						scheduling.realPath('longScheduleDesignIndex.action')
								+ '?planId="' + scheduling.longplanId + '"'
								+ '&origOrgCode="' + scheduling.longorigOrgCode
								+ '"' + '&destOrgCode="'
								+ scheduling.longdestOrgCode + '"'
								+ '&planDate="' + scheduling.longplanDate + '"');
			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.error'), json.message);
		}
	});
}

Ext.define('Foss.forecastQuantity.OpenBillNotJoinResultModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'billingId',
						type : 'string'
					}, {
						name : 'forecastQuantityId',// 货量预测表ID
						type : 'string'
					}, {
						name : 'belongOrgCode',// 所属部门
						type : 'string'
					}, {
						name : 'belongOrgName',// 所属部门
						type : 'string'
					}, {
						name : 'region',// 区域
						type : 'string'
					}, {
						name : 'regionName',// 区域
						type : 'string'
					}, {
						name : 'relevantOrgCode',// 目的地
						type : 'string'
					}, {
						name : 'relevantOrgName',// 目的地
						type : 'string'
					}, {
						name : 'billingSalesDistrict',// 所属营业区
						type : 'string'
					}, {
						name : 'billingSalesDistrictName',// 所属营业区
						type : 'string'
					}, {
						name : 'billingSalesDepartment',// 所属营业部
						type : 'string'
					}, {
						name : 'billingSalesDepartmentName',// 所属营业部
						type : 'string'
					}, {
						name : 'billingWeight',// 开单重量
						type : 'float'
					}, {
						name : 'billingVolume',// 开单体积
						type : 'float'
					}, {
						name : 'billingQty',// 开单票数
						type : 'int'
					}, {
						name : 'gpsEnabledResWeight',// 卡航重量
						type : 'float'
					}, {
						name : 'gpsEnabledResVolume',// 卡航体积
						type : 'float'
					}, {
						name : 'gpsEnabledResQty',// 卡航票数
						type : 'int'
					}, {
						name : 'precisionIfsWeight',// 城运重量
						type : 'float'
					}, {
						name : 'precisionIfsVolume',// 城运体积
						type : 'float'
					}, {
						name : 'precisionIfsQty',// 城运票数
						type : 'int'
					}, {
						name : 'expressWeight',// 快递重量
						type : 'float'
					}, {
						name : 'expressVolume',// 快递体积
						type : 'float'
					}, {
						name : 'expressQty',// 快递票数
						type : 'int'
					}, {
						name : 'statisticsTime',// 预测发起时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'type',// 预测类型 出发/到达
						type : 'string'
					}]
		});

// 开单未交接 store
Ext.define('Foss.forecastQuantity.OpenBillNotJoinResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.forecastQuantity.OpenBillNotJoinResultModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : scheduling.realPath('queryBilling.action'),
				reader : {
					type : 'json',
					root : 'forecastVO.billingList'
				}
			}
		});

// 合车调整信息
Ext.define('Foss.forecastQuantity.ChangeQuantityModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'belongTransferCenter',
						type : 'string'
					}, {
						name : 'belongTransferCenterName',
						type : 'string'
					}, {
						name : 'origDestOrg',
						type : 'string'
					}, {
						name : 'origDestOrgName',
						type : 'string'
					}, {
						name : 'newDestOrg',
						type : 'string'
					}, {
						name : 'newDestOrgName',
						type : 'string'
					}, {
						name : 'modifyWeight',
						type : 'float'
					}, {
						name : 'type',
						type : 'string'
					}, {
						name : 'changeTime',
						type : 'string',
						convert : function(value) {
							if (value != null) {
								var date = new Date(value);
								return Ext.Date.format(date, 'Y-m-d H:i:s');
							} else {
								return null;
							}
						}
					}]
		});

// 合车调整信息
Ext.define('Foss.forecastQuantity.ChangeQuantityGrid', {
	extend : 'Ext.grid.Panel',
	title : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.changeQuantityData'),
	stripeRows : true,
	frame : true,
	animCollapse : true,
	autoScroll : true,
	height : 210,
	columns : [{
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.belongTransferCenter'),
				dataIndex : 'belongTransferCenterName',
				flex : 0.5
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.origDestOrg'),
				dataIndex : 'origDestOrgName',
				flex : 0.5
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.newDestOrg'),
				dataIndex : 'newDestOrgName',
				flex : 0.5
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.modifyVolume'),
				dataIndex : 'modifyWeight',
				flex : 0.6
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.type'),
				dataIndex : 'type',
				flex : 0.4
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.changeTime'),
				dataIndex : 'changeTime',
				flex : 1
			}],
	bindData : function(record) {
		// 如果没有合车体积则不需要查询&展示
		var deviationVolume = record.get('deviationVolume');
		if (null == deviationVolume || "" == deviationVolume) {
			return false;
		}
		var params = {
			'forecastVO.forecastQuantityEntity.forecastTime' : record
					.get('forecastTime'),
			'forecastVO.forecastQuantityEntity.belongOrgCode' : record
					.get('belongOrgCode'),
			'forecastVO.forecastQuantityEntity.relevantOrgCode' : record
					.get('relevantOrgCode')
		}, me = this;
		Ext.Ajax.request({
			url : scheduling.realPath('queryChangeQuantity.action'),
			params : params,
			success : function(response) {
				var result = Ext.decode(response.responseText), store = me.store;
				store.model = 'Foss.forecastQuantity.ChangeQuantityModel';
				store.remoteSort = 'false';
				store.loadData(result.forecastVO.changeQuantityList);
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.OpenBillNotJoinGrid', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : false,
	columnWidth : 1,
	features : [{
				ftype : 'groupingsummary',
				groupHeaderTpl : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.billingSalesDistrictCount')
			}],
	columns : [{
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.billingSalesDistrict'),
				sortable : false,
				xtype : 'ellipsiscolumn',
				dataIndex : 'billingSalesDistrictName',
				width : 130,
				// 列统计信息显示
				summaryRenderer : function(value, summaryData, dataIndex) {
					return scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.count');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.waybillQtyTotal'),
				sortable : false,
				dataIndex : 'billingQty',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'sum'
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.weightTotal'),
				sortable : false,
				dataIndex : 'billingWeight',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return scheduling.forecastQuantity.sumRecord(records,
							'billingWeight');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.volumeTotal'),
				sortable : false,
				dataIndex : 'billingVolume',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return scheduling.forecastQuantity.sumRecord(records,
							'billingVolume');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.gpsEnabledRes'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{gpsEnabledResWeight}/{gpsEnabledResVolume}/{gpsEnabledResQty}',
				align : 'center',
				width : 150,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'gpsEnabledRes', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.precisionIfs'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{precisionIfsWeight}/{precisionIfsVolume}/{precisionIfsQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'precisionIfs', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.express'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{expressWeight}/{expressVolume}/{expressQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'express', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.fromBilling'),
				sortable : false,
				xtype : 'templatecolumn',
				tpl : '',
				align : 'center',
				width : 150
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create(
				'Foss.forecastQuantity.OpenBillNotJoinResultStore', {
					// 分组的字段
					groupField : 'billingSalesDistrict'
				});
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.TransitResultModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'string'
					}, {
						name : 'intransitId',
						type : 'string'
					}, {
						name : 'forecastQuantityId',// 货量预测表ID
						type : 'string'
					}, {
						name : 'belongOrgCode',// 所属部门
						type : 'string'
					}, {
						name : 'belongOrgCodeName',// 所属部门
						type : 'string'
					}, {
						name : 'region',// 区域
						type : 'string'
					}, {
						name : 'relevantOrgCode',// 目的地
						type : 'string'
					}, {
						name : 'relevantOrgCodeName',// 目的地
						type : 'string'
					}, {
						name : 'intransitVehicleNo',// 在途车牌号
						type : 'string'
					}, {
						name : 'intransitWeight',// 在途重量
						type : 'float'
					}, {
						name : 'intransitVolume',// 在途体积
						type : 'float'
					}, {
						name : 'intransitQty',// 在途票数
						type : 'int'
					}, {
						name : 'gpsEnabledResWeight',// 卡航重量
						type : 'float'
					}, {
						name : 'gpsEnabledResVolume',// 卡航体积
						type : 'float'
					}, {
						name : 'gpsEnabledResQty',// 卡航票数
						type : 'int'
					}, {
						name : 'precisionIfsWeight',// 城运重量
						type : 'float'
					}, {
						name : 'precisionIfsVolume',// 城运体积
						type : 'float'
					}, {
						name : 'precisionIfsQty',// 城运票数
						type : 'int'
					}, {
						name : 'expressWeight',// 快递重量
						type : 'float'
					}, {
						name : 'expressVolume',// 快递体积
						type : 'float'
					}, {
						name : 'expressQty',// 快递票数
						type : 'int'
					}, {
						name : 'statisticsTime',// 预测发起时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'type',// 预测类型 出发/到达
						type : 'string'
					}]
		});

// 在途 store
Ext.define('Foss.forecastQuantity.TransitResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.forecastQuantity.TransitResultModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : scheduling.realPath('queryInTransit.action'),
				reader : {
					type : 'json',
					root : 'forecastVO.inTransitList'
				}
			}
		});

//货量统计明细中的明细grid
Ext.define('Foss.forecastQuantity.TransitGrid', {
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
    //增加表格列的分割线
	columnLines: true,
	animCollapse : true,
	selModel : null,
	features : [{
				ftype : 'groupingsummary',
				groupHeaderTpl : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.vehicleCount'),
				hideGroupedHeader : true
			}],
	columns : [{
				dataIndex : 'forecastQuantityId',
				width : 60
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.vehicleNo'),
				sortable : false,
				dataIndex : 'intransitVehicleNo',
				width : 130,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'count',
				// 列统计信息显示
				summaryRenderer : function(value, summaryData, dataIndex) {
					return scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.count') + value + scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.vehicleUnit');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.waybillQtyTotal'),
				sortable : false,
				dataIndex : 'intransitQty',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'sum'
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.weightTotal'),
				sortable : false,
				dataIndex : 'intransitWeight',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return scheduling.forecastQuantity.sumRecord(records,
							'intransitWeight', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.volumeTotal'),
				sortable : false,
				dataIndex : 'intransitVolume',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return scheduling.forecastQuantity.sumRecord(records,
							'intransitVolume', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.gpsEnabledRes'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{gpsEnabledResWeight}/{gpsEnabledResVolume}/{gpsEnabledResQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'gpsEnabledRes', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.precisionIfs'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{precisionIfsWeight}/{precisionIfsVolume}/{precisionIfsQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'precisionIfs', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.express'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{expressWeight}/{expressVolume}/{expressQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return scheduling.forecastQuantity.detailRecord(records,
							'express', '');
				}
			}, {
				header : scheduling.forecastQuantity.i18n('foss.scheduling.forecastQuantity.forecastQuantity.fromIntransit'),
				xtype : 'templatecolumn',
				tpl : '',
				align : 'center',
				width : 150
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.forecastQuantity.TransitResultStore', {
					// 分组的字段
					groupField : 'forecastQuantityId'
				});
		me.callParent([cfg]);
	}
});

Ext.define('Foss.forecastQuantity.OpenBillNotJoinGridAndTransitGridContainer',
		{
			extend : 'Ext.panel.Panel',
			bodyCls : 'autoHeight',
			cls : 'autoHeight',
			openBillNotJoinGrid : null,
			transitGrid : null,
			margin : '0 0 0 107',
			bindData : function(record, grid, rowBodyElement) {
				var forecastQuantityId = record.get('forecastQuantityId');
				rowBodyElement.getOpenBillNotJoinGrid().getStore().load({
					params : {
						'forecastVO.billingEntity.forecastQuantityId' : forecastQuantityId
					}
				});
				rowBodyElement.getTransitGrid().getStore().load({
					params : {
						'forecastVO.inTransitEntity.forecastQuantityId' : forecastQuantityId
					}
				});
			},
			getOpenBillNotJoinGrid : function() {
				if (!this.openBillNotJoinGrid) {
					this.openBillNotJoinGrid = Ext
							.create('Foss.forecastQuantity.OpenBillNotJoinGrid');
				}
				return this.openBillNotJoinGrid;
			},
			getTransitGrid : function() {
				if (!this.transitGrid) {
					this.transitGrid = Ext
							.create('Foss.forecastQuantity.TransitGrid');
				}
				return this.transitGrid;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				this.items = [this.getOpenBillNotJoinGrid(),
						this.getTransitGrid()];
				me.callParent([cfg]);
			}
		});

Ext.onReady(function() {
			Ext.QuickTips.init();
			if (null == scheduling.forecastQuantity.action
					|| "" == scheduling.forecastQuantity.action) {
				scheduling.forecastQuantity.action = 'DEPART';
			}
			scheduling.forecastQuantity.queryForm = Ext
					.create('Foss.forecastQuantity.QueryForm');
			scheduling.forecastQuantity.queryResult = Ext
					.create('Foss.forecastQuantity.QueryResult');
			Ext.create('Ext.panel.Panel', {
						id : 'T_scheduling-forecastQuantityIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [scheduling.forecastQuantity.queryForm,
								scheduling.forecastQuantity.queryResult],
						renderTo : 'T_scheduling-forecastQuantityIndex-body'
					});
		});