platform.goodsAreaDensityDetail.goodsAreaTypeStore = FossDataDictionary.getDataDictionaryStore('BSE_GOODSAREA_TYPE');
platform.goodsAreaDensityDetail.goodsAreaTypeStore.insert(0,{'valueCode' : '','valueName' : '全部'});

//外场库区密度明细  日库区密度查询form
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '外场',
		name : 'outfieldCode',
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		readOnly : !Ext.isEmpty(platform.goodsAreaDensityDetail.outfieldCode),
		transferCenter : 'Y',
		listeners : {
			'change' : function(field,  newValue,  oldValue,  eOpts){
				var goodsAreaCmp = this.up('form').getForm().findField('goodsAreaCode');
				goodsAreaCmp.reset();
				goodsAreaCmp.store.removeAll();
				goodsAreaCmp.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = newValue;
				});
				goodsAreaCmp.store.loadPage(1);
			}
		}
	}, {
		fieldLabel : '库区类型',
		labelWidth : 85,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable : false,
		store : platform.goodsAreaDensityDetail.goodsAreaTypeStore,
		name : 'goodsAreaType',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm(),
					goodsAreaCodeCmp = form.findField('goodsAreaCode');
				if(Ext.isEmpty(newValue)){
					goodsAreaCodeCmp.reset();
					goodsAreaCodeCmp.setReadOnly(true);
				}else{
					//库区控件可编辑
					goodsAreaCodeCmp.reset();
					goodsAreaCodeCmp.setReadOnly(false);
					//对库区增加库区类型过滤
					var goodsAreaCmp = this.up('form').getForm().findField('goodsAreaCode');
					goodsAreaCmp.reset();
					goodsAreaCmp.store.removeAll();
					goodsAreaCmp.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
								params : searchParams
							});
						}
						searchParams['goodsAreaVo.goodsAreaEntity.goodsAreaType'] = newValue;
					});
					goodsAreaCmp.store.loadPage(1);
				}
			}
		}
}, {
		fieldLabel : '库区',
		name : 'goodsAreaCode',
		xtype : 'commongoodsareaselector',
		readOnly : true
	}, {
		xtype : 'rangeDateField',
		fieldLabel : '查询周期',
		columnWidth : 2/3,
		dateType: 'datefield',
		fromName : 'dayDensityQueryStartTime',
		toName : 'dayDensityQueryEndTime',
		fromValue : new Date(),
		toValue : new Date(),
		allowBlank : false,
		disallowBlank : true
	},FossDataDictionary.getDataDictionaryCombo('TFR_TWENTY_FOUR_OCLOCK',{
		fieldLabel : '时间点',
		labelWidth : 85,
		name : 'statisticTimePoint',
		displayField : 'valueName',
	    valueField : 'valueCode',
	    value : new Date().getHours() >= 10 ? '' + new Date().getHours() : '0' + new Date().getHours(),
		allowBlank : false
}),{
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : '重置',
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				//如果当前部门为外场，则外场控件重新赋值
				if(!Ext.isEmpty(platform.goodsAreaDensityDetail.outfieldCode)){
					form.findField('outfieldCode').setCombValue(
							platform.goodsAreaDensityDetail.outfieldName,
							platform.goodsAreaDensityDetail.outfieldCode
					);
				}
				//日期赋值
				form.findField('dayDensityQueryStartTime').setValue(Ext.Date.format(new Date(), 'Y-m-d'));
				form.findField('dayDensityQueryStartTime').setValue(Ext.Date.format(new Date(), 'Y-m-d'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm(),
					goodsAreaCode = form.findField('goodsAreaCode').getValue();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error', 1500);
					return;
				}
				
				//校验日期范围
				var fromDate = form.findField('dayDensityQueryStartTime').getValue(),
					toDate = form.findField('dayDensityQueryEndTime').getValue();
				var mSeconds = toDate.getTime() - fromDate.getTime();
				var days = parseInt(mSeconds / (1000 * 60 * 60 * 24));
				if(Ext.isEmpty(goodsAreaCode)){
					if(days > 16){
						Ext.ux.Toast.msg('提示', '最大可查询16天的数据！', 'error', 1500);
						return;
					}
					if(fromDate - toDate != 0){
						Ext.ux.Toast.msg('提示', '库区为空时只能查询一天的数据！', 'error', 1500);
						return;
					}
					platform.goodsAreaDensityDetail.dayResultGrid.setVisible(true);
					platform.goodsAreaDensityDetail.dayResultCurveChartPanel.setVisible(false);
					platform.goodsAreaDensityDetail.dayResultGrid.store.load();
				}else{
					if(days > 31){
						Ext.ux.Toast.msg('提示', '最大可查询31天的数据！', 'error', 1500);
						return;
					}
					platform.goodsAreaDensityDetail.dayResultGrid.setVisible(false);
					platform.goodsAreaDensityDetail.dayResultCurveChartPanel.setVisible(true);
					platform.goodsAreaDensityDetail.dayResultCurveChartPanel.items.items[0].store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//外场库区密度明细  日列表之Model
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'transferCenterName',
		type : 'string'
	},{
		name : 'goodsAreaTypeName',
		type : 'string'
	}, {
		name : 'goodsAreaName',
		type : 'string'
	}, {
		name : 'goodsAreaVolume',
		type : 'string'
	}, {
		name : 'goodsVolume',
		type : 'string'
	}, {
		name : 'density',
		type : 'string'
	}]
});

//外场库区密度明细 日列表之store
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('selectPagingGoodsAreaDensity4Timely.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'goodsAreaDensityVo.goodsAreaDensityEntities',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.goodsAreaDensityDetail.dayQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticDate' : queryParams.dayDensityQueryStartTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticDate' : queryParams.dayDensityQueryEndTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.statisticTimePoint' : queryParams.statisticTimePoint,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//外场库区密度 日列表
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '库区密度信息',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var queryForm = platform.goodsAreaDensityDetail.dayQueryForm.getForm();
				queryParams = queryForm.getValues();
			if(!queryForm.isValid()){
				Ext.ux.Toast.msg('提示', '请输入导出条件！', 'error', 1500);
				return;
			}
			var orgName = queryForm.findField('outfieldCode').store.findRecord('code', queryParams.outfieldCode, false, true, true).get('name');
			var exportParams = {
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterName' : orgName,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticDate' : queryParams.dayDensityQueryStartTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticDate' : queryParams.dayDensityQueryEndTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.statisticTimePoint' : queryParams.statisticTimePoint,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType
			}; 
			if(!Ext.fly('downloadReturnRateFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadReturnRateFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
				url : platform.realPath('exportGoodsAreaDensity4Timely.action'),
				form: Ext.fly('downloadReturnRateFileForm'),
				method : 'POST',
				params : exportParams,
				isUpload: true,
				exception : function(response,opts) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('提示','导出失败',result.message);
					//myMask.hide();
				}	
			});
		}
	}],
	columns : [{
        xtype: 'rownumberer',
        width: 50,
        text : '序号',
        align : 'center',
        sortable: false
    },{
		dataIndex : 'transferCenterName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '外场'
	},{
		dataIndex : 'goodsAreaTypeName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区类型'
	}, {
		dataIndex : 'goodsAreaName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区名称'
	},{
		dataIndex : 'goodsAreaVolume',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区容量(F)'
	},{
		dataIndex : 'goodsVolume',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区货量(F)'
	},{
		dataIndex : 'density',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区密度',
		renderer : function(value){
			if(value != null){
				value = new String((value*100).toFixed(1));
				return value + '%';
			}else{
				return value;
			}
		}
	}]
});

//日库区密度查询，指定库区密度曲线图之Model
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurveModel', {
    extend: 'Ext.data.Model',
    fields : [{
		name : 'density',
		type : 'string',
		convert : function(value){
			return parseFloat(value);
		}
	},{
		name : 'statisticDate',
		type : 'date',
		convert : dateConvert
	}]
});

//日库区密度查询，指定库区密度曲线图之store
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurveStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurveModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('selectGoodsAreaDensity4Timely.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'goodsAreaDensityVo.goodsAreaDensityEntities',
			successProperty: 'success'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.goodsAreaDensityDetail.dayQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticDate' : queryParams.dayDensityQueryStartTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticDate' : queryParams.dayDensityQueryEndTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.statisticTimePoint' : queryParams.statisticTimePoint,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaCode' : queryParams.goodsAreaCode
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日库区密度查询，指定库区密度曲线图
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurveChart',{
    extend :  'Ext.chart.Chart',
    animate: false,
    store : Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurveStore'),
    width : 950,
    height : 400,
    insetPadding: 30,
    axes: [{
               type : 'Numeric',
               position : 'left',
               fields : ['density'],
               minimum : 0,
               grid : true,
               title : '库区密度',
               label : {
                   renderer: function(density) {
                       return (density*100).toFixed(1) + '%';
                   }
               }
           },{
               type: 'Category',
               position: 'bottom',
               title : '日期',
               fields: ['statisticDate'],
               label: {
                   renderer: function(statisticDate) {
                	   return Ext.Date.format(statisticDate,'m/d')
                   }
               }
           }
       ],
       series: [{
            type: 'line',
            xField: 'statisticDate',
            axis: ['left','bottom'],
            yField: 'density',
            tips: {
                trackMouse: true,
                width: 100,
                height: 40,
                renderer: function(storeItem, item) {
                	this.setTitle(Ext.Date.format(storeItem.get('statisticDate'),'Y/m/d'));
                    this.update((storeItem.get('density')*100).toFixed(1) + '%');
                }
            }
        }]
});

//日库区密度查询，指定库区密度曲线图面板
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurvePanel', {
	extend : 'Ext.panel.Panel',
	title : '库区密度信息',
	frame : true,
	collapsible : true,
	hidden : true,
	animCollapse : true,
	items : [Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurveChart')],
	tbar : [/*{
        text: '导出图片',
        handler: function(){
        	var chart = platform.goodsAreaDensityDetail.dayResultCurveChartPanel.items.items[0];
        	if(chart.store.getCount() > 0){
        		chart.save({
                    type: 'image/png'
                });
        	}else{
        		Ext.ux.Toast.msg('提示', '折线图中没有数据！', 'error', 1500);
				return;
        	}
        }
    },*/{
    	text: '导出表格',
        handler: function(){
			var queryForm = platform.goodsAreaDensityDetail.dayQueryForm.getForm();
				queryParams = queryForm.getValues();
			if(!queryForm.isValid()){
				Ext.ux.Toast.msg('提示', '请输入导出条件！', 'error', 1500);
				return;
			}
			var orgName = queryForm.findField('outfieldCode').store.findRecord('code', queryParams.outfieldCode, false, true, true).get('name');
			var exportParams = {
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterName' : orgName,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticDate' : queryParams.dayDensityQueryStartTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticDate' : queryParams.dayDensityQueryEndTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.statisticTimePoint' : queryParams.statisticTimePoint,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaCode' : queryParams.goodsAreaCode
			}; 
			if(!Ext.fly('downloadReturnRateFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadReturnRateFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
				url : platform.realPath('exportGoodsAreaDensity4Timely.action'),
				form: Ext.fly('downloadReturnRateFileForm'),
				method : 'POST',
				params : exportParams,
				isUpload: true,
				exception : function(response,opts) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('提示','导出失败',result.message);
				}	
			});
		}
    }],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//日库区密度查询form
platform.goodsAreaDensityDetail.dayQueryForm = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayQueryForm');

//日库区密度查询结果Grid
platform.goodsAreaDensityDetail.dayResultGrid = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayGrid');

//日库区密度查询结果Chart
platform.goodsAreaDensityDetail.dayResultCurveChartPanel = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailDayCurvePanel');

//外场库区密度明细  日库区密度查询form
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonQueryForm', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	collapsible : true,
	animCollapse : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 85,
		columnWidth : 1 / 3,
		xtype : 'textfield'
	},
	layout : 'column',
	items : [{
		fieldLabel : '外场',
		name : 'outfieldCode',
		allowBlank : false,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		readOnly : !Ext.isEmpty(platform.goodsAreaDensityDetail.outfieldCode),
		listeners : {
			'change' : function(field,  newValue,  oldValue,  eOpts){
				var goodsAreaCmp = this.up('form').getForm().findField('goodsAreaCode');
				goodsAreaCmp.reset();
				goodsAreaCmp.store.removeAll();
				goodsAreaCmp.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
							params : searchParams
						});
					}
					searchParams['goodsAreaVo.goodsAreaEntity.organizationCode'] = newValue;
				});
				goodsAreaCmp.store.loadPage(1);
			}
		}
	}, {
		fieldLabel : '库区类型',
		labelWidth : 85,
		xtype : 'combobox',
		queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable : false,
		store : platform.goodsAreaDensityDetail.goodsAreaTypeStore,
		name : 'goodsAreaType',
		listeners : {
			'change' : function(field, newValue, oldValue, eOpts){
				var form = this.up('form').getForm(),
					goodsAreaCodeCmp = form.findField('goodsAreaCode');
				if(Ext.isEmpty(newValue)){
					goodsAreaCodeCmp.reset();
					goodsAreaCodeCmp.setReadOnly(true);
				}else{
					//可输入库区
					goodsAreaCodeCmp.reset();
					goodsAreaCodeCmp.setReadOnly(false);
					//对库区增加库区类型过滤
					var goodsAreaCmp = this.up('form').getForm().findField('goodsAreaCode');
					goodsAreaCmp.reset();
					goodsAreaCmp.store.removeAll();
					goodsAreaCmp.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
								params : searchParams
							});
						}
						searchParams['goodsAreaVo.goodsAreaEntity.goodsAreaType'] = newValue;
					});
					goodsAreaCmp.store.loadPage(1);
				}
			}
		}
}, {
		fieldLabel : '库区',
		name : 'goodsAreaCode',
		readOnly : true,
		xtype : 'commongoodsareaselector'
	}, {
		xtype : 'rangeDateField',
		fieldLabel : '查询周期',
		columnWidth : 2/3,
		dateType: 'monthdatefield',
		fromName : 'monDensityQueryStartTime',
		toName : 'monDensityQueryEndTime',
		value : new Date(),
		allowBlank : false,
		disallowBlank : true
	}, {
		border : false,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		defaults : {
			margin : '5 0 5 0'
		},
		items : [ {
			xtype : 'button',
			columnWidth : .08,
			text : '重置',
			handler : function() {
				var form = this.up('form').getForm();
				form.reset();
				//如果当前部门为外场，则外场控件重新赋值
				if(!Ext.isEmpty(platform.goodsAreaDensityDetail.outfieldCode)){
					form.findField('outfieldCode').setCombValue(
							platform.goodsAreaDensityDetail.outfieldName,
							platform.goodsAreaDensityDetail.outfieldCode
					);
				}
				//日期赋值
				form.findField('monDensityQueryStartTime').setValue(Ext.Date.format(new Date(), 'Y-m'));
				form.findField('monDensityQueryStartTime').setValue(Ext.Date.format(new Date(), 'Y-m'));
			}
		}, {
			border : false,
			columnWidth : .84,
			html : '&nbsp;'
		}, {
			columnWidth : .08,
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			handler : function(){
				var form = this.up('form').getForm(),
					goodsAreaCode = form.findField('goodsAreaCode').getValue();
				if(!form.isValid()){
					Ext.ux.Toast.msg('提示', '请输入查询条件！', 'error', 1500);
					return;
				}
				var fromDate = form.findField('monDensityQueryStartTime').getValue(),
					toDate = form.findField('monDensityQueryEndTime').getValue();
				var monthCount = (toDate.getFullYear() - fromDate.getFullYear())*12 + (toDate.getMonth() - fromDate.getMonth());
				if(monthCount < 0){
					Ext.ux.Toast.msg('提示', '查询截止月份不能小于起始月份！', 'error', 1500);
					return;
				}
				if(monthCount >= 12){
					Ext.ux.Toast.msg('提示', '查询月份相差不能超过一年！', 'error', 1500);
					return;
				}
				if(Ext.isEmpty(goodsAreaCode)){
					if(fromDate - toDate != 0){
						Ext.ux.Toast.msg('提示', '库区为空时只能查询一个月的数据！', 'error', 1500);
						return;
					}
					platform.goodsAreaDensityDetail.monResultGrid.setVisible(true);
					platform.goodsAreaDensityDetail.monResultCurveChartPanel.setVisible(false);
					platform.goodsAreaDensityDetail.monResultGrid.store.load();
				}else{
					platform.goodsAreaDensityDetail.monResultGrid.setVisible(false);
					platform.goodsAreaDensityDetail.monResultCurveChartPanel.setVisible(true);
					platform.goodsAreaDensityDetail.monResultCurveChartPanel.items.items[0].store.load();
				}
			}
		} ]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//外场库区密度明细  月列表之Model
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'transferCenterName',
		type : 'string'
	},{
		name : 'goodsAreaTypeName',
		type : 'string'
	}, {
		name : 'goodsAreaName',
		type : 'string'
	}, {
		name : 'goodsAreaVolume',
		type : 'string'
	}, {
		name : 'goodsVolume',
		type : 'string'
	},{
		name : 'density',
		type : 'string'
	}]
});

//外场库区密度明细 月列表之store
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonStore', {
	extend : 'Ext.data.Store',
	pageSize : 20,
	// 绑定一个模型
	model : 'Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('selectPagingTfrCtrGoodsAreaAvgDensity4Monthly.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'goodsAreaDensityVo.goodsAreaDensityEntities',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.goodsAreaDensityDetail.monQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticMonth' : queryParams.monDensityQueryStartTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticMonth' : queryParams.monDensityQueryEndTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//外场库区密度 月tab列表
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	title : '库区密度信息',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	emptyText : '查询结果为空',
	autoScroll : true,
	collapsible : true,
	animCollapse : true,
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonStore');
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store : me.store,
			pageSize : 20,
			maximumSize : 50,
			plugins : Ext.create('Deppon.ux.PageSizePlugin', {
				sizeList : [['20', 20], ['30', 30], ['40', 40], ['50', 50]]
			})
		});
		platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonPagingBar = me.bbar;
		me.callParent([cfg]);
	},
	tbar : [{
		xtype : 'button',
		text : '导出',
		handler : function(){
			var queryForm = platform.goodsAreaDensityDetail.monQueryForm.getForm();
				queryParams = queryForm.getValues();
			if(!queryForm.isValid()){
				Ext.ux.Toast.msg('提示', '请输入导出条件！', 'error', 1500);
				return;
			}
			var orgName = queryForm.findField('outfieldCode').store.findRecord('code', queryParams.outfieldCode, false, true, true).get('name');
			var exportParams = {
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterName' : orgName,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticMonth' : queryParams.monDensityQueryStartTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticMonth' : queryParams.monDensityQueryEndTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType
			}; 
			if(!Ext.fly('downloadReturnRateFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadReturnRateFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
				url : platform.realPath('exportTfrCtrGoodsAreaAvgDensity4Monthly.action'),
				form: Ext.fly('downloadReturnRateFileForm'),
				method : 'POST',
				params : exportParams,
				isUpload: true,
				exception : function(response,opts) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('提示','导出失败',result.message);
					//myMask.hide();
				}	
			});
		}
	}],
	columns : [{
        xtype: 'rownumberer',
        text : '序号',
        align : 'center',
        width: 50,
        sortable: false
    },{
		dataIndex : 'transferCenterName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '外场'
	},{
		dataIndex : 'goodsAreaTypeName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区类型'
	}, {
		dataIndex : 'goodsAreaName',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区名称'
	},{
		dataIndex : 'goodsAreaVolume',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '库区容量(F)'
	},{
		dataIndex : 'goodsVolume',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月库区货量(F)'
	},{
		dataIndex : 'density',
		align : 'center',
		flex : 1,
		xtype : 'ellipsiscolumn',
		text : '当月库区密度',
		renderer : function(value){
			if(value != null){
				value = new String((value*100).toFixed(1));
				return value + '%';
			}else{
				return value;
			}
		}
	}]
});

//月库区密度查询，指定库区密度曲线图之Model
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurveModel', {
    extend: 'Ext.data.Model',
    fields : [{
		name : 'density',
		type : 'string',
		convert : function(value){
			return parseFloat(value);
		}
	},{
		name : 'statisticMonth',
		type : 'string'
	}]
});

//月库区密度查询，指定库区密度曲线图之store
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurveStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurveModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		// 请求的url
		url : platform.realPath('selectTfrCtrGoodsAreaAvgDensity4Monthly.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'goodsAreaDensityVo.goodsAreaDensityEntities',
			successProperty: 'success'
		}
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts) {
			var queryForm = platform.goodsAreaDensityDetail.monQueryForm.getForm();
			if (!Ext.isEmpty(queryForm)) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticMonth' : queryParams.monDensityQueryStartTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticMonth' : queryParams.monDensityQueryEndTime,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType,
						'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaCode' : queryParams.goodsAreaCode
					}
				});
			}
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//月库区密度查询，指定库区密度曲线图
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurveChart',{
    extend :  'Ext.chart.Chart',
    animate: false,
    store : Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurveStore'),
    width : 950,
    height : 400,
    insetPadding: 30,
    axes: [{
               type: 'Numeric',
               position: 'left',
               fields: ['density'],
               minimum: 0,
               grid: true,
               title : '库区密度',
               label: {
                   renderer: function(density) {
                	   return (density*100).toFixed(1) + '%';
                   }
               }
           },{
               type: 'Category',
               position: 'bottom',
               title : '月份',
               fields: ['statisticMonth']
           }
       ],
       series: [{
            type: 'line',
            xField: 'statisticMonth',
            axis: ['left','bottom'],
            yField: 'density',
            tips: {
                trackMouse: true,
                width: 100,
                height: 40,
                renderer: function(storeItem, item) {
                	this.setTitle(storeItem.get('statisticMonth'));
                	this.update((storeItem.get('density')*100).toFixed(1) + '%');
                }
            }
        }]
});

//月库区密度查询，指定库区密度曲线图面板
Ext.define('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurvePanel', {
	extend : 'Ext.panel.Panel',
	title : '库区密度信息',
	frame : true,
	collapsible : true,
	hidden : true,
	animCollapse : true,
	items : [Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurveChart')],
	tbar : [/*{
        text: '导出图片',
        handler: function(){
        	var chart = platform.goodsAreaDensityDetail.monResultCurveChartPanel.items.items[0];
        	if(chart.store.getCount() > 0){
        		chart.save({
                    type: 'image/png'
                });
        	}else{
        		Ext.ux.Toast.msg('提示', '折线图中没有数据！', 'error', 1500);
				return;
        	}
        }
    },*/{
    	text: '导出表格',
        handler: function(){
			var queryForm = platform.goodsAreaDensityDetail.monQueryForm.getForm();
				queryParams = queryForm.getValues();
			if(!queryForm.isValid()){
				Ext.ux.Toast.msg('提示', '请输入导出条件！', 'error', 1500);
				return;
			}
			var orgName = queryForm.findField('outfieldCode').store.findRecord('code', queryParams.outfieldCode, false, true, true).get('name');
			var exportParams = {
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterCode' : queryParams.outfieldCode,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.transferCenterName' : orgName,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.beginStatisticMonth' : queryParams.monDensityQueryStartTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.endStatisticMonth' : queryParams.monDensityQueryEndTime,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaTypeCode' : queryParams.goodsAreaType,
					'goodsAreaDensityVo.goodsAreaDensityQcDto.goodsAreaCode' : queryParams.goodsAreaCode
			}; 
			if(!Ext.fly('downloadReturnRateFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadReturnRateFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
			}
			
			Ext.Ajax.request({
				url : platform.realPath('exportTfrCtrGoodsAreaAvgDensity4Monthly.action'),
				form: Ext.fly('downloadReturnRateFileForm'),
				method : 'POST',
				params : exportParams,
				isUpload: true,
				exception : function(response,opts) {
					var result = Ext.decode(response.responseText);
					Ext.MessageBox.alert('提示','导出失败',result.message);
				}	
			});
		}
    }],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

//月库区密度查询form
platform.goodsAreaDensityDetail.monQueryForm = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonQueryForm');

//日库区密度查询结果Grid
platform.goodsAreaDensityDetail.monResultGrid = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonGrid');

//日库区密度查询结果Chart
platform.goodsAreaDensityDetail.monResultCurveChartPanel = Ext.create('Foss.platform.goodsAreaDensityDetail.goodsAreaDensityDetailMonCurvePanel');

//主页面
Ext.onReady(function() {
	Ext.create('Ext.panel.Panel',{
		id:'T_platform-goodsAreaDensityDetailIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout: 'auto',
		items : [{
			xtype : 'tabpanel',
			frame : false,
			bodyCls : 'autoHeight',
			cls : 'innerTabPanel',
			items : [{
				title : '日库区密度',
				tabConfig : {
					width : 120
				},
				items : [platform.goodsAreaDensityDetail.dayQueryForm,
				         platform.goodsAreaDensityDetail.dayResultGrid,
				         platform.goodsAreaDensityDetail.dayResultCurveChartPanel]
			},{
				title : '月库区密度',
				tabConfig : {
					width : 120
				},
				items : [platform.goodsAreaDensityDetail.monQueryForm,
				         platform.goodsAreaDensityDetail.monResultGrid,
				         platform.goodsAreaDensityDetail.monResultCurveChartPanel]
			}]
		}],
		renderTo: 'T_platform-goodsAreaDensityDetailIndex-body'
	});
	
	var dayForm = platform.goodsAreaDensityDetail.dayQueryForm.getForm();
	
	//给两个“外场”控件赋值
	dayForm.findField('outfieldCode').setCombValue(
			platform.goodsAreaDensity.mainPageOrgName,
			platform.goodsAreaDensity.mainPageOrgCode
	);
	platform.goodsAreaDensityDetail.monQueryForm.getForm().findField('outfieldCode').setCombValue(
			platform.goodsAreaDensity.mainPageOrgName,
			platform.goodsAreaDensity.mainPageOrgCode
	);

	//给月tab里的月份控件赋值
	platform.goodsAreaDensityDetail.monQueryForm.getForm().findField('monDensityQueryStartTime').setValue(new Date());
	platform.goodsAreaDensityDetail.monQueryForm.getForm().findField('monDensityQueryEndTime').setValue(new Date());
	
});