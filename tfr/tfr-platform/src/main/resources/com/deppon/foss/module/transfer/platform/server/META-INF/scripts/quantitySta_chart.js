// 查询条件
Ext.define('Foss.quantityStaChart.QueryForm', {
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
			dateArray = [],
			todayStr = Ext.util.Format.date(today, 'Y-m-d');
		dateArray.push({'dataBelongDate' : todayStr});
		//日期下拉框初始化为最近8天
		for(var i = 1;i < 8;i++){
			var beforeDate = new Date(today.getFullYear(),today.getMonth(),today.getDate() - i),
				dateStr = Ext.util.Format.date(beforeDate, 'Y-m-d');
			dateArray.push({'dataBelongDate' : dateStr});
		}
		if (me.timeCombo == null) {
			var store = Ext.create('Ext.data.Store', {
				fields : ['dataBelongDate'],
				data : dateArray
			});
			me.timeCombo = Ext.create('Ext.form.ComboBox', {
				name : 'dataBelongDate',
				fieldLabel : '日期',
				editable : false,
				queryMode : 'local',
				allowBlank : false,
				margin : '5 0 5 140',
				width : 300,
				multiSelect : true,
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
		me.items = [me.getTypeCombo(),me.getParticularType(), me.getTimeCombo(),me.getRelevantOrgCode(),{
	        xtype : 'radiogroup',
	        margin : '12 140 5 440',
	        width : 230,
	        items : [
	            { boxLabel : '按重量(吨)', name : 'chartType', inputValue : 'byWeight', checked : true},
	            { boxLabel : '按体积(方)', name : 'chartType', inputValue : 'byVolume' }
	        ]
	    },{
			xtype : 'container',
			margin : '-34 140 5 665',
			items : [{
				xtype : 'button',
				disabled : !platform.quantityStaChart.isPermission('platform/queryQuantityChartButton'),
				hidden : !platform.quantityStaChart.isPermission('platform/queryQuantityChartButton'),
				text : '查询',
				cls : 'yellow_button',
				arrowAlign : 'bottom',
				minWidth : 93,
				handler : function() {
					if (!me.getForm().isValid()) {
						Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error');
						return;
					}
					var params = me.getValues(),
						dateList = params.dataBelongDate,
						chartType = params.chartType,
						columnNameArray,
						unit,
						warnField;
					if(chartType == 'byVolume'){
						columnNameArray = ['volumeActual','volumeFcst'];
						warnField = 'volumeWarn';
						unit = '方';
					}else{
						columnNameArray = ['weightActual','weightFcst'];
						warnField = 'weightWarn';
						unit = '吨';
					}
					//把之前的chart移除
					platform.quantityStaChart.chartPanel.removeAll(true);
					//日期排序，因为是正序，所以倒序循环
					dateList.sort();
					for(var i = dateList.length - 1; i >= 0; i--){
						var chart = Ext.create('Foss.quantityStaChart.dayQuantityChart',{
							'columnNameArray' : columnNameArray,
							'staDate' : dateList[i],
							'warnField' : warnField,
							'unit' : unit
						});
						chart.bindData(params.type,params.particularType,dateList[i],params.relevantOrgCode);
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});

//用以显示图表的Model
Ext.define('Foss.quantityStaChart.chartModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'weightFcst',//总重量
		type : 'float'
	},{
		name : 'volumeFcst',//总体积
		type : 'float'
	},{
		name : 'staHh',//货量时间点
		type : 'string'
	},{
		name : 'weightActual',//实际重量
		type : 'float'
	},{
		name : 'volumeActual',//实际体积
		type : 'float'
	},{
		name : 'weightWarn',//警戒重量
		type : 'float'
	},{
		name : 'volumeWarn',//警戒体积
		type : 'float'
	},{
		name : 'orgCode',//转运场code
		type : 'string'
	},{
		name : 'relevantOrgName',//相关部门Name
		type : 'string'
	},{
		name : 'type',//预测类型
		type : 'string'
	},{
		name : 'particularType',//出发/到达类型
		type : 'string'
	},{
		name : 'dataBelongDate',//货量所属日期
		type : 'date',
		convert : dateConvert
	},{
		name : 'staDate',//统计日期
		type : 'date',
		convert : dateConvert
	}]
});

//用于显示日货量图表的store
Ext.define('Foss.quantityStaChart.dayChartStore', {
	extend: 'Ext.data.Store',
	model : 'Foss.quantityStaChart.chartModel',
	proxy : {
		type : 'ajax',
		actionMethods: 'POST',
		url : platform.realPath('queryDayChart.action'),
		reader : {
			type : 'json',
			root : 'quantityStaChartVo.quantitySta4ChartDtoList'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

////自定义颜色主题
//Ext.define('Ext.chart.theme.QuantityStaChartTheme', {  
//    extend : 'Ext.chart.theme.Base',         
//    constructor : function(config) {  
//        this.callParent([Ext.apply({  
//            colors : ['#373C64','#FAAF19']
//        }, config)]);  
//    }  
//});

//日货量信息chart
Ext.define('Foss.quantityStaChart.dayQuantityChart',{
	extend : 'Ext.chart.Chart',
//	theme : 'QuantityStaChartTheme',
	height : 350,
	animate: {
	    duration: 1000
	},
	shadow: true,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config),
			columnNameArray = config.columnNameArray,
			warnField = config.warnField,
			unit = config.unit,
			staDate = config.staDate;
		me.store = Ext.create('Foss.quantityStaChart.dayChartStore');	
		me.axes = [{
			type: 'Numeric',
            position: 'left',
            fields: columnNameArray,
            minimum: 0,
            grid: true
		},{
			type: 'Category',
            position: 'bottom',
            fields: 'staHh',
            title : staDate,
            label: {
				renderer: function(value) {
					if(Ext.isEmpty(value)){
						return;
					}
					return value + '点';
				}
			}
		}];
		me.series = [{
			type: 'column',
            axis: 'left',
            xField: 'staHh',
            yField: columnNameArray,
            highLight : true,
            showInLegend : false,
            renderer : function(sprite, storeItem, src, i, store) {
            	//此处i为偶数时为渲染第一个柱子，为奇数时为渲染第二个柱子，可使用这种方式改变柱子颜色，实现类似theme的功能
            	if(i%2 == 0){
            		src.fill = '#373C64';
            	}else{
            		src.fill = '#FAAF19';
            	}
				return src;
			},
			tips: {
				trackMouse: true,
				width : 150,
				height : 50,
				renderer: function(storeItem, item) {
					//提示文本
					this.setTitle('实际值：' + storeItem.get(columnNameArray[0]) + unit + '</br>预测值：' + storeItem.get(columnNameArray[1]) + unit + '</br>双击查看近8天' + storeItem.get('staHh') + '点货量');
				}
			},
			listeners : {
				'itemdblclick' : function(src, event) {
					var rec = src.storeItem.data,
						i = 1;
					if(platform.quantityStaChart.hourWin == null){
						platform.quantityStaChart.hourWin = Ext.create('Foss.quantitySta.hourChartWindow');
						i = 0;
					};
					var win = platform.quantityStaChart.hourWin,
						relevantOrgName = rec.relevantOrgName,
						orgName = platform.quantityStaChart.transferCenterName;
					if(Ext.isEmpty(relevantOrgName)){
						relevantOrgName = '';
					}else{
						if(rec.type == 'DEPARTURE'){
							relevantOrgName = '--' + relevantOrgName;
						}else{
							relevantOrgName = relevantOrgName + '--';
						}
					}
					if(rec.type == 'DEPARTURE'){
						win.setTitle(rec.staHh + '点&nbsp' + orgName + relevantOrgName + '出发货量图表');
					}else{
						win.setTitle(rec.staHh + '点&nbsp' + relevantOrgName + orgName + '到达货量图表');
					}
					win.showWin({columnNameArray : ['weightActual','weightFcst'],
							warnField : 'weightWarn',
							type : rec.type,
							i : i,
							particularType : rec.particularType,
							relevantOrgCode : rec.relevantOrgCode,
							staHh : rec.staHh,
							transferCenterCode : platform.quantityStaChart.transferCenterCode});
				}
			}
		},{
            type : 'line',  
            yField : warnField,
            xField : 'staHh',
            highLight : true,
            showMarkers : false,
            shadowAttributes: [{
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }, {
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }, {
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }, {
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }],
            tips: {
    			trackMouse: true,
    			width: 110,
    			height: 25,
    			renderer: function(storeItem, item) {
    				this.setTitle('警戒值：' + storeItem.get(warnField) + unit);
    			}
    		}
        }];
		me.callParent([cfg]);
	},
	bindData : function(type,particularType,dataBelongDate,relevantOrgCode){
    	var me = this,
    		store = me.store;
    	//保证日期排序，所以未load前先添加
    	platform.quantityStaChart.chartPanel.add(me);
    	store.load({
    		params : {
				'quantityStaChartVo.quantityStaQcDto.transferCenterCode' : platform.quantityStaChart.transferCenterCode,
				'quantityStaChartVo.quantityStaQcDto.type' : type,
				'quantityStaChartVo.quantityStaQcDto.dataType' : particularType,
				'quantityStaChartVo.quantityStaQcDto.staDate' : dataBelongDate,
				'quantityStaChartVo.quantityStaQcDto.relevantOrgCode' : relevantOrgCode
    		},
    		callback : function(records, operation, success){
    			if(records.length == 0){
    				//如果该天没有货量信息再移除
    				platform.quantityStaChart.chartPanel.remove(me,true);
    			}
    		}
    	});
    }
});

//日货量查询结果图表面板
Ext.define('Foss.quantityStaChart.dayChartPanel', {
	extend : 'Ext.panel.Panel',
	title : '查询结果',
	frame : true,
	collapsible : true,
	layout: {
        type: 'vbox',
        align: 'stretch'
    },//这一段一定要有，否则会有layout run failed的错误，图表无法渲染
	animCollapse : true,
	tbar : ['->',{
		xtype : 'container',
		width : 23,
		height : 10,
		style : 'background-color:#373C64;'
	},{
		xtype : 'label',
		text : '实际值'
	},{
		xtype : 'container',
		width : 23,
		height : 10,
		style : 'background-color:#FAAF19;'
	},{
		xtype : 'label',
		text : '预测值'
	},{
		xtype : 'container',
		width : 23,
		height : 10,
		style : 'background-color:#FF0000;'
	},{
		xtype : 'label',
		text : '警戒值'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//用于显示最近8天的某整点货量图表的store
Ext.define('Foss.quantityStaChart.hourChartStore', {
	extend: 'Ext.data.Store',
	model : 'Foss.quantityStaChart.chartModel',
	proxy : {
		type : 'ajax',
		actionMethods: 'POST',
		url : platform.realPath('queryHourChart.action'),
		reader : {
			type : 'json',
			root : 'quantityStaChartVo.quantitySta4ChartDtoList'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//整点货量信息chart
Ext.define('Foss.quantityStaChart.hourQuantityChart',{
	extend : 'Ext.chart.Chart',
	height : 350,
	animate: {
	    duration: 1000
	},
	padding : '10 0 0 0',
	shadow: true,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config),
			columnNameArray = config.columnNameArray,
			unit = config.unit,
			warnField = config.warnField;
		me.store = Ext.create('Foss.quantityStaChart.hourChartStore');	
		me.axes = [{
			type: 'Numeric',
            position: 'left',
            fields: columnNameArray,
            minimum: 0,
            grid: true
		},{
			type: 'Category',
            position: 'bottom',
            fields: 'staDate',
            label: {
				renderer: function(value) {
					if(Ext.isEmpty(value)){
						return;
					}
					return Ext.util.Format.date(value, 'Y-m-d');
				}
			}
		}];
		me.series = [{
			type: 'column',
            axis: 'left',
            xField: 'staDate',
            yField: columnNameArray,
            showInLegend : false,
            highlight: true,
            renderer : function(sprite, storeItem, src, i, store) {
            	//此处i为偶数时为渲染第一个柱子，为奇数时为渲染第二个柱子，可使用这种方式改变柱子颜色，实现类似theme的功能
            	if(i%2 == 0){
            		src.fill = '#373C64';
            	}else{
            		src.fill = '#FAAF19';
            	}
				return src;
			},
			tips: {
				trackMouse: true,
				width : 150,
				height : 40,
				renderer: function(storeItem, item) {
					//提示文本
					this.setTitle('实际值：' + storeItem.get(columnNameArray[0]) + unit + '</br>' + '预测值：' + storeItem.get(columnNameArray[1]) + unit);
				}
			}
		},{
            type : 'line',  
            yField : warnField,
            xField : 'staDate',
            highlight: true,
            showMarkers : false,
            shadowAttributes: [{
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }, {
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }, {
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }, {
            	'stroke-opacity' : 0.3,
                stroke: 'rgb(255, 0, 0)',
                translate: {
                    x: 1,
                    y: 1
                }
            }],
            tips: {
    			trackMouse: true,
    			width: 110,
    			height: 25,
    			renderer: function(storeItem, item) {
    				this.setTitle('警戒值：' + storeItem.get(warnField) + unit);
    			}
    		}
        }];
		me.callParent([cfg]);
	},
	bindData : function(type,particularType,staHh,relevantOrgCode){
    	var me = this,
    		store = me.store;
    	var todayDate = new Date();
    	todayDate.setHours(0, 0, 0, 0);
    	var eightDaysAgo = new Date(todayDate.getFullYear(),todayDate.getMonth(),todayDate.getDate() - 7)
    	store.load({
    		params : {
				'quantityStaChartVo.quantityStaQcDto.transferCenterCode' : platform.quantityStaChart.transferCenterCode,
				'quantityStaChartVo.quantityStaQcDto.type' : type,
				'quantityStaChartVo.quantityStaQcDto.dataType' : particularType,
				'quantityStaChartVo.quantityStaQcDto.staHh' : staHh,
				'quantityStaChartVo.quantityStaQcDto.beginStaDate' : eightDaysAgo,
				'quantityStaChartVo.quantityStaQcDto.endStaDate' : todayDate,
				'quantityStaChartVo.quantityStaQcDto.relevantOrgCode' : relevantOrgCode
    		}
    	});
    }
});

//双击日货量信息中某个小时的货量后弹出的窗口
Ext.define('Foss.quantitySta.hourChartWindow',{
	extend:'Ext.window.Window',
	width: 1024,
	height: 440,
	layout: {
        type: 'vbox',
        align: 'stretch'
    },
	closeAction: 'hide',
	resizable : false,
	modal : true,
	tbar : [{
		xtype : 'button',
		text : '按重量(吨)',
		disabled : true,
		handler : function(){
			var win = platform.quantityStaChart.hourWin,
				params = win.params;
			this.setDisabled(true);
			this.ownerCt.items.items[1].setDisabled(false);
			win.removeAll(true);
			var chart = Ext.create('Foss.quantityStaChart.hourQuantityChart',{
				columnNameArray : ['weightActual','weightFcst'],
				warnField : 'weightWarn',
				unit : '吨'
			});
			chart.bindData(params.type,params.particularType,params.staHh,params.relevantOrgCode);
			win.add(chart);
		}
	},{
		xtype : 'button',
		text : '按体积(方)',
		handler : function(){
			var win = platform.quantityStaChart.hourWin,
				params = win.params;
			this.setDisabled(true);
			this.ownerCt.items.items[0].setDisabled(false);
			win.removeAll(true);
			var chart = Ext.create('Foss.quantityStaChart.hourQuantityChart',{
				columnNameArray : ['volumeActual','volumeFcst'],
				warnField : 'volumeWarn',
				unit : '方'
			});
			chart.bindData(params.type,params.particularType,params.staHh,params.relevantOrgCode);
			win.add(chart);
		}
	},'->',{
		xtype : 'container',
		width : 23,
		height : 10,
		style : 'background-color:#373C64;'
	},{
		xtype : 'label',
		text : '实际值'
	},{
		xtype : 'container',
		width : 23,
		height : 10,
		style : 'background-color:#FAAF19;'
	},{
		xtype : 'label',
		text : '预测值'
	},{
		xtype : 'container',
		width : 23,
		height : 10,
		style : 'background-color:#FF0000;'
	},{
		xtype : 'label',
		text : '警戒值'
	}],
	params : null,
	showWin : function(params){
		var me = this;
		/**
		 * 设置重量按钮灰色，体积按钮可用
		 * create window时，i为0，create后i为1，所以此处传入
		 */
		me.dockedItems.items[params.i].items.items[0].setDisabled(true);
		me.dockedItems.items[params.i].items.items[1].setDisabled(false);
		me.params = params;
		me.removeAll(true);
		var chart = Ext.create('Foss.quantityStaChart.hourQuantityChart',{
			columnNameArray : params.columnNameArray,
			warnField : params.warnField,
			unit : '吨'
		});
		chart.bindData(params.type,params.particularType,params.staHh,params.relevantOrgCode);
		me.add(chart);
		me.show();
	},
	constructor: function(config) {
		var me = this,
			cfg = Ext.apply(me, config);
		me.callParent(cfg);
	}
});

Ext.onReady(function() {
	//接收上一页面的转运场code
	platform.quantityStaChart.transferCenterCode = platform.quantitySta.transferCenterCode;
	platform.quantityStaChart.transferCenterName = platform.quantitySta.transferCenterName;
	//查询form和charPanel
	platform.quantityStaChart.queryForm = Ext.create('Foss.quantityStaChart.QueryForm');
	platform.quantityStaChart.chartPanel = Ext.create('Foss.quantityStaChart.dayChartPanel');
	
	//1、创建主页面
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-quantityStaChartIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		items : [platform.quantityStaChart.queryForm,
		         platform.quantityStaChart.chartPanel],
		renderTo : 'T_platform-quantityStaChartIndex-body'
	});
	
	//2、初始化查询条件仅初始化起始（目的）地、预测类型、出发到达类型(因有change事件)
	platform.quantityStaChart.queryForm.getForm().findField('type').setValue(platform.quantitySta.type);
	platform.quantityStaChart.queryForm.getForm().findField('particularType').setValue(platform.quantitySta.particularType);
	if(!Ext.isEmpty(platform.quantitySta.relevantOrgName)){
		platform.quantityStaChart.queryForm.getForm().findField('relevantOrgCode').setCombValue(platform.quantitySta.relevantOrgName,platform.quantitySta.relevantOrgCode);
	}
	
	//3、执行查询按钮动作
	platform.quantityStaChart.queryForm.items.items[5].items.items[0].handler();
});