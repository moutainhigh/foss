(function(){
	platform.forecastQuantity.forecastDate;
	// 根据出发到达部门,查询当前时间属于哪个预测日期
	Ext.Ajax.request({
		url : platform.realPath('nowForecastDate.action'),
		params : {
			'forecastVO.forecastQuantityEntity.belongOrgCode' : FossUserContext
					.getCurrentDeptCode()
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			// 获取预测时间区段
			platform.forecastQuantity.forecastDate = Ext.Date.format(new Date(result.forecastVO.maxStatisticsTime), 'Y-m-d');
			if (null == platform.forecastQuantity.forecastDate) {
				Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'),
						platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.cantFindTimeDuration'));
			}
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			Ext.ux.Toast.msg(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), result.message, 'error');
		}
	});
})();

// 查询条件
Ext.define('Foss.forecastQuantity.QueryForm', {
	extend : 'Ext.form.Panel',
	title : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.queryForecast'),
    frame : true,
    collapsible : true,
    animCollapse : true,
	typeCombo : null,
    layout : 'column',
	getTypeCombo : function() {
		var me = this;
		if (me.typeCombo == null) {
			me.typeCombo = FossDataDictionary.getDataDictionaryCombo(
					'TFR_FORECAST_TYPE', {
						name : 'type',
						emptyText : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.selectType'),
						allowBlank : false,
						editable : false,
						margin : '5 0 5 140',
						value : platform.forecastQuantity.action,
						width : 300,
						blankText : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.typeCantEmpty'),
						fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.forecastType'),
						listeners : {
							change : function(field, newValue, oldValue, eOpts) {
								me.getTimeCombo().store.load();

                                var departurearrival = me.getDeparturearrival();
                                departurearrival.reset();
                                var departurearrivalArray = new Array();

                                //出发
                                if (newValue == 'DEPART') {
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.localdeparture'),
                                        'valueCode' : 'depart_localdeparture'});		// 本地出发
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.secondarytransit'),
                                        'valueCode' : 'depart_secondarytransit'});		// 二次中转
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.arrivaltransit'),
                                        'valueCode' : 'depart_arrivaltransit'});		// 到达中转
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.deliveryvolume'),
                                        'valueCode' : 'depart_deliveryvolume'});		// 派送货量
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.all'),
                                        'valueCode' : 'depart_all'});	// 全部
                                }else{
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.arrive.longreach'),
                                        'valueCode' : 'arrive_longreach'});		// 长途到达
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.arrive.shortreach'),
                                        'valueCode' : 'arrive_shortreach'});		// 短途到达
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.arrive.centralizedpick'),
                                        'valueCode' : 'arrive_centralizedpick'});		// 集中接货
                                    departurearrivalArray.push({'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.all'),
                                        'valueCode' : 'arrive_all'});	// 全部
                                }
                                departurearrival.store.loadData(departurearrivalArray);
                                departurearrival.setValue(departurearrival.store.getAt(0));

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
					url : platform.realPath('queryTimeList.action'),
					reader : {
						type : 'json',
						root : 'forecastVO.forecastDateList'
					}
				},
				listeners : {
					load : function(store, records, successful, eOpts) {
						// platform.forecastQuantity.maxStatisticsTime =
						// store.getProxy().getReader().rawData.forecastVO.maxStatisticsTime;
						if (records!=null && records.length > 0) {
							me.timeCombo.setValue(records[0]);
						} else {
							Ext.ux.Toast.msg(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.cantFindForecastTime'), 'error');
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
						emptyText : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.selectTime'),
						fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.time'),
						editable : false,
						queryMode : 'local',
						allowBlank : false,
						blankText : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.timeCantEmpty'),
						margin : '5 0 5 140',
						width : 300,
						value : store.getAt(0),
						displayField : 'forecastDate',
						valueField : 'forecastDate',
						store : store
					});
		}
		return this.timeCombo;
	},
    departurearrival : null,
    getDeparturearrival : function() {
        var me = this;
        if (me.departurearrival == null) {
           var store = Ext.create('Ext.data.Store', {
                fields : ['valueName', 'valueCode'],
                data : [
                    {'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.localdeparture'),
                        'valueCode' : 'depart_localdeparture'},		// 本地出发
                    {'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.secondarytransit'),
                        'valueCode' : 'depart_secondarytransit'},		// 二次中转
                    {'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.arrivaltransit'),
                        'valueCode' : 'depart_arrivaltransit'},		// 到达中转
                    {'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.depart.deliveryvolume'),
                        'valueCode' : 'depart_deliveryvolume'},		// 派送货量
                    {'valueName' : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.all'),
                        'valueCode' : 'depart_all'}		// 全部
                ]
            });

            me.departurearrival = Ext.create('Ext.form.ComboBox', {
            name : 'departurearrival',
            margin : '5 140 5 20',
            labelWidth : 100,
            fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.departurearrival'),
            emptyText : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.selectdeparturearrival'),
            width : 300,
            store : store,
            xtype : 'combo',
            queryMode : 'local',
            displayField : 'valueName',
            allowBlank : false,
            valueField : 'valueCode',
            value : store.getAt(0),
            editable : false
            });
        }
        return me.departurearrival;
    },constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getTypeCombo(),me.getDeparturearrival(), me.getTimeCombo(),{
					xtype : 'dynamicorgcombselector',
					type : 'ORG',
					transferCenter: 'Y',
					salesDepartment : 'Y',
					airDispatch: 'Y',
					name : 'relevantOrgCode',
					margin : '5 140 5 20',
					labelWidth : 100,
					fieldLabel : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.relevantOrg'),
					emptyText : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.selectRelevantOrg'),
					width : 300
				}, {
					xtype : 'container',
					//layout : 'hbox',
					margin : '15 0 0 740',
					items : [{
						xtype : 'button',
						disabled : !platform.forecastQuantity.isPermission('platform/queryForecastByPageButton'),
						hidden : !platform.forecastQuantity.isPermission('platform/queryForecastByPageButton'),
						text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.query'),
						cls : 'yellow_button',
						arrowAlign : 'bottom',
						minWidth : 93,
						handler : function() {
							if (!me.getForm().isValid()) {
								Ext.ux.Toast.msg(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.needMustItem'), 'error');
								return;
							}
							platform.forecastQuantity.queryResult.store.load();
									//.getPagingToolbar().moveFirst();
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
					}, {
						name : 'unbillingWeightTot',// 预测未开单总重量
						type : 'float'
					}, {
						name : 'unbillingVolumeTot',// 预测未开单总体积
						type : 'float'
					}, {
						name : 'unbillingWaybillQtyTot',// 预测未开单总票数
						type : 'int'
					}, {
						name : 'weightTot',// 预测总重量
						type : 'float'
					}, {
						name : 'volumeTot',// 预测总体积
						type : 'float'
					}, {
						name : 'waybillQtyTot',// 预测总票数
						type : 'int'
					}]
		});

// 地区的store
Ext.define('Foss.forecastQuantity.QueryResultStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.forecastQuantity.ForecastQuantityModel',
	//pageSize : 5,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryForecastByPage.action'),
		reader : {
			type : 'json',
			root : 'forecastVO.forecastQuantityList'
				//,
			//totalProperty: 'totalCount'
		},
		listeners : {
			exception : function(dataProxy, response, action, options) {
				var result = Ext.decode(response.responseText);
				Ext.ux.Toast.msg('提示', result.message);
			}
		}
	},
	listeners : {
		beforeload : function(store, operation) {
			var queryForm = platform.forecastQuantity.queryForm.getValues();
			Ext.apply(operation, {
				params : {
					'forecastVO.forecastQuantityEntity.type' : queryForm.type,
					'forecastVO.forecastQuantityEntity.relevantOrgCode' : queryForm.relevantOrgCode,
					'forecastVO.forecastQuantityEntity.forecastTime' : queryForm.forecastTime,
					'forecastVO.forecastQuantityEntity.departurearrival' : queryForm.departurearrival,
					'forecastVO.forecastQuantityEntity.belongOrgCode' : FossUserContext
							.getCurrentDeptCode()
				}
			});
		},
		load : function(store, records, successful, epots) {
			if (store.getCount() == 0) {
				Ext.ux.Toast.msg(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.cantFindResult'), 'error');
			}
			var forecastStartTime = new Date(store.getProxy().getReader().rawData.forecastVO.forecastStartTime), forecastEndTime = new Date(store
					.getProxy().getReader().rawData.forecastVO.forecastEndTime), timeRande = Ext.Date
					.format(forecastStartTime, 'Y-m-d H:i:s')
					+ '-' + Ext.Date.format(forecastEndTime, 'Y-m-d H:i:s');
			platform.forecastQuantity.queryResult.getTimeRangeLable()
					.update(timeRande);
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

platform.forecastQuantity.detailRecord = function(records, field, pram) {
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

platform.forecastQuantity.sumRecord = function(records, field) {
	var i = 0, length = records.length, total, record, sum = 0;
	for (; i < length; ++i) {
		record = records[i];
		sum += parseFloat(record.get(field));
	}
	total = sum.toFixed(2);
	return total;
};

Ext.define('Foss.forecastQuantity.JoinCarWindow', {
	extend : 'Ext.window.Window',
	title : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.joinCarChange'),
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
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.modifyCantEmpty'));
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
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.modifyBecomeNegative'));
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
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.modifyBecomeNegative'));
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
	title : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.forecast'),
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
					groupField : 'regionName'      //departmentType
				});
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			padding : '0 5 0 5',
			items : [{
				xtype : 'button',
				text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.joinCarChange'),
				handler : function() {
					var form = platform.forecastQuantity.queryForm, store = form
							.getTimeCombo().getStore();
					win = this.up('grid').getLineGoodChartsWin(), formData = form
							.getValues();
					// 拒绝必须有备注信息
					var forecastQuantityQueryGrid = platform.forecastQuantity.queryResult;
					if (!forecastQuantityQueryGrid.getSelectionModel()
							.hasSelection()) {
						Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.selectChange'));
						return false;
					}
					var dataList = forecastQuantityQueryGrid
							.getSelectionModel().getSelection();
					var fromRecord = dataList[0];
					var toRecord = dataList[1];
					if (dataList && dataList.length != 2) {
						Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.selectTwoChange'));
						return false;
					}
					if (Ext.Date.format(fromRecord.get('forecastTime'), 'Y-m-d') != platform.forecastQuantity.forecastDate) {
						Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.onlyTodayCanChange'));
						return;
					}
					me.getJoinCarAdjustWin(fromRecord, toRecord).show();
					var findField = function(operateForm, name) {
						return operateForm.findField(name);
					};
				}
			}, '->', {
				xtype : 'label',
				text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.timeScope')
			}, me.getTimeRangeLable(), '->', {
				xtype : 'button',
				disabled : !platform.forecastQuantity.isPermission('platform/forecastExportExcelButton'),
				hidden : !platform.forecastQuantity.isPermission('platform/forecastExportExcelButton'),
				text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.exportData'),
				handler : function() {
					var form = platform.forecastQuantity.queryForm;
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
						url : platform.realPath('forecastExportExcel.action'),
						form : Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : params,
						isUpload : true,
						exception : function(response) {
							var result = Ext.decode(response.responseText);
							top.Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.exportFail'), result.message);
						}
					});
				}
			}, {
				xtype : 'button',
				disabled : !platform.forecastQuantity.isPermission('platform/showChartAllPathButton'),
				hidden : !platform.forecastQuantity.isPermission('platform/showChartAllPathButton'),
				iconCls : 'deppon_icons_viewChartWhite',
				text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.queryTotalChart'),
				handler : function() {
					var form = platform.forecastQuantity.queryForm, store = form
							.getTimeCombo().getStore();
					var rawValue = form.getTypeCombo().rawValue;
					win = this.up('grid').getLineGoodChartsWin(), formData = form
							.getValues();
					if (formData.forecastTime != platform.forecastQuantity.forecastDate) {
						Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.onlyTodayCanQueryChart'));
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
		}];
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
		text : '实际货量',
		columns : [
		{
			//地区名称
			header: '地区名称', 
			dataIndex: 'regionName',
			width:60
		},{
			//部门类型 : 看看是外场还是集中接货还是营业部
			header: '',
			dataIndex: 'departmentType',
			width:60
		},{
				// 字段标题
				xtype : 'actioncolumn',
				sortable : false,
				width : 70,
				align : 'center',
				text : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.operation'),
				items : [{
					iconCls : 'foss_icons_tfr_viewChart',
					tooltip : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.queryChart'),
					handler : function(gridView, rowIndex, colIndex) {
						var form = platform.forecastQuantity.queryForm, store = form
								.getTimeCombo().getStore();
						var rawValue = form.getTypeCombo().rawValue;
						win = this.up('grid').getLineGoodChartsWin(), rec = gridView
								.getStore().getAt(rowIndex), formData = form
								.getValues();
								
						if (Ext.Date.format(rec.get('forecastTime'), 'Y-m-d')!= platform.forecastQuantity.forecastDate) {
							Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.onlyTodayCanQueryChart'));
							return;
						}
						rec.data.rawValue=rawValue;
						win.bindData(rec);
						win.show();
					}
				}, {
					iconCls : 'foss_icons_tfr_departPlan',
					tooltip : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.departPlan'),
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
							Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.arriveDontHaveDepartPlan'));
							return false;
						}
						// 根据出发到达部门,查询长短途
						Ext.Ajax.request({
							url : platform.realPath('queryDepartureType.action'),
							params : {
								'forecastVO.origOrgCode' : origOrgCode,
								'forecastVO.destOrgCode' : destOrgCode
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								// 获取长短途类型
								var departureType = result.forecastVO.departureType;
								if (null == departureType) {
									Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.line')
													+ origOrgCodeName + "-"
													+ destOrgCodeName
													+ platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.DontHaveDepartPlan'));
									return false;
								} else if ("L" == departureType) {
									platform.addAndInitLongTruckPlan(
											origOrgCode, destOrgCode, planDate);
								} else if ("S" == departureType) {
									platform.addAndInitShortTruckPlan(
											origOrgCode, destOrgCode, planDate);
								} else {
									Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.line')
													+ origOrgCodeName + "-"
													+ destOrgCodeName
													+ platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.wrongLineType'));
								}
								item.enable();
							},
							exception : function(response) {
								item.enable();
								var result = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.hint'), result.message, 'error');
							}
						})
					}
				}, {
					iconCls : 'deppon_icons_export',
					tooltip : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.detailExport'),
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
									url : platform
											.realPath('detailExportExcel.action'),
									form : Ext.fly('downloadAttachFileForm'),
									method : 'POST',
									params : params,
									isUpload : true,
									exception : function(response) {
										var result = Ext
												.decode(response.responseText);
										top.Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.exportFail'),
												result.message);
									}
								});
					}
				}]
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.relevantOrg'),
				// 关联model中的字段名
				dataIndex : 'relevantOrgCodeName',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 90,
				// 列统计信息显示
				summaryRenderer : function() {
					return platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.regionCount');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.waybillQtyTotal'),
				// 关联model中的字段名
				dataIndex : 'waybillQtyTotal',
				sortable : false,
				width : 60,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'sum'
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.weightTotal'),
				// 关联model中的字段名
				dataIndex : 'weightTotal',
				align : 'center',
				sortable : false,
				width : 70,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return platform.forecastQuantity.sumRecord(records,
							'weightTotal');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.volumeTotal'),
				// 关联model中的字段名
				dataIndex : 'volumeTotal',
				align : 'center',
				sortable : false,
				width : 70,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return platform.forecastQuantity.sumRecord(records,
							'volumeTotal');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.deviationVolume'),
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
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.gpsEnabledRes'),
				xtype : 'templatecolumn',
				tpl : '{gpsEnabledResWeightTotal}/{gpsEnabledResVolumeTotal}/{gpsEnabledResQtyTotal}',
				align : 'center',
				sortable : false,
				width : 110,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'gpsEnabledRes', 'Total');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.precisionIfs'),
				xtype : 'templatecolumn',
				tpl : '{precisionIfsWeightTotal}/{precisionIfsVolumeTotal}/{precisionIfsQtyTotal}',
				align : 'center',
				sortable : false,
				width : 110,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'precisionIfs', 'Total');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.express'),
				xtype : 'templatecolumn',
				tpl : '{expressWeightTotal}/{expressVolumeTotal}/{expressQtyTotal}',
				align : 'center',
				sortable : false,
				width : 110,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'express', 'Total');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.billing'),
				xtype : 'templatecolumn',
				tpl : '{billingWeightTotal}/{billingVolumeTotal}/{billingQtyTotal}',
				align : 'center',
				sortable : false,
				width : 110,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'billing', 'Total');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.inventory'),
				xtype : 'templatecolumn',
				tpl : '{inventoryWeightTotal}/{inventoryVolumeTotal}/{inventoryQtyTotal}',
				align : 'center',
				sortable : false,
				width : 110,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'inventory', 'Total');
				}
			}, {
				// 字段标题
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.intransit'),
				xtype : 'templatecolumn',
				tpl : '{intransitWeightTotal}/{intransitVolumeTotal}/{intransitQtyTotal}',
				align : 'center',
				sortable : false,
				width : 110,
				// 指定的统计的方法，默认有count、sum、min、max、average等，可以自定义，自定义方法如下
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'intransit', 'Total');
				}
			}]
	}, {
		text : '预测货量',
		columns : [
			{
				// 字段标题
				header : '预测未开单货量<br/>(重量/体积/票数)',
				xtype : 'templatecolumn',
				tpl : '{unbillingWeightTot}/{unbillingVolumeTot}/{unbillingWaybillQtyTot}',
				align : 'center',
				sortable : false,
				width : 110,
				summaryType : function(records) {
					return platform.forecastQuantity.sumRecord(records, 'unbillingWeightTot') + '/' +
						platform.forecastQuantity.sumRecord(records, 'unbillingVolumeTot') + '/' +
						platform.forecastQuantity.sumRecord(records, 'unbillingWaybillQtyTot');
				}
		}, {
			// 字段标题
			header : '总货量<br/>(重量/体积/票数)',
			xtype : 'templatecolumn',
			tpl : '{weightTot}/{volumeTot}/{waybillQtyTot}',
			align : 'center',
			sortable : false,
			width : 110,
			summaryType : function(records) {
				return platform.forecastQuantity.sumRecord(records, 'weightTot') + '/' +
					platform.forecastQuantity.sumRecord(records, 'volumeTot') + '/' +
					platform.forecastQuantity.sumRecord(records, 'waybillQtyTot');
			}
	}]
	}]
});

// 短途
// 新增发车计划，并初始化发车计划
platform.addAndInitShortTruckPlan = function(origOrgCode, destOrgCode,
		planDate) {
	var actionUrl = platform.realPath('addTruckDepartPlan.action');
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
						platform.shortplanId = json.vo.planDto.id;
						// 出发部门
						platform.shortorigOrgCode = origOrgCode;
						// 到达部门
						platform.shortdestOrgCode = destOrgCode;
						// 计划日期
						platform.shortplanDate = planDate;
						if (!Ext
								.isEmpty(Ext
										.getCmp('T_platform-shortScheduleDesignIndex'))) {
							removeTab('T_platform-shortScheduleDesignIndex');
						}

						addTab(
								'T_platform-shortScheduleDesignIndex',// 对应打开的目标页面js的onReady里定义的renderTo
								platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.shortDepartPlan'),// 打开的Tab页的标题
								platform
										.realPath('shortScheduleDesignIndex.action')
										+ '?planId="'
										+ platform.shortplanId
										+ '"'
										+ '&origOrgCode="'
										+ platform.shortorigOrgCode
										+ '"'
										+ '&destOrgCode="'
										+ platform.shortdestOrgCode
										+ '"'
										+ '&planDate="'
										+ platform.shortplanDate + '"');
					}
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText);
					Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.error'), json.message);
				}
			});
}
// 长途
// 新增发车计划，并初始化发车计划
platform.addAndInitLongTruckPlan = function(origOrgCode, destOrgCode,
		planDate) {
	var actionUrl = platform.realPath('addTruckDepartPlan.action');
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
				platform.longplanId = json.vo.planDto.id;
				// 出发部门
				platform.longorigOrgCode = origOrgCode;
				// 到达部门
				platform.longdestOrgCode = destOrgCode;
				// 计划日期
				platform.longplanDate = planDate;
				// 关闭原有标签
				if (!Ext.isEmpty(Ext
						.getCmp('T_platform-longScheduleDesignIndex'))) {
					removeTab('T_platform-longScheduleDesignIndex');
				}
				addTab(
						'T_platform-longScheduleDesignIndex',// 对应打开的目标页面js的onReady里定义的renderTo
						platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.longDepartPlan'),// 打开的Tab页的标题
						platform.realPath('longScheduleDesignIndex.action')
								+ '?planId="' + platform.longplanId + '"'
								+ '&origOrgCode="' + platform.longorigOrgCode
								+ '"' + '&destOrgCode="'
								+ platform.longdestOrgCode + '"'
								+ '&planDate="' + platform.longplanDate + '"');
			}
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			Ext.MessageBox.alert(platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.error'), json.message);
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
				url : platform.realPath('queryBilling.action'),
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
	title : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.changeQuantityData'),
	stripeRows : true,
	frame : true,
	animCollapse : true,
	autoScroll : true,
	height : 210,
	columns : [{
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.belongTransferCenter'),
				dataIndex : 'belongTransferCenterName',
				flex : 0.5
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.origDestOrg'),
				dataIndex : 'origDestOrgName',
				flex : 0.5
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.newDestOrg'),
				dataIndex : 'newDestOrgName',
				flex : 0.5
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.modifyVolume'),
				dataIndex : 'modifyWeight',
				flex : 0.6
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.type'),
				dataIndex : 'type',
				flex : 0.4
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.changeTime'),
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
			url : platform.realPath('queryChangeQuantity.action'),
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
				groupHeaderTpl : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.billingSalesDistrictCount')
			}],
	columns : [{
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.billingSalesDistrict'),
				sortable : false,
				xtype : 'ellipsiscolumn',
				dataIndex : 'billingSalesDistrictName',
				width : 130,
				// 列统计信息显示
				summaryRenderer : function(value, summaryData, dataIndex) {
					return platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.count');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.waybillQtyTotal'),
				sortable : false,
				dataIndex : 'billingQty',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'sum'
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.weightTotal'),
				sortable : false,
				dataIndex : 'billingWeight',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return platform.forecastQuantity.sumRecord(records,
							'billingWeight');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.volumeTotal'),
				sortable : false,
				dataIndex : 'billingVolume',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return platform.forecastQuantity.sumRecord(records,
							'billingVolume');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.gpsEnabledRes'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{gpsEnabledResWeight}/{gpsEnabledResVolume}/{gpsEnabledResQty}',
				align : 'center',
				width : 150,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'gpsEnabledRes', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.precisionIfs'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{precisionIfsWeight}/{precisionIfsVolume}/{precisionIfsQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'precisionIfs', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.express'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{expressWeight}/{expressVolume}/{expressQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'express', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.fromBilling'),
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
				url : platform.realPath('queryInTransit.action'),
				reader : {
					type : 'json',
					root : 'forecastVO.inTransitList'
				}
			}
		});

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
				groupHeaderTpl : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.vehicleCount'),
				hideGroupedHeader : true
			}],
	columns : [{
				dataIndex : 'forecastQuantityId',
				width : 60
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.vehicleNo'),
				sortable : false,
				dataIndex : 'intransitVehicleNo',
				width : 130,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'count',
				// 列统计信息显示
				summaryRenderer : function(value, summaryData, dataIndex) {
					return platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.count') + value + platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.vehicleUnit');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.waybillQtyTotal'),
				sortable : false,
				dataIndex : 'intransitQty',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : 'sum'
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.weightTotal'),
				sortable : false,
				dataIndex : 'intransitWeight',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return platform.forecastQuantity.sumRecord(records,
							'intransitWeight', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.volumeTotal'),
				sortable : false,
				dataIndex : 'intransitVolume',
				width : 100,
				// 指定的统计的方法，默认有count、sum、min、max、average等
				summaryType : function(records) {
					return platform.forecastQuantity.sumRecord(records,
							'intransitVolume', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.gpsEnabledRes'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{gpsEnabledResWeight}/{gpsEnabledResVolume}/{gpsEnabledResQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'gpsEnabledRes', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.precisionIfs'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{precisionIfsWeight}/{precisionIfsVolume}/{precisionIfsQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'precisionIfs', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.express'),
				xtype : 'templatecolumn',
				sortable : false,
				tpl : '{expressWeight}/{expressVolume}/{expressQty}',
				align : 'center',
				width : 150,
				summaryType : function(records) {
					return platform.forecastQuantity.detailRecord(records,
							'express', '');
				}
			}, {
				header : platform.forecastQuantity.i18n('foss.platform.forecastQuantity.forecastQuantity.fromIntransit'),
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
			if (null == platform.forecastQuantity.action
					|| "" == platform.forecastQuantity.action) {
				platform.forecastQuantity.action = 'DEPART';
			}
			platform.forecastQuantity.queryForm = Ext
					.create('Foss.forecastQuantity.QueryForm');
			platform.forecastQuantity.queryResult = Ext
					.create('Foss.forecastQuantity.QueryResult');
			Ext.create('Ext.panel.Panel', {
						id : 'T_platform-forecastQuantityIndex_content',
						cls : "panelContentNToolbar",
						bodyCls : 'panelContentNToolbar-body',
						layout : 'auto',
						items : [platform.forecastQuantity.queryForm,
								platform.forecastQuantity.queryResult],
						renderTo : 'T_platform-forecastQuantityIndex-body'
					});
		});