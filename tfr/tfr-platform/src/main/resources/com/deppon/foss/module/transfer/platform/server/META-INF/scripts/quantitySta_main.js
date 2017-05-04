// 查询条件
Ext.define('Foss.quantitySta.QueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
    frame : true,
    collapsible : true,
    animCollapse : true,
	typeCombo : null,
    layout : 'column',
	getTypeCombo : function() {
		var me = this;
		if (me.typeCombo == null) {
           var store = Ext.create('Ext.data.Store', {
                fields : ['valueName', 'valueCode'],
                data : [
                    {'valueName' : '出发',
                        'valueCode' : 'DEPARTURE'},
                    {'valueName' : '到达',
                        'valueCode' : 'ARRIVAL'}
                ]
            });
			me.typeCombo = Ext.create('Ext.form.ComboBox', {
				name : 'type',
				allowBlank : false,
				editable : false,
				margin : '5 0 5 140',
				width : 300,
				store : store,
				value : store.getAt(0),
				queryMode : 'local',
				xtype : 'combo',
				displayField : 'valueName',
				valueField : 'valueCode',
				fieldLabel : '预测类型',
				listeners : {
					change : function(field, newValue, oldValue, eOpts) {
                        var particularType = me.getParticularType();
                        particularType.reset();
                        var particularTypeArray = new Array();

                        //出发
                        if (newValue == 'DEPARTURE') {
                        	particularTypeArray.push({'valueName' : '长途出发',
                                'valueCode' : 'DEPARTURE_LONG_DISTANCE'});
                        	particularTypeArray.push({'valueName' : '短途出发',
                                'valueCode' : 'DEPARTURE_SHORT_DISTANCE'});
                        	particularTypeArray.push({'valueName' : '派送货量',
                                'valueCode' : 'DEPARTURE_DELIVER'});
                        	particularTypeArray.push({'valueName' : '全部',
                                'valueCode' : ''});
                        }else{
                        	particularTypeArray.push({'valueName' : '长途到达',
                                'valueCode' : 'ARRIVAL_LONG_DISTANCE'});
                        	particularTypeArray.push({'valueName' : '短途到达',
                                'valueCode' : 'ARRIVAL_SHORT_DISTANCE'});
                        	particularTypeArray.push({'valueName' : '集中接货',
                                'valueCode' : 'ARRIVAL_CENTRALIZE_PICKUP'});
                        	particularTypeArray.push({'valueName' : '全部',
                                'valueCode' : ''});
                        }
                        particularType.store.loadData(particularTypeArray);
                        particularType.setValue(particularType.store.getAt(0));
					}
				}
			});
		}
		return me.typeCombo;
	},
	timeCombo : null,
	getTimeCombo : function() {
		var me = this,
			today = new Date(),
			tomorrow = new Date(today.getFullYear(),today.getMonth(),today.getDate() + 1),
			todayStr = Ext.util.Format.date(today, 'Y-m-d'),
			tomorrowStr = Ext.util.Format.date(tomorrow, 'Y-m-d');
		if (me.timeCombo == null) {
			var store = Ext.create('Ext.data.Store', {
				fields : ['dataBelongDate'],
				data : [{'dataBelongDate' : todayStr},{'dataBelongDate' : tomorrowStr}]
			});
			me.timeCombo = Ext.create('Ext.form.ComboBox', {
				name : 'dataBelongDate',
				fieldLabel : '日期',
				editable : false,
				queryMode : 'local',
				allowBlank : false,
				margin : '5 0 5 140',
				width : 300,
				value : store.getAt(0),
				displayField : 'dataBelongDate',
				valueField : 'dataBelongDate',
				store : store
			});
		}
		return this.timeCombo;
	},
    particularType : null,
    getParticularType : function() {
        var me = this;
        if (me.particularType == null) {
           var store = Ext.create('Ext.data.Store', {
                fields : ['valueName', 'valueCode'],
                data : [
                    {'valueName' : '长途出发',
                        'valueCode' : 'DEPARTURE_LONG_DISTANCE'},
                    {'valueName' : '短途出发',
                        'valueCode' : 'DEPARTURE_SHORT_DISTANCE'},
                    {'valueName' : '派送货量',
                        'valueCode' : 'DEPARTURE_DELIVER'},
                    {'valueName' : '全部',
                        'valueCode' : ''}
                ]
            });

            me.particularType = Ext.create('Ext.form.ComboBox', {
		        name : 'particularType',
		        margin : '5 140 5 20',
		        labelWidth : 100,
		        fieldLabel : '出发(到达)类型',
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
        return me.particularType;
    },
    relevantOrgCode : null,
    getRelevantOrgCode : function(){
    	var me = this;
    	if(me.relevantOrgCode == null){
    		me.relevantOrgCode = {
    				xtype : 'dynamicorgcombselector',
    				type : 'ORG',
    				transferCenter: 'Y',
    				salesDepartment : 'Y',
    				airDispatch: 'Y',
    				name : 'relevantOrgCode',
    				margin : '5 140 5 20',
    				labelWidth : 100,
    				fieldLabel : '起始(目的)地',
    				width : 300
    		};
    	}
    	return me.relevantOrgCode;
    },
    constructor : function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getTypeCombo(),me.getParticularType(), me.getTimeCombo(),me.getRelevantOrgCode(), {
			xtype : 'container',
			margin : '4 0 0 665',
			items : [{
				xtype : 'button',
				disabled : !platform.quantitySta.isPermission('platform/queryQuantityButton'),
				hidden : !platform.quantitySta.isPermission('platform/queryQuantityButton'),
				text : '查询',
				cls : 'yellow_button',
				arrowAlign : 'bottom',
				minWidth : 93,
				handler : function() {
					if (!me.getForm().isValid()) {
						Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error');
						return;
					}
					platform.quantitySta.queryResultGrid.store.load();
				}
			}]
		}];
		me.callParent([cfg]);
	}
});

//查询结果Model
Ext.define('Foss.quantitySta.quantityStaModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'id',
				type : 'string'
			}, {
				name : 'orgCode',
				type : 'string'
			}, {
				name : 'groupSiteId',
				type : 'string'
			}, {
				name : 'groupSiteName',
				type : 'string'
			}, {
				name : 'relevantOrgCode',// 目的地
				type : 'string'
			}, {
				name : 'relevantOrgName',// 目的地
				type : 'string'
			}, {
				name : 'weightTotal',// 总重量
				type : 'float'
			}, {
				name : 'volumeTotal',// 总体积
				type : 'float'
			}, {
				name : 'waybillQtyTotal',// 总票数
				type : 'float'
			}, {
				name : 'flfWeightTotal',// 卡航重量
				type : 'float'
			}, {
				name : 'flfVolumeTotal',// 卡航体积
				type : 'float'
			}, {
				name : 'flfQtyTotal',// 卡航票数
				type : 'float'
			}, {
				name : 'fsfWeightTotal',// 城运重量
				type : 'float'
			}, {
				name : 'fsfVolumeTotal',// 城运体积
				type : 'float'
			}, {
				name : 'fsfQtyTotal',// 城运票数
				type : 'float'
			}, {
				name : 'expWeightTotal',// 快递重量
				type : 'float'
			}, {
				name : 'expVolumeTotal',// 快递体积
				type : 'float'
			}, {
				name : 'expQtyTotal',// 快递票数
				type : 'float'
			}, {
				name : 'instoreWeightTotal',// 在库重量
				type : 'float'
			}, {
				name : 'instoreVolumeTotal',// 在库体积
				type : 'float'
			}, {
				name : 'instoreQtyTotal',// 在库票数
				type : 'float'
			}, {
				name : 'billedWeightTotal',// 开单重量
				type : 'float'
			}, {
				name : 'billedVolumeTotal',// 开单体积
				type : 'float'
			}, {
				name : 'billedQtyTotal',// 开单票数
				type : 'float'
			}, {
				name : 'intransitWeightTotal',// 在途重量
				type : 'float'
			}, {
				name : 'intransitVolumeTotal',// 在途体积
				type : 'float'
			}, {
				name : 'intransitQtyTotal',// 在途票数
				type : 'float'
			}, {
				name : 'type',// 预测类型 出发/到达
				type : 'string'
			}, {
				name : 'particularType',// 预测类型 出发/到达
				type : 'string'
			}, {
				name : 'forecastUnbilledWeightTotal',// 预测未开单总重量
				type : 'float'
			}, {
				name : 'forecastUnbilledVolumeTotal',// 预测未开单总体积
				type : 'float'
			}, {
				name : 'forecastUnbilledQtyTotal',// 预测未开单总票数
				type : 'float'
			}, {
				name : 'forecastWeightTotal',// 预测总重量
				type : 'float'
			}, {
				name : 'forecastVolumeTotal',// 预测总体积
				type : 'float'
			}, {
				name : 'forecastQtyTotal',// 预测总票数
				type : 'float'
			}, {
				name : 'dataBelongDate',// 货量日期
				type : 'date',
				convert : dateConvert
			}]
});

// 查询结果store
Ext.define('Foss.quantitySta.QueryResultStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.quantitySta.quantityStaModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : platform.realPath('queryQuantity.action'),
		reader : {
			type : 'json',
			root : 'quantityStaVo.quantityStaDtoList'
		}
	},
	listeners : {
		beforeload : function(store, operation) {
			var queryForm = platform.quantitySta.queryForm.getValues(),
				secondDay = false,
				today = new Date(),
				todayStr = Ext.util.Format.date(today, 'Y-m-d');
				todayDate = new Date(todayStr);
				todayDate.setHours(0, 0, 0, 0);
			if(queryForm.dataBelongDate != todayStr){
				secondDay = true;
			}
			Ext.apply(operation, {
				params : {
					'quantityStaVo.quantityStaQcDto.relevantOrgCode' : queryForm.relevantOrgCode,
					'quantityStaVo.quantityStaQcDto.staDate' : todayDate,
					'quantityStaVo.quantityStaQcDto.secondDay' : secondDay,
					'quantityStaVo.quantityStaQcDto.type' : queryForm.type,
					'quantityStaVo.quantityStaQcDto.dataType' : queryForm.particularType,
					'quantityStaVo.quantityStaQcDto.transferCenterCode' : platform.quantitySta.transferCenterCode
				}
			});
		},
		load : function(store, records, successful, epots) {
			if (store.getCount() == 0) {
				Ext.ux.Toast.msg('提示', '查询结果为空！', 'error');
			}
			//更新grid表头上的统计时间
			var queryForm = platform.quantitySta.queryForm.getValues(),
				queryDateStr = queryForm.dataBelongDate,
				queryDate = new Date(queryDateStr),
				nextDate = new Date(queryDate.getFullYear(),queryDate.getMonth(),queryDate.getDate() + 1),
				nextDateStr = Ext.Date.format(nextDate, 'Y-m-d')
				staStartStr = store.getProxy().getReader().rawData.quantityStaVo.staStartTime,
				staStartTime = staStartStr.substr(0,2) + ':' + staStartStr.substr(2,2) + ':' + '00';
			
			var timeRange = '时间范围：' + queryDateStr + ' ' + staStartTime
					+ '至' + nextDateStr + ' ' + staStartTime;
			platform.quantitySta.queryResultGrid.getTimeRangeLable()
					.update(timeRange);
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//因为js浮点数运算的问题，舍弃summaryType的sum，自定义求和方法
platform.quantitySta.sum = function(records, field) {
	var i = 0, length = records.length, total, record, sum = 0;
	for (; i < length; ++i) {
		record = records[i];
		if(record.get(field) == null || record.get(field) == ''){
			sum += 0;
		}else{
			sum += parseFloat(record.get(field));
		}
	}
	total = sum.toFixed(3);
	//使用toString()截取掉保留三位后多余的0
	return parseFloat(total).toString();
};

//查询结果grid
Ext.define('Foss.quantitySta.QueryResultGrid', {
	extend : 'Ext.grid.Panel',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 915,
	enableColumnHide : false,
    //增加表格列的分割线
	columnLines: true,
	// 表格对象增加一个边框
	frame : true,
	collapsible: true,
	animCollapse: false,
	// 定义表格的标题
	title : '查询结果',
	selModel : null,
	store : null,
	features : [{
		ftype : 'groupingsummary',
		hideGroupedHeader : true
	}],
	timeRangeLable : null,
	getTimeRangeLable : function() {
		if (this.timeRangeLable == null) {
			this.timeRangeLable = Ext.create('Ext.form.Label');
		}
		return this.timeRangeLable;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.quantitySta.QueryResultStore', {
			groupField : 'groupSiteName'
		});
		me.dockedItems = [{
			xtype : 'toolbar',
			dock : 'top',
			padding : '0 5 0 5',
			items : [{
				xtype : 'button',
				hidden : !platform.quantitySta.isPermission('platform/showTotalChartButton'),
				iconCls : 'deppon_icons_viewChartWhite',
				text : '查看统计图表',
				handler : function() {
					//定义查询参数，在下一界面接收
					var queryForm = platform.quantitySta.queryForm.getValues();
					//预测类型
					platform.quantitySta.type = queryForm.type;
					//出发(到达)类型
					platform.quantitySta.particularType = queryForm.particularType;
					//如果起始(目的)地不为空，则传至下一页面
					if(queryForm.relevantOrgCode != null){
						var relevantOrgCmp = platform.quantitySta.queryForm.getForm().findField('relevantOrgCode');
						platform.quantitySta.relevantOrgName = relevantOrgCmp.store.findRecord('code',queryForm.relevantOrgCode,0,false,true,true).get('name');
						platform.quantitySta.relevantOrgCode = queryForm.relevantOrgCode;
					}else{
						platform.quantitySta.relevantOrgName = null;
						platform.quantitySta.relevantOrgCode = null;
					}
					//打开新的tab页（货量统计图表）
					var mainTab = Ext.getCmp('mainAreaPanel'),
				    	tab = Ext.getCmp('T_platform-quantityStaChartIndex');
					if(tab){
						mainTab.remove(tab,true);
						addTab('T_platform-quantityStaChartIndex','货量统计图表',platform.realPath('quantityStaChartIndex.action'));
					}else{
						addTab('T_platform-quantityStaChartIndex','货量统计图表',platform.realPath('quantityStaChartIndex.action'));
					}
				}
			}, '->', {
				xtype : 'label'
			}, me.getTimeRangeLable(), '->', {
				xtype : 'button',
				hidden : !platform.quantitySta.isPermission('platform/exportQuantityExcelButton'),
				text : '导出统计数据',
				handler : function() {
					var queryForm = platform.quantitySta.queryForm.getValues(),
						secondDay = false,
						todayDate = new Date(),
						todayStr = Ext.util.Format.date(todayDate, 'Y-m-d');
					todayDate.setHours(0, 0, 0, 0);
					if(queryForm.dataBelongDate != todayStr){
						secondDay = true;
					}
					var params = {
						'quantityStaVo.quantityStaQcDto.relevantOrgCode' : queryForm.relevantOrgCode,
						'quantityStaVo.quantityStaQcDto.staDate' : todayDate,
						'quantityStaVo.quantityStaQcDto.secondDay' : secondDay,
						'quantityStaVo.quantityStaQcDto.type' : queryForm.type,
						'quantityStaVo.quantityStaQcDto.dataType' : queryForm.particularType,
						'quantityStaVo.quantityStaQcDto.transferCenterCode' : platform.quantitySta.transferCenterCode
					}
					if (!Ext.fly('downloadAttachFileForm')) {
						var frm = document.createElement('form');
						frm.id = 'downloadAttachFileForm';
						frm.style.display = 'none';
						document.body.appendChild(frm);
					}
					Ext.Ajax.request({
						url : platform.realPath('exportQuantity.action'),
						form : Ext.fly('downloadAttachFileForm'),
						method : 'POST',
						params : params,
						isUpload : true,
						exception : function(response){
							var json = Ext.decode(response.responseText);
							Ext.ux.Toast.msg('提示', '导出失败，' + json.quantityStaVo.exportError, 'error');
						}
					});
				}
			}]
		}];
		me.callParent([cfg]);
	},
	// 定义表格列信息
	columns : [{
		xtype : 'actioncolumn',
		sortable : false,
		width : 70,
		align : 'center',
		text : '操作',
		items : [{
			iconCls : 'foss_icons_tfr_viewChart',
			tooltip : '查看图表',
			handler : function(gridView, rowIndex, colIndex) {
				var record = gridView.getStore().getAt(rowIndex);
				//预测类型
				platform.quantitySta.type = record.get('type');
				//出发(到达)类型
				platform.quantitySta.particularType = record.get('particularType');
				//起始地、目的地
				platform.quantitySta.relevantOrgName = record.get('relevantOrgName');
				platform.quantitySta.relevantOrgCode = record.get('relevantOrgCode');
				//打开新的tab页（货量统计图表）
				var mainTab = Ext.getCmp('mainAreaPanel'),
			    	tab = Ext.getCmp('T_platform-quantityStaChartIndex');
				if(tab){
					mainTab.remove(tab,true);
					addTab('T_platform-quantityStaChartIndex','货量统计图表',platform.realPath('quantityStaChartIndex.action'));
				}else{
					addTab('T_platform-quantityStaChartIndex','货量统计图表',platform.realPath('quantityStaChartIndex.action'));
				}
			}
		}],
		renderer : function(value,metaData,record,rowIndex,colIndex,store,view){
			//如果是第二天的货量，则不显示查看图表按钮Ext.Date.format(nextDate, 'Y-m-d')
			var dataBelongDate = record.get('dataBelongDate'),
				dataStr = Ext.Date.format(dataBelongDate, 'Y-m-d'),
				todayStr = Ext.Date.format(new Date(), 'Y-m-d');
			if(dataStr !== todayStr){
				this.items[0].iconCls = '';
			}else{
				this.items[0].iconCls = 'foss_icons_tfr_viewChart';
			}
		}
	}, {
		// 字段标题
		header : '起始(目的)地',
		// 关联model中的字段名
		dataIndex : 'relevantOrgName',
		xtype : 'ellipsiscolumn',
		sortable : false,
		width : 180,
		summaryRenderer : function() {
			return '&nbsp&nbsp&nbsp&nbsp&nbsp小计';
		}
	}, {
		text : '实际货量',
		columns : [{
				header: '站点组', 
				dataIndex: 'groupSiteName',
				width:60
			},{
				// 字段标题
				header : '总票数',
				// 关联model中的字段名
				dataIndex : 'waybillQtyTotal',
				align : 'center',
				sortable : false,
				xtype : 'ellipsiscolumn',
				width : 60,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'waybillQtyTotal');
				}
			}, {
				// 字段标题
				header : '总重量',
				// 关联model中的字段名
				dataIndex : 'weightTotal',
				align : 'center',
				sortable : false,
				xtype : 'ellipsiscolumn',
				width : 70,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'weightTotal');
				}
			}, {
				// 字段标题
				header : '总体积',
				// 关联model中的字段名
				dataIndex : 'volumeTotal',
				align : 'center',
				sortable : false,
				xtype : 'ellipsiscolumn',
				width : 70,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'volumeTotal');
				}
			}, {
				text : '卡货',
				columns : [{
					header : '重量',
					dataIndex : 'flfWeightTotal',
					align : 'center',
					sortable : false,
					xtype : 'ellipsiscolumn',
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'flfWeightTotal');
					}
				},{
					header : '体积',
					dataIndex : 'flfVolumeTotal',
					align : 'center',
					sortable : false,
					xtype : 'ellipsiscolumn',
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'flfVolumeTotal');
					}
				},{
					header : '票数',
					dataIndex : 'flfQtyTotal',
					align : 'center',
					sortable : false,
					xtype : 'ellipsiscolumn',
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'flfQtyTotal');
					}
				}]
			}, {
				text : '城货',
				columns : [{
					header : '重量',
					dataIndex : 'fsfWeightTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'fsfWeightTotal');
					}
				},{
					header : '体积',
					dataIndex : 'fsfVolumeTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'fsfVolumeTotal');
					}
				},{
					header : '票数',
					dataIndex : 'fsfQtyTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'fsfQtyTotal');
					}
				}]
			},{
				text : '快递',
				columns : [{
					header : '重量',
					dataIndex : 'expWeightTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'expWeightTotal');
					}
				},{
					header : '体积',
					dataIndex : 'expVolumeTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'expVolumeTotal');
					}
				},{
					header : '票数',
					dataIndex : 'expQtyTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'expQtyTotal');
					}
				}]
			},{
				text : '开单未交接',
				columns : [{
					header : '重量',
					dataIndex : 'billedWeightTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'billedWeightTotal');
					}
				},{
					header : '体积',
					dataIndex : 'billedVolumeTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'billedVolumeTotal');
					}
				},{
					header : '票数',
					dataIndex : 'billedQtyTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'billedQtyTotal');
					}
				}]
			},{
				text : '库存余货',
				columns : [{
					header : '重量',
					dataIndex : 'instoreWeightTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'instoreWeightTotal');
					}
				},{
					header : '体积',
					dataIndex : 'instoreVolumeTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'instoreVolumeTotal');
					}
				},{
					header : '票数',
					dataIndex : 'instoreQtyTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'instoreQtyTotal');
					}
				}]
			},{
				text : '在途',
				columns : [{
					header : '重量',
					dataIndex : 'intransitWeightTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'intransitWeightTotal');
					}
				},{
					header : '体积',
					dataIndex : 'intransitVolumeTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'intransitVolumeTotal');
					}
				},{
					header : '票数',
					dataIndex : 'intransitQtyTotal',
					align : 'center',
					xtype : 'ellipsiscolumn',
					sortable : false,
					width : 55,
					summaryType : function(records){
						return platform.quantitySta.sum(records,'intransitQtyTotal');
					}
				}]
			}]
	}, {
		text : '预测货量',
		columns : [{
			text : '预测未开单货量',
			columns : [{
				header : '重量',
				dataIndex : 'forecastUnbilledWeightTotal',
				align : 'center',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 55,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'forecastUnbilledWeightTotal');
				}
			},{
				header : '体积',
				dataIndex : 'forecastUnbilledVolumeTotal',
				align : 'center',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 55,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'forecastUnbilledVolumeTotal');
				}
			},{
				header : '票数',
				dataIndex : 'forecastUnbilledQtyTotal',
				align : 'center',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 55,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'forecastUnbilledQtyTotal');
				}
			}]
		}, {
			text : '总货量',
			columns : [{
				header : '重量',
				dataIndex : 'forecastWeightTotal',
				align : 'center',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 55,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'forecastWeightTotal');
				}
			},{
				header : '体积',
				dataIndex : 'forecastVolumeTotal',
				align : 'center',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 55,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'forecastVolumeTotal');
				}
			},{
				header : '票数',
				dataIndex : 'forecastQtyTotal',
				align : 'center',
				xtype : 'ellipsiscolumn',
				sortable : false,
				width : 55,
				summaryType : function(records){
					return platform.quantitySta.sum(records,'forecastQtyTotal');
				}
			}]
		}]
	}]
});

// 短途
// 新增发车计划，并初始化发车计划
platform.quantitySta.addAndInitShortTruckPlan = function(origOrgCode, destOrgCode,
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
						if (!Ext.isEmpty(Ext.getCmp('T_platform-shortScheduleDesignIndex'))) {
							removeTab('T_platform-shortScheduleDesignIndex');
						}

						addTab(
								'T_platform-shortScheduleDesignIndex',// 对应打开的目标页面js的onReady里定义的renderTo
								platform.quantitySta.i18n('foss.platform.quantitySta.quantitySta.shortDepartPlan'),// 打开的Tab页的标题
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
					Ext.MessageBox.alert(platform.quantitySta.i18n('foss.platform.quantitySta.quantitySta.error'), json.message);
				}
			});
}

// 长途
// 新增发车计划，并初始化发车计划
platform.quantitySta.addAndInitLongTruckPlan = function(origOrgCode, destOrgCode,
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
				if (!Ext.isEmpty(Ext.getCmp('T_platform-longScheduleDesignIndex'))) {
					removeTab('T_platform-longScheduleDesignIndex');
				}
				addTab(
						'T_platform-longScheduleDesignIndex',// 对应打开的目标页面js的onReady里定义的renderTo
						platform.quantitySta.i18n('foss.platform.quantitySta.quantitySta.longDepartPlan'),// 打开的Tab页的标题
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
			Ext.MessageBox.alert(platform.quantitySta.i18n('foss.platform.quantitySta.quantitySta.error'), json.message);
		}
	});
}

Ext.onReady(function() {
	platform.quantitySta.queryForm = Ext.create('Foss.quantitySta.QueryForm');
	platform.quantitySta.queryResultGrid = Ext.create('Foss.quantitySta.QueryResultGrid');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-quantityStaIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [platform.quantitySta.queryForm,
				platform.quantitySta.queryResultGrid],
		renderTo : 'T_platform-quantityStaIndex-body'
	});
});