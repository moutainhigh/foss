
/********************************************************************************************************************
 *查询库存界面：
 *	库存查询条件表单：Foss.stock.stockmanage.QueryForm	
 *	运单库存表格：Foss.stock.stockmanage.WaybillGrid
 *	货件库存 Grid （第二层表格）：Foss.stock.stockmanage.GoodsGrid
 *
 *单件入库界面：
 *	入库窗口：Foss.stock.stockmanage.InStockWindow	
 *     入库表单：Foss.stock.stockmanage.InStockForm
 *     流水号表格：Foss.stock.stockmanage.GoodsSerialNOGrid  
 * ******************************************************************************************************************/
//运输性质
Ext.define('Foss.stock.stockmanage.ProductStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: stock.realPath('queryProductList.action'),
		reader : {
			type : 'json',
			root : 'stockVO.productList'
		}
    }
});

//驻地派送部货区
Ext.define('Foss.stock.stockmanage.AreaByOrgcodeStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: stock.realPath('areaByOrgcodeList.action'),
		reader : {
			type : 'json',
			root : 'stockVO.areaByOrgcodeList'
		}
    }
});

//驻地派送部货区(主页面,2个库区,一个是零担一个是快递)
Ext.define('Foss.stock.stockmanage.AreaByStationcodeStore',{
	extend:'Ext.data.Store',
	autoLoad: true,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: stock.realPath('stationAreaByOrgcodeList.action'),
		reader : {
			type : 'json',
			root : 'stockVO.areaByOrgcodeList'
		}
    }
});


//货区对应的库位
Ext.define('Foss.stock.stockmanage.PositionStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
        type : 'ajax',
        actionMethods:'POST',
        url: stock.realPath('queryPositionList.action'),
		reader : {
			type : 'json',
			root : 'stockVO.positionList'
		}
    }
});

//提货方式
Ext.define('Foss.stock.stockmanage.ReceiveMethodStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	data:[
	    {"valueName": "全部", "valueCode": ""},
	    {"valueName": "自提", "valueCode": "PICKUP"},
	    {"valueName": "派送", "valueCode": "DELIVER"},
	]
});


//库存查询条件表单
Ext.define('Foss.stock.stockmanage.QueryForm',{
	extend: 'Ext.form.Panel',
	id:'Foss_stock_stockmanage_QueryForm_ID',
	layout: 'column',
	frame: true,
	border: false,
	//title : '查询库存',
	title : stock.stockmanage.i18n('foss.stock.searchstock'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		//fieldLabel: '部门',
		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgCodeName',
		readOnly: true, 
		columnWidth:.3
	},{
		xtype: 'textfield',
		fieldLabel: '部门编号',
		name: 'orgCode',
		hidden: true
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'textfield',
		//fieldLabel: '运单号',
		fieldLabel: stock.stockmanage.i18n('foss.stock.waybill'),
		name: 'waybillNO',
		vtype: 'waybill',
		columnWidth:.3
	},{
		xtype: 'container',
		columnWidth:.05,
		html: '&nbsp;'
	},{
		xtype: 'combo',
		//fieldLabel: '运输性质',
		fieldLabel: stock.stockmanage.i18n('foss.stock.product'),
		name: 'productCode',
		displayField: 'valueName',
		valueField:'valueCode', 
		value: 'ALL',
		columnWidth:.3,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.stockmanage.ProductStore')
	},{
		xtype: 'combo',
		fieldLabel: '提货方式',
		//fieldLabel: stock.stockmanage.i18n('foss.stock.product'),
		name: 'receiveMethod',
		displayField: 'valueName',
		valueField:'valueCode', 
		columnWidth:.3,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.stockmanage.ReceiveMethodStore')
	},{
		xtype: 'rangeDateField',
		//fieldLabel: '入库时间',
		fieldLabel: stock.stockmanage.i18n('foss.stock.instocktime'),
		fieldId: 'Foss_stock_QueryForm_InstockTime_ID',
		dateType: 'datetimefield_date97',
		fromName: 'beginInStockTime',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-5,
										'00', '00'), 'Y-m-d H:i:s'),
		toName: 'endInStockTime',
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'),
		disallowBlank: true,
		allowBlank: false,
		//blankText:'字段不能为空'
		blankText:stock.stockmanage.i18n('foss.stock.notnull'),
		columnWidth: .5
	},{
		xtype: 'container',
		columnWidth:.5,
		html: '&nbsp;'
	},{
		border: 1,
		xtype:'fieldset',
		columnWidth:1,
		//title: '货区',
		title : stock.stockmanage.i18n('foss.stock.goodsarea'),
		layout:'column',
		hidden: false,
		items:[
		{
			xtype: 'commongoodsareaselector',
			//fieldLabel: '货区',
			name: 'goodsAreaCode',
			columnWidth:.15
		},{
			xtype: 'combo',
			//fieldLabel: '货区',
			name: 'goodsAreaCodeForStation',
			columnWidth:.15,
			hidden:true,
			displayField: 'valueName',
			valueField:'valueCode', 
			value: '',
			columnWidth:.15,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			store:Ext.create('Foss.stock.stockmanage.AreaByStationcodeStore'),
			listeners : {
			      afterRender : function(combo) {
			    	  if('Y' == stock.stockmanage.isStationDelivery ){//|| 'Y' == stock.stockmanage.isAirDispatch){
			    		//如果获取驻地派送部对应的库区code失败,则页面抛错
						if(stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.length == 0){
							Ext.MessageBox.alert('提示','加载库区失败！');
							stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').setValue('加载库区失败');
							return;
						}
			    		combo.setValue(stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.items[0].data.valueCode);
			    	  }
			      }
			   }
			
		},{
			xtype: 'container',
			columnWidth:.05,
			html: '&nbsp;'
		},{             
	        xtype: 'checkboxgroup',  //复选框组
	        fieldLabel : '', //复选框组的字段标签
	        columns: 3, //列来存放选择框项
			vertical: true, //按照columns中指定的列数来分配复选框组件
			columnWidth:.25,
			style: { marginTop: '5px' },
	        items: [{ 
						//boxLabel  : '散货', 
						boxLabel  : stock.stockmanage.i18n('foss.stock.bulkgoodsarea'),
						name      : 'otherGoodsAreaGode',
						inputValue: 'BULK_GOODS_AREA'
						//checked   : true //选中
					},{ 
						//boxLabel  : '整车', 
						boxLabel  : stock.stockmanage.i18n('foss.stock.wholegoodsarea'),
						name      : 'otherGoodsAreaGode',
						inputValue: 'WHOLE_GOODS_AREA'
						//checked   : true //选中
					},{ 
						boxLabel  : '库位', 
						disabled: !stock.stockmanage.isPermission('stock/positionButton'),
						hidden: !stock.stockmanage.isPermission('stock/positionButton'),
						name      : 'positionSelectGoodsArea',
						inputValue: 'POSITION_GOODS_AREA'
						//checked   : true //选中
					}],
		listeners: {
			change : function (newValue,oldValue,eOpts) {
				var tempValue = eOpts.otherGoodsAreaGode;
				for (var i = 0; i < newValue.items.length-1; i++)    
					{    
						if (newValue.items.items[i].checked && newValue.items.items[i].name=='otherGoodsAreaGode')    
						{    
							if(eOpts.otherGoodsAreaGode){
								//newValue.reset();
								if(newValue.items.items[i].inputValue == tempValue){
									newValue.items.items[i].setValue(false);
								}else{
									newValue.items.items[i].setValue(true);
								}
							}
						}    
					}
			}  
				}
	    }]
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			//text: '重置',
			text: stock.stockmanage.i18n('foss.stock.reset'),
			columnWidth:.08,
			handler: function(){
				stock.stockmanage.queryform.getForm().reset();
				stock.stockmanage.queryform.getForm().findField('beginInStockTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate()-5,
										'00', '00'), 'Y-m-d H:i:s'));
				stock.stockmanage.queryform.getForm().findField('endInStockTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),
										new Date().getMonth(), new Date().getDate(),
										'23', '59', '59'), 'Y-m-d H:i:s'));
				stock.stockmanage.queryform.getForm().findField('orgCode').setValue(stock.stockmanage.orgCode);
				stock.stockmanage.queryform.getForm().findField('orgCodeName').setValue(stock.stockmanage.orgName);
				
				//驻地派送部 或 空运总调  -->取消掉了驻地派送部
				if('Y' == stock.stockmanage.isAirDispatch){
					var goodsAreaSelector = stock.stockmanage.queryform.getForm().findField('goodsAreaCode');
					goodsAreaSelector.setValue(stock.stockmanage.goodsAreaName);
					goodsAreaSelector.getStore().load({params:{'goodsAreaVo.goodsAreaEntity.goodsAreaName' : stock.stockmanage.goodsAreaName}});
					goodsAreaSelector.setValue(stock.stockmanage.goodsAreaCode);
					goodsAreaSelector.addCls('readonlyhaveborder');
					goodsAreaSelector.setReadOnly(true);
				}
			}
		},{
			xtype: 'container',
			columnWidth:.84,
			html: '&nbsp;'
		},{
			//text: '查询'
			text: stock.stockmanage.i18n('foss.stock.search'),
			disabled: !stock.stockmanage.isPermission('stock/queryWaybillStockButton'),
			hidden: !stock.stockmanage.isPermission('stock/queryWaybillStockButton'),
			columnWidth:.08,
			cls : 'yellow_button',
			handler: function() {
				if(stock.stockmanage.queryform.getForm().isValid()){
					stock.stockmanage.waybillGoodsMap.clear();
					stock.stockmanage.pagingBar.moveFirst();
				}
			}
		}]
	}
	],
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//运单库存 model
Ext.define('Foss.stock.stockmanage.WaybillModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id' , type: 'string'},
		{name: 'waybillNO' , type: 'string'},
		{name: 'orgCode' , type: 'string'},
		{name: 'goodsAreaCode' , type: 'string'},
		{name: 'goodsAreaName' , type: 'string'},
		{name: 'stockGoodsCount', type: 'int'},
		{name: 'waybillGoodsCount' , type: 'string'},
		//行政区域
		{name: 'administrativeArea' , type: 'string'},
		{name: 'createWaybillTime',type:'date',convert: dateConvert},
		{name: 'inStockTime',type:'date',convert: dateConvert},
		{name: 'productCode', type: 'string'},
		{name: 'receiveMethod', type: 'string'},
		{name: 'weightTotal', type: 'string'},
		{name: 'volumeTotal', type: 'string'},
		{name: 'goodsName', type: 'string'},
		{name: 'departureCode', type: 'string'},
		{name: 'receiveOrgCode', type: 'string'},
		{name: 'arrivalTime', type: 'date',convert: dateConvert}
	]
});
//货件库存 model
Ext.define('Foss.stock.stockmanage.GoodsModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id', type: 'string'},
		{name: 'waybillNO', type: 'string'},
		{name: 'serialNO' , type: 'string'},
		{name: 'inStockTime',type:'date', convert: dateConvert},
		{name: 'operatorName', type: 'string'},
		{name: 'orgCode', type: 'string'},
		{name: 'goodsAreaCode', type: 'string'},
		{name: 'position',type:'string'},
		{name: 'createTime', type: 'date',convert: dateConvert}
	]
});

//货件库存 Store
Ext.define('Foss.stock.stockmanage.GoodsStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.stockmanage.GoodsModel',
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: stock.realPath('queryStock.action'),
		reader : {
			type : 'json',
			root : 'stockVO.stockList'
		}
    }
});

//运单库存 Store
var waybillStore_loading = null;
Ext.define('Foss.stock.stockmanage.WaybillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.stock.stockmanage.WaybillModel',
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'POST',
        url: stock.realPath('queryWaybillStock.action'),
		reader : {
			type : 'json',
			root : 'stockVO.waybillStockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				var queryParams = stock.stockmanage.queryform.getValues();
				var goodsAreaCode = null;
				if(queryParams.productCode == 'ALL'){
					queryParams.productCode = '';
				}
				//判断是不是驻地营业部,如果是的话,就把stockVO.waybillStockEntity.goodsAreaCode的值设置为queryParams.goodsAreaCodeForStation
				if('Y' == stock.stockmanage.isStationDelivery ){
					//如果在驻地部门查询,且输入了流水号,此时忽略库区code , 根据查询出来的数据的货区来设置库区
					if(queryParams.waybillNO == null || queryParams.waybillNO == ''){
						goodsAreaCode = queryParams.goodsAreaCodeForStation;
					}
					else{
						//如果只有一个库区
						if(stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.length == 1){
							goodsAreaCode = stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.items[0].data.valueCode;
						}else{
							//让两个驻地库区用','号连在一起
							goodsAreaCode = stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.items[0].data.valueCode
								+ ',' +stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.items[1].data.valueCode;
						}
					
					}
				}else{
					goodsAreaCode = queryParams.goodsAreaCode;
				}
				
				
				Ext.apply(operation, {
					params : {
						'stockVO.isStationDelivery' : stock.stockmanage.isStationDelivery,
						'stockVO.waybillStockEntity.orgCode' : queryParams.orgCode,
						'stockVO.waybillStockEntity.waybillNO' :queryParams.waybillNO ,
						'stockVO.waybillStockEntity.goodsAreaCode' : goodsAreaCode,
						'stockVO.waybillStockEntity.receiveMethod' : queryParams.receiveMethod,
//						'stockVO.beginInStockTime' : Ext.Date.parse(queryParams.beginInStockTime,'Y-m-d H:i:s'),
//						'stockVO.endInStockTime' : Ext.Date.parse(queryParams.endInStockTime,'Y-m-d H:i:s'),
						
						'stockVO.beginInStockTime' : queryParams.beginInStockTime,
						'stockVO.endInStockTime' : queryParams.endInStockTime,
						
						'stockVO.waybillStockEntity.otherGoodsAreaGode' : queryParams.otherGoodsAreaGode,
						'stockVO.waybillStockEntity.productCode' : queryParams.productCode,
						'stockVO.waybillStockEntity.position' : queryParams.positionSelectGoodsArea
					}
				});	
//				waybillStore_loading=new Ext.LoadMask(Ext.getCmp('Foss_stock_stockmanage_WaybillGrid_ID'),{
				waybillStore_loading=new Ext.LoadMask(Ext.getBody(),{
		              msg : 'Loading...',
		              removeMask : true// 完成后移除
		            });            
				waybillStore_loading.show();
				
		},
		
		//此监听用于支持翻页勾选
		'load' : function( store, records, successful, eOpts){
			
			stock.stockmanage.waybillMap.clear();
			
			var waybillStockStatisticsDto = store.proxy.reader.rawData.stockVO.waybillStockStatisticsDto;
			
			Ext.getCmp('Foss_stock_stockmanage_WaybillGrid_Toolbar_waybillTotal_ID').setValue(waybillStockStatisticsDto.waybillQty);
			Ext.getCmp('Foss_stock_stockmanage_WaybillGrid_Toolbar_goodsTotal_ID').setValue(waybillStockStatisticsDto.stockGoodsQty);
			Ext.getCmp('Foss_stock_stockmanage_WaybillGrid_Toolbar_weightTotal_ID').setValue(waybillStockStatisticsDto.weightTotal + ' 公斤');
			Ext.getCmp('Foss_stock_stockmanage_WaybillGrid_Toolbar_volumeTotal_ID').setValue(waybillStockStatisticsDto.volumeTotal + ' 方');
			var record;
			for(var i in records){
				record = records[i];

				  //如果查询某个流水号,根据查询出来的'货区code'来设置 货区下拉菜单的值
			if('Y' == stock.stockmanage.isStationDelivery ){//|| 'Y' == stock.stockmanage.isAirDispatch
				var goodsAreaCode = record.get('goodsAreaCode');
//					//如果库区code既不等于stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.items[1].data.valueCode
//					if(goodsAreaCode != stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.items[0].data.valueCode
//							&& goodsAreaCode != stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').store.data.items[1].data.valueCode){
//						//不加载此条数据
//						
//						
//					}else{
				stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').setValue(goodsAreaCode);
//					}
				
			}
				
				var receiveMethod= record.get('receiveMethod');
				if(!Ext.isEmpty(receiveMethod)){
					var regex = /(DELIVER)+/g.test(receiveMethod);
					if(regex){
						record.set('receiveMethod','派送');
					}else{
						record.set('receiveMethod','自提');
					}
				}
				
				var waybillNO = record.get('waybillNO');
				var goodsAreaCode = record.get('goodsAreaCode');
				var mapKey = waybillNO + goodsAreaCode;
				stock.stockmanage.waybillMap.add(mapKey,record);
				if(stock.stockmanage.waybillGoodsMap.get(mapKey) != null){
					var selectRecord = stock.stockmanage.waybillMap.get(mapKey);
					stock.stockmanage.waybillGrid.getSelectionModel().select(selectRecord,true,true);
				}
			}
			waybillStore_loading.hide();
		}
	}

});

// 货件库存 Grid （第二层表格）
Ext.define('Foss.stock.stockmanage.GoodsGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_stock_stockmanage_GoodsGrid_ID',
	columnLines: true,
    frame: true,
	//根据上一层表的行数据 加载Store
	bindData :function(record){
		var waybillNO = record.get('waybillNO');
		var goodsAreaCode = record.get('goodsAreaCode');
		var orgCode = record.get('orgCode');
		var grid = this;
		Ext.Ajax.request({
			url: stock.realPath('queryStock.action'),
			params:{'stockVO.waybillStockEntity.waybillNO': waybillNO,
					'stockVO.waybillStockEntity.orgCode': orgCode,
					'stockVO.waybillStockEntity.goodsAreaCode': goodsAreaCode
					},
			success:function(response){
				var result = Ext.decode(response.responseText);
				/*if(!result.stockVO.stockList || result.stockVO.stockList.length == 0) {
					Ext.MessageBox.alert("提示", "没有数据行!");
					return;
				}*/
				grid.store.loadData(result.stockVO.stockList);
				var serialNO;
				
				var mapKey = waybillNO + goodsAreaCode;
				var goodsMap = stock.stockmanage.waybillGoodsMap.get(mapKey);
				if(goodsMap != null){
		
					for(var i=0;i<grid.store.getCount();i++){
						serialNO = grid.store.getAt(i).get('serialNO');
						if(goodsMap.get(serialNO) != null){
							
							grid.getSelectionModel().select(grid.store.getAt(i),true,true);
						}
					}
				}
			}
		})
		
	},
	columns: [{
			//header: '流水号', 
			header: stock.stockmanage.i18n('foss.stock.serialno'), 
			dataIndex: 'serialNO' 
		},{ 
			//header: '入库时间', 
			header: stock.stockmanage.i18n('foss.stock.instocktime'), 
			dataIndex: 'inStockTime',
			xtype: 'datecolumn', 
			width : 150,
			format:'Y-m-d H:i:s'
		},{
			//header: '操作人', 
			header: stock.stockmanage.i18n('foss.stock.operator'),
			dataIndex: 'operatorName' 
		},{
			header: '库位', 
			//header: stock.stockmanage.i18n('foss.stock.operator'),
			dataIndex: 'position' 
		},{
			//header: '到达时间', 
			header: '到达时间', 
			dataIndex: 'createTime',
			xtype: 'datecolumn', 
			width : 150,
			format:'Y-m-d H:i:s'
		}],    
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stock.stockmanage.GoodsStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			showHeaderCheckbox : false,
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.callParent([cfg]);
	},
	listeners: {
			select : function(rowModel, record, index, eOpts) {
				var waybillNO = record.get('waybillNO');
				var goodsAreaCode = record.get('goodsAreaCode');
				var mapKey = waybillNO + goodsAreaCode;

				var serialNO = record.get('serialNO');
				var goodsMap = stock.stockmanage.waybillGoodsMap.get(mapKey);
				
				if( goodsMap== null){
					goodsMap = new Ext.util.HashMap();
				}
				goodsMap.add(serialNO,record);
				stock.stockmanage.waybillGoodsMap.add(mapKey,goodsMap);
				//勾选上层运单库存
				var selectRecord = stock.stockmanage.waybillMap.get(mapKey);
				stock.stockmanage.waybillGrid.getSelectionModel().select(selectRecord,true,true);
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this;
				var waybillNO = record.get('waybillNO');
				var goodsAreaCode = record.get('goodsAreaCode');
				var mapKey = waybillNO + goodsAreaCode;
				
				var serialNO = record.get('serialNO');
				var selectedList = grid.getSelectionModel().selected;
				if(selectedList.length == 0){
					//取消勾选上层运单库存
					var superGrid = this.superGrid;
					var superRecord = stock.stockmanage.waybillMap.get(mapKey);
					superGrid.getSelectionModel().deselect(superRecord,true);
					stock.stockmanage.waybillGoodsMap.removeAtKey(mapKey);
				}else{
					var goodsMap = stock.stockmanage.waybillGoodsMap.get(mapKey);
					goodsMap.removeAtKey(serialNO);
					stock.stockmanage.waybillGoodsMap.add(mapKey,goodsMap);
				}
			}
		}
});
//运单库存表格
Ext.define('Foss.stock.stockmanage.WaybillGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_stock_stockmanage_WaybillGrid_ID',
	height: 500,
	autoScroll:true,
	columnLines: true,
    frame: true,
    viewConfig : {
		loadMask : false
	},
    plugins: [{
        ptype: 'rowexpander',
        pluginId: 'Foss.stock.stockmanage.WaybillGrid_Plugin_ID',
		rowsExpander: false,
		rowBodyElement : 'Foss.stock.stockmanage.GoodsGrid'
	}],
	columns: [{
			//header: '运单号', 
			header: stock.stockmanage.i18n('foss.stock.waybill'),
			dataIndex: 'waybillNO',
			width : 100,
			align: 'center'
		},{ 
			//header: '货区', 
			header: stock.stockmanage.i18n('foss.stock.goodsarea'),
			dataIndex: 'goodsAreaName',
			width : 100,
			align: 'center',
			renderer : function(value,metaData,record){
				var goodsAreaCode = record.get('goodsAreaCode');
				if(!Ext.isEmpty(goodsAreaCode)){
					if(goodsAreaCode == 'BULK_GOODS_AREA'){
						value = '散货';
					}
					if(goodsAreaCode == 'WHOLE_GOODS_AREA'){
						value = '整车';
					}
				}
				return value;
			}
		},{ 
			//header: '库存件数', 
			header: stock.stockmanage.i18n('foss.stock.goodsqty'),
			dataIndex: 'stockGoodsCount',
			width : 90,
			align: 'center'
		},{ 
			//header: '开单件数', 
			header: stock.stockmanage.i18n('foss.stock.waybillgoodsqty'),
			dataIndex: 'waybillGoodsCount',
			width : 90,
			align: 'center'
		},{ 
			header: '行政区域', 
//			header: stock.stockmanage.i18n('foss.stock.waybillgoodsqty'),
			dataIndex: 'administrativeArea',
			width : 90,
			align: 'center'
		},{ 
			//header: '开单时间', 
			header: stock.stockmanage.i18n('foss.stock.createbilltime'),
			dataIndex: 'createWaybillTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 150,
			align: 'center'
		},{ 
			header: '到达时间', 
			dataIndex: 'arrivalTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 150,
			align: 'center'
		},{ 
			header: '入库时间', 
			dataIndex: 'inStockTime',
			xtype: 'datecolumn', 
			format:'Y-m-d H:i:s',
			width : 150,
			align: 'center'
		},{ 
			//header: '运输性质', 
			header: stock.stockmanage.i18n('foss.stock.product'),
			dataIndex: 'productCode',
			width : 90,
			align: 'center'
		},{ 
			header: '提货方式', 
			//header: stock.stockmanage.i18n('foss.stock.product'),
			dataIndex: 'receiveMethod',
			width : 90,
			align: 'center'
		},{ 
			//header: '重量（公斤）', 
			header: stock.stockmanage.i18n('foss.stock.weight'),
			dataIndex: 'weightTotal',
			width : 90,
			align: 'center'
		},{ 
			//header: '体积（方）', 
			header: stock.stockmanage.i18n('foss.stock.volume'),
			dataIndex: 'volumeTotal',
			width : 90,
			align: 'center'
		},{ 
			//header: '货物名称', 
			header: stock.stockmanage.i18n('foss.stock.goodsname'),
			dataIndex: 'goodsName',
			width : 100,
			align: 'center'
		},{ 
			//header: '出发部门', 
			xtype: 'ellipsiscolumn',
			//header: stock.stockmanage.i18n('foss.stock.departureorg'),
			text: stock.stockmanage.i18n('foss.stock.departureorg'),
			dataIndex: 'departureCode',
			width : 180,
			align: 'center'
		},{ 
			//header: '到达部门',
			xtype: 'ellipsiscolumn',
			//header: stock.stockmanage.i18n('foss.stock.receiveorg'),
			text: stock.stockmanage.i18n('foss.stock.receiveorg'),
			dataIndex: 'receiveOrgCode',
			width : 180,
			align: 'center'
		}],
		dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true
		   },
		   items:[{
			   //fieldLabel:'总票数',
			   fieldLabel: stock.stockmanage.i18n('foss.stock.waybilltotal'),
			   id:'Foss_stock_stockmanage_WaybillGrid_Toolbar_waybillTotal_ID',
			   columnWidth:.15,
			   labelWidth:60,
			   dataIndex: 'waybillTotal'
		   },{
			   //fieldLabel:'总件数',
			   fieldLabel: stock.stockmanage.i18n('foss.stock.goodstotal'),
			   id:'Foss_stock_stockmanage_WaybillGrid_Toolbar_goodsTotal_ID',
			   columnWidth:.15,
			   labelWidth:60,
			   dataIndex: 'goodsTotal'
		   },{
			   //fieldLabel:'总重量',
		   	   fieldLabel: stock.stockmanage.i18n('foss.stock.weighttotal'),
			   id:'Foss_stock_stockmanage_WaybillGrid_Toolbar_weightTotal_ID',
			   columnWidth:.25,
			   labelWidth:60,
			   dataIndex: 'weightTotal'
		   },{
			   //fieldLabel:'总体积',
			   fieldLabel: stock.stockmanage.i18n('foss.stock.volumetotal'),
			   id:'Foss_stock_stockmanage_WaybillGrid_Toolbar_volumeTotal_ID',
			   columnWidth:.25,
			   labelWidth:60,
			   dataIndex: 'volumeTotal'
		   }]
		}],
	    constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.stock.stockmanage.WaybillStore');
			me.selModel = Ext.create('Ext.selection.CheckboxModel',{
				//showHeaderCheckbox : false,
				mode : 'SIMPLE',
				checkOnly : true//限制只有点击checkBox后才能选中行
			});
			
			me.tbar = [{
				xtype: 'button',
				text: stock.stockmanage.i18n('foss.stock.instock'),
				disabled: !stock.stockmanage.isPermission('/tfr-stock-web/stock/inStock.action'),
				hidden: !stock.stockmanage.isPermission('/tfr-stock-web/stock/inStock.action'),
				//text: '单票入库',
				gridContainer: this,
				handler: function() {
					stock.stockmanage.inStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getInStockWindow();
					stock.stockmanage.saleOrgInStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getSaleOrgInStockWindow();
					var areaStore = stock.stockmanage.inStockSpecialAreaForm.getForm().findField('goodsAreaCode').store;

					//外场类型
					if(stock.stockmanage.isTransferCenter == 'Y'){
						areaStore.load();
						Ext.getCmp('Foss_stock_stockmanage_GoodsSerialNOGrid_WaybillNO_ID').allowBlank = false;
						Ext.getCmp('Foss_stock_stockmanage_InStockSpecialAreaSerialNOGrid_WaybillNO_ID').allowBlank = false;
						stock.stockmanage.inStockForm.getForm().findField('orgCode').setValue(stock.stockmanage.orgCode);
						stock.stockmanage.inStockSpecialAreaForm.getForm().findField('orgCode').setValue(stock.stockmanage.orgCode);
						stock.stockmanage.inStockForm.getForm().findField('orgName').setValue(stock.stockmanage.orgName);
						stock.stockmanage.inStockSpecialAreaForm.getForm().findField('orgName').setValue(stock.stockmanage.orgName);
						stock.stockmanage.inStockWindow.show();
					}else{
						Ext.getCmp('Foss_stock_stockmanage_SaleOrgGoodsSerialNOGrid_WaybillNO_ID').allowBlank = false;
						stock.stockmanage.saleOrgInStockForm.getForm().findField('orgCode').setValue(stock.stockmanage.orgCode);
						stock.stockmanage.saleOrgInStockForm.getForm().findField('orgName').setValue(stock.stockmanage.orgName);
						stock.stockmanage.saleOrgInStockWindow.show();
					}
					
					
				}
				},{
					xtype: 'button',
					//text: '导出',
					text: stock.stockmanage.i18n('foss.stock.export'),
					disabled: !stock.stockmanage.isPermission('stock/exportExcelButton'),
					hidden: !stock.stockmanage.isPermission('stock/exportExcelButton'),
					gridContainer: this,
					handler: function() {
						
						var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
						/*if(!goodsMapList || goodsMapList.length==0){
							Ext.MessageBox.alert('请选择要导出的数据！');
							return;
						}*/
						var ids='';
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								ids +=RecordMapList[j].data.id + ","; 
							}
						}
						ids = ids.substring(0,ids.length-1);
						if(!Ext.fly('downloadAttachFileForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadAttachFileForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
						}
						var queryParams = stock.stockmanage.queryform.getValues();
						if(queryParams.productCode == 'ALL'){
							queryParams.productCode = '';
						}
						//判断是不是驻地营业部   如果是驻地营业部goodsareaCode 取值为queryParams.goodsAreaCodeForStation
						goodsAreaCode = queryParams.goodsAreaCode;
						if('Y' == stock.stockmanage.isStationDelivery){
							goodsAreaCode = queryParams.goodsAreaCodeForStation;
						}
						
						Ext.Ajax.request({
			    			url: stock.realPath('exportExcel.action'),
			    			form: Ext.fly('downloadAttachFileForm'),
			    			method : 'POST',
			    			params : {'stockVO.ids' : ids,
			    					  'stockVO.waybillStockEntity.orgCode' : queryParams.orgCode,
									  'stockVO.waybillStockEntity.waybillNO' :queryParams.waybillNO ,
									  'stockVO.waybillStockEntity.goodsAreaCode' : goodsAreaCode,
									  'stockVO.beginInStockTime' : queryParams.beginInStockTime,
									  'stockVO.endInStockTime' : queryParams.endInStockTime,
									  'stockVO.waybillStockEntity.receiveMethod' : queryParams.receiveMethod,
									  'stockVO.waybillStockEntity.productCode' : queryParams.productCode
			    			},
			    			isUpload: true,
			    			exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				//top.Ext.MessageBox.alert('导出失败',result.message);
			    				top.Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.exportfailure'),result.message);
			    			}
		    			});
					}
				}, {
					xtype: 'button',
					//text: '登出',
					text: stock.stockmanage.i18n('foss.stock.logout'),
					disabled: !stock.stockmanage.isPermission('stock/logoutStockButton'),
					hidden: !stock.stockmanage.isPermission('stock/logoutStockButton'),
					gridContainer: this,
					handler: function() {
						var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							//Ext.ux.Toast.msg('提示', '请选择需要登出的货物！', 'error', 2000);
							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.logout.goods'), 'error', 2000);
							return;
						}
						var outStockList = new Array();
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								//校验货区是否是特殊货区 goodsAreaCode
								if(RecordMapList[j].data.goodsAreaCode != stock.stockmanage.valuableAreaCode &&
								   	  RecordMapList[j].data.goodsAreaCode != stock.stockmanage.packingAreaCode &&
										 RecordMapList[j].data.goodsAreaCode != stock.stockmanage.exceptionAreaCode){
									//Ext.MessageBox.alert('提示','非特殊货区货物不可登出！');	
								 	Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.special.not.logout'));	
									return;
								}else if('W' == RecordMapList[j].data.waybillNO.charAt(0)){
									Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),'无标签货物不可登出，请在异常货模块处理无标签货物');
									return;
								}else{
									outStockList.push(RecordMapList[j].data);
								}
							}
						}
						//Ext.MessageBox.confirm('提示', '确认将选中的货物登出吗？',function(btn){
						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.logout'),function(btn){
						if(btn == 'yes'){
							
							var jsonParam = {stockVO: {outStockList:outStockList}};
							Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
							Ext.Ajax.request({
			    			url: stock.realPath('logoutStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				stock.stockmanage.waybillGoodsMap.clear();
			    				stock.stockmanage.waybillGrid.store.load();
			    				//Ext.ux.Toast.msg('提示', '登出成功！', 'ok', 3000);
			    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.logout.success'), 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('登出失败', result.message, 'error', 3000);
			    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.logout.failure'), result.message, 'error', 3000);
			    			}
			    			});	
						}
					});
						
											
					}
				}, {
					xtype: 'button',
					//text: '出库',
					text: stock.stockmanage.i18n('foss.stock.outstock'),
					disabled: !stock.stockmanage.isPermission('stock/outStockButton'),
					hidden: !stock.stockmanage.isPermission('stock/outStockButton'),
					gridContainer: this,
					handler: function() {
						
						/*Ext.Ajax.request({
			    			url: stock.realPath('testInStockCreateBill.action'),
			    			success:function(response){
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('提示', '测试成功!', 'ok', 3000);
			    			},
			    			exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				Ext.ux.Toast.msg('测试失败', result.message, 'error', 3000);
			    			}
		    			});*/
						
						var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							//Ext.ux.Toast.msg('提示', '请选择需要出库的货物！', 'error', 2000);
							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.outstock.goods'), 'error', 2000);
							return;
						}
						var outStockList = new Array();
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								//校验货区是否是特殊货区 goodsAreaCode
								if(RecordMapList[j].data.goodsAreaCode == stock.stockmanage.valuableAreaCode ||
								   	  RecordMapList[j].data.goodsAreaCode == stock.stockmanage.packingAreaCode ||
										 RecordMapList[j].data.goodsAreaCode == stock.stockmanage.exceptionAreaCode){
									//Ext.MessageBox.alert('提示','特殊货区货物不可出库！');
									Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.special.not.out.stock'));
									return;
								}else{
									outStockList.push(RecordMapList[j].data);
								}
							}
						}
						//Ext.MessageBox.confirm('提示', '确认将选中的货物出库吗？',function(btn){
						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.confirm.outstock'),function(btn){
							if(btn == 'yes'){
								/*var outStockList = new Array();
								var RecordMapList;
								for(var i=0;i<goodsMapList.length;i++){
									RecordMapList = goodsMapList[i].getValues();
									for(var j=0;j<RecordMapList.length;j++){
										outStockList.push(RecordMapList[j].data);
									}
								}*/
								
								var jsonParam = {stockVO: {outStockList:outStockList}};
								Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
								Ext.Ajax.request({
					    			url: stock.realPath('outStock.action'),
					    			jsonData:jsonParam,
					    			success:function(response){
					    				Ext.Msg.hide();
					    				var result = Ext.decode(response.responseText);
					    				stock.stockmanage.waybillGoodsMap.clear();
					    				stock.stockmanage.waybillGrid.store.load();
					    				//Ext.ux.Toast.msg('提示', '出库成功!', 'ok', 3000);
					    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.outstock.success'), 'ok', 3000);
					    			},
					    			exception : function(response) {
					    				Ext.Msg.hide();
					    				var result = Ext.decode(response.responseText);
					    				//Ext.ux.Toast.msg('出库失败', result.message, 'error', 3000);
					    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.outstock.failure'), result.message, 'error', 3000);
					    			}
				    			});
							}
						});
					}
			},{
					xtype: 'button',
					//text: '散货入库',
					text: stock.stockmanage.i18n('foss.stock.bulkinstock'),
					disabled: !stock.stockmanage.isPermission('stock/bulkGoodsInStockButton'),
					hidden: !stock.stockmanage.isPermission('stock/bulkGoodsInStockButton'),
					gridContainer: this,
					handler: function(item) {
						item.disable();
						var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
						if(!goodsMapList.length > 0){
							//Ext.ux.Toast.msg('提示', '请选择需要入库的货物！', 'error', 2000);
							Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.instock.goods'), 'error', 2000);
							item.enable();
							return;
						}
						var inStockList = new Array();
						var RecordMapList;
						for(var i=0;i<goodsMapList.length;i++){
							RecordMapList = goodsMapList[i].getValues();
							for(var j=0;j<RecordMapList.length;j++){
								//校验货区是否是散货货区 goodsAreaCode
								if(RecordMapList[j].data.goodsAreaCode != 'BULK_GOODS_AREA' ){
									//Ext.MessageBox.alert('提示','非散货货区货物不可做散货入库！');
									Ext.MessageBox.alert(stock.stockmanage.i18n('foss.stock.prompt'),stock.stockmanage.i18n('foss.stock.not.bulk.not.instock'));	
									item.enable();
									return;
								}else{
									inStockList.push(RecordMapList[j].data);
								}
							}
						}
						
						Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
						var jsonParam = {stockVO: {inStockList:inStockList}};
						
						Ext.Ajax.request({
			    			url: stock.realPath('bulkGoodsInStock.action'),
			    			jsonData:jsonParam,
			    			success:function(response){
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				stock.stockmanage.waybillGoodsMap.clear();
			    				stock.stockmanage.waybillGrid.store.load();
			    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
			    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
			    				item.enable();
			    			},
			    			exception : function(response) {
			    				Ext.Msg.hide();
			    				var result = Ext.decode(response.responseText);
			    				//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
			    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
			    				item.enable();
			    			}
		    			});
						
					}
			},{
				xtype: 'button',
				text: '库位确认',
				//text: stock.stockmanage.i18n('foss.stock.bulkinstock'),
				disabled: !stock.stockmanage.isPermission('stock/positionButton'),
				hidden: !stock.stockmanage.isPermission('stock/positionButton'),
				gridContainer: this,
				handler: function(item) {
					item.disable();				
					var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
					if(!goodsMapList.length > 0){
						Ext.ux.Toast.msg('提示', '请选择需要库位确认的货物！', 'error', 2000);
						item.enable();
						return;
					}
					
					stock.stockmanage.positionSelectWindow.show();
					item.enable();
				}
			}];
			
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
			stock.stockmanage.pagingBar = me.bbar;
			me.callParent([cfg]);
		},
		listeners: {
			select : function(rowModel, record, index, eOpts) {
				var grid = this,
				plugin = grid.getPlugin('Foss.stock.stockmanage.WaybillGrid_Plugin_ID');
				var waybillNO = record.get('waybillNO');
				var orgCode = record.get('orgCode');
				var goodsAreaCode = record.get('goodsAreaCode');
				
				var mapKey = waybillNO + goodsAreaCode;
				
				var goodsStore = Ext.create('Foss.stock.stockmanage.GoodsStore',{
					listeners: {
						'load': function(store, records, successful, eOpts ){
							var goodsMap = new Ext.util.HashMap();
							for(var i = 0; i<records.length;i++){
								var record = records[i];
								goodsMap.add(record.get('serialNO'),record);
							}
							stock.stockmanage.waybillGoodsMap.add(mapKey,goodsMap);
						}
					}
				});
				goodsStore.load({
					params:{'stockVO.waybillStockEntity.waybillNO': waybillNO,
					'stockVO.waybillStockEntity.orgCode': orgCode,
					'stockVO.waybillStockEntity.goodsAreaCode': goodsAreaCode
					}
				});	
				
				if(!Ext.isEmpty(plugin.getExpendRow())) {
					var item = plugin.getExpendRowBody();
					var store = item.getStore();
					var subWaybillNo = store.getAt(0).get('waybillNO');
					var subGoodsAreaCode = store.getAt(0).get('goodsAreaCode');
					if(subWaybillNo == record.get('waybillNO') && subGoodsAreaCode == record.get('goodsAreaCode')){
						item.getSelectionModel().selectAll(true);
					}
				}
				
			},
			deselect : function(rowModel, record, index, eOpts) {
				var grid = this,
				plugin = grid.getPlugin('Foss.stock.stockmanage.WaybillGrid_Plugin_ID');
				var waybillNO = record.get('waybillNO');
				var goodsAreaCode = record.get('goodsAreaCode');
				var mapKey = waybillNO + goodsAreaCode;
				
				stock.stockmanage.waybillGoodsMap.removeAtKey(mapKey);
				if(!Ext.isEmpty(plugin.getExpendRow())) {
					var item = plugin.getExpendRowBody();
					var store = item.getStore();
					var subWaybillNo = store.getAt(0).get('waybillNO');
					var subGoodsAreaCode = store.getAt(0).get('goodsAreaCode');
					if(subWaybillNo == record.get('waybillNO') && subGoodsAreaCode == record.get('goodsAreaCode')){
						item.getSelectionModel().deselectAll(true);
					}
				}
			}
		}
});
/************************************************************单件入库界面****************************************/

//单件入库特殊货区
Ext.define('Foss.stock.stockmanage.InStockGoodsAreaStore',{
	extend:'Ext.data.Store',
	autoLoad: false,
	fields:[
		{name: 'valueName',  type: 'string'},
		{name: 'valueCode',  type: 'string'}
	],
	proxy: {
		//以JSON的方式加载数据
		type : 'ajax',
		actionMethods:'post',
		url: stock.realPath('querySpecialAreaList.action'),
		reader : {
			type : 'json',
			root : 'stockVO.specialAreaList',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			Ext.apply(operation, {
				params : {
					'stockVO.stockOrgCode' : stock.stockmanage.queryform.getForm().findField('orgCode').getValue()
				}
			});	
		}
	}
});

//单票入库界面 流水号表格 Store
Ext.define('Foss.stock.stockmanage.GoodsSerialNOStore',{
	extend: 'Ext.data.Store',
	fields: ['serialNO','stockStatic'],
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: stock.realPath('queryGoods.action'),
        timeout:300000,
		reader : {
			type : 'json',
			root : 'stockVO.stockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
						'stockVO.inOutStockEntity.waybillNO' : Ext.getCmp('Foss_stock_stockmanage_GoodsSerialNOGrid_WaybillNO_ID').getValue()
					}
				});	
		},
		'load': function(records, operation, success){
				for(var i = 0; i<records.data.length;i++){
					var record = records.data.items[i];
					var recordStatic = record.get('stockStatic');
					if(recordStatic!=null&&recordStatic=='AT'){
						record.set('stockStatic','在库存');
					}
					if(recordStatic!=null&&recordStatic=='SIGN'){
						record.set('stockStatic','已签收');
					}
				}
			}
	}
});

//单票入库界面 流水号表格
Ext.define('Foss.stock.stockmanage.GoodsSerialNOGrid', {
	extend:'Ext.grid.Panel',
	columnLines: true,
    frame: true,
    height: 300,
	 viewConfig: {
    	stripeRows: false,
    	getRowClass: function(record, rowIndex, rp, ds) {
    		var status = record.get('stockStatic');
    			if(status!=null){
    				return 'predeliver_notice_customer_row_purole';	
    				}
    			}	
    },
	columns: [{
			//header: '流水号',
			header: stock.stockmanage.i18n('foss.stock.serialno'), 
			dataIndex: 'serialNO' 
		},{
			header: '状态',
			//header: stock.stockmanage.i18n('foss.stock.serialno'), 
			dataIndex: 'stockStatic' 
		}],   
		
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stock.stockmanage.GoodsSerialNOStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		/*me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		stock.stockmanage.inStockpagBar = me.bbar;*/
		me.tbar = [{
					xtype: 'textfield',
					//fieldLabel: '运单号',
					fieldLabel: stock.stockmanage.i18n('foss.stock.waybill'),
			        name: 'waybillNO',
			        id: 'Foss_stock_stockmanage_GoodsSerialNOGrid_WaybillNO_ID',
			        allowBlank: false,
					//blankText:'字段不能为空'
					blankText:stock.stockmanage.i18n('foss.stock.notnull'),
			        columnWidth:.4,
			        vtype: 'waybill'
				},{
					xtype: 'button',
					//text: '查询',
					text: stock.stockmanage.i18n('foss.stock.search'),
					gridContainer: this,
					handler: function() {
						stock.stockmanage.inStockSerialNOMap.clear();
						//stock.stockmanage.inStockpagBar.moveFirst();
						stock.stockmanage.goodsSerialNOGrid.store.load();
					}
				}];
		
		
		me.callParent([cfg]);
	},
	listeners: {
			beforeselect : function(rowModel, record, index, eOpts) {
				var recordStatic = record.get('stockStatic');
				if(recordStatic!=null){
					return false;
				}
			},
			select : function(rowModel, record, index, eOpts) {
					var serialNO = record.get('serialNO');
					stock.stockmanage.inStockSerialNOMap.add(serialNO,serialNO);
			},
			deselect : function(rowModel, record, index, eOpts) {
				var serialNO = record.get('serialNO');
				stock.stockmanage.inStockSerialNOMap.removeAtKey(serialNO);
			}
	}
});
//单票入库表单
Ext.define('Foss.stock.stockmanage.InStockForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: '部门编号',
		name: 'orgCode',
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '部门名称',
		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgName',
		readOnly: true,
		columnWidth:.5
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},
	FossDataDictionary.getDataDictionaryCombo('STOCK_TYPE_IN',
		{
			//fieldLabel : '入库类型',
			fieldLabel: stock.stockmanage.i18n('foss.stock.instocktype'),
			name : 'inOutStockType',
			editable : false,
			columnWidth:.4,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank: false,
			//blankText:'字段不能为空'
			blankText:stock.stockmanage.i18n('foss.stock.notnull')
		}
	)	
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义特殊货区登入表单
Ext.define('Foss.stock.stockmanage.InStockSpecialAreaForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: '部门编号',
		name: 'orgCode',
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '部门',
		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgName',
		readOnly: true,
		columnWidth:.5
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},{
		xtype: 'combo',
		//fieldLabel: '货区',
		fieldLabel: stock.stockmanage.i18n('foss.stock.goodsarea'),
		name: 'goodsAreaCode',
		displayField: 'valueName',
		valueField:'valueCode', 
		columnWidth:.4,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		allowBlank: false,
		//blankText:'字段不能为空',
		blankText: stock.stockmanage.i18n('foss.stock.notnull'),
		store:Ext.create('Foss.stock.stockmanage.InStockGoodsAreaStore')
	}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//特殊货区登入页签  流水号表格 Store
Ext.define('Foss.stock.stockmanage.InStockSpecialAreaSerialNOStore',{
	extend: 'Ext.data.Store',
	fields: ['serialNO'],
	pageSize:10,
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'get',
        url: stock.realPath('queryGoods.action'),
        timeout:300000,
		reader : {
			type : 'json',
			root : 'stockVO.stockList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
						'stockVO.inOutStockEntity.waybillNO' : Ext.getCmp('Foss_stock_stockmanage_InStockSpecialAreaSerialNOGrid_WaybillNO_ID').getValue()
					}
				});	
		}
    }
    /*listeners: {
		beforeload : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
						'stockVO.inOutStockEntity.waybillNO' : stock.stockmanage.inStockSpecialAreaForm.getValues().waybillNO
					}
				});	
		},
		//此监听用于支持翻页勾选
		'load' : function( store, records, successful, eOpts){
			var serialNO;
			for(var i in records){
				serialNO = records[i].get('serialNO');
				if(stock.stockmanage.inStockSpecialSerialNOMap.get(serialNO)!= null){
					var selectRecord = stock.stockmanage.inStockSpecialAreaSerialNOGrid.store.findRecord('serialNO',serialNO,0,false,false,false);
					stock.stockmanage.inStockSpecialAreaSerialNOGrid.getSelectionModel().select(selectRecord,true,true);
				}
			}
		}
	}*/
});


//特殊货区登入页签  流水号表格
Ext.define('Foss.stock.stockmanage.InStockSpecialAreaSerialNOGrid', {
	extend:'Ext.grid.Panel',
	columnLines: true,
    frame: true,
    height: 300,
	columns: [{
			//header: '流水号',
			header: stock.stockmanage.i18n('foss.stock.serialno'),
			dataIndex: 'serialNO' 
		}],   
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stock.stockmanage.InStockSpecialAreaSerialNOStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.tbar = [{
					xtype: 'textfield',
					//fieldLabel: '运单号',
					fieldLabel: stock.stockmanage.i18n('foss.stock.waybill'),
			        name: 'waybillNO',
			        id: 'Foss_stock_stockmanage_InStockSpecialAreaSerialNOGrid_WaybillNO_ID',
			        allowBlank: false,
					//blankText:'字段不能为空',
					blankText: stock.stockmanage.i18n('foss.stock.notnull'),
			        columnWidth:.4,
			        vtype: 'waybill'
				},{
					xtype: 'button',
					//text: '查询',
					text: stock.stockmanage.i18n('foss.stock.search'),
					gridContainer: this,
					handler: function() {
						stock.stockmanage.inStockSpecialSerialNOMap.clear();
						stock.stockmanage.inStockSpecialAreaSerialNOGrid.store.load();
					}
				}];
		me.callParent([cfg]);
	},
	listeners: {
			select : function(rowModel, record, index, eOpts) {
				var serialNO = record.get('serialNO');
				stock.stockmanage.inStockSpecialSerialNOMap.add(serialNO,serialNO);
			},
			deselect : function(rowModel, record, index, eOpts) {
				var serialNO = record.get('serialNO');
				stock.stockmanage.inStockSpecialSerialNOMap.removeAtKey(serialNO);
			}
	}
});

//定义单票入库Tab页
Ext.define('Foss.stock.stockmanage.InStockTab',{
	extend:'Ext.tab.Panel',
	id:'Foss.stock.stockmanage.InStockTab_Id',
	activeTab:0,
	autoScroll:false,
	frame: false,
	width: 770,
	constructor: function(config) {
		Ext.apply(this, config);
		this.items = [{
				//title: '单票入库',
				title: stock.stockmanage.i18n('foss.stock.instock'),
				disabled: !stock.stockmanage.isPermission('stock/queryGoodsButton'),
				hidden: !stock.stockmanage.isPermission('stock/queryGoodsButton'),
				tabConfig:{width:80},
				items: Ext.create('Foss.stock.stockmanage.InStockTabPanel')
			 },{
				//title: '特殊货区登入',
				title: stock.stockmanage.i18n('foss.stock.instockspecial'),
				disabled: !stock.stockmanage.isPermission('stock/inStockButton'),
				hidden: !stock.stockmanage.isPermission('stock/inStockButton'),
				tabConfig:{width:80},
				items: Ext.create('Foss.stock.stockmanage.InStockSpecialAreaTabPanel')
			 }
		];
		this.callParent(arguments);
	}
});
//单票入库页签
Ext.define('Foss.stock.stockmanage.InStockTabPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.stockmanage.inStockForm = Ext.create('Foss.stock.stockmanage.InStockForm');	
			stock.stockmanage.goodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.GoodsSerialNOGrid');
			me.items = [
			    stock.stockmanage.inStockForm, stock.stockmanage.goodsSerialNOGrid   
			];
			me.callParent([cfg]);
		},
		buttons: [
		  { //text: '确认入库',
		    text: stock.stockmanage.i18n('foss.stock.confirminstock'),
		  	cls : 'yellow_button',
		    handler: function(){
        	if(stock.stockmanage.inStockForm.getForm().isValid()){
        		var formParams = stock.stockmanage.inStockForm.getValues();
        		formParams.waybillNO = Ext.getCmp('Foss_stock_stockmanage_GoodsSerialNOGrid_WaybillNO_ID').getValue();
        		var flag = true;//用于判断是不是快递
        		//此处需要验证运单是不是快递
        		Ext.Ajax.request({
        			url: stock.realPath('ifIsExpressWaybill.action'),
        			async:false,
        			params:{'stockVO.waybillNOs':formParams.waybillNO},                          
        			success:function(response){
        				var result = Ext.decode(response.responseText);
        				if(result.stockVO.ifIsExpressWaybill==false){
        					Ext.Msg.alert("提示","此票货非快递货，您无入库操作权限");
        					flag=result.stockVO.ifIsExpressWaybill;
        					return;
        				}
        			},
        			failure:function(response){
        				Ext.Msg.alert("提示","验证是否是快递运单失败！");
        				return;
        			}
        		});
        		if(flag==false){
        			return;
        		}
        		
	        	//获取所有被选中的记录
	        	var serialNOList = stock.stockmanage.inStockSerialNOMap.getValues();
	        	
	        	if(serialNOList.length < 1){
	        		//Ext.ux.Toast.msg('提示', '请选择流水号！', 'error', 2000);
	        		Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
	        	}
	        	
	        	var serialNOs = '';
	        	for(var i=0;i<serialNOList.length;i++){
						serialNOs +=serialNOList[i] + ","; 
					}
				serialNOs = serialNOs.substring(0,serialNOs.length-1);
				
	    		var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs}};
				Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
	    		//提交入库
	        	Ext.Ajax.request({
	    			url: stock.realPath('inStock.action'),
					timeout: 300000,
	    			jsonData:jsonParam,
	    			success:function(response){
					Ext.Msg.hide(); 
	    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
	    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
	    				var result = Ext.decode(response.responseText);
	    				//清空MAP
	    				stock.stockmanage.waybillGoodsMap.clear();
	    				stock.stockmanage.waybillGrid.store.load();
	    				var inStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getInStockWindow();
    					inStockWindow.close();
	    			},
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				var exceptionCode = result.stockVO.exceptionCode;
	    				if(exceptionCode == 'error.no.partialline' || exceptionCode == 'error.no.arrive.sheet' ||
		    						exceptionCode == 'error.no.arrange.send' || exceptionCode == 'error.no.air.waybill' ||
		    							exceptionCode == 'error.no.diff'){
	    					//Ext.MessageBox.confirm('提示', result.message+'?' ,function(btn){
	    					Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), result.message+'?' ,function(btn){
								if(btn == 'yes'){
								Ext.Msg.hide(); 
								Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
									var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs,inStockConfirmFlag:'Y'}};
									Ext.Ajax.request({
						    			url: stock.realPath('inStock.action'),
										timeout: 300000,
						    			jsonData:jsonParam,
						    			success:function(response){
										Ext.Msg.hide(); 
						    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
						    				var result = Ext.decode(response.responseText);
						    				//清空MAP
						    				stock.stockmanage.waybillGoodsMap.clear();
						    				stock.stockmanage.waybillGrid.store.load();
						    				var inStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getInStockWindow();
					    					inStockWindow.close();
						    			},
						    			exception : function(response) {
						    				var result = Ext.decode(response.responseText);
						    				
						    				if(result.stockVO.exceptionCode == 'error.need.change.transport.path'){
						    					//Ext.MessageBox.confirm('提示', result.message+'?' ,function(btn){
						    					Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), result.message+'?' ,function(btn){
							    					if(btn == 'yes'){
													Ext.Msg.hide(); 
													Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
														var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs,inStockConfirmFlag:'Y',confirmFlag:'Y'}};
														Ext.Ajax.request({
											    			url: stock.realPath('inStock.action'),
															timeout: 300000,
											    			jsonData:jsonParam,
											    			success:function(response){
															Ext.Msg.hide(); 
											    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
											    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
											    				var result = Ext.decode(response.responseText);
											    				//清空MAP
											    				stock.stockmanage.waybillGoodsMap.clear();
											    				stock.stockmanage.waybillGrid.store.load();
											    				var inStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getInStockWindow();
										    					inStockWindow.close();
											    			},
											    			exception : function(response) {
															Ext.Msg.hide(); 
											    				var result = Ext.decode(response.responseText);
											    				//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
											    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
											    			}
										    			})
													}else{
														Ext.Msg.hide();
													}
												});
						    				}else{
												Ext.Msg.hide(); 
						    					//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
						    					Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
						    				}
						    				
						    				
						    			}
					    			})
								}else{
									Ext.Msg.hide();
								}
							});
	    				}else if(result.stockVO.exceptionCode == 'error.need.change.transport.path'){
	    					//Ext.MessageBox.confirm('提示', result.message+'?' ,function(btn){
	    					Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), result.message+'?' ,function(btn){
								if(btn == 'yes'){
								Ext.Msg.hide(); 
								Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
									var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs,confirmFlag:'Y'}};
									Ext.Ajax.request({
						    			url: stock.realPath('inStock.action'),
						    			jsonData:jsonParam,
										timeout: 300000,
						    			success:function(response){
										Ext.Msg.hide(); 
						    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
						    				var result = Ext.decode(response.responseText);
						    				//清空MAP
						    				stock.stockmanage.waybillGoodsMap.clear();
						    				stock.stockmanage.waybillGrid.store.load();
						    				var inStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getInStockWindow();
					    					inStockWindow.close();
						    			},
						    			exception : function(response) {
										Ext.Msg.hide(); 
						    				var result = Ext.decode(response.responseText);
						    				//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
						    			}
					    			})
								}else{
									Ext.Msg.hide();
								}
							});
	    				}else{
						Ext.Msg.hide(); 
	    					//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
	    					Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
	    				}
	    			}
	    		});
	
        	
//////////////////////////////////////=================*
        
		}
		  }
		}]
		
});

//特殊货区登入页签
Ext.define('Foss.stock.stockmanage.InStockSpecialAreaTabPanel',{
		extend:'Ext.panel.Panel',
		cls:"innerTabPanel",
		bodyCls:'panelContentNToolbar-body',
		margin:'10 5 10 10',
		frame:false,
		layout: 'auto',
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			stock.stockmanage.inStockSpecialAreaForm = Ext.create('Foss.stock.stockmanage.InStockSpecialAreaForm');	
			stock.stockmanage.inStockSpecialAreaSerialNOGrid = Ext.create('Foss.stock.stockmanage.InStockSpecialAreaSerialNOGrid');
			me.items = [
			    stock.stockmanage.inStockSpecialAreaForm,
			    stock.stockmanage.inStockSpecialAreaSerialNOGrid
			];
			me.callParent([cfg]);
		},
		buttons: [
		  { //text: '确认入库',
		    text: stock.stockmanage.i18n('foss.stock.confirminstock'),
		  	cls : 'yellow_button',
		  	handler: function(){
        	if(stock.stockmanage.inStockSpecialAreaForm.getForm().isValid()){
        		var formParams = stock.stockmanage.inStockSpecialAreaForm.getValues();
        		formParams.waybillNO = Ext.getCmp('Foss_stock_stockmanage_InStockSpecialAreaSerialNOGrid_WaybillNO_ID').getValue();
        		var flag = true;//用于判断是不是快递
        		//此处需要验证运单是不是快递
        		Ext.Ajax.request({
        			url: stock.realPath('ifIsExpressWaybill.action'),
        			async:false,
        			params:{'stockVO.waybillNOs':formParams.waybillNO},
        			success:function(response){
        				var result = Ext.decode(response.responseText);
        				if(result.stockVO.ifIsExpressWaybill==false){
        					Ext.Msg.alert("提示","此票货非快递货，您无入库操作权限");
        					flag=result.stockVO.ifIsExpressWaybill;
        					return;
        				}
        			},
        			failure:function(response){
        				Ext.Msg.alert("提示","验证是否是快递运单失败！");
        				return;
        			}
        		});
        		if(flag==false){
        			return;
        		}
	        	//获取所有被选中的记录
	        	var serialNOList = stock.stockmanage.inStockSpecialSerialNOMap.getValues();
	        	
	        	if(serialNOList.length < 1){
	        		//Ext.ux.Toast.msg('提示', '请选择流水号！', 'error', 2000);
	        		Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		
	        		return;
	        	}
	        	
	        	var serialNOs = '';
	        	for(var i=0;i<serialNOList.length;i++){
						serialNOs +=serialNOList[i] + ","; 
					}
				serialNOs = serialNOs.substring(0,serialNOs.length-1);
				Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
	    		var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs}};
	    		//提交入库
	        	Ext.Ajax.request({
	    			url: stock.realPath('inStock.action'),
	    			jsonData:jsonParam,
					timeout: 300000,
	    			success:function(response){
	    				Ext.Msg.hide();
	    				//Ext.ux.Toast.msg('提示', '入库成功！', 'ok', 3000);
	    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
	    				var result = Ext.decode(response.responseText);
	    				//清空MAP
	    				stock.stockmanage.waybillGoodsMap.clear();
	    				stock.stockmanage.waybillGrid.store.load();
	    				var inStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getInStockWindow();
    					inStockWindow.close();
	    			},
	    			exception : function(response) {
	    				var result = Ext.decode(response.responseText);
	    				if(result.stockVO.exceptionCode == 'error.need.change.transport.path'){
	    					//Ext.MessageBox.confirm('提示', result.message+'?' ,function(btn){
	    					Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), result.message+'?' ,function(btn){
								if(btn == 'yes'){
									Ext.Msg.hide();
									Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待
									var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs,confirmFlag:'Y'}};
									Ext.Ajax.request({
						    			url: stock.realPath('inStock.action'),
										timeout: 300000,
						    			jsonData:jsonParam,
						    			success:function(response){
						    				Ext.Msg.hide();
						    				//Ext.ux.Toast.msg('提示', '入库成功！', 'ok', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
						    				//清空MAP
						    				stock.stockmanage.waybillGoodsMap.clear();
						    				stock.stockmanage.waybillGrid.store.load();
						    				var inStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getInStockWindow();
					    					inStockWindow.close();
						    			},
						    			exception : function(response) {
						    				Ext.Msg.hide();
						    				var result = Ext.decode(response.responseText);
						    				//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
						    			}
					    			})
								}else{
									Ext.Msg.hide();
								}
							});
	    				}else{
	    					Ext.Msg.hide();
	    					//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
	    					Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
	    				}
	    			}
	    		});
        	}
        }
		  }
		]
		
});

//单票入库窗口
Ext.define('Foss.stock.stockmanage.InStockWindow', {
	extend: 'Ext.window.Window',
	//title: '单票入库',
	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var inStockTab = Ext.create('Foss.stock.stockmanage.InStockTab'); 	
		me.items = [
		    inStockTab    
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.stockmanage.inStockForm.getForm().reset();
			Ext.getCmp('Foss_stock_stockmanage_GoodsSerialNOGrid_WaybillNO_ID').allowBlank = true;
			Ext.getCmp('Foss_stock_stockmanage_GoodsSerialNOGrid_WaybillNO_ID').reset();
			stock.stockmanage.goodsSerialNOGrid.store.removeAll();
			
			stock.stockmanage.inStockSpecialAreaForm.getForm().reset();
			Ext.getCmp('Foss_stock_stockmanage_InStockSpecialAreaSerialNOGrid_WaybillNO_ID').allowBlank = true;
			Ext.getCmp('Foss_stock_stockmanage_InStockSpecialAreaSerialNOGrid_WaybillNO_ID').reset();
			stock.stockmanage.inStockSpecialAreaSerialNOGrid.store.removeAll();
			
		}
	}
});
//非外场部门单票入库表单
Ext.define('Foss.stock.stockmanage.SaleOrgInStockForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	border: false,
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'textfield',
		fieldLabel: '部门编号',
		name: 'orgCode',
		hidden: true
	},{
		xtype: 'textfield',
		//fieldLabel: '部门',
		fieldLabel: stock.stockmanage.i18n('foss.stock.org'),
		name: 'orgName',
		readOnly: true,
		columnWidth:.5
	},{
		xtype: 'container',
		columnWidth:.1,
		html: '&nbsp;'
	},
	FossDataDictionary.getDataDictionaryCombo('STOCK_TYPE_IN',
		{
			//fieldLabel : '入库类型',
			fieldLabel: stock.stockmanage.i18n('foss.stock.instocktype'),
			name : 'inOutStockType',
			editable : false,
			columnWidth:.4,
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			allowBlank: false,
			//blankText:'字段不能为空'
			blankText: stock.stockmanage.i18n('foss.stock.notnull')
		}
	)
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
//非外场部门单票入库界面 流水号表格 Store
Ext.define('Foss.stock.stockmanage.SaleOrgGoodsSerialNOStore',{
	extend: 'Ext.data.Store',
	fields: ['waybillNO','serialNO','stockStatic'],
	autoLoad: false,
	proxy: {
        type : 'ajax',
        actionMethods:'post',
        url: stock.realPath('queryGoods.action'),
        timeout:300000,
		reader : {
			type : 'json',
			root : 'stockVO.stockList',
			successProperty: 'success'
		}
    },
    listeners: {
		beforeload : function(store, operation, eOpts) {
				Ext.apply(operation, {
					params : {
						'stockVO.inOutStockEntity.waybillNO' : Ext.getCmp('Foss_stock_stockmanage_SaleOrgGoodsSerialNOGrid_WaybillNO_ID').getValue()
					}
				});	
		},
		'load': function(records, operation, success){
			for(var i = 0; i<records.data.length;i++){
				var record = records.data.items[i];
				var recordStatic = record.get('stockStatic');
				if(recordStatic!=null&&recordStatic=='AT'){
					record.set('stockStatic','在库存');
				}
				if(recordStatic!=null&&recordStatic=='SIGN'){
					record.set('stockStatic','已签收');
				}
			}
		}
	}
});
//非外场部门单票入库界面 流水号表格
Ext.define('Foss.stock.stockmanage.SaleOrgGoodsSerialNOGrid', {
	extend:'Ext.grid.Panel',
	columnLines: true,
    frame: true,
    height: 300,
    viewConfig: {
    	stripeRows: false,
    	getRowClass: function(record, rowIndex, rp, ds) {
    		var status = record.get('stockStatic');
    			if(status!=null){
    				return 'predeliver_notice_customer_row_purole';	
    				}
    			}	
    },
	columns: [{
			//header: '流水号', 
			header: stock.stockmanage.i18n('foss.stock.serialno'),
			dataIndex: 'serialNO' 
		},{
			header: '状态', 
			dataIndex: 'stockStatic' 
		}],   
    constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.stock.stockmanage.SaleOrgGoodsSerialNOStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.tbar = [{
					xtype: 'textfield',
					//fieldLabel: '运单号',
					fieldLabel: stock.stockmanage.i18n('foss.stock.waybill'),
			        name: 'waybillNO',
			        id: 'Foss_stock_stockmanage_SaleOrgGoodsSerialNOGrid_WaybillNO_ID',
			        allowBlank: false,
					//blankText:'字段不能为空',
			        blankText: stock.stockmanage.i18n('foss.stock.notnull'),
			        columnWidth:.4,
			        vtype: 'waybill'
				},{
					xtype: 'button',
					//text: '查询',
					text: stock.stockmanage.i18n('foss.stock.search'),
					disabled: !stock.stockmanage.isPermission('stock/queryGoodsButton'),
					hidden: !stock.stockmanage.isPermission('stock/queryGoodsButton'),
					gridContainer: this,
					handler: function() {
						stock.stockmanage.saleOrgGoodsSerialNOGrid.store.load();
					}
				}];
		me.callParent([cfg]);
	},
	listeners: {
			beforeselect : function(rowModel, record, index, eOpts) {
				var recordStatic = record.get('stockStatic');
				if(recordStatic!=null){
					return false;
				}
			}
	}
});
//非外场部门单票入库窗口
Ext.define('Foss.stock.stockmanage.SaleOrgInStockWindow', {
	extend: 'Ext.window.Window',
	//title: '单票入库',
	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var saleOrgInStockForm = Ext.create('Foss.stock.stockmanage.SaleOrgInStockForm'); 	
		stock.stockmanage.saleOrgInStockForm = saleOrgInStockForm;
		var saleOrgGoodsSerialNOGrid = Ext.create('Foss.stock.stockmanage.SaleOrgGoodsSerialNOGrid');
		stock.stockmanage.saleOrgGoodsSerialNOGrid = saleOrgGoodsSerialNOGrid;
		me.items = [
		    saleOrgInStockForm,saleOrgGoodsSerialNOGrid
		];
		me.callParent([cfg]);
	},
	listeners : {
		beforeclose : function(){
			stock.stockmanage.saleOrgInStockForm.getForm().reset();
			Ext.getCmp('Foss_stock_stockmanage_SaleOrgGoodsSerialNOGrid_WaybillNO_ID').allowBlank = true;
			Ext.getCmp('Foss_stock_stockmanage_SaleOrgGoodsSerialNOGrid_WaybillNO_ID').reset();
			stock.stockmanage.saleOrgGoodsSerialNOGrid.store.removeAll();
		}
	},
	buttons: [
		  { //text: '确认入库',
		    text: stock.stockmanage.i18n('foss.stock.confirminstock'),
		    disabled: !stock.stockmanage.isPermission('stock/queryGoodsButton'),
		    hidden: !stock.stockmanage.isPermission('stock/queryGoodsButton'),
		  	cls : 'yellow_button',
		    handler: function(){
        	if(stock.stockmanage.saleOrgInStockForm.getForm().isValid()){
        		var formParams = stock.stockmanage.saleOrgInStockForm.getValues();
        		formParams.waybillNO = Ext.getCmp('Foss_stock_stockmanage_SaleOrgGoodsSerialNOGrid_WaybillNO_ID').getValue();
        		var flag = true;//用于判断是不是快递
        		//此处需要验证运单是不是快递
        		Ext.Ajax.request({
        			url: stock.realPath('ifIsExpressWaybill.action'),
        			async:false,
        			params:{'stockVO.waybillNOs':formParams.waybillNO},
        			success:function(response){
        				var result = Ext.decode(response.responseText);
        				if(result.stockVO.ifIsExpressWaybill==false){
        					Ext.Msg.alert("提示","此票货非快递货，您无入库操作权限");
        					flag=result.stockVO.ifIsExpressWaybill;
        					return;
        				}
        			},
        			failure:function(response){
        				Ext.Msg.alert("提示","验证是否是快递运单失败！");
        				return;
        			}
        		});
        		if(flag==false){
        			return;
        		}
        		//var formParams = stock.stockmanage.saleOrgInStockForm.getValues();
        		//formParams.waybillNO = Ext.getCmp('Foss_stock_stockmanage_SaleOrgGoodsSerialNOGrid_WaybillNO_ID').getValue();
	        	//获取所有被选中的记录
	        	var selectedRecords = stock.stockmanage.saleOrgGoodsSerialNOGrid.getSelectionModel().getSelection();
			 	if(selectedRecords.length < 1){
	        		//Ext.ux.Toast.msg('提示', '请选择流水号！', 'error', 2000);
	        		Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.select.serialno'), 'error', 2000);
	        		return;
	        	}
			 	var serialNoList = new Array();
			 	formParams.waybillNO = selectedRecords[0].data.waybillNO;
			 	for(var i in selectedRecords){
					serialNoList.push(selectedRecords[i].data.serialNO); 
				}
	        	
	        	var serialNOs = '';
	        	for(var i=0;i<serialNoList.length;i++){
						serialNOs += serialNoList[i] + ","; 
					}
				serialNOs = serialNOs.substring(0,serialNOs.length-1);
				
				
	    		var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs}};
	    		Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
	    		//提交入库
	        	Ext.Ajax.request({
	    			url: stock.realPath('inStock.action'),
					timeout: 300000,
	    			jsonData:jsonParam,
	    			success:function(response){
	    				Ext.Msg.hide();
	    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
	    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
	    				var result = Ext.decode(response.responseText);
	    				//清空MAP
	    				stock.stockmanage.waybillGoodsMap.clear();
	    				stock.stockmanage.waybillGrid.store.load();
	    				var saleOrgInStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getSaleOrgInStockWindow();
    					saleOrgInStockWindow.close();
	    			},
	    			exception : function(response) {
		    				var result = Ext.decode(response.responseText);
		    				var exceptionCode = result.stockVO.exceptionCode;
		    				if(exceptionCode == 'error.no.partialline' || exceptionCode == 'error.no.arrive.sheet' ||
		    						exceptionCode == 'error.no.arrange.send' || exceptionCode == 'error.no.air.waybill' ||
		    							exceptionCode == 'error.no.diff'){
		    					//Ext.MessageBox.confirm('提示', result.message+'?' ,function(btn){
	    						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), result.message+'?' ,function(btn){
								if(btn == 'yes'){
									Ext.Msg.hide();
									Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
									var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs,inStockConfirmFlag:'Y'}};
									Ext.Ajax.request({
						    			url: stock.realPath('inStock.action'),
										timeout: 300000,
						    			jsonData:jsonParam,
						    			success:function(response){
						    				Ext.Msg.hide();
						    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
						    				var result = Ext.decode(response.responseText);
						    				//清空MAP
						    				stock.stockmanage.waybillGoodsMap.clear();
						    				stock.stockmanage.waybillGrid.store.load();
						    				var saleOrgInStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getSaleOrgInStockWindow();
					    					saleOrgInStockWindow.close();
						    			},
						    			exception : function(response) {
						    				var result = Ext.decode(response.responseText);
						    				
						    				if(result.stockVO.exceptionCode == 'error.need.change.transport.path'){
						    					//Ext.MessageBox.confirm('提示', result.message+'?' ,function(btn){
					    						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), result.message+'?' ,function(btn){
													if(btn == 'yes'){
														Ext.Msg.hide(); 
														Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
														var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs,inStockConfirmFlag:'Y',confirmFlag:'Y'}};
														Ext.Ajax.request({
											    			url: stock.realPath('inStock.action'),
															timeout: 300000,
											    			jsonData:jsonParam,
											    			success:function(response){
											    				Ext.Msg.hide();
											    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
											    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
											    				var result = Ext.decode(response.responseText);
											    				//清空MAP
											    				stock.stockmanage.waybillGoodsMap.clear();
											    				stock.stockmanage.waybillGrid.store.load();
											    				var saleOrgInStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getSaleOrgInStockWindow();
										    					saleOrgInStockWindow.close();
											    			},
											    			exception : function(response) {
											    				Ext.Msg.hide();
											    				var result = Ext.decode(response.responseText);
											    				//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
											    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
											    			}
										    			})
													}else{
														Ext.Msg.hide();
													}
						    					})
							    			}else{
							    				Ext.Msg.hide();
							    				//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
							    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
							    			}
					    			  }
					    			})
								 }else{
									 Ext.Msg.hide();
								 }
								});
		    				}else if(result.stockVO.exceptionCode == 'error.need.change.transport.path'){
		    					//Ext.MessageBox.confirm('提示', result.message+'?' ,function(btn){
	    						Ext.MessageBox.confirm(stock.stockmanage.i18n('foss.stock.prompt'), result.message+'?' ,function(btn){
								if(btn == 'yes'){
									Ext.Msg.hide();
									Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
									var jsonParam = {stockVO: {inOutStockEntity:formParams,serialNOs:serialNOs,confirmFlag:'Y'}};
									Ext.Ajax.request({
						    			url: stock.realPath('inStock.action'),
										timeout: 300000,
						    			jsonData:jsonParam,
						    			success:function(response){
						    				Ext.Msg.hide();
						    				//Ext.ux.Toast.msg('提示', '入库成功!', 'ok', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.prompt'), stock.stockmanage.i18n('foss.stock.instock.success'), 'ok', 3000);
						    				var result = Ext.decode(response.responseText);
						    				//清空MAP
						    				stock.stockmanage.waybillGoodsMap.clear();
						    				stock.stockmanage.waybillGrid.store.load();
						    				var saleOrgInStockWindow = Ext.getCmp('T_stock-stockmanageindex_content').getSaleOrgInStockWindow();
					    					saleOrgInStockWindow.close();
						    			},
						    			exception : function(response) {
						    				Ext.Msg.hide(); 
						    				var result = Ext.decode(response.responseText);
						    				//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
						    				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
						    			}
					    			})
									}else{
										Ext.Msg.hide();
									}
								});
		    				}else{
		    					Ext.Msg.hide();
		    					//Ext.ux.Toast.msg('入库失败', result.message, 'error', 3000);
		    					Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.instock.failure'), result.message, 'error', 3000);
		    				}
		    			}
	    		});
        	
        	}
        }
		  }
		]
});

//库位确认的窗口
Ext.define('Foss.stock.stockmanage.positionSelectWindow', {
	extend: 'Ext.window.Window',
	title: '库位确认',
//	title: stock.stockmanage.i18n('foss.stock.instock'),
	modal:true,
	closeAction: 'hide',
	width: 800,
	buttons: [],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var kkk = Ext.create('Foss.stock.stockmanage.positionSelectForm');	
		stock.stockmanage.positionSelectForm = kkk;
		me.items = [
		    kkk
		];
		me.callParent([cfg]);
	}
});

//库位确认的Form页面
Ext.define('Foss.stock.stockmanage.positionSelectForm', {
	extend: 'Ext.form.Panel',
	id:'Foss_stock_stockmanage_PositionForm_ID',
	layout: 'column',
	frame: true,
	border: false,
	title : '库位确认',
	//title : stock.stockmanage.i18n('foss.stock.searchstock'),
	defaults: {
		margin: '5 5 5 5',
		columns:4
	},
	items: [{
		xtype: 'combo',
		fieldLabel: '货区',
		//fieldLabel: stock.stockmanage.i18n('foss.stock.product'),
		id:'goodsAreaCodeByOrg',
		name: 'goodsAreaCodeByOrg',
		displayField: 'valueName',
		valueField:'valueCode', 
		//value: 'ALL',
		//columnWidth:.3,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.stockmanage.AreaByOrgcodeStore'),
		listeners : {
		     select : function(combo, record, index) {
		      //每次重新选中的时候把cityCombo的值设置为空
		      //当然也可以通过这个方法为cityCombo设置一个初始值，依据具体应用而定
		      //this.getValue（这个是Id值）或者record.data.provinceName把这个放到setValue里就可以做到了。
		      Ext.getCmp('positionCode').setValue('');
		      Ext.getCmp('positionCode').store.load({
		       params : {
		    	   'stockVO.goodsAreaCode' : this.getValue()
		        //cityStore向'searchCityList.html'请求是附带参数provinceId，可以根据这个值查询出某个省相应的城市信息
		       }
		      });
		     }
		}
	},{
		xtype: 'combo',
		fieldLabel: '库位',
		//fieldLabel: stock.stockmanage.i18n('foss.stock.product'),
		id: 'positionCode',
		name: 'positionCode',
		displayField: 'valueName',
		valueField:'valueCode', 
//		value: 'ALL',
//		columnWidth:.3,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		store:Ext.create('Foss.stock.stockmanage.PositionStore')
	}],
	buttons: [{
		autoWidth : true,
		xtype:'button',
		text: '库位确认',
		handler: function(){
			var positionCodeExt = Ext.getCmp('positionCode').value;
			if(positionCodeExt==null || !positionCodeExt.length>0){
				Ext.ux.Toast.msg('提示', '请选择库位！', 'error', 2000);
				return;
			}
			
			var goodsMapList = stock.stockmanage.waybillGoodsMap.getValues();
			if(!goodsMapList.length > 0){
				Ext.ux.Toast.msg('提示', '请选择需要库位确认的货物！', 'error', 2000);
				return;
			}
			var inStockList = new Array();
			var RecordMapList;
			for(var i=0;i<goodsMapList.length;i++){
				RecordMapList = goodsMapList[i].getValues();
				for(var j=0;j<RecordMapList.length;j++){
					inStockList.push(RecordMapList[j].data);
				}
			}
			
			var jsonParam = {stockVO: {inStockList:inStockList,position:positionCodeExt}};
			Ext.Msg.wait('处理中，请稍后...', '提示'); //进度条等待  
			Ext.Ajax.request({
    			url: stock.realPath('updatePosition.action'),
    			jsonData:jsonParam,
    			timeout: 300000,
    			success:function(response){
    				Ext.Msg.hide();
    				var result = Ext.decode(response.responseText);
    				stock.stockmanage.waybillGoodsMap.clear();
    				stock.stockmanage.waybillGrid.store.load();
    				Ext.ux.Toast.msg('提示', '库位确认成功!', 'ok', 3000);
    				stock.stockmanage.positionSelectWindow.hide();
    			},
    			exception : function(response) {
    				Ext.Msg.hide();
    				var result = Ext.decode(response.responseText);
    				Ext.ux.Toast.msg('库位确认失败', result.message, 'error', 3000);
    			}
			});
			
			
		}
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});



Ext.onReady(function(){
	
	Ext.Ajax.request({
		url: stock.realPath('queryOrgCodeInfo.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			stock.stockmanage.orgCode = result.stockVO.stockOrgCode;
			stock.stockmanage.orgName = result.stockVO.stockOrgName;
			stock.stockmanage.isTransferCenter = result.stockVO.isTransferCenter;
			stock.stockmanage.isStationDelivery = result.stockVO.isStationDelivery;
			stock.stockmanage.isAirDispatch = result.stockVO.isAirDispatch;
			stock.stockmanage.goodsAreaName = result.stockVO.goodsAreaName;
			stock.stockmanage.goodsAreaCode = result.stockVO.goodsAreaCode;
			
			
			//外场
			if(stock.stockmanage.isTransferCenter == 'Y'){
				if(!Ext.isEmpty(result.stockVO.specialAreaList[0])){
					stock.stockmanage.valuableAreaCode = result.stockVO.specialAreaList[0].valueCode;
				}
				if(!Ext.isEmpty(result.stockVO.specialAreaList[1])){
					stock.stockmanage.packingAreaCode = result.stockVO.specialAreaList[1].valueCode;
				}
				if(!Ext.isEmpty(result.stockVO.specialAreaList[2])){
					stock.stockmanage.exceptionAreaCode = result.stockVO.specialAreaList[2].valueCode;
				}
			}
			
			Ext.QuickTips.init();
			var queryform = Ext.create('Foss.stock.stockmanage.QueryForm');
			stock.stockmanage.queryform = queryform;
			var waybillGrid = Ext.create('Foss.stock.stockmanage.WaybillGrid');
			stock.stockmanage.waybillGrid = waybillGrid;
			stock.stockmanage.waybillMap = new Ext.util.HashMap();//
			stock.stockmanage.waybillGoodsMap = new Ext.util.HashMap();//用于查询库存界面存放已勾选的货件库存
			stock.stockmanage.inStockSerialNOMap = new Ext.util.HashMap();//单件入库界面存放已勾选的流水号
			stock.stockmanage.inStockSpecialSerialNOMap = new Ext.util.HashMap();//特殊货区登入界面存放已勾选的流水号
			Ext.create('Ext.panel.Panel',{
				id:'T_stock-stockmanageindex_content',
				cls:"panelContentNToolbar",
				bodyCls:'panelContent-body',
				inStockWindow:null,
				saleOrgInStockWindow:null,
				getInStockWindow: function() {
					if(this.inStockWindow == null) {
						this.inStockWindow = Ext.create('Foss.stock.stockmanage.InStockWindow');
					}
					return this.inStockWindow;
				},
				getSaleOrgInStockWindow: function() {
					if(this.saleOrgInStockWindow == null) {
						this.saleOrgInStockWindow = Ext.create('Foss.stock.stockmanage.SaleOrgInStockWindow');
					}
					return this.saleOrgInStockWindow;
				},
				items : [queryform,waybillGrid],
				renderTo: 'T_stock-stockmanageindex-body'
			});
			
			stock.stockmanage.queryform.getForm().findField('goodsAreaCode').deptCode = stock.stockmanage.orgCode;
			stock.stockmanage.queryform.getForm().findField('orgCode').setValue(stock.stockmanage.orgCode);
			stock.stockmanage.queryform.getForm().findField('orgCodeName').setValue(result.stockVO.stockOrgName);
			
			//驻地派送部 或 空运总调
			if('Y' == stock.stockmanage.isStationDelivery ){//|| 'Y' == stock.stockmanage.isAirDispatch

				//如果是驻地的,就请求综合提供的那个方法,获取两个库区,然后做成下拉框
				stock.stockmanage.queryform.getForm().findField('goodsAreaCode').setVisible(false);
				stock.stockmanage.queryform.getForm().findField('goodsAreaCodeForStation').setVisible(true);
//				var goodsAreaSelector = stock.stockmanage.queryform.getForm().findField('goodsAreaCode');
//				goodsAreaSelector.setValue(result.stockVO.goodsAreaName);
//				goodsAreaSelector.getStore().load({params:{'goodsAreaVo.goodsAreaEntity.goodsAreaName' : result.stockVO.goodsAreaName}});
//				goodsAreaSelector.setValue(result.stockVO.goodsAreaCode);
//				goodsAreaSelector.addCls('readonlyhaveborder');
//				goodsAreaSelector.setReadOnly(false);
				
			}
			//如果是空运总调
			if('Y' == stock.stockmanage.isAirDispatch){
				var goodsAreaSelector = stock.stockmanage.queryform.getForm().findField('goodsAreaCode');
				goodsAreaSelector.setValue(result.stockVO.goodsAreaName);
				goodsAreaSelector.getStore().load({params:{'goodsAreaVo.goodsAreaEntity.goodsAreaName' : result.stockVO.goodsAreaName}});
				goodsAreaSelector.setValue(result.stockVO.goodsAreaCode);
				goodsAreaSelector.addCls('readonlyhaveborder');
				goodsAreaSelector.setReadOnly(true);
			}
			
			var positionSelectWindow = Ext.create('Foss.stock.stockmanage.positionSelectWindow');
			stock.stockmanage.positionSelectWindow=positionSelectWindow;
			
			
		},
		exception : function(response) {
				var result = Ext.decode(response.responseText);
				//Ext.ux.Toast.msg('获取库存部门失败', result.message, 'error', 3000);
				Ext.ux.Toast.msg(stock.stockmanage.i18n('foss.stock.get.stockorg.failure'), result.message, 'error', 3000);
				
		}
	});
});

