/**
 * 查询库区密度走势图界面分两个页签：日库区密度走势图、月库区密度走势图
 */
/**
 * 密度走势查询结果model
 */
Ext.define('Foss.platform.densityTrend.model', {
	extend: 'Ext.data.Model',
	fields: [{//待叉区密度
		name : 'densityOfFork',
		type:'float'
	},{//长途库区密度
		name : 'densityOfLong',
		type:'float'
	},{//短途库区密度
		name : 'densityOfShort',
		type:'float'
	},{//派送库区密度
		name : 'densityOfDpt',
		type:'float'
	},{//转运场密度
		name : 'densityOfTfr',
		type:'float'
	},{//统计日期
		name : 'staDate',
		type : 'date'
	},{//时间点
		name:'staHour',
		type:'int'
	}]
});

/**
 * 日密度走势查询结果store
 */
Ext.define('Foss.platform.densityTrend.storeByDay', {
	extend:'Ext.data.Store',
    model: 'Foss.platform.densityTrend.model'
//    data:[{
//    	'staHour':1,
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':2,
//    	'densityOfTfr':0.3356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':3,
//    	'densityOfTfr':0.7356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':4,
//    	'densityOfTfr':0.4356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':5,
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':6,
//    	'densityOfTfr':0.8356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':14,
//    	'densityOfTfr':1.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':7,
//    	'densityOfTfr':0.6356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':8,
//    	'densityOfTfr':0.3356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':9,
//    	'densityOfTfr':0.8356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':10,
//    	'densityOfTfr':0.3356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':11,
//    	'densityOfTfr':0.8356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':12,
//    	'densityOfTfr':0.6356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staHour':13,
//    	'densityOfTfr':0.7356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    }]
});
/**
 * 月密度走势查询结果store
 */
Ext.define('Foss.platform.densityTrend.storeByMonth', {
	extend:'Ext.data.Store',
    model: 'Foss.platform.densityTrend.model'
//    data:[{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -13),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -12),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -11),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -10),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -9),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -8),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -7),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -6),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -5),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -4),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -3),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -2),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':Ext.Date.add(new Date(), Ext.Date.DAY, -1),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    },{
//    	'staDate':new Date(),
//    	'densityOfTfr':0.2356,
//    	'densityOfDpt':0.4582,
//    	'densityOfShort':0.859,
//    	'densityOfLong':0.9825,
//    	'densityOfFork':1.2598
//    }]
});
//日库区密度查询条件
Ext.define('Foss.platform.densityTrend.formOfDay', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.businessDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityTrend.initDeptInfo(_this,'HQCODE');
			}
		}
	},{
		name : 'queryCondition.tfrCtrCode',
		fieldLabel : '外场',
		allowBlank:false,
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityTrend.initDeptInfo(_this,'OUTFIELDCODE');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'日期',
    	allowBlank:false,
		name:'queryCondition.staDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m-d',
		columnWidth:.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		items:[{
	        xtype: 'checkboxgroup',
	        fieldLabel: '',
	        columnWidth : 1,
	        layout : 'column',
	        name:'queryCondition.goodsAreaTypeGroup',
	    	defaults : {
	    		columns : 5,
	    		margin : '5 5 5 5'
	    	},
	        items: [{ 
	        	boxLabel: '待叉区', 
	        	name: 'goodsAreaType', 
	        	columnWidth : .15,
	        	inputValue: 'fork' 
	        },{ 
	        	boxLabel: '长途货区', 
	        	name: 'goodsAreaType', 
	        	inputValue: 'long', 
	        	columnWidth : .15
	        },{ 
	        	boxLabel: '短途货区', 
	        	columnWidth : .15,
	        	name: 'goodsAreaType', 
	        	inputValue: 'short' 
	        },{ 
	        	boxLabel: '派送库区', 
	        	columnWidth : .15,
	        	name: 'goodsAreaType', 
	        	inputValue: 'dpt' 
	        },{ 
	        	boxLabel: '转运场', 
	        	columnWidth : .15,
	        	name: 'goodsAreaType', 
	        	inputValue: 'tfr' 
	        }]
	    }]
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				//重置查询条件form
				form.reset();
				//获得查询条件的外场
				var tfrCtrCode = form.findField('queryCondition.tfrCtrCode');
				//初始化外场信息
				platform.densityTrend.initDeptInfo(tfrCtrCode,'OUTFIELDCODE');
				//获得查询条件的经营本部
				var businessDeptCode = form.findField('queryCondition.businessDeptCode');
				//初始化经营本部信息
				platform.densityTrend.initDeptInfo(businessDeptCode,'HQCODE');
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function(th) {
				
//				获得查询条件form
				var queryForm = th.up('form').getForm();
				if (!queryForm.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				//获取查询参数
				var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
				var staDate = queryForm.findField('queryCondition.staDate').getValue();
				//密度类型数组
				var goodsAreaType = queryForm.findField('queryCondition.goodsAreaTypeGroup').getValue().goodsAreaType;
				if (Ext.isEmpty(goodsAreaType)) {
					Ext.Msg.alert('提示','五种库区，至少选择一种!');
					return false;
				}
				var flagFork = 0;
				var flagLong = 0;
				var flagShort = 0;
				var flagDpt = 0;
				var flagTfr = 0;
				if(!Ext.isEmpty(goodsAreaType)){
					if(Ext.Array.contains(goodsAreaType,'fork')){
						flagFork = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'long')){
						flagLong = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'short')){
						flagShort = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'dpt')){
						flagDpt = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'tfr')){
						flagTfr = 1;
					}
				}
				//获取页面中的折线图
				var chart = th.up('form').up('panel').down('chart');
				
				//判断折线图中的哪些折线需要显示
				if(flagFork === 0){
					//隐藏无需展示的折线图
					chart.series.items[0].hideAll();
				}else{
					chart.series.items[0].showAll();
				}
				if(flagLong === 0){
					//隐藏无需展示的折线图
					chart.series.items[1].hideAll();
				}else{
					chart.series.items[1].showAll();
				}
				if(flagShort === 0){
					//隐藏无需展示的折线图
					chart.series.items[2].hideAll();
				}else{
					chart.series.items[2].showAll();
				}
				if(flagDpt === 0){
					//隐藏无需展示的折线图
					chart.series.items[3].hideAll();
				}else{
					chart.series.items[3].showAll();
				}
				if(flagTfr === 0){
					//隐藏无需展示的折线图
					chart.series.items[4].hideAll();
				}else{
					chart.series.items[4].showAll();
				}
				
				var params = {
					'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
					'densityVo.densityQcDto.flagFork':flagFork,
					'densityVo.densityQcDto.flagLong':flagLong,
					'densityVo.densityQcDto.flagShort':flagShort,
					'densityVo.densityQcDto.flagDpt':flagDpt,
					'densityVo.densityQcDto.flagTfr':flagTfr,
					'densityVo.densityQcDto.staDate':staDate
				};
				//加载数据
//				th.up('form').up('panel').down('chart').store.load();
				//为密度趋势折线图加载数据
				Ext.Ajax.request({
					url:platform.realPath('findTrendDay.action'),
					params:params,
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(result.success){
							//密度信息列表
							var trendDtos = result.densityVo.trendDtos;
							//密度信息数组
							var densityArray = new Array();
							//将后端返回列表中的空值置为零
							if(!Ext.isEmpty(trendDtos) && trendDtos.length > 0){
								for(var i = 0;i < trendDtos.length;i++){
									if(Ext.isEmpty(trendDtos[i].densityOfFork)){
										trendDtos[i].densityOfFork = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfLong)){
										trendDtos[i].densityOfLong = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfShort)){
										trendDtos[i].densityOfShort = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfDpt)){
										trendDtos[i].densityOfDpt = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfTfr)){
										trendDtos[i].densityOfTfr = 0;
									}
									//设置统计时间
									trendDtos[i].staDate = staDate;
									densityArray.push(trendDtos[i].densityOfFork,trendDtos[i].densityOfLong,
											trendDtos[i].densityOfShort,trendDtos[i].densityOfDpt,trendDtos[i].densityOfTfr);
								}
								//查找所有密度信息中的最大值
								var max = platform.densityTrend.findMaxValue(densityArray);
								//设置图表中纵坐标的最大值
								chart.axes.map.left.maximum = max;
								//在线条的数据点上显示标记
								for(var i = 0;i < 5;i++){
									chart.series.items[i].showMarkers = true;
								}
								chart.store.removeAll();
								//加载数据
								chart.store.loadData(trendDtos);
							}
							else{
								Ext.ux.Toast.msg('提示', '查询结果为空！', 'error', 2000);
								return;
							}
							
						}else{
							if(Ext.isEmpty(result)){
								Ext.Msg.alert('查询失败！');
							}
							else{
								Ext.Msg.alert(result.message);
							}
						}
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							Ext.Msg.alert('查询失败！');
						}
						else{
							Ext.Msg.alert(result.message);
						}
					}
				});
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//月库区密度查询条件
Ext.define('Foss.platform.densityTrend.formOfMonth', {
	extend : 'Ext.form.Panel',
	title : '查询条件',
	frame : true,
	layout : 'column',
	defaults : {
		columns : 4,
		margin : '5 5 5 5'
	},
	items : [{
		name : 'queryCondition.businessDeptCode',
		fieldLabel : '经营本部',
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityTrend.initDeptInfo(_this,'HQCODE');
			}
		}
	},{
		name : 'queryCondition.tfrCtrCode',
		fieldLabel : '外场',
		allowBlank:false,
		columnWidth : 0.25,
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter : 'Y',
		listeners : {
			beforerender : function(_this, eOpts ){
				//初始化外场信息
				platform.densityTrend.initDeptInfo(_this,'OUTFIELDCODE');
			}
		}
	},{
		xtype:'datefield',
    	fieldLabel:'月份',
    	allowBlank:false,
		name:'queryCondition.staDate',
		editable:false,
		value: Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		maxValue:Ext.Date.add(login.currentServerTime, Ext.Date.DAY, -1),
		format:'Y-m',
		columnWidth:.25
	},{
		xtype : 'container',
		columnWidth : .25,
		html : '&nbsp;'
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		layout : 'column',
		items:[{
	        xtype: 'checkboxgroup',
	        fieldLabel: '',
	        name:'queryCondition.goodsAreaTypeGroup',
	        columnWidth : 1,
	        layout : 'column',
	    	defaults : {
	    		columns : 5,
	    		margin : '5 5 5 5'
	    	},
	        items: [{ 
	        	boxLabel: '待叉区', 
	        	name: 'goodsAreaType', 
	        	columnWidth : .15,
	        	inputValue: 'fork' 
	        },{ 
	        	boxLabel: '长途货区', 
	        	name: 'goodsAreaType', 
	        	inputValue: 'long', 
	        	columnWidth : .15
	        },{ 
	        	boxLabel: '短途货区', 
	        	columnWidth : .15,
	        	name: 'goodsAreaType', 
	        	inputValue: 'short' 
	        },{ 
	        	boxLabel: '派送库区', 
	        	columnWidth : .15,
	        	name: 'goodsAreaType', 
	        	inputValue: 'dpt' 
	        },{ 
	        	boxLabel: '转运场', 
	        	columnWidth : .15,
	        	name: 'goodsAreaType', 
	        	inputValue: 'tfr' 
	        }]
	    }]
	},{
		border : 1,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout : 'column',
		items : [{
			text : '重置',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var form = th.up('form').getForm();
				//重置查询条件form
				form.reset();
				//获得查询条件的外场
				var tfrCtrCode = form.findField('queryCondition.tfrCtrCode');
				//初始化外场信息
				platform.densityTrend.initDeptInfo(tfrCtrCode,'OUTFIELDCODE');
				//获得查询条件的经营本部
				var businessDeptCode = form.findField('queryCondition.businessDeptCode');
				//初始化经营本部信息
				platform.densityTrend.initDeptInfo(businessDeptCode,'HQCODE');
			}
		},{
			xtype : 'container',
			columnWidth : .80,
			html : '&nbsp;'
		},{
			text : '查询',
			cls : 'yellow_button',
			columnWidth : .08,
			handler : function(th) {
				//获得查询条件form
				var queryForm = th.up('form').getForm();
				if (!queryForm.isValid()) {
					Ext.Msg.alert('提示','查询失败，请输入有效条件!');
					return false;
				}
				//获取查询参数
				var tfrCtrCode = queryForm.findField('queryCondition.tfrCtrCode').getValue();
				var staDate = queryForm.findField('queryCondition.staDate').getValue();
				//查询月份
				var staMonth = new Number(Ext.Date.format(staDate,'Ym').toString())
				//密度类型数组
				var goodsAreaType = queryForm.findField('queryCondition.goodsAreaTypeGroup').getValue().goodsAreaType;
				if (Ext.isEmpty(goodsAreaType)) {
					Ext.Msg.alert('提示','五种库区，至少选择一种!');
					return false;
				}
				var flagFork = 0;
				var flagLong = 0;
				var flagShort = 0;
				var flagDpt = 0;
				var flagTfr = 0;
				if(!Ext.isEmpty(goodsAreaType)){
					if(Ext.Array.contains(goodsAreaType,'fork')){
						flagFork = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'long')){
						flagLong = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'short')){
						flagShort = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'dpt')){
						flagDpt = 1;
					}
					if(Ext.Array.contains(goodsAreaType,'tfr')){
						flagTfr = 1;
					}
				}
				//获取页面中的折线图
				var chart = th.up('form').up('panel').down('chart');
				
				//判断折线图中的哪些折线需要显示
				if(flagFork === 0){
					//隐藏无需展示的折线图
					chart.series.items[0].hideAll();
				}else{
					chart.series.items[0].showAll();
				}
				if(flagLong === 0){
					//隐藏无需展示的折线图
					chart.series.items[1].hideAll();
				}else{
					chart.series.items[1].showAll();
				}
				if(flagShort === 0){
					//隐藏无需展示的折线图
					chart.series.items[2].hideAll();
				}else{
					chart.series.items[2].showAll();
				}
				if(flagDpt === 0){
					//隐藏无需展示的折线图
					chart.series.items[3].hideAll();
				}else{
					chart.series.items[3].showAll();
				}
				if(flagTfr === 0){
					//隐藏无需展示的折线图
					chart.series.items[4].hideAll();
				}else{
					chart.series.items[4].showAll();
				}
				var params = {
					'densityVo.densityQcDto.tfrCtrCode':tfrCtrCode,
					'densityVo.densityQcDto.flagFork':flagFork,
					'densityVo.densityQcDto.flagLong':flagLong,
					'densityVo.densityQcDto.flagShort':flagShort,
					'densityVo.densityQcDto.flagDpt':flagDpt,
					'densityVo.densityQcDto.flagTfr':flagTfr,
					'densityVo.densityQcDto.staMonth':staMonth
				};
				//为密度趋势折线图加载数据
				Ext.Ajax.request({
					url:platform.realPath('findTrendMonth.action'),
					params:params,
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(result.success){
							//密度信息列表
							var trendDtos = result.densityVo.trendDtos;
							//密度信息数组
							var densityArray = new Array();
							//将后端返回列表中的空值置为零
							if(!Ext.isEmpty(trendDtos) && trendDtos.length > 0){
								for(var i = 0;i < trendDtos.length;i++){
									if(Ext.isEmpty(trendDtos[i].densityOfFork)){
										trendDtos[i].densityOfFork = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfLong)){
										trendDtos[i].densityOfLong = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfShort)){
										trendDtos[i].densityOfShort = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfDpt)){
										trendDtos[i].densityOfDpt = 0;
									}
									if(Ext.isEmpty(trendDtos[i].densityOfTfr)){
										trendDtos[i].densityOfTfr = 0;
									}
									trendDtos[i].staDate = Ext.Date.format(new Date(trendDtos[i].staDate),'Y-m-d');
									densityArray.push(trendDtos[i].densityOfFork,trendDtos[i].densityOfLong,
											trendDtos[i].densityOfShort,trendDtos[i].densityOfDpt,trendDtos[i].densityOfTfr);
								}
								//查找所有密度信息中的最大值
								var max = platform.densityTrend.findMaxValue(densityArray);
								//设置图表中纵坐标的最大值
								chart.axes.map.left.maximum = max;
								//在线条的数据点上显示标记
								for(var i = 0;i < 5;i++){
									chart.series.items[i].showMarkers = true;
								}
								chart.store.removeAll();
								chart.store.loadData(trendDtos);
							}
							else{
								Ext.ux.Toast.msg('提示', '查询结果为空！', 'error', 2000);
								return;
							}
							
						}else{
							if(Ext.isEmpty(result)){
								Ext.Msg.alert('查询失败！');
							}
							else{
								Ext.Msg.alert(result.message);
							}
						}
					},
					failure:function(response){
						var result = Ext.decode(response.responseText);
						if(Ext.isEmpty(result)){
							Ext.Msg.alert('查询失败！');
						}
						else{
							Ext.Msg.alert(result.message);
						}
					}
				});
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 日密度走势图
 */
Ext.define('Foss.platform.densityTrend.chartOfDay', {
	extend:'Ext.chart.Chart',
    animate: true,
    style: 'background:#fff',
    shadow: true,
    legend: {
        position: 'right'
    },
	initComponent:function(){
		var me = this;
		me.store = Ext.create('Foss.platform.densityTrend.storeByDay');
		me.axes = [{
	        type: 'Numeric',
	        minorTickSteps: 1,
	        position: 'left',
	        fields: ['densityOfFork','densityOfLong','densityOfShort','densityOfDpt','densityOfTfr'],
	        width:150,
	        label: {
	            renderer: Ext.util.Format.numberRenderer('00.00')
	        },
	        grid:{
	        	odd:{
	        		opacity: 1,
                    fill: '#ddd',
                    stroke: '#bbb',
                    'stroke-width': 0.5
	        	}
	        },
	        minimum: 0,
	        title:'库区密度'
	    },{
	        type: 'Category',
	        position: 'bottom',
	        grid: true,
	        label: {
	            renderer: function(val){
	            	return ' '+val+'点';
	            },
	            display:'rotate',
	            margin:'0 0 0 5'
	        },
	        fields: ['staHour'],
	        title:'时间点'
	    }];
		me.series = [{
	        type: 'line',
	        smooth:true,
	        title:'待叉区',
//	        showInLegend:true,
	        style: {
	            stroke: '#fbb404'
	        },
	        highlight: {
                size: 7,
                radius: 7
            },
	        axis: 'left',
	        xField: 'staHour',
	        yField: 'densityOfFork',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
	    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
	    	    		+storeItem.get('staHour')+'点<br/>待叉区密度：'
	    	    		+(storeItem.get('densityOfFork')*100).toFixed(2)+'%');
	    	  }
	    	},
	    	markerConfig: {
	    		type: 'cross',
	    		size: 4,
	    		radius: 4,
	    		'stroke-width': 0,
	    		'fill': '#fbb404'
	    	}
	    },{
	        type: 'line',
	        title:'长途货区',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#414068'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staHour',
	        yField: 'densityOfLong',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>长途货区密度：'
		    	    		+(storeItem.get('densityOfLong')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'circle',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#414068'
	        }
	    },{
	        type: 'line',
	        title:'短途货区',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#00ff00'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staHour',
	        yField: 'densityOfShort',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>短途货区密度：'
		    	    		+(storeItem.get('densityOfShort')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'circle',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#00ff00'
	        }
	    },{
	        type: 'line',
	        title:'派送库区',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#ff0000'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staHour',
	        yField: 'densityOfDpt',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>派送库区密度：'
		    	    		+(storeItem.get('densityOfDpt')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'cross',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#ff0000'
	        }
	    },{
	        type: 'line',
	        title:'转运场',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#0000ff'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staHour',
	        yField: 'densityOfTfr',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>转运场密度：'
		    	    		+(storeItem.get('densityOfTfr')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'cross',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#0000ff'
	        }
	    }];
		this.callParent();
	}
});
/**
 * 月密度走势图
 */
Ext.define('Foss.platform.densityTrend.chartOfMonth', {
	extend:'Ext.chart.Chart',
    animate: true,
    style: 'background:#fff',
    shadow: true,
    legend: {
        position: 'right'
    },
	initComponent:function(){
		var me = this;
		me.store = Ext.create('Foss.platform.densityTrend.storeByMonth');
		me.axes = [{
	        type: 'Numeric',
	        minorTickSteps: 1,
	        position: 'left',
	        fields: ['densityOfFork','densityOfLong','densityOfShort','densityOfDpt','densityOfTfr'],
	        width:150,
	        label: {
	            renderer: Ext.util.Format.numberRenderer('00.00')
	        },
	        grid:{
	        	odd:{
	        		opacity: 1,
                    fill: '#ddd',
                    stroke: '#bbb',
                    'stroke-width': 0.5
	        	}
	        },
	        minimum: 0,
	        title:'库区密度'
	    },{
	        type: 'Category',
	        position: 'bottom',
	        grid: true,
	        label: {
	            renderer: function(val){
	            	return Ext.Date.format(val,'Y-m-d');
	            },
	            display:'rotate',
	            margin:'0 0 0 5'
	        },
	        fields: ['staDate'],
	        title:'日期'
	    }];
		me.series = [{
	        type: 'line',
	        smooth:true,
	        title:'待叉区',
	        showInLegend:true,
	        style: {
	            stroke: '#fbb404'
	        },
	        highlight: {
                size: 7,
                radius: 7
            },
	        axis: 'left',
	        xField: 'staDate',
	        yField: 'densityOfFork',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
	    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
	    	    		+storeItem.get('staHour')+'点<br/>待叉区密度：'
	    	    		+(storeItem.get('densityOfFork')*100).toFixed(2)+'%');
	    	  }
	    	},
	    	markerConfig: {
	    		type: 'cross',
	    		size: 4,
	    		radius: 4,
	    		'stroke-width': 0,
	    		'fill': '#fbb404'
	    	}
	    },{
	        type: 'line',
	        title:'长途货区',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#414068'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staDate',
	        yField: 'densityOfLong',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>长途货区密度：'
		    	    		+(storeItem.get('densityOfLong')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'circle',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#414068'
	        }
	    },{
	        type: 'line',
	        title:'短途货区',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#00ff00'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staDate',
	        yField: 'densityOfShort',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>短途货区密度：'
		    	    		+(storeItem.get('densityOfShort')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'circle',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#00ff00'
	        }
	    },{
	        type: 'line',
	        title:'派送库区',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#ff0000'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staDate',
	        yField: 'densityOfDpt',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>派送库区密度：'
		    	    		+(storeItem.get('densityOfDpt')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'cross',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#ff0000'
	        }
	    },{
	        type: 'line',
	        title:'转运场',
	        showInLegend:true,
	        smooth:true,
	        style: {
	            stroke: '#0000ff'
	        },
	        highlight: {
	            size: 7,
	            radius: 7
	        },
	        axis: 'left',
	        xField: 'staDate',
	        yField: 'densityOfTfr',
	        tips:{
	    	  trackMouse: true,
	    	  width: 200,
	    	  height: 60,
	    	  renderer: function(storeItem, item) {
		    	    this.setTitle('日期：'+Ext.Date.format(storeItem.get('staDate'),'Y-m-d')+'<br/>时间点：'
		    	    		+storeItem.get('staHour')+'点<br/>转运场密度：'
		    	    		+(storeItem.get('densityOfTfr')*100).toFixed(2)+'%');
	    	  }
	    	},
	        markerConfig: {
	            type: 'cross',
	            size: 4,
	            radius: 4,
	            'stroke-width': 0,
	            'fill':'#0000ff'
	        }
	    }];
		this.callParent();
	}
});
//日密度走势图面板
Ext.define('Foss.platform.densityTrend.PanelCharOfDay', {
	extend : 'Ext.form.Panel',
	frame : true,
	layout : 'fit',
	title:'日密度走势图',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//日密度走势图查询结果折线图
		var chart = Ext.create('Foss.platform.densityTrend.chartOfDay',{
			height:400
		});
		me.items = [chart];
		me.callParent([cfg]);
	}
});

//月密度走势图面板
Ext.define('Foss.platform.densityTrend.PanelCharOfMonth', {
	extend : 'Ext.form.Panel',
	frame : true,
	layout : 'fit',
	title:'月密度走势图',
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		//日密度走势图查询结果折线图
		var chart = Ext.create('Foss.platform.densityTrend.chartOfMonth',{
			height:400
		});
		me.items = [chart];
		me.callParent([cfg]);
	}
});
//填充外场与经营本部信息并设置外场combobox的readOnly属性
platform.densityTrend.initDeptInfo = function(item,desc){
	//如果是外场需要被初始化
	if('OUTFIELDCODE' === desc){
		//初始化外场信息
		if(!Ext.isEmpty(platform.densityTrend.outfieldCode)){
			//外场初始化
			item.setCombValue(
				platform.densityTrend.outfield,
				platform.densityTrend.outfieldCode
			);
			item.readOnly = true;
		}
	}else if('HQCODE' === desc){//如果是经营本部需要被初始化
		if(!Ext.isEmpty(platform.densityTrend.outfieldCode)){
			//设置经营本部只读
			item.readOnly = true;
		}else if(Ext.isEmpty(platform.densityTrend.outfieldCode) && !Ext.isEmpty(platform.densityTrend.hqCode)){
			//经营本部初始化
			item.setCombValue(
				platform.densityTrend.hqName,
				platform.densityTrend.hqCode
			);
			//设置经营本部只读
			item.readOnly = true;
		}
	}
}
//从array中查找后返回最大值
platform.densityTrend.findMaxValue = function(values){
	var max = 0;
	for(var i = 0;i < values.length;i++){
		if(values[i] > max){
			max = values[i];
		}
	}
	if(max == 0){
		max = 1;
	}
	return max;
}
//日密度走势图查询条件form
platform.densityTrend.formOfDay = Ext.create('Foss.platform.densityTrend.formOfDay');
//日密度走势图查询结果折线图
platform.densityTrend.chartOfDay = Ext.create('Foss.platform.densityTrend.PanelCharOfDay');
//月密度走势图查询条件form
platform.densityTrend.formOfMonth = Ext.create('Foss.platform.densityTrend.formOfMonth');
//月密度走势图查询结果折线图
platform.densityTrend.chartOfMonth = Ext.create('Foss.platform.densityTrend.PanelCharOfMonth');
/** -----------------------------------------------页面显示view--------------------------------------------------* */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//日密度走势图页签页面
	Ext.define('Foss.platform.densityTrend.PanelOfDay', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//待叉区查询条件form
			var form = platform.densityTrend.formOfDay;
			//日密度走势图查询结果折线图
			var chart = platform.densityTrend.chartOfDay;
			me.items = [form, chart];
			me.callParent([cfg]);
		}
	});
	//月密度走势图页签页面
	Ext.define('Foss.platform.densityTrend.PanelOfMonth', {
		extend : 'Ext.panel.Panel',
		frame : false,
		layout : 'auto',
		constructor : function(config) {
			var me = this;
			var cfg = Ext.apply({}, config);
			//驻地派送库区查询条件form
			var form = platform.densityTrend.formOfMonth;
			//月密度走势图查询结果折线图
			var chart = platform.densityTrend.chartOfMonth;
			me.items = [form, chart];
			me.callParent([cfg]);
		}
	});
	/**
	 * 查询库区密度走势图界面两个页签panel
	 */
	Ext.define('Foss.platform.densityTrend.MainTabPanel', {
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
			title : '日密度走势图',
			items : Ext.create('Foss.platform.densityTrend.PanelOfDay')
		}, {
			tabConfig : {
				width : 100
			},
			title : '月密度走势图',
			items : Ext.create('Foss.platform.densityTrend.PanelOfMonth')
		}]
	});
	platform.densityTrend.MainTabPanel = Ext.create('Foss.platform.densityTrend.MainTabPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_platform-densityTrend_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContent-body',
		items : [platform.densityTrend.MainTabPanel],
		renderTo : 'T_platform-densityTrend-body'
	});
//	platform.densityTrend.chartOfDay.down('chart').axes.map.left.maximum = 1;
});